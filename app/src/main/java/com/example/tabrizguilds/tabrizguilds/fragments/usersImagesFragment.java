package com.example.tabrizguilds.tabrizguilds.fragments;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.adapter.userImagesAdapter;
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.db.DatabaseHelper;
import com.example.tabrizguilds.tabrizguilds.loginActivity;
import com.example.tabrizguilds.tabrizguilds.models.ImgModel;
import com.example.tabrizguilds.tabrizguilds.services.ClassDate;
import com.example.tabrizguilds.tabrizguilds.services.FilePath;
import com.example.tabrizguilds.tabrizguilds.services.WebService;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class usersImagesFragment extends Fragment {


    private RecyclerView recycler;
    private FloatingActionButton floactAction;


    public boolean flagPermission = false;
    private static final int PICK_FILE_REQUEST = 1;
    private String selectedFilePath, selectedImgName;


    private int idUser;
    private SharedPreferences prefs;
    int idRow;
    String detailsResult;
    Dialog dialog2;

    List<ImgModel> imgList;

    public usersImagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users_images, container, false);
        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        floactAction = (FloatingActionButton) view.findViewById(R.id.floactAction);

        Bundle args = getArguments();
        //why this didnt work
//        idRow = args.getInt("ID");
//        mainType = args.getInt("MainType");

        idRow = imagesFragment.idRow;


        prefs = getContext().getSharedPreferences("MYPREFS", 0);
        idUser = prefs.getInt("UserId", -1);

//        setUpRecyclerView();
        WebServiceCallbackList callbackList = new WebServiceCallbackList();
        callbackList.execute();


        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.FOCUS_DOWN) {
                    floactAction.hide();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    // Scroll Down
                    if (floactAction.isShown()) {
                        floactAction.hide();
                    }
                } else if (dy < 0) {
                    // Scroll Up
                    if (!floactAction.isShown()) {
                        floactAction.show();
                    }
                }
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            } else {
                flagPermission = true;
            }
        } else {
            flagPermission = true;
        }

        floactAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagPermission) {

                    if (app.isInternetOn()) {

                        if (idUser > 0) {

                            showFileChooser();
                        } else {
                            Snackbar snackbar = Snackbar.make(getView(), "ابتدا باید ثبت نام کنید", Snackbar.LENGTH_LONG);
                            snackbar.setAction("ثبت نام", new registerAction());

                            Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
                            TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
                            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5f);
                            textView.setLayoutParams(parms);
                            textView.setGravity(Gravity.LEFT);
                            snackbar.setActionTextColor(getResources().getColor(R.color.yellow));
                            snackbar.show();
                        }
                    } else {
                        Snackbar.make(getView(), "به اینترنت متصل نیستید", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });

        return view;
    }

    public class registerAction implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            Intent i = new Intent(getActivity(), loginActivity.class);
            startActivity(i);
        }
    }

    private void setUpRecyclerView(List<ImgModel> list) {

        userImagesAdapter adapter = new userImagesAdapter(getContext(), list);
        recycler.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recycler.setLayoutManager(gridLayoutManager);
    }


    private void showFileChooser() {
        Intent intent = new Intent();
        //sets the select file to all types of files
        intent.setType("*/*");
        //allows to select data and return it
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //starts new activity to select file and return data
        startActivityForResult(Intent.createChooser(intent, "انتخاب فایل"), PICK_FILE_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_FILE_REQUEST) {
                if (data == null) {
                    //no data present
                    return;
                }


                Uri selectedFileUri = data.getData();
                selectedFilePath = FilePath.getPath(this.getActivity(), selectedFileUri);
                Log.i(TAG, "Selected File Path:" + selectedFilePath);

                if (selectedFilePath != null && !selectedFilePath.equals("")) {

                    String extension = selectedFilePath.substring(selectedFilePath.lastIndexOf(".") + 1, selectedFilePath.length());
                    ClassDate classDate = new ClassDate();
                    selectedImgName = classDate.getDateTime()  + "." + extension;

                    CallBackFileDetails callBackFileDetails = new CallBackFileDetails();
                    callBackFileDetails.execute();

                }
            } else {
                Toast.makeText(getContext(), "خطا در انتخاب فایل", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
            flagPermission = true;
        } else
            flagPermission = false;
    }

    public class WebServiceCallbackList extends AsyncTask<Object, Void, Void> {


        private WebService webService;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imgList = new ArrayList<>();
            webService = new WebService();
        }


        @Override
        protected Void doInBackground(Object... objects) {

            imgList = webService.selectUserSentImages(app.isInternetOn(), idRow);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (imgList != null) {
                if (imgList.size() > 0) {
                    setUpRecyclerView(imgList);
                }
            }

        }



    }

    private class CallBackFileDetails extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String lastUpdate;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();

//            dialog2 = new Dialog(getContext());
//            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog2.setContentView(R.layout.dialog_waiting);
//            dialog2.setCancelable(true);
//            dialog2.setCanceledOnTouchOutside(true);
//            dialog2.show();

            ClassDate classDate = new ClassDate();
            lastUpdate = classDate.getDateTime();
        }

        @Override
        protected Void doInBackground(Object... params) {

            detailsResult = webService.postUserImages(app.isInternetOn(), idRow, selectedImgName, idUser, Long.parseLong(lastUpdate));

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

//            dialog2.dismiss();

            if (detailsResult != null) {

                if (Integer.parseInt(detailsResult) > 0) {
                    CallBackFile callBackFile = new CallBackFile();
                    callBackFile.execute();
                } else if (Integer.parseInt(detailsResult) == 0) {
                    dialog2.dismiss();
                    Toast.makeText(getContext(), "ارسال تصویر ناموفق است", Toast.LENGTH_LONG).show();
                } else if (Integer.parseInt(detailsResult) == -1) {
                    dialog2.dismiss();
                    Toast.makeText(getContext(), "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();
                }
            } else {
                dialog2.dismiss();
                Toast.makeText(getContext(), "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class CallBackFile extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        int fileResult;
        String lastUpdate;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();

            dialog2 = new Dialog(getContext());
            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog2.setContentView(R.layout.dialog_waiting);
            dialog2.setCancelable(true);
            dialog2.setCanceledOnTouchOutside(true);
            dialog2.show();

            ClassDate classDate = new ClassDate();
            lastUpdate = classDate.getDateTime();
        }

        @Override
        protected Void doInBackground(Object... params) {

            fileResult = webService.uploadFile(app.isInternetOn(), selectedFilePath, selectedImgName);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if (fileResult == 200) {
                dialog2.dismiss();
                Toast.makeText(getContext(), "تصویر با موفقیت آپلود شد", Toast.LENGTH_SHORT).show();

            } else if (fileResult == 0) {
                Toast.makeText(getContext(), "متاسفانه تصویر آپلود نشد", Toast.LENGTH_SHORT).show();
                CallBackFileDelete callBackFileDelete = new CallBackFileDelete();
                callBackFileDelete.execute();
            } else {
                Toast.makeText(getContext(), "متاسفانه تصویر آپلود نشد", Toast.LENGTH_SHORT).show();
                CallBackFileDelete callBackFileDelete = new CallBackFileDelete();
                callBackFileDelete.execute();
            }
        }
    }

    private class CallBackFileDelete extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String deleteResult;
        String lastUpdate;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();

//            dialog2 = new Dialog(getContext());
//            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog2.setContentView(R.layout.dialog_waiting);
//            dialog2.setCancelable(true);
//            dialog2.setCanceledOnTouchOutside(true);
//            dialog2.show();
//
//            ClassDate classDate = new ClassDate();
//            lastUpdate = classDate.getDateTime();
        }

        @Override
        protected Void doInBackground(Object... params) {

            if (detailsResult != null)
                if (Integer.parseInt(detailsResult) > 0)
                    deleteResult = webService.deleteuserImgDetails(app.isInternetOn(), Integer.parseInt(detailsResult));

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialog2.dismiss();

//            if (deleteResult != null) {
//
//                if (Integer.parseInt(deleteResult) > 0) {
//                    CallBackFile callBackFile = new CallBackFile();
//                    callBackFile.execute();
//                } else if (Integer.parseInt(deleteResult) == 0) {
//                    dialog2.dismiss();
//                    Toast.makeText(getContext(), "ارسال تصویر ناموفق است", Toast.LENGTH_LONG).show();
//                } else if (Integer.parseInt(deleteResult) == -1) {
//                    dialog2.dismiss();
//                    Toast.makeText(getContext(), "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();
//                }
//            } else {
//                dialog2.dismiss();
//                Toast.makeText(getContext(), "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();
//            }
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        prefs = getContext().getSharedPreferences("MYPREFS", 0);
        idUser = prefs.getInt("UserId", -1);
    }
}

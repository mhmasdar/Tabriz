package com.example.tabrizguilds.tabrizguilds.fragments.categories;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tabrizguilds.tabrizguilds.BuildConfig;
import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.RoutingActivity;
import com.example.tabrizguilds.tabrizguilds.ViewPagerCustomDuration;
import com.example.tabrizguilds.tabrizguilds.adapter.organizationAdapter;
import com.example.tabrizguilds.tabrizguilds.adapter.organizationSliderAdapter;
import com.example.tabrizguilds.tabrizguilds.adapter.restaurantListAdapter;
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.db.DatabaseHelper;
import com.example.tabrizguilds.tabrizguilds.models.ImgModel;
import com.example.tabrizguilds.tabrizguilds.models.PhoneModel;
import com.example.tabrizguilds.tabrizguilds.models.PlacesModel;
import com.viewpagerindicator.CirclePageIndicator;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class organizationFragment extends Fragment {


    private ViewPager mPager;
    private int currentPage = 0;
    private RelativeLayout organizationSlider;
    private CirclePageIndicator indicator;
    private LinearLayout lytOrgIntroduce;
    private LinearLayout lytOrgAddress;
    private LinearLayout lytOrgPhone;
    private LinearLayout lytOrgWeb;
    private LinearLayout lytOrgRules;
    private ExpandableLayout expanableLayout1, expanableLayout2, expanableLayout3;
    private boolean openCheck1 = false, openCheck2 = false, openCheck3 = false;
    private int height;
    private boolean OrganiztionSlider = false;
    private ViewPagerCustomDuration pager;
    private TextView txtOrgintroduce;
    private TextView txtOrgAddress;
    private static Timer swipeTimer = new Timer();
    private ImageView imgBack;
    private LinearLayout lytRoute;
    private RecyclerView Recycler;

    PlacesModel placesModel;
    List<PhoneModel> phoneList;
    private List<ImgModel> imgList;
    private DatabaseCallback databaseCallback;
    private DatabaseCallbackPhones CallbackPhones;
    Dialog dialog;
    private String addr = "http://80.191.210.19:7862/api/";
    private String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
    private int permsRequestCode;

    public organizationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_organization, container, false);
        initView(view);
        //initSlider(view);

        databaseCallback = new DatabaseCallback(getContext());
        databaseCallback.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        CallbackPhones = new DatabaseCallbackPhones(getContext());
        CallbackPhones.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        //setUpRecyclerView();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });

        lytOrgIntroduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!openCheck1) {
                    height = lytOrgIntroduce.getHeight(); // get height of layout
                    setLayoutsHeight();
                    expanableLayout1.expand();

                    openCheck1 = true;
                } else {
                    expanableLayout1.setExpanded(false);
                    openCheck1 = false;
                }
            }
        });

        lytOrgAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!openCheck2) {
                    height = lytOrgAddress.getHeight(); // get height of layout
                    setLayoutsHeight();
                    expanableLayout2.expand();

                    openCheck2 = true;
                } else {
                    expanableLayout2.setExpanded(false);
                    openCheck2 = false;
                }
            }
        });


        lytOrgPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!openCheck3) {
                    height = lytOrgPhone.getHeight(); // get height of layout
                    setLayoutsHeight();
                    expanableLayout3.expand();

                    openCheck3 = true;
                } else {
                    expanableLayout3.setExpanded(false);
                    openCheck3 = false;
                }
            }
        });

        lytOrgWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open website in explorer
                String url = "";
                if (placesModel.website != null && !placesModel.website.equals("") && !placesModel.website.equals("null")) {
                    url = placesModel.website;

                    if (!url.startsWith("http://") && !url.startsWith("https://"))
                        url = "http://" + url;
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                } else {
                    Toast.makeText(getContext(), "وب سایت موجود نمی باشد", Toast.LENGTH_LONG).show();
                }
            }
        });

        lytRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent iRouting = new Intent(getContext(), RoutingActivity.class);
//                iRouting.putExtra("PlaceName", placesModel.name);
//                iRouting.putExtra("PlaceLat", placesModel.lat);
//                iRouting.putExtra("PlaceLon", placesModel.lon);
//                //iRouting.putExtra("PlaceType", placesModel.type);
//                iRouting.putExtra("PlaceMainType", 8);
//                startActivity(iRouting);

                showdialog();
            }
        });


        lytOrgRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isReadStateAllowed())
                {
                    downloadFile file = new downloadFile();
                    file.execute();
                }

                else
                {
                    requestStoragePermission();
                    permsRequestCode = 201;
                }
            }
        });

        return view;
    }

    private void showdialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_navigate);
        Button btnGoogle = (Button) dialog.findViewById(R.id.btnGoogle);
        Button btnInside = (Button) dialog.findViewById(R.id.btnInside);

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = "http://maps.google.com/maps?daddr=" + placesModel.lat + "," + placesModel.lon;

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(address));
                startActivity(intent);
                dialog.dismiss();
            }
        });


        btnInside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iRouting = new Intent(getContext(), RoutingActivity.class);
                iRouting.putExtra("PlaceName", placesModel.name);
                iRouting.putExtra("PlaceLat", placesModel.lat);
                iRouting.putExtra("PlaceLon", placesModel.lon);
                iRouting.putExtra("PlaceType", placesModel.type);
                iRouting.putExtra("PlaceMainType", 8);
                startActivity(iRouting);
                dialog.dismiss();
            }
        });



        dialog.show();
    }

    private void initSlider(View view) {

        mPager = (ViewPager) view.findViewById(R.id.pager);


        mPager.setAdapter(new organizationSliderAdapter(getContext(), imgList));


        CirclePageIndicator indicator = (CirclePageIndicator)
                view.findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        indicator.setRadius(5 * density);


        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 3) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        if (OrganiztionSlider == false) {
            swipeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 2000, 4000);
            OrganiztionSlider = true;
        }
        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }

    private void initView(View view) {
        organizationSlider = (RelativeLayout) view.findViewById(R.id.organizationSlider);
        indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
        lytOrgIntroduce = (LinearLayout) view.findViewById(R.id.lytOrgIntroduce);
        lytOrgAddress = (LinearLayout) view.findViewById(R.id.lytOrgAddress);
        lytOrgPhone = (LinearLayout) view.findViewById(R.id.lytOrgPhone);
        lytOrgWeb = (LinearLayout) view.findViewById(R.id.lytOrgWeb);
        lytOrgRules = (LinearLayout) view.findViewById(R.id.lytOrgRules);
        expanableLayout1 = (ExpandableLayout) view.findViewById(R.id.expanableLayout1);
        expanableLayout2 = (ExpandableLayout) view.findViewById(R.id.expanableLayout2);
        expanableLayout3 = (ExpandableLayout) view.findViewById(R.id.expanableLayout3);
        pager = (ViewPagerCustomDuration) view.findViewById(R.id.pager);
        txtOrgintroduce = (TextView) view.findViewById(R.id.txtOrgintroduce);
        txtOrgAddress = (TextView) view.findViewById(R.id.txtOrgAddress);
        imgBack = (ImageView) view.findViewById(R.id.imgBack);
        lytRoute = (LinearLayout) view.findViewById(R.id.lytRoute);
        Recycler = (RecyclerView) view.findViewById(R.id.Recycler);
    }

    private void setUpRecyclerView(List<PhoneModel> list) {

        organizationAdapter adapter = new organizationAdapter(getContext(), list);
        Recycler.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        Recycler.setLayoutManager(mLinearLayoutManagerVertical);
    }

    private void setLayoutsHeight() {
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
        p.weight = 0;
        lytOrgAddress.setLayoutParams(p);
        lytOrgIntroduce.setLayoutParams(p);
        lytOrgPhone.setLayoutParams(p);
        lytOrgWeb.setLayoutParams(p);
    }


    public class DatabaseCallback extends AsyncTask<Object, Void, Void> {


        private DatabaseHelper databaseHelper;
        private Context context;
//        private String tblName;
//        int id;

        public DatabaseCallback(Context context) {
            this.context = context;
//            this.tblName = tblName;
//            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            placesModel = new PlacesModel();
            imgList = new ArrayList<>();
            databaseHelper = new DatabaseHelper(context);
        }


        @Override
        protected Void doInBackground(Object... objects) {

            placesModel = databaseHelper.selectOrgDetail("Tbl_Offices", 6);

            imgList = databaseHelper.selectPlacesImages(8, placesModel.id,1);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (imgList != null)
                if (imgList.size() > 0)
                    initSlider(getView());

            if (placesModel != null) {

                txtOrgAddress.setText(placesModel.address);
                txtOrgintroduce.setText(placesModel.info);
            }

        }

    }


    public class DatabaseCallbackPhones extends AsyncTask<Object, Void, Void> {


        private DatabaseHelper databaseHelper;
        private Context context;
//        private String tblName;
//        int id;

        public DatabaseCallbackPhones(Context context) {
            this.context = context;
//            this.tblName = tblName;
//            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            phoneList = new ArrayList<>();
            databaseHelper = new DatabaseHelper(context);
        }


        @Override
        protected Void doInBackground(Object... objects) {

            phoneList = databaseHelper.selectAllPhones();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (phoneList != null) {

               setUpRecyclerView(phoneList);
            }

        }

    }


    private class downloadFile extends AsyncTask<Object, Void, Void>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_waiting);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected Void doInBackground(Object... params) {

            String fileName = "aras.pdf";
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, ".aras");
            if (!folder.isDirectory())
                folder.mkdir();


            File pdfFile = new File(folder, fileName);

            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;

            try {
                URL url = new URL("http://80.191.210.19:7862/Content/Upload/PDF/arasfz.pdf");
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();



                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream(pdfFile);

                byte data[] = new byte[1*1024*1024];
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    output.write(data, 0, count);
                }
            } catch (Exception e) {

            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialog.dismiss();
            File pdfFile = new File(Environment.getExternalStorageDirectory() + "/.aras/" + "aras.pdf");  // -> filename = maven.pdf
            openFile(pdfFile);
        }
    }

    private void openFile(File file){
        Uri path;

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
            path = Uri.fromFile(file);
        else
            path = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".fileprovider", file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        // Check what kind of file you are trying to open, by comparing the url with extensions.
        // When the if condition is matched, plugin sets the correct intent (mime) type,
        // so Android knew what application to use to open the file
        if(path.toString().contains(".pdf") || path.toString().contains(".PDF")) {
            // PDF file
            intent.setDataAndType(path, "application/pdf");
        }
        else {
            //if you want you can also define the intent type for any other file

            //additionally use else clause below, to manage other unknown extensions
            //in this case, Android will show all applications installed on the device
            //so you can choose which application to use
            intent.setDataAndType(path, "*/*");
        }

        try {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), "هیچ برنامه ای برای باز کردن فایل وجود ندارد", Toast.LENGTH_LONG).show();
        }

    }


    public boolean isReadStateAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    public void requestStoragePermission(){

        requestPermissions(perms, permsRequestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            downloadFile file = new downloadFile();
            file.execute();
        }
        else
        {
            Toast.makeText(getContext() , "لطفا برای دانلود فایل اجازه دسترسی را صادر کنید " , Toast.LENGTH_LONG).show();
        }
    }



    @Override
    public void onStop() {
        super.onStop();

        if(databaseCallback != null && databaseCallback.getStatus() == AsyncTask.Status.RUNNING)
            databaseCallback.cancel(true);
        if(CallbackPhones != null && CallbackPhones.getStatus() == AsyncTask.Status.RUNNING)
            CallbackPhones.cancel(true);
    }
}

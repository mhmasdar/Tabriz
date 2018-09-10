package com.example.tabrizguilds.tabrizguilds.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.RoutingActivity;
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.dateConvert;
import com.example.tabrizguilds.tabrizguilds.db.DatabaseHelper;
import com.example.tabrizguilds.tabrizguilds.loginActivity;
import com.example.tabrizguilds.tabrizguilds.models.EventModel;
import com.example.tabrizguilds.tabrizguilds.models.ImgModel;
import com.example.tabrizguilds.tabrizguilds.services.WebService;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class eventsDetailsFragment extends Fragment {

    TextView txtTitle, txtStartDate, txtEndtDate, txtAddress, txtInfo, txtLikeCount;
    Button btnCall, btnAddtoCalender;
    ImageView imgBookmark, imgTitle, imgShare, imgBack;
    private LikeButton btnLike;
    LinearLayout lytLocation;

    EventModel currentModel = new EventModel();
    private List<ImgModel> imgList;

    private int mainType;
    private String tblName;
    private int id;

    private int idUserFavorite = -1;
    private int idUserLike = -1;
    private int idUser;
    private SharedPreferences prefs;

    private boolean CanAddFavorite = true;
    private boolean CanLike = true;

    Dialog dialog;

    public eventsDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events_details, container, false);
        initView(view);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/font.ttf");
        txtInfo.setTypeface(typeface);

        //set image dark
        imgTitle.setColorFilter(Color.rgb(170, 170, 170), android.graphics.PorterDuff.Mode.MULTIPLY);


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });


//        Bundle args = getArguments();
//        currentModel.id = args.getInt("id");
//        currentModel.body = args.getString("body");
//        currentModel.name = args.getString("name");
//        currentModel.startTime = args.getString("startTime");
//        currentModel.startDate = args.getInt("startDate");
//        currentModel.endTime = args.getString("endTime");
//        currentModel.endDate = args.getInt("endDate");
//        currentModel.lat = args.getInt("lat");
//        currentModel.lon = args.getInt("lon");
//        currentModel.place = args.getString("place");
//        currentModel.address = args.getString("address");
//        currentModel.phone = args.getString("phone");
//        currentModel.website = args.getString("website");
//        currentModel.visibility = args.getBoolean("visibility");

        Bundle args = getArguments();
        id = args.getInt("ID");
        tblName = args.getString("TBL_NAME");
        mainType = 10;

        //setViews();

        DatabaseCallback databaseCallback = new DatabaseCallback(getContext(), tblName, id);
        databaseCallback.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        prefs = getContext().getSharedPreferences("MYPREFS", 0);
        idUser = prefs.getInt("UserId", -1);
        if (idUser > 0) {
            DatabaseCallFavoriteLike databaseCallFavoriteLike = new DatabaseCallFavoriteLike(getContext(), tblName, id);
            databaseCallFavoriteLike.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        btnAddtoCalender.setOnClickListener(btnCalenderClick);


        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, currentModel.name + "\n" + "http://arkatech.ir/");
                startActivity(Intent.createChooser(share, "به اشتراک گذاری از طریق..."));
            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentModel.phone != null) {
                    if (currentModel.phone != "null") {
                        if (currentModel.phone.length() > 0) {
                            Intent intentCall = new Intent(Intent.ACTION_DIAL);
                            intentCall.setData(Uri.fromParts("tel", "0" + currentModel.phone, null));
                            startActivity(intentCall);
                        } else
                            Toast.makeText(getContext(), "شماره تلفن موجود نیست", Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(getContext(), "شماره تلفن موجود نیست", Toast.LENGTH_LONG).show();
            }
        });

        lytLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent iRouting = new Intent(getContext(), RoutingActivity.class);
//                iRouting.putExtra("PlaceName", currentModel.name);
//                iRouting.putExtra("PlaceLat", currentModel.lat);
//                iRouting.putExtra("PlaceLon", currentModel.lon);
//                //iRouting.putExtra("PlaceType", "");
//                iRouting.putExtra("PlaceMainType", 10);
//                startActivity(iRouting);

                showdialog();

            }
        });

        imgBookmark.setOnClickListener(imgBookmarkClick);

        //btnLike.setOnClickListener(btnLikeClick);

        btnLike.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {

                if (CanLike) {

                    if (idUser > 0) {


                        if (idUserLike < 1) {
                            CanLike = false;
                            //btnLike.setLiked(true);
                            currentModel.likeCount++;
                            txtLikeCount.setText(currentModel.likeCount + "");
                            WebServiceCallLikeAdd webServiceCallLikeAdd = new WebServiceCallLikeAdd();
                            webServiceCallLikeAdd.execute();
                        }
                    } else {

                        btnLike.setLiked(false);

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
                }

            }

            @Override
            public void unLiked(LikeButton likeButton) {

                if (CanLike) {

                    if (idUser > 0) {



                        if (idUserLike > 0) {
                            CanLike = false;
                            btnLike.setLiked(false);
                            currentModel.likeCount--;
                            txtLikeCount.setText(currentModel.likeCount + "");
                            WebServiceCallLikeDelete likeDelete = new WebServiceCallLikeDelete();
                            likeDelete.execute();

                        }
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
                String address = "http://maps.google.com/maps?daddr=" + currentModel.lat + "," + currentModel.lon;

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(address));
                startActivity(intent);
                dialog.dismiss();
            }
        });


        btnInside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iRouting = new Intent(getContext(), RoutingActivity.class);
                iRouting.putExtra("PlaceName", currentModel.name);
                iRouting.putExtra("PlaceLat", currentModel.lat);
                iRouting.putExtra("PlaceLon", currentModel.lon);
                iRouting.putExtra("PlaceType", 0);
                iRouting.putExtra("PlaceMainType", 10);
                startActivity(iRouting);
                dialog.dismiss();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });

        dialog.show();
    }

    private void initView(View view) {

        txtTitle = view.findViewById(R.id.txtTitle);
        txtStartDate = view.findViewById(R.id.txtStartDate);
        txtEndtDate = view.findViewById(R.id.txtEndtDate);
        txtAddress = view.findViewById(R.id.txtAddress);
        txtInfo = view.findViewById(R.id.txtInfo);
        btnAddtoCalender = view.findViewById(R.id.btnAddtoCalender);
        btnCall = view.findViewById(R.id.btnCall);
        imgBookmark = view.findViewById(R.id.imgBookmark);
        imgTitle = view.findViewById(R.id.imgTitle);
        imgBack = view.findViewById(R.id.imgBack);
        imgShare = view.findViewById(R.id.imgShare);
        btnLike = view.findViewById(R.id.btnLike);
        txtLikeCount = view.findViewById(R.id.txtLikeCount);
        lytLocation = view.findViewById(R.id.lytLocation);

    }

    private void setViews() {

        txtTitle.setText(currentModel.name);
        txtStartDate.setText(app.changeDateToString(currentModel.startDate) + " ساعت " + currentModel.startTime);
        txtEndtDate.setText(app.changeDateToString(currentModel.endDate) + " ساعت " + currentModel.endTime);
        txtAddress.setText(currentModel.name);
        txtInfo.setText(currentModel.name);

    }

    View.OnClickListener btnCalenderClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String yearStart = String.valueOf(currentModel.startDate).substring(0, 4);
            String monthStart = String.valueOf(currentModel.startDate).substring(4, 6);
            String dayStart = String.valueOf(currentModel.startDate).substring(6, 8);

            String yearEnd = String.valueOf(currentModel.endDate).substring(0, 4);
            String monthEnd = String.valueOf(currentModel.endDate).substring(4, 6);
            String dayEnd = String.valueOf(currentModel.endDate).substring(6, 8);

            String gregorianDate;

            dateConvert convert = new dateConvert();
            convert.PersianToGregorian(Integer.parseInt(yearStart), Integer.parseInt(monthStart), Integer.parseInt(dayStart));
            gregorianDate = convert.toString().replace("/", "");

            Calendar beginTime = Calendar.getInstance();
            beginTime.set(Integer.parseInt(gregorianDate.substring(0, 4)), Integer.parseInt(gregorianDate.substring(4, 6)), Integer.parseInt(gregorianDate.substring(6, 8)), Integer.parseInt(currentModel.startTime.substring(0, 2)), Integer.parseInt(currentModel.startTime.substring(3, 5)));

            convert.PersianToGregorian(Integer.parseInt(yearEnd), Integer.parseInt(monthEnd), Integer.parseInt(dayEnd));
            gregorianDate = convert.toString().replace("/", "");

            Calendar endTime = Calendar.getInstance();
            endTime.set(Integer.parseInt(gregorianDate.substring(0, 4)), Integer.parseInt(gregorianDate.substring(4, 6)), Integer.parseInt(gregorianDate.substring(6, 8)), Integer.parseInt(currentModel.endTime.substring(0, 2)), Integer.parseInt(currentModel.endTime.substring(3, 5)));

            Intent intent = new Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                    .putExtra(CalendarContract.Events.TITLE, currentModel.name)
                    .putExtra(CalendarContract.Events.DESCRIPTION, currentModel.body)
                    .putExtra(CalendarContract.Events.EVENT_LOCATION, currentModel.address)
                    .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                    .putExtra(Intent.EXTRA_EMAIL, currentModel.website);
            startActivity(intent);

        }
    };

    View.OnClickListener imgBookmarkClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (CanAddFavorite) {

                if (idUser > 0) {

                    CanAddFavorite = false;

                    if (idUserFavorite > 0) {
                        imgBookmark.setImageResource(R.drawable.ic_bookmark1);
                        WebServiceCallBackFavoriteDelete favoriteDelete = new WebServiceCallBackFavoriteDelete();
                        favoriteDelete.execute();

                    } else {
                        imgBookmark.setImageResource(R.drawable.ic_bookmark1_selected);
                        WebServiceCallBackFavoriteAdd webServiceCallBackFavoriteAdd = new WebServiceCallBackFavoriteAdd();
                        webServiceCallBackFavoriteAdd.execute();
                    }
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
            }

        }
    };

    public class registerAction implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            Intent i = new Intent(getActivity(), loginActivity.class);
            startActivity(i);
        }
    }

    View.OnClickListener btnLikeClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (CanLike) {

                if (idUser > 0) {

                    CanLike = false;

                    if (idUserLike > 0) {
                        btnLike.setLiked(false);
                        currentModel.likeCount--;
                        txtLikeCount.setText(currentModel.likeCount + "");
                        WebServiceCallLikeDelete likeDelete = new WebServiceCallLikeDelete();
                        likeDelete.execute();

                    } else if (idUserLike < 1) {
                        btnLike.setLiked(true);
                        currentModel.likeCount++;
                        txtLikeCount.setText(currentModel.likeCount + "");
                        WebServiceCallLikeAdd webServiceCallLikeAdd = new WebServiceCallLikeAdd();
                        webServiceCallLikeAdd.execute();
                    }
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
            }

        }
    };


    public class DatabaseCallback extends AsyncTask<Object, Void, Void> {


        private DatabaseHelper databaseHelper;
        private Context context;
        private String tblName;
        int id;

        public DatabaseCallback(Context context, String tblName, int id) {
            this.context = context;
            this.tblName = tblName;
            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            currentModel = new EventModel();
            imgList = new ArrayList<>();
            databaseHelper = new DatabaseHelper(context);
        }


        @Override
        protected Void doInBackground(Object... objects) {

//            currentModel = databaseHelper.selectEventsDetail(tblName, id);

            //imgList = databaseHelper.selectPlacesImages(10, id);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            txtTitle.setText(currentModel.name);
            if (String.valueOf(currentModel.startDate).length() == 8)
            txtStartDate.setText(app.changeDateToString(currentModel.startDate) + " ساعت " + currentModel.startTime);
            if (String.valueOf(currentModel.endDate).length() == 8)
            txtEndtDate.setText(app.changeDateToString(currentModel.endDate) + " ساعت " + currentModel.endTime);
            txtAddress.setText(currentModel.address);
            txtInfo.setText(currentModel.body);
            txtLikeCount.setText(currentModel.likeCount + "");
            if (currentModel.image != null)
                if (!currentModel.image.equals(""))
                    Glide.with(context).load(app.imgMainAddr + app.eventImgAddr + currentModel.image).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgTitle);
        }

    }

    private class WebServiceCallBackFavoriteAdd extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.postFavorite(app.isInternetOn(), id, idUser, mainType);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (result != null) {

                if (Integer.parseInt(result) > 0) {
                    idUserFavorite = Integer.parseInt(result);
                    DatabaseCallUpdateFavorite favoriteUpdate = new DatabaseCallUpdateFavorite(getContext(), tblName, id, Integer.parseInt(result));
                    favoriteUpdate.execute();
                } else {
                    Toast.makeText(getContext(), "ثبت علاقه مندی نا موفق", Toast.LENGTH_LONG).show();
                    imgBookmark.setImageResource(R.drawable.ic_bookmark1);
                }

            } else {
                Toast.makeText(getContext(), "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
                imgBookmark.setImageResource(R.drawable.ic_bookmark1);
            }

            CanAddFavorite = true;

        }

    }

    private class WebServiceCallBackFavoriteDelete extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.deleteFavorite(app.isInternetOn(), idUserFavorite);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (result != null) {

                if (result.equals("true")) {
                    idUserFavorite = -1;
                    DatabaseCallUpdateFavorite favoriteUpdate = new DatabaseCallUpdateFavorite(getContext(), tblName, id, -1);
                    favoriteUpdate.execute();
                } else {
                    Toast.makeText(getContext(), "حذف علاقه مندی نا موفق", Toast.LENGTH_LONG).show();
                    imgBookmark.setImageResource(R.drawable.ic_bookmark1_selected);
                }

            } else {
                Toast.makeText(getContext(), "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
                imgBookmark.setImageResource(R.drawable.ic_bookmark1_selected);
            }

            CanAddFavorite = true;

        }

    }

    public class DatabaseCallUpdateFavorite extends AsyncTask<Object, Void, Void> {


        private DatabaseHelper databaseHelper;
        private Context context;
        private String tblName;
        int idRow, idFavorite;

        public DatabaseCallUpdateFavorite(Context context, String tblName, int idRow, int idFavorite) {
            this.context = context;
            this.tblName = tblName;
            this.idRow = idRow;
            this.idFavorite = idFavorite;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            databaseHelper = new DatabaseHelper(context);
        }


        @Override
        protected Void doInBackground(Object... objects) {

            databaseHelper.updateTblByFavorite(tblName, idRow, idFavorite);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (idUserFavorite > 0) {
                imgBookmark.setImageResource(R.drawable.ic_bookmark1_selected);
            } else {
                imgBookmark.setImageResource(R.drawable.ic_bookmark1);
            }

        }

    }

    public class DatabaseCallFavoriteLike extends AsyncTask<Object, Void, Void> {


        private DatabaseHelper databaseHelper;
        private Context context;
        private String tblName;
        int id;

        public DatabaseCallFavoriteLike(Context context, String tblName, int id) {
            this.context = context;
            this.tblName = tblName;
            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            databaseHelper = new DatabaseHelper(context);
        }


        @Override
        protected Void doInBackground(Object... objects) {

            idUserFavorite = databaseHelper.selectFavoriteById(tblName, id);

            idUserLike = databaseHelper.selectLikeById(tblName, id);


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (idUserFavorite > 0) {
                imgBookmark.setImageResource(R.drawable.ic_bookmark1_selected);
            } else {
                imgBookmark.setImageResource(R.drawable.ic_bookmark1);
            }

            if (idUserLike > 0) {
                btnLike.setLiked(true);

            } else {
                btnLike.setLiked(false);
            }

        }

    }

    private class WebServiceCallLikeDelete extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;
//        int idLR = -1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();

//            if (idUserLike > 0)
//                idLR = idUserLike;
//            else if (idUserRate > 0 && idUserLike < 1)
//                idLR = idUserRate;
//            else
//                idLR = -1;

            // in this condition idUserLike is always > 0
        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.postLike(app.isInternetOn(), idUserLike, id, idUser, mainType, 0, -1);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (result != null) {

                if (Integer.parseInt(result) >= 0) {
                    idUserLike = -1;
                    DatabaseCallUpdateLike LikeUpdate = new DatabaseCallUpdateLike(getContext(), tblName, id, -1);
                    LikeUpdate.execute();
                } else {
                    Toast.makeText(getContext(), "ثبت نپسندیدن نا موفق", Toast.LENGTH_LONG).show();
                    btnLike.setLiked(true);
                    currentModel.likeCount++;
                    txtLikeCount.setText(currentModel.likeCount + "");
                }

            } else {
                Toast.makeText(getContext(), "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
                btnLike.setLiked(true);
                currentModel.likeCount++;
                txtLikeCount.setText(currentModel.likeCount + "");
            }
            CanLike = true;

        }

    }

    public class DatabaseCallUpdateLike extends AsyncTask<Object, Void, Void> {


        private DatabaseHelper databaseHelper;
        private Context context;
        private String tblName;
        int idRow, idLike;

        public DatabaseCallUpdateLike(Context context, String tblName, int idRow, int idLike) {
            this.context = context;
            this.tblName = tblName;
            this.idRow = idRow;
            this.idLike = idLike;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            databaseHelper = new DatabaseHelper(context);
        }


        @Override
        protected Void doInBackground(Object... objects) {

            databaseHelper.updateTblByLike(tblName, idRow, idLike, currentModel.likeCount);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

//            if (idUserLike > 0){
//                btnLike.setLiked(true);
//            }
//            else{
//                btnLike.setLiked(false);
//            }

        }

    }

    private class WebServiceCallLikeAdd extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;
        int idLR = -1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
        }

        @Override
        protected Void doInBackground(Object... params) {

            // id is for place
            result = webService.postLike(app.isInternetOn(), idLR, id, idUser, mainType, 1, -1);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (result != null) {

                if (Integer.parseInt(result) > 0) {
                    idUserLike = Integer.parseInt(result);
                    DatabaseCallUpdateLike likeUpdate = new DatabaseCallUpdateLike(getContext(), tblName, id, Integer.parseInt(result));
                    likeUpdate.execute();
                } else {
                    Toast.makeText(getContext(), "ثبت پسندیدن نا موفق", Toast.LENGTH_LONG).show();
                    btnLike.setLiked(false);
                    currentModel.likeCount--;
                    txtLikeCount.setText(currentModel.likeCount + "");
                }

            } else {
                Toast.makeText(getContext(), "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
                btnLike.setLiked(false);
                currentModel.likeCount--;
                txtLikeCount.setText(currentModel.likeCount + "");
            }

            CanLike = true;

        }

    }


    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences prefs = getContext().getSharedPreferences("MYPREFS", 0);
        idUser = prefs.getInt("UserId", -1);
        if (idUser > 0) {
            DatabaseCallFavoriteLike databaseCallFavoriteLike = new DatabaseCallFavoriteLike(getContext(), tblName, id);
            databaseCallFavoriteLike.execute();
        }
    }
}

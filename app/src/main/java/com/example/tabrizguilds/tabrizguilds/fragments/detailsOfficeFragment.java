package com.example.tabrizguilds.tabrizguilds.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.RoutingActivity;
import com.example.tabrizguilds.tabrizguilds.adapter.detailsSliderAdapter;
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.db.DatabaseHelper;
import com.example.tabrizguilds.tabrizguilds.models.ImgModel;
import com.example.tabrizguilds.tabrizguilds.models.PlacesModel;
import com.like.LikeButton;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class detailsOfficeFragment extends Fragment {

    private ViewPager mPager;
    private int currentPage = 0;
    private boolean detailsSlider = false;
    public Timer swipeTimer = new Timer();
    private LinearLayout lytCall;
    private TextView txtName;
    private ImageView imgBack;
    private LinearLayout lytLocation;
    private TextView txtAddress;
    private TextView txtInfo;
    private LinearLayout lytWebsite;
    ImageView imgMenuAndCost;
    TextView txtMenuAndCost;

    String tblName;
    int id;
    PlacesModel placesModel;
    private List<ImgModel> imgList;

    Dialog dialog;

    LinearLayout lytGallery;

    public detailsOfficeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details_office, container, false);

        Bundle args = getArguments();
        id = args.getInt("ID");
        tblName = args.getString("TBL_NAME");

        initView(view);

        Animation fade_in = AnimationUtils.loadAnimation(getContext(), R.anim.details_gallery_layout);
        lytGallery.startAnimation(fade_in);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/font.ttf");
        txtInfo.setTypeface(typeface);

        DatabaseCallbackOffice databaseCallbackOffice = new DatabaseCallbackOffice(getContext(), tblName, id);
        databaseCallbackOffice.execute();



        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });

        lytCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (placesModel.tel != null && !placesModel.tel.equals("") && !placesModel.tel.equals("null")) {
//
//                    Intent intentCall = new Intent(Intent.ACTION_DIAL);
//                    intentCall.setData(Uri.fromParts("tel", "0" + placesModel.tel, null));
//                    startActivity(intentCall);
//                }
//                else
//                    Toast.makeText(getContext(), "شماره تلفن موجود نیست", Toast.LENGTH_LONG).show();
            }
        });

        lytWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "";
//                if (placesModel.website != null && !placesModel.website.equals("") && !placesModel.website.equals("null")) {
//                    url = placesModel.website;
//
//                    if (!url.startsWith("http://") && !url.startsWith("https://"))
//                        url = "http://" + url;
//                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                    startActivity(browserIntent);
//                } else {
//                    Toast.makeText(getContext(), "وب سایت موجود نمی باشد", Toast.LENGTH_LONG).show();
//                }
            }
        });

        lytLocation.setOnClickListener(new View.OnClickListener() {
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

        lytGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryFragment fragment = new galleryFragment();

                Bundle args = new Bundle();
                args.putInt("ID", id);
                args.putInt("MainType", 8);

                fragment.setArguments(args);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                    ft.replace(R.id.container, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        initSlider(view);

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
//                String address = "http://maps.google.com/maps?daddr=" + placesModel.lat + "," + placesModel.lon;

//                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(address));
//                startActivity(intent);
//                dialog.dismiss();
            }
        });


        btnInside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent iRouting = new Intent(getContext(), RoutingActivity.class);
//                iRouting.putExtra("PlaceName", placesModel.name);
//                iRouting.putExtra("PlaceLat", placesModel.lat);
//                iRouting.putExtra("PlaceLon", placesModel.lon);
//                iRouting.putExtra("PlaceType", placesModel.type);
//                iRouting.putExtra("PlaceMainType", 8);
//                startActivity(iRouting);
//                dialog.dismiss();
            }
        });



        dialog.show();
    }

    private void initSlider(View view) {

        mPager = (ViewPager) view.findViewById(R.id.pager);


        mPager.setAdapter(new detailsSliderAdapter(app.context, imgList));


        CirclePageIndicator indicator = (CirclePageIndicator)
                view.findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        indicator.setRadius(3 * density);


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
        if (detailsSlider == false) {
            swipeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 2000, 3000);
            detailsSlider = true;
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

        txtName = (TextView) view.findViewById(R.id.txtName);
        imgBack = (ImageView) view.findViewById(R.id.imgBack);
        lytCall = (LinearLayout) view.findViewById(R.id.lytCall);
        lytLocation = (LinearLayout) view.findViewById(R.id.lytLocation);
        txtAddress = (TextView) view.findViewById(R.id.txtAddress);
        txtInfo = (TextView) view.findViewById(R.id.txtInfo);
        lytWebsite = (LinearLayout) view.findViewById(R.id.lytWebsite);
        lytGallery = view.findViewById(R.id.lytGallery);

    }

    public class DatabaseCallbackOffice extends AsyncTask<Object, Void, Void> {


        private DatabaseHelper databaseHelper;
        private Context context;
        private String tblName;
        int id;

        public DatabaseCallbackOffice(Context context, String tblName, int id) {
            this.context = context;
            this.tblName = tblName;
            this.id = id;
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

            imgList = databaseHelper.selectPlacesImages(8, id, 1);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

//            if (placesModel.address != null && !placesModel.address.equals("null"))
//            txtAddress.setText("آدرس: " + placesModel.address);
//            if (placesModel.info != null && !placesModel.info.equals("null"))
//            txtInfo.setText(placesModel.info);
//            if (placesModel.name != null && !placesModel.name.equals("null"))
//            txtName.setText(placesModel.name);
//
//            if (imgList != null)
//                if (imgList.size() > 0)
//                    initSlider(getView());

        }

    }


}

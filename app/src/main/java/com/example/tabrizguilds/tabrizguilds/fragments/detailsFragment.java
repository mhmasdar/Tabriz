package com.example.tabrizguilds.tabrizguilds.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.RoutingActivity;
import com.example.tabrizguilds.tabrizguilds.adapter.detailsSliderAdapter;
import com.example.tabrizguilds.tabrizguilds.adapter.facilityDialogAdapter;
import com.example.tabrizguilds.tabrizguilds.adapter.menuDialogAdapter;
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.commentsActivity;
import com.example.tabrizguilds.tabrizguilds.db.DatabaseHelper;
import com.example.tabrizguilds.tabrizguilds.loginActivity;
import com.example.tabrizguilds.tabrizguilds.models.FacilityModel;
import com.example.tabrizguilds.tabrizguilds.models.ImgModel;
import com.example.tabrizguilds.tabrizguilds.models.MenuModel;
import com.example.tabrizguilds.tabrizguilds.models.PlacesModel;
import com.example.tabrizguilds.tabrizguilds.models.UserActivityModel;
import com.example.tabrizguilds.tabrizguilds.services.WebService;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class detailsFragment extends Fragment {

    private ViewPager mPager;
    CirclePageIndicator indicator;
    private int currentPage = 0;
    private boolean detailsSlider = false;
    public Timer detailsTimer = new Timer();
    private LinearLayout lytRating;
    private LinearLayout lytGallery;
    private TextView txtName;
    private ImageView imgShare;
    private ImageView imgBookmark;
    private ImageView imgBack;
    private LikeButton btnLike;
    private TextView txtLikeCount;
    private LinearLayout lytCall;
    private LinearLayout lytMenu;
    private LinearLayout lytLocation;
    private LinearLayout lytLoadingM;
    private LinearLayout lytEmptyM, lytDisconnectM;
    private LinearLayout lytLoadingF;
    private LinearLayout lytEmptyF, lytDisconnectF;
    private TextView txtAddress;
    private TextView txtHotelStars;
    private TextView txtInfo, txtHour, txtDay;
    private LinearLayout lytWebsite;
    private LinearLayout lytOptions;
    private LinearLayout lytComments;
    private LinearLayout lytDrivers;
    private LinearLayout lytRouting;
    private ImageView imgMenuAndCost;
    private TextView txtMenuAndCost;
    private RatingBar rateBar;

    private SharedPreferences prefs;

    private int id;
    private PlacesModel placesModel;
    private List<ImgModel> imgList;
    private UserActivityModel userActivityModel;
    private int idUser;
    private boolean isFromFavorite;

    //recycler in dialog_menu
    private RecyclerView recyclerMenu;
    List<MenuModel> menuList;

    //recycler in dialog_Facility
    private RecyclerView recyclerFacility;
    List<FacilityModel> facilityList;

    // diolog rating content
    RatingBar rating_dialog;
    Dialog dialog;
    SmoothProgressBar progressBar;
    LinearLayout lytLoading;

    private boolean CanLike = true;
    private boolean CanAddFavorite = true;


    public detailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        Bundle args = getArguments();
        id = args.getInt("ID");
        isFromFavorite = args.getBoolean("isFromFaavorite");

        initView(view);
//        initSlider(view);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/font.ttf");
        txtInfo.setTypeface(typeface);

        DatabaseCallback databaseCallback = new DatabaseCallback(getContext(), id);
        databaseCallback.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


        if (placesModel.RootCategory == 10) {
            lytDrivers.setVisibility(View.VISIBLE);
            lytMenu.setVisibility(View.GONE);
        }

        prefs = getContext().getSharedPreferences("MYPREFS", 0);
        idUser = prefs.getInt("UserId", -1);
        if (idUser > 0) {
            DatabaseCallFavoriteLikeRate databaseCallFavoriteLikeRate = new DatabaseCallFavoriteLikeRate(getContext(), id);
            databaseCallFavoriteLikeRate.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

        Animation fade_in = AnimationUtils.loadAnimation(getContext(), R.anim.details_gallery_layout);
        lytGallery.startAnimation(fade_in);


        lytGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagesFragment fragment = new imagesFragment();

                Bundle args = new Bundle();
                args.putInt("ID", id);

                fragment.setArguments(args);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                if (!isFromFavorite)
                    ft.replace(R.id.container, fragment);
                else
                    ft.replace(R.id.container1, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        lytLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent iRouting = new Intent(getContext(), RoutingActivity.class);
//                iRouting.putExtra("PlaceName", placesModel.Name);
//                iRouting.putExtra("PlaceLat", placesModel.lat);
//                iRouting.putExtra("PlaceLon", placesModel.lon);
//                //iRouting.putExtra("PlaceType", placesModel.type);
//                iRouting.putExtra("PlaceMainType", mainType);
//                startActivity(iRouting);
                showdialog();
            }
        });

        lytDrivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                driversFragment fragment = new driversFragment();

                Bundle args = new Bundle();
                args.putInt("IdRow", id);
                fragment.setArguments(args);

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                if (!isFromFavorite)
                    ft.replace(R.id.container, fragment);
                else
                    ft.replace(R.id.container1, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });


        lytRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRatingDialog();
            }
        });


        lytComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getContext(), commentsActivity.class);
                i.putExtra("IdRow", id);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.fragment_enter, R.anim.fragment_exit);
            }
        });

        lytCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (placesModel.Phone != null && !placesModel.Phone.equals("") && !placesModel.Phone.equals("null")) {

                    Intent intentCall = new Intent(Intent.ACTION_DIAL);
                    intentCall.setData(Uri.fromParts("tel", "0" + placesModel.Phone, null));
                    startActivity(intentCall);
                } else
                    Toast.makeText(getContext(), "شماره تلفن موجود نیست", Toast.LENGTH_LONG).show();
            }
        });


        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, placesModel.Name + " را در همراه ارس ببین\n" + "http://arkatech.ir/");
                startActivity(Intent.createChooser(share, "به اشتراک گذاری از طریق..."));
            }
        });

        lytWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "";
                if (placesModel.webSite != null && !placesModel.webSite.equals("") && !placesModel.webSite.equals("null")) {
                    url = placesModel.webSite;

                    if (!url.startsWith("http://") && !url.startsWith("https://"))
                        url = "http://" + url;
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                } else {
                    Toast.makeText(getContext(), "شبکه اجتماعی موجود نمی باشد", Toast.LENGTH_LONG).show();
                }
            }
        });

        lytMenu.setOnClickListener(lytMenuClick);

        lytOptions.setOnClickListener(lytOptionsClick);

        imgBookmark.setOnClickListener(imgBookmarkClick);

        //btnLike.setOnClickListener(btnLikeClick);

        btnLike.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {


                if (CanLike) {

                    if (idUser > 0) {

                        CanLike = false;

                        if (userActivityModel.idUserRate > 0 && userActivityModel.idUserLike < 1) {
                            //btnLike.setLiked(true);
                            placesModel.likeCount++;
                            txtLikeCount.setText(placesModel.likeCount + "");
                            WebServiceCallLikeAdd webServiceCallLikeAdd = new WebServiceCallLikeAdd();
                            webServiceCallLikeAdd.execute();
                        } else if (userActivityModel.idUserRate < 1 && userActivityModel.idUserLike < 1) {
                            //btnLike.setLiked(true);
                            placesModel.likeCount++;
                            txtLikeCount.setText(placesModel.likeCount + "");
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

                        CanLike = false;

                        if (userActivityModel.idUserLike > 0) {
                            //btnLike.setLiked(false);
                            placesModel.likeCount--;
                            txtLikeCount.setText(placesModel.likeCount + "");
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
                String address = "http://maps.google.com/maps?daddr=" + placesModel.Lat + "," + placesModel.Long;

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(address));
                startActivity(intent);
                dialog.dismiss();
            }
        });


        btnInside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iRouting = new Intent(getContext(), RoutingActivity.class);
                iRouting.putExtra("PlaceName", placesModel.Name);
                iRouting.putExtra("PlaceLat", placesModel.Lat);
                iRouting.putExtra("PlaceLon", placesModel.Long);
                iRouting.putExtra("PlaceType", placesModel.Category);
                startActivity(iRouting);
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private void initSlider() {


        mPager.setAdapter(new detailsSliderAdapter(app.context, imgList));


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

//        app.isScheduled = true;
        if (app.isScheduledSlider == false) {
            app.detailsTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 2000, 4000);
            app.isScheduledSlider = true;
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

    private void showRatingDialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_rating);
        rating_dialog = (RatingBar) dialog.findViewById(R.id.rating_dialog);
        Button btnSubmitRating = (Button) dialog.findViewById(R.id.btnSubmitRating);
        Button btnCancelRating = (Button) dialog.findViewById(R.id.btnCancelRating);
        progressBar = dialog.findViewById(R.id.progressBar);
        lytLoading = dialog.findViewById(R.id.lytLoading);
        btnCancelRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSubmitRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (idUser > 0) {

//                    if (idUserLike > 0) {
//                        btnLike.setLiked(false);
//                        WebServiceCallLikeDelete likeDelete = new WebServiceCallLikeDelete();
//                        likeDelete.execute();
//
//                    } else if (idUserRate > 0 && idUserLike < 1){
//                        btnLike.setLiked(true);
//                        WebServiceCallLikeAdd webServiceCallLikeAdd = new WebServiceCallLikeAdd();
//                        webServiceCallLikeAdd.execute();
//                    } else if (idUserRate < 1 && idUserLike < 1){
//                        btnLike.setLiked(true);
//                        WebServiceCallLikeAdd webServiceCallLikeAdd = new WebServiceCallLikeAdd();
//                        webServiceCallLikeAdd.execute();
//                    }

                    WebServiceCallRateAdd webServiceCallRateAdd = new WebServiceCallRateAdd();
                    webServiceCallRateAdd.execute();

                } else {

                    dialog.dismiss();

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
        });
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public class registerAction implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            Intent i = new Intent(getActivity(), loginActivity.class);
            startActivity(i);
        }
    }

    private void initView(View view) {
        lytRating = (LinearLayout) view.findViewById(R.id.lytRating);
        lytGallery = (LinearLayout) view.findViewById(R.id.lytGallery);
        lytDrivers = (LinearLayout) view.findViewById(R.id.lytDrivers);
        txtName = (TextView) view.findViewById(R.id.txtName);
        txtHotelStars = (TextView) view.findViewById(R.id.txtHotelStars);
        imgShare = (ImageView) view.findViewById(R.id.imgShare);
        imgBookmark = (ImageView) view.findViewById(R.id.imgBookmark);
        imgBack = (ImageView) view.findViewById(R.id.imgBack);
        btnLike = (LikeButton) view.findViewById(R.id.btnLike);
        txtLikeCount = (TextView) view.findViewById(R.id.txtLikeCount);
        lytCall = (LinearLayout) view.findViewById(R.id.lytCall);
        lytMenu = (LinearLayout) view.findViewById(R.id.lytMenu);
        lytLocation = (LinearLayout) view.findViewById(R.id.lytLocation);
        txtAddress = (TextView) view.findViewById(R.id.txtAddress);
        txtInfo = (TextView) view.findViewById(R.id.txtInfo);
        lytWebsite = (LinearLayout) view.findViewById(R.id.lytWebsite);
        lytOptions = (LinearLayout) view.findViewById(R.id.lytOptions);
        lytComments = (LinearLayout) view.findViewById(R.id.lytComments);
        txtDay = view.findViewById(R.id.txtDay);
        txtHour = view.findViewById(R.id.txtHour);
        txtMenuAndCost = view.findViewById(R.id.txtMenuAndCost);
        imgMenuAndCost = view.findViewById(R.id.imgMenuAndCost);
        rateBar = view.findViewById(R.id.rateBar);
        mPager = (ViewPager) view.findViewById(R.id.pager);
        indicator = (CirclePageIndicator)
                view.findViewById(R.id.indicator);
    }

    View.OnClickListener lytMenuClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Dialog dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_menu);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();

            recyclerMenu = dialog.findViewById(R.id.recycler);
            lytLoadingM = dialog.findViewById(R.id.lytLoading);
            lytEmptyM = dialog.findViewById(R.id.lytEmpty);
            lytDisconnectM = dialog.findViewById(R.id.lytDisconnect);

            WebServiceCallBackMenu webServiceCallBackMenu = new WebServiceCallBackMenu();
            webServiceCallBackMenu.execute();

        }
    };

    View.OnClickListener lytOptionsClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Dialog dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_facilities);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();

            recyclerFacility = dialog.findViewById(R.id.recycler);
            lytLoadingF = dialog.findViewById(R.id.lytLoading);
            lytEmptyF = dialog.findViewById(R.id.lytEmpty);
            lytDisconnectF = dialog.findViewById(R.id.lytDisconnect);

            WebServiceCallBackFacilities webServiceCallBackFacilities = new WebServiceCallBackFacilities();
            webServiceCallBackFacilities.execute();

        }
    };

    View.OnClickListener imgBookmarkClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (CanAddFavorite) {

                if (idUser > 0) {

                    CanAddFavorite = false;

                    if (userActivityModel.idUserFavorite > 0) {
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

    View.OnClickListener btnLikeClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (CanLike) {

                if (idUser > 0) {

                    CanLike = false;

                    if (userActivityModel.idUserLike > 0) {
                        btnLike.setLiked(false);
                        placesModel.likeCount--;
                        txtLikeCount.setText(placesModel.likeCount + "");
                        WebServiceCallLikeDelete likeDelete = new WebServiceCallLikeDelete();
                        likeDelete.execute();

                    } else if (userActivityModel.idUserRate > 0 && userActivityModel.idUserLike < 1) {
                        btnLike.setLiked(true);
                        placesModel.likeCount++;
                        txtLikeCount.setText(placesModel.likeCount + "");
                        WebServiceCallLikeAdd webServiceCallLikeAdd = new WebServiceCallLikeAdd();
                        webServiceCallLikeAdd.execute();
                    } else if (userActivityModel.idUserRate < 1 && userActivityModel.idUserLike < 1) {
                        btnLike.setLiked(true);
                        placesModel.likeCount++;
                        txtLikeCount.setText(placesModel.likeCount + "");
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

    private void setUpRecyclerViewMenu(List<MenuModel> menuList) {

        menuDialogAdapter adapter = new menuDialogAdapter(getContext(), menuList);
        recyclerMenu.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerMenu.setLayoutManager(mLinearLayoutManagerVertical);
    }


    private void setUpRecyclerViewFacilities(List<FacilityModel> facilityList) {

        facilityDialogAdapter adapter = new facilityDialogAdapter(getContext(), facilityList);
        recyclerFacility.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerFacility.setLayoutManager(mLinearLayoutManagerVertical);
    }


    public class DatabaseCallback extends AsyncTask<Object, Void, Void> {


        private DatabaseHelper databaseHelper;
        private Context context;
        int id;

        public DatabaseCallback(Context context, int id) {
            this.context = context;
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

            placesModel = databaseHelper.selectPlacesDetail(id);

            imgList = databaseHelper.selectPlacesImages(id);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (imgList != null)
                if (imgList.size() > 0)
                    initSlider();

            txtLikeCount.setText(placesModel.likeCount + "");
            txtAddress.setText("آدرس: " + placesModel.Address);
            if (!placesModel.Info.equals("null"))
                txtInfo.setText(placesModel.Info);
            txtName.setText(placesModel.Name);
            txtHour.setText(placesModel.StartTime + " تا " + placesModel.EndTime);
            txtDay.setText(placesModel.AvailableDay);

            if (placesModel.RootCategory == 3) {
                txtHotelStars.setVisibility(View.VISIBLE);
                txtHotelStars.setText(placesModel.placeStar);
            }

        }

    }

    public class DatabaseCallFavoriteLikeRate extends AsyncTask<Object, Void, Void> {


        private DatabaseHelper databaseHelper;
        private Context context;
        int id;

        public DatabaseCallFavoriteLikeRate(Context context, int id) {
            this.context = context;
            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            databaseHelper = new DatabaseHelper(context);
            userActivityModel = new UserActivityModel();
        }


        @Override
        protected Void doInBackground(Object... objects) {

            userActivityModel = databaseHelper.selectUserActivityByPlaceId(id);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (userActivityModel.idUserFavorite > 0) {
                imgBookmark.setImageResource(R.drawable.ic_bookmark1_selected);
            } else {
                imgBookmark.setImageResource(R.drawable.ic_bookmark1);
            }
            if (userActivityModel.idUserLike > 0) {
                btnLike.setLiked(true);

            } else {
                btnLike.setLiked(false);
            }
            if (userActivityModel.idUserRate > 0) {
                rateBar.setRating((float) userActivityModel.userRate);
            }

        }

    }

    public class DatabaseCallUpdateFavorite extends AsyncTask<Object, Void, Void> {


        private DatabaseHelper databaseHelper;
        private Context context;
        int idRow, idFavorite;

        public DatabaseCallUpdateFavorite(Context context, int idRow, int idFavorite) {
            this.context = context;
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

            databaseHelper.updateTblByFavorite(idRow, idFavorite);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (userActivityModel.idUserFavorite > 0) {
                imgBookmark.setImageResource(R.drawable.ic_bookmark1_selected);
            } else {
                imgBookmark.setImageResource(R.drawable.ic_bookmark1);
            }

        }

    }

    public class DatabaseCallUpdateLike extends AsyncTask<Object, Void, Void> {


        private DatabaseHelper databaseHelper;
        private Context context;
        int idRow, idLike;

        public DatabaseCallUpdateLike(Context context, int idRow, int idLike) {
            this.context = context;
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

            databaseHelper.updateTblByLike(idRow, idLike, placesModel.likeCount);

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

    public class DatabaseCallUpdateRate extends AsyncTask<Object, Void, Void> {


        private DatabaseHelper databaseHelper;
        private Context context;
        int idRow, idRate;
        double rateValue;

        public DatabaseCallUpdateRate(Context context, int idRow, int idRate, double rateValue) {
            this.context = context;
            this.idRow = idRow;
            this.idRate = idRate;
            this.rateValue = rateValue;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            databaseHelper = new DatabaseHelper(context);
        }


        @Override
        protected Void doInBackground(Object... objects) {

            databaseHelper.updateTblByRate(idRow, idRate, rateValue);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (userActivityModel.idUserRate > 0) {
                //rating_dialog.setRating(R.drawable.ic_bookmark1_selected);
            } else {
                rating_dialog.setRating((float) 2.5);
            }

        }

    }

    private class WebServiceCallBackMenu extends AsyncTask<Object, Void, Void> {

        private WebService webService;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            menuList = new ArrayList<>();
            webService = new WebService();
            lytLoadingM.setVisibility(View.VISIBLE);
            lytDisconnectM.setVisibility(View.GONE);
            lytEmptyM.setVisibility(View.GONE);
            recyclerMenu.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Object... params) {

            menuList = webService.getMenu(app.isInternetOn(), placesModel.id);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            lytLoadingM.setVisibility(View.GONE);

            if (menuList != null) {

                if (menuList.size() > 0) {
                    recyclerMenu.setVisibility(View.VISIBLE);
                    setUpRecyclerViewMenu(menuList);
                } else {
                    lytEmptyM.setVisibility(View.VISIBLE);
                    //Toast.makeText(getContext(), "موردی وجود ندارد", Toast.LENGTH_LONG).show();
                }

            } else {
                //Toast.makeText(getContext(), "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
                lytDisconnectM.setVisibility(View.VISIBLE);
            }

        }

    }

    private class WebServiceCallBackFacilities extends AsyncTask<Object, Void, Void> {

        private WebService webService;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            facilityList = new ArrayList<>();
            webService = new WebService();
            lytLoadingF.setVisibility(View.VISIBLE);
            lytDisconnectF.setVisibility(View.GONE);
            lytEmptyF.setVisibility(View.GONE);
            recyclerFacility.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Object... params) {

            facilityList = webService.getfacility(app.isInternetOn(), placesModel.id);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            lytLoadingF.setVisibility(View.GONE);

            if (facilityList != null) {

                if (facilityList.size() > 0) {
                    setUpRecyclerViewFacilities(facilityList);
                    recyclerFacility.setVisibility(View.VISIBLE);
                } else {
                    //Toast.makeText(getContext(), "موردی وجود ندارد", Toast.LENGTH_LONG).show();
                    lytEmptyF.setVisibility(View.VISIBLE);
                }
            } else {
                //Toast.makeText(getContext(), "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
                lytDisconnectF.setVisibility(View.VISIBLE);
            }

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

            result = webService.postFavorite(app.isInternetOn(), id, idUser, 1);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (result != null) {

                if (Integer.parseInt(result) > 0) {
                    userActivityModel.idUserFavorite = Integer.parseInt(result);
                    DatabaseCallUpdateFavorite favoriteUpdate = new DatabaseCallUpdateFavorite(getContext(), id, Integer.parseInt(result));
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

            result = webService.deleteFavorite(app.isInternetOn(), userActivityModel.idUserFavorite);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (result != null) {

                if (result.equals("true")) {
                    userActivityModel.idUserFavorite = -1;
                    DatabaseCallUpdateFavorite favoriteUpdate = new DatabaseCallUpdateFavorite(getContext(), id, -1);
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

    private class WebServiceCallLikeAdd extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;
        int idLR = -1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            if (userActivityModel.idUserLike > 0)
                idLR = userActivityModel.idUserLike;
            else if (userActivityModel.idUserRate > 0 && userActivityModel.idUserLike < 1)
                idLR = userActivityModel.idUserRate;
            else
                idLR = -1;
        }

        @Override
        protected Void doInBackground(Object... params) {

            // id is for place
            result = webService.postLike(app.isInternetOn(), idLR, id, idUser, 1, -1, 1);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (result != null) {

                if (Integer.parseInt(result) > 0) {
                    userActivityModel.idUserLike = Integer.parseInt(result);
                    DatabaseCallUpdateLike likeUpdate = new DatabaseCallUpdateLike(getContext(), id, Integer.parseInt(result));
                    likeUpdate.execute();
                } else {
                    Toast.makeText(getContext(), "ثبت پسندیدن نا موفق", Toast.LENGTH_LONG).show();
                    btnLike.setLiked(false);
                    placesModel.likeCount--;
                    txtLikeCount.setText(placesModel.likeCount + "");
                }

            } else {
                Toast.makeText(getContext(), "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
                btnLike.setLiked(false);
                placesModel.likeCount--;
                txtLikeCount.setText(placesModel.likeCount + "");
            }

            CanLike = true;

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

            result = webService.postLike(app.isInternetOn(), userActivityModel.idUserLike, id, idUser, 0, -1, 1);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (result != null) {

                if (Integer.parseInt(result) >= 0) {
                    userActivityModel.idUserLike = -1;
                    DatabaseCallUpdateLike LikeUpdate = new DatabaseCallUpdateLike(getContext(), id, -1);
                    LikeUpdate.execute();
                } else {
                    Toast.makeText(getContext(), "ثبت نپسندیدن نا موفق", Toast.LENGTH_LONG).show();
                    btnLike.setLiked(true);
                    placesModel.likeCount++;
                    txtLikeCount.setText(placesModel.likeCount + "");
                }

            } else {
                Toast.makeText(getContext(), "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
                btnLike.setLiked(true);
                placesModel.likeCount++;
                txtLikeCount.setText(placesModel.likeCount + "");
            }

            CanLike = true;

        }

    }

    private class WebServiceCallRateAdd extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;
        double rate;
        int idLR = -1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            lytLoading.setVisibility(View.VISIBLE);
            if (userActivityModel.idUserRate > 0)
                idLR = userActivityModel.idUserRate;
            else if (userActivityModel.idUserRate < 1 && userActivityModel.idUserLike > 0)
                idLR = userActivityModel.idUserLike;
            else
                idLR = -1;

            rate = rating_dialog.getRating();
        }

        @Override
        protected Void doInBackground(Object... params) {

            // id is for place
            result = webService.postLike(app.isInternetOn(), idLR, id, idUser, -1, rate, 1);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            lytLoading.setVisibility(View.INVISIBLE);

            if (result != null) {

                if (Integer.parseInt(result) > 0) {
                    userActivityModel.idUserRate = Integer.parseInt(result);
                    DatabaseCallUpdateRate rateUpdate = new DatabaseCallUpdateRate(getContext(), id, Integer.parseInt(result), rate);
                    rateUpdate.execute();
                    rateBar.setRating((float) rate);
                    dialog.dismiss();

                } else {
                    Toast.makeText(getContext(), "ثبت امتیاز نا موفق", Toast.LENGTH_LONG).show();
                    //btnLike.setLiked(false);
                }

            } else {
                Toast.makeText(getContext(), "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
                //btnLike.setLiked(false);
            }

        }

    }

    @Override
    public void onResume() {
        super.onResume();

        prefs = getContext().getSharedPreferences("MYPREFS", 0);
        idUser = prefs.getInt("UserId", -1);
        if (idUser > 0) {
            DatabaseCallFavoriteLikeRate databaseCallFavoriteLikeRate = new DatabaseCallFavoriteLikeRate(getContext(), id);
            databaseCallFavoriteLikeRate.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }
}

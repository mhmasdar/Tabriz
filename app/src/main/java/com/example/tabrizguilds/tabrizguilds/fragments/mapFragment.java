package com.example.tabrizguilds.tabrizguilds.fragments;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tabrizguilds.tabrizguilds.MainActivity;
import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.RoutingActivity;
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.db.DatabaseHelper;
import com.example.tabrizguilds.tabrizguilds.models.MapModel;
import com.example.tabrizguilds.tabrizguilds.models.PlacesModel;
import com.example.tabrizguilds.tabrizguilds.navigationDrawerActivity;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class mapFragment extends Fragment {


    public MapView map;
    public MyLocationNewOverlay mLocationOverlay;
    public CompassOverlay mCompassOverlay;
    public ItemizedIconOverlay locationOverlay;
    public ItemizedIconOverlay currentLocationOverlay;
    public GeoPoint currentLocation;
    public MyLocationListener locationListener;
    public LocationManager locationManager;
    public boolean flagPermission = false;
    private static final String TAG = MainActivity.class.getSimpleName();
    private RelativeLayout lytMenu;
    private LinearLayout lytMapTools;
    ArrayList<OverlayItem> items = new ArrayList<>();
    ArrayList<OverlayItem> currentItems;
    private LinearLayout lytDetails;
    private TextView txtName, txtAddress;
    private ImageView imgDetails, imgMyLocation, imgZoomOut, imgZoomIn, imgFilter, imgSort, imgNav, imgGoogle;
    private Dialog filterDialog, sortDialog;
    private Animation mp, mp2, mp3, mp4;
    RatingBar rating;


    IMapController mapController;
    OverlayItem myLocationOverlayItem;
    Drawable myCurrentLocationMarker;

    private int zoomLevel = 15;
    List<PlacesModel> placesList;
    List<PlacesModel> filteredList;
    List<PlacesModel> sortedList;
    List<PlacesModel> favoriteList;
    PlacesModel tapedPlace;

    private List<Integer> allFilters;
    private List<Integer> selectedFilters;
    private String selectedSort;
    private List<String> allSorts;

    private SmoothProgressBar lytLoading;

    private Dialog dialog2;

    public mapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Context ctx = getContext();
        //important! set your user agent to prevent getting banned from the osm servers
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        app.check = 4;
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        allFilters = new ArrayList<>();
        allFilters.add(1);
        allFilters.add(2);
        allFilters.add(3);
        allFilters.add(4);
        allFilters.add(5);
        allFilters.add(6);
        allFilters.add(7);
        allFilters.add(8);
        allFilters.add(9);
        allFilters.add(10);
        allFilters.add(11);
        allFilters.add(12);
        allFilters.add(13);

        selectedFilters = allFilters;

        initView(view);

        DatabaseCallbackListAll databaseCallback = new DatabaseCallbackListAll(getContext());
        databaseCallback.execute();

        map.setTileSource(TileSourceFactory.MAPNIK);

        mp = AnimationUtils.loadAnimation(getContext(), R.anim.map_tool);
        mp2 = AnimationUtils.loadAnimation(getContext(), R.anim.map_tool2);
        mp4 = AnimationUtils.loadAnimation(getContext(), R.anim.map_tool3);
        mp3 = AnimationUtils.loadAnimation(getContext(), R.anim.splash0);

        lytMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapIntent = new Intent(getActivity(), navigationDrawerActivity.class);
                startActivity(mapIntent);
                getActivity().overridePendingTransition(R.anim.bottom_to_top, R.anim.stay);
            }
        });


        //map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        mapController = map.getController();
        mapController.setZoom(12);
        GpsMyLocationProvider myLocation = new GpsMyLocationProvider(ctx);
        GeoPoint startPoint = new GeoPoint(38.077583, 46.3025249);
//        GeoPoint startPoint2 = new GeoPoint(38.9939216, 45.8146334);
//        GeoPoint startPoint3 = new GeoPoint(38.9339216, 45.6146334);
        //GeoPoint startPoint = new GeoPoint(myLocation.getLastKnownLocation().getLatitude(), myLocation.getLastKnownLocation().getLongitude());
        mapController.setCenter(startPoint);


        this.mLocationOverlay = new MyLocationNewOverlay(myLocation, map);
        this.mLocationOverlay.enableMyLocation();
        //map.getOverlays().add(this.mLocationOverlay);
        //mapController.setCenter(mLocationOverlay.getMyLocation());

        this.mCompassOverlay = new CompassOverlay(ctx, new InternalCompassOrientationProvider(ctx), map);
        this.mCompassOverlay.enableCompass();
        map.getOverlays().add(this.mCompassOverlay);


        // add marker with custom icon ****************************************************************************************

//        items = new ArrayList<OverlayItem>();
//        myLocationOverlayItem = new OverlayItem("Here1", "Current Position1", startPoint3);
//        myCurrentLocationMarker = this.getResources().getDrawable(R.drawable.tourism);
//        myLocationOverlayItem.setMarker(myCurrentLocationMarker);
//
//        items.add(myLocationOverlayItem);
//
//        myLocationOverlayItem = new OverlayItem("Here2", "Current Position2", startPoint2);
//        myCurrentLocationMarker = this.getResources().getDrawable(R.drawable.transport);
//        myLocationOverlayItem.setMarker(myCurrentLocationMarker);
//
//        items.add(myLocationOverlayItem);
//
//        myLocationOverlayItem = new OverlayItem("Here3", "Current Position3", startPoint3);
//        myCurrentLocationMarker = this.getResources().getDrawable(R.drawable.government);
//        myLocationOverlayItem.setMarker(myCurrentLocationMarker);
//
//        items.add(myLocationOverlayItem);

//        locationOverlay = new ItemizedIconOverlay<OverlayItem>(items,
//                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
//                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
//
//                        Glide.with(getContext()).load(R.drawable.test2).into(imgDetails);
//                        lytDetails.setVisibility(View.VISIBLE);
//                        lytDetails.startAnimation(mp3);
//                        txtName.setText("نام مکان");
//                        txtAddress.setText("آدرس مکان");
//                        return true;
//                    }
//
//                    public boolean onItemLongPress(final int index, final OverlayItem item) {
//                        return true;
//                    }
//                }, getContext());
//        map.getOverlays().add(this.locationOverlay);


        // current location ***********************************************************************************

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || getContext().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            } else {
                flagPermission = true;
            }
        } else {
            flagPermission = true;
        }

        if (flagPermission == true) {
            locationListener = new MyLocationListener();
            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location location;
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            if (location != null) {
                currentLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
                markCurrentLocatin();
            }
        }


        imgZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (map.getZoomLevel() < 19) {
                    mapController.zoomIn();
                }

            }
        });

        imgZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (map.getZoomLevel() > 4) {
                    mapController.zoomOut();
                }
            }
        });


        imgMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentLocation != null) {
                    zoomLevel = 17;
                    mapController.setZoom(zoomLevel);
                    mapController.setCenter(currentLocation);
                    markCurrentLocatin();
                } else {
                    Toast.makeText(getContext(), "موقعیت شما یافت نشد", Toast.LENGTH_LONG).show();
                }
            }
        });

        imgNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iRouting = new Intent(getContext(), RoutingActivity.class);
                iRouting.putExtra("PlaceName", tapedPlace.Name);
                iRouting.putExtra("PlaceLat", tapedPlace.Lat);
                iRouting.putExtra("PlaceLon", tapedPlace.Long);
                iRouting.putExtra("PlaceType", tapedPlace.Category);
                iRouting.putExtra("PlaceMainType", tapedPlace.RootCategory);
                startActivity(iRouting);
                getActivity().overridePendingTransition(R.anim.activity_enter, R.anim.stay);
            }
        });

        imgGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent iRouting = new Intent(getContext(), RoutingActivity.class);
//                iRouting.putExtra("PlaceName", tapedPlace.Name);
//                iRouting.putExtra("PlaceLat", tapedPlace.lat);
//                iRouting.putExtra("PlaceLon", tapedPlace.lon);
//                //iRouting.putExtra("PlaceType", placesModel.type);
//                iRouting.putExtra("PlaceMainType", tapedPlace.mainType);
//                startActivity(iRouting);
//                getActivity().overridePendingTransition(R.anim.activity_enter, R.anim.stay);


//String address = "http://maps.google.com/maps?saddr=" + currentLocation.getLatitude() + "," + currentLocation.getLongitude() + "&daddr=" + tapedPlace.lat + "," + tapedPlace.lon;
                String address = "http://maps.google.com/maps?daddr=" + tapedPlace.Lat + "," + tapedPlace.Long;

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(address));
                startActivity(intent);


            }
        });

        imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFiltersDialog();
            }
        });

        imgSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSortDialog();
            }
        });

        lytDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                app.check = 7;
                detailsFragment fragment = new detailsFragment();
                Bundle args = new Bundle();
                args.putInt("ID", tapedPlace.id);
                fragment.setArguments(args);


                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container, fragment);
                ft.addToBackStack(null);
                ft.commit();

            }
        });


        lytMapTools.startAnimation(mp);
        imgFilter.startAnimation(mp2);
        imgSort.startAnimation(mp2);


        return view;
    }

    public void addEventListener() {
        //detect tap on map ********************************************************************************
        MapEventsReceiver mReceive = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                //Toast.makeText(getBaseContext(),p.getLatitude() + " - "+p.getLongitude(),Toast.LENGTH_LONG).show();

                lytDetails.setVisibility(View.GONE);
                imgNav.setVisibility(View.GONE);
                imgGoogle.setVisibility(View.GONE);

                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        };
        MapEventsOverlay OverlayEvents = new MapEventsOverlay(getContext(), mReceive);
        map.getOverlays().add(OverlayEvents);
    }

    private void initView(View view) {
        map = (MapView) view.findViewById(R.id.map);
        lytMenu = (RelativeLayout) view.findViewById(R.id.lytMenu);
        lytMapTools = (LinearLayout) view.findViewById(R.id.lytMapTools);
        lytDetails = (LinearLayout) view.findViewById(R.id.lytDetails);
        txtName = (TextView) view.findViewById(R.id.txtName);
        txtAddress = (TextView) view.findViewById(R.id.txtAddress);
        imgDetails = (ImageView) view.findViewById(R.id.imgDetails);
        imgMyLocation = (ImageView) view.findViewById(R.id.imgMyLocation);
        imgZoomOut = (ImageView) view.findViewById(R.id.imgZoomOut);
        imgZoomIn = (ImageView) view.findViewById(R.id.imgZoomIn);
        imgFilter = (ImageView) view.findViewById(R.id.imgFilter);
        imgSort = (ImageView) view.findViewById(R.id.imgSort);
        imgNav = (ImageView) view.findViewById(R.id.imgNav);
        rating = view.findViewById(R.id.rating);
        lytLoading = view.findViewById(R.id.lytLoading);
        imgGoogle = view.findViewById(R.id.imgGoogle);
    }

    public void onResume() {
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        Configuration.getInstance().load(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()));
    }


    public class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
            currentLocation = new GeoPoint(location);

            markCurrentLocatin();

//            new Handler().postDelayed(new Runnable() {
//
//            /*
//             * Showing splash screen with a timer. This will be useful when you
//             * want to show case your app logo / company
//             */
//
//                @Override
//                public void run() {
//
//                    markCurrentLocatin();
//
//                }
//            }, 1000);


            //displayMyCurrentLocationOverlay();
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

    private void markCurrentLocatin() {


        currentItems = new ArrayList<>();

        try {
            if (currentLocationOverlay != null)
                if (currentLocationOverlay.size() > 0)
                    currentLocationOverlay.removeAllItems();
        } catch (Exception e) {

        }


        OverlayItem myLocationOverlayItemCurrent = new OverlayItem("current", "Current Position", currentLocation);

        if (this.getView() != null) {
            Drawable myCurrentLocationMarker = this.getResources().getDrawable(R.mipmap.marker_user);
            myLocationOverlayItemCurrent.setMarker(myCurrentLocationMarker);

            currentItems.add(myLocationOverlayItemCurrent);

            currentLocationOverlay = new ItemizedIconOverlay<OverlayItem>(currentItems,
                    new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                        public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                            Toast.makeText(getContext(), "موقعیت خودم", Toast.LENGTH_LONG).show();
                            return true;
                        }

                        public boolean onItemLongPress(final int index, final OverlayItem item) {
                            return true;
                        }
                    }, getContext());
            map.getOverlays().add(this.currentLocationOverlay);

        }

    }

//    private void displayMyCurrentLocationOverlay() {
//        if (currentLocation != null) {
//            if (currentLocationOverlay == null) {
//                currentLocationOverlay = new ItemizedIconOverlay();
//                myCurrentLocationOverlayItem = new OverlayItem(currentLocation, "My Location", "My Location!");
//                currentLocationOverlay.addItem(myCurrentLocationOverlayItem);
//                map.getOverlays().add(currentLocationOverlay);
//            } else {
//                myCurrentLocationOverlayItem.setPoint(currentLocation);
//                currentLocationOverlay.requestRedraw();
//            }
//            map.getController().setCenter(currentLocation);
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
            flagPermission = true;
        } else
            flagPermission = false;

    }

    private void showFiltersDialog() {

        filterDialog = new Dialog(getActivity());
        filterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        filterDialog.setContentView(R.layout.dialog_filters);
        Button btnFilter = (Button) filterDialog.findViewById(R.id.btnFilter);
        Button btn_cancel = (Button) filterDialog.findViewById(R.id.btn_cancel);
        final CheckBox[] checkBox = new CheckBox[14]; // check[0] is all
        checkBox[0] = filterDialog.findViewById(R.id.check0);
        checkBox[1] = filterDialog.findViewById(R.id.check1);
        checkBox[2] = filterDialog.findViewById(R.id.check2);
        checkBox[3] = filterDialog.findViewById(R.id.check3);
        checkBox[4] = filterDialog.findViewById(R.id.check4);
        checkBox[5] = filterDialog.findViewById(R.id.check5);
        checkBox[6] = filterDialog.findViewById(R.id.check6);
        checkBox[7] = filterDialog.findViewById(R.id.check7);
        checkBox[8] = filterDialog.findViewById(R.id.check8);
        checkBox[9] = filterDialog.findViewById(R.id.check9);
        checkBox[10] = filterDialog.findViewById(R.id.check10);
        checkBox[11] = filterDialog.findViewById(R.id.check11);
        checkBox[12] = filterDialog.findViewById(R.id.check12);
        checkBox[13] = filterDialog.findViewById(R.id.check13);

        if (selectedFilters.size() == 13) {
            checkBox[0].setChecked(true);
        } else {
            for (int i = 0; i < selectedFilters.size(); i++) {
                if (selectedFilters.get(i) == 1)
                    checkBox[1].setChecked(true);
                if (selectedFilters.get(i) == 2)
                    checkBox[2].setChecked(true);
                if (selectedFilters.get(i) == 3)
                    checkBox[3].setChecked(true);
                if (selectedFilters.get(i) == 4)
                    checkBox[4].setChecked(true);
                if (selectedFilters.get(i) == 5)
                    checkBox[5].setChecked(true);
                if (selectedFilters.get(i) == 6)
                    checkBox[6].setChecked(true);
                if (selectedFilters.get(i) == 7)
                    checkBox[7].setChecked(true);
                if (selectedFilters.get(i) == 8)
                    checkBox[8].setChecked(true);
                if (selectedFilters.get(i) == 9)
                    checkBox[9].setChecked(true);
                if (selectedFilters.get(i) == 10)
                    checkBox[10].setChecked(true);
                if (selectedFilters.get(i) == 11)
                    checkBox[11].setChecked(true);
                if (selectedFilters.get(i) == 12)
                    checkBox[12].setChecked(true);
                if (selectedFilters.get(i) == 13)
                    checkBox[13].setChecked(true);
            }
        }

        for (int i = 1; i < 13; i++) {
            checkBox[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        checkBox[0].setChecked(false);
                }
            });
        }


        checkBox[0].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 1; i < 13; i++)
                        checkBox[i].setChecked(false);

                }

            }
        });


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterDialog.dismiss();
            }
        });


        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedFilters = new ArrayList<>();
                filteredList = new ArrayList<>();
                map.getOverlays().clear();
                lytDetails.setVisibility(View.GONE);

                if (checkBox[0].isChecked()) {
                    filteredList = placesList;
                } else {

                    for (int j = 1; j < 13; j++) {
                        if (checkBox[j].isChecked()) {
                            for (int i = 0; i < placesList.size(); i++) {
                                if (placesList.get(i).RootCategory == j)
                                    filteredList.add(placesList.get(i));
                            }
                        }
                    }
                }

                boolean isCheckedAtLeastOneCheckbox = false;
                for (int k = 0; k < 13; k++) {
                    if (checkBox[k].isChecked())
                        isCheckedAtLeastOneCheckbox = true;
                }

                if (!checkBox[0].isChecked() && !isCheckedAtLeastOneCheckbox) {
                    Toast.makeText(getContext(), "حداقل یک مورد انتخاب کنید", Toast.LENGTH_LONG).show();
                } else {
                    if (filteredList.size() > 0) {
                        addMarkersToMap(filteredList);
                        filterDialog.dismiss();
                    } else {
                        filterDialog.dismiss();
                        Toast.makeText(getContext(), "موردی وجود ندارد", Toast.LENGTH_LONG).show();
                    }

                }

            }
        });


        filterDialog.setCancelable(true);
        filterDialog.setCanceledOnTouchOutside(true);
        filterDialog.show();
    }

    private void showSortDialog() {

        sortDialog = new Dialog(getActivity());
        sortDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sortDialog.setContentView(R.layout.dialog_sort_map);
        Button btnSort = (Button) sortDialog.findViewById(R.id.btnSort);
        Button btn_cancel = (Button) sortDialog.findViewById(R.id.btn_cancel);
        final RadioButton radioRate = sortDialog.findViewById(R.id.radio1);
        final RadioButton radioNear = sortDialog.findViewById(R.id.radio2);
        final RadioButton radioNone = sortDialog.findViewById(R.id.radio);
        final RadioButton radioFavorite = sortDialog.findViewById(R.id.radio3);

//        switch (selectedSort) {
//            case "none":
//                radioNone.setChecked(true);
//                break;
//            case "rate":
//                radioRate.setChecked(true);
//                break;
//            case "near":
//                radioNear.setChecked(true);
//                break;
//            case "favorite":
//                radioFavorite.setChecked(true);
//                break;
//            default:
//        }

        radioNone.setChecked(true);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortDialog.dismiss();
            }
        });

        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sortedList = new ArrayList<>();

                if (radioNone.isChecked()) {
                    selectedSort = "none";
                    sortedList = filteredList;
                } else if (radioRate.isChecked()) {
                    selectedSort = "rate";
                    for (int i = 0; i < filteredList.size(); i++) {
                        if (filteredList.get(i).Star > 3)
                            sortedList.add(filteredList.get(i));
                    }
                } else if (radioNear.isChecked()) {
                    selectedSort = "near";
                    if (currentLocation != null) {
                        for (int i = 0; i < filteredList.size(); i++) {
                            if (getDistance(currentLocation.getLatitude(), currentLocation.getLongitude(), filteredList.get(i).Lat, filteredList.get(i).Long, "K") < 4)
                                sortedList.add(filteredList.get(i));
                        }
                    } else {
                        sortedList = filteredList;
                        Toast.makeText(getContext(), "موقعیت شما یافت نشد", Toast.LENGTH_LONG).show();
                    }

                } else if (radioFavorite.isChecked()) {
                    selectedSort = "favorite";

                    if (favoriteList != null) {
                        if (favoriteList.size() > 0) {
                            for (int i = 0; i < filteredList.size(); i++) {
                                for (int j = 0; j < favoriteList.size(); j++) {
                                    if (favoriteList.get(j).RootCategory == filteredList.get(i).RootCategory && favoriteList.get(j).id == filteredList.get(i).id) {
                                        sortedList.add(filteredList.get(i));
                                    }
                                }
                            }
                        }
                    } else {
                        Toast.makeText(getContext(), "لیست علاقه مندی ها خالی می باشد", Toast.LENGTH_LONG).show();
                    }


                }


                lytDetails.setVisibility(View.GONE);
                map.getOverlays().clear();
                addMarkersToMap(sortedList);
                sortDialog.dismiss();
            }
        });

        sortDialog.setCancelable(true);
        sortDialog.setCanceledOnTouchOutside(true);
        sortDialog.show();
    }


    private void addMarkersToMap(final List<PlacesModel> placesList) {

        if (this.getView() != null) {

            items = new ArrayList<OverlayItem>();

            for (int i = 0; i < placesList.size(); i++) {

                GeoPoint placeLoc = new GeoPoint(placesList.get(i).Lat, placesList.get(i).Long);
                myLocationOverlayItem = new OverlayItem("" + placesList.get(i).id, placesList.get(i).Name, placeLoc);

                switch (placesList.get(i).RootCategory) {
                    case 1:
                        myCurrentLocationMarker = getResources().getDrawable(R.mipmap.restaurants);
                        break;
                    case 2:
                        myCurrentLocationMarker = this.getResources().getDrawable(R.mipmap.shopping);
                        break;
                    case 3:
                        myCurrentLocationMarker = this.getResources().getDrawable(R.mipmap.hotels);
                        break;
                    case 4:
                        myCurrentLocationMarker = this.getResources().getDrawable(R.mipmap.tourism);
                        break;
                    case 5:
                        myCurrentLocationMarker = this.getResources().getDrawable(R.mipmap.museums);
                        break;
                    case 6:
                        myCurrentLocationMarker = this.getResources().getDrawable(R.mipmap.museums);
                        break;
                    case 7:
                        myCurrentLocationMarker = this.getResources().getDrawable(R.mipmap.museums);
                    case 8:
                        myCurrentLocationMarker = this.getResources().getDrawable(R.mipmap.gym);
                        break;
                    case 9:
                        myCurrentLocationMarker = this.getResources().getDrawable(R.mipmap.transport);
                        break;
                    case 10:
                        myCurrentLocationMarker = this.getResources().getDrawable(R.mipmap.transport);
                        break;
                    case 11:
                        myCurrentLocationMarker = this.getResources().getDrawable(R.mipmap.services);
                        break;
                    case 12:
                        myCurrentLocationMarker = this.getResources().getDrawable(R.mipmap.government);
                        break;
                    case 13:
                        myCurrentLocationMarker = this.getResources().getDrawable(R.mipmap.medical);
                        break;
                }

                myLocationOverlayItem.setMarker(myCurrentLocationMarker);
                items.add(myLocationOverlayItem);

            }

            locationOverlay = new ItemizedIconOverlay<OverlayItem>(items,
                    new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                        public boolean onItemSingleTapUp(final int index, final OverlayItem item) {

                            tapedPlace = placesList.get(index);
                            lytDetails.setVisibility(View.VISIBLE);
                            rating.setVisibility(View.VISIBLE);
                            imgNav.setVisibility(View.VISIBLE);
                            imgNav.setAnimation(mp4);
                            imgGoogle.setVisibility(View.VISIBLE);
                            imgGoogle.setAnimation(mp4);
                            lytDetails.startAnimation(mp3);
                            txtName.setText(placesList.get(index).Name);
                            txtAddress.setText(placesList.get(index).Address);
                            if (placesList.get(index).image != null)
                                if (!placesList.get(index).image.equals(""))
                                    Glide.with(getContext()).load(app.imgMainAddr + app.placesImgAddr + placesList.get(index).image).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgDetails);

                            rating.setRating(Float.parseFloat(placesList.get(index).Star + ""));

                            return true;
                        }

                        public boolean onItemLongPress(final int index, final OverlayItem item) {
                            return true;
                        }
                    }, getContext());
            map.getOverlays().add(this.locationOverlay);


            //detect tap on map
            addEventListener();

        }

    }


    private static double getDistance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }

        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::	This function converts decimal degrees to radians						 :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::	This function converts radians to decimal degrees						 :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    public class DatabaseCallbackListAll extends AsyncTask<Object, Void, Void> {


        private DatabaseHelper databaseHelper;
        private Context context;

        public DatabaseCallbackListAll(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            placesList = new ArrayList<>();
            favoriteList = new ArrayList<>();
            databaseHelper = new DatabaseHelper(context);

//            dialog2 = new Dialog(getContext());
//            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog2.setContentView(R.layout.dialog_waiting);
//            dialog2.setCancelable(false);
//            dialog2.setCanceledOnTouchOutside(false);
//            dialog2.show();

            lytLoading.setVisibility(View.VISIBLE);

        }


        @Override
        protected Void doInBackground(Object... objects) {

            placesList = databaseHelper.selectAllPlacesToMap();

            favoriteList = databaseHelper.selectAllPlacesByFavorite();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            lytLoading.setVisibility(View.GONE);
//            dialog2.dismiss();

            if (placesList != null) {
                filteredList = placesList;
                if (placesList.size() > 0)
                    addMarkersToMap(placesList);
            }

        }

    }


}

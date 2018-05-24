package com.example.tabrizguilds.tabrizguilds;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tabrizguilds.tabrizguilds.fragments.mapFragment;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.bonuspack.routing.RoadNode;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class RoutingActivity extends AppCompatActivity {


    public MapView map;
    public MyLocationNewOverlay mLocationOverlay;
    public CompassOverlay mCompassOverlay;
    public ItemizedIconOverlay locationOverlay;
    public ItemizedIconOverlay currentLocationOverlay;
    ItemizedIconOverlay startLocationOverlay;
    public GeoPoint currentLocation;
    public MyLocationListener locationListener;
    public LocationManager locationManager;
    public boolean flagPermission = false;
    private static final String TAG = MapActivity.class.getSimpleName();
    private RelativeLayout lytBack;
    private LinearLayout lytMapTools;
    ArrayList<OverlayItem> items = new ArrayList<>();
    ArrayList<OverlayItem> currentItems;
    private LinearLayout lytDetails;
    private TextView txtName, txtAddress;
    private ImageView imgDetails, imgMyLocation, imgZoomOut, imgZoomIn, imgFilter, imgSort;
    private Dialog filterDialog, sortDialog;
    private Animation mp, mp2, mp3;
    RatingBar rating;
    private SmoothProgressBar lytLoading;

    IMapController mapController;
    OverlayItem myLocationOverlayItem;
    Drawable myCurrentLocationMarker;
    Polyline roadOverlay;
    Marker nodeMarker;
    RoadNode PreviousNode;

    private double placeLat, placeLon;
    private String placeName = "";
    private int placeType, placeMainType;

    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = getApplicationContext();
        //important! set your user agent to prevent getting banned from the osm servers
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_routing);

        placeLat = getIntent().getDoubleExtra("PlaceLat", 0);
        placeLon = getIntent().getDoubleExtra("PlaceLon", 0);
        placeName = getIntent().getStringExtra("PlaceName");
        placeType = getIntent().getIntExtra("PlaceType", 0);
        placeMainType = getIntent().getIntExtra("PlaceMainType", 0);

        initView();

        mp = AnimationUtils.loadAnimation(ctx, R.anim.map_tool);

        lytBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.stay, R.anim.activity_back_enter);
            }
        });


        map.setMultiTouchControls(true);
        addCompass();

        mapController = map.getController();
        mapController.setZoom(16);
        GpsMyLocationProvider myLocation = new GpsMyLocationProvider(ctx);



        this.mLocationOverlay = new MyLocationNewOverlay(myLocation, map);
        this.mLocationOverlay.enableMyLocation();
        //map.getOverlays().add(this.mLocationOverlay);
        //mapController.setCenter(mLocationOverlay.getMyLocation());


        if (placeLon != 0 && placeLat != 0) {
            addPlaceMarker(placeLat, placeLon);
        } else {
            Toast.makeText(getApplicationContext(), "محل مورد نظر یافت نشد", Toast.LENGTH_LONG).show();
            lytLoading.setVisibility(View.INVISIBLE);
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            } else {
                flagPermission = true;
            }
        } else {
            flagPermission = true;
        }

        if (flagPermission) {
            locationListener = new MyLocationListener();
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (locationManager == null) {
                Toast.makeText(getApplicationContext(), "GPS" + " دستگاه خاموش است", Toast.LENGTH_LONG).show();
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location location;
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            if (location != null) {
                currentLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
                markCurrentLocatin();
                markStartLocation();
                if (currentLocation != null) {
                    if (app.isInternetOn())
                        drawRoute();
                    else
                        Toast.makeText(getApplicationContext(), "اتصال شما با اینترنت برقرار نیست", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "موقعیت شما یافت نشد", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "موقعیت شما یافت نشد", Toast.LENGTH_LONG).show();
            }
        }


        if (currentLocation != null){
                GeoPoint startPoint = new GeoPoint(currentLocation.getLatitude(), currentLocation.getLongitude());
                mapController.setCenter(startPoint);
        }
        else{
            GeoPoint startPoint = new GeoPoint(placeLat, placeLon);
            mapController.setCenter(startPoint);
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
                    mapController.setZoom(17);
                    mapController.setCenter(currentLocation);
                } else {
                    Toast.makeText(getApplicationContext(), "موقعیت شما یافت نشد", Toast.LENGTH_LONG).show();
                }
            }
        });


        lytMapTools.startAnimation(mp);

    }

    private void initView() {
        map = (MapView) findViewById(R.id.map);
        lytBack = (RelativeLayout) findViewById(R.id.lytBack);
        lytMapTools = (LinearLayout) findViewById(R.id.lytMapTools);
//        lytDetails = (LinearLayout) findViewById(R.id.lytDetails);
//        txtName = (TextView) findViewById(R.id.txtName);
//        txtAddress = (TextView) findViewById(R.id.txtAddress);
//        imgDetails = (ImageView) findViewById(R.id.imgDetails);
        imgMyLocation = (ImageView) findViewById(R.id.imgMyLocation);
        imgZoomOut = (ImageView) findViewById(R.id.imgZoomOut);
        imgZoomIn = (ImageView) findViewById(R.id.imgZoomIn);
        lytLoading = findViewById(R.id.lytLoading);
//        imgFilter = (ImageView) findViewById(R.id.imgFilter);
//        imgSort = (ImageView) findViewById(R.id.imgSort);
//        rating = findViewById(R.id.rating);
    }

    private void addCompass(){
        this.mCompassOverlay = new CompassOverlay(ctx, new InternalCompassOrientationProvider(ctx), map);
        this.mCompassOverlay.enableCompass();
        map.getOverlays().add(this.mCompassOverlay);
    }

    private void addPlaceMarker(double lat, double lon) {

        items = new ArrayList<OverlayItem>();

        GeoPoint placeLoc = new GeoPoint(lat, lon);
        myLocationOverlayItem = new OverlayItem("" + placeName, "", placeLoc);

        switch (placeMainType) {
            case 1:
                myCurrentLocationMarker = this.getResources().getDrawable(R.mipmap.restaurants);
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
                myCurrentLocationMarker = this.getResources().getDrawable(R.mipmap.transport);
                break;
            case 7:
                if (placeType == 1)
                    myCurrentLocationMarker = this.getResources().getDrawable(R.mipmap.gym);
                else
                    myCurrentLocationMarker = this.getResources().getDrawable(R.mipmap.services);
                break;
            case 8:
                myCurrentLocationMarker = this.getResources().getDrawable(R.mipmap.government);
                break;
            case 9:
                myCurrentLocationMarker = this.getResources().getDrawable(R.mipmap.medical);
                break;
            case 10:
                myCurrentLocationMarker = this.getResources().getDrawable(R.mipmap.events);
                break;
        }


        myLocationOverlayItem.setMarker(myCurrentLocationMarker);
        items.add(myLocationOverlayItem);

        locationOverlay = new ItemizedIconOverlay<OverlayItem>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {


                        return true;
                    }

                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return true;
                    }
                }, getApplicationContext());
        map.getOverlays().add(this.locationOverlay);

    }


    public class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
            currentLocation = new GeoPoint(location);

            markCurrentLocatin();

//            if (currentLocation != null) {
//                drawRoute();
//            } else {
//                Toast.makeText(getApplicationContext(), "موقعیت شما یافت نشد", Toast.LENGTH_LONG).show();
//            }

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


        OverlayItem myLocationOverlayItemCurrent = new OverlayItem("شما", "موقعیت شما", currentLocation);
        Drawable myCurrentLocationMarker = this.getResources().getDrawable(R.mipmap.marker_user);
        myLocationOverlayItemCurrent.setMarker(myCurrentLocationMarker);

        currentItems.add(myLocationOverlayItemCurrent);

        currentLocationOverlay = new ItemizedIconOverlay<OverlayItem>(currentItems,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        Toast.makeText(getApplicationContext(), "موقعیت خودم", Toast.LENGTH_LONG).show();
                        return true;
                    }

                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return true;
                    }
                }, getApplicationContext());
        map.getOverlays().add(this.currentLocationOverlay);


//        Drawable userIcon = getResources().getDrawable(R.mipmap.marker_user);
//
//
//        Marker m = new Marker(map);
//        m.setPosition(currentLocation);
//        m.setIcon(userIcon);
////        if (currentLocation != null && PreviousNode != null)
////            if (PreviousNode.mLocation != null)
////                m.setRotation((float) bearingBetweenLocations(currentLocation.getLatitude(), currentLocation.getLongitude(), PreviousNode.mLocation.getLatitude(), PreviousNode.mLocation.getLongitude()));
//
//        map.getOverlays().add(m);


    }

    private void markStartLocation(){

//        Drawable startIcon = getResources().getDrawable(R.mipmap.startloc);
//
//        Marker mc = new Marker(map);
//        mc.setPosition(currentLocation);
//        mc.setIcon(startIcon);
////        if (currentLocation != null && PreviousNode != null)
////            if (PreviousNode.mLocation != null)
////                mc.setRotation((float) bearingBetweenLocations(currentLocation.getLatitude(), currentLocation.getLongitude(), PreviousNode.mLocation.getLatitude(), PreviousNode.mLocation.getLongitude()));
//
//        map.getOverlays().add(mc);

        ArrayList<OverlayItem>  startItems = new ArrayList<>();

        OverlayItem startOverlayItem = new OverlayItem("شما", "موقعیت شما", currentLocation);
        Drawable startLocationMarker = this.getResources().getDrawable(R.mipmap.startloc);
        startOverlayItem.setMarker(startLocationMarker);

        startItems.add(startOverlayItem);

        startLocationOverlay = new ItemizedIconOverlay<OverlayItem>(startItems,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        Toast.makeText(getApplicationContext(), "مکان شروع", Toast.LENGTH_LONG).show();
                        return true;
                    }

                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return true;
                    }
                }, getApplicationContext());
        map.getOverlays().add(this.startLocationOverlay);


    }


    private double bearingBetweenLocations(double l1, double o1, double l2, double o2) {

        double PI = 3.14159;
        double lat1 = l1 * PI / 180;
        double long1 = o1 * PI / 180;
        double lat2 = l2 * PI / 180;
        double long2 = o2 * PI / 180;

        double dLon = (long2 - long1);

        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
                * Math.cos(lat2) * Math.cos(dLon);

        double brng = Math.atan2(y, x);

        brng = Math.toDegrees(brng);
        brng = (brng + 360) % 360;

        return brng;
    }


    public void drawRoute() {


        ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
        GeoPoint startPoint = new GeoPoint(currentLocation.getLatitude(), currentLocation.getLongitude());
        waypoints.add(startPoint);
        GeoPoint endPoint = new GeoPoint(placeLat, placeLon);
        waypoints.add(endPoint);


        WebServiceCallBackRoute callBackRoute = new WebServiceCallBackRoute(getApplicationContext(), waypoints);
        callBackRoute.execute();
    }

    private class WebServiceCallBackRoute extends AsyncTask<Object, Void, Void> {

        RoadManager roadManager;
        Road road;
        Context context;
        ArrayList<GeoPoint> waypoints;

        public WebServiceCallBackRoute(Context context, ArrayList<GeoPoint> waypoints) {
            this.context = context;
            this.waypoints = waypoints;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            lytLoading.setVisibility(View.VISIBLE);
            roadManager = new OSRMRoadManager(context);
            PreviousNode = new RoadNode();
        }

        @Override
        protected Void doInBackground(Object... params) {

            road = roadManager.getRoad(waypoints);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if (currentLocation != null) {
                if (placeLon != 0 && placeLat != 0) {
                    if (road != null) {
                        if (road.mNodes != null) {
                            //map.getOverlays().clear();
                            //markCurrentLocatin();
                            //addPlaceMarker(placeLat, placeLon);
                            //addCompass();

                            roadOverlay = RoadManager.buildRoadOverlay(road);
                            roadOverlay.setWidth(12);
                            map.getOverlays().add(roadOverlay);

                            Drawable nodeIcon = getResources().getDrawable(R.mipmap.marker_node);
                            for (int i = 0; i < road.mNodes.size(); i++) {
                                RoadNode node = road.mNodes.get(i);
                                nodeMarker = new Marker(map);
                                nodeMarker.setPosition(node.mLocation);
                                nodeMarker.setIcon(nodeIcon);
                                nodeMarker.setTitle("قدم " + i);
                                //nodeMarker.setSnippet(node.mInstructions);
                                nodeMarker.setSubDescription(Road.getLengthDurationText(context, node.mLength, node.mDuration));
                                map.getOverlays().add(nodeMarker);
                            }

                            map.invalidate();

                            lytLoading.setVisibility(View.GONE);

                        }
                    }

                }
            }


        }

    }

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

    public void onResume() {
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.activity_back_enter);
    }
}

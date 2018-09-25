package com.example.tabrizguilds.tabrizguilds.services;

import android.content.SharedPreferences;
import android.util.Log;

import com.bumptech.glide.gifdecoder.GifHeaderParser;
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.db.DatabaseHelper;
import com.example.tabrizguilds.tabrizguilds.models.ActionModel;
import com.example.tabrizguilds.tabrizguilds.models.CommentModel;
import com.example.tabrizguilds.tabrizguilds.models.DriverModel;
import com.example.tabrizguilds.tabrizguilds.models.FacilityModel;
import com.example.tabrizguilds.tabrizguilds.models.HomePageModel;
import com.example.tabrizguilds.tabrizguilds.models.ImgModel;
import com.example.tabrizguilds.tabrizguilds.models.LogoModel;
import com.example.tabrizguilds.tabrizguilds.models.MenuModel;
import com.example.tabrizguilds.tabrizguilds.models.NewsModel;
import com.example.tabrizguilds.tabrizguilds.models.PhoneModel;
import com.example.tabrizguilds.tabrizguilds.models.PlacesModel;
import com.example.tabrizguilds.tabrizguilds.models.ReferendumModel;
import com.example.tabrizguilds.tabrizguilds.models.ReligiousTimesModel;
import com.example.tabrizguilds.tabrizguilds.models.SubCategoryModel;
import com.example.tabrizguilds.tabrizguilds.models.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by EHSAN on 12/1/2017.
 */

public class WebService {

    //    private String addr = "http://80.191.210.19:7862/api/";
//    private String addr = "http://gsharing.ir/api/";
    private String addr = "https://tabriz.touristsapp.com/api/";
//    String addr = "http://172.16.42.96/api/";


    private String connectToServer(String address, String RequestMethod) {
        try {
            URL url = new URL(address);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(RequestMethod);
            return inputStreamToString(urlConnection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String connectToServerByJson(String address, String requestMethod, String JsonDATA) {

        String JsonResponse = null;

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(address);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            // is output buffer writter
            urlConnection.setRequestMethod(requestMethod);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
//set headers and method
            Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
            writer.write(JsonDATA);
// json data
            writer.close();
            InputStream inputStream = urlConnection.getInputStream();
//input stream
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String inputLine;
            while ((inputLine = reader.readLine()) != null)
                buffer.append(inputLine);
            if (buffer.length() == 0) {
                // Stream was empty. No point in parsing.
                return null;
            }
            JsonResponse = buffer.toString();
//response data
            Log.i(TAG, JsonResponse);
            //send to post execute
            return JsonResponse;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }
        return null;

    }

    private String inputStreamToString(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String nextLine;
        try {
            while ((nextLine = reader.readLine()) != null) {
                stringBuilder.append(nextLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    public ReligiousTimesModel getReligiousTimesJolfa(boolean isInternetAvailable) {

        if (isInternetAvailable) {

            String response = connectToServer("https://prayer.aviny.com/api/prayertimes/6", "GET");
            Log.i("LOG", response + "");


            if (response != null) {
                ReligiousTimesModel religiousTimesModel = new ReligiousTimesModel();
                try {
                    JSONObject Object = new JSONObject(response);

                    religiousTimesModel.Imsaak = Object.getString("Imsaak");
                    religiousTimesModel.Maghreb = Object.getString("Maghreb");
                    religiousTimesModel.Midnight = Object.getString("Midnight");
                    religiousTimesModel.Noon = Object.getString("Noon");
                    religiousTimesModel.Sunrise = Object.getString("Sunrise");
                    religiousTimesModel.Sunset = Object.getString("Sunset");
                    religiousTimesModel.TimeZone = Object.getString("TimeZone");
                    religiousTimesModel.Today = Object.getString("Today");
                    religiousTimesModel.TodayQamari = Object.getString("TodayQamari");

                    return religiousTimesModel;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;
        } else
            return null;
    }

    public ReligiousTimesModel getReligiousTimesNordoz(boolean isInternetAvailable) {

        if (isInternetAvailable) {

//            String response = connectToServer("https://prayer.aviny.com/api/prayertimes/1218", "GET");
            String response = connectToServer("https://prayer.aviny.com/api/prayertimes/1003", "GET");
            Log.i("LOG", response + "");


            if (response != null) {
                ReligiousTimesModel religiousTimesModel = new ReligiousTimesModel();
                try {
                    JSONObject Object = new JSONObject(response);

                    religiousTimesModel.Imsaak = Object.getString("Imsaak");
                    religiousTimesModel.Maghreb = Object.getString("Maghreb");
                    religiousTimesModel.Midnight = Object.getString("Midnight");
                    religiousTimesModel.Noon = Object.getString("Noon");
                    religiousTimesModel.Sunrise = Object.getString("Sunrise");
                    religiousTimesModel.Sunset = Object.getString("Sunset");
                    religiousTimesModel.TimeZone = Object.getString("TimeZone");
                    religiousTimesModel.Today = Object.getString("Today");
                    religiousTimesModel.TodayQamari = Object.getString("TodayQamari");

                    return religiousTimesModel;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;
        } else
            return null;
    }

    public ReligiousTimesModel getReligiousTimesKhod(boolean isInternetAvailable) {

        if (isInternetAvailable) {

            String response = connectToServer("https://prayer.aviny.com/api/prayertimes/1066", "GET");
            Log.i("LOG", response + "");


            if (response != null) {
                ReligiousTimesModel religiousTimesModel = new ReligiousTimesModel();
                try {
                    JSONObject Object = new JSONObject(response);

                    religiousTimesModel.Imsaak = Object.getString("Imsaak");
                    religiousTimesModel.Maghreb = Object.getString("Maghreb");
                    religiousTimesModel.Midnight = Object.getString("Midnight");
                    religiousTimesModel.Noon = Object.getString("Noon");
                    religiousTimesModel.Sunrise = Object.getString("Sunrise");
                    religiousTimesModel.Sunset = Object.getString("Sunset");
                    religiousTimesModel.TimeZone = Object.getString("TimeZone");
                    religiousTimesModel.Today = Object.getString("Today");
                    religiousTimesModel.TodayQamari = Object.getString("TodayQamari");

                    return religiousTimesModel;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;
        } else
            return null;
    }

    public UserModel postLoginInfo(boolean isInternetAvailable, String userName, String password) {

        if (isInternetAvailable) {

            String response = connectToServer(addr + "login/login?username=" + userName + "&pass=" + password, "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                if (response.equals("[]")) {
                    UserModel userModel = new UserModel();
                    userModel.id = -1;
                    return userModel;
                } else {

                    try {

                        JSONArray Arrey = new JSONArray(response);
                        JSONObject Object = Arrey.getJSONObject(0);
                        UserModel userModel = new UserModel();
                        userModel.id = Object.getInt("id");
                        userModel.name = Object.getString("Name");
                        userModel.lName = Object.getString("LName");
                        userModel.mobile = Object.getString("Mobile");
                        userModel.email = Object.getString("Email");
//                        userModel.pass = Object.getString("Pass");

                        return userModel;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
            return null;
        } else
            return null;
    }

    public String postUserPhone(boolean isInternetAvailable, String phone) {

        if (isInternetAvailable) {

            String response = connectToServer(addr + "login/getPhone?num=" + phone, "GET");
            Log.i("LOG", response + "");


            return response;

        } else
            return null;
    }

    public String postRegisterInfo(boolean isInternetAvailable, String Name, String lName, String mobile, String email, String pass) {

        if (isInternetAvailable) {

            String req = "{\"Name\":\"" + Name + "\",\"LName\":\"" + lName + "\",\"Mobile\":\"" + mobile + "\",\"Email\":\"" + email + "\",\"Pass\":\"" + pass + "\",\"Visibility\":1,\"IsValid\":0,\"id\":0,\"AccsessId\":0,\"PCode\":\"\",\"Type\":4}";
//            String req = "{idRow:0,Name:\"" + name + "\",LName:\"" + lName + "\",Mobile:\"" + mobile + "\",Email:\"" + email + "\",Pass:\"" + pass + "\",Visibility:1,lastUpdate:1,imei:null,macAddress:null}";
            String response = connectToServerByJson(addr + "login/register", "POST", req);
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }

    public String editProfile(boolean isInternetAvailable, int id, String name, String lName, String mobile, String email, String pass) {

        if (isInternetAvailable) {

            String req = "{\"id\":" + id + ",\"Name\":\"" + name + "\",\"LName\":\"" + lName + "\",\"Mobile\":\"" + mobile + "\",\"Email\":\"" + email + "\",\"Pass\":\"" + pass + "\",\"Visibility\":1,\"lastUpdate\":1}";
            String response = connectToServerByJson(addr + "login/editProfile", "POST", req);
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }

    public String changePass(boolean isInternetAvailable, int idUser, String lastPass, String newPass) {

        if (isInternetAvailable) {

            String response = connectToServer(addr + "login/changePassword?iduser=" + idUser + "&lastPass=" + lastPass + "&newpass=" + newPass, "GET");
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }

    public String recoverPass(boolean isInternetAvailable, String email, int type) {

        if (isInternetAvailable) {

            String response = connectToServer(addr + "login/forgetPass?email=" + email + "&type=" + type, "GET");
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }

    public String freeNet(boolean isInternetAvailable, String email, String c1, String c2) {

        if (isInternetAvailable) {

            String response = connectToServer(addr + "login/freeNet?email=" + email + "&c1=" + c1 + "&c2=" + c2, "GET");
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }

    public int getPlaces(boolean isInternetAvailable) {

        if (isInternetAvailable) {
            DatabaseHelper helper = new DatabaseHelper(app.context);
            String maxUpdate = helper.getLastUpdate("Tbl_Places");
            Log.i("TAG", maxUpdate + "");

            String response = connectToServer(addr + "Asnaf?lastUpdate=" + maxUpdate, "GET");
            Log.i("TAG", response + "");

            if (response != null) {
                List<PlacesModel> placeslList = new ArrayList<>();
                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        PlacesModel placesModel = new PlacesModel();
                        placesModel.id = Object.getInt("id");
                        placesModel.RootCategory = Object.getInt("RootCategory");
                        placesModel.Category = Object.getInt("Categroy");
                        placesModel.AvailableDay = Object.getString("AvailableDay");
                        placesModel.StartTime = Object.getString("StartTime");
                        placesModel.EndTime = Object.getString("EndTime");
                        placesModel.Name = Object.getString("Name");
                        placesModel.Lat = Object.getDouble("Lat");
                        placesModel.Long = Object.getDouble("Long");
                        placesModel.Address = Object.getString("Address");
                        placesModel.Phone = Object.getString("Phone");
                        placesModel.Star = Object.getDouble("Star");
                        placesModel.StarCount = Object.getInt("StarCount");
                        placesModel.likeCount = Object.getInt("likeCount");
                        placesModel.Info = Object.getString("Info");
                        placesModel.webSite = Object.getString("webSite");
                        placesModel.Visibility = Object.getBoolean("Visibility");
                        placesModel.lastUpdate = Object.getString("lastUpdate");
                        placesModel.image = Object.getString("image");
                        if (!Object.getString("Cost").equals("null"))
                            placesModel.Cost = Object.getInt("Cost");
                        else
                            placesModel.Cost = 0;
                        if (!Object.getString("placeStar").equals("null"))
                            placesModel.placeStar = Object.getInt("placeStar");
                        else placesModel.placeStar = 0;

                        placeslList.add(placesModel);

                    }


                    if (placeslList.size() > 0) {

                        PlacesModel placesModel = new PlacesModel();

                        for (int i = 0; i < placeslList.size(); i++) {
                            placesModel = placeslList.get(i);
                            List<String> s = helper.selectPlacesById(placesModel.id + "");
                            //List<String> s1 = helper.selectCity("Name");
                            //Log.i("MAH", s1 + "");
                            if (s.isEmpty()) {
                                if (placesModel.Visibility)
                                    helper.insertNewPlace(placesModel);
                            } else {
                                if (placesModel.id == Integer.parseInt(s.get(0))) {
                                    List<String> v = helper.selectPlacesByLastUpdate(placesModel.id + "");
                                    if (!placesModel.Visibility)
                                        helper.deletePlaces(s.get(0));
                                    else if (placesModel.lastUpdate.compareTo(v.get(0)) > 0) {
                                        helper.updatePlaces(placesModel);
                                    }

                                }
                            }
                        }
                    }


                    return 1;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return -2;
        } else
            return -10;
    }

    public int getImages(boolean isInternetAvailable) {

        if (isInternetAvailable) {
            DatabaseHelper helper = new DatabaseHelper(app.context);
            String maxUpdate = helper.getLastUpdate("Tbl_Images");
            Log.i("TAG", maxUpdate + "");

            String response = connectToServer(addr + "images/selectImage?lastUpdate=" + maxUpdate, "GET");
            Log.i("TAG", response + "");

            if (response != null) {
                List<ImgModel> imgList = new ArrayList<>();
                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        ImgModel imgModel = new ImgModel();
                        imgModel.id = Object.getInt("id");
                        imgModel.Name = Object.getString("Name");
                        imgModel.lastUpdate = Object.getString("lastUpdate");
                        imgModel.idRow = Object.getInt("idRow");
                        imgModel.Visibility = Object.getBoolean("Visibility");

                        imgList.add(imgModel);

                    }


                    if (imgList.size() > 0) {

                        ImgModel imgModel = new ImgModel();

                        for (int i = 0; i < imgList.size(); i++) {
                            imgModel = imgList.get(i);
                            List<String> s = helper.selectImageId(imgModel.id + "");
                            if (s.isEmpty()) {
                                if (imgModel.Visibility)
                                    helper.insertNewImage(imgModel);
                            } else {
                                if (imgModel.id == Integer.parseInt(s.get(0))) {
                                    List<String> v = helper.selectImageByLastUpdate(imgModel.id + "");
                                    if (!imgModel.Visibility)
                                        helper.deleteImage(s.get(0));
                                    if (imgModel.lastUpdate.compareTo(v.get(0)) > 0) {
                                        helper.updateImage(imgModel);
                                    }

                                }
                            }
                        }
                    }


                    return 1;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return -2;
        } else
            return -10;
    }

    public List<ImgModel> selectUserSentImages(boolean isInternetAvailable, int idPlace) {

        if (isInternetAvailable) {

            String response = connectToServer(addr + "images/selectRImage?lastUpdate=0", "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<ImgModel> list = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        ImgModel model = new ImgModel();

                        model.id = Object.getInt("id");
                        model.idRow = Object.getInt("idRow");
                        model.idUser = Object.getInt("IdUser");
                        model.Type = Object.getInt("Type");
                        model.Name = Object.getString("Name");
                        model.lastUpdate = Object.getString("lastUpdate");

                        if (model.idRow == idPlace)
                            list.add(model);

                    }
                    return list;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }

//    public int getEvents(boolean isInternetAvailable) {
//
//        if (isInternetAvailable) {
//            DatabaseHelper helper = new DatabaseHelper(app.context);
//            String maxUpdate = helper.getLastUpdate("Tbl_Events");
//            Log.i("TAG", maxUpdate + "");
//
//            String response = connectToServer(addr + "events/select?lastUpdate=" + maxUpdate, "GET");
//            Log.i("TAG", response + "");
//
//            if (response != null) {
//                List<EventModel> eventList = new ArrayList<>();
//                try {
//
//                    JSONArray Arrey = new JSONArray(response);
//                    for (int i = 0; i < Arrey.length(); i++) {
//                        JSONObject Object = Arrey.getJSONObject(i);
//                        EventModel eventModel = new EventModel();
//                        eventModel.id = Object.getInt("id");
//                        eventModel.startDate = Object.getInt("startDate");
//                        eventModel.endDate = Object.getInt("endDate");
//                        eventModel.likeCount = Object.getInt("likeCount");
//                        eventModel.lat = Object.getDouble("Lat");
//                        eventModel.lon = Object.getDouble("Long");
//                        eventModel.Visibility = Object.getBoolean("Visibility");
//                        eventModel.Name = Object.getString("Title");
//                        eventModel.body = Object.getString("Body");
//                        eventModel.startTime = Object.getString("startTime");
//                        eventModel.endTime = Object.getString("endTime");
//                        eventModel.place = Object.getString("Place");
//                        eventModel.address = Object.getString("Address");
//                        eventModel.phone = Object.getString("Phone");
//                        eventModel.website = Object.getString("webSite");
//                        eventModel.lastUpdate = Object.getString("lastUpdate");
//                        eventModel.image = Object.getString("img");
//
//                        eventList.add(eventModel);
//
//                    }
//
//
//                    if (eventList.size() > 0) {
//
//                        EventModel eventModel = new EventModel();
//
//                        for (int i = 0; i < eventList.size(); i++) {
//                            eventModel = eventList.get(i);
//                            List<String> s = helper.selectEventId(eventModel.id + "");
//                            if (s.isEmpty()) {
//                                if (eventModel.Visibility)
//                                    helper.insertNewEvent(eventModel);
//                            } else {
//                                if (eventModel.id == Integer.parseInt(s.get(0))) {
//                                    List<String> v = helper.selectEventByLastUpdate(eventModel.id + "");
//                                    if (!eventModel.Visibility)
//                                        helper.deleteEvent(s.get(0));
//                                    if (eventModel.lastUpdate.compareTo(v.get(0)) > 0) {
//                                        helper.updateEvent(eventModel);
//                                    }
//
//                                }
//                            }
//                        }
//                    }
//
//
//                    return 1;
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            return -2;
//        } else
//            return -10;
//    }

    public int getHomePage(boolean isInternetAvailable) {

        if (isInternetAvailable) {
            DatabaseHelper helper = new DatabaseHelper(app.context);
            String maxUpdate = helper.getLastUpdate("Tbl_HomePage");
            Log.i("TAG", maxUpdate + "");

            String response = connectToServer(addr + "mainPage/select?lastUpdate=" + maxUpdate, "GET");
            Log.i("TAG", response + "");

            if (response != null) {
                List<HomePageModel> homeList = new ArrayList<>();
                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        HomePageModel homeModel = new HomePageModel();
                        homeModel.id = Object.getInt("id");
                        homeModel.title = Object.getString("title");
                        homeModel.des = Object.getString("des");
                        homeModel.image = Object.getString("image");
                        homeModel.visibility = Object.getBoolean("Visibility");
                        homeModel.lastUpdate = Object.getString("lastUpdate");

                        homeList.add(homeModel);

                    }


                    if (homeList.size() > 0) {

                        HomePageModel homeModel = new HomePageModel();

                        for (int i = 0; i < homeList.size(); i++) {
                            homeModel = homeList.get(i);
                            List<String> s = helper.selectHomePageById(homeModel.id + "");
                            //List<String> s1 = helper.selectCity("Name");
                            //Log.i("MAH", s1 + "");
                            if (s.isEmpty()) {
                                if (homeModel.visibility)
                                    helper.insertNewHomePage(homeModel);
                            } else {
                                if (homeModel.id == Integer.parseInt(s.get(0))) {
                                    List<String> v = helper.selectHomePageByLastUpdate(homeModel.id + "");
                                    if (!homeModel.visibility)
                                        helper.deleteHomePage(s.get(0));
                                    else if (homeModel.lastUpdate.compareTo(v.get(0)) > 0) {
                                        helper.updateHomePage(homeModel);
                                    }

                                }
                            }
                        }
                    }


                    return 1;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return -2;
        } else
            return -10;
    }

    public int getSubCategory(boolean isInternetAvailable) {

        if (isInternetAvailable) {
            DatabaseHelper helper = new DatabaseHelper(app.context);
            String maxUpdate = helper.getLastUpdate("Tbl_SubCategory");
            Log.i("TAG", maxUpdate + "");

            String response = connectToServer(addr + "Group?lastUpdate=" + maxUpdate, "GET");
            Log.i("TAG", response + "");

            if (response != null) {
                List<SubCategoryModel> homeList = new ArrayList<>();
                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        SubCategoryModel homeModel = new SubCategoryModel();
                        homeModel.SubCategoryId = Object.getInt("SubCategoryId");
                        homeModel.CategoryId = Object.getInt("CategoryId");
                        homeModel.SubCategoryName = Object.getString("SubCategoryName");
                        homeModel.ImageName = Object.getString("ImageName");
                        homeModel.isActive = Object.getBoolean("isActive");
                        homeModel.lastUpdate = Object.getString("lastUpdate");

                        homeList.add(homeModel);

                    }


                    if (homeList.size() > 0) {

                        SubCategoryModel homeModel = new SubCategoryModel();

                        for (int i = 0; i < homeList.size(); i++) {
                            homeModel = homeList.get(i);
                            List<String> s = helper.selectSubCategoryById(homeModel.SubCategoryId + "");
                            //List<String> s1 = helper.selectCity("Name");
                            //Log.i("MAH", s1 + "");
                            if (s.isEmpty()) {
                                if (homeModel.isActive)
                                    helper.insertNewSubCategory(homeModel);
                            } else {
                                if (homeModel.SubCategoryId == Integer.parseInt(s.get(0))) {
                                    List<String> v = helper.selectSubCategoryByLastUpdate(homeModel.SubCategoryId + "");
                                    if (!homeModel.isActive)
                                        helper.deleteSubCategory(s.get(0));
                                    else if (homeModel.lastUpdate.compareTo(v.get(0)) > 0) {
                                        helper.updateSubCategory(homeModel);
                                    }

                                }
                            }
                        }
                    }


                    return 1;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return -2;
        } else
            return -10;
    }

    public List<MenuModel> getMenu(boolean isInternetAvailable, int id) {

        if (isInternetAvailable) {

            String response = connectToServer(addr + "menu/select?idType=" + 1 + "&id=" + id, "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<MenuModel> menuList = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        MenuModel menuModel = new MenuModel();
//                        menuModel.id = Object.getInt("id");
                        menuModel.idRow = Object.getInt("idRow");
//                        menuModel.Type = Object.getInt("Type");
                        menuModel.Price = Object.getString("Price");
                        menuModel.Des = Object.getString("Des");
                        menuModel.Name = Object.getString("Name");

                        menuList.add(menuModel);

                    }
                    return menuList;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }

    public List<FacilityModel> getfacility(boolean isInternetAvailable, int id) {

        if (isInternetAvailable) {

            String response = connectToServer(addr + "facility/select?idType=" + 1 + "&id=" + id, "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<FacilityModel> facilityList = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        FacilityModel facilityModel = new FacilityModel();
//                        facilityModel.id = Object.getInt("id");
                        facilityModel.idRow = Object.getInt("idRow");
//                        facilityModel.Type = Object.getInt("Type");
                        facilityModel.Name = Object.getString("Name");

                        facilityList.add(facilityModel);

                    }
                    return facilityList;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }

    public String postSuggestion(boolean isInternetAvailable, String name, String email, String title, String body, String date) {

        if (isInternetAvailable) {

            String req = "{\"Name\":\"" + name + "\",\"email\":\"" + email + "\",\"title\":\"" + title + "\",\"body\":\"" + body + "\",\"date\":" + Integer.parseInt(date) + "}";
            String response = connectToServerByJson(addr + "suggestion/add", "POST", req);
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }

//    public List<EventModel> getEvents(boolean isInternetAvailable, String date) {
//
//        if (isInternetAvailable) {
//
//            String response = connectToServer(addr + "events/select?date=" + Integer.parseInt(date), "GET");
//            Log.i("LOG", response + "");
//
//            if (response != null) {
//
//                List<EventModel> eventList = new ArrayList<>();
//
//                try {
//
//                    JSONArray Arrey = new JSONArray(response);
//                    for (int i = 0; i < Arrey.length(); i++) {
//                        JSONObject Object = Arrey.getJSONObject(i);
//                        EventModel eventModel = new EventModel();
//                        eventModel.id = Object.getInt("id");
//                        eventModel.startDate = Object.getInt("startDate");
//                        eventModel.endDate = Object.getInt("endDate");
//                        eventModel.lat = Object.getDouble("Lat");
//                        eventModel.lon = Object.getDouble("Long");
//                        eventModel.Visibility = Object.getBoolean("Visibility");
//                        eventModel.Name = Object.getString("Title");
//                        eventModel.body = Object.getString("Body");
//                        eventModel.startTime = Object.getString("startTime");
//                        eventModel.endTime = Object.getString("endTime");
//                        eventModel.place = Object.getString("Place");
//                        eventModel.address = Object.getString("Address");
//                        eventModel.phone = Object.getString("Phone");
//
//
//                        eventList.add(eventModel);
//
//                    }
//                    return eventList;
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//            return null;
//
//        } else
//            return null;
//    }

    public String postFavorite(boolean isInternetAvailable, int idRow, int idUser, int type) {

        if (isInternetAvailable) {

            String req = "{\"idRow\":" + idRow + ",\"idUser\":" + idUser + ",\"Type\":\"" + type + "}";
            String response = connectToServerByJson(addr + "favorite/add", "POST", req);
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }

    public String deleteFavorite(boolean isInternetAvailable, int id) {

        if (isInternetAvailable) {

            String response = connectToServer(addr + "favorite/delete?id=" + id, "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                return response;

            }
            return null;
        } else
            return null;
    }

    public String postLike(boolean isInternetAvailable, int idLR, int idRow, int idUser, int like, double rate, int type) {

        if (isInternetAvailable) {


//            String req = "{id:" + idLR + ",idRow:" + idRow + ",idUser:" + idUser + ",Likes:" + like + ",Rate:" + rate + ",Type:" + type + "}";
            String req = "{\"id\":" + idLR + ",\"idRow\":" + idRow + ",\"idUser\":" + idUser + ",\"Likes\":" + like + ",\"Rate\":" + rate + ",\"Type\":" + type + "}";
            String response = connectToServerByJson(addr + "like/add", "POST", req);
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }

    public void getFavorites(boolean isInternetAvailable, int idUser) {

        if (isInternetAvailable) {

            DatabaseHelper helper = new DatabaseHelper(app.context);
            String response = connectToServer(addr + "favorite/select?idUser=" + idUser, "GET");
            Log.i("TAG", response + "");

            if (response != null) {
                List<ActionModel> actionList = new ArrayList<>();
                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        ActionModel actionModel = new ActionModel();
                        actionModel.id = Object.getInt("id");
                        actionModel.idRow = Object.getInt("idRow");
                        actionModel.type = Object.getInt("Type");
                        actionModel.idUser = Object.getInt("idUser");
//                        actionModel.likes = Object.getInt("likes");
//                        actionModel.rate = Object.getDouble("rate");

                        actionList.add(actionModel);

                    }


                    if (actionList.size() > 0) {

                        ActionModel actionModel = new ActionModel();

                        for (int i = 0; i < actionList.size(); i++) {

                            actionModel = actionList.get(i);

                            List<String> s = helper.selectAllById(actionModel.idRow + "");
                            if (!s.isEmpty())
                                helper.updateTblByFavorite(actionModel.idRow, actionModel.id);
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void getLikesAndRates(boolean isInternetAvailable, int idUser) {

        if (isInternetAvailable) {

            SharedPreferences prefs = app.context.getSharedPreferences("MYPREFS", 0);
            DatabaseHelper helper = new DatabaseHelper(app.context);
            String response = connectToServer(addr + "like/userLikes?idUser=" + idUser, "GET");
            Log.i("TAG", response + "");

            if (response != null) {
                List<ActionModel> actionList = new ArrayList<>();
                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        ActionModel actionModel = new ActionModel();
                        actionModel.id = Object.getInt("id");
                        actionModel.idRow = Object.getInt("idRow");
                        actionModel.type = Object.getInt("Type");
                        actionModel.idUser = Object.getInt("idUser");
                        actionModel.likes = Object.getInt("likes");
                        actionModel.rate = Object.getDouble("rate");

                        actionList.add(actionModel);

                    }


                    if (actionList.size() > 0) {

                        ActionModel actionModel = new ActionModel();

                        for (int i = 0; i < actionList.size(); i++) {

                            actionModel = actionList.get(i);

                            if (actionModel.type != 1) {

                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putBoolean("NewsLike" + actionModel.idRow, true);
                                editor.putInt("IdUserLike" + actionModel.idRow, actionModel.id);
                                editor.apply();

                            } else {
                                List<String> s = helper.selectAllById(actionModel.idRow + "");
                                if (!s.isEmpty())
                                    helper.updateTblByLikeAndRate(actionModel.idRow, actionModel.id, actionModel.rate, actionModel.likes);
                            }
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public List<CommentModel> getComments(boolean isInternetAvailable, int id, int idType) {

        if (isInternetAvailable) {

            String response = connectToServer(addr + "comment/select?type=" + idType + "&idRow=" + id, "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<CommentModel> commentList = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        CommentModel model = new CommentModel();
                        model.answers = new ArrayList<>();
                        model.id = Object.getInt("id");
                        model.likeCount = Object.getInt("likeCount");
                        model.date = Object.getInt("date");
                        model.name = Object.getString("name");
                        model.body = Object.getString("body");
                        String answers = Object.getString("answers");

                        JSONArray ArreyAnswer = new JSONArray(answers);
                        for (int j = 0; j < ArreyAnswer.length(); j++) {
                            JSONObject ObjectAnswer = ArreyAnswer.getJSONObject(j);
                            CommentModel modelAnswer = new CommentModel();
                            // modelAnswer.id = ObjectAnswer.getInt("id");
                            modelAnswer.name = ObjectAnswer.getString("Name");
                            modelAnswer.body = ObjectAnswer.getString("body");

                            model.answers.add(modelAnswer);
                        }


                        commentList.add(model);

                    }
                    return commentList;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }

    public String postComment(boolean isInternetAvailable, int idRow, int idUser, int idParent, int Type, int likeCount, String Body, int date) {

        if (isInternetAvailable) {


            String req = "{\"idRow\":" + idRow + ",\"idUser\":" + idUser + ",\"idParent\":" + idParent + ",\"Type\":" + Type + ",\"likeCount\":" + 0 + ",\"Body\":\"" + Body + "\",\"Date\":" + date + ", \"isActive\":0}";
            String response = connectToServerByJson(addr + "comment/add", "POST", req);
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }

    public String postCommentLikeDisLike(boolean isInternetAvailable, int id, boolean isliKe) {

        if (isInternetAvailable) {

            String response = connectToServer(addr + "comment/like?idComment=" + id + "&status=" + isliKe, "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                return response;

            }
            return null;
        } else
            return null;
    }


    public List<NewsModel> getNews(boolean isInternetAvailable, int count) {

        if (isInternetAvailable) {

            String response = connectToServer(addr + "news?num=" + count, "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<NewsModel> newsList = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        NewsModel model = new NewsModel();

                        model.id = Object.getInt("id");
                        model.likeCount = Object.getInt("likeCount");
                        model.Type = Object.getInt("Type");
                        model.commentCount = Object.getInt("commentCount");
                        model.Date = Object.getInt("Date");
                        model.Title = Object.getString("Title");
                        model.Body = Object.getString("Body");
                        model.Img = Object.getString("Img");
                        model.externalLink = Object.getString("externalLink");

                        newsList.add(model);

                    }
                    return newsList;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }


    public List<DriverModel> getDrivers(boolean isInternetAvailable, int idRow) {

        if (isInternetAvailable) {

            String response = connectToServer(addr + "taxi/select?id=" + idRow, "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<DriverModel> driversList = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        DriverModel model = new DriverModel();

                        model.id = Object.getInt("id");
                        model.idRow = Object.getInt("idRow");
                        model.isMarried = Object.getBoolean("isMarried");
                        model.LName = Object.getString("LName");
                        model.Name = Object.getString("Name");
                        model.Img = Object.getString("Img");
                        model.Mobile = Object.getString("Mobile");
                        model.natCode = Object.getString("natCode");
                        model.Plate = Object.getString("Plate");
                        model.Model = Object.getString("Model");
                        model.Color = Object.getString("Color");
                        model.Direction = Object.getString("Direction");

                        driversList.add(model);

                    }
                    return driversList;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }


    public List<ReferendumModel> getComptitions(boolean isInternetAvailable) {

        if (isInternetAvailable) {

            String response = connectToServer(addr + "referendum/select?date=" + app.getDate(), "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<ReferendumModel> referendumList = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        ReferendumModel model = new ReferendumModel();
                        model.options = new ArrayList<>();
                        model.id = Object.getInt("idPackage");
                        model.idQuestion = Object.getInt("id");
                        model.title = Object.getString("title");
                        model.question = Object.getString("question");
                        model.award = Object.getString("award");
                        String options = Object.getString("options");

                        JSONArray ArreyOptions = new JSONArray(options);
                        for (int j = 0; j < ArreyOptions.length(); j++) {
                            JSONObject ObjectOptions = ArreyOptions.getJSONObject(j);
                            String op1 = "", op2 = "", op3 = "", op4 = "";
                            op1 = ObjectOptions.getString("op1");
                            model.options.add(op1);
                            op2 = ObjectOptions.getString("op2");
                            model.options.add(op2);
                            op3 = ObjectOptions.getString("op3");
                            model.options.add(op3);
                            op4 = ObjectOptions.getString("op4");
                            model.options.add(op4);


                        }

                        referendumList.add(model);

                    }
                    return referendumList;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }

    public String postCompetitionAnswers(boolean isInternetAvailable, List<Integer> idQ, int idUser, List<Integer> userAnswers) {

        if (isInternetAvailable) {

            String req = "[";

            for (int i = 0; i < userAnswers.size(); i++) {
                req += "{\"idRef\":" + idQ.get(i) + ",\"idUser\":" + idUser + ",\"userAnswer\":" + userAnswers.get(i) + "}";
                if (i != userAnswers.size() - 1)
                    req += ",";
            }

            req += "]";

            String response = connectToServerByJson(addr + "referendum/answer", "POST", req);
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }

    public List<ReferendumModel> getReferendoms(boolean isInternetAvailable) {

        if (isInternetAvailable) {

            String response = connectToServer(addr + "referendum/referendum?date=" + app.getDate(), "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                List<ReferendumModel> referendumList = new ArrayList<>();

                try {

                    JSONArray Arrey = new JSONArray(response);
                    for (int i = 0; i < Arrey.length(); i++) {
                        JSONObject Object = Arrey.getJSONObject(i);
                        ReferendumModel model = new ReferendumModel();
                        model.options = new ArrayList<>();
                        model.id = Object.getInt("idPackage");
                        model.idQuestion = Object.getInt("id");
                        model.title = Object.getString("title");
                        model.question = Object.getString("question");
                        model.award = Object.getString("award");
                        model.res1 = Object.getInt("res1");
                        model.res2 = Object.getInt("res2");
                        model.res3 = Object.getInt("res3");
                        model.res4 = Object.getInt("res4");
                        String options = Object.getString("options");

                        JSONArray ArreyOptions = new JSONArray(options);
                        for (int j = 0; j < ArreyOptions.length(); j++) {
                            JSONObject ObjectOptions = ArreyOptions.getJSONObject(j);
                            String op1 = "", op2 = "", op3 = "", op4 = "";
                            op1 = ObjectOptions.getString("op1");
                            model.options.add(op1);
                            op2 = ObjectOptions.getString("op2");
                            model.options.add(op2);
                            op3 = ObjectOptions.getString("op3");
                            model.options.add(op3);
                            op4 = ObjectOptions.getString("op4");
                            model.options.add(op4);


                        }

                        referendumList.add(model);

                    }
                    return referendumList;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }


    public String postUserImages(boolean isInternetAvailable, int idRow, String Name, int idUser, long lastUpdate) {

        if (isInternetAvailable) {

            String req = "{\"idRow\":" + idRow + ",\"Name\":\"" + Name + "\",\"Visibility\":" + 0 + ",\"isAdmin\":" + 0 + ",\"idUser\":" + idUser + ",\"lastUpdate\":" + lastUpdate + "}";
            String response = connectToServerByJson(addr + "images/add", "POST", req);
            Log.i("LOG", response + "");

            return response;
        } else
            return null;
    }

    //android upload file to server
    public int uploadFile(boolean isInternetAvailable, String selectedFilePath, String fileName) {

        if (isInternetAvailable) {

            int serverResponseCode = 0;

            HttpURLConnection connection;
            DataOutputStream dataOutputStream;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";


            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            File selectedFile = new File(selectedFilePath);


            String[] parts = selectedFilePath.split("/");
            //final String fileName = parts[parts.length - 1];

            selectedFilePath = "";

            for (int i = 0; i < parts.length; i++) {
                if (i == parts.length - 1) {
                    selectedFilePath += fileName;
                } else {

                    selectedFilePath += parts[i];
                    selectedFilePath += "/";
                }
            }


            if (!selectedFile.isFile()) {
                //dialog.dismiss();

                // Toast.makeText(g, "فایل یافت نشد", Toast.LENGTH_LONG).show();

                return -1;
            } else {
                try {
                    FileInputStream fileInputStream = new FileInputStream(selectedFile);
                    URL url = new URL(addr + "images/uploadImage");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);//Allow Inputs
                    connection.setDoOutput(true);//Allow Outputs
                    connection.setUseCaches(false);//Don't use a cached Copy
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Connection", "Keep-Alive");
                    connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                    connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    connection.setRequestProperty("uploaded_file", selectedFilePath);

                    //creating new dataoutputstream
                    dataOutputStream = new DataOutputStream(connection.getOutputStream());

                    //writing bytes to data outputstream
                    dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                    dataOutputStream.writeBytes("Content-Disposition: form-data; Name=\"uploaded_file\";filename=\""
                            + selectedFilePath + "\"" + lineEnd);

                    dataOutputStream.writeBytes(lineEnd);

                    //returns no. of bytes present in fileInputStream
                    bytesAvailable = fileInputStream.available();
                    //selecting the buffer size as minimum of available bytes or 1 MB
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    //setting the buffer as byte array of size of bufferSize
                    buffer = new byte[bufferSize];

                    //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    //loop repeats till bytesRead = -1, i.e., no bytes are left to read
                    while (bytesRead > 0) {
                        //write the bytes read from inputstream
                        dataOutputStream.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    }

                    dataOutputStream.writeBytes(lineEnd);
                    dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                    serverResponseCode = connection.getResponseCode();
                    String serverResponseMessage = connection.getResponseMessage();

                    Log.i(GifHeaderParser.TAG, "Server Response is: " + serverResponseMessage + ": " + serverResponseCode);

                    //response code of 200 indicates the server status OK
//                if (serverResponseCode == 200) {
//                    return 1;
//                }

                    //closing the input and output streams
                    fileInputStream.close();
                    dataOutputStream.flush();
                    dataOutputStream.close();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return -2;

                    //Toast.makeText(getContext(), "مشکل در آپلود فایل", Toast.LENGTH_SHORT).show();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    //Toast.makeText(getContext(), "مشکل در اتصال برای آپلود", Toast.LENGTH_SHORT).show();
                    return -3;
                } catch (IOException e) {
                    e.printStackTrace();
                    return -4;
                    //Toast.makeText(getContext(), "Cannot Read/Write File!", Toast.LENGTH_SHORT).show();
                }

                return serverResponseCode;
            }

        }
        return -5;
    }

    public String deleteuserImgDetails(boolean isInternetAvailable, int id) {

        if (isInternetAvailable) {

            String response = connectToServer(addr + "images/delete?id=" + id, "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                return response;

            }
            return null;
        } else
            return null;
    }


    public LogoModel getLogo(boolean isInternetAvailable) {

        if (isInternetAvailable) {

            String response = connectToServer(addr + "login/logo", "GET");
            Log.i("LOG", response + "");

            if (response != null) {

                try {

                    JSONObject Object = new JSONObject(response);
                    LogoModel model = new LogoModel();

                    model.logo = Object.getString("logo");
                    model.text = Object.getString("text");


                    return model;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;

        } else
            return null;
    }

}

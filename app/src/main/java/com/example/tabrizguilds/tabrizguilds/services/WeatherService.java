package com.example.tabrizguilds.tabrizguilds.services;

import com.example.tabrizguilds.tabrizguilds.models.WeatherModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by EHSAN PC on 4/5/2017.
 */
public class WeatherService {

    private String connectToServer(String address, String requestMethod) {
        try {
            URL url = new URL(address);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(requestMethod);
            return inputStreamToString(httpURLConnection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
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

    public WeatherModel getWeatherJolfa() {
        String response = connectToServer("http://api.openweathermap.org/data/2.5/weather?lat=38.9275559&lon=45.6499703&APPID=b996dd71d70d2b9f4e72a87e5c3b9260&units=metric", "GET");
        if (response != null) {
            WeatherModel weatherModel = new WeatherModel();

            try {

                JSONObject json = new JSONObject(response);
                JSONObject details = json.getJSONArray("weather").getJSONObject(0);
                JSONObject main = json.getJSONObject("main");
                DateFormat df = DateFormat.getDateTimeInstance();
                weatherModel.city = json.getString("name").toUpperCase(Locale.US) + ", " + json.getJSONObject("sys").getString("country");
                weatherModel.description = details.getString("description").toUpperCase(Locale.US);
                weatherModel.temperature = String.format("%.2f", main.getDouble("temp"));
                int dot;
                if (weatherModel.temperature.contains("٫"))
                    dot = weatherModel.temperature.indexOf("٫");
                else
                    dot = weatherModel.temperature.indexOf(".");
                weatherModel.temperature = weatherModel.temperature.substring(0, dot) + "°";
                weatherModel.humidity = main.getString("humidity") + "%";
                weatherModel.pressure = main.getString("pressure") + " hPa";
                weatherModel.updatedOn = df.format(new Date(json.getLong("dt") * 1000));
                weatherModel.iconText = setWeatherIcon(details.getInt("id"),
                        json.getJSONObject("sys").getLong("sunrise") * 1000,
                        json.getJSONObject("sys").getLong("sunset") * 1000);


                return weatherModel;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public WeatherModel getWeatherNordoz() {
        String response = connectToServer("http://api.openweathermap.org/data/2.5/weather?lat=38.843511&lon=46.2027724&APPID=b996dd71d70d2b9f4e72a87e5c3b9260&units=metric", "GET");
        if (response != null) {
            WeatherModel weatherModel = new WeatherModel();

            try {

                JSONObject json = new JSONObject(response);
                JSONObject details = json.getJSONArray("weather").getJSONObject(0);
                JSONObject main = json.getJSONObject("main");
                DateFormat df = DateFormat.getDateTimeInstance();
                weatherModel.city = json.getString("name").toUpperCase(Locale.US) + ", " + json.getJSONObject("sys").getString("country");
                weatherModel.description = details.getString("description").toUpperCase(Locale.US);
                weatherModel.temperature = String.format("%.2f", main.getDouble("temp"));
                int dot;
                if (weatherModel.temperature.contains("٫"))
                    dot = weatherModel.temperature.indexOf("٫");
                else
                    dot = weatherModel.temperature.indexOf(".");
                weatherModel.temperature = weatherModel.temperature.substring(0, dot) + "°";
                weatherModel.humidity = main.getString("humidity") + "%";
                weatherModel.pressure = main.getString("pressure") + " hPa";
                weatherModel.updatedOn = df.format(new Date(json.getLong("dt") * 1000));
                weatherModel.iconText = setWeatherIcon(details.getInt("id"),
                        json.getJSONObject("sys").getLong("sunrise") * 1000,
                        json.getJSONObject("sys").getLong("sunset") * 1000);


                return weatherModel;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public WeatherModel getWeatherKhod() {
        String response = connectToServer("http://api.openweathermap.org/data/2.5/weather?lat=39.1375218&lon=46.9523732&APPID=b996dd71d70d2b9f4e72a87e5c3b9260&units=metric", "GET");
        if (response != null) {
            WeatherModel weatherModel = new WeatherModel();

            try {

                JSONObject json = new JSONObject(response);
                JSONObject details = json.getJSONArray("weather").getJSONObject(0);
                JSONObject main = json.getJSONObject("main");
                DateFormat df = DateFormat.getDateTimeInstance();
                weatherModel.city = json.getString("name").toUpperCase(Locale.US) + ", " + json.getJSONObject("sys").getString("country");
                weatherModel.description = details.getString("description").toUpperCase(Locale.US);
                weatherModel.temperature = String.format("%.2f", main.getDouble("temp"));
                int dot;
                if (weatherModel.temperature.contains("٫"))
                    dot = weatherModel.temperature.indexOf("٫");
                else
                    dot = weatherModel.temperature.indexOf(".");
                weatherModel.temperature = weatherModel.temperature.substring(0, dot) + "°";
                weatherModel.humidity = main.getString("humidity") + "%";
                weatherModel.pressure = main.getString("pressure") + " hPa";
                weatherModel.updatedOn = df.format(new Date(json.getLong("dt") * 1000));
                weatherModel.iconText = setWeatherIcon(details.getInt("id"),
                        json.getJSONObject("sys").getLong("sunrise") * 1000,
                        json.getJSONObject("sys").getLong("sunset") * 1000);


                return weatherModel;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String setWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        String icon = "";
        if (actualId == 800) {
            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime < sunset) {
                icon = "&#xf00d;";
            } else {
                icon = "&#xf02e;";
            }
        } else {
            switch (id) {
                case 2:
                    icon = "&#xf01e;";
                    break;
                case 3:
                    icon = "&#xf01c;";
                    break;
                case 7:
                    icon = "&#xf014;";
                    break;
                case 8:
                    icon = "&#xf013;";
                    break;
                case 6:
                    icon = "&#xf01b;";
                    break;
                case 5:
                    icon = "&#xf019;";
                    break;
            }
        }
        return icon;
    }


}

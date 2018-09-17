package com.example.tabrizguilds.tabrizguilds.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tabrizguilds.tabrizguilds.models.HomePageModel;
import com.example.tabrizguilds.tabrizguilds.models.ImgModel;
import com.example.tabrizguilds.tabrizguilds.models.MapModel;
import com.example.tabrizguilds.tabrizguilds.models.PhoneModel;
import com.example.tabrizguilds.tabrizguilds.models.PlacesModel;
import com.example.tabrizguilds.tabrizguilds.models.SubCategoryModel;
import com.example.tabrizguilds.tabrizguilds.models.UserActivityModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EHSAN on 11/29/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context) {
        super(context, IOHelper.DATA_DIRECTORY + IOHelper.DATABASE_FILE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public String getLastUpdate(String tableName) {

        String lastUpdate;
        SQLiteDatabase ArasDB = getReadableDatabase();
        Cursor cursor = ArasDB.rawQuery("SELECT MAX(lastUpdate) FROM "
                + tableName, null);
        cursor.moveToFirst();
        lastUpdate = cursor.getString(0);
        cursor.close();
        if (lastUpdate == null)
            return "0";
        else
            return lastUpdate;
    }

    public List<PlacesModel> selectAllPlacesToMap() {

        List<PlacesModel> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        //String order = "orderb";
        String sql = "SELECT * FROM Tbl_Places";
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                PlacesModel pm = new PlacesModel();
                pm.id = cursor.getInt(cursor.getColumnIndex("id"));
                pm.RootCategory = cursor.getInt(cursor.getColumnIndex("RootCategory"));
                pm.Category = cursor.getInt(cursor.getColumnIndex("Categroy"));
                pm.Name = cursor.getString(cursor.getColumnIndex("Name"));
                pm.Address = cursor.getString(cursor.getColumnIndex("Address"));
                pm.image = cursor.getString(cursor.getColumnIndex("image"));
                pm.Lat = cursor.getDouble(cursor.getColumnIndex("Lat"));
                pm.Long = cursor.getDouble(cursor.getColumnIndex("Long"));
                pm.Star = cursor.getDouble(cursor.getColumnIndex("Star"));


                list.add(pm);
            } while (cursor.moveToNext());

        }
        cursor.close();
        ArasDB.close();
        return list;
    }

    public List<PlacesModel> selectPlacesToList(int rootCategory, int subCategory) {

        List<PlacesModel> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        //String order = "orderb";
        String sql = "SELECT * FROM Tbl_Places WHERE RootCategory=" + rootCategory + " AND Categroy=" + subCategory;
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                PlacesModel pm = new PlacesModel();
                pm.id = cursor.getInt(cursor.getColumnIndex("id"));
                pm.Category = cursor.getInt(cursor.getColumnIndex("Categroy"));
                pm.RootCategory = cursor.getInt(cursor.getColumnIndex("RootCategory"));
                pm.Name = cursor.getString(cursor.getColumnIndex("Name"));
                pm.Address = cursor.getString(cursor.getColumnIndex("Address"));
                pm.image = cursor.getString(cursor.getColumnIndex("image"));
                pm.Star = cursor.getFloat(cursor.getColumnIndex("Star"));

                list.add(pm);
            } while (cursor.moveToNext());

        }
        cursor.close();
        ArasDB.close();
        return list;
    }

//    public List<EventModel> selectAllEventsToList(String tblName) {
//
//        List<EventModel> list = new ArrayList<>();
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        //String order = "orderb";
//        String sql = "SELECT * FROM " + tblName;
//        Cursor cursor = ArasDB.rawQuery(sql, null);
//        cursor.moveToFirst();
//        if (!cursor.isAfterLast()) {
//            do {
//                EventModel pm = new EventModel();
//                pm.id = cursor.getInt(cursor.getColumnIndex("id"));
//                pm.startDate = cursor.getInt(cursor.getColumnIndex("startDate"));
//                pm.Name = cursor.getString(cursor.getColumnIndex("Name"));
//                pm.address = cursor.getString(cursor.getColumnIndex("address"));
//                pm.image = cursor.getString(cursor.getColumnIndex("image"));
//                //pm.imgPersonal = cursor.getString(cursor.getColumnIndex("imgPersonal"));
//
//
//                list.add(pm);
//            } while (cursor.moveToNext());
//
//        }
//        cursor.close();
//        ArasDB.close();
//        return list;
//    }

    public PlacesModel selectPlacesDetail(int id) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_Places WHERE id=" + id;
        PlacesModel pm = new PlacesModel();
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {

                pm.id = cursor.getInt(cursor.getColumnIndex("id"));
                pm.Name = cursor.getString(cursor.getColumnIndex("Name"));
                pm.Category = cursor.getInt(cursor.getColumnIndex("Categroy"));
                pm.Lat = cursor.getDouble(cursor.getColumnIndex("Lat"));
                pm.Long = cursor.getDouble(cursor.getColumnIndex("Long"));
                pm.Address = cursor.getString(cursor.getColumnIndex("Address"));
                pm.Phone = cursor.getString(cursor.getColumnIndex("Phone"));
                pm.Star = cursor.getDouble(cursor.getColumnIndex("Star"));
                pm.StarCount = cursor.getInt(cursor.getColumnIndex("StarCount"));
                pm.likeCount = cursor.getInt(cursor.getColumnIndex("likeCount"));
                pm.Info = cursor.getString(cursor.getColumnIndex("Info"));
                pm.webSite = cursor.getString(cursor.getColumnIndex("webSite"));
                pm.StartTime = cursor.getString(cursor.getColumnIndex("StartTime"));
                pm.EndTime = cursor.getString(cursor.getColumnIndex("EndTime"));
                pm.Cost = cursor.getInt(cursor.getColumnIndex("Cost"));
                pm.placeStar = cursor.getInt(cursor.getColumnIndex("placeStar"));


            } while (cursor.moveToNext());

        }
        cursor.close();
        ArasDB.close();
        return pm;
    }

//    public EventModel selectEventsDetail(String tblName, int id) {
//
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql = "SELECT * FROM " + tblName + " WHERE id=" + id;
//        EventModel pm = new EventModel();
//        Cursor cursor = ArasDB.rawQuery(sql, null);
//        cursor.moveToFirst();
//        if (!cursor.isAfterLast()) {
//            do {
//
//                pm.id = cursor.getInt(cursor.getColumnIndex("id"));
//                pm.body = cursor.getString(cursor.getColumnIndex("body"));
//                pm.Name = cursor.getString(cursor.getColumnIndex("Name"));
//                pm.startTime = cursor.getString(cursor.getColumnIndex("startTime"));
//                pm.startDate = cursor.getInt(cursor.getColumnIndex("startDate"));
//                pm.endDate = cursor.getInt(cursor.getColumnIndex("endDate"));
//                pm.likeCount = cursor.getInt(cursor.getColumnIndex("likeCount"));
//                pm.endTime = cursor.getString(cursor.getColumnIndex("endTime"));
//                pm.place = cursor.getString(cursor.getColumnIndex("place"));
//                pm.lat = cursor.getDouble(cursor.getColumnIndex("lat"));
//                pm.lon = cursor.getDouble(cursor.getColumnIndex("lon"));
//                pm.address = cursor.getString(cursor.getColumnIndex("address"));
//                pm.website = cursor.getString(cursor.getColumnIndex("webSite"));
//                pm.phone = cursor.getString(cursor.getColumnIndex("phone"));
//                pm.image = cursor.getString(cursor.getColumnIndex("image"));
//
////                pm.Visibility = cursor.getInt(cursor.getColumnIndex("Visibility"));
////                pm.lastUpdate = cursor.getInt(cursor.getColumnIndex("lastUpdate"));
//
//
//            } while (cursor.moveToNext());
//
//        }
//        cursor.close();
//        ArasDB.close();
//        return pm;
//    }
//
//    public PlacesModel selectOfficesDetail(String tblName, int id) {
//
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql = "SELECT * FROM " + tblName + " WHERE id=" + id;
//        PlacesModel pm = new PlacesModel();
//        Cursor cursor = ArasDB.rawQuery(sql, null);
//        cursor.moveToFirst();
//        if (!cursor.isAfterLast()) {
//            do {
//
//                pm.id = cursor.getInt(cursor.getColumnIndex("id"));
//                pm.Name = cursor.getString(cursor.getColumnIndex("Name"));
//                pm.type = cursor.getInt(cursor.getColumnIndex("type"));
//                pm.lat = cursor.getDouble(cursor.getColumnIndex("lat"));
//                pm.lon = cursor.getDouble(cursor.getColumnIndex("lon"));
//                pm.address = cursor.getString(cursor.getColumnIndex("address"));
//                pm.tel = cursor.getString(cursor.getColumnIndex("tel"));
//                pm.info = cursor.getString(cursor.getColumnIndex("Info"));
//                pm.website = cursor.getString(cursor.getColumnIndex("webSite"));
//
//            } while (cursor.moveToNext());
//
//        }
//        cursor.close();
//        ArasDB.close();
//        return pm;
//    }
//
//    public PlacesModel selectOrgDetail(String tblName, int type) {
//
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql = "SELECT * FROM " + tblName + " WHERE type=" + type;
//        PlacesModel pm = new PlacesModel();
//        Cursor cursor = ArasDB.rawQuery(sql, null);
//        cursor.moveToFirst();
//        if (!cursor.isAfterLast()) {
//            do {
//
//                pm.id = cursor.getInt(cursor.getColumnIndex("id"));
//                pm.Name = cursor.getString(cursor.getColumnIndex("Name"));
//                pm.type = cursor.getInt(cursor.getColumnIndex("type"));
//                pm.lat = cursor.getDouble(cursor.getColumnIndex("lat"));
//                pm.lon = cursor.getDouble(cursor.getColumnIndex("lon"));
//                pm.address = cursor.getString(cursor.getColumnIndex("address"));
//                pm.phone = cursor.getString(cursor.getColumnIndex("tel"));
//                pm.info = cursor.getString(cursor.getColumnIndex("Info"));
//                pm.website = cursor.getString(cursor.getColumnIndex("webSite"));
////                pm.Visibility = cursor.getInt(cursor.getColumnIndex("Visibility"));
////                pm.lastUpdate = cursor.getInt(cursor.getColumnIndex("lastUpdate"));
//
//
//            } while (cursor.moveToNext());
//
//        }
//        cursor.close();
//        ArasDB.close();
//        return pm;
//    }


    public double selectRateValueById(String tblName, int r) {
        String userLike = "";
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM " + tblName + " WHERE id=" + r;
        String request = "userRate";
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {

            userLike = cursor.getString(cursor.getColumnIndex(request));

        }
        cursor.close();
        ArasDB.close();

        if (userLike != null) {
            if (Double.parseDouble(userLike) > 0)
                return Double.parseDouble(userLike);
            else
                return -1;
        } else
            return -1;

    }

    public int selectRateById(String tblName, int r) {
        String userLike = "";
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM " + tblName + " WHERE id=" + r;
        String request = "idUserRate";
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {

            userLike = cursor.getString(cursor.getColumnIndex(request));

        }
        cursor.close();
        ArasDB.close();
        if (userLike != null) {
            if (Integer.parseInt(userLike) > 0)
                return Integer.parseInt(userLike);
            else
                return -1;
        } else
            return -1;

    }

    public int selectLikeById(String tblName, int r) {
        String userLike = "";
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM " + tblName + " WHERE id=" + r;
        String request = "userLike";
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {

            userLike = cursor.getString(cursor.getColumnIndex(request));
        }
        cursor.close();
        ArasDB.close();
        if (userLike != null) {
            if (Integer.parseInt(userLike) > 0)
                return Integer.parseInt(userLike);
            else
                return -1;
        } else return -1;

    }

    public void updateTblByLike(int idRow, int idLike, int likeCount) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql;
        sql = "UPDATE Tbl_Places SET userLike=" + idLike + ",likeCount=" + likeCount + " WHERE id=" + idRow;
        ArasDB.execSQL(sql);
        ArasDB.close();
    }

    public void updateTblByRate(int idRow, int idRate, double rateValue) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql;
        sql = "UPDATE Tbl_Places SET userRate=" + rateValue + ",idUserRate=" + idRate + " WHERE id=" + idRow;
        ArasDB.execSQL(sql);
        ArasDB.close();
    }

    public UserActivityModel selectUserActivityByPlaceId(int r) {
        String userFavorite = "";
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_Places WHERE id=" + r;
//        String request = "userFavorite";
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();

        UserActivityModel model = new UserActivityModel();

        if (!cursor.isAfterLast()) {

            model.idUserFavorite = cursor.getInt(cursor.getColumnIndex("idUserFavorite"));
            model.idUserLike = cursor.getInt(cursor.getColumnIndex("idUserLike"));
            model.idUserRate = cursor.getInt(cursor.getColumnIndex("idUserRate"));
            model.userRate = cursor.getInt(cursor.getColumnIndex("userRate"));

        }
        cursor.close();
        ArasDB.close();
        return model;

    }

    public void updateTblByFavorite(int idRow, int idFavorite) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql;
        sql = "UPDATE Tbl_Places SET idUserFavorite=" + idFavorite + " WHERE id=" + idRow;
        ArasDB.execSQL(sql);
        ArasDB.close();
    }

    public void updateTblByLikeAndRate(int idRow, int idLR, double rate, int like) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql;
        sql = "UPDATE Tbl_Places SET idUserLike=" + ((like == 0) ? 0 : idLR) + ",idUserRate=" + ((rate == -1) ? 0 : idLR) + ",userRate=" + rate + " WHERE id=" + idRow;
        ArasDB.execSQL(sql);
        ArasDB.close();
    }

    public void updateTblAfterExit() {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql;
        sql = "UPDATE Tbl_Places SET idUserFavorite=-1,idUserLike=-1,userRate=-1,idUserRate=-1";
        ArasDB.execSQL(sql);
        ArasDB.close();
    }


    public List<String> selectAllById(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_Places WHERE id=" + r;
        String request = "id";
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                String s = cursor.getString(cursor.getColumnIndex(request));
                list.add(s);
            } while (cursor.moveToNext());

        }
        cursor.close();
        ArasDB.close();
        return list;
    }


    public List<String> selectPlacesById(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_Places WHERE id=" + r;
        String request = "id";
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                String s = cursor.getString(cursor.getColumnIndex(request));
                list.add(s);
            } while (cursor.moveToNext());

        }
        cursor.close();
        ArasDB.close();
        return list;
    }

    public void insertNewPlace(PlacesModel placesModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "";
        sql = "INSERT INTO Tbl_Places (id,RootCategory,Categroy,AvailableDay,StartTime,EndTime,Name,Lat,Long,Address,Phone,Star,StarCount,likeCount,Info,webSite,Visibility,lastUpdate,image,Cost,placeStar) VALUES('"
                + placesModel.id + "','" + placesModel.RootCategory + "','" + placesModel.Category + "','" + placesModel.AvailableDay + "','" + placesModel.StartTime + "','" + placesModel.EndTime + "','" + placesModel.Name + "','" + placesModel.Lat + "','" + placesModel.Long + "','" + placesModel.Address + "','" + placesModel.Phone + "','" + placesModel.Star + "','" + placesModel.StarCount + "','" + placesModel.likeCount + "','" + placesModel.Info + "','" + placesModel.webSite + "','" + placesModel.Visibility + "','" + placesModel.lastUpdate + "','" + placesModel.image + "','" + placesModel.Cost + "','" + placesModel.placeStar + "')";
        ArasDB.execSQL(sql);

        ArasDB.close();
    }

    public List<String> selectPlacesByLastUpdate(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_Places WHERE id=" + r;
        String request = "lastUpdate";
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                String s = cursor.getString(cursor.getColumnIndex(request));
                list.add(s);
            } while (cursor.moveToNext());

        }
        cursor.close();
        ArasDB.close();
        return list;
    }

    public void updatePlaces(PlacesModel placesModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql;
        sql = "UPDATE Tbl_Places SET RootCategory=" + placesModel.RootCategory + ",Categroy=" + placesModel.Category + ",AvailableDay='" + placesModel.AvailableDay + "',StartTime='" + placesModel.StartTime + "',EndTime='" + placesModel.EndTime + "',Name='" + placesModel.Name + "',Lat=" + placesModel.Lat + ",Long=" + placesModel.Long + ",Address='" + placesModel.Address + "',Phone=" + placesModel.Phone + ",Star=" + placesModel.Star + ",StarCount=" + placesModel.StarCount + ",likeCount='" + placesModel.likeCount + "',Info='" + placesModel.Info + "',Visibility=" + ((placesModel.Visibility) ? 1 : 0) + ",webSite='" + placesModel.webSite + "',lastUpdate='" + placesModel.lastUpdate + "',image='" + placesModel.image + "',Cost=" + placesModel.Cost + ",placeStar=" + placesModel.placeStar + " WHERE id=" + placesModel.id;
        ArasDB.execSQL(sql);
        ArasDB.close();
    }

    public void deletePlaces(String id) {

        //Log.i("LOG", "delete city:" + id);
        SQLiteDatabase ArasDB = getWritableDatabase();
        String sql = "DELETE FROM Tbl_Places WHERE id=" + id + "";
        ArasDB.execSQL(sql);
        ArasDB.close();

    }


//    public List<String> selectOfficeById(String r) {
//        List<String> list = new ArrayList<>();
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql = "SELECT * FROM Tbl_Offices WHERE id=" + r;
//        String request = "id";
//        Cursor cursor = ArasDB.rawQuery(sql, null);
//        cursor.moveToFirst();
//        if (!cursor.isAfterLast()) {
//            do {
//                String s = cursor.getString(cursor.getColumnIndex(request));
//                list.add(s);
//            } while (cursor.moveToNext());
//
//        }
//        cursor.close();
//        ArasDB.close();
//        return list;
//    }
//
//    public void insertNewOffice(PlacesModel placesModel) {
//
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql = "";
//        sql = "INSERT INTO Tbl_Offices (id,type,Name,lat,lon,info,website,Visibility,lastUpdate,address,tel,image,mainType) VALUES('"
//                + placesModel.id + "','" + placesModel.type + "','" + placesModel.Name + "','" + placesModel.lat + "','" + placesModel.lon + "','" + placesModel.info + "','" + placesModel.website + "','" + placesModel.Visibility + "','" + placesModel.lastUpdate + "','" + placesModel.address + "','" + placesModel.tel + "','" + placesModel.image + "','" + 8 + "')";
//        ArasDB.execSQL(sql);
//
//        ArasDB.close();
//    }
//
//    public List<String> selectOfficeByLastUpdate(String r) {
//        List<String> list = new ArrayList<>();
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql = "SELECT * FROM Tbl_Offices WHERE id=" + r;
//        String request = "lastUpdate";
//        Cursor cursor = ArasDB.rawQuery(sql, null);
//        cursor.moveToFirst();
//        if (!cursor.isAfterLast()) {
//            do {
//                String s = cursor.getString(cursor.getColumnIndex(request));
//                list.add(s);
//            } while (cursor.moveToNext());
//
//        }
//        cursor.close();
//        ArasDB.close();
//        return list;
//    }
//
//    public void deleteOffice(String id) {
//
//        //Log.i("LOG", "delete city:" + id);
//        SQLiteDatabase ArasDB = getWritableDatabase();
//        String sql = "DELETE FROM Tbl_Offices WHERE id=" + id + "";
//        ArasDB.execSQL(sql);
//        ArasDB.close();
//
//    }
//
//    public void updateOffice(PlacesModel placesModel) {
//
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql;
//        sql = "UPDATE Tbl_Offices SET type=" + placesModel.type + ",Name='" + placesModel.Name + "',lat=" + placesModel.lat + ",lon=" + placesModel.lon + ",Info='" + placesModel.info + "',webSite='" + placesModel.website + "',Visibility=" + ((placesModel.Visibility) ? 1 : 0) + ",lastUpdate='" + placesModel.lastUpdate + "',address='" + placesModel.address + "',tel='" + placesModel.tel + "',image='" + placesModel.image + "' WHERE id=" + placesModel.id;
//        ArasDB.execSQL(sql);
//        ArasDB.close();
//    }
//
//
//    public List<String> selectEatingById(String r) {
//        List<String> list = new ArrayList<>();
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql = "SELECT * FROM Tbl_Eating WHERE id=" + r;
//        String request = "id";
//        Cursor cursor = ArasDB.rawQuery(sql, null);
//        cursor.moveToFirst();
//        if (!cursor.isAfterLast()) {
//            do {
//                String s = cursor.getString(cursor.getColumnIndex(request));
//                list.add(s);
//            } while (cursor.moveToNext());
//
//        }
//        cursor.close();
//        ArasDB.close();
//        return list;
//    }
//
//    public void insertNewEating(PlacesModel placesModel) {
//
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql = "";
//        sql = "INSERT INTO Tbl_Eating (id,type,idStartDay,idEndDay,startTime,endTime,Name,lat,lon,phone,star,starCount,likeCount,info,website,Visibility,lastUpdate,address,image,mainType) VALUES('"
//                + placesModel.id + "','" + placesModel.type + "','" + placesModel.idStartDay + "','" + placesModel.idEndDay + "','" + placesModel.startTime + "','" + placesModel.endTime + "','" + placesModel.Name + "','" + placesModel.lat + "','" + placesModel.lon + "','" + placesModel.phone + "','" + placesModel.star + "','" + placesModel.starCount + "','" + placesModel.likeCount + "','" + placesModel.info + "','" + placesModel.website + "','" + placesModel.Visibility + "','" + placesModel.lastUpdate + "','" + placesModel.address + "','" + placesModel.image + "','" + 1 + "')";
//        ArasDB.execSQL(sql);
//
//        ArasDB.close();
//    }
//
//    public List<String> selectEatingByLastUpdate(String r) {
//        List<String> list = new ArrayList<>();
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql = "SELECT * FROM Tbl_Eating WHERE id=" + r;
//        String request = "lastUpdate";
//        Cursor cursor = ArasDB.rawQuery(sql, null);
//        cursor.moveToFirst();
//        if (!cursor.isAfterLast()) {
//            do {
//                String s = cursor.getString(cursor.getColumnIndex(request));
//                list.add(s);
//            } while (cursor.moveToNext());
//
//        }
//        cursor.close();
//        ArasDB.close();
//        return list;
//    }
//
//    public void updateEating(PlacesModel placesModel) {
//
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql;
//        sql = "UPDATE Tbl_Eating SET type=" + placesModel.type + ",idStartDay=" + placesModel.idStartDay + ",idEndDay=" + placesModel.idEndDay + ",startTime='" + placesModel.startTime + "',endTime='" + placesModel.endTime + "',Name='" + placesModel.Name + "',lat=" + placesModel.lat + ",lon=" + placesModel.lon + ",phone='" + placesModel.phone + "',star=" + placesModel.star + ",starCount=" + placesModel.starCount + ",likeCount=" + placesModel.likeCount + ",Info='" + placesModel.info + "',webSite='" + placesModel.website + "',Visibility=" + ((placesModel.Visibility) ? 1 : 0) + ",lastUpdate='" + placesModel.lastUpdate + "',address='" + placesModel.address + "',image='" + placesModel.image + "' WHERE id=" + placesModel.id;
//        ArasDB.execSQL(sql);
//        ArasDB.close();
//    }
//
//    public void deleteEating(String id) {
//
//        //Log.i("LOG", "delete city:" + id);
//        SQLiteDatabase ArasDB = getWritableDatabase();
//        String sql = "DELETE FROM Tbl_Eating WHERE id=" + id + "";
//        ArasDB.execSQL(sql);
//        ArasDB.close();
//
//    }
//
//
//    public List<String> selectMedicalById(String r) {
//        List<String> list = new ArrayList<>();
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql = "SELECT * FROM Tbl_Medicals WHERE id=" + r;
//        String request = "id";
//        Cursor cursor = ArasDB.rawQuery(sql, null);
//        cursor.moveToFirst();
//        if (!cursor.isAfterLast()) {
//            do {
//                String s = cursor.getString(cursor.getColumnIndex(request));
//                list.add(s);
//            } while (cursor.moveToNext());
//
//        }
//        cursor.close();
//        ArasDB.close();
//        return list;
//    }
//
//    public void insertNewMedical(PlacesModel placesModel) {
//
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql = "";
//        sql = "INSERT INTO Tbl_Medicals (id,type,idStartDay,idEndDay,startTime,endTime,Name,lat,lon,phone,star,starCount,likeCount,info,website,Visibility,lastUpdate,address,image,mainType) VALUES('"
//                + placesModel.id + "','" + placesModel.type + "','" + placesModel.idStartDay + "','" + placesModel.idEndDay + "','" + placesModel.startTime + "','" + placesModel.endTime + "','" + placesModel.Name + "','" + placesModel.lat + "','" + placesModel.lon + "','" + placesModel.phone + "','" + placesModel.star + "','" + placesModel.starCount + "','" + placesModel.likeCount + "','" + placesModel.info + "','" + placesModel.website + "','" + placesModel.Visibility + "','" + placesModel.lastUpdate + "','" + placesModel.address + "','" + placesModel.image + "','" + 9 + "')";
//        ArasDB.execSQL(sql);
//
//        ArasDB.close();
//    }
//
//    public List<String> selectMedicalByLastUpdate(String r) {
//        List<String> list = new ArrayList<>();
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql = "SELECT * FROM Tbl_Medicals WHERE id=" + r;
//        String request = "lastUpdate";
//        Cursor cursor = ArasDB.rawQuery(sql, null);
//        cursor.moveToFirst();
//        if (!cursor.isAfterLast()) {
//            do {
//                String s = cursor.getString(cursor.getColumnIndex(request));
//                list.add(s);
//            } while (cursor.moveToNext());
//
//        }
//        cursor.close();
//        ArasDB.close();
//        return list;
//    }
//
//    public void updateMedical(PlacesModel placesModel) {
//
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql;
//        sql = "UPDATE Tbl_Medicals SET type=" + placesModel.type + ",idStartDay=" + placesModel.idStartDay + ",idEndDay=" + placesModel.idEndDay + ",startTime='" + placesModel.startTime + "',endTime='" + placesModel.endTime + "',Name='" + placesModel.Name + "',lat=" + placesModel.lat + ",lon=" + placesModel.lon + ",phone='" + placesModel.phone + "',star=" + placesModel.star + ",starCount=" + placesModel.starCount + ",likeCount=" + placesModel.likeCount + ",Info='" + placesModel.info + "',webSite='" + placesModel.website + "',Visibility=" + ((placesModel.Visibility) ? 1 : 0) + ",lastUpdate='" + placesModel.lastUpdate + "',address='" + placesModel.address + "',image='" + placesModel.image + "' WHERE id=" + placesModel.id;
//        ArasDB.execSQL(sql);
//        ArasDB.close();
//    }
//
//    public void deleteMedical(String id) {
//
//        //Log.i("LOG", "delete city:" + id);
//        SQLiteDatabase ArasDB = getWritableDatabase();
//        String sql = "DELETE FROM Tbl_Medicals WHERE id=" + id + "";
//        ArasDB.execSQL(sql);
//        ArasDB.close();
//
//    }
//
//
//    public List<String> selectServiceById(String r) {
//        List<String> list = new ArrayList<>();
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql = "SELECT * FROM Tbl_Services WHERE id=" + r;
//        String request = "id";
//        Cursor cursor = ArasDB.rawQuery(sql, null);
//        cursor.moveToFirst();
//        if (!cursor.isAfterLast()) {
//            do {
//                String s = cursor.getString(cursor.getColumnIndex(request));
//                list.add(s);
//            } while (cursor.moveToNext());
//
//        }
//        cursor.close();
//        ArasDB.close();
//        return list;
//    }
//
//    public void insertNewService(PlacesModel placesModel) {
//
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql = "";
//        sql = "INSERT INTO Tbl_Services (id,type,idStartDay,idEndDay,startTime,endTime,Name,lat,lon,phone,star,starCount,likeCount,info,website,Visibility,lastUpdate,address,image,mainType) VALUES('"
//                + placesModel.id + "','" + placesModel.type + "','" + placesModel.idStartDay + "','" + placesModel.idEndDay + "','" + placesModel.startTime + "','" + placesModel.endTime + "','" + placesModel.Name + "','" + placesModel.lat + "','" + placesModel.lon + "','" + placesModel.phone + "','" + placesModel.star + "','" + placesModel.starCount + "','" + placesModel.likeCount + "','" + placesModel.info + "','" + placesModel.website + "','" + placesModel.Visibility + "','" + placesModel.lastUpdate + "','" + placesModel.address + "','" + placesModel.image + "','" + 7 + "')";
//        ArasDB.execSQL(sql);
//
//        ArasDB.close();
//    }
//
//    public List<String> selectServiceByLastUpdate(String r) {
//        List<String> list = new ArrayList<>();
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql = "SELECT * FROM Tbl_Services WHERE id=" + r;
//        String request = "lastUpdate";
//        Cursor cursor = ArasDB.rawQuery(sql, null);
//        cursor.moveToFirst();
//        if (!cursor.isAfterLast()) {
//            do {
//                String s = cursor.getString(cursor.getColumnIndex(request));
//                list.add(s);
//            } while (cursor.moveToNext());
//
//        }
//        cursor.close();
//        ArasDB.close();
//        return list;
//    }
//
//    public void updateService(PlacesModel placesModel) {
//
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql;
//        sql = "UPDATE Tbl_Services SET type=" + placesModel.type + ",idStartDay=" + placesModel.idStartDay + ",idEndDay=" + placesModel.idEndDay + ",startTime='" + placesModel.startTime + "',endTime='" + placesModel.endTime + "',Name='" + placesModel.Name + "',lat=" + placesModel.lat + ",lon=" + placesModel.lon + ",phone='" + placesModel.phone + "',star=" + placesModel.star + ",starCount=" + placesModel.starCount + ",likeCount=" + placesModel.likeCount + ",Info='" + placesModel.info + "',webSite='" + placesModel.website + "',Visibility=" + ((placesModel.Visibility) ? 1 : 0) + ",lastUpdate='" + placesModel.lastUpdate + "',address='" + placesModel.address + "',image='" + placesModel.image + "' WHERE id=" + placesModel.id;
//        ArasDB.execSQL(sql);
//        ArasDB.close();
//    }
//
//    public void deleteService(String id) {
//
//        //Log.i("LOG", "delete city:" + id);
//        SQLiteDatabase ArasDB = getWritableDatabase();
//        String sql = "DELETE FROM Tbl_Services WHERE id=" + id + "";
//        ArasDB.execSQL(sql);
//        ArasDB.close();
//
//    }
//
//
//    public List<String> selectShoppingById(String r) {
//        List<String> list = new ArrayList<>();
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql = "SELECT * FROM Tbl_Shoppings WHERE id=" + r;
//        String request = "id";
//        Cursor cursor = ArasDB.rawQuery(sql, null);
//        cursor.moveToFirst();
//        if (!cursor.isAfterLast()) {
//            do {
//                String s = cursor.getString(cursor.getColumnIndex(request));
//                list.add(s);
//            } while (cursor.moveToNext());
//
//        }
//        cursor.close();
//        ArasDB.close();
//        return list;
//    }
//
//    public void insertNewShopping(PlacesModel placesModel) {
//
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql = "";
//        sql = "INSERT INTO Tbl_Shoppings (id,type,idStartDay,idEndDay,startTime,endTime,Name,lat,lon,phone,star,starCount,likeCount,info,website,Visibility,lastUpdate,address,image,mainType) VALUES('"
//                + placesModel.id + "','" + placesModel.type + "','" + placesModel.idStartDay + "','" + placesModel.idEndDay + "','" + placesModel.startTime + "','" + placesModel.endTime + "','" + placesModel.Name + "','" + placesModel.lat + "','" + placesModel.lon + "','" + placesModel.phone + "','" + placesModel.star + "','" + placesModel.starCount + "','" + placesModel.likeCount + "','" + placesModel.info + "','" + placesModel.website + "','" + placesModel.Visibility + "','" + placesModel.lastUpdate + "','" + placesModel.address + "','" + placesModel.image + "','" + 2 + "')";
//        ArasDB.execSQL(sql);
//
//        ArasDB.close();
//    }
//
//    public List<String> selectShoppingByLastUpdate(String r) {
//        List<String> list = new ArrayList<>();
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql = "SELECT * FROM Tbl_Shoppings WHERE id=" + r;
//        String request = "lastUpdate";
//        Cursor cursor = ArasDB.rawQuery(sql, null);
//        cursor.moveToFirst();
//        if (!cursor.isAfterLast()) {
//            do {
//                String s = cursor.getString(cursor.getColumnIndex(request));
//                list.add(s);
//            } while (cursor.moveToNext());
//
//        }
//        cursor.close();
//        ArasDB.close();
//        return list;
//    }
//
//    public void deleteShopping(String id) {
//
//        //Log.i("LOG", "delete city:" + id);
//        SQLiteDatabase ArasDB = getWritableDatabase();
//        String sql = "DELETE FROM Tbl_Shoppings WHERE id=" + id + "";
//        ArasDB.execSQL(sql);
//        ArasDB.close();
//
//    }
//
//    public void updateShopping(PlacesModel placesModel) {
//
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql;
//        sql = "UPDATE Tbl_Shoppings SET type=" + placesModel.type + ",idStartDay=" + placesModel.idStartDay + ",idEndDay=" + placesModel.idEndDay + ",startTime='" + placesModel.startTime + "',endTime='" + placesModel.endTime + "',Name='" + placesModel.Name + "',lat=" + placesModel.lat + ",lon=" + placesModel.lon + ",phone='" + placesModel.phone + "',star=" + placesModel.star + ",starCount=" + placesModel.starCount + ",likeCount=" + placesModel.likeCount + ",Info='" + placesModel.info + "',webSite='" + placesModel.website + "',Visibility=" + ((placesModel.Visibility) ? 1 : 0) + ",lastUpdate='" + placesModel.lastUpdate + "',address='" + placesModel.address + "',image='" + placesModel.image + "' WHERE id=" + placesModel.id;
//        ArasDB.execSQL(sql);
//        ArasDB.close();
//    }
//
//
//    public List<String> selectTourismById(String r) {
//        List<String> list = new ArrayList<>();
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql = "SELECT * FROM Tbl_Tourisms WHERE id=" + r;
//        String request = "id";
//        Cursor cursor = ArasDB.rawQuery(sql, null);
//        cursor.moveToFirst();
//        if (!cursor.isAfterLast()) {
//            do {
//                String s = cursor.getString(cursor.getColumnIndex(request));
//                list.add(s);
//            } while (cursor.moveToNext());
//
//        }
//        cursor.close();
//        ArasDB.close();
//        return list;
//    }
//
//    public void insertNewTourism(PlacesModel placesModel) {
//
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql = "";
//        sql = "INSERT INTO Tbl_Tourisms (id,type,idStartDay,idEndDay,startTime,endTime,Name,lat,lon,phone,star,starCount,likeCount,info,website,Visibility,lastUpdate,address,cost,image,mainType) VALUES('"
//                + placesModel.id + "','" + placesModel.type + "','" + placesModel.idStartDay + "','" + placesModel.idEndDay + "','" + placesModel.startTime + "','" + placesModel.endTime + "','" + placesModel.Name + "','" + placesModel.lat + "','" + placesModel.lon + "','" + placesModel.phone + "','" + placesModel.star + "','" + placesModel.starCount + "','" + placesModel.likeCount + "','" + placesModel.info + "','" + placesModel.website + "','" + placesModel.Visibility + "','" + placesModel.lastUpdate + "','" + placesModel.address + "','" + placesModel.cost + "','" + placesModel.image + "','" + 4 + "')";
//        ArasDB.execSQL(sql);
//
//        ArasDB.close();
//    }
//
//    public List<String> selectTourismByLastUpdate(String r) {
//        List<String> list = new ArrayList<>();
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql = "SELECT * FROM Tbl_Tourisms WHERE id=" + r;
//        String request = "lastUpdate";
//        Cursor cursor = ArasDB.rawQuery(sql, null);
//        cursor.moveToFirst();
//        if (!cursor.isAfterLast()) {
//            do {
//                String s = cursor.getString(cursor.getColumnIndex(request));
//                list.add(s);
//            } while (cursor.moveToNext());
//
//        }
//        cursor.close();
//        ArasDB.close();
//        return list;
//    }
//
//    public void deleteTourism(String id) {
//
//        //Log.i("LOG", "delete city:" + id);
//        SQLiteDatabase ArasDB = getWritableDatabase();
//        String sql = "DELETE FROM Tbl_Tourisms WHERE id=" + id + "";
//        ArasDB.execSQL(sql);
//        ArasDB.close();
//
//    }
//
//    public void updateTourism(PlacesModel placesModel) {
//
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql;
//        sql = "UPDATE Tbl_Tourisms SET type=" + placesModel.type + ",idStartDay=" + placesModel.idStartDay + ",idEndDay=" + placesModel.idEndDay + ",startTime='" + placesModel.startTime + "',endTime='" + placesModel.endTime + "',Name='" + placesModel.Name + "',lat=" + placesModel.lat + ",lon=" + placesModel.lon + ",phone='" + placesModel.phone + "',star=" + placesModel.star + ",starCount=" + placesModel.starCount + ",likeCount=" + placesModel.likeCount + ",Info='" + placesModel.info + "',webSite='" + placesModel.website + "',Visibility=" + ((placesModel.Visibility) ? 1 : 0) + ",lastUpdate='" + placesModel.lastUpdate + "',address='" + placesModel.address + "',cost='" + placesModel.cost + "',image='" + placesModel.image + "' WHERE id=" + placesModel.id;
//        ArasDB.execSQL(sql);
//        ArasDB.close();
//    }
//
//
//    public List<String> selectTransportById(String r) {
//        List<String> list = new ArrayList<>();
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql = "SELECT * FROM Tbl_Transports WHERE id=" + r;
//        String request = "id";
//        Cursor cursor = ArasDB.rawQuery(sql, null);
//        cursor.moveToFirst();
//        if (!cursor.isAfterLast()) {
//            do {
//                String s = cursor.getString(cursor.getColumnIndex(request));
//                list.add(s);
//            } while (cursor.moveToNext());
//
//        }
//        cursor.close();
//        ArasDB.close();
//        return list;
//    }
//
//    public void insertNewTransport(PlacesModel placesModel) {
//
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql = "";
//        sql = "INSERT INTO Tbl_Transports (id,type,idStartDay,idEndDay,startTime,endTime,Name,lat,lon,phone,star,starCount,likeCount,info,website,Visibility,lastUpdate,address,image,mainType) VALUES('"
//                + placesModel.id + "','" + placesModel.type + "','" + placesModel.idStartDay + "','" + placesModel.idEndDay + "','" + placesModel.startTime + "','" + placesModel.endTime + "','" + placesModel.Name + "','" + placesModel.lat + "','" + placesModel.lon + "','" + placesModel.phone + "','" + placesModel.star + "','" + placesModel.starCount + "','" + placesModel.likeCount + "','" + placesModel.info + "','" + placesModel.website + "','" + placesModel.Visibility + "','" + placesModel.lastUpdate + "','" + placesModel.address + "','" + placesModel.image + "','" + 6 + "')";
//        ArasDB.execSQL(sql);
//
//        ArasDB.close();
//    }
//
//    public List<String> selectTransportByLastUpdate(String r) {
//        List<String> list = new ArrayList<>();
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql = "SELECT * FROM Tbl_Transports WHERE id=" + r;
//        String request = "lastUpdate";
//        Cursor cursor = ArasDB.rawQuery(sql, null);
//        cursor.moveToFirst();
//        if (!cursor.isAfterLast()) {
//            do {
//                String s = cursor.getString(cursor.getColumnIndex(request));
//                list.add(s);
//            } while (cursor.moveToNext());
//
//        }
//        cursor.close();
//        ArasDB.close();
//        return list;
//    }
//
//    public void deleteTransport(String id) {
//
//        //Log.i("LOG", "delete city:" + id);
//        SQLiteDatabase ArasDB = getWritableDatabase();
//        String sql = "DELETE FROM Tbl_Transports WHERE id=" + id + "";
//        ArasDB.execSQL(sql);
//        ArasDB.close();
//
//    }
//
//    public void updateTransport(PlacesModel placesModel) {
//
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql;
//        sql = "UPDATE Tbl_Transports SET type=" + placesModel.type + ",idStartDay=" + placesModel.idStartDay + ",idEndDay=" + placesModel.idEndDay + ",startTime='" + placesModel.startTime + "',endTime='" + placesModel.endTime + "',Name='" + placesModel.Name + "',lat=" + placesModel.lat + ",lon=" + placesModel.lon + ",phone='" + placesModel.phone + "',star=" + placesModel.star + ",starCount=" + placesModel.starCount + ",likeCount=" + placesModel.likeCount + ",Info='" + placesModel.info + "',webSite='" + placesModel.website + "',Visibility=" + ((placesModel.Visibility) ? 1 : 0) + ",lastUpdate='" + placesModel.lastUpdate + "',address='" + placesModel.address + "',image='" + placesModel.image + "' WHERE id=" + placesModel.id;
//        ArasDB.execSQL(sql);
//        ArasDB.close();
//    }
//
//
//    public List<String> selectRestmById(String r) {
//        List<String> list = new ArrayList<>();
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql = "SELECT * FROM Tbl_Rests WHERE id=" + r;
//        String request = "id";
//        Cursor cursor = ArasDB.rawQuery(sql, null);
//        cursor.moveToFirst();
//        if (!cursor.isAfterLast()) {
//            do {
//                String s = cursor.getString(cursor.getColumnIndex(request));
//                list.add(s);
//            } while (cursor.moveToNext());
//
//        }
//        cursor.close();
//        ArasDB.close();
//        return list;
//    }
//
//    public void insertNewRest(PlacesModel placesModel) {
//
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql = "";
//        sql = "INSERT INTO Tbl_Rests (id,type,idStartDay,idEndDay,startTime,endTime,Name,lat,lon,phone,star,starCount,likeCount,info,website,Visibility,lastUpdate,address,placeStar,image,mainType) VALUES('"
//                + placesModel.id + "','" + placesModel.type + "','" + placesModel.idStartDay + "','" + placesModel.idEndDay + "','" + placesModel.startTime + "','" + placesModel.endTime + "','" + placesModel.Name + "','" + placesModel.lat + "','" + placesModel.lon + "','" + placesModel.phone + "','" + placesModel.star + "','" + placesModel.starCount + "','" + placesModel.likeCount + "','" + placesModel.info + "','" + placesModel.website + "','" + placesModel.Visibility + "','" + placesModel.lastUpdate + "','" + placesModel.address + "','" + placesModel.placeStar + "','" + placesModel.image + "','" + 3 + "')";
//        ArasDB.execSQL(sql);
//
//        ArasDB.close();
//    }
//
//    public List<String> selectRestByLastUpdate(String r) {
//        List<String> list = new ArrayList<>();
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql = "SELECT * FROM Tbl_Rests WHERE id=" + r;
//        String request = "lastUpdate";
//        Cursor cursor = ArasDB.rawQuery(sql, null);
//        cursor.moveToFirst();
//        if (!cursor.isAfterLast()) {
//            do {
//                String s = cursor.getString(cursor.getColumnIndex(request));
//                list.add(s);
//            } while (cursor.moveToNext());
//
//        }
//        cursor.close();
//        ArasDB.close();
//        return list;
//    }
//
//    public void deleteRest(String id) {
//
//        //Log.i("LOG", "delete city:" + id);
//        SQLiteDatabase ArasDB = getWritableDatabase();
//        String sql = "DELETE FROM Tbl_Rests WHERE id=" + id + "";
//        ArasDB.execSQL(sql);
//        ArasDB.close();
//
//    }
//
//    public void updateRest(PlacesModel placesModel) {
//
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql;
//        sql = "UPDATE Tbl_Rests SET type=" + placesModel.type + ",idStartDay=" + placesModel.idStartDay + ",idEndDay=" + placesModel.idEndDay + ",startTime='" + placesModel.startTime + "',endTime='" + placesModel.endTime + "',Name='" + placesModel.Name + "',lat=" + placesModel.lat + ",lon=" + placesModel.lon + ",phone='" + placesModel.phone + "',star=" + placesModel.star + ",starCount=" + placesModel.starCount + ",likeCount=" + placesModel.likeCount + ",Info='" + placesModel.info + "',webSite='" + placesModel.website + "',Visibility=" + ((placesModel.Visibility) ? 1 : 0) + ",lastUpdate='" + placesModel.lastUpdate + "',address='" + placesModel.address + "',placeStar='" + placesModel.placeStar + "',image='" + placesModel.image + "' WHERE id=" + placesModel.id;
//        ArasDB.execSQL(sql);
//        ArasDB.close();
//    }
//
//
//    public List<String> selectEventId(String r) {
//        List<String> list = new ArrayList<>();
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql = "SELECT * FROM Tbl_Events WHERE id=" + r;
//        String request = "id";
//        Cursor cursor = ArasDB.rawQuery(sql, null);
//        cursor.moveToFirst();
//        if (!cursor.isAfterLast()) {
//            do {
//                String s = cursor.getString(cursor.getColumnIndex(request));
//                list.add(s);
//            } while (cursor.moveToNext());
//
//        }
//        cursor.close();
//        ArasDB.close();
//        return list;
//    }
//
//    public void insertNewEvent(EventModel placesModel) {
//
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql = "";
//        sql = "INSERT INTO Tbl_Events (id,body,Name,startTime,startDate,endDate,endTime,place,lat,lon,address,phone,website,Visibility,lastUpdate,image,mainType,likeCount) VALUES('"
//                + placesModel.id + "','" + placesModel.body + "','" + placesModel.Name + "','" + placesModel.startTime + "','" + placesModel.startDate + "','" + placesModel.endDate + "','" + placesModel.endTime + "','" + placesModel.place + "','" + placesModel.lat + "','" + placesModel.lon + "','" + placesModel.address + "','" + placesModel.phone + "','" + placesModel.website + "','" + placesModel.Visibility + "','" + placesModel.lastUpdate + "','" + placesModel.image + "','" + 10 + "','" + placesModel.likeCount + "')";
//        ArasDB.execSQL(sql);
//
//        ArasDB.close();
//    }
//
//    public List<String> selectEventByLastUpdate(String r) {
//        List<String> list = new ArrayList<>();
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql = "SELECT * FROM Tbl_Events WHERE id=" + r;
//        String request = "lastUpdate";
//        Cursor cursor = ArasDB.rawQuery(sql, null);
//        cursor.moveToFirst();
//        if (!cursor.isAfterLast()) {
//            do {
//                String s = cursor.getString(cursor.getColumnIndex(request));
//                list.add(s);
//            } while (cursor.moveToNext());
//
//        }
//        cursor.close();
//        ArasDB.close();
//        return list;
//    }
//
//    public void updateEvent(EventModel placesModel) {
//
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        String sql;
//        sql = "UPDATE Tbl_Events SET body='" + placesModel.body + "',Name='" + placesModel.Name + "',startTime='" + placesModel.startTime + "',startDate=" + placesModel.startDate + ",endDate=" + placesModel.endDate + ",endTime='" + placesModel.endTime + "',place='" + placesModel.place + "',lat=" + placesModel.lat + ",lon=" + placesModel.lon + ",address='" + placesModel.address + "',phone='" + placesModel.phone + "',webSite='" + placesModel.website + "',Visibility=" + ((placesModel.Visibility) ? 1 : 0) + ",lastUpdate='" + placesModel.lastUpdate + "',image='" + placesModel.image + "',likeCount=" + placesModel.likeCount + " WHERE id=" + placesModel.id;
//        ArasDB.execSQL(sql);
//        ArasDB.close();
//    }
//
//    public void deleteEvent(String id) {
//
//        //Log.i("LOG", "delete city:" + id);
//        SQLiteDatabase ArasDB = getWritableDatabase();
//        String sql = "DELETE FROM Tbl_Events WHERE id=" + id + "";
//        ArasDB.execSQL(sql);
//        ArasDB.close();
//
//    }


    public List<String> selectHomePageById(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_HomePage WHERE id=" + r;
        String request = "id";
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                String s = cursor.getString(cursor.getColumnIndex(request));
                list.add(s);
            } while (cursor.moveToNext());

        }
        cursor.close();
        ArasDB.close();
        return list;
    }

    public void insertNewHomePage(HomePageModel placesModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "";
        sql = "INSERT INTO Tbl_HomePage (id,title,des,image,visibility,lastUpdate) VALUES('"
                + placesModel.id + "','" + placesModel.title + "','" + placesModel.des + "','" + placesModel.image + "','" + placesModel.visibility + "','" + placesModel.lastUpdate + "')";
        ArasDB.execSQL(sql);

        ArasDB.close();
    }

    public List<String> selectHomePageByLastUpdate(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_HomePage WHERE id=" + r;
        String request = "lastUpdate";
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                String s = cursor.getString(cursor.getColumnIndex(request));
                list.add(s);
            } while (cursor.moveToNext());

        }
        cursor.close();
        ArasDB.close();
        return list;
    }

    public void updateHomePage(HomePageModel placesModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql;
        sql = "UPDATE Tbl_HomePage SET title='" + placesModel.title + "',des='" + placesModel.des + "',image='" + placesModel.image + "',visibility=" + ((placesModel.visibility) ? 1 : 0) + ",lastUpdate='" + placesModel.lastUpdate + "' WHERE id=" + placesModel.id;
        ArasDB.execSQL(sql);
        ArasDB.close();
    }

    public void deleteHomePage(String id) {

        //Log.i("LOG", "delete city:" + id);
        SQLiteDatabase ArasDB = getWritableDatabase();
        String sql = "DELETE FROM Tbl_HomePage WHERE id=" + id + "";
        ArasDB.execSQL(sql);
        ArasDB.close();

    }

    public List<HomePageModel> selectAllHomePages() {

        List<HomePageModel> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        //String order = "orderb";
        String sql = "SELECT * FROM Tbl_HomePage";
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                HomePageModel pm = new HomePageModel();
                pm.id = cursor.getInt(cursor.getColumnIndex("id"));
                pm.title = cursor.getString(cursor.getColumnIndex("title"));
                pm.des = cursor.getString(cursor.getColumnIndex("des"));
                pm.image = cursor.getString(cursor.getColumnIndex("image"));

                list.add(pm);
            } while (cursor.moveToNext());

        }
        cursor.close();
        ArasDB.close();
        return list;
    }


    public List<String> selectSubCategoryById(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_SubCategory WHERE SubCategoryId=" + r;
        String request = "SubCategoryId";
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                String s = cursor.getString(cursor.getColumnIndex(request));
                list.add(s);
            } while (cursor.moveToNext());

        }
        cursor.close();
        ArasDB.close();
        return list;
    }

    public void insertNewSubCategory(SubCategoryModel placesModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "";
        sql = "INSERT INTO Tbl_SubCategory (SubCategoryId,CategoryId,SubCategoryName,ImageName,isActive,lastUpdate) VALUES('"
                + placesModel.SubCategoryId + "','" + placesModel.CategoryId + "','" + placesModel.SubCategoryName + "','" + placesModel.ImageName + "','" + placesModel.isActive + "','" + placesModel.lastUpdate + "')";
        ArasDB.execSQL(sql);

        ArasDB.close();
    }

    public List<String> selectSubCategoryByLastUpdate(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_SubCategory WHERE SubCategoryId=" + r;
        String request = "lastUpdate";
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                String s = cursor.getString(cursor.getColumnIndex(request));
                list.add(s);
            } while (cursor.moveToNext());

        }
        cursor.close();
        ArasDB.close();
        return list;
    }

    public void updateSubCategory(SubCategoryModel placesModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql;
        sql = "UPDATE Tbl_SubCategory SET CategoryId=" + placesModel.CategoryId + ",SubCategoryName='" + placesModel.SubCategoryName + "',ImageName='" + placesModel.ImageName + "',isActive=" + ((placesModel.isActive) ? 1 : 0) + ",lastUpdate='" + placesModel.lastUpdate + "' WHERE SubCategoryId=" + placesModel.SubCategoryId;
        ArasDB.execSQL(sql);
        ArasDB.close();
    }

    public void deleteSubCategory(String id) {

        //Log.i("LOG", "delete city:" + id);
        SQLiteDatabase ArasDB = getWritableDatabase();
        String sql = "DELETE FROM Tbl_SubCategory WHERE SubCategoryId=" + id + "";
        ArasDB.execSQL(sql);
        ArasDB.close();

    }

    public List<SubCategoryModel> selectSubCategoryToList(int CategoryId) {

        List<SubCategoryModel> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        //String order = "orderb";
        String sql = "SELECT * FROM Tbl_SubCategory where CategoryId=" + CategoryId;
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                SubCategoryModel pm = new SubCategoryModel();
                pm.SubCategoryId = cursor.getInt(cursor.getColumnIndex("SubCategoryId"));
                pm.CategoryId = cursor.getInt(cursor.getColumnIndex("CategoryId"));
                pm.SubCategoryName = cursor.getString(cursor.getColumnIndex("SubCategoryName"));
                pm.ImageName = cursor.getString(cursor.getColumnIndex("ImageName"));

                list.add(pm);
            } while (cursor.moveToNext());

        }
        cursor.close();
        ArasDB.close();
        return list;
    }


    public List<String> selectImageId(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_Images WHERE id=" + r;
        String request = "id";
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                String s = cursor.getString(cursor.getColumnIndex(request));
                list.add(s);
            } while (cursor.moveToNext());

        }
        cursor.close();
        ArasDB.close();
        return list;
    }

    public void insertNewImage(ImgModel imgModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "";
        sql = "INSERT INTO Tbl_Images (id,Name,lastUpdate,idRow) VALUES('"
                + imgModel.id + "','" + imgModel.Name + "','" + imgModel.lastUpdate + "','" + imgModel.idRow + "')";
        ArasDB.execSQL(sql);

        ArasDB.close();
    }

    public List<String> selectImageByLastUpdate(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_Images WHERE id=" + r;
        String request = "lastUpdate";
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                String s = cursor.getString(cursor.getColumnIndex(request));
                list.add(s);
            } while (cursor.moveToNext());

        }
        cursor.close();
        ArasDB.close();
        return list;
    }

    public void updateImage(ImgModel imgModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql;
        sql = "UPDATE Tbl_Images SET Name='" + imgModel.Name + "',lastUpdate='" + imgModel.lastUpdate + "',idRow=" + imgModel.idRow + " WHERE id=" + imgModel.id;
        ArasDB.execSQL(sql);
        ArasDB.close();
    }

    public void deleteImage(String id) {

        //Log.i("LOG", "delete city:" + id);
        SQLiteDatabase ArasDB = getWritableDatabase();
        String sql = "DELETE FROM Tbl_Images WHERE id=" + id + "";
        ArasDB.execSQL(sql);
        ArasDB.close();

    }


    public List<ImgModel> selectPlacesImages(int idRow) {

        List<ImgModel> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        //String order = "orderb";
        String sql = "SELECT * FROM Tbl_Images WHERE idRow=" + idRow;
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                ImgModel pm = new ImgModel();
                pm.id = cursor.getInt(cursor.getColumnIndex("id"));
                pm.idRow = cursor.getInt(cursor.getColumnIndex("idRow"));
                pm.Name = cursor.getString(cursor.getColumnIndex("Name"));

                list.add(pm);
            } while (cursor.moveToNext());

        }
        cursor.close();
        ArasDB.close();
        return list;
    }


    public List<PlacesModel> selectAllPlacesbySearch(String searchValue, String sort, List<Integer> selectedFilters) {

        List<PlacesModel> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        //String order = "orderb";
        String sql = "SELECT * FROM Tbl_Places WHERE RootCategory In (";
        for (int i = 0; i < selectedFilters.size(); i++) {
            if (i == selectedFilters.size() - 1)
                sql += selectedFilters.get(i);
            else
                sql += selectedFilters.get(i) + ",";
        }

        sql += ") AND Name LIKE '%" + searchValue + "%' order by " + sort + " DESC";

        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                PlacesModel pm = new PlacesModel();
                pm.id = cursor.getInt(cursor.getColumnIndex("id"));
                pm.Category = cursor.getInt(cursor.getColumnIndex("Categroy"));
                pm.Name = cursor.getString(cursor.getColumnIndex("Name"));
                pm.Address = cursor.getString(cursor.getColumnIndex("Address"));
                pm.Star = cursor.getDouble(cursor.getColumnIndex("Star"));
                pm.RootCategory = cursor.getInt(cursor.getColumnIndex("RootCategory"));
                pm.image = cursor.getString(cursor.getColumnIndex("image"));

                list.add(pm);
            } while (cursor.moveToNext());

        }
        cursor.close();
        ArasDB.close();
        return list;
    }

    public List<PlacesModel> selectAllPlacesByFavorite() {

        List<PlacesModel> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        //String order = "orderb";
        String sql = "SELECT * FROM Tbl_Places WHERE idUserFavorite > 0";
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                PlacesModel pm = new PlacesModel();
                pm.id = cursor.getInt(cursor.getColumnIndex("id"));
                pm.Category = cursor.getInt(cursor.getColumnIndex("Category"));
                pm.RootCategory = cursor.getInt(cursor.getColumnIndex("RootCategory"));
                pm.Name = cursor.getString(cursor.getColumnIndex("Name"));
                pm.Address = cursor.getString(cursor.getColumnIndex("Address"));
                pm.image = cursor.getString(cursor.getColumnIndex("image"));
                pm.Star = cursor.getDouble(cursor.getColumnIndex("Star"));


                list.add(pm);
            } while (cursor.moveToNext());

        }
        cursor.close();
        ArasDB.close();
        return list;
    }

//    public List<EventModel> selectAllEventsByFavorite(String tblName) {
//
//        List<EventModel> list = new ArrayList<>();
//        SQLiteDatabase ArasDB = getReadableDatabase();
//        //String order = "orderb";
//        String sql = "SELECT * FROM " + tblName + " WHERE userFavorite > 0";
//        Cursor cursor = ArasDB.rawQuery(sql, null);
//        cursor.moveToFirst();
//        if (!cursor.isAfterLast()) {
//            do {
//                EventModel pm = new EventModel();
//                pm.id = cursor.getInt(cursor.getColumnIndex("id"));
//                pm.startDate = cursor.getInt(cursor.getColumnIndex("startDate"));
//                pm.Name = cursor.getString(cursor.getColumnIndex("Name"));
//                pm.address = cursor.getString(cursor.getColumnIndex("address"));
//                pm.image = cursor.getString(cursor.getColumnIndex("image"));
//
//
//                list.add(pm);
//            } while (cursor.moveToNext());
//
//        }
//        cursor.close();
//        ArasDB.close();
//        return list;
//    }

}

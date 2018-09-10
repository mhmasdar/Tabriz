package com.example.tabrizguilds.tabrizguilds.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tabrizguilds.tabrizguilds.models.EventModel;
import com.example.tabrizguilds.tabrizguilds.models.HomePageModel;
import com.example.tabrizguilds.tabrizguilds.models.ImgModel;
import com.example.tabrizguilds.tabrizguilds.models.MapModel;
import com.example.tabrizguilds.tabrizguilds.models.PhoneModel;
import com.example.tabrizguilds.tabrizguilds.models.PlacesModel;
import com.example.tabrizguilds.tabrizguilds.models.SubCategoryModel;

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

    public List<MapModel> selectAllPlacesToMap(String tblName) {

        List<MapModel> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        //String order = "orderb";
        String sql = "SELECT * FROM " + tblName;
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                MapModel pm = new MapModel();
                pm.id = cursor.getInt(cursor.getColumnIndex("id"));
                if (!tblName.equals("Tbl_Events"))
                    pm.type = cursor.getInt(cursor.getColumnIndex("type"));
                pm.mainType = cursor.getInt(cursor.getColumnIndex("mainType"));
                pm.name = cursor.getString(cursor.getColumnIndex("name"));
                pm.address = cursor.getString(cursor.getColumnIndex("address"));
                pm.image = cursor.getString(cursor.getColumnIndex("image"));
                pm.lat = cursor.getDouble(cursor.getColumnIndex("lat"));
                pm.lon = cursor.getDouble(cursor.getColumnIndex("lon"));
                if (!tblName.equals("Tbl_Offices") && !tblName.equals("Tbl_Events"))
                    pm.star = cursor.getDouble(cursor.getColumnIndex("star"));
                else
                    pm.star = 0;
                //pm.imgPersonal = cursor.getString(cursor.getColumnIndex("imgPersonal"));


                list.add(pm);
            } while (cursor.moveToNext());

        }
        cursor.close();
        ArasDB.close();
        return list;
    }

    public List<PlacesModel> selectAllPlacesToList(String tblName) {

        List<PlacesModel> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        //String order = "orderb";
        String sql = "SELECT * FROM " + tblName;
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                PlacesModel pm = new PlacesModel();
                pm.id = cursor.getInt(cursor.getColumnIndex("id"));
                pm.Categroy = cursor.getInt(cursor.getColumnIndex("type"));
                pm.mainType = cursor.getInt(cursor.getColumnIndex("mainType"));
                pm.Name = cursor.getString(cursor.getColumnIndex("name"));
                pm.Address = cursor.getString(cursor.getColumnIndex("address"));
                pm.image = cursor.getString(cursor.getColumnIndex("image"));
                if (!tblName.equals("Tbl_Offices"))
                    pm.Star = cursor.getDouble(cursor.getColumnIndex("star"));
                //pm.imgPersonal = cursor.getString(cursor.getColumnIndex("imgPersonal"));


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
//                pm.name = cursor.getString(cursor.getColumnIndex("name"));
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

    public PlacesModel selectPlacesDetail(String tblName, int id) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM " + tblName + " WHERE id=" + id;
        PlacesModel pm = new PlacesModel();
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {

                pm.id = cursor.getInt(cursor.getColumnIndex("id"));
                pm.Name = cursor.getString(cursor.getColumnIndex("name"));
                pm.Categroy = cursor.getInt(cursor.getColumnIndex("type"));
                pm.Lat = cursor.getDouble(cursor.getColumnIndex("lat"));
                pm.Long = cursor.getDouble(cursor.getColumnIndex("lon"));
                pm.Address = cursor.getString(cursor.getColumnIndex("address"));
                pm.Phone = cursor.getString(cursor.getColumnIndex("phone"));
                pm.Star = cursor.getDouble(cursor.getColumnIndex("star"));
                pm.StarCount = cursor.getInt(cursor.getColumnIndex("starCount"));
                pm.likeCount = cursor.getInt(cursor.getColumnIndex("likeCount"));
                pm.Info = cursor.getString(cursor.getColumnIndex("Info"));
                pm.webSite = cursor.getString(cursor.getColumnIndex("webSite"));
                pm.StartTime = cursor.getString(cursor.getColumnIndex("startTime"));
                pm.EndTime = cursor.getString(cursor.getColumnIndex("endTime"));
                if (tblName.equals("Tbl_Tourisms"))
                    pm.Cost = cursor.getInt(cursor.getColumnIndex("cost"));
                if (tblName.equals("Tbl_Rests"))
                    pm.placeStar = cursor.getInt(cursor.getColumnIndex("placeStar"));
//                pm.visibility = cursor.getInt(cursor.getColumnIndex("visibility"));
//                pm.lastUpdate = cursor.getInt(cursor.getColumnIndex("lastUpdate"));


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
//                pm.name = cursor.getString(cursor.getColumnIndex("name"));
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
////                pm.visibility = cursor.getInt(cursor.getColumnIndex("visibility"));
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
//                pm.name = cursor.getString(cursor.getColumnIndex("name"));
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
//                pm.name = cursor.getString(cursor.getColumnIndex("name"));
//                pm.type = cursor.getInt(cursor.getColumnIndex("type"));
//                pm.lat = cursor.getDouble(cursor.getColumnIndex("lat"));
//                pm.lon = cursor.getDouble(cursor.getColumnIndex("lon"));
//                pm.address = cursor.getString(cursor.getColumnIndex("address"));
//                pm.phone = cursor.getString(cursor.getColumnIndex("tel"));
//                pm.info = cursor.getString(cursor.getColumnIndex("Info"));
//                pm.website = cursor.getString(cursor.getColumnIndex("webSite"));
////                pm.visibility = cursor.getInt(cursor.getColumnIndex("visibility"));
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

    public void updateTblByLike(String tblName, int idRow, int idLike, int likeCount) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql;
        sql = "UPDATE " + tblName + " SET userLike=" + idLike + ",likeCount=" + likeCount + " WHERE id=" + idRow;
        ArasDB.execSQL(sql);
        ArasDB.close();
    }

    public void updateTblByRate(String tblName, int idRow, int idRate, double rateValue) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql;
        sql = "UPDATE " + tblName + " SET userRate=" + rateValue + ",idUserRate=" + idRate + " WHERE id=" + idRow;
        ArasDB.execSQL(sql);
        ArasDB.close();
    }

    public int selectFavoriteById(String tblName, int r) {
        String userFavorite = "";
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM " + tblName + " WHERE id=" + r;
        String request = "userFavorite";
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {

            userFavorite = cursor.getString(cursor.getColumnIndex(request));

        }
        cursor.close();
        ArasDB.close();
        if (userFavorite != null) {
            if (Integer.parseInt(userFavorite) > 0)
                return Integer.parseInt(userFavorite);
            else
                return -1;
        } else
            return -1;

    }

    public void updateTblByFavorite(String tblName, int idRow, int idFavorite) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql;
        sql = "UPDATE " + tblName + " SET userFavorite=" + idFavorite + " WHERE id=" + idRow;
        ArasDB.execSQL(sql);
        ArasDB.close();
    }

    public void updateTblByLikeAndRate(String tblName, int idRow, int idLR, double rate, int like) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql;
        sql = "UPDATE " + tblName + " SET userLike=" + ((like == 0) ? 0 : idLR) + ",idUserRate=" + ((rate == -1) ? 0 : idLR) + ",userRate=" + rate + " WHERE id=" + idRow;
        ArasDB.execSQL(sql);
        ArasDB.close();
    }

    public void updateTblsAfterExit(String tblName) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql;
        sql = "UPDATE " + tblName + " SET userFavorite=-1,userLike=-1,userRate=-1,idUserRate=-1";
        ArasDB.execSQL(sql);
        ArasDB.close();
    }


    public List<String> selectAllById(String tblName, String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM " + tblName + " WHERE id=" + r;
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
        sql = "INSERT INTO Tbl_Places (id,type,idStartDay,idEndDay,startTime,endTime,name,lat,lon,phone,star,starCount,likeCount,info,website,visibility,lastUpdate,address,image,mainType) VALUES('"
                + placesModel.id + "','" + placesModel.type + "','" + placesModel.idStartDay + "','" + placesModel.idEndDay + "','" + placesModel.startTime + "','" + placesModel.endTime + "','" + placesModel.name + "','" + placesModel.lat + "','" + placesModel.lon + "','" + placesModel.phone + "','" + placesModel.star + "','" + placesModel.starCount + "','" + placesModel.likeCount + "','" + placesModel.info + "','" + placesModel.website + "','" + placesModel.visibility + "','" + placesModel.lastUpdate + "','" + placesModel.address + "','" + placesModel.image + "','" + 5 + "')";
        ArasDB.execSQL(sql);

        ArasDB.close();
    }

    public List<String> selectPlacesByLastUpdate(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_Culturals WHERE id=" + r;
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
        sql = "UPDATE Tbl_Culturals SET type=" + placesModel.type + ",idStartDay=" + placesModel.idStartDay + ",idEndDay=" + placesModel.idEndDay + ",startTime='" + placesModel.startTime + "',endTime='" + placesModel.endTime + "',name='" + placesModel.name + "',lat=" + placesModel.lat + ",lon=" + placesModel.lon + ",phone='" + placesModel.phone + "',star=" + placesModel.star + ",starCount=" + placesModel.starCount + ",likeCount=" + placesModel.likeCount + ",Info='" + placesModel.info + "',webSite='" + placesModel.website + "',visibility=" + ((placesModel.visibility) ? 1 : 0) + ",lastUpdate='" + placesModel.lastUpdate + "',address='" + placesModel.address + "',image='" + placesModel.image + "' WHERE id=" + placesModel.id;
        ArasDB.execSQL(sql);
        ArasDB.close();
    }

    public void deletePlaces(String id) {

        //Log.i("LOG", "delete city:" + id);
        SQLiteDatabase ArasDB = getWritableDatabase();
        String sql = "DELETE FROM Tbl_Culturals WHERE id=" + id + "";
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
//        sql = "INSERT INTO Tbl_Offices (id,type,name,lat,lon,info,website,visibility,lastUpdate,address,tel,image,mainType) VALUES('"
//                + placesModel.id + "','" + placesModel.type + "','" + placesModel.name + "','" + placesModel.lat + "','" + placesModel.lon + "','" + placesModel.info + "','" + placesModel.website + "','" + placesModel.visibility + "','" + placesModel.lastUpdate + "','" + placesModel.address + "','" + placesModel.tel + "','" + placesModel.image + "','" + 8 + "')";
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
//        sql = "UPDATE Tbl_Offices SET type=" + placesModel.type + ",name='" + placesModel.name + "',lat=" + placesModel.lat + ",lon=" + placesModel.lon + ",Info='" + placesModel.info + "',webSite='" + placesModel.website + "',visibility=" + ((placesModel.visibility) ? 1 : 0) + ",lastUpdate='" + placesModel.lastUpdate + "',address='" + placesModel.address + "',tel='" + placesModel.tel + "',image='" + placesModel.image + "' WHERE id=" + placesModel.id;
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
//        sql = "INSERT INTO Tbl_Eating (id,type,idStartDay,idEndDay,startTime,endTime,name,lat,lon,phone,star,starCount,likeCount,info,website,visibility,lastUpdate,address,image,mainType) VALUES('"
//                + placesModel.id + "','" + placesModel.type + "','" + placesModel.idStartDay + "','" + placesModel.idEndDay + "','" + placesModel.startTime + "','" + placesModel.endTime + "','" + placesModel.name + "','" + placesModel.lat + "','" + placesModel.lon + "','" + placesModel.phone + "','" + placesModel.star + "','" + placesModel.starCount + "','" + placesModel.likeCount + "','" + placesModel.info + "','" + placesModel.website + "','" + placesModel.visibility + "','" + placesModel.lastUpdate + "','" + placesModel.address + "','" + placesModel.image + "','" + 1 + "')";
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
//        sql = "UPDATE Tbl_Eating SET type=" + placesModel.type + ",idStartDay=" + placesModel.idStartDay + ",idEndDay=" + placesModel.idEndDay + ",startTime='" + placesModel.startTime + "',endTime='" + placesModel.endTime + "',name='" + placesModel.name + "',lat=" + placesModel.lat + ",lon=" + placesModel.lon + ",phone='" + placesModel.phone + "',star=" + placesModel.star + ",starCount=" + placesModel.starCount + ",likeCount=" + placesModel.likeCount + ",Info='" + placesModel.info + "',webSite='" + placesModel.website + "',visibility=" + ((placesModel.visibility) ? 1 : 0) + ",lastUpdate='" + placesModel.lastUpdate + "',address='" + placesModel.address + "',image='" + placesModel.image + "' WHERE id=" + placesModel.id;
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
//        sql = "INSERT INTO Tbl_Medicals (id,type,idStartDay,idEndDay,startTime,endTime,name,lat,lon,phone,star,starCount,likeCount,info,website,visibility,lastUpdate,address,image,mainType) VALUES('"
//                + placesModel.id + "','" + placesModel.type + "','" + placesModel.idStartDay + "','" + placesModel.idEndDay + "','" + placesModel.startTime + "','" + placesModel.endTime + "','" + placesModel.name + "','" + placesModel.lat + "','" + placesModel.lon + "','" + placesModel.phone + "','" + placesModel.star + "','" + placesModel.starCount + "','" + placesModel.likeCount + "','" + placesModel.info + "','" + placesModel.website + "','" + placesModel.visibility + "','" + placesModel.lastUpdate + "','" + placesModel.address + "','" + placesModel.image + "','" + 9 + "')";
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
//        sql = "UPDATE Tbl_Medicals SET type=" + placesModel.type + ",idStartDay=" + placesModel.idStartDay + ",idEndDay=" + placesModel.idEndDay + ",startTime='" + placesModel.startTime + "',endTime='" + placesModel.endTime + "',name='" + placesModel.name + "',lat=" + placesModel.lat + ",lon=" + placesModel.lon + ",phone='" + placesModel.phone + "',star=" + placesModel.star + ",starCount=" + placesModel.starCount + ",likeCount=" + placesModel.likeCount + ",Info='" + placesModel.info + "',webSite='" + placesModel.website + "',visibility=" + ((placesModel.visibility) ? 1 : 0) + ",lastUpdate='" + placesModel.lastUpdate + "',address='" + placesModel.address + "',image='" + placesModel.image + "' WHERE id=" + placesModel.id;
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
//        sql = "INSERT INTO Tbl_Services (id,type,idStartDay,idEndDay,startTime,endTime,name,lat,lon,phone,star,starCount,likeCount,info,website,visibility,lastUpdate,address,image,mainType) VALUES('"
//                + placesModel.id + "','" + placesModel.type + "','" + placesModel.idStartDay + "','" + placesModel.idEndDay + "','" + placesModel.startTime + "','" + placesModel.endTime + "','" + placesModel.name + "','" + placesModel.lat + "','" + placesModel.lon + "','" + placesModel.phone + "','" + placesModel.star + "','" + placesModel.starCount + "','" + placesModel.likeCount + "','" + placesModel.info + "','" + placesModel.website + "','" + placesModel.visibility + "','" + placesModel.lastUpdate + "','" + placesModel.address + "','" + placesModel.image + "','" + 7 + "')";
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
//        sql = "UPDATE Tbl_Services SET type=" + placesModel.type + ",idStartDay=" + placesModel.idStartDay + ",idEndDay=" + placesModel.idEndDay + ",startTime='" + placesModel.startTime + "',endTime='" + placesModel.endTime + "',name='" + placesModel.name + "',lat=" + placesModel.lat + ",lon=" + placesModel.lon + ",phone='" + placesModel.phone + "',star=" + placesModel.star + ",starCount=" + placesModel.starCount + ",likeCount=" + placesModel.likeCount + ",Info='" + placesModel.info + "',webSite='" + placesModel.website + "',visibility=" + ((placesModel.visibility) ? 1 : 0) + ",lastUpdate='" + placesModel.lastUpdate + "',address='" + placesModel.address + "',image='" + placesModel.image + "' WHERE id=" + placesModel.id;
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
//        sql = "INSERT INTO Tbl_Shoppings (id,type,idStartDay,idEndDay,startTime,endTime,name,lat,lon,phone,star,starCount,likeCount,info,website,visibility,lastUpdate,address,image,mainType) VALUES('"
//                + placesModel.id + "','" + placesModel.type + "','" + placesModel.idStartDay + "','" + placesModel.idEndDay + "','" + placesModel.startTime + "','" + placesModel.endTime + "','" + placesModel.name + "','" + placesModel.lat + "','" + placesModel.lon + "','" + placesModel.phone + "','" + placesModel.star + "','" + placesModel.starCount + "','" + placesModel.likeCount + "','" + placesModel.info + "','" + placesModel.website + "','" + placesModel.visibility + "','" + placesModel.lastUpdate + "','" + placesModel.address + "','" + placesModel.image + "','" + 2 + "')";
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
//        sql = "UPDATE Tbl_Shoppings SET type=" + placesModel.type + ",idStartDay=" + placesModel.idStartDay + ",idEndDay=" + placesModel.idEndDay + ",startTime='" + placesModel.startTime + "',endTime='" + placesModel.endTime + "',name='" + placesModel.name + "',lat=" + placesModel.lat + ",lon=" + placesModel.lon + ",phone='" + placesModel.phone + "',star=" + placesModel.star + ",starCount=" + placesModel.starCount + ",likeCount=" + placesModel.likeCount + ",Info='" + placesModel.info + "',webSite='" + placesModel.website + "',visibility=" + ((placesModel.visibility) ? 1 : 0) + ",lastUpdate='" + placesModel.lastUpdate + "',address='" + placesModel.address + "',image='" + placesModel.image + "' WHERE id=" + placesModel.id;
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
//        sql = "INSERT INTO Tbl_Tourisms (id,type,idStartDay,idEndDay,startTime,endTime,name,lat,lon,phone,star,starCount,likeCount,info,website,visibility,lastUpdate,address,cost,image,mainType) VALUES('"
//                + placesModel.id + "','" + placesModel.type + "','" + placesModel.idStartDay + "','" + placesModel.idEndDay + "','" + placesModel.startTime + "','" + placesModel.endTime + "','" + placesModel.name + "','" + placesModel.lat + "','" + placesModel.lon + "','" + placesModel.phone + "','" + placesModel.star + "','" + placesModel.starCount + "','" + placesModel.likeCount + "','" + placesModel.info + "','" + placesModel.website + "','" + placesModel.visibility + "','" + placesModel.lastUpdate + "','" + placesModel.address + "','" + placesModel.cost + "','" + placesModel.image + "','" + 4 + "')";
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
//        sql = "UPDATE Tbl_Tourisms SET type=" + placesModel.type + ",idStartDay=" + placesModel.idStartDay + ",idEndDay=" + placesModel.idEndDay + ",startTime='" + placesModel.startTime + "',endTime='" + placesModel.endTime + "',name='" + placesModel.name + "',lat=" + placesModel.lat + ",lon=" + placesModel.lon + ",phone='" + placesModel.phone + "',star=" + placesModel.star + ",starCount=" + placesModel.starCount + ",likeCount=" + placesModel.likeCount + ",Info='" + placesModel.info + "',webSite='" + placesModel.website + "',visibility=" + ((placesModel.visibility) ? 1 : 0) + ",lastUpdate='" + placesModel.lastUpdate + "',address='" + placesModel.address + "',cost='" + placesModel.cost + "',image='" + placesModel.image + "' WHERE id=" + placesModel.id;
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
//        sql = "INSERT INTO Tbl_Transports (id,type,idStartDay,idEndDay,startTime,endTime,name,lat,lon,phone,star,starCount,likeCount,info,website,visibility,lastUpdate,address,image,mainType) VALUES('"
//                + placesModel.id + "','" + placesModel.type + "','" + placesModel.idStartDay + "','" + placesModel.idEndDay + "','" + placesModel.startTime + "','" + placesModel.endTime + "','" + placesModel.name + "','" + placesModel.lat + "','" + placesModel.lon + "','" + placesModel.phone + "','" + placesModel.star + "','" + placesModel.starCount + "','" + placesModel.likeCount + "','" + placesModel.info + "','" + placesModel.website + "','" + placesModel.visibility + "','" + placesModel.lastUpdate + "','" + placesModel.address + "','" + placesModel.image + "','" + 6 + "')";
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
//        sql = "UPDATE Tbl_Transports SET type=" + placesModel.type + ",idStartDay=" + placesModel.idStartDay + ",idEndDay=" + placesModel.idEndDay + ",startTime='" + placesModel.startTime + "',endTime='" + placesModel.endTime + "',name='" + placesModel.name + "',lat=" + placesModel.lat + ",lon=" + placesModel.lon + ",phone='" + placesModel.phone + "',star=" + placesModel.star + ",starCount=" + placesModel.starCount + ",likeCount=" + placesModel.likeCount + ",Info='" + placesModel.info + "',webSite='" + placesModel.website + "',visibility=" + ((placesModel.visibility) ? 1 : 0) + ",lastUpdate='" + placesModel.lastUpdate + "',address='" + placesModel.address + "',image='" + placesModel.image + "' WHERE id=" + placesModel.id;
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
//        sql = "INSERT INTO Tbl_Rests (id,type,idStartDay,idEndDay,startTime,endTime,name,lat,lon,phone,star,starCount,likeCount,info,website,visibility,lastUpdate,address,placeStar,image,mainType) VALUES('"
//                + placesModel.id + "','" + placesModel.type + "','" + placesModel.idStartDay + "','" + placesModel.idEndDay + "','" + placesModel.startTime + "','" + placesModel.endTime + "','" + placesModel.name + "','" + placesModel.lat + "','" + placesModel.lon + "','" + placesModel.phone + "','" + placesModel.star + "','" + placesModel.starCount + "','" + placesModel.likeCount + "','" + placesModel.info + "','" + placesModel.website + "','" + placesModel.visibility + "','" + placesModel.lastUpdate + "','" + placesModel.address + "','" + placesModel.placeStar + "','" + placesModel.image + "','" + 3 + "')";
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
//        sql = "UPDATE Tbl_Rests SET type=" + placesModel.type + ",idStartDay=" + placesModel.idStartDay + ",idEndDay=" + placesModel.idEndDay + ",startTime='" + placesModel.startTime + "',endTime='" + placesModel.endTime + "',name='" + placesModel.name + "',lat=" + placesModel.lat + ",lon=" + placesModel.lon + ",phone='" + placesModel.phone + "',star=" + placesModel.star + ",starCount=" + placesModel.starCount + ",likeCount=" + placesModel.likeCount + ",Info='" + placesModel.info + "',webSite='" + placesModel.website + "',visibility=" + ((placesModel.visibility) ? 1 : 0) + ",lastUpdate='" + placesModel.lastUpdate + "',address='" + placesModel.address + "',placeStar='" + placesModel.placeStar + "',image='" + placesModel.image + "' WHERE id=" + placesModel.id;
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
//        sql = "INSERT INTO Tbl_Events (id,body,name,startTime,startDate,endDate,endTime,place,lat,lon,address,phone,website,visibility,lastUpdate,image,mainType,likeCount) VALUES('"
//                + placesModel.id + "','" + placesModel.body + "','" + placesModel.name + "','" + placesModel.startTime + "','" + placesModel.startDate + "','" + placesModel.endDate + "','" + placesModel.endTime + "','" + placesModel.place + "','" + placesModel.lat + "','" + placesModel.lon + "','" + placesModel.address + "','" + placesModel.phone + "','" + placesModel.website + "','" + placesModel.visibility + "','" + placesModel.lastUpdate + "','" + placesModel.image + "','" + 10 + "','" + placesModel.likeCount + "')";
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
//        sql = "UPDATE Tbl_Events SET body='" + placesModel.body + "',name='" + placesModel.name + "',startTime='" + placesModel.startTime + "',startDate=" + placesModel.startDate + ",endDate=" + placesModel.endDate + ",endTime='" + placesModel.endTime + "',place='" + placesModel.place + "',lat=" + placesModel.lat + ",lon=" + placesModel.lon + ",address='" + placesModel.address + "',phone='" + placesModel.phone + "',webSite='" + placesModel.website + "',visibility=" + ((placesModel.visibility) ? 1 : 0) + ",lastUpdate='" + placesModel.lastUpdate + "',image='" + placesModel.image + "',likeCount=" + placesModel.likeCount + " WHERE id=" + placesModel.id;
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




    public List<String> selectOfficePhoneById(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_OfficePhone WHERE id=" + r;
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

    public void insertNewOfficePhone(PhoneModel placesModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "";
        sql = "INSERT INTO Tbl_OfficePhone (id,name,phone,visibility,lastUpdate) VALUES('"
                + placesModel.id + "','" + placesModel.name + "','" + placesModel.phone + "','" + placesModel.visibility + "','" + placesModel.lastUpdate + "')";
        ArasDB.execSQL(sql);

        ArasDB.close();
    }

    public List<String> selectOfficePhoneByLastUpdate(String r) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql = "SELECT * FROM Tbl_OfficePhone WHERE id=" + r;
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

    public void updateOfficePhone(PhoneModel placesModel) {

        SQLiteDatabase ArasDB = getReadableDatabase();
        String sql;
        sql = "UPDATE Tbl_OfficePhone SET name='" + placesModel.name + "',phone='" + placesModel.phone + "',visibility=" + ((placesModel.visibility) ? 1 : 0) + ",lastUpdate='" + placesModel.lastUpdate + "' WHERE id=" + placesModel.id;
        ArasDB.execSQL(sql);
        ArasDB.close();
    }

    public void deleteOfficePhone(String id) {

        //Log.i("LOG", "delete city:" + id);
        SQLiteDatabase ArasDB = getWritableDatabase();
        String sql = "DELETE FROM Tbl_OfficePhone WHERE id=" + id + "";
        ArasDB.execSQL(sql);
        ArasDB.close();

    }

    public List<PhoneModel> selectAllPhones() {

        List<PhoneModel> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        //String order = "orderb";
        String sql = "SELECT * FROM Tbl_OfficePhone";
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                PhoneModel pm = new PhoneModel();
                pm.id = cursor.getInt(cursor.getColumnIndex("id"));
                pm.name = cursor.getString(cursor.getColumnIndex("name"));
                pm.phone = cursor.getString(cursor.getColumnIndex("phone"));

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
        sql = "INSERT INTO Tbl_Images (id,type,name,lastUpdate,idRow,isAdmin,idUser) VALUES('"
                + imgModel.id + "','" + imgModel.type + "','" + imgModel.name + "','" + imgModel.lastUpdate + "','" + imgModel.idRow + "','" + ((imgModel.isAdmin) ? 1 : 0) + "','" + imgModel.idUser + "')";
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
        sql = "UPDATE Tbl_Images SET type=" + imgModel.type + ",name='" + imgModel.name + "',lastUpdate='" + imgModel.lastUpdate + "',idRow=" + imgModel.idRow + ",isAdmin=" + ((imgModel.isAdmin) ? 1 : 0) + ",idUser=" + imgModel.idUser + " WHERE id=" + imgModel.id;
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


    public List<ImgModel> selectPlacesImages(int mainType, int idRow, int isAdmin) {

        List<ImgModel> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        //String order = "orderb";
        String sql = "SELECT * FROM Tbl_Images WHERE type=" + mainType + " AND idRow=" + idRow + " AND isAdmin=" + isAdmin;
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                ImgModel pm = new ImgModel();
                pm.id = cursor.getInt(cursor.getColumnIndex("id"));
                pm.idRow = cursor.getInt(cursor.getColumnIndex("idRow"));
                pm.type = cursor.getInt(cursor.getColumnIndex("type"));
                pm.name = cursor.getString(cursor.getColumnIndex("name"));

                list.add(pm);
            } while (cursor.moveToNext());

        }
        cursor.close();
        ArasDB.close();
        return list;
    }


    public List<PlacesModel> selectAllPlacesbySearch(List<String> tblNames, String searchValue, String sort) {

        List<PlacesModel> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        //String order = "orderb";
        String sql = "SELECT id,type,name,address,star,likeCount,mainType,image FROM " + tblNames.get(0) + " WHERE name LIKE '%" + searchValue + "%'";

        for (int i = 1; i < tblNames.size(); i++) {
            sql += " UNION SELECT id,type,name,address,star,likeCount,mainType,image FROM " + tblNames.get(i) + " WHERE name LIKE '%" + searchValue + "%'";
        }

        sql += "order by " + sort + " DESC";

        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                PlacesModel pm = new PlacesModel();
                pm.id = cursor.getInt(cursor.getColumnIndex("id"));
                pm.Categroy = cursor.getInt(cursor.getColumnIndex("type"));
                pm.Name = cursor.getString(cursor.getColumnIndex("name"));
                pm.Address = cursor.getString(cursor.getColumnIndex("address"));
                if (!tblNames.equals("Tbl_Offices"))
                    pm.Star = cursor.getDouble(cursor.getColumnIndex("star"));
                pm.mainType = cursor.getInt(cursor.getColumnIndex("mainType"));
                pm.image = cursor.getString(cursor.getColumnIndex("image"));


                if (!(pm.mainType == 8 && pm.id == 1))
                    list.add(pm);
            } while (cursor.moveToNext());

        }
        cursor.close();
        ArasDB.close();
        return list;
    }

    public List<PlacesModel> selectAllPlacesByFavorite(String tblName) {

        List<PlacesModel> list = new ArrayList<>();
        SQLiteDatabase ArasDB = getReadableDatabase();
        //String order = "orderb";
        String sql = "SELECT * FROM " + tblName + " WHERE userFavorite > 0";
        Cursor cursor = ArasDB.rawQuery(sql, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                PlacesModel pm = new PlacesModel();
                pm.id = cursor.getInt(cursor.getColumnIndex("id"));
                if (!tblName.equals("Tbl_Events"))
                    pm.Categroy = cursor.getInt(cursor.getColumnIndex("type"));
                pm.mainType = cursor.getInt(cursor.getColumnIndex("mainType"));
                pm.Name = cursor.getString(cursor.getColumnIndex("name"));
                pm.Address = cursor.getString(cursor.getColumnIndex("address"));
                pm.image = cursor.getString(cursor.getColumnIndex("image"));
                if (!tblName.equals("Tbl_Offices") && !tblName.equals("Tbl_Events"))
                    pm.Star = cursor.getDouble(cursor.getColumnIndex("star"));
                //pm.imgPersonal = cursor.getString(cursor.getColumnIndex("imgPersonal"));


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
//                pm.name = cursor.getString(cursor.getColumnIndex("name"));
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

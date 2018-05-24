package com.example.tabrizguilds.tabrizguilds.db;

import android.content.Context;

import com.example.tabrizguilds.tabrizguilds.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Created by EHSAN PC on 3/13/2017.
 */

public class IOHelper {

    public final static String DATA_DIRECTORY = app.context.getApplicationInfo().dataDir + "/";
    public final static String DATABASE_FILE_NAME = "ArasDB.db";

    public static void transferDatabaseFile(Context context) {
        File databaseFile = new File(DATA_DIRECTORY + DATABASE_FILE_NAME);
        if (!databaseFile.exists()) {
            InputStream inputStream;
            OutputStream outputStream;
            try {
                inputStream = context.getAssets().open(DATABASE_FILE_NAME);
                new File(DATA_DIRECTORY + DATABASE_FILE_NAME).createNewFile();
                outputStream = new FileOutputStream(DATA_DIRECTORY + DATABASE_FILE_NAME);
                copyFile(inputStream, outputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void copyFile(InputStream inputStream, OutputStream outputStream) {
        byte[] buffer = new byte[1024];
        int lenRead ;
        try {
            while ((lenRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, lenRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
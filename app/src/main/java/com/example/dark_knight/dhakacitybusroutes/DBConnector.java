package com.example.dark_knight.dhakacitybusroutes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBConnector extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 4 ;
    public static final String DATABASE_NAME = "bus.db";

    public DBConnector(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + BusProfile.KEY_TABLE_NAME  + "("
                + BusProfile.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + BusProfile.KEY_NAME + " TEXT, "
                + BusProfile.KEY_RENT + " INTEGER, "
                + BusProfile.KEY_SEAT_NUMBERS + " INTEGER, "
                + BusProfile.KEY_ROUTE + " INTEGER )";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS "+BusProfile.KEY_TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }


}

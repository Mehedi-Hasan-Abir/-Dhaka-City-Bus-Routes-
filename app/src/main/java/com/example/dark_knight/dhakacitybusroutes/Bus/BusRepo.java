package com.example.dark_knight.dhakacitybusroutes.Bus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class BusRepo {
    private DBConnector dbConnector;

    public BusRepo(Context context){
        dbConnector = new DBConnector(context);
    }
    public int insert(BusProfile busProfile){ //sp will be added to the database
        SQLiteDatabase db = dbConnector.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BusProfile.KEY_ROUTE, busProfile.route);
        values.put(BusProfile.KEY_RENT,busProfile.rent);
        values.put(BusProfile.KEY_SEAT_NUMBERS,busProfile.seatNo);
        values.put(BusProfile.KEY_NAME, busProfile.name);

        long bus_ID = db.insert(BusProfile.KEY_TABLE_NAME,null,values);
        db.close();
        return (int)(bus_ID);
    }
    public void delete(String Bus_NAME){
        SQLiteDatabase db = dbConnector.getWritableDatabase();
        db.delete(BusProfile.KEY_TABLE_NAME, BusProfile.KEY_NAME + "= ?", new String[] {String.valueOf(Bus_NAME) });
        db.close();
    }

    public void update(BusProfile Bus) {

        SQLiteDatabase db = dbConnector.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(BusProfile.KEY_NAME, Bus.name);
        values.put(BusProfile.KEY_SEAT_NUMBERS, Bus.seatNo);
        values.put(BusProfile.KEY_ROUTE,Bus.route);
        values.put(BusProfile.KEY_RENT, Bus.rent);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(BusProfile.KEY_TABLE_NAME, values, BusProfile.KEY_NAME + "= ?", new String[] { String.valueOf(Bus.name) });
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>> getBusList() {
        //Open connection to read only
        SQLiteDatabase db = dbConnector.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                BusProfile.KEY_ID + "," +
                BusProfile.KEY_NAME + "," +
                BusProfile.KEY_SEAT_NUMBERS + "," +
                BusProfile.KEY_ROUTE + "," +
                BusProfile.KEY_FULL_ROUTE + "," +
                BusProfile.KEY_RENT+
                " FROM " + BusProfile.KEY_TABLE_NAME;

        ArrayList<HashMap<String, String>> busList = new ArrayList();

        Cursor cursor = db.rawQuery(selectQuery, null); //point the location
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> Bus = new HashMap();
                Bus.put("id", cursor.getString(cursor.getColumnIndex(BusProfile.KEY_ID)));
                Bus.put("name", cursor.getString(cursor.getColumnIndex(BusProfile.KEY_NAME))); //adding to list
                busList.add(Bus);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return busList;

    }
    public BusProfile getBusById(int Id ,String name){
        SQLiteDatabase db = dbConnector.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                BusProfile.KEY_ID + "," +
                BusProfile.KEY_NAME + "," +
                BusProfile.KEY_SEAT_NUMBERS + "," +
                BusProfile.KEY_ROUTE + "," +
                BusProfile.KEY_FULL_ROUTE + "," +
                BusProfile.KEY_RENT+
                " FROM " + BusProfile.KEY_TABLE_NAME
                + " WHERE " +
                BusProfile.KEY_NAME + "=?";//good practice to use parameter ?.... instead of concatenate string

        //int iCount =0;
        BusProfile busProfile = new BusProfile();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(name) } );

        if (cursor.moveToFirst()) {
            do {
                busProfile.id=cursor.getInt(cursor.getColumnIndex(BusProfile.KEY_ID));
                busProfile.name =cursor.getString(cursor.getColumnIndex(BusProfile.KEY_NAME));
                busProfile.seatNo  =cursor.getInt(cursor.getColumnIndex(BusProfile.KEY_SEAT_NUMBERS));
                busProfile.rent =cursor.getInt(cursor.getColumnIndex(BusProfile.KEY_RENT));
                busProfile.route =cursor.getString(cursor.getColumnIndex(BusProfile.KEY_ROUTE));
                busProfile.fullRoute =cursor.getString(cursor.getColumnIndex(BusProfile.KEY_FULL_ROUTE));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return busProfile;
    }


}

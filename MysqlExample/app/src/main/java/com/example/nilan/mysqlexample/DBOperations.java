package com.example.nilan.mysqlexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by nilan on 3/7/2017.
 */

public class DBOperations {
    DBHelper dbHelper;
    SQLiteDatabase database;

    public DBOperations(Context context)
    {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public void insertitems(float latitude, float longitude, String timestamp)
    {
        ContentValues values = new ContentValues();
        values.put("latitude", latitude);
        values.put("longitude", longitude);
        values.put("timestamp", timestamp);
        database.insert("location", null, values);
        return;
    }

    /*
    Cursor query (boolean distinct,
                  String table,
                  String[] columns,
                  String selection,
                  String[] selectionArgs,
                  String groupBy,
                  String having,
                  String orderBy,
                  String limit)
                  */
    public List<Float> returnlatitude()
    {
        Cursor temp = database.query("location", new String[]{"ID", "latitude", "longitude" +
                "timestamp"}, null, null, null, null, null, null);

        List<Float> alllatitudes = new LinkedList<Float>();
        do{
            Float lat = temp.getFloat(1);
            alllatitudes.add(lat);
        }while(temp.moveToNext());

        return alllatitudes;
    }

}

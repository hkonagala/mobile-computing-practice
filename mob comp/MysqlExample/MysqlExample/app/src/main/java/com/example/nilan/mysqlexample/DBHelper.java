package com.example.nilan.mysqlexample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nilan on 3/7/2017.
 */

public class DBHelper extends SQLiteOpenHelper{

    private static String DATABASE_NAME = "locationdb";
    private static int DATABASE_VERSION = 1;
    private String CREATETABLE = "create table location(id integer, latitude real," +
            "lontitude real, timestamp text, primary key(id)";
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATETABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF Exists location");
        onCreate(db);
    }
}

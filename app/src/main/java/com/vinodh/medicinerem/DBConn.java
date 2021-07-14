package com.vinodh.medicinerem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBConn extends SQLiteOpenHelper {
    public DBConn(Context context) {
        super(context, "MedDB", null, 1);//database name
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table MDTable(MedicineName TEXT, Date TEXT, Time TEXT)");//Creates a table in the database

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertvalues(String medname, String meddate, String medtime){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MedicineName",medname);
        contentValues.put("Date",meddate);
        contentValues.put("Time",medtime);
        long res = database.insert("MDTable", null, contentValues);
        if (res == -1)
            return  false;
        else
            return true;
    }

    public Cursor FetchData(String date, String time){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.rawQuery("select * from MDTable where date='"+date+"' AND time='"+time+"'", null);
        return c;
    }
}

package com.example.bruker.s236308_mappe3.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import com.example.bruker.s236308_mappe3.models.Trip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bruker on 03.12.2016.
 */

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Quicktrip_database";
    public static final String TABLE_NAME = "QuickTrips";
    public static final String KEY_ID = "_ID";
    public static final String KEY_FROM = "Fra";
    public static final String KEY_TO = "Til";
    public static final String KEY_FROM_ID = "FraID";
    public static final String KEY_TO_ID = "TilID";
    public static final int VERSION = 1;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create = "create table " + TABLE_NAME + "(" +
                KEY_ID + " integer primary key autoincrement," +
                KEY_FROM + " text," +
                KEY_TO + " text," +
                KEY_FROM_ID + " integer," +
                KEY_TO_ID + " integer);";

        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void insertQuickTrip(ModelTrip trip) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FROM, trip.from);
        values.put(KEY_TO, trip.to);
        values.put(KEY_FROM_ID, trip.fromID);
        values.put(KEY_TO_ID, trip.toID);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<ModelTrip> getAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " order by " + KEY_ID, null);
        ArrayList<ModelTrip> list = new ArrayList<>();
        if(cursor.moveToFirst()) {
            do {
                ModelTrip trip = new ModelTrip(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4));
                list.add(trip);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public void delete(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + "=" + id, null);

        db.close();
    }
}

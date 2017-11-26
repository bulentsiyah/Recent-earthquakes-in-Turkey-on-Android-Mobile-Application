package com.bulentsiyah.androidturkiyedekisondepremleruygulamasi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bülent SİYAH on 15.8.2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DataBase";

    private static final String TABLE_tb_test = "depremler";
    private static final String tb_Pk = "Pk";
    private static final String tb_Text = "Text";
    private static final String tb_DateTime = "Datetime";

    public static SQLiteDatabase db;
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        try {
            db = getWritableDatabase();
        }catch(Exception e){

        }

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            String create_TABLE_tbTest = "CREATE TABLE " + TABLE_tb_test + "("
                    + tb_Pk + " INTEGER PRIMARY KEY," + tb_Text + " TEXT,"
                    + tb_DateTime + " BIGINT" + ")";

            db.execSQL(create_TABLE_tbTest);

        } catch (SQLException e) {
            e.toString();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_tb_test);
            onCreate(db);
        } catch (SQLException e) {
            e.toString();
        }

    }

    public void addTbTest(String text, Long dateTime) {
        try {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(tb_Text, text);
            values.put(tb_DateTime, dateTime);

            db.insert(TABLE_tb_test, null, values);
            db.close();
        } catch (SQLException e) {
            e.toString();
        }
    }

    public void updateTbTest(int id, String text,
                             Long dateTime) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(tb_Text, text);
            values.put(tb_DateTime, dateTime);

            db.update(TABLE_tb_test, values, tb_Pk + " = ?",
                    new String[] { String.valueOf(id) });
        } catch (SQLException e) {
            e.toString();
        }

    }

    public int getItemTbTest() {
        try {
            String countQuery = "SELECT * FROM " + TABLE_tb_test;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            return cursor.getCount();
        } catch (Exception e) {
            e.toString();
            return 0;
        }
    }

    public List<String[]> getAllTbTest() {
        List<String[]> tmpData = new ArrayList<String[]>();
        try {
            String selectQuery = "SELECT * FROM " + TABLE_tb_test;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    try {

                        String[] tmp = new String[3];
                        tmp[0] = cursor.getString(0);
                        tmp[1] = cursor.getString(1);
                        tmp[2] = cursor.getString(2);
                        tmpData.add(tmp);
                    } catch (Exception exp) {
                        exp.toString();
                    }
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.toString();
        }
        return tmpData;

    }

    public void deleteTbTest(String[] dataRow) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_tb_test, tb_Pk + " = ?",
                    new String[] { String.valueOf(dataRow[0]) });
            db.close();
        } catch (Exception e) {
            e.toString();
        }

    }

}
package com.a7codes.menu.Classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

public class DBHelper extends SQLiteOpenHelper {

    Context context;
    private static final String  DATABASE_NAME      = "A7MENU.db";
    private static final Integer DATABASE_VERSION   = 1;

    //Class A Item
    private static final String  TABLE_NAME_CA         = "ClassA";
    private static final String  COLUMN_ID_CA          = "_id";
    private static final String  COLUMN_TITLE_CA       = "title";
    private static final String  COLUMN_PARENT_CA      = "parent";

    //Class B Item
    private static final String  TABLE_NAME_CB         = "ClassB";
    private static final String  COLUMN_ID_CB          = "_id";
    private static final String  COLUMN_TITLE_CB       = "title";
    private static final String  COLUMN_PARENT_CB      = "parent";
    private static final String  COLUMN_IMG_CB         = "img";

    //Class C Item
    private static final String  TABLE_NAME_CC         = "ClassC";
    private static final String  COLUMN_ID_CC          = "_id";
    private static final String  COLUMN_TITLE_CC       = "title";
    private static final String  COLUMN_PARENT_CC      = "parent";
    private static final String  COLUMN_IMG_CC         = "img";
    private static final String  COLUMN_DESC_CC        = "description";
    private static final String  COLUMN_PRICE_CC       = "price";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    //1-First Table Creation
    public void createTableClassA(){
        String CreateQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_CA +
                " (" + COLUMN_ID_CA + " INTEGER PRIMARY KEY , " +
                COLUMN_TITLE_CA + " TEXT, " +
                COLUMN_PARENT_CA + " INT );";
        this.getWritableDatabase().execSQL(CreateQuery);
    }

    //2-Second Table Creation
    public void createTableClassB(){
        String CreateQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_CB +
                " (" + COLUMN_ID_CB     + " INTEGER PRIMARY KEY , " +
                       COLUMN_TITLE_CB  + " TEXT, " +
                       COLUMN_PARENT_CB + " INT, "  +
                       COLUMN_IMG_CB    + " TEXT "
                + " );";
        this.getWritableDatabase().execSQL(CreateQuery);
    }

    //2-Second Table Creation
    public void createTableClassC(){
        String CreateQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_CC +
                " (" + COLUMN_ID_CC     + " INTEGER PRIMARY KEY , " +
                COLUMN_TITLE_CC  + " TEXT, " +
                COLUMN_PARENT_CC + " INT, "  +
                COLUMN_IMG_CC    + " TEXT, " +
                COLUMN_DESC_CC   + " TEXT, " +
                COLUMN_PRICE_CC  + " TEXT "
                + " );";
            this.getWritableDatabase().execSQL(CreateQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CA);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CB);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CC);
        onCreate(sqLiteDatabase);
    }

    public void AddClassA(int id, String title, int parent){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID_CA, id);
        cv.put(COLUMN_TITLE_CA,title);
        cv.put(COLUMN_PARENT_CA,parent);

        long result = db.insert(TABLE_NAME_CA, null, cv);
    }

    public void AddClassB(int id, String title, int parent, String img){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID_CB, id);
        cv.put(COLUMN_TITLE_CB, title);
        cv.put(COLUMN_PARENT_CB, parent);
        cv.put(COLUMN_IMG_CB, img);

        long result = db.insert(TABLE_NAME_CB, null, cv);

    }

    public void AddClassC(int id, String title, int parent, String img, String desc, String price){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID_CC, id);
        cv.put(COLUMN_TITLE_CC, title);
        cv.put(COLUMN_PARENT_CC, parent);
        cv.put(COLUMN_IMG_CC, img);
        cv.put(COLUMN_DESC_CC, desc);
        cv.put(COLUMN_PRICE_CC, price);

        long result = db.insert(TABLE_NAME_CC, null, cv);
    }

    //Read Data
    public Cursor SelectAll(int mode){
        String selectAllQuery = "";

        switch (mode){
            case 1:
                selectAllQuery =  "SELECT * FROM " + TABLE_NAME_CA;
                break;
            case 2:
                selectAllQuery =  "SELECT * FROM " + TABLE_NAME_CB;
                break;
            case 3:
                selectAllQuery =  "SELECT * FROM " + TABLE_NAME_CC;
                break;

            default:
                selectAllQuery =  "SELECT * FROM " + TABLE_NAME_CA;
                break;
        }

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null) {
            cursor = db.rawQuery(selectAllQuery, null);
        }

        return cursor;
    }

    //Delete Data
    public void DeleteRow(int mode, int ID){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = -1;

        switch (mode){
            case 1:
                result = db.delete(TABLE_NAME_CA, "_id=?", new String[]{String.valueOf(ID)});
                break;

            case 2:
                result = db.delete(TABLE_NAME_CB, "_id=?", new String[]{String.valueOf(ID)});
                break;

            case 3:
                result = db.delete(TABLE_NAME_CC, "_id=?", new String[]{String.valueOf(ID)});
                break;
        }
    }

    public void UpdateRow(int mode, int id, String title, int parent, String img,String desc ,String price){

        SQLiteDatabase db = this.getWritableDatabase();

        switch (mode){
            case 1:
                ContentValues cv1 = new ContentValues();
                cv1.put(COLUMN_ID_CA, id);
                cv1.put(COLUMN_TITLE_CA,title);
                cv1.put(COLUMN_PARENT_CA,parent);

                long result1 = db.update(TABLE_NAME_CA, cv1, "_id=?", new String[]{String.valueOf(id)});
                break;

            case 2:
                ContentValues cv2 = new ContentValues();
                cv2.put(COLUMN_ID_CB, id);
                cv2.put(COLUMN_TITLE_CB, title);
                cv2.put(COLUMN_PARENT_CB, parent);
                cv2.put(COLUMN_IMG_CB, img);
                long result2 = db.update(TABLE_NAME_CB, cv2, "_id=?", new String[]{String.valueOf(id)});
                break;

            case 3:
                ContentValues cv3 = new ContentValues();
                cv3.put(COLUMN_ID_CC, id);
                cv3.put(COLUMN_TITLE_CC, title);
                cv3.put(COLUMN_PARENT_CC, parent);
                cv3.put(COLUMN_IMG_CC, img);
                cv3.put(COLUMN_DESC_CC, desc);
                cv3.put(COLUMN_PRICE_CC, price);
                long result3 = db.update(TABLE_NAME_CC, cv3, "_id=?", new String[]{String.valueOf(id)});
                break;

        }

    }


}

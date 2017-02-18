package com.example.brian.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class SqliteHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "INVENTORY_TABLE";
    public static final String ITEM = "INVENTORY_ITEM";
    public static final String MODEL_NUMBER = "MODEL_NUMBER";
    public static final String SERIAL_NUMBER = "SERIAL_NUMBER";
    public static final String ID = "UNIQUE_ID";


    private static final String DATABASE_NAME = "INVENTORY_DB";
    private static final int VERSION = 1;
    private static final String CREATE_QUERY = "CREATE TABLE " + TABLE_NAME + "(" + ITEM + " TEXT,"
            + MODEL_NUMBER + " TEXT," + SERIAL_NUMBER + " TEXT," + ID + " TEXT);";

    public SqliteHelper(Context context){

        super(context,DATABASE_NAME,null,VERSION,null);
        Log.e("DATABASE OPERATIONS", "Database created / opened...");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.e("DATABASE OPERATIONS", "TABLE Created...");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
    }



    public boolean insertData(String item, String model_number, String serial_number, String id_number, SQLiteDatabase db){

        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM, item);
        contentValues.put(MODEL_NUMBER, model_number);
        contentValues.put(SERIAL_NUMBER, serial_number);
        contentValues.put(ID, id_number);

        db.insert(TABLE_NAME, null, contentValues);
        Log.e("DATABASE OPERATIONS", "One row inserted...");
       /* long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1){
            return false;
        }
        else{
            return true;
        }
*/
    }

    public boolean removeData(int position){
        SQLiteDatabase databasee = this.getWritableDatabase();
        databasee.delete(TABLE_NAME, ID + "=" + position, null);
        return true;
    }

    public Cursor getData(){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor data = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
                return data;
    }

}

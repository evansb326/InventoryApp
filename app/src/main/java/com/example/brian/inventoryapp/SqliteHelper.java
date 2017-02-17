package com.example.brian.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SqliteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "INVENTORY_DB";
    public static final String TABLE_NAME = "INVENTORY_TABLE";
    public static final int VERSION = 1;
    public static final String ITEM = "INVENTORY_ITEM";
    public static final String MODEL_NUMBER = "MODEL_NUMBER";
    public static final String SERIAL_NUMBER = "SERIAL_NUMBER";
    public static final String ID = "UNIQUE_ID";


    public SqliteHelper(Context context){super(context,DATABASE_NAME,null,1);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME
                    + "INVENTORY_ITEM " + ITEM
                    + " MODEL_NUMBER " + MODEL_NUMBER
                    + " SERIAL_NUMBER " + SERIAL_NUMBER
                    + " UNIQUE_ID " + ID);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
    }

    

    public boolean insertData(String inventory_item, String model_number, String serial_number, String unique_id){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM, inventory_item);
        contentValues.put(MODEL_NUMBER, model_number);
        contentValues.put(SERIAL_NUMBER, serial_number);
        contentValues.put(ID, unique_id);

        long result = database.insert(TABLE_NAME, null, contentValues);

        if(result == -1){
            return false;
        }
        else{
            return true;
        }

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

package com.example.brian.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class SqliteHelper extends SQLiteOpenHelper {

    //Databse version
    private static final int VERSION = 1;

    private static String DB_PATH;
    //Database name
    private static final String DATABASE_NAME = "inventoryManager.db";

    //Inventory table name
    public static final String TABLE_NAME = "InventoryItems";

    //Inventory table column names
    public static final String ITEM = "inventory_item";
    public static final String MODEL_NUMBER = "model_number";
    public static final String SERIAL_NUMBER = "serial_number";
    public static final String ID = "unique_id";


    //This method is what creates the database and insterts the columns using a try catch block
    //and also creates the file path where the database is saved.
    SQLiteDatabase db;
    public SqliteHelper(Context context){

        super(context,DATABASE_NAME,null,VERSION);
        db = null;
        try {
            DB_PATH = context.getFilesDir().getAbsolutePath() + File.separator + DATABASE_NAME;

            File file = new File(DB_PATH);
            if(file.exists())
                db = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
            else {
                db = SQLiteDatabase.openOrCreateDatabase(DB_PATH, null);
                String CREATE_QUERY = "CREATE TABLE " + TABLE_NAME + "("
                        + ITEM + " TEXT,"
                        + MODEL_NUMBER + " TEXT,"
                        + SERIAL_NUMBER + " TEXT,"
                        + ID + " INTEGER PRIMARY KEY AUTOINCREMENT"+ ")";

                db.execSQL(CREATE_QUERY);
            }
        }catch(SQLiteException e){
            e.printStackTrace();
        }

        //Log for Android Monitor and logcat
        Log.e("DATABASE OPERATIONS", "Database created / opened...");

    }
    //Create table
    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.e("DATABASE OPERATIONS", "TABLE Created...");

    }



    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        //Drop older table if existed
        database.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);

        //Create tables again
        onCreate(database);



    }
    //Method that uses the InventoryItem model class to add items to the database
    public void addToDatabase(InventoryItem newItem) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM, newItem.getItem());
        contentValues.put(MODEL_NUMBER, newItem.getModelNumber());
        contentValues.put(SERIAL_NUMBER, newItem.getSerialNumber());
        db.insert(TABLE_NAME, null, contentValues);
        Log.e("DATABASE OPERATIONS", "One row inserted...");
        db.close();

    }

    //Used to remove inventory items
    public boolean removeData(int position) {

        db.delete(TABLE_NAME, ID + "=" + position, null);
        return true;

    }

    //Selects on items in enventory using a Cursor that gets the rawQuery
    // using basic database commands.
    public ArrayList<InventoryItem> getData(){
        ArrayList<InventoryItem> data = new ArrayList<InventoryItem>();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (c != null ) {
            if  (c.moveToFirst()) {
                do {
                    data.add(new InventoryItem(
                            c.getString(c.getColumnIndex(ITEM)),
                            c.getString(c.getColumnIndex(MODEL_NUMBER)),
                            c.getString(c.getColumnIndex(SERIAL_NUMBER)),
                            c.getString(c.getColumnIndex(ID))
                    ));
                }while (c.moveToNext());
            }
        }

        return data;
    }

    public InventoryItem getData(String id) {
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where unique_id = " + id, null);
        InventoryItem item = null;
        if (c.moveToFirst()) {
            item = new InventoryItem(
                            c.getString(c.getColumnIndex(ITEM)),
                            c.getString(c.getColumnIndex(MODEL_NUMBER)),
                            c.getString(c.getColumnIndex(SERIAL_NUMBER)),
                            c.getString(c.getColumnIndex(ID)));
        }
        return item;
    }

    public void modifyItem(InventoryItem item) {
        String query = "UPDATE " + TABLE_NAME +
                        " SET " + MODEL_NUMBER + " = '" + item.getModelNumber() +
                        "', " + SERIAL_NUMBER + " = '" + item.getSerialNumber() +
                        "', " + ITEM + " = '" + item.getItem() +
                        "' WHERE " + ID + " = " + item.getId();
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        c.close();
    }


}

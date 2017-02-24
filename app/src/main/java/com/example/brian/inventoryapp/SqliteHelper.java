package com.example.brian.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class SqliteHelper extends SQLiteOpenHelper {

    //Databse version
    private static final int VERSION = 1;

    private static String DB_PATH;
    //Database name
    private static final String DATABASE_NAME = "inventoryManager";

    //Inventory table name
    public static final String TABLE_NAME = "inventory.db";

    //Inventory table column names
    public static final String ITEM = "inventory_item";
    public static final String MODEL_NUMBER = "model_number";

    public static final String SERIAL_NUMBER = "serial_number";
    public static final String ID = "unique_id";


    public SqliteHelper(Context context){

        super(context,DATABASE_NAME,null,VERSION);

        DB_PATH = context.getFilesDir().getAbsolutePath().replace("files",
                "databases")+ File.separator + DATABASE_NAME;

        Log.e("DATABASE OPERATIONS", "Database created / opened...");

    }
    //Create table
    @Override
    public void onCreate(SQLiteDatabase db) {
         String CREATE_QUERY = "CREATE TABLE" + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ITEM + " TEXT,"
                + MODEL_NUMBER + " TEXT,"
                + SERIAL_NUMBER + " TEXT" + ")";

        db.execSQL(CREATE_QUERY);
        Log.e("DATABASE OPERATIONS", "TABLE Created...");

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        //Drop older table if existed
        database.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);

        //Create tables again
        onCreate(database);


    }

    public void addToDatabase(InventoryItem newItem) {

        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
        //SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ITEM, newItem.getItem());
        contentValues.put(MODEL_NUMBER, newItem.getModelNumber());
        contentValues.put(SERIAL_NUMBER, newItem.getSerialNumber());
        //contentValues.put(ID, newItem.getId());
        database.insert(TABLE_NAME, null, contentValues);
        Log.e("DATABASE OPERATIONS", "One row inserted...");
        database.close();

    }


    public boolean removeData(int position){
        SQLiteDatabase databasee = this.getWritableDatabase();
        databasee.delete(TABLE_NAME, ID + "=" + position, null);
        return true;
    }

    public ArrayList<InventoryItem> getData(){
        //SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DB_PATH + "/" + DATABASE_NAME, null);
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DB_PATH + TABLE_NAME, null);
        ArrayList<InventoryItem> data = new ArrayList<InventoryItem>();
        Cursor c = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (c != null ) {
            if  (c.moveToFirst()) {
                do {
                    data.add(new InventoryItem(
                            c.getString(c.getColumnIndex(ID)),
                            c.getString(c.getColumnIndex(MODEL_NUMBER)),
                            c.getString(c.getColumnIndex(SERIAL_NUMBER)),
                            c.getString(c.getColumnIndex(ITEM))
                    ));
                }while (c.moveToNext());
            }
        }

        return data;
    }

}

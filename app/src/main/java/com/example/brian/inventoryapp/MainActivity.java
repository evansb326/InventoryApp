package com.example.brian.inventoryapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.attr.data;


public class MainActivity extends AppCompatActivity {

    //Create request variable used to get the data from CreateActivity class
    public static final int CREATE_REQUEST = 1;

    //Global variables
    private Button addButton;
    private ListView itemListView;
    private ArrayList<String> arrayList;
    private ArrayAdapter arrayAdapter;
    protected SqliteHelper dbContext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creates a listview to show inventory items
        itemListView = (ListView)findViewById(R.id.itemListView);

        //ArrayList to hold the inventory items
        arrayList = new ArrayList<String>();

        //Instantiating the SqliteHelper class named dbContext
        dbContext = new SqliteHelper(this.getApplicationContext());

        //ArrayList that populates the list view when the app starts using a for each loop
        //The currentData ArrayList uses the getData method thats created in the SqliteHelper class
        ArrayList currentData = dbContext.getData();
        ArrayList<String> currentDataStrings = new ArrayList<>();
        for (Object item : currentData) {
            currentDataStrings.add(item.toString());
        }
        //Adding to the arrayList that I created earlier
        arrayList.addAll(currentDataStrings);

        //The adapter that holds the arrayList and uses
        // the built in simple_list_item_1 format to show inventory items
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        itemListView.setAdapter(arrayAdapter);
        registerForContextMenu(itemListView);

        //Make a button and set the onClickListener to take you to another activity
        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this, CreateActivity.class);
               startActivityForResult(intent, CREATE_REQUEST);
           }

       });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_context_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;
        info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.delete:
                arrayList.remove(info.position);
                dbContext.removeData(info.position);
                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                arrayAdapter.notifyDataSetChanged();
                return true;
            case R.id.edit:

                editItem(info.position);


            default:
                return super.onContextItemSelected(item);

    }
}

    private void editItem(int position) {
        Intent intent = new Intent(MainActivity.this, editTextView.class);
        String listStuff = arrayList.get(position);
        intent.putExtra("listStuff", listStuff);
        Log.i("Position:", listStuff);
        startActivity(intent);

    }

    //The onActivityResult method is how the listview is populated with new inventory
    // items and gets saved to the database also uses an intent thats used to transfer data
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CREATE_REQUEST){
            if(resultCode == RESULT_OK){
                InventoryItem item = (InventoryItem)data.getExtras().get("invItem");

                //String item = data.getStringExtra("data");
                arrayList.add(item.toString());
                arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
                itemListView.setAdapter(arrayAdapter);

                //Add to the db
                SqliteHelper database = new SqliteHelper(this.getApplicationContext());
                database.addToDatabase(item);
            }
        }



    }
}

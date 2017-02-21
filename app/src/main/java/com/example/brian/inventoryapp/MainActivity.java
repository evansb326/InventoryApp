package com.example.brian.inventoryapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static final int CREATE_REQUEST = 1;

    private Button addButton;
    private ListView itemListView;
    private ArrayList<String> arrayList;
    private ArrayAdapter arrayAdapter;
    private SQLiteDatabase newDatabase;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        itemListView = (ListView)findViewById(R.id.itemListView);


        arrayList = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);


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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CREATE_REQUEST){
            if(resultCode == RESULT_OK){
                String item = data.getStringExtra("data");
                arrayList.add(item);
                arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
                itemListView.setAdapter(arrayAdapter);
            }
        }



    }
}

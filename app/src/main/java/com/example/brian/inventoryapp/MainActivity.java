package com.example.brian.inventoryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.layout.simple_list_item_1;


public class MainActivity extends AppCompatActivity {

    Button addButton;
    ListView itemListView;
    public static ArrayList<String> arrayList;
    public static ArrayAdapter arrayAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Bundle editTextData = getIntent().getExtras();

        if(editTextData != null){

            itemListView = (ListView)findViewById(R.id.itemListView);
            String data = editTextData.getString("data");
            arrayList = new ArrayList<String>();
            arrayList.add(data);
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
            itemListView.setAdapter(arrayAdapter);

            if(data != ""){

                    arrayAdapter.notifyDataSetChanged();
            }
        }


        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this, CreateActivity.class);
               startActivity(intent);
           }

       });
    }

}

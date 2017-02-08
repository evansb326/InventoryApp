package com.example.brian.inventoryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button addButton;
    ListView itemListView;
    public static ArrayList<String> arrayList = new ArrayList<>();
    public static ArrayAdapter<String> arrayAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemListView = (ListView)findViewById(R.id.itemListView);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String item = intent.getStringExtra("data");
        arrayList.add(item);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        //arrayAdapter.notifyDataSetChanged();
        //itemListView.getAdapter(arrayAdapter);

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

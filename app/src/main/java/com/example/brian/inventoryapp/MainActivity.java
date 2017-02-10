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


public class MainActivity extends AppCompatActivity {

    Button addButton;
    ListView itemListView;
    public static ArrayList<String> arrayList = new ArrayList<>();
    public static ArrayAdapter<String> arrayAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemListView = (ListView)findViewById(R.id.itemListView);
        Intent intent = getIntent();
        String item = intent.getStringExtra("data");
        arrayList.addAll(Arrays.asList(item));
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        arrayAdapter.add(item);
        //itemListView.setAdapter(arrayAdapter);

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

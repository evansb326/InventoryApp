package com.example.brian.inventoryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.example.brian.inventoryapp.R.id.editTextItem;

public class CreateActivity extends AppCompatActivity {

    EditText editTextItem;
    EditText editTextModel;
    EditText editTextSerial;
    EditText editTextID;
    Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        getIntent();

        editTextItem = (EditText)findViewById(R.id.editTextItem);
        editTextModel = (EditText)findViewById(R.id.editTextModel);
        editTextSerial = (EditText)findViewById(R.id.editTextSerial);
        editTextID = (EditText)findViewById(R.id.editTextID);
        createButton = (Button) findViewById(R.id.createButton);

        createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String item = "";
                item = editTextItem.getText().toString().trim() +
                              editTextModel.getText().toString().trim() +
                              editTextSerial.getText().toString().trim() +
                              editTextID.getText().toString().trim();

                Intent intent = new Intent();
                intent.putExtra("data", item);

                setResult(RESULT_OK, intent);
                finish();
                //startActivity(intent);
            }
        });

    }
}

package com.example.brian.inventoryapp;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateActivity extends AppCompatActivity {

    EditText editTextItem;
    EditText editTextModel;
    EditText editTextSerial;
    EditText editTextID;
    Button createButton;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        //getIntent();

        editTextItem = (EditText)findViewById(R.id.editTextItem);
        editTextModel = (EditText)findViewById(R.id.editTextModel);
        editTextSerial = (EditText)findViewById(R.id.editTextSerial);
        editTextID = (EditText)findViewById(R.id.editTextID);
        createButton = (Button) findViewById(R.id.createButton);

        Intent intent = getIntent();

        id = intent.getStringExtra("id");
        if(id != null && Integer.parseInt(id) >= 0) {
            SqliteHelper database = new SqliteHelper(this.getApplicationContext());
            InventoryItem item = database.getData(id);

            editTextItem.setText(item.getItem());
            editTextModel.setText(item.getModelNumber());
            editTextSerial.setText(item.getSerialNumber());
            editTextID.setText(item.getId());
        }
        intent.getIntExtra("position", 0);

        createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(CreateActivity.this, MainActivity.class);
                InventoryItem newItem = new InventoryItem(
                        editTextItem.getText().toString(),
                        editTextModel.getText().toString(),
                        editTextSerial.getText().toString(),
                        editTextID.getText().toString()
                );

                bundle.putSerializable("invItem", newItem);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();

            }
        });

    }

    private boolean isModified() { return id != null && !id.isEmpty(); }




}

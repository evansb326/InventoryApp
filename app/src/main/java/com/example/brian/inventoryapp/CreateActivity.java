package com.example.brian.inventoryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class CreateActivity extends AppCompatActivity {

    private String id;
    EditText editTextItem;
    EditText editTextModel;
    EditText editTextSerial;
    EditText editTextID;
    Button saveButton;
    ImageButton imageButtonModel;
    ImageButton imageButtonSerial;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);



        editTextItem = (EditText)findViewById(R.id.editTextItem);
        editTextModel = (EditText)findViewById(R.id.editTextModel);
        editTextSerial = (EditText)findViewById(R.id.editTextSerial);
        editTextID = (EditText)findViewById(R.id.editTextID);
        editTextID.setEnabled(false);
        saveButton = (Button) findViewById(R.id.saveButton);
        imageButtonModel = (ImageButton)findViewById(R.id.imageButtonModel);
        imageButtonSerial = (ImageButton)findViewById(R.id.imageButtonSerial);



        imageButtonModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(CreateActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

        imageButtonSerial.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(CreateActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }

        });



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

        saveButton.setOnClickListener(new View.OnClickListener(){
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            if (result.getContents() == null){
                Log.d("CreateActivity", "Cancelled Scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }else {
                Log.d("CreateActivity", "Scanned");
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_SHORT).show();
                editTextSerial.setText(result.getContents());
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

   // private boolean isModified() { return id != null && !id.isEmpty(); }




}

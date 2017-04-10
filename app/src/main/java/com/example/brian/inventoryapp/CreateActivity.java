package com.example.brian.inventoryapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
    static final String SCAN_PACKAGE = "com.google.zxing.client.android.SCAN";



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
               try {
                   int requestCode = 0;
                   Intent modelIntent = new Intent(SCAN_PACKAGE);
                   modelIntent.putExtra("SCAN_FORMATS","CODE_39,CODE_93,CODE_128,DATA_MATRIX,ITF,CODABAR,EAN_13,EAN_8,UPC_A,QR_CODE");
                   startActivityForResult(modelIntent, requestCode);
               }catch (Exception e){
                   Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                   Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
                   startActivity(marketIntent);
               }

            }
        });

        imageButtonSerial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int requestCode = 1;
                    Intent serialIntent = new Intent(SCAN_PACKAGE);
                    serialIntent.putExtra("SCAN_FORMATS","CODE_39,CODE_93,CODE_128,DATA_MATRIX,ITF,CODABAR,EAN_13,EAN_8,UPC_A,QR_CODE");
                    startActivityForResult(serialIntent, requestCode);
                }catch (Exception e){
                    Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
                    startActivity(marketIntent);
                }

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

                String checkTextItem = editTextItem.getText().toString();
                String checkTextModel = editTextModel.getText().toString();
                String checkTextSerial = editTextSerial.getText().toString();

                if (TextUtils.isEmpty((checkTextItem))){
                    editTextItem.setError("The item name cannot be empty.");
                    return;
                }else if (TextUtils.isEmpty(checkTextModel)){
                    editTextModel.setError("The model number cannot be empty.");
                    return;
                }else if (TextUtils.isEmpty(checkTextSerial)){
                    editTextSerial.setError("The serial number cannot be empty.");
                    return;
                }

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

        switch (requestCode){
            case 0:
                String modelContents = data.getStringExtra("SCAN_RESULT");
                editTextModel.setText(modelContents);
                break;
            case 1:
                String serialContents = data.getStringExtra("SCAN_RESULT");
                editTextSerial.setText(serialContents);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // private boolean isModified() { return id != null && !id.isEmpty(); }


}

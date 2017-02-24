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
                Bundle b = new Bundle();
                InventoryItem newItem = new InventoryItem(
                        editTextItem.getText().toString(),
                        editTextModel.getText().toString(),
                        editTextSerial.getText().toString(),
                        editTextID.getText().toString()
                );
                Intent intent = new Intent();
                b.putSerializable("b", newItem);
                intent.putExtras(b);
                intent.putExtra("InventoryItem", newItem);
                setResult(RESULT_OK, intent);

                finish();
/*


                String item = "";
                item = "Item: " +editTextItem.getText().toString()  + "\n" +
                        "Model Number: " + editTextModel.getText().toString() + "\n" +
                        "Serial Number: " + editTextSerial.getText().toString() + "\n" +
                        "ID Number: " + editTextID.getText().toString();


                Intent intent = new Intent();
                intent.putExtra("data", item);
                setResult(RESULT_OK, intent);

                String inventory_item = editTextItem.getText().toString();
                String model = editTextModel.getText().toString();
                String serial = editTextSerial.getText().toString();
                String id = editTextID.getText().toString();

                finish();
*/
            }
        });

    }




}

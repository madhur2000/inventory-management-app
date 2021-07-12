package com.example.inventorymanagementsystem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.inventorymanagementsystem.Data.InventoryManagementSystemContract;
import com.example.inventorymanagementsystem.Data.InventoryManagementSystemDBHelper;
import com.github.drjacky.imagepicker.ImagePicker;

public class AddActivity extends AppCompatActivity {

    ImageView productImageImageView;
    EditText productNameEditText;
    EditText quantityEditText;
    EditText pricePerUnitEditText;

    String imageURIString;

    String[] completeProjection = {
            InventoryManagementSystemContract.InventoryManagementSystemEntry.PRODUCT_ID_COLUMN_NAME,
            InventoryManagementSystemContract.InventoryManagementSystemEntry.IMAGE_URL_COLUMN_NAME,
            InventoryManagementSystemContract.InventoryManagementSystemEntry.PRODUCT_NAME_COLUMN_NAME,
            InventoryManagementSystemContract.InventoryManagementSystemEntry.QUANTITY_COLUMN_NAME,
            InventoryManagementSystemContract.InventoryManagementSystemEntry.PRICE_PER_UNIT_COLUMN_NAME
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        productImageImageView = findViewById(R.id.product_image_view);
        productNameEditText = findViewById(R.id.product_name_edit_text);
        quantityEditText = findViewById(R.id.product_quantity_edit_text);
        pricePerUnitEditText = findViewById(R.id.price_per_unit_edit_text);

        productImageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImagePicker.Companion.with(AddActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
//                        .cropOval()	    		//Allow dimmed layer to have a circle inside
//                        .cropFreeStyle()	    //Let the user to resize crop bounds
//                        .galleryOnly()          //We have to define what image provider we want to use
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
//                        .createIntent()
                        .compress(1024)
                        .start();

            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri imageURI = data.getData();

        productImageImageView.setImageURI(imageURI);

//        getting imageURI of selected image in string form
        imageURIString = data.getDataString();

//        Log.v("Image URI String", data.getDataString());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.add_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:

                String productName = productNameEditText.getText().toString().trim();
                String quantityString = quantityEditText.getText().toString().trim();
                String pricePerUnit = pricePerUnitEditText.getText().toString().trim();

                if(!productName.equals("") && !quantityString.equals("") && !pricePerUnit.equals("")) {

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(InventoryManagementSystemContract.InventoryManagementSystemEntry.IMAGE_URL_COLUMN_NAME, imageURIString);
                    contentValues.put(InventoryManagementSystemContract.InventoryManagementSystemEntry.PRODUCT_NAME_COLUMN_NAME, productName);
                    contentValues.put(InventoryManagementSystemContract.InventoryManagementSystemEntry.QUANTITY_COLUMN_NAME, Integer.parseInt(quantityString));
                    contentValues.put(InventoryManagementSystemContract.InventoryManagementSystemEntry.PRICE_PER_UNIT_COLUMN_NAME, Double.parseDouble(pricePerUnit));

                    InventoryManagementSystemDBHelper dbHelper = new InventoryManagementSystemDBHelper(this);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.insert(InventoryManagementSystemContract.InventoryManagementSystemEntry.INVENTORY_TABLE_NAME, null, contentValues);

                    Cursor cursor = db.query(InventoryManagementSystemContract.InventoryManagementSystemEntry.INVENTORY_TABLE_NAME, completeProjection, null, null, null, null, null);
//                Toast.makeText(this, cursor.getCount() + "", Toast.LENGTH_SHORT).show();

//                while(cursor.moveToNext()){
//                    int prodId = cursor.getInt(0);
//                    String imageURL = cursor.getString(1);
//                    String prodName = cursor.getString(2);
//                    int quant = cursor.getInt(3);
//                    double price = cursor.getDouble(4);
//
//                    String toPrint = prodId + "\n" + imageURL + "\n" + prodName + "\n" + quant + "\n" + price;
//
//                    Toast.makeText(this, toPrint, Toast.LENGTH_SHORT).show();
//
//                }
//
                    cursor.close();

                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);

                    finish();

                }
                else{
                    Toast.makeText(this, "Please Enter All Details!", Toast.LENGTH_SHORT).show();
                }

                break;

        }

        return super.onOptionsItemSelected(item);
    }

}

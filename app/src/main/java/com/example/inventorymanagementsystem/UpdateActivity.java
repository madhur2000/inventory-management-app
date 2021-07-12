package com.example.inventorymanagementsystem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.inventorymanagementsystem.Data.InventoryManagementSystemContract;
import com.example.inventorymanagementsystem.Data.InventoryManagementSystemDBHelper;
import com.github.drjacky.imagepicker.ImagePicker;

public class UpdateActivity extends AppCompatActivity {



    ImageView productImageImageView;
    EditText productNameEditText;
    EditText quantityEditText;
    EditText pricePerUnitEditText;

    String imageURIString;

    Product product;

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
        setContentView(R.layout.activity_update);


        product = (Product) getIntent().getSerializableExtra("Product");

        productImageImageView = findViewById(R.id.product_image_view_update);
        productNameEditText = findViewById(R.id.product_name_edit_text_update);
        quantityEditText = findViewById(R.id.product_quantity_edit_text_update);
        pricePerUnitEditText = findViewById(R.id.price_per_unit_edit_text_update);

        if(product.imageURIString != null){
            productImageImageView.setImageURI(Uri.parse(product.imageURIString));
            imageURIString = product.imageURIString;
        }
        productNameEditText.setText(product.productName);
        quantityEditText.setText(product.quantity + "");
        pricePerUnitEditText.setText(product.pricePerUnit + "");


        productImageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImagePicker.Companion.with(UpdateActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
//                        .cropOval()	    		//Allow dimmed layer to have a circle inside
//                        .cropFreeStyle()	    //Let the user to resize crop bounds
//                        .galleryOnly()          //We have to define what image provider we want to use
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
//                        .createIntent()
                        .compress(750)
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
        getMenuInflater().inflate(R.menu.update_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:

                InventoryManagementSystemDBHelper dbHelper = new InventoryManagementSystemDBHelper(this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues contentValues = new ContentValues();
                contentValues.put(InventoryManagementSystemContract.InventoryManagementSystemEntry.IMAGE_URL_COLUMN_NAME, imageURIString);
                contentValues.put(InventoryManagementSystemContract.InventoryManagementSystemEntry.PRODUCT_NAME_COLUMN_NAME, productNameEditText.getText().toString().trim());
                contentValues.put(InventoryManagementSystemContract.InventoryManagementSystemEntry.QUANTITY_COLUMN_NAME,
                        Integer.parseInt(quantityEditText.getText().toString().trim()));
                contentValues.put(InventoryManagementSystemContract.InventoryManagementSystemEntry.PRICE_PER_UNIT_COLUMN_NAME,
                        Double.parseDouble(pricePerUnitEditText.getText().toString().trim()));

                db.update(InventoryManagementSystemContract.InventoryManagementSystemEntry.INVENTORY_TABLE_NAME, contentValues, InventoryManagementSystemContract.InventoryManagementSystemEntry.PRODUCT_ID_COLUMN_NAME + " = ?", new String[]{product.productId + ""});

//                Intent intent2 = new Intent(UpdateItemActivity.this, UpdateActivity.class);
//                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK); //this will always start your activity as a new task
//                startActivity(intent2);

                Intent intent = new Intent();

//                Product prod = new Product(product.productId, imageURIString, productNameEditText.getText().toString().trim(),
//                        Integer.parseInt(quantityEditText.getText().toString().trim()), Double.parseDouble(pricePerUnitEditText.getText().toString().trim()));
//
//                intent.putExtra("Product", prod);
                setResult(RESULT_OK, intent);

                finish();

                break;

            case R.id.action_delete:

                new AlertDialog.Builder(UpdateActivity.this)
                        .setTitle("Delete")
                        .setIcon(R.drawable.ic_delete_black_24dp)
                        .setMessage("Are you sure you want to Delete this Product?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                InventoryManagementSystemDBHelper dbHelper = new InventoryManagementSystemDBHelper(UpdateActivity.this);

                                SQLiteDatabase db = dbHelper.getReadableDatabase();

                                Cursor cursor = db.query(InventoryManagementSystemContract.InventoryManagementSystemEntry.INVENTORY_TABLE_NAME, completeProjection, null, null, null, null, null);
                                try{
                                    while(cursor.moveToNext()){

                                        int productId = cursor.getInt(0);

                                        if(product.productId == productId){
                                            db.delete(InventoryManagementSystemContract.InventoryManagementSystemEntry.INVENTORY_TABLE_NAME, InventoryManagementSystemContract.InventoryManagementSystemEntry.PRODUCT_ID_COLUMN_NAME + "=" + productId, null);
                                            break;
                                        }

                                    }
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    if (cursor != null) {
                                        cursor.close();
                                    }
                                }

                                Intent intent = new Intent();
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).show();
                break;


        }

        return super.onOptionsItemSelected(item);
    }

}

package com.example.inventorymanagementsystem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.inventorymanagementsystem.Data.InventoryManagementSystemContract;
import com.example.inventorymanagementsystem.Data.InventoryManagementSystemDBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    int position = 0;

    ProductAdapter adapter;

    ArrayList<Product> productList;

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
        setContentView(R.layout.activity_main);

        productList = new ArrayList<>();

        InventoryManagementSystemDBHelper dbHelper = new InventoryManagementSystemDBHelper(this);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(InventoryManagementSystemContract.InventoryManagementSystemEntry.INVENTORY_TABLE_NAME, completeProjection, null, null, null, null, null);

        try{
            while(cursor.moveToNext()){

                int productId = cursor.getInt(0);
                String imageURI = cursor.getString(1);
                String productName = cursor.getString(2);
                int quantity = cursor.getInt(3);
                double pricePerUnit = cursor.getDouble(4);

                Product product = new Product(productId, imageURI, productName, quantity, pricePerUnit);

                productList.add(product);

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        GridView gridView = findViewById(R.id.grid_view);
        adapter = new ProductAdapter(this, R.color.colorAccent, productList);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                position = i;

                Intent intent = new Intent(view.getContext(), UpdateActivity.class);

                Product product = productList.get(i);

                intent.putExtra("Product", product);
                startActivityForResult(intent, 2);

            }
        });



        FloatingActionButton fab = findViewById(R.id.add_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, 1);
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {

                productList.clear();
                InventoryManagementSystemDBHelper dbHelper = new InventoryManagementSystemDBHelper(this);

                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursor = db.query(InventoryManagementSystemContract.InventoryManagementSystemEntry.INVENTORY_TABLE_NAME, completeProjection, null, null, null, null, null);

                try{
                    while(cursor.moveToNext()){

                        int productId = cursor.getInt(0);
                        String imageURI = cursor.getString(1);
                        String productName = cursor.getString(2);
                        int quantity = cursor.getInt(3);
                        double pricePerUnit = cursor.getDouble(4);

                        Product product = new Product(productId, imageURI, productName, quantity, pricePerUnit);

                        productList.add(product);

                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
                GridView gridView = findViewById(R.id.grid_view);
                adapter = new ProductAdapter(this, R.color.colorAccent, productList);

                gridView.setAdapter(adapter);

            }
        }
        else if(requestCode == 2){
            if(resultCode == RESULT_OK) {

                productList.clear();
                InventoryManagementSystemDBHelper dbHelper = new InventoryManagementSystemDBHelper(this);

                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursor = db.query(InventoryManagementSystemContract.InventoryManagementSystemEntry.INVENTORY_TABLE_NAME, completeProjection, null, null, null, null, null);

                try{
                    while(cursor.moveToNext()){

                        int productId = cursor.getInt(0);
                        String imageURI = cursor.getString(1);
                        String productName = cursor.getString(2);
                        int quantity = cursor.getInt(3);
                        double pricePerUnit = cursor.getDouble(4);

                        Product product = new Product(productId, imageURI, productName, quantity, pricePerUnit);

                        productList.add(product);

                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
                GridView gridView = findViewById(R.id.grid_view);
                adapter = new ProductAdapter(this, R.color.colorAccent, productList);

                gridView.setAdapter(adapter);

            }
        }
    }
}

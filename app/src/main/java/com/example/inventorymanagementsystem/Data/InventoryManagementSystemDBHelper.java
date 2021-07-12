package com.example.inventorymanagementsystem.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class InventoryManagementSystemDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "inventory.db";
    public static final int DATABASE_VERSION = 1;

    String SQL_CREATE_STOCK_TABLE = "CREATE TABLE " + InventoryManagementSystemContract.InventoryManagementSystemEntry.INVENTORY_TABLE_NAME + "("
            + InventoryManagementSystemContract.InventoryManagementSystemEntry.PRODUCT_ID_COLUMN_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + InventoryManagementSystemContract.InventoryManagementSystemEntry.IMAGE_URL_COLUMN_NAME + " TEXT,"
            + InventoryManagementSystemContract.InventoryManagementSystemEntry.PRODUCT_NAME_COLUMN_NAME + " TEXT NOT NULL,"
            + InventoryManagementSystemContract.InventoryManagementSystemEntry.QUANTITY_COLUMN_NAME + " INTEGER NOT NULL,"
            + InventoryManagementSystemContract.InventoryManagementSystemEntry.PRICE_PER_UNIT_COLUMN_NAME + " REAL NOT NULL);";


    public InventoryManagementSystemDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(SQL_CREATE_STOCK_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

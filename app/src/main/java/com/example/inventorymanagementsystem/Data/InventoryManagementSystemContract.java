package com.example.inventorymanagementsystem.Data;

import android.provider.BaseColumns;

public final class InventoryManagementSystemContract {

    public static final class InventoryManagementSystemEntry implements BaseColumns{

        private InventoryManagementSystemEntry(){}
        public static final String INVENTORY_TABLE_NAME = "stockTable";

        public static final String PRODUCT_ID_COLUMN_NAME = "productId";

        public static final String IMAGE_URL_COLUMN_NAME = "imageURL";

        public static final String PRODUCT_NAME_COLUMN_NAME = "productName";

        public static final String QUANTITY_COLUMN_NAME = "quantity";

        public static final String PRICE_PER_UNIT_COLUMN_NAME = "pricePerUnit";

    }

}

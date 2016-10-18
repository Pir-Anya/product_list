package com.example.anuta_list;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper implements IDatabaseHandler {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "shoplist";
    private static final String TABLE_LIST = "list";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SECT_NO = "parent";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ProductS_TABLE = "CREATE TABLE " + TABLE_LIST + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_SECT_NO + " TEXT" + ")";
        db.execSQL(CREATE_ProductS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIST);

        onCreate(db);
    }

    @Override
    public void addProduct(Product Product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, Product.getName());
        values.put(KEY_SECT_NO, 1);

        db.insert(TABLE_LIST, null, values);
        db.close();
    }

    @Override
    public Product getProduct(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_LIST, new String[] { KEY_ID,
                KEY_NAME, KEY_SECT_NO }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        Product Product = new Product(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));

        return Product;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> ProductList = new ArrayList<Product>();
        String selectQuery = "SELECT  * FROM " + TABLE_LIST;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Product Product = new Product();
                Product.setID(Integer.parseInt(cursor.getString(0)));
                Product.setName(cursor.getString(1));
                Product.setPhoneNumber(cursor.getString(2));
                ProductList.add(Product);
            } while (cursor.moveToNext());
        }

        return ProductList;
    }

    @Override
    public int updateProduct(Product Product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, Product.getName());
        values.put(KEY_SECT_NO, Product.getPhoneNumber());

        return db.update(TABLE_LIST, values, KEY_ID + " = ?",
                new String[] { String.valueOf(Product.getID()) });
    }

    @Override
    public void deleteProduct(Product Product) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LIST, KEY_ID + " = ?", new String[] { String.valueOf(Product.getID()) });
        db.close();
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LIST, null, null);
        db.close();
    }

    @Override
    public int getProductsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LIST;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }
}

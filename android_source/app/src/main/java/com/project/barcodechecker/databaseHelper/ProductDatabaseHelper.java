package com.project.barcodechecker.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.project.barcodechecker.models.History;
import com.project.barcodechecker.models.Product;

import java.util.ArrayList;

/**
 * Created by Lenovo on 15/10/2017.
 */

public class ProductDatabaseHelper extends SQLiteOpenHelper {

    // Phiên bản
    private static final int DATABASE_VERSION = 1;


    // Tên cơ sở dữ liệu.
    private static final String DATABASE_NAME = "BarcodeData.db";

    public ProductDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public ProductDatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    //name table
    private static final String TABLE_PRODUCT = "Product";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PRODUCT_ID = "product_id";
    private static final String COLUMN_PRODUCT_CATEGORY_ID = "category_id";
    private static final String COLUMN_PRODUCT_NAME = "name";
    private static final String COLUMN_PRODUCT_PRICE = "price";
    private static final String COLUMN_PRODUCT_COUNTRY = "country";
    private static final String COLUMN_PRODUCT_ADDRESS = "address";
    private static final String COLUMN_PRODUCT_PHONE = "phone";
    private static final String COLUMN_PRODUCT_EMAIL = "email";
    private static final String COLUMN_PRODUCT_CODE = "code";
    private static final String COLUMN_PRODUCT_DESCRIPTION = "description";
    private static final String COLUMN_PRODUCT_Image = "image";


    @Override
    public void onCreate(SQLiteDatabase db) {
        String script = "CREATE TABLE " + TABLE_PRODUCT + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + COLUMN_PRODUCT_ID + " INTEGER,"
                + COLUMN_PRODUCT_CATEGORY_ID + " INTEGER,"
                + COLUMN_PRODUCT_NAME + " TEXT,"
                + COLUMN_PRODUCT_PRICE + " DOUBLE,"
                + COLUMN_PRODUCT_COUNTRY + " TEXT,"
                + COLUMN_PRODUCT_ADDRESS + " TEXT,"
                + COLUMN_PRODUCT_PHONE + " TEXT,"
                + COLUMN_PRODUCT_EMAIL + " TEXT,"
                + COLUMN_PRODUCT_CODE + " TEXT,"
                + COLUMN_PRODUCT_DESCRIPTION + " TEXT,"
                +COLUMN_PRODUCT_Image + " TEXT"+")";
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        // Và tạo lại.
        onCreate(db);
    }
//
//    public void createDefaultToDoIfNeed()  {
//        History history = new History("haha","2143244325435");
//        History history2 = new History("haha","32435435");
//        this.addHistory(history);
//        this.addHistory(history2);
//    }


    public ArrayList<Product> getAllProducts() {
        // Log.i(TAG, "MyDatabaseHelper.getAllNotes ... " );

        ArrayList<Product> products = new ArrayList<Product>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT +" ORDER BY "+ COLUMN_ID+" DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setIdDatabase(cursor.getInt(0));
                product.setId(cursor.getInt(1));
                product.setCategoryID(cursor.getInt(2));
                product.setName(cursor.getString(3));
                product.setPrice(cursor.getDouble(4));
                product.setCountry(cursor.getString(5));
                product.setAddress(cursor.getString(6));
                product.setPhone(cursor.getString(7));
                product.setEmail(cursor.getString(8));
                product.setCode(cursor.getString(9));
                product.setDescription(cursor.getString(10));
                product.setImgDefault(cursor.getString(11));
                // Thêm vào danh sách.
                products.add(product);
            } while (cursor.moveToNext());
        }

        // return note list
        return products;
    }


    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_ID , product.getId());
        values.put(COLUMN_PRODUCT_CATEGORY_ID , product.getCategoryID());
        values.put(COLUMN_PRODUCT_NAME, product.getName());
        values.put(COLUMN_PRODUCT_PRICE, product.getPrice());
        values.put(COLUMN_PRODUCT_COUNTRY, product.getCountry());
        values.put(COLUMN_PRODUCT_ADDRESS, product.getAddress());
        values.put(COLUMN_PRODUCT_PHONE, product.getPhone());
        values.put(COLUMN_PRODUCT_EMAIL, product.getEmail());
        values.put(COLUMN_PRODUCT_CODE, product.getCode());
        values.put(COLUMN_PRODUCT_DESCRIPTION, product.getDescription());
        values.put(COLUMN_PRODUCT_Image, product.getImgDefault());
        // Insert 1 row to database.
        db.insert(TABLE_PRODUCT, null, values);
        // close connect database
        db.close();
    }

    public void deleteProduct(int id) {
        //Log.i(TAG, "MyDatabaseHelper.updateNote ... " + note.getNoteTitle() );

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCT, COLUMN_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }




}

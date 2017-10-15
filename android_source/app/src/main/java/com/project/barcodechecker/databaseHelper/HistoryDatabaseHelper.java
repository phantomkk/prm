package com.project.barcodechecker.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.project.barcodechecker.models.History;

import java.util.ArrayList;

/**
 * Created by Lenovo on 15/10/2017.
 */

public class HistoryDatabaseHelper extends SQLiteOpenHelper {

    // Phiên bản
    private static final int DATABASE_VERSION = 1;


    // Tên cơ sở dữ liệu.
    private static final String DATABASE_NAME = "ToDoList";

    public HistoryDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public HistoryDatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    //name table
    private static final String TABLE_HISTORY = "Todo";
    private static final String COLUMN_HISTORY_ID = "Id";
    private static final String COLUMN_PRODUCT_NAME = "Product_Name";
    private static final String COLUMN_PRODUCT_CODE = "Product_Code";
    private static final String COLUMN_PRODUCT_DATE = "Date";

    @Override
    public void onCreate(SQLiteDatabase db) {
        String script = "CREATE TABLE " + TABLE_HISTORY + "("
                + COLUMN_HISTORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + COLUMN_PRODUCT_NAME + " TEXT,"
                + COLUMN_PRODUCT_CODE + " TEXT,"
                + COLUMN_PRODUCT_DATE + " TEXT"+")";
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        // Và tạo lại.
        onCreate(db);
    }

    public void createDefaultToDoIfNeed()  {

        History history = new History("haha","2143244325435");
        History history2 = new History("haha","32435435");
        this.addHistory(history);
        this.addHistory(history2);
    }


    public ArrayList<History> getAllHistories() {
        // Log.i(TAG, "MyDatabaseHelper.getAllNotes ... " );

        ArrayList<History> histories = new ArrayList<History>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_HISTORY +" ORDER BY "+ COLUMN_HISTORY_ID+" DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                History history = new History();
                history.setId(cursor.getInt(0));
                history.setProductName(cursor.getString(1));
                history.setProductCode(cursor.getString(2));
                history.setDatetime(cursor.getString(3));
                // Thêm vào danh sách.
                histories.add(history);
            } while (cursor.moveToNext());
        }

        // return note list
        return histories;
    }


    public void addHistory(History history) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_NAME, history.getProductName());
        values.put(COLUMN_PRODUCT_CODE, history.getProductCode());
        values.put(COLUMN_PRODUCT_DATE, history.getDatetime());
        // Insert 1 row to database.
        db.insert(TABLE_HISTORY, null, values);
        // close connect database
        db.close();
    }

    public void deleteNote(int id) {
        //Log.i(TAG, "MyDatabaseHelper.updateNote ... " + note.getNoteTitle() );

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HISTORY, COLUMN_HISTORY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }




}

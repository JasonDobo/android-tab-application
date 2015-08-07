package com.nbkuk.tabapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_COMMENTS = "tbl1";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TASK = "task";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_DATETIME = "date";

    private static final String[] COLUMNS = {COLUMN_ID,COLUMN_TASK,COLUMN_STATUS,COLUMN_DATETIME};

    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table " + TABLE_COMMENTS + "("
            + COLUMN_ID + " integer primary key autoincrement not null unique, "
            + COLUMN_TASK + " text not null, "
            + COLUMN_STATUS + " integer, "
            + COLUMN_DATETIME + " integer)";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        onCreate(db);
    }

    public void insertData(String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        String time = String.valueOf(System.currentTimeMillis());

        String cmd = "INSERT INTO " + TABLE_COMMENTS + " (" +
                COLUMN_TASK + ", " + COLUMN_STATUS + ", " + COLUMN_DATETIME + ") VALUES (" +
                "'" + task + "', 0, " + time + ")";
        db.execSQL(cmd);
    }

    public void updateData(int id, String task) {
        // Get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK, task);

        // 3. updating row
        int i = db.update(TABLE_COMMENTS, //table
                values, // column/value
                COLUMN_ID+" = ?", // selections
                new String[] { String.valueOf(id) }); //selection args

        // 4. close
        db.close();
    }

    public void deleteData(int id) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_COMMENTS, //table name
                COLUMN_ID+" = ?",  // selections
                new String[] { String.valueOf(id) }); //selections args

        // 3. close
        db.close();
    }

    public Cursor getData(int id) {
        // Get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
        // Build query
        Cursor cursor = db.query(TABLE_COMMENTS, // a. table
                        COLUMNS, // b. column names
                        " _id = ?", // c. selections
                        new String[]{String.valueOf(id)}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we get multiple results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_COMMENTS + ";", null);
        return cursor;
    }


}

package com.nbkuk.tabapplication;

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
        String s = String.valueOf(System.currentTimeMillis());
        // TODO remove after testing

        String cmd = "INSERT INTO " + TABLE_COMMENTS + " (" +
                COLUMN_TASK + ", " + COLUMN_STATUS + ", " + COLUMN_DATETIME + ") VALUES (" +
                "'Vegetables', 0, " + s + ")";
        database.execSQL(cmd);

        cmd = "INSERT INTO " + TABLE_COMMENTS + " (" +
                COLUMN_TASK + ", " + COLUMN_STATUS + ", " + COLUMN_DATETIME + ") VALUES (" +
                "'Fruit', 0, " + s + ")";
        database.execSQL(cmd);

        cmd = "INSERT INTO " + TABLE_COMMENTS + " (" +
                COLUMN_TASK + ", " + COLUMN_STATUS + ", " + COLUMN_DATETIME + ") VALUES (" +
                "'Chicken', 0, " + s + ")";
        database.execSQL(cmd);
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

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_COMMENTS + ";", null);
        return c;
    }
}

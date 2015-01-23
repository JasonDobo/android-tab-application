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

    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table " + TABLE_COMMENTS + "("
            + COLUMN_ID + " integer primary key autoincrement not null unique, "
            + COLUMN_TASK + " text not null, "
            + COLUMN_STATUS + " integer)";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);

        database.execSQL("INSERT INTO " + TABLE_COMMENTS + " (" + COLUMN_TASK + ") VALUES ('Vegetables')");
        database.execSQL("INSERT INTO " + TABLE_COMMENTS + " (" + COLUMN_TASK + ") VALUES ('Fruit')");
        database.execSQL("INSERT INTO " + TABLE_COMMENTS + " (" + COLUMN_TASK + ") VALUES ('Chicken')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        onCreate(db);
    }

    public Cursor GetAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_COMMENTS + ";", null);
        return c;
    }
}

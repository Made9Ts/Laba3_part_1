package com.example.laba3_part_1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "students.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Одногруппники";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_FIO = "ФИО";
    public static final String COLUMN_TIME = "Время_добавления";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FIO + " TEXT NOT NULL,"
                + COLUMN_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void clearAndInsertSampleData(SQLiteDatabase db) {
        db.execSQL("DELETE FROM " + TABLE_NAME);
        for (int i = 1; i <= 5; i++) {
            db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COLUMN_FIO + ") VALUES ('Студент " + i + "')");
        }
    }
}

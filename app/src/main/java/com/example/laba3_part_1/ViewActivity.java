package com.example.laba3_part_1;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> studentList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();
        listView = findViewById(R.id.listView);
        studentList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
        listView.setAdapter(adapter);

        loadRecords();
    }

    private void loadRecords() {
        try (Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME, null)) {
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range")
                    String fio = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FIO));
                    @SuppressLint("Range")
                    String addedTime = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TIME));
                    studentList.add(fio + " - " + addedTime);
                } while (cursor.moveToNext());
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null && db.isOpen()) {
            db.close();
        }
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}

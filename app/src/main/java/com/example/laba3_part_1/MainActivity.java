package com.example.laba3_part_1;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        // Инициализируем базу данных примерными данными
        dbHelper.clearAndInsertSampleData(db);

        // Настройка кнопок
        Button buttonView = findViewById(R.id.button2);
        Button buttonAdd = findViewById(R.id.button);
        Button buttonUpdate = findViewById(R.id.button3);

        buttonView.setOnClickListener(v -> openViewActivity());
        buttonAdd.setOnClickListener(v -> addNewStudent());
        buttonUpdate.setOnClickListener(v -> updateLastStudent());
    }

    private void openViewActivity() {
        Intent intent = new Intent(MainActivity.this, ViewActivity.class);
        startActivity(intent);
    }

    private void addNewStudent() {
        try {
            db.execSQL("INSERT INTO " + DatabaseHelper.TABLE_NAME + " (" + DatabaseHelper.COLUMN_FIO + ") VALUES ('Новый Студент')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateLastStudent() {
        try {
            db.execSQL("UPDATE " + DatabaseHelper.TABLE_NAME + " SET " + DatabaseHelper.COLUMN_FIO +
                    " = 'Иванов Иван Иванович' WHERE " + DatabaseHelper.COLUMN_ID +
                    " = (SELECT MAX(" + DatabaseHelper.COLUMN_ID + ") FROM " + DatabaseHelper.TABLE_NAME + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}

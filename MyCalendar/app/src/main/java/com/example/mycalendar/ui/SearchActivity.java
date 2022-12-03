package com.example.mycalendar.ui;

import static com.example.mycalendar.DiaryDatabase.DATABASE_NAME;
import static com.example.mycalendar.DiaryDatabase.TABLE_CONTACT_INFO;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.TextView;

import com.example.mycalendar.DataBase;
import com.example.mycalendar.R;

public class SearchActivity extends AppCompatActivity {
    TextView textView8;
    DataBase dataBase = new DataBase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        textView8 = findViewById(R.id.textView);

        dataBase.openDatabase(this, DATABASE_NAME);//데이터베이스 이름 설정
        dataBase.createTable(TABLE_CONTACT_INFO);
        dataBase.insertRecord(TABLE_CONTACT_INFO);//테이블 이름설정
        dataBase.selectData(TABLE_CONTACT_INFO); //테이블설정
    }
}
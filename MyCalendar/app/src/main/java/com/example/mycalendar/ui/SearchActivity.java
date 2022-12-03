package com.example.mycalendar.ui;

import static com.example.mycalendar.DataBase.DATABASE_NAME;
import static com.example.mycalendar.DataBase.TABLE_CONTACT_INFO;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mycalendar.DataBase;
import com.example.mycalendar.R;
import com.example.mycalendar.databinding.ActivitySearchBinding;

public class SearchActivity extends AppCompatActivity {
    DataBase dataBase = new DataBase();
    private ActivitySearchBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_search);
        dataBase.openDatabase(this, DATABASE_NAME);//데이터베이스 이름 설정
        dataBase.createContactTable(TABLE_CONTACT_INFO);
        dataBase.insertContactRecord(TABLE_CONTACT_INFO);//테이블 이름설정
        dataBase.selectData(TABLE_CONTACT_INFO); //테이블설정
    }
}
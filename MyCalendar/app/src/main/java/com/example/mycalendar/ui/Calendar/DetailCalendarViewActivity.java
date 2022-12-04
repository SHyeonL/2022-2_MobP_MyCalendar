package com.example.mycalendar.ui.Calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.mycalendar.databinding.ActivityDetailCalendarViewBinding;

public class DetailCalendarViewActivity extends AppCompatActivity {

    Intent intent;
    private ActivityDetailCalendarViewBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailCalendarViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intent = getIntent();
        String id = intent.getStringExtra("id");
        Log.d("db id", id);
        String subject = intent.getStringExtra("subject");
        String content = intent.getStringExtra("content");
        String date = intent.getStringExtra("date");

        binding.textDetailSubject.setText(subject);
        binding.textDetailContents.setText(content);
        binding.textDetailDate.setText(date);
    }
}
package com.example.mycalendar.ui.Calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mycalendar.databinding.ActivityDetailContactViewBinding;

public class DetailCalendarViewActivity extends AppCompatActivity {

    private ActivityDetailContactViewBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailContactViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
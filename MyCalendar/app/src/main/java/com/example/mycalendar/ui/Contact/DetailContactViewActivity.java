package com.example.mycalendar.ui.Contact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.mycalendar.R;
import com.example.mycalendar.databinding.ActivityDetailContactViewBinding;

public class DetailContactViewActivity extends AppCompatActivity {

    Intent intent;
    private ActivityDetailContactViewBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailContactViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intent = getIntent();
        String id = intent.getStringExtra("id");
        Log.d("db id", id);
        String name = intent.getStringExtra("name");
        String number = intent.getStringExtra("number");

        binding.textDetailName.setText(name);
        binding.textDetailNumber.setText(number);
    }
}
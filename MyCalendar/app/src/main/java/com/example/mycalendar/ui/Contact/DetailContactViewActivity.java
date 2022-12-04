package com.example.mycalendar.ui.Contact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.modify_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                break;
            case R.id.action_delete:
                AlertDialog.Builder menu = new AlertDialog.Builder(this);
                menu.setTitle("연락처 삭제");
                menu.setMessage("정말로 연락처를 삭제하시겠습니까?");
                menu.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        onBackPressed();
                    }
                });
                menu.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                menu.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
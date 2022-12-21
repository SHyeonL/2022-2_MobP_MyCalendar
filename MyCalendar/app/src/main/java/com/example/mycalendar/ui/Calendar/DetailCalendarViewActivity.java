package com.example.mycalendar.ui.Calendar;

import static com.example.mycalendar.DataBase.DATABASE_NAME;
import static com.example.mycalendar.DataBase.TABLE_CONTACT_INFO;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.example.mycalendar.DataBase;
import com.example.mycalendar.R;
import com.example.mycalendar.databinding.ActivityDetailCalendarViewBinding;

import java.util.Calendar;

public class DetailCalendarViewActivity extends AppCompatActivity {

    int id = 1;
    Intent intent;
    private ActivityDetailCalendarViewBinding binding;

    DataBase dataBase = new DataBase();

    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);

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
        binding.btnEditDate.setText(date);
        binding.editDetailSubject.setText(subject);
        binding.editDetailContent.setText(content);
        binding.btnEditDate.setClickable(false);

        dataBase.openDatabase(this, DATABASE_NAME);
        dataBase.createContactTable(TABLE_CONTACT_INFO);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = (month + 1);
                String tempMonth = "0" + month;
                String tempDay = "0" + dayOfMonth;
                if (dayOfMonth < 10) {
                    if (month < 10) {
                        binding.btnEditDate.setText(year + "/" + tempMonth + "/" + tempDay);
                    } else {
                        binding.btnEditDate.setText(year + "/" + month + "/" + tempDay);
                    }
                } else {
                    if (month < 10) {
                        binding.btnEditDate.setText(year + "/" + tempMonth + "/" + dayOfMonth);
                    } else {
                        binding.btnEditDate.setText(year + "/" + month + "/" + dayOfMonth);
                    }
                }
            }
        }, year, month, day);

        binding.btnEditDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        binding.btnEditSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject = binding.editDetailSubject.getText().toString();
                String content = binding.editDetailContent.getText().toString();
                String date = binding.btnEditDate.getText().toString();
                dataBase.updateDiaryRecord(id, subject, content, date);
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.modify_action_bar, menu);
        return true;
    }

    public void setVisibility(MenuItem item) {
        if(id > 0) {
            binding.editDetailSubject.setVisibility(View.VISIBLE);
            binding.editDetailContent.setVisibility(View.VISIBLE);
            binding.btnEditSubmit.setVisibility(View.VISIBLE);
            binding.textDetailSubject.setVisibility(View.INVISIBLE);
            binding.textDetailContents.setVisibility(View.INVISIBLE);
            binding.btnEditDate.setClickable(true);
            id *= -1;
            item.setIcon(R.drawable.image_backspace);
        } else {
            binding.editDetailSubject.setVisibility(View.INVISIBLE);
            binding.editDetailContent.setVisibility(View.INVISIBLE);
            binding.btnEditSubmit.setVisibility(View.INVISIBLE);
            binding.textDetailSubject.setVisibility(View.VISIBLE);
            binding.textDetailContents.setVisibility(View.VISIBLE);
            binding.btnEditDate.setClickable(false);
            binding.editDetailSubject.setText(binding.textDetailSubject.getText());
            binding.editDetailContent.setText(binding.textDetailContents.getText());
            id *= -1;
            item.setIcon(R.drawable.image_edit);
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                setVisibility(item);
                break;

            case R.id.action_delete:
                AlertDialog.Builder menu = new AlertDialog.Builder(this);
                menu.setTitle("일정 삭제");
                menu.setMessage("정말로 일정을 삭제하시겠습니까?");
                menu.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dataBase.deleteDiaryById(intent.getStringExtra("id"));
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
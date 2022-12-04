package com.example.mycalendar.ui.Calendar;
//
//

import static com.example.mycalendar.DataBase.DATABASE_NAME;
import static com.example.mycalendar.DataBase.TABLE_DIARY_INFO;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.mycalendar.DataBase;
import com.example.mycalendar.databinding.ActivityAddScheduleBinding;

import java.util.Calendar;

public class AddScheduleActivity extends AppCompatActivity {

    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);

    DataBase dataBase = new DataBase();
    private ActivityAddScheduleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddScheduleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dataBase.openDatabase(this, DATABASE_NAME);
        dataBase.createDiaryTable(TABLE_DIARY_INFO);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = (month + 1);
                String tempMonth = "0" + month;
                String tempDay = "0" + dayOfMonth;
                if (dayOfMonth < 10) {
                    if (month < 10) {
                        binding.btnDate.setText(year + "/" + tempMonth + "/" + tempDay);
                    } else {
                        binding.btnDate.setText(year + "/" + month + "/" + tempDay);
                    }
                } else {
                    if (month < 10) {
                        binding.btnDate.setText(year + "/" + tempMonth + "/" + dayOfMonth);
                    } else {
                        binding.btnDate.setText(year + "/" + month + "/" + dayOfMonth);
                    }
                }
            }
        }, year, month, day);

        binding.btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = binding.editTitle.getText().toString();
                String content = binding.editContent.getText().toString();
                String date = binding.btnDate.getText().toString();

                if (title.equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "제목을 입력해주세요", Toast.LENGTH_LONG);
                    toast.show();
                } else if (content.equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "내용을 입력해주세요", Toast.LENGTH_LONG);
                    toast.show();
                } else if (date.equals("날짜 선택")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "날짜를 선택해주세요", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    dataBase.insertDiaryRecord(TABLE_DIARY_INFO, date, title, content);
                }
            }
        });
    }
}
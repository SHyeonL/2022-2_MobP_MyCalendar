package com.example.mycalendar.ui.Calendar;
//
import static android.content.ContentValues.TAG;
import static com.example.mycalendar.DiaryDatabase.database;
//
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycalendar.DiaryDatabase;
import com.example.mycalendar.R;

public class AddScheduleActivity extends AppCompatActivity {


    DiaryDatabase database;
    EditText editText;//일정 제목
    EditText editText3;//일정 내용

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        //
        editText = findViewById(R.id.editText);
        editText3 = findViewById(R.id.editText3);
        // open database
        if (database != null) {
            database.close();
            database = null;
        }

        database = DiaryDatabase.getInstance(this);
        boolean isOpen = database.open();
        if (isOpen) {
            Log.d(TAG, "diary database is open.");
        } else {
            Log.d(TAG, "diary database is not open.");
        }

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = editText.getText().toString();
                String contents = editText3.getText().toString();

                database.insertRecord( subject, contents);
                Toast.makeText(getApplicationContext(), "정보를 추가했습니다.", Toast.LENGTH_LONG).show();
            }
        });
    }

    protected void onDestroy() {
        // close database
        if (database != null) {
            database.close();
            database = null;
        }
        super.onDestroy();
    }
}
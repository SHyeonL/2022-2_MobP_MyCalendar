package com.example.mycalendar.ui.Contact;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
//
import static com.example.mycalendar.DiaryDatabase.database;
//
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycalendar.DiaryDatabase;
import com.example.mycalendar.R;

public class AddContactActivity extends AppCompatActivity {

    DiaryDatabase database;
    EditText editText4;//연락처 이름
    EditText editText5;//연락처 번호

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        editText4 = findViewById(R.id.editText4);
        editText5 = findViewById(R.id.editText5);

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

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText4.getText().toString();
                //int number = Integer.parseInt(editText5.getText().toString());
                String number = editText5.getText().toString();

                database.insertRecord2( name, number);
                Toast.makeText(getApplicationContext(), "연락처 추가했습니다.", Toast.LENGTH_LONG).show();
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
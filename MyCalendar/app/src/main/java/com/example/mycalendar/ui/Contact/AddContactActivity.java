package com.example.mycalendar.ui.Contact;

import static com.example.mycalendar.DataBase.DATABASE_NAME;
import static com.example.mycalendar.DataBase.TABLE_CONTACT_INFO;

import androidx.appcompat.app.AppCompatActivity;
//
//
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mycalendar.DataBase;
import com.example.mycalendar.R;
import com.example.mycalendar.databinding.ActivityAddContactBinding;

public class AddContactActivity extends AppCompatActivity {

    DataBase database = new DataBase();

    private ActivityAddContactBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database.openDatabase(this, DATABASE_NAME);
        database.createContactTable(TABLE_CONTACT_INFO);

        binding.btnContactSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.editName.getText().toString();
                String number = binding.editNumber.getText().toString();
                if (name.equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "이름을 입력해주세요.", Toast.LENGTH_LONG);
                    toast.show();
                } else if (number.equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "전화번호를 입력해주세요.", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    database.insertContactRecord(name, number);
                    onBackPressed();
                }
            }
        });
    }

}
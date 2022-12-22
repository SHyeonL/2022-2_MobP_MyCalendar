package com.example.mycalendar.ui.Contact;

import static com.example.mycalendar.DataBase.DATABASE_NAME;
import static com.example.mycalendar.DataBase.TABLE_CONTACT_INFO;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.mycalendar.DataBase;
import com.example.mycalendar.R;
import com.example.mycalendar.databinding.ActivityDetailContactViewBinding;

public class DetailContactViewActivity extends AppCompatActivity {

    Intent intent;
    private ActivityDetailContactViewBinding binding;

    int id = 1;

    DataBase dataBase = new DataBase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailContactViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intent = getIntent();
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        String number = intent.getStringExtra("number");

        binding.textDetailName.setText(name);
        binding.textDetailNumber.setText(number);

        binding.editDetailName.setText(name);
        binding.editDetailNumber.setText(number);

        dataBase.openDatabase(this, DATABASE_NAME);
        dataBase.createContactTable(TABLE_CONTACT_INFO);

        binding.btnEditConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.editDetailName.getText().toString();
                String number = binding.editDetailNumber.getText().toString();
                dataBase.updateContactRecord(id, name, number);
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
        if (id > 0) {
            binding.editDetailName.setVisibility(View.VISIBLE);
            binding.editDetailNumber.setVisibility(View.VISIBLE);
            binding.btnEditConfirm.setVisibility(View.VISIBLE);
            binding.textDetailName.setVisibility(View.INVISIBLE);
            binding.textDetailNumber.setVisibility(View.INVISIBLE);
            id *= -1;
            item.setIcon(R.drawable.image_backspace);
        } else {
            binding.editDetailName.setVisibility(View.INVISIBLE);
            binding.editDetailNumber.setVisibility(View.INVISIBLE);
            binding.btnEditConfirm.setVisibility(View.INVISIBLE);
            binding.textDetailName.setVisibility(View.VISIBLE);
            binding.textDetailNumber.setVisibility(View.VISIBLE);
            binding.editDetailName.setText(binding.textDetailName.getText());
            binding.editDetailNumber.setText(binding.textDetailNumber.getText());
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
                menu.setTitle("연락처 삭제");
                menu.setMessage("정말로 연락처를 삭제하시겠습니까?");
                menu.setPositiveButton("삭제", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dataBase.deleteContactById(intent.getStringExtra("id"));
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
package com.example.mycalendar.ui.home;

import static com.example.mycalendar.DataBase.DATABASE_NAME;
import static com.example.mycalendar.DataBase.TABLE_CONTACT_INFO;
import static com.example.mycalendar.DataBase.TABLE_DIARY_INFO;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mycalendar.DataBase;
import com.example.mycalendar.MainActivity;
import com.example.mycalendar.R;
import com.example.mycalendar.databinding.FragmentHomeBinding;
import com.example.mycalendar.ui.Calendar.AddScheduleActivity;
import com.example.mycalendar.ui.Calendar.toDoItem;
import com.example.mycalendar.ui.Calendar.toDoListView;
import com.example.mycalendar.ui.SearchActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    DataBase dataBase = new DataBase();
    public toDoListAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        View root = binding.getRoot();
        dataBase.openDatabase(container.getContext(), DATABASE_NAME);
        dataBase.createDiaryTable(TABLE_DIARY_INFO);
        adapter = new toDoListAdapter();

//        adapter.addItem(new toDoItem("Study", "2022-01-01"));
//        adapter.addItem(new toDoItem("할일2", "2022-01-02"));
//        adapter.addItem(new toDoItem("할일3", "2022-01-03"));
//        adapter.addItem(new toDoItem("할일4", "2022-01-04"));
        binding.todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                toDoItem item = (toDoItem) adapter.getItem(position);
                Toast.makeText(getActivity().getApplicationContext(), "선택 : " + item.getTitle(), Toast.LENGTH_LONG).show();
            }
        });
        ArrayList arrayList = new ArrayList<String>();
        arrayList = dataBase.getDiaryInfo();
        for (int i = 0; i < arrayList.toArray().length; i += 3) {
            Log.d("데이터베이스 값", arrayList.get(i).toString());
            String date = arrayList.get(i).toString();
            String subject = arrayList.get(i + 1).toString();
            String contents = arrayList.get(i + 2).toString();
            adapter.addItem(new toDoItem(subject, date, contents));
        }
        binding.todoList.setAdapter(adapter);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    class toDoListAdapter extends BaseAdapter {

        ArrayList<toDoItem> items = new ArrayList<toDoItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(toDoItem item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            toDoListView view = new toDoListView(getActivity().getApplicationContext());

            toDoItem item = items.get(position);
            view.setTitle(item.getTitle());
            view.setDate(item.getDate());
            view.setContent(item.getContent());
            return view;
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.common_action_bar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                final EditText editText = new EditText(getContext());
                AlertDialog.Builder menu = new AlertDialog.Builder(getActivity());
                menu.setIcon(R.mipmap.ic_launcher);
                menu.setTitle("일정 검색"); // 제목
                menu.setView(editText);


                // 확인 버튼
                menu.setPositiveButton("검색", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // dialog 제거
                        dialog.dismiss();
                    }
                });

                // 취소 버튼
                menu.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // dialog 제거
                        dialog.dismiss();
                    }
                });
                menu.show();
                break;
            case R.id.action_add:
                StartAddScheduleActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void StartAddScheduleActivity() {
        Intent intent = new Intent(getActivity(), AddScheduleActivity.class);
        startActivity(intent);
    }

    public void StartSearchActivity() {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent);
    }
}
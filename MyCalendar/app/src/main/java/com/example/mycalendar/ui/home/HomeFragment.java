package com.example.mycalendar.ui.home;

import static com.example.mycalendar.DataBase.DATABASE_NAME;
import static com.example.mycalendar.DataBase.TABLE_DIARY_INFO;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mycalendar.DataBase;
import com.example.mycalendar.R;
import com.example.mycalendar.databinding.FragmentHomeBinding;
import com.example.mycalendar.ui.Calendar.AddScheduleActivity;
import com.example.mycalendar.ui.Calendar.DetailCalendarViewActivity;
import com.example.mycalendar.ui.Calendar.toDoItem;
import com.example.mycalendar.ui.Calendar.toDoListView;
import com.example.mycalendar.ui.Contact.ContactItem;
import com.example.mycalendar.ui.SearchActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ArrayList arrayList = new ArrayList<String>();
    ArrayList<toDoItem> diaryInfo;
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

        binding.todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                toDoItem item = (toDoItem) adapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailCalendarViewActivity.class);
                intent.putExtra("id", item.getId());
                intent.putExtra("subject", item.getTitle());
                intent.putExtra("content", item.getContent());
                intent.putExtra("date", item.getDate());
                startActivity(intent);
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.clearItems();
        arrayList = dataBase.getDiaryInfo();
        for (int i = 0; i < arrayList.toArray().length; i += 4) {
            Log.d("데이터베이스 값", arrayList.get(i).toString());
            String id = arrayList.get(i).toString();
            String date = arrayList.get(i + 1).toString();
            String subject = arrayList.get(i + 2).toString();
            String contents = arrayList.get(i + 3).toString();
            adapter.addItem(new toDoItem(id, date, subject, contents));
        }
        binding.todoList.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    class toDoListAdapter extends BaseAdapter {

        ArrayList<toDoItem> items = new ArrayList<toDoItem>();

        public void clearItems() { items.clear(); }

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
                        //startSearch();
                        // dialog 제거
                        //startSearch();
                        String test = editText.getText().toString();
                        adapter.clearItems();
                        diaryInfo = dataBase.searchDiaryRecord(test);
                        for (int i = 0; i < diaryInfo.toArray().length; i++) {
                            toDoItem vo = diaryInfo.get(i);
                            adapter.addItem(vo);
                        }
                        binding.todoList.setAdapter(adapter);
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
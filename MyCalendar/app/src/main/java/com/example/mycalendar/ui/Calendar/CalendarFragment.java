package com.example.mycalendar.ui.Calendar;

import static com.example.mycalendar.DataBase.DATABASE_NAME;
import static com.example.mycalendar.DataBase.TABLE_DIARY_INFO;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mycalendar.DataBase;
import com.example.mycalendar.MainActivity;
import com.example.mycalendar.R;
import com.example.mycalendar.databinding.FragmentCalendarBinding;
import com.example.mycalendar.ui.Contact.AddContactActivity;
import com.example.mycalendar.ui.SearchActivity;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment {

    DataBase dataBase = new DataBase();
    ArrayList<String> arrayList = new ArrayList<String>();
    ArrayList<toDoItem> diaryInfo;
    ArrayList<toDoItem> clickedInfo;
    public toDoListAdapter adapter;

    final String TAG = "calendar test";
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("yyyy년 MM월", Locale.KOREA);
    private SimpleDateFormat dateFormatForMonth2 = new SimpleDateFormat("yyyy/MM", Locale.KOREA);

    private FragmentCalendarBinding binding;
    MainActivity mainActivity = new MainActivity();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        adapter = new toDoListAdapter();

        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        View root = binding.getRoot();

        dataBase.openDatabase(container.getContext(), DATABASE_NAME);
        dataBase.createDiaryTable(TABLE_DIARY_INFO);
        // 캘린더 코드 시작
        binding.textViewMonth.setText(dateFormatForMonth.format(binding.compactcalendarView.getFirstDayOfCurrentMonth()));

        binding.compactcalendarView.setFirstDayOfWeek(Calendar.SUNDAY);

        binding.compactcalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                adapter.clearItems();
                setList(dateClicked);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                binding.textViewMonth.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });

        binding.calendarList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    public void setList(Date date) {
        adapter.clearItems();
        Date dateClicked = date;
        Log.d("dateClicked", date.toString());
        List<Event> events = binding.compactcalendarView.getEvents(dateClicked);

        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy/MM/dd");
        String date1 = transFormat.format(dateClicked);
        clickedInfo = dataBase.showClickedDate(date1);

        if (events.size() > 0) {
            for(int i = 0; i < events.size(); i++) {
                adapter.addItem(clickedInfo.get(i));
            }
        }
        binding.calendarList.setAdapter(adapter);

        binding.textViewResult.setText("클릭한 날짜 " + date1);
        binding.textCalendarDate.setText(date1);
    }

    public void getCalendarInfo() {
        for (int i = 0; i < diaryInfo.toArray().length; i++) {
            toDoItem vo = diaryInfo.get(i);
            Date trans_date1 = null;
            try {
                trans_date1 = dateFormatForDisplaying.parse(vo.date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long time = trans_date1.getTime();

            Event event = new Event(Color.GREEN, time, vo.title);
            binding.compactcalendarView.addEvent(event);
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
                menu.setTitle("일정 검색");
                menu.setView(editText);

                menu.setPositiveButton("검색", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String subject = editText.getText().toString();
                        diaryInfo = dataBase.searchDiaryRecord(subject);
                        binding.compactcalendarView.removeAllEvents();
                        getCalendarInfo();
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
                startAddScheduleActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startAddScheduleActivity() {
        Intent intent = new Intent(getActivity(), AddScheduleActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("onResume", "Resume");
        diaryInfo = dataBase.getDiaryInfo();
        binding.compactcalendarView.removeAllEvents();
        getCalendarInfo();
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
            view.setContent(item.getContent());
            view.setInvisible();
            return view;
        }
    }
}


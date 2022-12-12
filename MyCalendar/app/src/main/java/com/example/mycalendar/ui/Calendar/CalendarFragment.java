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
    final String TAG = "calendar test";
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("yyyy년 MM월", Locale.KOREA);
    private SimpleDateFormat dateFormatForMonth2 = new SimpleDateFormat("yyyy/MM", Locale.KOREA);

    private FragmentCalendarBinding binding;
    MainActivity mainActivity = new MainActivity();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        View root = binding.getRoot();

        dataBase.openDatabase(container.getContext(), DATABASE_NAME);
        dataBase.createDiaryTable(TABLE_DIARY_INFO);

        arrayList = dataBase.getDiaryInfo();
        // 캘린더 코드 시작
        binding.textViewMonth.setText(dateFormatForMonth.format(binding.compactcalendarView.getFirstDayOfCurrentMonth()));

        binding.compactcalendarView.setFirstDayOfWeek(Calendar.SUNDAY);

        getCalendarInfo();

        binding.buttonRemoveEvents.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.compactcalendarView.removeAllEvents();
            }
        });

        binding.buttonGetEvent.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date dateyymm = binding.compactcalendarView.getFirstDayOfCurrentMonth();
                String yyymm = dateFormatForMonth2.format(dateyymm);

                String date = yyymm + "-01"; //"2021-11-01";

                Date trans_date = null;
                try {
                    trans_date = dateFormatForDisplaying.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long time = trans_date.getTime();
                List<Event> events = binding.compactcalendarView.getEvents(time);

                String info = "";
                if (events.size() > 0) {
                    info = events.get(0).getData().toString();
                }

            }
        });

        binding.compactcalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                List<Event> events = binding.compactcalendarView.getEvents(dateClicked);

                SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date1 = transFormat.format(dateClicked);

                String event_name = "";
                String event_date = "";

                if (events.size() > 0) {
                    event_name = events.get(0).getData().toString();
                    long time1 = events.get(0).getTimeInMillis();
                    event_date = transFormat.format(new Date(time1));
                }

                binding.textViewResult.setText("클릭한 날짜 " + date1 + "정보 :" + event_name + " " + event_date);

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                binding.textViewMonth.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });
        return root;
    }

    public void getCalendarInfo() {
        for (int i = 0; i < arrayList.toArray().length; i+= 4) {
            String id = arrayList.get(i);
            String date = arrayList.get(i + 1);
            String subject = arrayList.get(i + 2);
            String contents = arrayList.get(i + 3);
            Date trans_date1 = null;
            try {
                trans_date1 = dateFormatForDisplaying.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            long time = trans_date1.getTime();

            Event event = new Event(Color.GREEN, time, subject);
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
                        String test = editText.getText().toString();
                        diaryInfo = dataBase.searchDiaryRecord(test);
                        for (int i = 0; i < diaryInfo.toArray().length; i++) {
                            arrayList.clear();
                            //arrayList += diaryInfo.get(i);
                            //adapter.addItem(vo);
                        }
                        //binding.todoList.setAdapter(adapter);
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

    public void startSearch() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
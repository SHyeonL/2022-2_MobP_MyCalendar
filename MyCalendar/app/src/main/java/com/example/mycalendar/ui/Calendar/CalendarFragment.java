package com.example.mycalendar.ui.Calendar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mycalendar.MainActivity;
import com.example.mycalendar.R;
import com.example.mycalendar.databinding.FragmentCalendarBinding;
import com.example.mycalendar.ui.Contact.AddContactActivity;
import com.example.mycalendar.ui.SearchActivity;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment {

    final String TAG = "calendar test";
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("yyyy년 MM월", Locale.KOREA);
    private SimpleDateFormat dateFormatForMonth2 = new SimpleDateFormat("yyyy-MM", Locale.KOREA);

    private FragmentCalendarBinding binding;
    MainActivity mainActivity = new MainActivity();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        View root = binding.getRoot();

//        binding.button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), AddScheduleActivity.class);
//                startActivity(intent);
//            }
//        });


        // 캘린더 코드 시작
        binding.textViewMonth.setText(dateFormatForMonth.format(binding.compactcalendarView.getFirstDayOfCurrentMonth()));

        binding.compactcalendarView.setFirstDayOfWeek(Calendar.MONDAY);

        binding.buttonAddEvents.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                Date date = binding.compactcalendarView.getFirstDayOfCurrentMonth();
                String yyymm = dateFormatForMonth2.format(date);

                String date1 = yyymm + "-01"; //"2021-11-01";

                Date trans_date1 = null;
                try {
                    trans_date1 = dateFormatForDisplaying.parse(date1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long time1 = trans_date1.getTime();

                Event ev1 = new Event(Color.GREEN, time1, "이벤트 1");
                binding.compactcalendarView.addEvent(ev1);


                String date2 = yyymm + "-02"; //"2021-11-02";

                Date trans_date2 = null;
                try {
                    trans_date2 = dateFormatForDisplaying.parse(date2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long time2 = trans_date2.getTime();

                Event ev2 = new Event(Color.GREEN, time2, "이벤트 2");
                binding.compactcalendarView.addEvent(ev2);
            }
        });

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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.common_action_bar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                StartSearchActivity();
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
//        Intent intent = new Intent(getActivity(), SearchActivity.class);
//        startActivity(intent);
        Date date = binding.compactcalendarView.getFirstDayOfCurrentMonth();
        String yyymm = dateFormatForMonth2.format(date);

        String date1 = yyymm + "-01"; //"2021-11-01";

        Date trans_date1 = null;
        try {
            trans_date1 = dateFormatForDisplaying.parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long time1 = trans_date1.getTime();

        Event ev1 = new Event(Color.GREEN, time1, "이벤트 1");
        binding.compactcalendarView.addEvent(ev1);


        String date2 = yyymm + "-02"; //"2021-11-02";

        Date trans_date2 = null;
        try {
            trans_date2 = dateFormatForDisplaying.parse(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long time2 = trans_date2.getTime();

        Event ev2 = new Event(Color.GREEN, time2, "이벤트 2");
        binding.compactcalendarView.addEvent(ev2);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
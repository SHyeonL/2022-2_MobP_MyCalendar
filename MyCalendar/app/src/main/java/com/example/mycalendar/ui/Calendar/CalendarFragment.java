package com.example.mycalendar.ui.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mycalendar.MainActivity;
import com.example.mycalendar.R;
import com.example.mycalendar.databinding.FragmentCalendarBinding;
import com.example.mycalendar.ui.Contact.AddContactActivity;
import com.example.mycalendar.ui.SearchActivity;

public class CalendarFragment extends Fragment {

    private FragmentCalendarBinding binding;
    MainActivity mainActivity = new MainActivity();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        binding.button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), AddScheduleActivity.class);
//                startActivity(intent);
//            }
//        });
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
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
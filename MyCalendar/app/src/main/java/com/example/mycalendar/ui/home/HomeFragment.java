package com.example.mycalendar.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mycalendar.R;
import com.example.mycalendar.databinding.FragmentHomeBinding;
import com.example.mycalendar.ui.Calendar.AddScheduleActivity;
import com.example.mycalendar.ui.Calendar.toDoItem;
import com.example.mycalendar.ui.Calendar.toDoListView;
import com.example.mycalendar.ui.Contact.AddContactActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    toDoListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        View root = binding.getRoot();
        adapter = new toDoListAdapter();

        adapter.addItem(new toDoItem("할일1", "2022-01-01"));
        adapter.addItem(new toDoItem("할일2", "2022-01-02"));
        adapter.addItem(new toDoItem("할일3", "2022-01-03"));
        adapter.addItem(new toDoItem("할일4", "2022-01-04"));

        binding.todoList.setAdapter(adapter);

//        Button button = (Button) findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String name = editText.getText().toString();
//                String mobile = "010-1000-1000";
//                int age = 20;
//
//                adapter.addItem(new SingerItem(name, mobile, age, R.drawable.singer3));
//                adapter.notifyDataSetChanged();
//            }
//        });


        binding.todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                toDoItem item = (toDoItem) adapter.getItem(position);
                Toast.makeText(getActivity().getApplicationContext(), "선택 : " + item.getTitle(), Toast.LENGTH_LONG).show();
            }
        });
        return root;
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
            return view;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.common_action_bar, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search :
                Intent intent = new Intent(getActivity(), AddContactActivity.class);
                startActivity(intent);
                break;

                case R.id.action_add:
                    Intent intent1 = new Intent(getActivity(), AddScheduleActivity.class);
                    startActivity(intent1);
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }
}
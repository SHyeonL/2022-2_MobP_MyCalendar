package com.example.mycalendar.ui.Contact;

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
import com.example.mycalendar.databinding.FragmentContactBinding;
import com.example.mycalendar.ui.SearchActivity;

import java.util.ArrayList;


public class ContactFragment extends Fragment {

    private FragmentContactBinding binding;
    ContactListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentContactBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        View root = binding.getRoot();
        adapter = new ContactListAdapter();

        adapter.addItem(new ContactItem("이성현", "010-5595-1170"));
        adapter.addItem(new ContactItem("일리요스벡", "010-1234-5678"));
        adapter.addItem(new ContactItem("하성호", "010-1234-5678"));

        binding.contactList.setAdapter(adapter);

        binding.contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ContactItem item = (ContactItem) adapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailContactViewActivity.class);
                intent.putExtra("name", item.getName());
                intent.putExtra("number", item.getPhoneNum());
                startActivity(intent);
            }
        });
        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    class ContactListAdapter extends BaseAdapter {
        ArrayList<ContactItem> items = new ArrayList<ContactItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(ContactItem item) { items.add(item); }

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
            ContactListView view = new ContactListView(getActivity().getApplicationContext());

            ContactItem item = items.get(position);
            view.setTextName(item.getName());
            view.setTextPhoneNum(item.getPhoneNum());
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
                StartSearchActivity();
                break;
            case R.id.action_add:
                StartAddContactActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void StartAddContactActivity() {
        Intent intent = new Intent(getActivity(), AddContactActivity.class);
        startActivity(intent);
    }

    public void StartSearchActivity() {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent);
    }

}
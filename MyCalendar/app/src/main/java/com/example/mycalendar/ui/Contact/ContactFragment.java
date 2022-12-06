package com.example.mycalendar.ui.Contact;

import static com.example.mycalendar.DataBase.DATABASE_NAME;
import static com.example.mycalendar.DataBase.TABLE_CONTACT_INFO;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mycalendar.DataBase;
import com.example.mycalendar.R;
import com.example.mycalendar.databinding.FragmentContactBinding;
import com.example.mycalendar.ui.Calendar.toDoItem;
import com.example.mycalendar.ui.SearchActivity;

import java.util.ArrayList;


public class ContactFragment extends Fragment {

    private FragmentContactBinding binding;
    ContactListAdapter adapter;
    ArrayList<ContactItem> contactInfo;

    ArrayList arrayList = new ArrayList<String>();
    DataBase dataBase = new DataBase();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentContactBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        View root = binding.getRoot();
        adapter = new ContactListAdapter();

        dataBase.openDatabase(container.getContext(), DATABASE_NAME);
        dataBase.createContactTable(TABLE_CONTACT_INFO);
        binding.contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ContactItem item = (ContactItem) adapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailContactViewActivity.class);
                intent.putExtra("id", item.getId());
                intent.putExtra("name", item.getName());
                intent.putExtra("number", item.getPhoneNum());
                startActivity(intent);
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.clearItems();
        arrayList = dataBase.getContactInfo();
        for (int i = 0; i < arrayList.toArray().length; i += 3) {
            Log.d("데이터베이스 값", arrayList.get(i).toString());
            String id = arrayList.get(i).toString();
            String name = arrayList.get(i + 1).toString();
            String number = arrayList.get(i + 2).toString();
            adapter.addItem(new ContactItem(id, name, number));
        }
        binding.contactList.setAdapter(adapter);
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

        public void addItem(ContactItem item) {
            items.add(item);
        }

        public void clearItems() { items.clear(); }

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
                final EditText editText = new EditText(getContext());
                AlertDialog.Builder menu = new AlertDialog.Builder(getActivity());
                menu.setIcon(R.mipmap.ic_launcher);
                menu.setTitle("연락처 검색"); // 제목
                menu.setView(editText);


                // 확인 버튼
                menu.setPositiveButton("검색", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // dialog 제거
                        //startSearch();
                        String test = editText.getText().toString();
                        adapter.clearItems();
                        contactInfo = dataBase.searchConteactRecord(test);
                        for (int i = 0; i < contactInfo.toArray().length; i++) {
                            ContactItem vo = contactInfo.get(i);
                            adapter.addItem(vo);
                        }
                        binding.contactList.setAdapter(adapter);
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
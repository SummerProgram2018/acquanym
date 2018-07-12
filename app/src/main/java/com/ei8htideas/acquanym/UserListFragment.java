package com.ei8htideas.acquanym;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;


import com.ei8htideas.acquanym.backend.DBReader;
import com.ei8htideas.acquanym.backend.Details;
import com.ei8htideas.acquanym.backend.Session;
import com.ei8htideas.acquanym.ProfileFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by Frances on 09/07/2018.
 */

public class UserListFragment extends Fragment {
    private View rootView;
    private ArrayAdapter<Details> adapter;
    private List<Details> people;
    private ListView lv;
    ArrayList<Details> searchResults=new ArrayList<Details>();
    ArrayList<Details> filterResults=new ArrayList<Details>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.user_list_fragment, container, false);

        final CheckBox chk100 = (CheckBox)rootView.findViewById(R.id.checkBox_100);
        final CheckBox chk200 = (CheckBox)rootView.findViewById(R.id.checkBox_200);
        final CheckBox chk500 = (CheckBox)rootView.findViewById(R.id.checkBox_500);
        chk100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chk200.setChecked(false);
                chk500.setChecked(false);
                if (chk100.isChecked()) {
                    filterDistance(100);
                } else {
                    filterDistance(0);
                }

            }
        });
        chk200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chk100.setChecked(false);
                chk500.setChecked(false);
                if (chk200.isChecked()) {
                    filterDistance(200);
                } else {
                    filterDistance(0);
                }
            }
        });
        chk500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chk100.setChecked(false);
                chk200.setChecked(false);
                if (chk500.isChecked()) {
                    filterDistance(500);
                } else {
                    filterDistance(0);
                }
            }
        });

        populatePeopleList();
        doSearch();
        return rootView;
    }

    private void doSearch() {
        final EditText et = (EditText)rootView.findViewById(R.id.search);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = et.getText().toString().toLowerCase(Locale.getDefault());
                filter(text);
            }
        });
    }



    private void populatePeopleList() {
        people = new DBReader().searchAllUsers(Session.getMyDetails(), "name"); // fix this
        searchResults.addAll(people);
        filterResults.addAll(people);

        lv = (ListView)rootView.findViewById(R.id.list);
        adapter = new UserListAdapter(getActivity().getApplicationContext(), R.layout.list_item, people);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProfileFragment fragment = new ProfileFragment();
                fragment.passData(people.get(position));
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }


    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        people.clear();
        if (charText.length() == 0) {
            people.addAll(filterResults);
        } else {
            for (Details wp : filterResults) {
                if (wp.name != null && wp.name.toLowerCase(Locale.getDefault()).contains(charText)) {
                    people.add(wp);
                } if (wp.username != null && wp.username.toLowerCase(Locale.getDefault()).contains(charText)) {
                    people.add(wp);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void filterDistance(int distance) {
        people.clear();

        if (distance == 0) {
            people.addAll(searchResults);
        } else {
            for (Details wp : searchResults) {
                if (wp.distance < distance ) {
                    people.add(wp);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }

}

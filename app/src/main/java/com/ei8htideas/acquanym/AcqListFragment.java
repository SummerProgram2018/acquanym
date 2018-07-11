package com.ei8htideas.acquanym;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.ei8htideas.acquanym.backend.DBReader;
import com.ei8htideas.acquanym.backend.Details;
import com.ei8htideas.acquanym.backend.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by Frances on 09/07/2018.
 */

public class AcqListFragment extends Fragment {

    private View rootView;
    private ArrayAdapter<Details> adapter;
    private List<Details> people;
    private ListView lv;
    ArrayList<Details> mAllData=new ArrayList<Details>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.acq_list_fragment, container, false);
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
        people = new DBReader().searchAllAcqs(Session.getMyDetails(), "name"); // fix this

        mAllData.addAll(people);
        lv = (ListView)rootView.findViewById(R.id.list);
        adapter = new UserListAdapter(getActivity().getApplicationContext(), R.layout.list_item, people);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment = new AccountFragment();
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
            people.addAll(mAllData);
        } else {
            for (Details wp : mAllData) {
                if (wp.name.toLowerCase(Locale.getDefault()).contains(charText)
                        || wp.username.toLowerCase(Locale.getDefault()).contains(charText)) {
                    people.add(wp);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }


}

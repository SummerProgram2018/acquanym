package com.ei8htideas.acquanym;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.ei8htideas.acquanym.backend.DBReader;
import com.ei8htideas.acquanym.backend.Details;
import com.ei8htideas.acquanym.backend.Session;

import java.util.ArrayList;


/**
 * Created by Frances on 09/07/2018.
 */

public class UserListFragment extends Fragment {

    private View rootView;
    private ListView listView;
    private SearchView searchView;
    private ArrayList<Details> people;
    private UserListAdapter userListAdapter;

    ArrayList<Details> mAllData = new ArrayList<Details>();


    //@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /**SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        search = (SearchView) getView().findViewById(R.id.search);

        search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        search.setSubmitButtonEnabled(true);
        search.setOnQueryTextListener(this);
        */
        /**
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) getView().findViewById(R.id.search);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        */

        rootView = inflater.inflate(R.layout.list_fragment, container, false);
        people = (ArrayList) DBReader.searchAllUsers(Session.getMyDetails(), "name");

        UserListAdapter listAdapter = new UserListAdapter(Session.getMain(), people);
        listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(listAdapter);
        doSearch();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new MapFragment()); // this needs to be the profile fragment - Adrian
                ft.commit();
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Search Users");
    }

    private void doSearch() {
        final SearchView et = (SearchView)rootView.findViewById(R.id.search);
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

}

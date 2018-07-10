package com.ei8htideas.acquanym;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.ei8htideas.acquanym.backend.Details;
import com.ei8htideas.acquanym.backend.Session;

import java.util.ArrayList;


public class AcqListFragment extends Fragment implements SearchView.OnQueryTextListener {

    private ListView list;
    /**String[] name = {"Adrian Van Katwyk", "Archit Sharma", "Bruce Bu", "Catherine Lee", "Celine Leung", "Coming Zhang", "Daniel Ju",
     "Frances Wong", "Henry O'Brien", "James Gabauer", "Richy Liu", "Sunny Xiang"}*/

    private ArrayList<Details> people; // This is just set to be a list of all users!!
    private UserListAdapter userListAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        UserListAdapter listAdapter = new UserListAdapter(Session.getMain(), people);
        list = (ListView) view.findViewById(R.id.list);
        list.setAdapter(listAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new MapFragment()); // this needs to be the profile fragment - Adrian
                ft.commit();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Search Users");
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        userListAdapter.getFilter().filter(newText);
        if (TextUtils.isEmpty(newText)) {
            list.clearTextFilter();
        } else {
            list.setFilterText(newText.toString());
        }

        return true;
    }

}

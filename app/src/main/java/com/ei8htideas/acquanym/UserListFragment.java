package com.ei8htideas.acquanym;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.ei8htideas.acquanym.backend.Session;

/**
 * Created by Frances on 09/07/2018.
 */

public class UserListFragment extends Fragment {

    ListView list;
    String[] name = {"Frances Wong", "Henry O'Brien", "Coming", "Catherine", "Adrian Van Katwyk", "Archit", "James",
                        "Sam", "Jack", "Kausta", "Daniel", "Celine", "Richy", "Clien"};
    String[] job = {"Hey", "There", "How", "You", "Doing", "?", "more",
                        "more", "more", "more", "more", "more", "more", "more"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        UserListAdapter listAdapter = new UserListAdapter(Session.getMain(), name, job);
        list = (ListView) view.findViewById(R.id.list);
        list.setAdapter(listAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction ft = FragmentActivity.getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new MapFragment()); // this needs to be the profile fragment - Adrian
                ft.commit();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Your Acquaintances");
    }

}

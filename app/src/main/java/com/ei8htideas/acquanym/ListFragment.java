package com.ei8htideas.acquanym;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Frances on 09/07/2018.
 */

public class ListFragment extends Fragment {

    ListView list;
    String[] name = {"Frances", "Henry", "Coming", "Catherine", "Adrian", "Archit"};
    String[] job = {"Hey", "There", "How", "You", "Doing", "?"};
    /**Integer[] imageId = {
            R.drawable.java,
            R.drawable.cplus,
            R.drawable.csharp,
            R.drawable.html,
            R.drawable.download
    };*/

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list_fragment);
        UserListAdapter listAdapter = new UserListAdapter(MainActivity, name, job);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(listAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListFragment.this, "You Clicked at " +name[+ position], Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    /**
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Your Acquaintances");
    }
    */
}

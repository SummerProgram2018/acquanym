package com.ei8htideas.acquanym;

import android.content.DialogInterface;
import android.media.Image;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ei8htideas.acquanym.backend.DBReader;
import com.ei8htideas.acquanym.backend.DBWriter;
import com.ei8htideas.acquanym.backend.Details;
import com.ei8htideas.acquanym.backend.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by Henry on 09/07/2018.
 */

public class AcqReqsFragment extends Fragment implements AdapterView.OnItemClickListener {

    private View rootView;
    private ArrayAdapter<Details> adapter;
    private List<Details> people;
    private ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.acq_reqs_fragment, container, false);
        populatePeopleList();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Acquaintance Requests");
    }


    private void populatePeopleList() {
        people = new DBReader().searchAllAcqs(Session.getMyDetails(), "name"); // fix this - should be people who haven't confirmed?

        lv = (ListView)rootView.findViewById(R.id.list);
        adapter = new AcqReqsListAdapter(getActivity().getApplicationContext(), R.layout.reqs_list_item, people);
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long viewId = view.getId();
        Details person = people.get(position);

        if (viewId == R.id.confirm) {
            // write to database confirmed
        } else if (viewId == R.id.delete) {
            // write to database removed
        }

        people.remove(person);
    }
}

package com.ei8htideas.acquanym;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ei8htideas.acquanym.backend.Details;
import com.ei8htideas.acquanym.backend.Session;
import com.ei8htideas.acquanym.backend.backend.acqadd.DBAddParams;
import com.ei8htideas.acquanym.backend.backend.acqadd.DBConfirm;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Henry on 09/07/2018.
 */

public class TestAcqReqsFragment extends Fragment implements AdapterView.OnItemClickListener {

    private View rootView;
    private ArrayAdapter<Details> adapter;
    private ArrayList<Details> people;
    private ListView lv;

    private ProgressDialog progress;

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
        /**DBSearchParams params = new DBSearchParams();
        params.me = Session.getMyDetails();
        params.ar = this;
        progress.show();
        new DBAcqReqSearch().execute(params);*/

        Details tester = new Details(1, "me",  100, 100, 10, "student", "me", 19,  "Female", "a cool girl");
        Details tester2 = new Details(2, "me2",  100, 100, 110, "student", "me", 19,  "Female", "a cool girl");
        Details tester3 = new Details(3, "me3",  100, 100, 410, "student", "me", 19,  "Female", "a cool girl");
        Details tester4 = new Details(4, "me4",  100, 100, 510, "student", "me", 19,  "Female", "a cool girl");
        //List<Details> test = new ArrayList<>();
        people = new ArrayList<>();
        people.add(tester);
        people.add(tester2);
        people.add(tester3);
        people.add(tester4);



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
        people.get(position).name = "help";
        ProfileFragment fragment = new ProfileFragment();
        fragment.passData(people.get(position));
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        long viewId = view.getId();
        Details person = people.get(position);

        if (viewId == R.id.confirm) {
            DBAddParams params = new DBAddParams();
            params.me = Session.getMyDetails();
            params.them = person;
            new DBConfirm().execute(params);
        } else if (viewId == R.id.delete) {
            // write to database removed
            // lul this isn't supported
            //TODO: actually do this
        }

        people.remove(person);
    }
}

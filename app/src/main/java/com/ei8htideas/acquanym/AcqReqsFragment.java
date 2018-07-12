package com.ei8htideas.acquanym;

import android.app.ProgressDialog;
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
import com.ei8htideas.acquanym.backend.backend.acqadd.DBAddParams;
import com.ei8htideas.acquanym.backend.backend.acqadd.DBConfirm;
import com.ei8htideas.acquanym.background.BackgroundLoad;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by Henry on 09/07/2018.
 */

public class AcqReqsFragment extends Fragment implements AdapterView.OnItemClickListener, Loader {

    private View rootView;
    private ArrayAdapter<Details> adapter;
    private List<Details> people;
    private ListView lv;

    private ProgressDialog progress;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.acq_reqs_fragment, container, false);

        progress = new ProgressDialog(this.getContext());
        progress.setTitle("Searching users");
        progress.setMessage("Please wait...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog

        populatePeopleList();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Acquaintance Requests");
    }

    public void onLoad() {
        progress.dismiss();
        this.people = Session.getRequests();

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

    private void populatePeopleList() {
        progress.show();
        new BackgroundLoad().execute(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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

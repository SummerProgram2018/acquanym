package com.ei8htideas.acquanym;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ei8htideas.acquanym.backend.Details;
import com.ei8htideas.acquanym.backend.Session;
import com.ei8htideas.acquanym.backend.backend.acqadd.DBAdd;
import com.ei8htideas.acquanym.backend.backend.acqadd.DBAddParams;

/**
 * Created by Adrian on 9/07/2018.
 */

public class AddFragment extends Fragment {
    private Button addBtn;
    private Details them;

    public void passData(Details details) {
        them = details;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View v = inflater.inflate(R.layout.add_fragment, container, false);
        addBtn = (Button) v.findViewById(R.id.add_button);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBAddParams params = new DBAddParams();
                params.me = Session.getMyDetails();
                params.them = AddFragment.this.them;
                new DBAdd().execute(params);
                Session.getUsers().remove(them);
                UserListFragment fragment = new UserListFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Add Acquaintance");
    }
}

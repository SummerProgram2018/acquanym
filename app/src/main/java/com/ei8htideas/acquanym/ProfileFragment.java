package com.ei8htideas.acquanym;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ei8htideas.acquanym.backend.Details;

/**
 * Created by Adrian on 5/07/2018.
 */

public class ProfileFragment extends Fragment {

    //private static final String DESCRIBABLE_KEY = "describable_key";
    private Details mPerson = null;
    private View rootView;

    public void passData(Details person) {
        mPerson = person;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        rootView = inflater.inflate(R.layout.profile_fragment, container, false);

        if (mPerson != null) {
            TextView name = (TextView) rootView.findViewById(R.id.name);
            name.setText(mPerson.name);

            TextView age = (TextView) rootView.findViewById(R.id.age);
            name.setText(mPerson.age);

            TextView gender = (TextView) rootView.findViewById(R.id.gender);
            name.setText(mPerson.gender);

            TextView job = (TextView) rootView.findViewById(R.id.job);
            name.setText(mPerson.title);

            TextView description = (TextView) rootView.findViewById(R.id.description);
            name.setText(mPerson.description);

        }

        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("My Account");
    }
}

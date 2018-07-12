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
import android.widget.ImageView;
import android.widget.TextView;

import com.ei8htideas.acquanym.R;
import com.ei8htideas.acquanym.backend.Details;
import com.ei8htideas.acquanym.backend.Session;

/**
 * Created by Adrian on 5/07/2018.
 */

public class AccountFragment extends Fragment {

    private Details mPerson = Session.getMyDetails();
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        rootView = inflater.inflate(R.layout.account_fragment, container, false);
        Button button = (Button) rootView.findViewById(R.id.edit_button);


        if (mPerson != null) {
            TextView name = (TextView) rootView.findViewById(R.id.name);
            name.setText(mPerson.name);

            TextView age = (TextView) rootView.findViewById(R.id.age);
            age.setText("Age: " + mPerson.age);

            TextView gender = (TextView) rootView.findViewById(R.id.gender);
            gender.setText("Gender: " + mPerson.gender);

            TextView job = (TextView) rootView.findViewById(R.id.job);
            job.setText("Occupation: " + mPerson.title);

            TextView description = (TextView) rootView.findViewById(R.id.description);
            description.setText("Description: " + mPerson.description);

            ImageView image = (ImageView) rootView.findViewById(R.id.image);
            image.setImageResource(R.drawable.joe);

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

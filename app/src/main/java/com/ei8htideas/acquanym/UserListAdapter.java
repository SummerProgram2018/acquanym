package com.ei8htideas.acquanym;


import android.app.Activity;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.content.Context;
import android.widget.TextView;

/**
 * Created by Frances on 09/07/2018.
 */

public class UserListAdapter extends ArrayAdapter {

    private final Activity context;
    private final String[] name;
    private final String[] job;
    //private final Integer[] imageId;

    public UserListAdapter(Activity context, String[] name, String[] job) {
        super(context, R.layout.user_list_fragment, name); // wtf
        this.context = context;
        this.name = name;
        this.job = job;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.user_list_fragment, null, true);
        TextView txtName = (TextView) rowView.findViewById(R.id.Name);
        TextView txtJob = (TextView) rowView.findViewById(R.id.Job);
        //ImageView img = (ImageView) rowView.findViewById(R.id.Image);
        txtName.setText(name[position]);
        txtJob.setText(job[position]);
        //imageView.setImageResource(imageId[position]);
        return rowView;
    }
}

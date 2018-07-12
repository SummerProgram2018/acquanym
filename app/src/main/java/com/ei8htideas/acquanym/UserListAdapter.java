package com.ei8htideas.acquanym;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.content.Context;
import android.widget.TextView;

import com.ei8htideas.acquanym.backend.Details;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frances on 09/07/2018.
 */

public class UserListAdapter extends ArrayAdapter<Details> {

    private ArrayList<Details> arraylist;

    public UserListAdapter(Context context, int layoutId, List<Details> items) {
        super(context, layoutId, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View arrayView = convertView;
        if(arrayView == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            arrayView = vi.inflate(R.layout.list_item, parent, false);
        }

        Details currentPosition = getItem(position);
        if(currentPosition != null){
            ImageView image = (ImageView)arrayView.findViewById(R.id.Image);
            image.setImageResource(R.drawable.joe);

            TextView name = (TextView)arrayView.findViewById(R.id.Name);
            name.setText(currentPosition.name);

            TextView job = (TextView)arrayView.findViewById(R.id.Job);
            job.setText(currentPosition.title);
        }
        return arrayView;
    }


}
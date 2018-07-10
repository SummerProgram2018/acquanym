package com.ei8htideas.acquanym;


import android.app.Activity;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.content.Context;
import android.widget.TextView;

import com.ei8htideas.acquanym.backend.Details;

import java.util.ArrayList;

/**
 * Created by Frances on 09/07/2018.
 */

public class UserListAdapter extends ArrayAdapter implements Filterable {

    private final Activity context;
    private UserFilter userFilter;
    private Typeface typeface;
    private final ArrayList<Details> people;
    private ArrayList<Details> filteredPeople;

    public UserListAdapter(Activity context, ArrayList<Details> people) {
        super(context, R.layout.user_list_fragment, people);
        this.context = context;
        this.people = people;
        this.filteredPeople = people;
        //typeface = Typeface.createFromAsset(context.getAssets(), "@fonts/vegure_2.otf");

        getFilter();
    }

    @Override
    public int getCount() {
        return filteredPeople.size();
    }

    @Override
    public Object getItem(int i) {
        return filteredPeople.get(i);
    }

    @Override
    public long getItemId(int i) {
        return filteredPeople.get(i).id;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final Details person = (Details) getItem(position);

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.user_list_fragment, null, true);
        TextView txtName = (TextView) rowView.findViewById(R.id.Name);
        TextView txtJob = (TextView) rowView.findViewById(R.id.Job);
        //ImageView img = (ImageView) rowView.findViewById(R.id.Image);

        txtName.setText(person.name);
        txtJob.setText(person.job);
        //imageView.setImageResource(imageId[position]);

        return rowView;
    }

    @Override
    public Filter getFilter() {
        if (userFilter == null) {
            userFilter = new UserFilter();
        }

        return userFilter;
    }


    private class UserFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<Details> tempList = new ArrayList<Details>();

                for (Details detail : people) {
                    if (detail.name.toLowerCase().contains(constraint.toString().toLowerCase())
                            || detail.username.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(detail);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = people.size();
                filterResults.values = people;
            }

            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredPeople = (ArrayList<Details>) results.values;
            notifyDataSetChanged();
        }

    }




}

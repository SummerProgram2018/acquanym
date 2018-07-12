package com.ei8htideas.acquanym;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ei8htideas.acquanym.backend.DBWriter;
import com.ei8htideas.acquanym.backend.Details;
import com.ei8htideas.acquanym.backend.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frances on 09/07/2018.
 */

public class AcqReqsListAdapter extends ArrayAdapter<Details> {

    private ArrayList<Details> arraylist;

    public AcqReqsListAdapter(Context context, int layoutId, List<Details> items) {
        super(context, layoutId, items);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View arrayView = convertView;
        if(arrayView == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            arrayView = vi.inflate(R.layout.reqs_list_item, parent, false);
        }

        final Details currentPosition = getItem(position);
        ImageButton confirm = (ImageButton) arrayView.findViewById(R.id.confirm);
        ImageButton delete = (ImageButton) arrayView.findViewById(R.id.delete);

        if(currentPosition != null){
            ImageView image = (ImageView)arrayView.findViewById(R.id.Image);
            image.setImageResource(R.drawable.joe);

            TextView name = (TextView)arrayView.findViewById(R.id.Name);
            name.setText(currentPosition.name);

            TextView job = (TextView)arrayView.findViewById(R.id.Job);
            job.setText(currentPosition.title);

            confirm.setImageResource(R.drawable.ic_confirm);
            delete.setImageResource(R.drawable.ic_delete);
        }

        final ViewGroup tempParent = parent;

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView) tempParent).performItemClick(view, position, 0);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView) tempParent).performItemClick(view, position, 0);
            }
        });

        return arrayView;
    }


}
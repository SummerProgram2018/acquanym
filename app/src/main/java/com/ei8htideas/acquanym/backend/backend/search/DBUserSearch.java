package com.ei8htideas.acquanym.backend.backend.search;

import android.os.AsyncTask;

import com.ei8htideas.acquanym.backend.DBReader;
import com.ei8htideas.acquanym.backend.Details;

import java.util.List;

/**
 * Created by Henry on 12/07/2018.
 */

public class DBUserSearch extends AsyncTask<DBSearchParams, Void, List<Details>> {
    private DBSearchParams params;

    @Override
    protected List<Details> doInBackground(DBSearchParams... params) {
        this.params = params[0];
        return DBReader.searchAllUsers(params[0].me, params[0].order);
    }

    @Override
    protected void onPostExecute(List<Details> result) {
        params.ul.populatePeopleListCallback(result);
    }
}

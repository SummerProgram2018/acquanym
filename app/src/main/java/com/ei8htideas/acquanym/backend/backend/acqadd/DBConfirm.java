package com.ei8htideas.acquanym.backend.backend.acqadd;

import android.os.AsyncTask;

import com.ei8htideas.acquanym.backend.DBWriter;

/**
 * Created by Henry on 11/07/2018.
 */

public class DBConfirm extends AsyncTask<DBAddParams, Void, Void> {
    private DBAddParams params;

    @Override
    protected Void doInBackground(DBAddParams... params) {
        this.params = params[0];
        DBWriter.confirmAcq(params[0].me, params[0].them);
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {

    }
}

package com.ei8htideas.acquanym.backend.backend.login;

import android.os.AsyncTask;

import com.ei8htideas.acquanym.backend.DBReader;

/**
 * Created by Henry on 11/07/2018.
 */

public class DBLogin extends AsyncTask<DBLoginParams, Void, Boolean> {
    private DBLoginParams params;

    @Override
    protected Boolean doInBackground(DBLoginParams... params) {
        this.params = params[0];
        return DBReader.verifyLogin(params[0].username, params[0].password);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if(result) {
            params.activity.onSuccess();
        } else {
            params.activity.onFail();
        }
    }
}

package com.ei8htideas.acquanym.backend.backend.login;

import android.os.AsyncTask;

import com.ei8htideas.acquanym.backend.DBReader;

/**
 * Created by Henry on 12/07/2018.
 */

public class DBRegister extends AsyncTask<DBLoginParams, Void, Boolean> {
    private DBLoginParams params;

    protected Boolean doInBackground(DBLoginParams... params) {
        this.params = params[0];
        return DBReader.newAccount(params[0].username, params[0].password, params[0].name,
                params[0].latitude, params[0].longitude);
    }

    protected void onPostExecute(Boolean result) {
        if(result) {
            params.register.onSuccess();
        } else {
            params.register.onFail();
        }
    }
}

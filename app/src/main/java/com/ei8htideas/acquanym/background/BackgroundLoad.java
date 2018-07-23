package com.ei8htideas.acquanym.background;

import android.os.AsyncTask;

import com.ei8htideas.acquanym.Loader;
import com.ei8htideas.acquanym.backend.DBReader;
import com.ei8htideas.acquanym.backend.Details;
import com.ei8htideas.acquanym.backend.Session;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Henry on 12/07/2018.
 */

public class BackgroundLoad extends AsyncTask<Loader, Void, Loader> {

    @Override
    protected Loader doInBackground(Loader... params) {
        while(!Session.isReady());
        return params[0];
    }

    @Override
    protected void onPostExecute(Loader l) {
        l.onLoad();
    }
}

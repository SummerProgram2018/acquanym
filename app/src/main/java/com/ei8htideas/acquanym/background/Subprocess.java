package com.ei8htideas.acquanym.background;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by Henry on 6/07/2018.
 */

public class Subprocess extends IntentService {
    public Subprocess() {
        super("Subprocess");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        System.out.println("memes");
    }
}

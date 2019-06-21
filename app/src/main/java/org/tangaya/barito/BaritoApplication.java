package org.tangaya.barito;

import android.app.Application;
import android.util.Log;

import timber.log.Timber;

public class BaritoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("BaritoApplication", "Starting application. Manual logging");
        Timber.d("Starting application");

        Timber.plant(new Timber.DebugTree());
    }
}

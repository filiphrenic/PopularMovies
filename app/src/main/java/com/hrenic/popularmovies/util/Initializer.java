package com.hrenic.popularmovies.util;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.squareup.leakcanary.LeakCanary;

/**
 * Used for application initialization
 */
public class Initializer {

    private static final String TAG = Initializer.class.getSimpleName();

    public static void init(@NonNull Application application) {

        FlowManager.init(new FlowConfig.Builder(application).build());
        if (!LeakCanary.isInAnalyzerProcess(application)) {
            LeakCanary.install(application);
        }

        Log.d(TAG, "Initialization done");
    }

}

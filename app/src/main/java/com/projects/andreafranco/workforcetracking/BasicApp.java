package com.projects.andreafranco.workforcetracking;

import android.app.Application;

import com.projects.andreafranco.workforcetracking.local.AppDatabase;
import com.projects.andreafranco.workforcetracking.repository.DataRepository;

/**
 * Class Used for accessing singletons.
 */
public class BasicApp extends Application {

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    public AppDatabase getDatabase(){
        return AppDatabase.getInstance(this, mAppExecutors);
    }

    public DataRepository getLocalUserRepository() {
        return DataRepository.getInstance(getDatabase(), mAppExecutors);
    }
}

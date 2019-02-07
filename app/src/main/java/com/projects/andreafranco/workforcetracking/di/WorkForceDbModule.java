package com.projects.andreafranco.workforcetracking.di;

import android.content.Context;

import com.projects.andreafranco.workforcetracking.AppExecutors;
import com.projects.andreafranco.workforcetracking.local.AppDatabase;
import com.projects.andreafranco.workforcetracking.local.dao.UserDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class WorkForceDbModule {

    @Singleton
    @Provides
    public AppDatabase provideAppDatabase(Context context, AppExecutors executors) {
        return AppDatabase.getInstance(context, executors);
    }

    @Singleton
    @Provides
    public UserDao provideUserDao(AppDatabase appDatabase) {
        return appDatabase.userDao();
    }

}

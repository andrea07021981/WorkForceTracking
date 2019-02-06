package com.projects.andreafranco.workforcetracking.di;

import android.content.Context;

import com.projects.andreafranco.workforcetracking.local.AppDatabase;
import com.projects.andreafranco.workforcetracking.local.UserDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class WorkForceDbModule {

    @Singleton
    @Provides
    public AppDatabase provideAppDatabase(Context context) {
        return AppDatabase.getInstance(context);
    }

    @Singleton
    @Provides
    public UserDao provideUserDao(AppDatabase appDatabase) {
        return appDatabase.userDao();
    }

}

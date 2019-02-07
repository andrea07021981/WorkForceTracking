package com.projects.andreafranco.workforcetracking.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.projects.andreafranco.workforcetracking.AppExecutors;
import com.projects.andreafranco.workforcetracking.local.AppDatabase;
import com.projects.andreafranco.workforcetracking.local.entity.UserEntity;

import java.util.List;

public class DataRepository {
    //TODO create a generic class or interface and implenents in every repository by type

    private static DataRepository sInstance;
    private final AppDatabase mDatabase;
    private final AppExecutors mExecutors;
    private MediatorLiveData<List<UserEntity>> mObservableUsers;

    private DataRepository(final AppDatabase appDatabase, AppExecutors appExecutors) {
        mDatabase = appDatabase;
        mExecutors = appExecutors;
        mObservableUsers = new MediatorLiveData<>();

        mObservableUsers.addSource(mDatabase.userDao().getAllUsers(),
                userEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableUsers.postValue(userEntities);
                    }
                });
    }

    public static DataRepository getInstance(final AppDatabase database, AppExecutors appExecutors) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance =  new DataRepository(database, appExecutors);
                }
            }
        }
        return sInstance;
    }

    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    public LiveData<List<UserEntity>> getUsers() {
        return mObservableUsers;
    }

    public LiveData<UserEntity> getUserById(final long userId) {
        return mDatabase.userDao().getUserById(userId);
    }

    public void insertUser(UserEntity userEntity)
    {
        mExecutors.diskIO().execute(()-> {
            mDatabase.userDao().insertUser(userEntity);
        });
    }
}

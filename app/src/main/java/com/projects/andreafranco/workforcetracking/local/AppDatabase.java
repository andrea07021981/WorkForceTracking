package com.projects.andreafranco.workforcetracking.local;

import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.projects.andreafranco.workforcetracking.AppExecutors;
import com.projects.andreafranco.workforcetracking.BuildConfig;
import com.projects.andreafranco.workforcetracking.local.converter.DateConverter;
import com.projects.andreafranco.workforcetracking.local.dao.UserDao;
import com.projects.andreafranco.workforcetracking.local.entity.UserEntity;

import java.util.List;


@Database(entities = {UserEntity.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "workforcedb";

    private static AppDatabase sInstance;
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(Context context, AppExecutors executors) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = buildDatabase(context, executors);
                sInstance.updateDatabaseCreated(context.getApplicationContext());
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    private static AppDatabase buildDatabase(Context context, AppExecutors executors) {
        return Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIO().execute(()-> {
                            if (BuildConfig.DEBUG) {
                                // Add a delay to simulate a long-running operation
                                addDelay();
                                // Generate the data for pre-population
                                AppDatabase database = AppDatabase.getInstance(context, executors);
                                List<UserEntity> users = DataGenerator.generateUsers();

                                insertData(database, users);
                                // notify that the database was created and it's ready to be used
                                database.setDatabaseCreated();
                            }
                        });
                    }
                }).build();
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            String currentDBPath=context.getDatabasePath(DATABASE_NAME).getAbsolutePath();
            Log.d(LOG_TAG, currentDBPath);
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    public MutableLiveData<Boolean> getDatabaseCreated(){
        return mIsDatabaseCreated;
    };

    private static void insertData(AppDatabase database, List<UserEntity> users) {
        database.runInTransaction(()-> {
            database.userDao().insertAllUsers(users);
        });
    }

    private static void addDelay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ignored) {
        }
    }

    public abstract UserDao userDao();
}

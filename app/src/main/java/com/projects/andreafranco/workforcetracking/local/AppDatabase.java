package com.projects.andreafranco.workforcetracking.local;

import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.projects.andreafranco.workforcetracking.AppExecutors;
import com.projects.andreafranco.workforcetracking.BuildConfig;
import com.projects.andreafranco.workforcetracking.local.converter.DateConverter;
import com.projects.andreafranco.workforcetracking.local.dao.FunctionDao;
import com.projects.andreafranco.workforcetracking.local.dao.ShiftDao;
import com.projects.andreafranco.workforcetracking.local.dao.TeamDao;
import com.projects.andreafranco.workforcetracking.local.dao.UserDao;
import com.projects.andreafranco.workforcetracking.local.dao.UserTeamDao;
import com.projects.andreafranco.workforcetracking.local.entity.FunctionEntity;
import com.projects.andreafranco.workforcetracking.local.entity.ShiftEntity;
import com.projects.andreafranco.workforcetracking.local.entity.TeamEntity;
import com.projects.andreafranco.workforcetracking.local.entity.UserEntity;

import java.util.List;


@Database(entities = {UserEntity.class, TeamEntity.class, ShiftEntity.class, FunctionEntity.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();

    @VisibleForTesting
    public static final String DATABASE_NAME = "workforcedb";
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
                                // Generate the data for pre-population
                                AppDatabase database = AppDatabase.getInstance(context, executors);

                                //Add teams(it's a duty of server, but now we don't have ws connections already prepared)
                                List<TeamEntity> teams = DataGenerator.generateTeams();
                                inserTeamData(database, teams);

                                //Add shifts(it's a duty of server, but now we don't have ws connections already prepared)
                                List<ShiftEntity> shifts = DataGenerator.generateShifts();
                                insertShiftData(database, shifts);


                                //Add funcions
                                List<FunctionEntity> functions = DataGenerator.generateFunctions();
                                insertFunctionData(database, functions);

                                //Add users
                                List<UserEntity> users = DataGenerator.generateUsers(context);
                                insertUserData(database, users);

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

    private static void insertUserData(AppDatabase database, List<UserEntity> users) {
        database.runInTransaction(()-> {
            database.userDao().insertAllUsers(users);
        });
    }

    private static void inserTeamData(AppDatabase database, List<TeamEntity> teams) {
        database.runInTransaction(()-> {
            database.teamDao().insertAllTeams(teams);
        });
    }

    private static void insertShiftData(AppDatabase database, List<ShiftEntity> shifts) {
        database.runInTransaction(()-> {
            database.shiftDao().insertAllShifts(shifts);
        });
    }

    private static void insertFunctionData(AppDatabase database, List<FunctionEntity> functions) {
        database.runInTransaction(()-> {
            database.functionDao().insertAllFunctions(functions);
        });
    }

    public abstract UserDao userDao();

    public abstract TeamDao teamDao();

    public abstract ShiftDao shiftDao();

    public abstract UserTeamDao userTeamDao();

    public abstract FunctionDao functionDao();
}

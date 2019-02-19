package com.projects.andreafranco.workforcetracking.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.projects.andreafranco.workforcetracking.BasicApp;
import com.projects.andreafranco.workforcetracking.local.entity.UserEntity;
import com.projects.andreafranco.workforcetracking.model.UserTeam;
import com.projects.andreafranco.workforcetracking.repository.DataRepository;

import java.util.List;

public class UserListViewModel extends AndroidViewModel {

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<UserEntity>> mObservableUsers;
    private final DataRepository mRepository;
    private final MediatorLiveData<List<UserTeam>> mObservableUserTeams;

    public UserListViewModel(@NonNull Application application, DataRepository repository, int userId, int teamId) {
        super(application);
        mRepository = repository;
        mObservableUsers = new MediatorLiveData<>();
        mObservableUserTeams = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableUsers.setValue(null);

        LiveData<List<UserEntity>> users = mRepository.getUsers();
        LiveData<List<UserTeam>> team = mRepository.getUserTeam(teamId);

        // observe the changes of the users from the database and forward them
        mObservableUsers.addSource(users, mObservableUsers::setValue);
        mObservableUserTeams.addSource(team, mObservableUserTeams::setValue);
    }

    /**
     * Expose the LiveData User query so the UI can observe it. Ex user list fragment
     */
    public LiveData<List<UserEntity>> getUsers() {
        return mObservableUsers;
    }

    public LiveData<List<UserTeam>> getUserTeam() {
        return mObservableUserTeams;
    }

    /**
     * A creator is used to inject the product ID into the ViewModel
     * <p>
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the product ID can be passed in a public method.
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final DataRepository mRepository;
        private final int mUserId;
        private final int mTeamId;

        public Factory(@NonNull Application application, int userId, int teamId) {
            mApplication = application;
            mRepository = ((BasicApp) application).getLocalUserRepository();
            mUserId = userId;
            mTeamId = teamId;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new UserListViewModel(mApplication, mRepository, mUserId, mTeamId);
        }
    }
}

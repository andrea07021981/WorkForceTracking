package com.projects.andreafranco.workforcetracking.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.projects.andreafranco.workforcetracking.BasicApp;
import com.projects.andreafranco.workforcetracking.local.entity.UserEntity;
import com.projects.andreafranco.workforcetracking.model.UserTeam;
import com.projects.andreafranco.workforcetracking.repository.DataRepository;

public class UserViewModel extends AndroidViewModel {

    //TODO USE THIS MODEL FOR EDIT/VIEW USER PROFILE IN FRAGMENT
    private final LiveData<UserEntity> mObservableUser;

    public ObservableField<UserEntity> mUser = new ObservableField<>();

    private final long mUserId;
    private final DataRepository mRepository;

    public UserViewModel(@NonNull Application application, DataRepository repository,
                            final long userId) {
        super(application);
        mUserId = userId;
        mRepository = repository;
        mObservableUser = repository.getUserById(mUserId);
    }

    public void setUser(UserEntity user) {
        this.mUser.set(user);
    }

    public void createUser(UserEntity user) {
        mRepository.insertUser(user);
    }

    public void getUser(UserEntity user) {
        mRepository.getUserById(user.getId());
    }

    public LiveData<UserEntity> checkValidLogin(String username, String password) {
        return mRepository.isValidAccount(username, password);
    }
    /**
     * Expose the LiveData Comments query so the UI can observe it.
     */

    public LiveData<UserEntity> getObservableUser() {
        return mObservableUser;
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

        private final long mUserId;

        private final DataRepository mRepository;

        public Factory(@NonNull Application application, long userId) {
            mApplication = application;
            mUserId = userId;
            mRepository = ((BasicApp) application).getLocalUserRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new UserViewModel(mApplication, mRepository, mUserId);
        }
    }
}

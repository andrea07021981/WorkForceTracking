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
import com.projects.andreafranco.workforcetracking.repository.DataRepository;

import java.util.List;

public class UserListViewModel extends AndroidViewModel {

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<UserEntity>> mObservableUsers;
    private final DataRepository mRepository;

    public UserListViewModel(@NonNull Application application, DataRepository repository) {
        super(application);
        mRepository = repository;
        mObservableUsers = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableUsers.setValue(null);

        LiveData<List<UserEntity>> users = mRepository.getUsers();

        // observe the changes of the users from the database and forward them
        mObservableUsers.addSource(users, mObservableUsers::setValue);
    }

    /**
     * Expose the LiveData User query so the UI can observe it. Ex user list fragment
     */
    public LiveData<List<UserEntity>> getUsers() {
        return mObservableUsers;
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

        public Factory(@NonNull Application application) {
            mApplication = application;
            mRepository = ((BasicApp) application).getLocalUserRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new UserListViewModel(mApplication, mRepository);
        }
    }
}

package com.projects.andreafranco.workforcetracking.repository;

import com.projects.andreafranco.workforcetracking.local.UserDao;
import com.projects.andreafranco.workforcetracking.local.UserEntry;

import java.util.List;
import java.util.concurrent.Executor;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public class LocalUserRepositoryImpl implements LocalUserRepository{

    private UserDao mUserDaoDAO;
    private Executor mExecutor;


    public LocalUserRepositoryImpl(UserDao mUserDaoDAO, Executor mExecutor) {
        this.mUserDaoDAO = mUserDaoDAO;
        this.mExecutor = mExecutor;
    }

    @Override

    public Flowable<List<UserEntry>> getAllUsers() {
        return mUserDaoDAO.getAllUsers();
    }

    @Override
    public Single<UserEntry> getUserById(long id) {
        return mUserDaoDAO.getUserById(id);
    }

    @Override
    public Maybe<UserEntry> getUserByUserAndPass(String username, String password) {
        return mUserDaoDAO.getUserByUserAndPass(username, password);
    }

    @Override
    public void insertUser(final UserEntry userEntry) {
        mExecutor.execute(() -> {
            mUserDaoDAO.insertUser(userEntry);
        });
    }

    @Override
    public void deleteUser(UserEntry userEntry) {
        mExecutor.execute(() -> {
            mUserDaoDAO.deleteUser(userEntry);
        });
    }
}

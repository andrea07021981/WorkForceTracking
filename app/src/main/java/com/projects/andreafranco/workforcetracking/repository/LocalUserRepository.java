package com.projects.andreafranco.workforcetracking.repository;

import com.projects.andreafranco.workforcetracking.local.UserEntry;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface LocalUserRepository {
    public Flowable<List<UserEntry>> getAllUsers();
    public Single<UserEntry> getUserById(long id);
    public Maybe<UserEntry> getUserByUserAndPass(String username, String password);
    public void insertUser(UserEntry userEntry);
    public void deleteUser(UserEntry userEntry);
}

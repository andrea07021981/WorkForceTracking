package com.projects.andreafranco.workforcetracking.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user ORDER BY name")
    Flowable<List<UserEntry>> getAllUsers();

    @Query("SELECT * FROM user WHERE id = :id")
    Single<UserEntry> getUserById(long id);

    @Query("SELECT * FROM user WHERE userName = :username AND password = :password")
    Maybe<UserEntry> getUserByUserAndPass(String username, String password);

    @Insert
    void insertUser(UserEntry userEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateUser(UserEntry userEntry);

    @Delete
    void deleteUser(UserEntry userEntry);
}

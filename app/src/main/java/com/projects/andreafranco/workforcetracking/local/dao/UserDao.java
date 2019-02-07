package com.projects.andreafranco.workforcetracking.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.projects.andreafranco.workforcetracking.local.entity.UserEntity;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface UserDao {

    //Moved to live data
    /*@Query("SELECT * FROM users ORDER BY name")
    Flowable<List<UserEntity>> getAllUsers();

    @Query("SELECT * FROM users WHERE id = :id")
    Single<UserEntity> getUserById(long id);

    @Query("SELECT * FROM users WHERE userName = :username AND password = :password")
    Maybe<UserEntity> getUserByUserAndPass(String username, String password);*/

    @Query("SELECT * FROM users ORDER BY name")
    LiveData<List<UserEntity>> getAllUsers();

    @Query("SELECT * FROM users WHERE id = :id")
    LiveData<UserEntity> getUserById(long id);

    @Query("SELECT * FROM users WHERE userName = :username AND password = :password")
    LiveData<UserEntity> getUserByUserAndPass(String username, String password);

    @Insert
    void insertUser(UserEntity userEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllUsers(List<UserEntity> usersList);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateUser(UserEntity userEntity);

    @Delete
    void deleteUser(UserEntity userEntity);
}

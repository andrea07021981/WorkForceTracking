package com.projects.andreafranco.workforcetracking.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import com.projects.andreafranco.workforcetracking.local.AppDatabase;
import com.projects.andreafranco.workforcetracking.local.dao.UserDao;
import com.projects.andreafranco.workforcetracking.local.entity.UserEntity;
import com.projects.andreafranco.workforcetracking.model.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class SimpleEntityReadWriteTest {

    private UserDao mUserDao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        Context context = getApplicationContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mUserDao = mDb.userDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
        UserEntity user = new UserEntity(1, "Andrea", "Franco", "Franco", "Franco", "Franco", 1, 1, 1, ,1, new Date());
        long value = mUserDao.insertUser(user);
        LiveData<UserEntity> byName = mUserDao.getUserById(value);
        assertThat(byName.getValue(), equalTo(user));
    }
}

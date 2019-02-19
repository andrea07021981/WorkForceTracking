package com.projects.andreafranco.workforcetracking.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.projects.andreafranco.workforcetracking.local.entity.UserEntity;
import com.projects.andreafranco.workforcetracking.model.UserTeam;

import java.util.List;

@Dao
public interface UserTeamDao {

    @Query("SELECT users.name AS username, users.surname AS surname, users.image AS image, users.latitude AS latitude, users.longitude AS longitude, teams.name AS team, shifts.name AS shift " +
            "FROM users " +
            "INNER JOIN teams ON users.teamid = teams.id " +
            "INNER JOIN shifts on users.shiftid = shifts.id " +
            "WHERE teams.id = :teamId")
    LiveData<List<UserTeam>> getTeamOfUser(int teamId);
}

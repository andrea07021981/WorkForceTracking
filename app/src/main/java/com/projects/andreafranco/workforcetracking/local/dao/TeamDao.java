package com.projects.andreafranco.workforcetracking.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.projects.andreafranco.workforcetracking.local.entity.TeamEntity;
import com.projects.andreafranco.workforcetracking.local.entity.UserEntity;

import java.util.List;

@Dao
public interface TeamDao {

    @Query("SELECT * FROM teams ORDER BY name")
    LiveData<List<TeamEntity>> getAllTeams();

    @Insert
    public long insertTeam(TeamEntity teamEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertAllTeams(List<TeamEntity> teamsList);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTeam(TeamEntity teamEntity);

    @Delete
    public int deleteTeam(TeamEntity teamEntity);
}

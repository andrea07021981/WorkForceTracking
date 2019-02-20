package com.projects.andreafranco.workforcetracking.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.projects.andreafranco.workforcetracking.local.entity.FunctionEntity;
import com.projects.andreafranco.workforcetracking.local.entity.ShiftEntity;

import java.util.List;

@Dao
public interface FunctionDao {

    @Query("SELECT * FROM functions ORDER BY name")
    LiveData<List<FunctionEntity>> getAllFunctions();

    @Insert
    public long insertFunction(FunctionEntity functionEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertAllFunctions(List<FunctionEntity> functionEntities);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFunction(FunctionEntity functionEntity);

    @Delete
    public int deleteFunction(FunctionEntity functionEntity);
}

package com.projects.andreafranco.workforcetracking.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.projects.andreafranco.workforcetracking.local.entity.ShiftEntity;
import com.projects.andreafranco.workforcetracking.local.entity.TeamEntity;
import com.projects.andreafranco.workforcetracking.model.Shift;

import java.util.List;

@Dao
public interface ShiftDao {

    @Query("SELECT * FROM shifts ORDER BY name")
    LiveData<List<ShiftEntity>> getAllShift();

    @Insert
    public long insertShift(ShiftEntity shiftEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertAllShifts(List<ShiftEntity> shiftEntities);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateShift(ShiftEntity shiftEntity);

    @Delete
    public int deleteShift(ShiftEntity shiftEntity);
}

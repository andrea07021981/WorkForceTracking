package com.projects.andreafranco.workforcetracking.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.projects.andreafranco.workforcetracking.model.Shift;
import com.projects.andreafranco.workforcetracking.model.Team;

@Entity(tableName = "shifts", indices = {@Index("id")})
public class ShiftEntity implements Shift {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    private int id;

    @NonNull
    @SerializedName("name")
    private String name;

    @NonNull
    @SerializedName("status")
    private int status;

    @Ignore
    public ShiftEntity(@NonNull String name) {
        this.name = name;
    }

    public ShiftEntity(int id,
                       @NonNull String name,
                       @NonNull int status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getStatus() {
        return status;
    }
}

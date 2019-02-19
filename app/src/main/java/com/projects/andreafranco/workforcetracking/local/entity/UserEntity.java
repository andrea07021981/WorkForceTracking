package com.projects.andreafranco.workforcetracking.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.location.Location;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.projects.andreafranco.workforcetracking.model.User;

import java.util.Date;

@Entity(tableName = "users", indices = {@Index(value="teamid"),@Index(value="shiftid")},
        foreignKeys = {
            @ForeignKey(entity = TeamEntity.class, parentColumns = "id", childColumns = "teamid", onDelete = ForeignKey.NO_ACTION),
            @ForeignKey(entity = ShiftEntity.class, parentColumns = "id", childColumns = "shiftid", onDelete = ForeignKey.NO_ACTION)})

public class UserEntity implements User {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    private int id;

    @NonNull
    @SerializedName("name")
    private String name;

    @NonNull
    @SerializedName("surname")
    private String surname;

    @NonNull
    @SerializedName("email")
    private String email;

    @NonNull
    @SerializedName("username")
    private String username;

    @NonNull
    @SerializedName("password")
    private String password;

    @NonNull
    @SerializedName("image")
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    @NonNull
    @SerializedName("latitude")
    private Double latitude;

    @NonNull
    @SerializedName("longitude")
    private Double longitude;

    @NonNull
    @SerializedName("teamid")
    private int teamid;

    @NonNull
    @SerializedName("shiftid")
    private int shiftid;

    @ColumnInfo(name="updated_at")
    private Date updatedAt;

    @Ignore
    public UserEntity(@NonNull String name, @NonNull String surname, @NonNull String username, @NonNull String email, @NonNull String password, byte[] image, Double latitude, Double longitude, Date updatedAt) {
        this.name = name;
        this.surname = surname;
        this.username= username;
        this.email = email;
        this.password = password;
        this.updatedAt = updatedAt;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public UserEntity(int id,
                      @NonNull String name,
                      @NonNull String surname,
                      @NonNull String username,
                      @NonNull String email,
                      @NonNull String password,
                      byte[] image,
                      Double latitude,
                      Double longitude,
                      int teamid,
                      int shiftid,
                      Date updatedAt) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username= username;
        this.email = email;
        this.password = password;
        this.image = image;
        this.updatedAt = updatedAt;
        this.latitude = latitude;
        this.longitude = longitude;
        this.teamid = teamid;
        this.shiftid = shiftid;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @Override
    @NonNull
    public String getSurname() {
        return surname;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @Override
    @NonNull
    public String getUsername() {
        return username;
    }

    public void setSurname(@NonNull String surname) {
        this.surname = surname;
    }

    @Override
    @NonNull
    public String getEmail() {
        return email;
    }

    @Override
    @NonNull
    public byte[] getImage() {
        return image;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @Override
    @NonNull
    public String getPassword() {
        return password;
    }

    @Override
    @NonNull
    public Double getLatitude() {
        return latitude;
    }

    @Override
    @NonNull
    public Double getLongitude() {
        return longitude;
    }

    @Override
    public int getTeamid() {
        return teamid;
    }

    @Override
    public int getShiftid() {
        return shiftid;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @Override
    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}

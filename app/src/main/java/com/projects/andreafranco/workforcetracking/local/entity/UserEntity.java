package com.projects.andreafranco.workforcetracking.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.projects.andreafranco.workforcetracking.model.User;

import java.util.Date;

@Entity(tableName = "users")
public class UserEntity implements User {

    @PrimaryKey
    @SerializedName("id")
    private long id;

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
    @SerializedName("userName")
    private String userName;

    @NonNull
    @SerializedName("password")
    private String password;

    @ColumnInfo(name="update_at")
    private Date updatedAt;

    @Ignore
    public UserEntity(@NonNull String name, @NonNull String surname, @NonNull String email, @NonNull String password, Date updatedAt) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.updatedAt = updatedAt;
    }

    public UserEntity(long id, @NonNull String name, @NonNull String surname, @NonNull String email, @NonNull String password, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.updatedAt = updatedAt;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public void setSurname(@NonNull String surname) {
        this.surname = surname;
    }

    @Override
    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @Override
    @NonNull
    public String getPassword() {
        return password;
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

    @Override
    @NonNull
    public String getUserName() {
        return userName;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }
}

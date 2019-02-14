package com.projects.andreafranco.workforcetracking.model;

import android.support.annotation.NonNull;

import java.util.Date;

public interface User {
    int getId();
    String getName();
    String getSurname();
    String getUsername();
    String getEmail();
    byte[] getImage();
    String getPassword();
    Date getUpdatedAt();
}

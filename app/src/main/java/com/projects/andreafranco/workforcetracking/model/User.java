package com.projects.andreafranco.workforcetracking.model;

import android.support.annotation.NonNull;

import java.util.Date;

public interface User {
    long getId();
    String getName();
    String getSurname();
    String getUserName();
    String getEmail();
    String getPassword();
    Date getUpdatedAt();
}

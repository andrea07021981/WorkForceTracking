package com.projects.andreafranco.workforcetracking.model;

import android.location.Location;
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
    Double getLatitude();
    Double getLongitude();
    int getTeamid();
    int getShiftid();
    Date getUpdatedAt();
}

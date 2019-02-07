package com.projects.andreafranco.workforcetracking.local;

import com.projects.andreafranco.workforcetracking.local.entity.UserEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DataGenerator {

    private static final String[] USER_NAME = new String[]{
            "Andrea", "Pippo", "Lou"};

    private static final String[] USER_SURNAME = new String[]{
            "Franco", "Rossi", "Bianchi"};

    private static final String[] USER_EMAIL = new String[]{
            "a@a.com", "b@a.com", "c@a.com"};

    private static final String[] USER_USERNAME = new String[]{
            "Andrea", "Pippo", "Lou"};

    private static final String[] USER_PSW = new String[]{
            "andrea", "pippo", "lou"};

    public static List<UserEntity> generateUsers() {
        List<UserEntity> users = new ArrayList<>();
        Random rnd = new Random();
        for (int i = 0; i < USER_NAME.length; i++) {
            UserEntity user = new UserEntity(
                    USER_NAME[i],
                    USER_SURNAME[i],
                    USER_USERNAME[i],
                    USER_EMAIL[i],
                    USER_PSW[i],
                    new Date());
            users.add(user);
        }
        return users;
    }
}

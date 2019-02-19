package com.projects.andreafranco.workforcetracking.local;

import com.projects.andreafranco.workforcetracking.local.entity.ShiftEntity;
import com.projects.andreafranco.workforcetracking.local.entity.TeamEntity;
import com.projects.andreafranco.workforcetracking.local.entity.UserEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DataGenerator {

    //Users
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

    private static final int[] USER_TEAM = new int[]{
            1,2,3};

    private static final int[] USER_SHIFT = new int[]{
            1,2,3};

    //Teams
    private static final String[] TEAM_NAME = new String[]{
            "one", "two", "three"};

    //Shifts
    private static final String[] SHIFT_NAME = new String[]{
            "ON SHIFT", "OFF SHIFT", "BREAK", "NOT AVAILABLE"};

    public static List<UserEntity> generateUsers() {
        List<UserEntity> users = new ArrayList<>();
        int id = 1;
        for (int i = 0; i < USER_NAME.length; i++) {
            UserEntity user = new UserEntity(
                    id,
                    USER_NAME[i],
                    USER_SURNAME[i],
                    USER_USERNAME[i],
                    USER_EMAIL[i],
                    USER_PSW[i],
                    new byte[0],
                    0.0,
                    0.0,
                    USER_TEAM[i],
                    USER_SHIFT[i],
                    new Date());
            users.add(user);
            id++;
        }
        return users;
    }

    public static List<TeamEntity> generateTeams() {
        List<TeamEntity> teams = new ArrayList<>();
        int id = 1;
        for (int i = 0; i < TEAM_NAME.length; i++) {
            TeamEntity team = new TeamEntity(
                    id,
                    TEAM_NAME[i]);
            teams.add(team);
            id++;
        }
        return teams;
    }

    public static List<ShiftEntity> generateShifts() {
        List<ShiftEntity> shifts = new ArrayList<>();
        int id = 1;
        for (int i = 0; i < SHIFT_NAME.length; i++) {
            ShiftEntity shift = new ShiftEntity(
                    id,
                    SHIFT_NAME[i]);
            shifts.add(shift);
            id++;
        }
        return shifts;
    }
}

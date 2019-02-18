package com.projects.andreafranco.workforcetracking.ui.component;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.projects.andreafranco.workforcetracking.ui.TeamManagementFragment;
import com.projects.andreafranco.workforcetracking.ui.UserListFragment;
import com.projects.andreafranco.workforcetracking.ui.UserMapFragment;

public class TeamManagementPagerAdapter extends FragmentPagerAdapter {

    private static final int NUM_TABS = 2;
    private final int mUserId;

    public TeamManagementPagerAdapter(FragmentManager fm, int userId) {
        super(fm);
        mUserId = userId;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return UserListFragment.newInstance(mUserId);
            case 1:
                return UserMapFragment.newInstance(mUserId);
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_TABS;
    }
}

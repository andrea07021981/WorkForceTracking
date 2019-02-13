package com.projects.andreafranco.workforcetracking.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.projects.andreafranco.workforcetracking.R;

import java.util.ArrayList;

public class DashBoardActivity extends SingleFragmentActivity implements DashBoardFragment.OnDashBoardFragmentInteractionListener {

    private int mUserId;
    private ArrayList<Integer> mMenuImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mUserId = extras.getInt(LoginFragment.USER_ID);
        }
        setTitle(R.string.dashboard_title);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        return DashBoardFragment.newInstance(mUserId);
    }

    @Override
    protected void setupToolbar(boolean visible) {
        //Use binding for toolbar or menu with user info and profile
        getSupportActionBar().setDisplayHomeAsUpEnabled(visible);
        getSupportActionBar().setDisplayShowHomeEnabled(visible);
    }

    @Override
    public void onFragmentInteraction() {
        //TODO actions
    }
}

package com.projects.andreafranco.workforcetracking.ui;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.projects.andreafranco.workforcetracking.R;

public class TeamManagementActivity extends SingleFragmentActivity implements
        TeamManagementFragment.OnTeamManagementFragmentInteractionListener,
        UserListFragment.OnUserListFragmentInteractionListener,
        UserMapFragment.OnUserMapFragmentInteractionListener{

    private int mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mUserId = extras.getInt(LoginFragment.USER_ID);
        }
        super.onCreate(savedInstanceState);
        setupToolbar(true);
        setTitle(R.string.team_management);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void onBackPressed() {
        finish();
    }

    @Override
    protected Fragment createFragment() {
        return TeamManagementFragment.newInstance(mUserId);
    }

    @Override
    protected void setupToolbar(boolean visible) {
        //Use binding for toolbar or menu with user info and profile
        getSupportActionBar().setDisplayHomeAsUpEnabled(visible);
        getSupportActionBar().setDisplayShowHomeEnabled(visible);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onUserItemSelected(Uri uri) {

    }

    @Override
    public void onMapClick(Uri uri) {

    }
}

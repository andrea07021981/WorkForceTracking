package com.projects.andreafranco.workforcetracking.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import com.projects.andreafranco.workforcetracking.R;

import static com.projects.andreafranco.workforcetracking.ui.UserListFragment.USER_TEAM;

public class CalendarActivity extends SingleFragmentActivity implements CalendarFragment.OnCalendarFragmentInteractionListener {

    private int mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mUserId = extras.getInt(USER_TEAM);
        }
        super.onCreate(savedInstanceState);
        setupToolbar(true);
        setTitle(R.string.jobs_scheduled);
    }

    public void onBackPressed() {
        //This allows the previous activity to animate the shared components.
        //Prev activity must have windowContentTransitions = "true"
        ActivityCompat.finishAfterTransition(this);
    }

    @Override
    protected Fragment createFragment() {
        return CalendarFragment.newInstance(mUserId);
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
}

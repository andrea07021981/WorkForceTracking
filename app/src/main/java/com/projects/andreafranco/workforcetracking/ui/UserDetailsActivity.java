package com.projects.andreafranco.workforcetracking.ui;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.projects.andreafranco.workforcetracking.R;

public class UserDetailsActivity extends SingleFragmentActivity implements UserDetailsFragment.OnUserDetailsFragmentInteractionListener {

    private int mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mUserId = extras.getInt(LoginFragment.USER_ID);
        }
        super.onCreate(savedInstanceState);
        setupToolbar(true);
        setTitle(R.string.my_team);
    }

    public void onBackPressed() {
        finish();
    }

    @Override
    protected Fragment createFragment() {
        return UserDetailsFragment.newInstance(mUserId);
    }

    @Override
    protected void setupToolbar(boolean visible) {
        //Use binding for toolbar or menu with user info and profile
        getSupportActionBar().setDisplayHomeAsUpEnabled(visible);
        getSupportActionBar().setDisplayShowHomeEnabled(visible);
    }

    @Override
    public void onUserDetailsInteraction() {

    }
}

package com.projects.andreafranco.workforcetracking.ui;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.Window;

import com.projects.andreafranco.workforcetracking.R;
import com.projects.andreafranco.workforcetracking.model.UserTeam;

import static com.projects.andreafranco.workforcetracking.ui.UserListFragment.USER_TEAM;

public class UserDetailsActivity extends SingleFragmentActivity implements UserDetailsFragment.OnUserDetailsFragmentInteractionListener {

    private UserTeam mUserTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mUserTeam = extras.getParcelable(USER_TEAM);
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
        return UserDetailsFragment.newInstance(mUserTeam);
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

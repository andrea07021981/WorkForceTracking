package com.projects.andreafranco.workforcetracking.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.projects.andreafranco.workforcetracking.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends SingleFragmentActivity implements MainFragment.OnMainFragmentInteractionListener{

    private int mUserId;
    private ArrayList<Integer> mMenuImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mUserId = extras.getInt(LoginFragment.USER_ID);
            //TODO, based on user type (Team leader or others) we'll have differents home pages. Now, we set it up with fixed images
            mMenuImages = new ArrayList<Integer>();
            mMenuImages.addAll(Arrays.asList(R.drawable.team, R.drawable.calendar, R.drawable.materials));
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        return MainFragment.newInstance(mUserId, mMenuImages);
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

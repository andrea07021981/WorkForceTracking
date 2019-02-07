package com.projects.andreafranco.workforcetracking.ui;

import android.support.v4.app.Fragment;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends SingleFragmentActivity implements LoginFragment.OnLoginFragmentInteractionListener {

    @Override
    protected Fragment createFragment() {
        return LoginFragment.newInstance(null);
    }

    @Override
    public void onFragmentInteraction() {

    }
}


package com.projects.andreafranco.workforcetracking.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.transition.Fade;

import com.projects.andreafranco.workforcetracking.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends SingleFragmentActivity implements LoginFragment.OnLoginFragmentInteractionListener, SignUpFragment.OnFragmentInteractionListener {

    @Override
    protected Fragment createFragment() {
        return LoginFragment.newInstance(null);
    }

    @Override
    public void onReplaceFragment() {
        //TODO add the animation transition with shared element LOGO
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,
                SignUpFragment.newInstance(null,null),
                SignUpFragment.class.getSimpleName());
        fragmentTransaction.commit();
        setupToolbar(true);
    }

    @Override
    public void onStartNewActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        setResult(RESULT_CANCELED);
        //TODO add the animation transition with shared element LOGO
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,
                LoginFragment.newInstance(null),
                LoginFragment.class.getSimpleName());
        fragmentTransaction.commit();
        setupToolbar(false);
        return true;
    }

    @Override
    protected void setupToolbar(boolean visible) {
        //Use binding for toolbar or menu with user info and profile
        getSupportActionBar().setDisplayHomeAsUpEnabled(visible);
        getSupportActionBar().setDisplayShowHomeEnabled(visible);
    }
}


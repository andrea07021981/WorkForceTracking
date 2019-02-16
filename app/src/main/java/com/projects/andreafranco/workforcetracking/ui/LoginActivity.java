package com.projects.andreafranco.workforcetracking.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.projects.andreafranco.workforcetracking.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends SingleFragmentActivity implements LoginFragment.OnLoginFragmentInteractionListener, SignUpFragment.OnSignUpInteractionListener {

    @Override
    protected Fragment createFragment() {
        return LoginFragment.newInstance(null);
    }

    @Override
    public void onReplaceFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
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
    public void onBackPressed() {
        moveToFragment();
    }

    @Override
    public boolean onSupportNavigateUp() {
        moveToFragment();
        return true;
    }

    private void moveToFragment() {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("Info message")
                .setMessage("Would you like exit?")
                .setNegativeButton("Cancel", ((dialog, which) -> dialog.dismiss()))
                .setPositiveButton("Ok", (dialog, which) -> goBack())
                .show();
    }

    private void goBack() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fragment_container,
                LoginFragment.newInstance(null),
                LoginFragment.class.getSimpleName());
        fragmentTransaction.commit();
        setupToolbar(false);
    }

    @Override
    protected void setupToolbar(boolean visible) {
        //Use binding for toolbar or menu with user info and profile
        getSupportActionBar().setDisplayHomeAsUpEnabled(visible);
        getSupportActionBar().setDisplayShowHomeEnabled(visible);
    }

    @Override
    public void onSavedUser() {
        goBack();
    }
}


package com.projects.andreafranco.workforcetracking.ui.component;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.projects.andreafranco.workforcetracking.R;

public class CustomDialogClass extends Dialog {

    private Button mYesButton, mNoButton;

    public CustomDialogClass(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        mYesButton = (Button) findViewById(R.id.yes_button);
        mYesButton.setOnClickListener(this::close);
        mNoButton = (Button) findViewById(R.id.no_button);
        mNoButton.setOnClickListener(this::dismiss);
    }

    private void dismiss(View view) {
        dismiss();
    }

    private void close(View view) {
        ((Activity) getContext()).finish();
    }
}

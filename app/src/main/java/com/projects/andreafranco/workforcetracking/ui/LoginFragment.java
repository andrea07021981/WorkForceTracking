package com.projects.andreafranco.workforcetracking.ui;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.andreafranco.workforcetracking.R;
import com.projects.andreafranco.workforcetracking.local.entity.UserEntity;
import com.projects.andreafranco.workforcetracking.viewmodel.UserViewModel;

import java.util.Objects;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class LoginFragment extends Fragment implements View.OnClickListener{

    // the fragment initialization parameters
    private static final String ARG_PARAM1 = "param1";

    private String mParam1;

    private Button mLoginButton;
    private TextView mSignUpTextView;
    private EditText mUsernameEditText, mPasswordEditText;
    private AlertDialog mAlertDialog;

    private UserViewModel mUserViewModel;
    private OnLoginFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment LoginFragment.
     */
    public static LoginFragment newInstance(String param1) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mLoginButton = view.findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(this);
        mSignUpTextView = view.findViewById(R.id.signup_textview);
        mSignUpTextView.setOnClickListener(this);
        mUsernameEditText = view.findViewById(R.id.username_edittext);
        mPasswordEditText = view.findViewById(R.id.password_edittext);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(LayoutInflater.from(getActivity()).inflate(R.layout.progress_dialog, null));
        mAlertDialog = builder.create();

        //TODO NOW IS 0, BUT WE WILL GET THE INFO IF A USER IS ALREADY LOGGED
        UserViewModel.Factory factory = new UserViewModel.Factory(getActivity().getApplication(), 0l);
        mUserViewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);
        subscribeToModel(mUserViewModel);
        return view;
    }

    //TODO MOVE THIS IN EDIT/NEW USER FRAGMENT
    private void subscribeToModel(final UserViewModel model) {
        model.getObservableUser().observe(this, new Observer<UserEntity>() {
            @Override
            public void onChanged(@Nullable UserEntity userEntity) {
                model.setUser(userEntity);
            }
        });
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginFragmentInteractionListener) {
            mListener = (OnLoginFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        if (inputMethodManager.isActive()) inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(getActivity().getCurrentFocus()).getWindowToken(), 0);
        int id = v.getId();
        switch (id) {

            case R.id.login_button:
                if (TextUtils.isEmpty(mUsernameEditText.getText()) ||
                        TextUtils.isEmpty(mPasswordEditText.getText())) {
                    Toast.makeText(getActivity(), "Username and password required", Toast.LENGTH_SHORT).show();
                } else {
                    doLogin(mUsernameEditText.getText().toString(), mPasswordEditText.getText().toString());
                }
                break;

            case R.id.signup_textview:
                doSignUp();
                break;

            default:
                break;
        }
    }

    private void doLogin(String username, String password) {
        mAlertDialog.show();
        mUserViewModel.checkValidLogin(username, password).observe(this, new Observer<UserEntity>() {
            @Override
            public void onChanged(@Nullable UserEntity userEntity) {
                if (userEntity != null) {
                    //TODO move ahead
                } else {
                    Toast.makeText(getActivity(), "Login error: Username or password wrong", Toast.LENGTH_SHORT).show();
                }
                mAlertDialog.dismiss();
            }
        });
    }

    private void doSignUp() {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnLoginFragmentInteractionListener {
        void onFragmentInteraction();
    }
}

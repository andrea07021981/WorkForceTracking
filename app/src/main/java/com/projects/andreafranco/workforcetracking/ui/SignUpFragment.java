package com.projects.andreafranco.workforcetracking.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.transition.TransitionInflater;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.projects.andreafranco.workforcetracking.R;

import java.io.IOException;
import java.util.Random;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignUpFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_IMAGES = 1;
    private static final int REQUEST_PERMISSION_CODE = 0;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView mPictureImageView;
    private EditText mNameEditText, mSurnameEditText, mEmailEditText, mPasswordEditText, mConfirmPasswordEditText;
    private Button mSignupButton;
    private Uri mProfileUri;
    private AlertDialog mAlertDialog;

    private OnFragmentInteractionListener mListener;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        mPictureImageView = view.findViewById(R.id.picture_imageview);
        mPictureImageView.setOnClickListener(this::profileImageClick);
        ((GradientDrawable) mPictureImageView.getBackground()).setColor(getRandonColor());
        mSignupButton = view.findViewById(R.id.signup_button);
        mSignupButton.setOnClickListener(this::signUpClick);
        mNameEditText = view.findViewById(R.id.name_edittext);
        mSurnameEditText = view.findViewById(R.id.surname_edittext);
        mEmailEditText = view.findViewById(R.id.email_edittext);
        mPasswordEditText = view.findViewById(R.id.password_edittext);
        mConfirmPasswordEditText = view.findViewById(R.id.confirm_password_editetext);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(LayoutInflater.from(getActivity()).inflate(R.layout.progress_dialog, null));
        mAlertDialog = builder.create();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
    public void onStop() {
        super.onStop();
        if (mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void signUpClick(View view) {
        if (checkMandatoryField()) {
            //TODO save user on ws
            mAlertDialog.show();
        }
    }

    private boolean checkMandatoryField() {
        boolean result = true;
        if (TextUtils.isEmpty(mNameEditText.getText())) {
            mNameEditText.setError("Name is mandatory!");
            result = false;
        }

        if (TextUtils.isEmpty(mSurnameEditText.getText())) {
            mSurnameEditText.setError("Surname is mandatory!");
            result = false;
        }

        if (TextUtils.isEmpty(mEmailEditText.getText())) {
            mEmailEditText.setError("Email is mandatory!");
            result = false;
        } else if (!isEmail(mEmailEditText)){
            mEmailEditText.setError("Email is wrong formatted!");
            result = false;
        }

        if (TextUtils.isEmpty(mPasswordEditText.getText())) {
            mPasswordEditText.setError("Password is mandatory!");
            result = false;
        }

        if (TextUtils.isEmpty(mConfirmPasswordEditText.getText())) {
            mConfirmPasswordEditText.setError("Confirm password!");
            result = false;
        } else if (!mConfirmPasswordEditText.getText().toString().equals(mPasswordEditText.getText().toString())) {
            mConfirmPasswordEditText.setError("Password is different!");
            result = false;
        }
        return result;
    }

    private boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void getPhoto() {
        Intent getPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(getPhotoIntent, REQUEST_IMAGES);
    }

    public void profileImageClick(View view) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            getPhoto();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
        }
    }

    @NonNull
    private int getRandonColor() {
        Random rand = new Random();
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);
        int randomColor = Color.rgb(r,g,b);
        return randomColor;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1 && grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getPhoto();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGES && resultCode == RESULT_OK) {
            mProfileUri = data.getData();
            //CHANGE dimension and setting
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), mProfileUri);
                mPictureImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

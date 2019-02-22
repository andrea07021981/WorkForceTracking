package com.projects.andreafranco.workforcetracking.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.projects.andreafranco.workforcetracking.R;
import com.projects.andreafranco.workforcetracking.model.UserTeam;
import com.projects.andreafranco.workforcetracking.ui.component.CircleImageView;

/**
 * create an instance of this fragment.
 */
public class UserDetailsFragment extends Fragment implements OnMapReadyCallback {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private UserTeam mUserTeam;
    private GoogleMap mGoogleMap;
    private AppCompatActivity mContext;
    String mNameSurnameFormat;

    private static final float DEFAULT_ZOOM_LEVEL = 15;

    private OnUserDetailsFragmentInteractionListener mListener;

    public UserDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userTeam Parameter 1.
     * @return A new instance of fragment UserDetailsFragment.
     */
    public static UserDetailsFragment newInstance(UserTeam userTeam) {
        UserDetailsFragment fragment = new UserDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, userTeam);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserTeam = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_details, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(mUserTeam.image, 0, mUserTeam.image.length);
        CircleImageView pictureImageView = view.findViewById(R.id.picture_imageview);
        pictureImageView.setImageBitmap(bitmap);

        TextView nameTextView = view.findViewById(R.id.name_textview);
        mNameSurnameFormat = mContext.getString(R.string.format_userinfo);
        nameTextView.setText(String.format(mNameSurnameFormat, mUserTeam.name, mUserTeam.surname));

        TextView funcionTextView = view.findViewById(R.id.function_textview);
        funcionTextView.setText(mUserTeam.userFunction);

        CircleImageView statusImageView = view.findViewById(R.id.status_imageview);
        setShiftStatus(mUserTeam.shiftStatus, statusImageView);
    }

    private void setShiftStatus(int shiftStatusId, CircleImageView imageView) {
        switch (shiftStatusId) {
            case 0://Negative
                imageView.setColorFilter(Color.RED);
                break;
            case 1://Positive
                imageView.setColorFilter(Color.GREEN);
                break;
            case 2://Neutral
                imageView.setColorFilter(Color.GRAY);
                break;
            default:
                imageView.setColorFilter(Color.GRAY);
                break;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        if(getActivity()!=null) {
            SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
            if (mapFragment != null) {
                mapFragment.getMapAsync(this);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUserDetailsFragmentInteractionListener) {
            mListener = (OnUserDetailsFragmentInteractionListener) context;
            mContext = (AppCompatActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnUserDetailsFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        try {
            LatLng latLngUser = new LatLng(mUserTeam.latitude, mUserTeam.longitude);
            MarkerOptions riderRequestMarker = new MarkerOptions();
            riderRequestMarker.position(latLngUser);
            riderRequestMarker.title(String.format(mNameSurnameFormat, mUserTeam.name, mUserTeam.surname));
            riderRequestMarker.anchor(0.5f, 0.5f);
            riderRequestMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mGoogleMap.addMarker(riderRequestMarker);
            CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(latLngUser, DEFAULT_ZOOM_LEVEL);
            mGoogleMap.animateCamera(cu);
        } catch (Exception e) {
            e.printStackTrace();
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
    public interface OnUserDetailsFragmentInteractionListener {
        void onUserDetailsInteraction();
    }
}

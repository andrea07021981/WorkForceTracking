package com.projects.andreafranco.workforcetracking.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.projects.andreafranco.workforcetracking.R;
import com.projects.andreafranco.workforcetracking.model.UserTeam;
import com.projects.andreafranco.workforcetracking.ui.component.CircleImageView;
import com.projects.andreafranco.workforcetracking.viewmodel.UserListViewModel;
import com.projects.andreafranco.workforcetracking.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnUserMapFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserMapFragment extends Fragment implements OnMapReadyCallback {
    private static final String ARG_PARAM1 = "param1";
    private int mUserId;
    private GoogleMap mGoogleMap;
    private AppCompatActivity mContext;

    private static final float DEFAULT_ZOOM_LEVEL = 15;

    private OnUserMapFragmentInteractionListener mListener;

    public UserMapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment UserMapFragment.
     */
    public static UserMapFragment newInstance(int param1) {
        UserMapFragment fragment = new UserMapFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserId = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_map, container, false);
        return view;
    }

    private void subscribeToModel(final UserViewModel model) {
        model.getObservableUser().observe(this, userEntity -> {
            model.setUser(userEntity);
            UserListViewModel.Factory factory = new UserListViewModel.Factory(getActivity().getApplication(), mUserId, userEntity.getTeamid());
            UserListViewModel userListViewModel = ViewModelProviders.of(getParentFragment(), factory).get(UserListViewModel.class);
            userListViewModel.getUserTeam().observe(this, userTeams -> {
                if (userTeams != null && userTeams.size() > 0) {
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (UserTeam user : userTeams) {
                        builder.include(addUserMarker(user));
                    }
                    CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(builder.build().getCenter(), DEFAULT_ZOOM_LEVEL);
                    mGoogleMap.animateCamera(cu);
                }
            });
        });
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
        if (context instanceof OnUserMapFragmentInteractionListener) {
            mContext = (AppCompatActivity) context;
            mListener = (OnUserMapFragmentInteractionListener) context;
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
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        UserViewModel.Factory factory = new UserViewModel.Factory(getActivity().getApplication(), mUserId);
        UserViewModel mUserViewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);
        subscribeToModel(mUserViewModel);
    }

    private LatLng addUserMarker(UserTeam user) {
        LatLng userLocation = new LatLng(user.latitude, user.longitude);
        String nameSurnameFormat = mContext.getString(R.string.format_userinfo);
        MarkerOptions riderRequestMarker = new MarkerOptions();
        riderRequestMarker.position(userLocation);
        riderRequestMarker.title(String.format(nameSurnameFormat, user.name, user.surname));
        riderRequestMarker.anchor(0.5f, 0.5f);

        Bitmap bitmap = BitmapFactory.decodeByteArray(user.image, 0, user.image.length);
        View view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_custom_marker, null);
        CircleImageView imageView = view.findViewById(R.id.profile_imageview);
        riderRequestMarker.icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(view, bitmap, imageView)));

        Marker marker = mGoogleMap.addMarker(riderRequestMarker);
        return marker.getPosition();
    }

    private Bitmap getMarkerBitmapFromView(View view, Bitmap bitmap, CircleImageView imageView) {
        imageView.setImageBitmap(bitmap);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(
                view.getMeasuredWidth(),
                view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = view.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        view.draw(canvas);
        return returnedBitmap;

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
    public interface OnUserMapFragmentInteractionListener {
        // TODO: Update argument type and name
        void onMapClick(Uri uri);
    }
}

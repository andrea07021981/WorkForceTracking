package com.projects.andreafranco.workforcetracking.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projects.andreafranco.workforcetracking.R;
import com.projects.andreafranco.workforcetracking.local.entity.UserEntity;
import com.projects.andreafranco.workforcetracking.ui.component.CircleImageView;
import com.projects.andreafranco.workforcetracking.ui.component.DashBoardRecycleViewAdapter;
import com.projects.andreafranco.workforcetracking.ui.component.SpacesItemDecoration;
import com.projects.andreafranco.workforcetracking.viewmodel.UserViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnDashBoardFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DashBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashBoardFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private int mUserId;

    private OnDashBoardFragmentInteractionListener mListener;

    public DashBoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userId Parameter 1.
     * @return A new instance of fragment DashBoardFragment.
     */
    public static DashBoardFragment newInstance(int userId) {
        DashBoardFragment fragment = new DashBoardFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, userId);
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
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initValues(view);
        return view;
    }

    private void initValues(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.main_recycleview);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        DashBoardRecycleViewAdapter dashBoardRecycleViewAdapter = new DashBoardRecycleViewAdapter(mUserId, getActivity());
        recyclerView.setAdapter(dashBoardRecycleViewAdapter);
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(decoration);
        dashBoardRecycleViewAdapter.notifyDataSetChanged();
        UserViewModel.Factory factory = new UserViewModel.Factory(getActivity().getApplication(), mUserId);
        UserViewModel mUserViewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);
        subscribeToModel(mUserViewModel);

        //TODO add item click on custom adapter.viewholder
        //TODO move this helper to the next list of users in the team
        /*new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {

            }
        }).attachToRecyclerView(recyclerView);*/
    }

    private void subscribeToModel(final UserViewModel model) {
        model.getObservableUser().observe(this, new Observer<UserEntity>() {
            @Override
            public void onChanged(@Nullable UserEntity userEntity) {
                //TODO must use data binding and viewmodel
                model.setUser(userEntity);
                NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
                View headerView = navigationView.getHeaderView(0);
                TextView userInfoTextView = (TextView) headerView.findViewById(R.id.userinfo_textview);
                userInfoTextView.setText(userEntity.getName() + " " + userEntity.getSurname());
                CircleImageView userLogoImageView = getActivity().findViewById(R.id.userlogo_imageView);
                //TODO check why image is not saved (or yes?)
                userLogoImageView.setImageBitmap(BitmapFactory.decodeByteArray(userEntity.getImage(), 0, userEntity.getImage().length));
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDashBoardFragmentInteractionListener) {
            mListener = (OnDashBoardFragmentInteractionListener) context;
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


    public interface OnDashBoardFragmentInteractionListener {
        void onFragmentInteraction();
    }
}

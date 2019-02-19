package com.projects.andreafranco.workforcetracking.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projects.andreafranco.workforcetracking.R;
import com.projects.andreafranco.workforcetracking.local.entity.UserEntity;
import com.projects.andreafranco.workforcetracking.model.DashboardFunction;
import com.projects.andreafranco.workforcetracking.ui.component.CircleImageView;
import com.projects.andreafranco.workforcetracking.ui.component.DashBoardRecycleViewAdapter;
import com.projects.andreafranco.workforcetracking.ui.component.SpacesItemDecoration;
import com.projects.andreafranco.workforcetracking.viewmodel.UserViewModel;

import static com.projects.andreafranco.workforcetracking.ui.LoginFragment.USER_ID;

/**
 * create an instance of this fragment.
 */
public class DashBoardFragment extends Fragment implements DashBoardRecycleViewAdapter.OnItemInteractionListener {
    private static final String ARG_PARAM1 = "param1";
    private int mUserId;

    private OnDashBoardFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private TextView mUserInfoTextView;
    private CircleImageView mUserLogoImageView;

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
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initValues(view);
        return view;
    }

    private void initValues(View view) {
        mUserInfoTextView = view.findViewById(R.id.userinfo_textview);
        mUserLogoImageView = view.findViewById(R.id.userlogo_imageView);
        initRecycleView(view);
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

    private void initRecycleView(View view) {
        mRecyclerView = view.findViewById(R.id.main_recycleview);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        DashBoardRecycleViewAdapter dashBoardRecycleViewAdapter = new DashBoardRecycleViewAdapter(mUserId, getActivity(), this);
        mRecyclerView.setAdapter(dashBoardRecycleViewAdapter);
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        mRecyclerView.addItemDecoration(decoration);
        dashBoardRecycleViewAdapter.notifyDataSetChanged();
    }

    private void subscribeToModel(final UserViewModel model) {
        model.getObservableUser().observe(this, userEntity -> {
            //TODO must use data binding and viewmodel
            model.setUser(userEntity);
            String dimensionFormat = getContext().getString(R.string.format_userinfo);
            mUserInfoTextView.setText(String.format(dimensionFormat, userEntity.getName(), userEntity.getSurname()));
            mUserLogoImageView.setImageBitmap(BitmapFactory.decodeByteArray(userEntity.getImage(), 0, userEntity.getImage().length));
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

    //Events from recycleview
    @Override
    public void onItemSelected(DashboardFunction dashboardFunction) {
        switch (dashboardFunction.getFunctionType()) {
            case DashboardFunction.FUNCTION_TEAM:
                Intent intent = new Intent(getActivity(), TeamManagementActivity.class);
                intent.putExtra(USER_ID, mUserId);
                startActivity(intent);
                break;

            case DashboardFunction.FUNCTION_CALENDAR:
                break;

            case DashboardFunction.FUNCTION_MATERIAL:
                break;

            default:
                break;
        }
    }

    public interface OnDashBoardFragmentInteractionListener {
        void onFragmentInteraction();
    }
}

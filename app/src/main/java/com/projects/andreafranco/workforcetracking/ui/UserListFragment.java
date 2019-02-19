package com.projects.andreafranco.workforcetracking.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projects.andreafranco.workforcetracking.R;
import com.projects.andreafranco.workforcetracking.local.entity.UserEntity;
import com.projects.andreafranco.workforcetracking.model.UserTeam;
import com.projects.andreafranco.workforcetracking.ui.component.DashBoardRecycleViewAdapter;
import com.projects.andreafranco.workforcetracking.ui.component.SpacesItemDecoration;
import com.projects.andreafranco.workforcetracking.ui.component.TeamRecycleViewAdapter;
import com.projects.andreafranco.workforcetracking.viewmodel.UserListViewModel;
import com.projects.andreafranco.workforcetracking.viewmodel.UserViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnUserListFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserListFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private int mUserId;
    private RecyclerView mRecyclerView;

    private OnUserListFragmentInteractionListener mListener;

    public UserListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment UserListFragment.
     */
    public static UserListFragment newInstance(int param1) {
        UserListFragment fragment = new UserListFragment();
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
        View view =  inflater.inflate(R.layout.fragment_user_list, container, false);
        mRecyclerView = view.findViewById(R.id.userlist_recycleview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        TeamRecycleViewAdapter teamRecycleViewAdapter = new TeamRecycleViewAdapter(new ArrayList<UserTeam>());
        mRecyclerView.setAdapter(teamRecycleViewAdapter);
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        mRecyclerView.addItemDecoration(decoration);

        UserViewModel.Factory factory = new UserViewModel.Factory(getActivity().getApplication(), mUserId);
        UserViewModel mUserViewModel = ViewModelProviders.of(this, factory).get(UserViewModel.class);
        subscribeToModel(mUserViewModel);
        return view;
    }

    private void subscribeToModel(final UserViewModel model) {
        model.getObservableUser().observe(this, userEntity -> {
            model.setUser(userEntity);
            UserListViewModel.Factory factory = new UserListViewModel.Factory(getActivity().getApplication(), mUserId, userEntity.getTeamid());
            UserListViewModel userListViewModel = ViewModelProviders.of(getParentFragment(), factory).get(UserListViewModel.class);
            userListViewModel.getUserTeam().observe(this, userTeams -> {
                if (userTeams != null && userTeams.size() > 0) {
                    //TODO update recycleview adapter. Then we'll add databinding and we'll be able to remove this section
                    ((TeamRecycleViewAdapter) mRecyclerView.getAdapter()).addAll(userTeams);
                }
            });
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUserListFragmentInteractionListener) {
            mListener = (OnUserListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnUserListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnUserListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onUserItemSelected(Uri uri);
    }
}

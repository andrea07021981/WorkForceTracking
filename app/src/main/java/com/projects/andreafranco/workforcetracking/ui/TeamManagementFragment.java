package com.projects.andreafranco.workforcetracking.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projects.andreafranco.workforcetracking.R;
import com.projects.andreafranco.workforcetracking.ui.component.TeamManagementPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnTeamManagementFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TeamManagementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamManagementFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private int mUserId;
    private OnTeamManagementFragmentInteractionListener mListener;

    public TeamManagementFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userId Parameter 1.
     * @return A new instance of fragment TeamManagementFragment.
     */
    public static TeamManagementFragment newInstance(int userId) {
        TeamManagementFragment fragment = new TeamManagementFragment();
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
        View view = inflater.inflate(R.layout.fragment_team_management, container, false);

        TabLayout teamTabLayout = view.findViewById(R.id.team_tablayout);
        ViewPager mainContentViewPager = view.findViewById(R.id.main_content_viewpager);
        TeamManagementPagerAdapter teamManagementPagerAdapter = new TeamManagementPagerAdapter(getChildFragmentManager(), mUserId);
        mainContentViewPager.setAdapter(teamManagementPagerAdapter);
        teamTabLayout.setupWithViewPager(mainContentViewPager);
        setupTabs(teamTabLayout);
        return view;
    }

    private void setupTabs(TabLayout teamTabLayout) {
        teamTabLayout.getTabAt(0).setIcon(R.mipmap.ic_list);
        teamTabLayout.getTabAt(1).setIcon(R.mipmap.ic_map);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTeamManagementFragmentInteractionListener) {
            mListener = (OnTeamManagementFragmentInteractionListener) context;
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
    public interface OnTeamManagementFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}

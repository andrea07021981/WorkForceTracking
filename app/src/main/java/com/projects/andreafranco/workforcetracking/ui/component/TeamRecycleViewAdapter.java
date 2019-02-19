package com.projects.andreafranco.workforcetracking.ui.component;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.projects.andreafranco.workforcetracking.R;
import com.projects.andreafranco.workforcetracking.model.UserTeam;

import java.util.ArrayList;
import java.util.List;

public class TeamRecycleViewAdapter extends RecyclerView.Adapter<TeamRecycleViewAdapter.TeamViewHolder> {
    private List<UserTeam> mUserTeamList;

    public TeamRecycleViewAdapter(List<UserTeam> userTeamList) {
        mUserTeamList = userTeamList;
    }

    @Override
    public void onBindViewHolder(TeamViewHolder holder, int position) {
        UserTeam userTeam = mUserTeamList.get(position);
        holder.bindTeamVierwHolder(userTeam);
    }

    @Override
    public int getItemCount() {
        return mUserTeamList.size();
    }

    @Override
    public TeamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item_view,parent, false);
        return new TeamViewHolder(v);
    }

    /**
     * View holder class
     */
    public class TeamViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mImageImageVIew;
        ImageView mStatusImageVIew;
        TextView mNameTextView;
        TextView mFunctionTextView;
        TextView mLocationTextView;
        TextView mShiftTextView;
        UserTeam mUserTeam;

        public TeamViewHolder(View view) {
            super(view);
            mImageImageVIew = (ImageView) view.findViewById(R.id.picture_imageview);
            mStatusImageVIew = (ImageView) view.findViewById(R.id.status_imageview);
            mNameTextView = (TextView) view.findViewById(R.id.name_textview);
            mFunctionTextView = (TextView) view.findViewById(R.id.function_textview);
            mLocationTextView = (TextView) view.findViewById(R.id.location_textview);
            mShiftTextView = (TextView) view.findViewById(R.id.shift_textview);
        }

        public void bindTeamVierwHolder(UserTeam userTeam) {
            mUserTeam = userTeam;
        }

        @Override
        public void onClick(View v) {
            //TODO action on click
        }
    }

    /**
     * Clears all items from the data set.
      */
    public void clear(){
        if(mUserTeamList != null){
            mUserTeamList.clear();
            notifyDataSetChanged();
        }
    }

    /*
     * Adds all of the items to the data set.
     * @param items The item array to be added.
     */
    public void addAll(List<UserTeam> items){
        if(mUserTeamList == null){
            mUserTeamList = new ArrayList<>();
        }
        mUserTeamList.addAll(items);
        notifyDataSetChanged();
    }
}

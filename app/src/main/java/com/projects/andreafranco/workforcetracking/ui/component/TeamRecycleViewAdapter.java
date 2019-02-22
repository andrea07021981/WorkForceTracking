package com.projects.andreafranco.workforcetracking.ui.component;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.projects.andreafranco.workforcetracking.Handler.GeocoderHandler;
import com.projects.andreafranco.workforcetracking.R;
import com.projects.andreafranco.workforcetracking.model.UserTeam;
import com.projects.andreafranco.workforcetracking.util.GeoUtils;

import java.util.ArrayList;
import java.util.List;

public class TeamRecycleViewAdapter extends RecyclerView.Adapter<TeamRecycleViewAdapter.TeamViewHolder> {
    private List<UserTeam> mUserTeamList;
    private Context mContext;
    OnUserIterationListener mListener;

    public interface OnUserIterationListener {
        void onUserSelected(UserTeam mUserTeam, Pair<View, String>... p);
    }

    public TeamRecycleViewAdapter(List<UserTeam> userTeamList, Context context, OnUserIterationListener listener) {
        mUserTeamList = userTeamList;
        mContext = context;
        mListener = listener;
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
    public class TeamViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, GeocoderHandler.OnHandleMessageListener{
        ImageView mPictureImageVIew;
        ImageView mStatusImageVIew;
        TextView mNameTextView;
        TextView mFunctionTextView;
        TextView mLocationTextView;
        TextView mShiftTextView;
        UserTeam mUserTeam;

        public TeamViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            mPictureImageVIew = (ImageView) view.findViewById(R.id.picture_imageview);
            mStatusImageVIew = (ImageView) view.findViewById(R.id.status_imageview);
            mNameTextView = (TextView) view.findViewById(R.id.name_textview);
            mFunctionTextView = (TextView) view.findViewById(R.id.function_textview);
            mLocationTextView = (TextView) view.findViewById(R.id.location_textview);
            mShiftTextView = (TextView) view.findViewById(R.id.shift_textview);
        }

        public void bindTeamVierwHolder(UserTeam userTeam) {
            mUserTeam = userTeam;
            mPictureImageVIew.setImageBitmap(BitmapFactory.decodeByteArray(mUserTeam.image, 0, mUserTeam.image.length));
            setShiftStatus(userTeam.shiftStatus);
            String nameSurnameFormat = mContext.getString(R.string.format_userinfo);
            mNameTextView.setText(String.format(nameSurnameFormat, userTeam.name, userTeam.surname));
            mFunctionTextView.setText(userTeam.userFunction);
            getAddressLocation(userTeam.latitude, userTeam.longitude);
            mShiftTextView.setText(userTeam.shift.toUpperCase());
        }

        private void getAddressLocation(Double latitude, Double longitude) {
            GeoUtils.getAddressFromLocation(
                    latitude,
                    longitude,
                    mContext,
                    new GeocoderHandler(this));
        }

        private void setShiftStatus(int shiftStatusId) {
            switch (shiftStatusId) {
                case 0://Negative
                    mStatusImageVIew.setColorFilter(Color.RED);
                    break;
                case 1://Positive
                    mStatusImageVIew.setColorFilter(Color.GREEN);
                    break;
                case 2://Neutral
                    mStatusImageVIew.setColorFilter(Color.GRAY);
                    break;
                default:
                    mStatusImageVIew.setColorFilter(Color.GRAY);
                    break;
            }
        }

        @Override
        public void onClick(View v) {
            Pair<View, String> p1 = Pair.create(mPictureImageVIew, "profile");
            Pair<View, String> p2 = Pair.create(mNameTextView, "name");
            Pair<View, String> p3 = Pair.create(mFunctionTextView, "function");
            Pair<View, String> p4 = Pair.create(mStatusImageVIew, "status");
            mListener.onUserSelected(mUserTeam, p1, p2, p3, p4);
        }

        @Override
        public void handleAddress(String address) {
            mLocationTextView.setText(address);
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

package com.projects.andreafranco.workforcetracking.ui.component;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.projects.andreafranco.workforcetracking.R;
import com.projects.andreafranco.workforcetracking.local.entity.UserEntity;
import com.projects.andreafranco.workforcetracking.model.DashboardFunction;

import java.util.ArrayList;
import java.util.List;

/*public class TeamRecycleViewAdapter extends RecyclerView.Adapter<TeamRecycleViewAdapter.TeamViewHolder> {
    private List<UserEntity> mImageList;

    public TeamRecycleViewAdapter(List<UserEntity> imageList) {
        mImageList = imageList;
    }

    @Override
    public void onBindViewHolder(TeamViewHolder holder, int position) {
        UserEntity image = mImageList.get(position);
        holder.bindTeamVierwHolder(image);
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    @Override
    public TeamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image,parent, false);
        return new TeamViewHolder(v);
    }

    *//**
     * View holder class
     * *//*
    public class TeamViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mImageImageVIew;
        TextView mTitleTextView;
        TextView mSizeTextView;
        TextView mDimensionsTextView;
        UserEntity mImage;

        public TeamViewHolder(View view) {
            super(view);
            mImageImageVIew = (ImageView) view.findViewById(R.id.image_image_view);
            mTitleTextView = (TextView) view.findViewById(R.id.title_text_view);
            mSizeTextView = (TextView) view.findViewById(R.id.size_text_view);
            mDimensionsTextView = (TextView) view.findViewById(R.id.dimensions_text_view);
        }

        public void bindTeamVierwHolder(UserEntity image) {
            mImage = image;
            mImageImageVIew.setImageBitmap(mImage.getImage());
            mTitleTextView.setText(mImage.getTitle());
            String sizeFormat = itemView.getContext().getString(R.string.format_size);
            mSizeTextView.setText(String.format(sizeFormat, mImage.getSize()/1024L));
            String dimensionFormat = itemView.getContext().getString(R.string.format_dimensions);
            mDimensionsTextView.setText(String.format(dimensionFormat, mImage.getWidth(), mImage.getHeight()));
        }

        @Override
        public void onClick(View v) {
            //TODO action on click
        }
    }

    *//**
     * Clears all items from the data set.
     *//*
    public void clear(){
        if(mImageList != null){
            mImageList.clear();
            notifyDataSetChanged();
        }
    }

    *//**
     * Adds all of the items to the data set.
     * @param items The item array to be added.
     *//*
    public void addAll(List<Image> items){
        if(mImageList == null){
            mImageList = new ArrayList<>();
        }
        mImageList.addAll(items);
        notifyDataSetChanged();
    }
}*/

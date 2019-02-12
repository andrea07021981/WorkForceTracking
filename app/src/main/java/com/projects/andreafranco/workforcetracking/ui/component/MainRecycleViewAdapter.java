package com.projects.andreafranco.workforcetracking.ui.component;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.projects.andreafranco.workforcetracking.R;

import java.util.ArrayList;
import java.util.List;

public class MainRecycleViewAdapter extends RecyclerView.Adapter<MainRecycleViewAdapter.MainViewHolder> {
    private List<Integer> mImageList;

    /**
     * View holder class
     * */
    public class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mImageImageVIew;
        int mImageRes;

        public MainViewHolder(View view) {
            super(view);
            mImageImageVIew = (ImageView) view.findViewById(R.id.info_imageview);
        }

        public void bindImage(int imageRes) {
            mImageRes = imageRes;
            mImageImageVIew.setImageResource(imageRes);
        }

        @Override
        public void onClick(View v) {
            //TODO action on click
        }
    }

    public MainRecycleViewAdapter(List<Integer> imageList) {
        mImageList = imageList;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        int imageRes = mImageList.get(position);
        holder.bindImage(imageRes);
    }

    //-------------------

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_item_view, parent, false);
        return new MainViewHolder(v);
    }

    /**
     * Clears all items from the data set.
     */
    public void clear(){
        if(mImageList != null){
            mImageList.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * Adds all of the items to the data set.
     * @param items The item array to be added.
     */
    public void addAll(List<Integer> items){
        if(mImageList == null){
            mImageList = new ArrayList<>();
        }
        mImageList.addAll(items);
        notifyDataSetChanged();
    }
}
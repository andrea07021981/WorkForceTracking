package com.projects.andreafranco.workforcetracking.ui.component;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projects.andreafranco.workforcetracking.R;
import com.projects.andreafranco.workforcetracking.model.DashboardFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DashBoardRecycleViewAdapter extends RecyclerView.Adapter<DashBoardRecycleViewAdapter.DashBoardViewHolder> {
    private final int mUserId;
    private final Context mContext;
    private List<DashboardFunction> mFunctionList;
    private OnItemInteractionListener mListener;

    public DashBoardRecycleViewAdapter(int userId, Context context, Fragment fragment) {
        mUserId = userId;
        mContext = context;
        //TODO, based on user type (Team leader or others) we'll have differents home pages. Now, we set it up with fixed images
        //TODO every item will become a fragment and it will be set it up on every xml with class...
        mFunctionList = new ArrayList<DashboardFunction>();
        mFunctionList.addAll(Arrays.asList(
                new DashboardFunction(DashboardFunction.FUNCTION_TEAM),
                new DashboardFunction(DashboardFunction.FUNCTION_CALENDAR),
                new DashboardFunction(DashboardFunction.FUNCTION_MATERIAL)));

        if (fragment instanceof OnItemInteractionListener) {
            mListener = (OnItemInteractionListener) fragment;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnItemInteractionListener");
        }
    }

    public interface OnItemInteractionListener {
        void onItemSelected(DashboardFunction dashboardFunction);
    }

    @Override
    public void onBindViewHolder(DashBoardViewHolder holder, int position) {
        DashboardFunction dashboardFunction = mFunctionList.get(position);
        holder.bindFunction(dashboardFunction);
    }

    @Override
    public int getItemCount() {
        return mFunctionList.size();
    }

    @Override
    public int getItemViewType(int position) {
        //Retur type based on position
        return mFunctionList.get(position).getFunctionType();
    }

    @NonNull
    @Override
    public DashBoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = null;
        DashBoardViewHolder dashBoardViewHolder = null;

        switch (viewType) {
            case DashboardFunction.FUNCTION_TEAM:
                layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_item_view, parent, false);
                dashBoardViewHolder = new DashBoardViewHolder(layoutView, viewType);
                break;

            case DashboardFunction.FUNCTION_CALENDAR:
                layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_item_view, parent, false);
                dashBoardViewHolder = new DashBoardViewHolder(layoutView, viewType);
                break;

            case DashboardFunction.FUNCTION_MATERIAL:
                layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.materials_item_view, parent, false);
                dashBoardViewHolder = new DashBoardViewHolder(layoutView, viewType);
                break;

            default:
                break;
        }
        return dashBoardViewHolder;
    }

    /**
     * Clears all items from the data set.
     */
    public void clear(){
        if(mFunctionList != null){
            mFunctionList.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * Adds all of the items to the data set.
     * @param items The item array to be added.
     */
    public void addAll(List<DashboardFunction> items){
        if(mFunctionList == null){
            mFunctionList = new ArrayList<>();
        }
        mFunctionList.addAll(items);
        notifyDataSetChanged();
    }


    /**
     * View holder class
     * */
    public class DashBoardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        DashboardFunction mDashboardFunction;
        CardView mLayout;

        public DashBoardViewHolder(View view, @DashboardFunction.FunctionType int type) {
            super(view);
            mLayout = (CardView) view.findViewById(R.id.card_view);
            view.setOnClickListener(this::onClick);
        }

        public void bindFunction(DashboardFunction function) {
            StaggeredGridLayoutManager.LayoutParams layoutParams;
            switch (function.getFunctionType()) {
                case DashboardFunction.FUNCTION_TEAM:
                    layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0,0,0, 5);
                    //Expand the item
                    layoutParams.setFullSpan(true);
                    mLayout.setLayoutParams(layoutParams);
                    break;

                case DashboardFunction.FUNCTION_CALENDAR:
                    layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0,0,0, 5);
                    mLayout.setLayoutParams(layoutParams);
                    break;

                case DashboardFunction.FUNCTION_MATERIAL:
                    layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0,0,0, 5);
                    mLayout.setLayoutParams(layoutParams);
                    break;

                default:
                    break;
            }
            mDashboardFunction = function;
        }

        @Override
        public void onClick(View v) {
            switch (mFunctionList.get(getAdapterPosition()).getFunctionType()) {
                case DashboardFunction.FUNCTION_TEAM:
                    mListener.onItemSelected(mFunctionList.get(getAdapterPosition()));
                    break;

                case DashboardFunction.FUNCTION_CALENDAR:

                    break;

                case DashboardFunction.FUNCTION_MATERIAL:
                    break;

                default:
                    break;
            }
        }
    }
}
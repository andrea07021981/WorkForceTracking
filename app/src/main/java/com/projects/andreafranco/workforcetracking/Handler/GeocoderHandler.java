package com.projects.andreafranco.workforcetracking.Handler;

import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.projects.andreafranco.workforcetracking.ui.UserListFragment;
import com.projects.andreafranco.workforcetracking.ui.component.TeamRecycleViewAdapter;

import java.lang.ref.WeakReference;
import java.util.Objects;

public class GeocoderHandler extends Handler {

    private OnHandleMessageListener mListener;

    private final WeakReference<RecyclerView.ViewHolder> mCaller;

    public GeocoderHandler(RecyclerView.ViewHolder viewHolder) {
        mCaller = new WeakReference<RecyclerView.ViewHolder>(viewHolder);
        if (viewHolder instanceof OnHandleMessageListener) {
            mListener = (OnHandleMessageListener) viewHolder;
        } else {
            throw new RuntimeException("Caller must implement OnHandleMessageListener");
        }
    }

    @Override
    public void handleMessage(Message message) {
        String formattedAddress = null;
        switch (message.what) {
            case 1:
                Bundle bundle = message.getData();
                Address originAddress = bundle.getParcelable("address");
                formattedAddress = getFormattedAddress(Objects.requireNonNull(originAddress));
                Log.e("location Address=", originAddress.getLocality());
                RecyclerView.ViewHolder viewHolder = mCaller.get();
                if (viewHolder instanceof TeamRecycleViewAdapter.TeamViewHolder) {
                    mListener.handleAddress(formattedAddress);
                }
                break;
            default:
                break;
        }
    }

    private String getFormattedAddress(@NonNull Address locationAddress) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < locationAddress.getMaxAddressLineIndex(); i++) {
            sb.append(locationAddress.getAddressLine(i)); //.append("\n");
        }
        sb.append(locationAddress.getLocality()).append("\n");
        sb.append(locationAddress.getPostalCode()).append("\n");
        sb.append(locationAddress.getCountryName());
        return sb.toString();
    }

    public interface OnHandleMessageListener {
        void handleAddress(String address);
    }
}

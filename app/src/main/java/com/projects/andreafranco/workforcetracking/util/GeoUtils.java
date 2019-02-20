package com.projects.andreafranco.workforcetracking.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.projects.andreafranco.workforcetracking.BuildConfig;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeoUtils {

    private static final String TAG = GeoUtils.class.getSimpleName();

    public static void getAddressFromLocation(final double latitude, final double longitude, final Context context, final Handler handler) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                Address address = null;
                try {
                    List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        address = addressList.get(0);
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Unable connect to Geocoder", e);
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (address != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("address", address);
                        message.setData(bundle);
                    } else {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("address", address);
                        message.setData(bundle);
                    }

                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }

    public static Location getLastKnownPosition(LocationManager locationManager) {
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        if (bestLocation == null && BuildConfig.DEBUG) {
            LatLng latLng = new LatLng(46.097732, 13.230475);
            bestLocation = new Location("");
            bestLocation.setLatitude(latLng.latitude);
            bestLocation.setLongitude(latLng.longitude);
        }
        return bestLocation;
    }
}

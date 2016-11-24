package com.example.dreamcast.KiteFlightAndroidShvaiba.additional;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Dreamcast on 20.10.2016.
 */

public class OtherMethods {

    //check internet connection
    public static boolean checkConnection(final Context context)
    {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        return false;
    }
}


//    public void onClickLogo(View v) { // \
//        // Create a system to run the physics loop for a set of springs.
//        SpringSystem springSystem = SpringSystem.create();
//
//        // Add a spring to the system.
//        Spring spring = springSystem.createSpring();
//
//        // Add a listener to observe the motion of the spring.
//        spring.addListener(new SimpleSpringListener() {
//
//            @Override
//            public void onSpringUpdate(Spring spring) {
//                // You can observe the updates in the spring
//                // state by asking its current value in onSpringUpdate.
//                float value = (float) spring.getCurrentValue();
//                float scale = 1f - (value * 0.15f);
//                imageView.setScaleX(scale);
//                imageView.setScaleY(scale);
//            }
//        });
//
//        // Set the spring in motion; moving from 0 to 1
//        spring.setEndValue(1);
//    }
package com.tennismate.tennismate.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LocationSharingBroadcastReceiver extends BroadcastReceiver
{
    public static final String  ACTION_SHARE_LOCATION = "ACTION_SHARE_LOCATION";
    public static final int  REQUEST_CODE = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, LocationSharingIntentService.class);
        i.setAction(ACTION_SHARE_LOCATION);
        context.startService(i);
    }
}

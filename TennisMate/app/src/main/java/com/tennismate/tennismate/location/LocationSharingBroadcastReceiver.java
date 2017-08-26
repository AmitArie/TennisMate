package com.tennismate.tennismate.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LocationSharingBroadcastReceiver extends BroadcastReceiver
{
    public static final int     REQUEST_CODE = 12345;
    public static final String  ACTION = "ACTION_SHARE_LOCATION";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, LocationSharingIntentService.class);
        i.putExtra("foo", "bar");
        i.setAction(ACTION);
        context.startService(i);
    }
}

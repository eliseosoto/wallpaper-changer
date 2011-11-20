package com.supervaca.wallpaperChanger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class WallpaperBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Schedule the alarm here
        Toast.makeText(context, "Wallpaper on Boot!", Toast.LENGTH_LONG).show();
        Log.i("context", context.toString());
        Log.i("intent", intent.toString());
    }
}

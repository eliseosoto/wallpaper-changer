package com.supervaca.wallpaperChanger;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class WallpaperService extends Service {
    public static final String REFRESH_WALLPAPER = "com.supervaca.wallpaperChanger.REFRESH_WALLPAPER";

    @Override
    public void onCreate() {
        String text = "WallpaperService created!";
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Launch background thread

        //stopSelf(startId);
        Toast.makeText(getApplicationContext(), "onStartCommand!", Toast.LENGTH_SHORT).show();

        return Service.START_NOT_STICKY;
    }
}

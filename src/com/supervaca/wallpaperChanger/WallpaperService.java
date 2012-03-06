package com.supervaca.wallpaperChanger;

import java.io.File;
import java.util.Random;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
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
    	String imageFileName = pickRandomFileFromDirectory();

        //stopSelf(startId);
        Toast.makeText(getApplicationContext(), imageFileName, Toast.LENGTH_SHORT).show();

        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        String text = "WallpaperService stopped!";
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
    
    private String pickRandomFileFromDirectory() {
    	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    	String selectedDirectory = prefs.getString("SELECTED_DIRECTORY", "");
    	
    	File file = new File(selectedDirectory);
    	File[] files = file.listFiles();
    	
    	Random rand = new Random();
    	
    	return files[rand.nextInt(files.length)].getName();
    }
}

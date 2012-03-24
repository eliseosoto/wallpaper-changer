package com.supervaca.wallpaperChanger;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Random;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
		File imageFile = pickRandomFileFromDirectory();
		Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());

		try {
			getApplicationContext().setWallpaper(bitmap);
		} catch (IOException e) {
			return Service.START_FLAG_RETRY;
		}

		Toast.makeText(getApplicationContext(), imageFile.getName(), Toast.LENGTH_SHORT).show();
		stopSelf();

		return Service.START_NOT_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		String text = "WallpaperService stopped!";
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
	}

	private File pickRandomFileFromDirectory() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String selectedDirectory = prefs.getString("SELECTED_DIRECTORY", "");

		File file = new File(selectedDirectory);
		File[] files = file.listFiles(new FileFilter() {
			private String[] extensions = { "jpg", "jpeg", "gif", "png", "bmp" };

			@Override
			public boolean accept(File pathname) {
				if (pathname.isDirectory()) {
					return false;
				}
				
				String fileName = pathname.getName().toLowerCase();
				for (String extension : extensions) {
					if (fileName.endsWith(extension)) {
						return true;
					}
				}
				
				return false;
			}
		});

		Random rand = new Random();

		return files[rand.nextInt(files.length)];
	}
}

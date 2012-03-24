package com.supervaca.wallpaperChanger;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.util.Log;

public class WallpaperService extends IntentService {
	public static final String REFRESH_WALLPAPER = "com.supervaca.wallpaperChanger.REFRESH_WALLPAPER";
	private static final int IMAGE_MAX_SIZE = 1024;

	public WallpaperService() {
		super("WallpaperService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// Launch background thread
		File imageFile = pickRandomFileFromDirectory();
		Bitmap bitmap = decodeFile(imageFile);

		try {
			getApplicationContext().setWallpaper(bitmap);
		} catch (IOException e) {
			Log.e(WALLPAPER_SERVICE, "error setting the wallpaper", e);
		}
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
	
	/**
	 * Resize an image if necessary before decoding
	 * 
	 * @link http://stackoverflow.com/a/3549021/427232
	 * @param f A file to decode
	 * @return
	 */
	private Bitmap decodeFile(File f){
	    Bitmap b = null;
	    try {
	        //Decode image size
	        BitmapFactory.Options o = new BitmapFactory.Options();
	        o.inJustDecodeBounds = true;

	        FileInputStream fis = new FileInputStream(f);
	        BitmapFactory.decodeStream(fis, null, o);
	        fis.close();

	        int scale = 1;
	        if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
	            scale = (int)Math.pow(2, (int) Math.round(Math.log(IMAGE_MAX_SIZE / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
	        }

	        //Decode with inSampleSize
	        BitmapFactory.Options o2 = new BitmapFactory.Options();
	        o2.inSampleSize = scale;
	        fis = new FileInputStream(f);
	        b = BitmapFactory.decodeStream(fis, null, o2);
	        fis.close();
	    } catch (IOException e) {
	    	Log.e(WALLPAPER_SERVICE, "Unable to decode file", e);
	    }
	    return b;
	}
}

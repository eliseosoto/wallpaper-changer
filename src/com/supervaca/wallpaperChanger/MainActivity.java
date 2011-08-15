package com.supervaca.wallpaperChanger;

import android.app.Activity;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        Drawable wallpaper = wallpaperManager.getDrawable();
        Log.i("wallpaperChanger", wallpaper.toString());

        // Rotate the wallpaper upside down.
        int width = wallpaper.getIntrinsicWidth();
        int height = wallpaper.getIntrinsicHeight();

        // Create matrix for manipulation
        Matrix matrix = new Matrix();
        matrix.postRotate(180);

        Bitmap bitmap = ((BitmapDrawable) wallpaper).getBitmap();
        Bitmap newWallpaper = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);

        try {
            getApplicationContext().setWallpaper(newWallpaper);
        } catch (IOException e) {
            Log.e("wallpaperChanger", "Error while setting the wallpaper", e);
        }
        finish();
    }
}

package com.supervaca.wallpaperChanger;

import android.app.Activity;
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

        BitmapDrawable wallpaper = (BitmapDrawable) getApplicationContext().getWallpaper();
        Log.i("wallpaperChanger", "Setting wallpaper");

        // Rotate the wallpaper upside down.
        int width = wallpaper.getBitmap().getWidth();
        int height = wallpaper.getBitmap().getHeight();

        // Create matrix for manipulation
        Matrix matrix = new Matrix();
        matrix.postRotate(180);

        Bitmap newWallpaper = Bitmap.createBitmap(wallpaper.getBitmap(), 0, 0, width, height, matrix, true);

        try {
            getApplicationContext().setWallpaper(newWallpaper);
        } catch (IOException e) {
            Log.e("wallpaperChanger", "Error while setting the wallpaper", e);
        }
        finish();
    }
}

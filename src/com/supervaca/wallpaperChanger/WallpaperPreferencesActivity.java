package com.supervaca.wallpaperChanger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.util.Log;

public class WallpaperPreferencesActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String SERVICE_ACTIVE = "SERVICE_ACTIVE";
    public static final String UPDATE_INTERVAL = "UPDATE_INTERVAL";

    private ListPreference updateIntervalListPref;
    private CheckBoxPreference serviceActiveCheckBoxPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the XML preferences file
        addPreferencesFromResource(R.xml.preferences);

        // Get a reference to the preferences
        updateIntervalListPref = (ListPreference) getPreferenceScreen().findPreference(UPDATE_INTERVAL);
        serviceActiveCheckBoxPref = (CheckBoxPreference) getPreferenceScreen().findPreference(SERVICE_ACTIVE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();

        // Setup the initial values
        updateIntervalListPref.setSummary(sharedPreferences.getString(UPDATE_INTERVAL, ""));

        // Set up a listener whenever a key changes
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Unregister the listener whenever a key changes
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(UPDATE_INTERVAL)) {
            updateIntervalListPref.setSummary(sharedPreferences.getString(key, ""));
        } else if (key.equals(SERVICE_ACTIVE)) {
            Log.i("wallpaperChanger", serviceActiveCheckBoxPref.isChecked() + "");
            Intent myIntent = new Intent(WallpaperService.REFRESH_WALLPAPER);

            if (serviceActiveCheckBoxPref.isChecked()) {
                // Implicitly start the Service
                startService(myIntent);
            } else {
                // Implicitly stop the Service
                stopService(myIntent);
            }
        }
    }
}

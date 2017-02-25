package com.pierluigipapeschi.popularmovies2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;

/**
 * Created by pier on 12/02/17.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String LOG_TAG = SettingsFragment.class.getSimpleName();

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        addPreferencesFromResource(R.xml.preferences);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        Preference p = preferenceScreen.getPreference(0);

        String value = sharedPreferences.getString(p.getKey(), "-");
        ListPreference listPreference = (ListPreference) p;
        int id = listPreference.findIndexOfValue(value);

        if (id >= 0) p.setSummary(listPreference.getEntries()[id]);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        Preference p = findPreference(key);

        if (p != null) {
            String value = sharedPreferences.getString(key, "-");
            ListPreference listPreference = (ListPreference) p;
            int id = listPreference.findIndexOfValue(value);
            if (id >= 0) p.setSummary(listPreference.getEntries()[id]);
            else p.setSummary(key);
        }
        Log.d(LOG_TAG, "onSharedPreferenceChanged()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop()");
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart()");
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }
}

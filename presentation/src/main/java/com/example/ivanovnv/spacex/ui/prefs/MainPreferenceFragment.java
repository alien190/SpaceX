package com.example.ivanovnv.spacex.ui.prefs;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.example.ivanovnv.spacex.R;
import com.example.ivanovnv.spacex.currentPreferences.ICurrentPreferences;

import javax.inject.Inject;

import toothpick.Scope;
import toothpick.Toothpick;

public class MainPreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Inject
    ICurrentPreferences mCurrentPreferences;

    public static MainPreferenceFragment newInstance() {

        Bundle args = new Bundle();

        MainPreferenceFragment fragment = new MainPreferenceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Scope scope = Toothpick.openScope("Application");
        Toothpick.inject(this, scope);
        initSummary();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        setSummaryFor(findPreference(key));
    }

    private void initSummary() {
        setSummaryFor(findPreference(getString(R.string.unit_key)));
    }

    private void setSummaryFor(Preference preference) {
        String value = "";
        if (preference instanceof ListPreference) {
            value = String.valueOf(((ListPreference) preference).getValue());
            preference.setSummary(((ListPreference) preference).getEntry());
        } else if (preference instanceof EditTextPreference) {
            value = String.valueOf(((EditTextPreference) preference).getText());
            preference.setSummary(((EditTextPreference) preference).getText());
        }
        mCurrentPreferences.setValue(preference.getKey(), value);
    }

//    @Override
//    public void onDisplayPreferenceDialog(Preference preference) {
//        if (preference instanceof TrackDecorationPreference) {
//            DialogFragment dialogFragment =
//                    TrackDecorationPreferencesDialogFragment.newInstance(preference.getKey());
//            dialogFragment.setTargetFragment(this, 0);
//            dialogFragment.show(getFragmentManager(), "TrackDecorationPreferencesDialogFragment");
//        } else {
//            super.onDisplayPreferenceDialog(preference);
//        }
//    }
}

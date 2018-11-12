package com.example.ivanovnv.spacex;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by IvanovNV on 21.02.2018.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_single_fragment);

        openScope();

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, getFragment())
                    .addToBackStack(getFragment().getClass().getSimpleName())
                    .commit();
        }
    }

    protected abstract Fragment getFragment();

    protected abstract void openScope();

    @Override
    protected void onDestroy() {
        closeScope();
        super.onDestroy();
    }

    protected abstract void closeScope();


    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() == 1) {
            finish();
        } else {
            fragmentManager.popBackStack();
        }
    }
}

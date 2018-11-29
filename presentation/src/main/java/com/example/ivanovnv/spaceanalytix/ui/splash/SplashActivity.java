package com.example.ivanovnv.spaceanalytix.ui.splash;

import com.example.ivanovnv.spaceanalytix.customComponents.SingleFragmentActivity;

import androidx.fragment.app.Fragment;

public class SplashActivity extends SingleFragmentActivity {
    @Override
    protected Fragment getFragment() {
        return SplashFragment.newInstance();
    }

    @Override
    protected void openScope() {

    }

    @Override
    protected void closeScope() {

    }
}

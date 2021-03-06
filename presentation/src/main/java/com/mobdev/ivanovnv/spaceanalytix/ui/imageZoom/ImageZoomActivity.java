package com.mobdev.ivanovnv.spaceanalytix.ui.imageZoom;

import android.content.Context;
import android.content.Intent;
import androidx.fragment.app.Fragment;
import android.view.View;

import com.mobdev.ivanovnv.spaceanalytix.customComponents.SingleFragmentActivity;


public class ImageZoomActivity extends SingleFragmentActivity {
    @Override
    protected Fragment getFragment() {
        return ImageZoomFragment.newInstance();
    }

    @Override
    protected void openScope() {

    }

    @Override
    protected void closeScope() {

    }

    public static void start(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, ImageZoomActivity.class);
            context.startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUI();
    }

    @Override
    protected void onPause() {
        showSystemUI();
        super.onPause();
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}

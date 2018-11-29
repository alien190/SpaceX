package com.example.ivanovnv.spaceanalytix.ui.splash;

import android.animation.Animator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.example.ivanovnv.spaceanalytix.ui.main.MainActivity;
import com.example.ivanovnv.spaceanalytix.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SplashFragment extends Fragment implements Animator.AnimatorListener {
    private LottieAnimationView mLottieAnimationView;
    private View mView;

    public static SplashFragment newInstance() {

        Bundle args = new Bundle();

        SplashFragment fragment = new SplashFragment();
        fragment.setArguments(args);
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fr_spalsh, container, false);
            mLottieAnimationView = mView.findViewById(R.id.lottie_animation);
            mLottieAnimationView.addAnimatorListener(this);
        }
        return mView;
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        exit();
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        //exit();
    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    private void exit() {
        MainActivity.start(getActivity());
        getActivity().finish();
    }
}

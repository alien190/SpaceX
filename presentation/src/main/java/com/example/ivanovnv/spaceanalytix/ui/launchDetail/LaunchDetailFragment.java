package com.example.ivanovnv.spaceanalytix.ui.launchDetail;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivanovnv.spaceanalytix.R;
import com.example.ivanovnv.spaceanalytix.databinding.DetailLaunchBinding;
import com.example.ivanovnv.spaceanalytix.di.launchDetail.LaunchDetailFragmentModule;

import javax.inject.Inject;

import toothpick.Scope;
import toothpick.Toothpick;

public class LaunchDetailFragment extends Fragment {
    private static final String FLIGHT_NUMBER_KEY = "com.example.ivanovnv.spacex.LaunchDetailFragment.KEY";

    @Inject
    LaunchDetailViewModel mDetailLaunchViewModel;


    public static LaunchDetailFragment newInstance(int flightNumber) {

        Bundle args = new Bundle();
        args.putInt(FLIGHT_NUMBER_KEY, flightNumber);
        LaunchDetailFragment fragment = new LaunchDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSharedElementEnterTransition(TransitionInflater
                .from(getContext())
                .inflateTransition(R.transition.image_shared_element_transition));
        setEnterTransition(TransitionInflater.from(getContext())
                .inflateTransition(R.transition.detail_fragment_enter_transition));
        postponeEnterTransition();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DetailLaunchBinding binding = DetailLaunchBinding.inflate(inflater);

        Bundle args = getArguments();
        try {
            int flightNumber = args.getInt(FLIGHT_NUMBER_KEY);
            injectToothpick(flightNumber);
            initBinding(binding);
            initObservers();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return binding.getRoot();
    }

    private void initObservers() {
        mDetailLaunchViewModel.getExternalLinkForOpen().observe(this, this::openExternalLink);
    }

    private void openExternalLink(String link) {
        Activity activity = getActivity();
        if (activity != null && link != null && !link.isEmpty()) {
            Uri uri = Uri.parse(link);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (intent.resolveActivity(activity.getPackageManager()) != null)
                startActivity(intent);
        }
    }

    private void initBinding(DetailLaunchBinding binding) {
        binding.setVm(mDetailLaunchViewModel);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();
        mDetailLaunchViewModel.loadLaunch();
        mDetailLaunchViewModel.getIsLoadDone().observe(this, this::startAnimation);
    }

    private void injectToothpick(int flightNumber) {
        Toothpick.closeScope("LaunchDetailFragment");
        Scope scope = Toothpick.openScopes("Application", "LaunchDetailFragment");
        scope.installModules(new LaunchDetailFragmentModule(this, flightNumber));
        Toothpick.inject(this, scope);
    }

    private void startAnimation(Boolean isDone) {
        if (isDone != null && isDone) {
            startPostponedEnterTransition();
        }
    }


    @Override
    public void onDestroy() {
        Toothpick.closeScope("LaunchDetailFragment");
        super.onDestroy();
    }

    @Override
    public void onStop() {
        mDetailLaunchViewModel.clearDisposable();
        super.onStop();
    }
}

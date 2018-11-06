package com.example.ivanovnv.spacex.detailLaunchFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivanovnv.spacex.R;
import com.example.ivanovnv.spacex.databinding.DetailLaunchBinding;
import com.example.ivanovnv.spacex.di.detailLaunchFragment.DetailLaunchFragmentModule;

import javax.inject.Inject;

import toothpick.Scope;
import toothpick.Toothpick;

public class DetailLaunchFragment extends Fragment {
    private static final String FLIGHT_NUMBER_KEY = "com.example.ivanovnv.spacex.DetailLaunchFragment.KEY";

    @Inject
    DetailLaunchViewModel mDetailLaunchViewModel;


    public static DetailLaunchFragment newInstance(int flightNumber) {

        Bundle args = new Bundle();
        args.putInt(FLIGHT_NUMBER_KEY, flightNumber);
        DetailLaunchFragment fragment = new DetailLaunchFragment();
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

            Toothpick.closeScope("DetailLaunchFragment");
            Scope scope = Toothpick.openScopes("Application", "DetailLaunchFragment");
            scope.installModules(new DetailLaunchFragmentModule(this, flightNumber));
            Toothpick.inject(this, scope);
            binding.setVm(mDetailLaunchViewModel);
            binding.setLifecycleOwner(this);
            binding.executePendingBindings();

            mDetailLaunchViewModel.loadLaunch();
            mDetailLaunchViewModel.getIsLoadDone().observe(this, this::startAnimation);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return binding.getRoot();
    }

    private void startAnimation(Boolean isDone) {
        if (isDone != null && isDone) {
            startPostponedEnterTransition();
        }
    }


    @Override
    public void onDestroy() {
        Toothpick.closeScope("DetailLaunchFragment");
        super.onDestroy();
    }

}

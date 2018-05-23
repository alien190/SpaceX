package com.example.ivanovnv.spacex.DetailLaunchFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ivanovnv.spacex.R;

public class DetailLaunchFragment extends Fragment {
    private static final String FLIGHT_NUMBER_KEY = "com.example.ivanovnv.spacex.DetailLaunchFragment.KEY";
    private TextView mTextView;

    public static DetailLaunchFragment newInstance(int flightNumber) {

        Bundle args = new Bundle();
        args.putInt(FLIGHT_NUMBER_KEY, flightNumber);
        DetailLaunchFragment fragment = new DetailLaunchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_detail_launch, container, false);
        mTextView = view.findViewById(R.id.tv_detail_launch);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        mTextView.setText("" + args.getInt(FLIGHT_NUMBER_KEY));
    }
}

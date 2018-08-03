package com.example.ivanovnv.spacex.DetailLaunchFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.data.database.LaunchDao;
import com.example.ivanovnv.spacex.R;
import com.squareup.picasso.Picasso;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DetailLaunchFragment extends Fragment {
    private static final String FLIGHT_NUMBER_KEY = "com.example.ivanovnv.spacex.DetailLaunchFragment.KEY";
    private TextView mFlightNumber;
    private ImageView mImageView;
    private TextView mMissionName;
    private TextView mRocketName;
    private TextView mLaunchDate;
    private TextView mDetails;
    private int mFlightNumberInt;


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
        mFlightNumber = view.findViewById(R.id.tv_flight_number);
        mMissionName = view.findViewById(R.id.tv_mission_name);
        mImageView = view.findViewById(R.id.iv_item);
        mRocketName = view.findViewById(R.id.tv_rocket_name);
        mLaunchDate = view.findViewById(R.id.tv_launch_date);
        mDetails = view.findViewById(R.id.tv_details);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        try {
//            mFlightNumberInt = args.getInt(FLIGHT_NUMBER_KEY);
//            loadLaunchFromDataBase(mFlightNumberInt);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private LaunchDao getLaunchDao() {
        return null;
        // ((App) getActivity().getApplication()).getLaunchDataBase().getLaunchDao();
    }

//    private void loadLaunchFromDataBase(int flightNumber) {
//        Single.create((SingleOnSubscribe<Launch>) emitter -> emitter.onSuccess(getLaunchDao().getLaunchByFlightNumber(flightNumber)))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SingleObserver<Launch>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onSuccess(Launch launch) {
//                        setLaunchValues(launch);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                    }
//                });
//    }
//
//    private void setLaunchValues(Launch launch) {
//        mMissionName.setText(launch.getMission_name());
//        mRocketName.setText(launch.getRocket_name());
//        mLaunchDate.setText(launch.getLaunch_date_utc());
//        mDetails.setText(launch.getDetails());
//        mFlightNumber.setText("" + launch.getFlight_number());
//
//        Picasso.get()
//                .load(launch.getMission_patch_small())
//                .into(mImageView);
//    }
}

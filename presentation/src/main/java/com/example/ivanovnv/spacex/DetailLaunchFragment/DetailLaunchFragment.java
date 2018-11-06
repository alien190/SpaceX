package com.example.ivanovnv.spacex.DetailLaunchFragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.data.database.LaunchDao;
import com.example.data.utils.DbBitmapUtility;
import com.example.domain.model.launch.DomainLaunch;
import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;
import toothpick.Scope;
import toothpick.Toothpick;

public class DetailLaunchFragment extends Fragment {
    private static final String FLIGHT_NUMBER_KEY = "com.example.ivanovnv.spacex.DetailLaunchFragment.KEY";
    //private TextView mFlightNumber;
    @BindView(R.id.iv_mission_icon)
    public ImageView mImageView;
    @BindView(R.id.tv_mission_name)
    public TextView mMissionName;
    //    private TextView mRocketName;
    @BindView(R.id.tv_launch_date)
    public TextView mLaunchDate;
    @BindView(R.id.tv_details)
    public TextView mDetails;
    @BindView(R.id.collapsing_toolbar)
    public CollapsingToolbarLayout mCollapsingToolbarLayout;
    private int mFlightNumberInt;

    @BindView(R.id.tv_details2)
    public TextView mDetails2;

    @BindView(R.id.tv_details3)
    public TextView mDetails3;

    @BindView(R.id.tv_details4)
    public TextView mDetails4;

    @BindView(R.id.tv_details5)
    public TextView mDetails5;

    @Inject
    ILaunchService mLaunchService;

    private Disposable mDisposable;

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

        //.inflateTransition(android.R.transition.move));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_detail_launch, container, false);

        ButterKnife.bind(this, view);

        Scope scope = Toothpick.openScope("Application");
        Toothpick.inject(this, scope);

        Bundle args = getArguments();
        try {
            mFlightNumberInt = args.getInt(FLIGHT_NUMBER_KEY);
            mImageView.setTransitionName("TransitionName" + String.valueOf(mFlightNumberInt));
            mDisposable = mLaunchService.getLaunchByFlightNumber(mFlightNumberInt)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::showLaunch, Timber::d);
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return view;
    }


    private void showLaunch(DomainLaunch domainLaunch) {
        if (domainLaunch != null) {
            mImageView.setImageBitmap(DbBitmapUtility.getImage(domainLaunch.getImage()));
            mMissionName.setText(domainLaunch.getMission_name());
            mCollapsingToolbarLayout.setTitle(domainLaunch.getMission_name());
            mDetails.setText(domainLaunch.getDetails());
            mDetails2.setText(domainLaunch.getDetails());
            mDetails3.setText(domainLaunch.getDetails());
            mDetails4.setText(domainLaunch.getDetails());
            mDetails5.setText(domainLaunch.getDetails());
            mLaunchDate.setText(domainLaunch.getLaunch_date_utc());
        }
    }

    @Override
    public void onDestroy() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        super.onDestroy();
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

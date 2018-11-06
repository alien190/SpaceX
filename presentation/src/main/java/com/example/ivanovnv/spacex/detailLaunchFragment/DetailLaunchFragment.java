package com.example.ivanovnv.spacex.detailLaunchFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.data.utils.DbBitmapUtility;
import com.example.domain.model.launch.DomainLaunch;
import com.example.ivanovnv.spacex.R;
import com.example.ivanovnv.spacex.databinding.DetailLaunchBinding;
import com.example.ivanovnv.spacex.di.detailLaunchFragment.DetailLaunchFragmentModule;

import javax.inject.Inject;

import toothpick.Scope;
import toothpick.Toothpick;

public class DetailLaunchFragment extends Fragment {
    private static final String FLIGHT_NUMBER_KEY = "com.example.ivanovnv.spacex.DetailLaunchFragment.KEY";
    //private TextView mFlightNumber;
//    @BindView(R.id.iv_mission_icon)
//    public ImageView mImageView;
//    @BindView(R.id.tv_mission_name)
//    public TextView mMissionName;
//    //    private TextView mRocketName;
//    @BindView(R.id.tv_launch_date)
//    public TextView mLaunchDate;
//    @BindView(R.id.tv_details)
//    public TextView mDetails;
//    @BindView(R.id.collapsing_toolbar)
//    public CollapsingToolbarLayout mCollapsingToolbarLayout;
//    @BindView(R.id.card_links)
//    public CardView mCardLinks;
//    @BindView(R.id.btn_youtube)
//    public MaterialButton mBtnYoutube;
//    @BindView(R.id.btn_article)
//    public MaterialButton mBtnArticle;
//    @BindView(R.id.btn_press_release)
//    public MaterialButton mBtnPressRelease;
//    @BindView(R.id.btn_wikipedia)
//    public MaterialButton mBtnWikipedia;

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

        //.inflateTransition(android.R.transition.move));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fr_detail_launch, container, false);
        //ButterKnife.bind(this, view);
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
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return binding.getRoot();
    }


//    private void showLaunch(DomainLaunch domainLaunch) {
//        if (domainLaunch != null) {
//            mImageView.setImageBitmap(DbBitmapUtility.getImage(domainLaunch.getImage()));
//            mMissionName.setText(domainLaunch.getMission_name());
//            mCollapsingToolbarLayout.setTitle(domainLaunch.getMission_name());
//            mDetails.setText(domainLaunch.getDetails());
//            mLaunchDate.setText(domainLaunch.getLaunch_date_utc());
//        }
//    }

    @Override
    public void onDestroy() {
        Toothpick.closeScope("DetailLaunchFragment");
        super.onDestroy();
    }
}

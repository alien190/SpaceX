package com.mobdev.ivanovnv.spaceanalytix.ui.launch;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.mobdev.data.utils.DbBitmapUtility;
import com.mobdev.domain.model.launch.DomainLaunch;
import com.mobdev.ivanovnv.spaceanalytix.currentPreferences.IConverter;
import com.mobdev.ivanovnv.spaceanalytix.customComponents.LaunchItemView;


public class LaunchViewHolder extends RecyclerView.ViewHolder {
    private View mView;
    private int mFlightNumber;
    private LaunchAdapter.OnItemClickListener mClickCallback = null;
    private IConverter mConverter;


    public LaunchViewHolder(View view, IConverter converter) {
        super(view);
        mView = view;
        if (converter != null) {
            mConverter = converter;
        } else {
            throw new IllegalArgumentException("converter can't be null");
        }
    }

    public void bind(DomainLaunch launch, LaunchAdapter.OnItemClickListener clickListener) {
        if (mView instanceof LaunchItemView) {
            LaunchItemView launchItemView = (LaunchItemView) mView;
            mFlightNumber = launch.getFlight_number();
            launchItemView.getAnimationHelper().setTransitionName("TransitionName" + String.valueOf(mFlightNumber));
            launchItemView.setMissionIconBitmap(DbBitmapUtility.getImage(launch.getImage()));
            launchItemView.setMissionName(launch.getMission_name());
            launchItemView.setDetails(launch.getDetails());
            launchItemView.setLaunchDate(mConverter.getTimeText(launch.getLaunch_date_utc()));
            launchItemView.setTransitionName(String.valueOf(launch.getFlight_number()));
            mClickCallback = clickListener;
            launchItemView.setOnClickListener(mOnClickListener);
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mClickCallback != null) {
                View sharedView = null;
                if (mView instanceof LaunchItemView) {
                    sharedView = ((LaunchItemView) mView).getAnimationHelper();
                }
                mClickCallback.onItemClick(mFlightNumber, sharedView);
            }
        }
    };
}

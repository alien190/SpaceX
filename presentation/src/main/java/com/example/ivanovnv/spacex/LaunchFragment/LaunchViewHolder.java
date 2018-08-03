package com.example.ivanovnv.spacex.LaunchFragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.domain.model.launch.DomainLaunch;
import com.example.ivanovnv.spacex.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LaunchViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_flight_number)
    public TextView mFlightNumber;
    @BindView(R.id.iv_item)
    public ImageView mImageView;
    @BindView(R.id.tv_mission_name)
    public TextView mMissionName;
    @BindView(R.id.tv_rocket_name)
    public TextView mRocketName;
    @BindView(R.id.tv_launch_date)
    public TextView mLaunchDate;

    private int mFlightNumberInt;

    public LaunchViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(DomainLaunch launch) {

        mFlightNumberInt = launch.getFlight_number();
        mFlightNumber.setText(String.valueOf(launch.getFlight_number()));
        mMissionName.setText(launch.getMission_name());
        mRocketName.setText(launch.getRocket_name());
        mLaunchDate.setText(launch.getLaunch_date_utc());

        Picasso.get()
                .load(launch.getMission_patch_small())
                .into(mImageView);
    }

    public void setItemClickListener(final LaunchAdapter.OnItemClickListener listener) {
        if (listener != null) {
            itemView.setOnClickListener(view -> listener.onItemClick(mFlightNumberInt));
        }
    }

}

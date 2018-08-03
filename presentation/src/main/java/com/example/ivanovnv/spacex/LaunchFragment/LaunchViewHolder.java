package com.example.ivanovnv.spacex.LaunchFragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.domain.model.launch.DomainLaunch;
import com.example.ivanovnv.spacex.R;
import com.squareup.picasso.Picasso;

public class LaunchViewHolder extends RecyclerView.ViewHolder {

    private TextView mFlightNumber;
    private ImageView mImageView;
    private TextView mMissionName;
    private TextView mRocketName;
    private TextView mLaunchDate;
    private int mFlightNumberInt;

    public LaunchViewHolder(View itemView) {
        super(itemView);
        mFlightNumber = itemView.findViewById(R.id.tv_flight_number);
        mMissionName = itemView.findViewById(R.id.tv_mission_name);
        mImageView = itemView.findViewById(R.id.iv_item);
        mRocketName = itemView.findViewById(R.id.tv_rocket_name);
        mLaunchDate = itemView.findViewById(R.id.tv_launch_date);

    }

    public void bind(DomainLaunch launch) {

        mFlightNumberInt = launch.getFlight_number();
        mFlightNumber.setText("" + launch.getFlight_number());
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

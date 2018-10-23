package com.example.ivanovnv.spacex.Launch;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.domain.model.launch.DomainLaunch;
import com.example.ivanovnv.spacex.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LaunchViewHolder extends RecyclerView.ViewHolder {
    View mView;

    @BindView(R.id.tv_flight_number)
    TextView mTvFlightNumber;


    public LaunchViewHolder(View view) {
        super(view);
        mView = view;
        ButterKnife.bind(this, mView);
    }

    public void bind(DomainLaunch launch, LaunchAdapter.OnItemClickListener clickListener) {
        mTvFlightNumber.setText(String.valueOf(launch.getFlight_number()));
    }

}

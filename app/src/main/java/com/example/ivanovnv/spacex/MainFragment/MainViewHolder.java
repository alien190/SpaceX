package com.example.ivanovnv.spacex.MainFragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ivanovnv.spacex.R;
import com.example.ivanovnv.spacex.SpaceXAPI.Launch;

public class MainViewHolder extends RecyclerView.ViewHolder {

    TextView mFlightNumber;

    public MainViewHolder(View itemView) {
        super(itemView);
        mFlightNumber = itemView.findViewById(R.id.tv_flight_number);
    }

    public void bind(Launch launch) {
        mFlightNumber.setText("" + launch.getFlight_number());
    }
}

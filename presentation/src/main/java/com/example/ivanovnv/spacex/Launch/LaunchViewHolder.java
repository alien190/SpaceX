package com.example.ivanovnv.spacex.Launch;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.domain.model.launch.DomainLaunch;


public class LaunchViewHolder extends RecyclerView.ViewHolder {
    View mView;

    public LaunchViewHolder(View view) {
        super(view);
        mView = view;
    }

    public void bind(DomainLaunch launch, LaunchAdapter.OnItemClickListener clickListener) {

    }

}

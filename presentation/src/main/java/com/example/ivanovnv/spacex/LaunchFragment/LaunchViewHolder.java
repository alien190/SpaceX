package com.example.ivanovnv.spacex.LaunchFragment;

import android.support.v7.widget.RecyclerView;
import com.example.domain.model.launch.DomainLaunch;
import com.example.ivanovnv.spacex.databinding.LaunchListItemBinding;


public class LaunchViewHolder extends RecyclerView.ViewHolder {
    LaunchListItemBinding mLaunchListItemBinding;

    public LaunchViewHolder(LaunchListItemBinding launchListItemBinding) {
        super(launchListItemBinding.getRoot());
        mLaunchListItemBinding = launchListItemBinding;
    }

    public void bind(DomainLaunch launch, LaunchAdapter.OnItemClickListener clickListener) {
        mLaunchListItemBinding.setVm(new LaunchItemViewModel(launch, clickListener));
        mLaunchListItemBinding.executePendingBindings();
    }

}

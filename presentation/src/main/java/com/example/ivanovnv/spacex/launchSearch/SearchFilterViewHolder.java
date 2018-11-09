package com.example.ivanovnv.spacex.launchSearch;

import android.support.annotation.NonNull;
import android.support.design.chip.Chip;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.domain.model.searchFilter.LaunchSearchFilter;
import com.example.ivanovnv.spacex.R;


public class SearchFilterViewHolder extends RecyclerView.ViewHolder {
    private Chip mChip;
    private LaunchSearchFilter mLaunchSearchFilter;
    private SearchFilterAdapter.IOnFilterItemClickListener mItemClickListener;

    public SearchFilterViewHolder(@NonNull View itemView) {
        super(itemView);
        mChip = itemView.findViewById(R.id.chip);
    }

    public void bind(LaunchSearchFilter item, SearchFilterAdapter.IOnFilterItemClickListener onItemClickListener) {
        mItemClickListener = onItemClickListener;
        mLaunchSearchFilter = item;
        mChip.setText(item.getValue());
        mChip.setOnClickListener(this::onClick);
        mChip.setSelected(item.isSelected());
    }

    private void onClick(View view) {
        if (mItemClickListener != null) {
            mItemClickListener.onFilterItemClick(mLaunchSearchFilter);
        }
    }

}

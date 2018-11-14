package com.example.ivanovnv.spacex.launchSearch;

import android.support.annotation.NonNull;
import android.support.design.chip.Chip;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.domain.model.searchFilter.ISearchFilterItem;
import com.example.ivanovnv.spacex.R;
import com.example.ivanovnv.spacex.launchSearch.adapter.BaseSearchFilterAdapter;


public class SearchFilterViewHolder extends RecyclerView.ViewHolder {
    private Chip mChip;
    private ISearchFilterItem mItem;
    private BaseSearchFilterAdapter.IOnFilterItemClickListener mItemClickListener;

    public SearchFilterViewHolder(@NonNull View itemView) {
        super(itemView);
        mChip = itemView.findViewById(R.id.chip);
    }

    public void bind(ISearchFilterItem item, BaseSearchFilterAdapter.IOnFilterItemClickListener onItemClickListener) {
        if (item != null) {
            mItem = item;
            mItemClickListener = onItemClickListener;
            mChip.setText(mItem.getValue());
            mChip.setOnClickListener(this::onClick);
            mChip.setOnCloseIconClickListener(this::onCloseIconClick);
            mChip.setSelected(mItem.isSelected());
        }
    }

    private void onClick(View view) {
        if (mItemClickListener != null) {
            mItemClickListener.onFilterItemClick(mItem);
        }
    }

    private void onCloseIconClick(View view) {
        if (mItem != null) {
            mItem.setSelected(false);
        }
    }
}

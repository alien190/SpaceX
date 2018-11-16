package com.example.ivanovnv.spacex.launchSearch;

import android.support.annotation.NonNull;
import android.support.design.chip.Chip;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.domain.model.filter.IBaseFilterItem;
import com.example.ivanovnv.spacex.R;
import com.example.ivanovnv.spacex.launchSearch.adapter.BaseFilterAdapter;


public class FilterViewHolder extends RecyclerView.ViewHolder {
    private Chip mChip;
    private IBaseFilterItem mItem;
    private BaseFilterAdapter.IOnFilterItemClickListener mItemClickListener;

    public FilterViewHolder(@NonNull View itemView) {
        super(itemView);
        mChip = itemView.findViewById(R.id.chip);
    }

    public void bind(IBaseFilterItem item, BaseFilterAdapter.IOnFilterItemClickListener onItemClickListener) {
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

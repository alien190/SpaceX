package com.example.ivanovnv.spacex.filterAdapter;

import android.support.annotation.NonNull;
import android.support.design.chip.Chip;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.domain.model.filter.IBaseFilterItem;
import com.example.ivanovnv.spacex.R;
import com.example.ivanovnv.spacex.filterAdapter.BaseFilterAdapter;


public class BaseFilterViewHolder extends RecyclerView.ViewHolder {
    protected Chip mChip;
    protected IBaseFilterItem mItem;
    protected BaseFilterAdapter.IOnFilterItemClickListener mItemClickListener;

    public BaseFilterViewHolder(@NonNull View itemView) {
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

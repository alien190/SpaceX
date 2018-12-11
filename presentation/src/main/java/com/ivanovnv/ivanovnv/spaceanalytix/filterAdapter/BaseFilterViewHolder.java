package com.ivanovnv.ivanovnv.spaceanalytix.filterAdapter;

import androidx.annotation.NonNull;
import com.google.android.material.chip.Chip;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.ivanovnv.domain.model.filter.IBaseFilterItem;
import com.ivanovnv.ivanovnv.spaceanalytix.R;


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

package com.example.ivanovnv.spacex.launchSearch;

import android.support.annotation.NonNull;
import android.support.design.chip.Chip;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.domain.model.searchFilter.ISearchFilter;
import com.example.ivanovnv.spacex.R;


public class SearchFilterViewHolder extends RecyclerView.ViewHolder {
    private Chip mChip;
    private int mIndex;
    private SearchFilterAdapterBase.IOnFilterItemClickListener mItemClickListener;
    private ISearchFilter mSearchFilter;

    public SearchFilterViewHolder(@NonNull View itemView) {
        super(itemView);
        mChip = itemView.findViewById(R.id.chip);
    }

    public void bind(int index, ISearchFilter searchFilter, SearchFilterAdapterBase.IOnFilterItemClickListener onItemClickListener) {
        mIndex = index;
        mItemClickListener = onItemClickListener;
        mSearchFilter = searchFilter;
        mChip.setText(mSearchFilter.getItemValue(index));
        mChip.setOnClickListener(this::onClick);
        mChip.setOnCloseIconClickListener(this::onCloseIconClick);
        mChip.setSelected(mSearchFilter.getIsItemSelected(index));
    }

    private void onClick(View view) {
        if (mItemClickListener != null) {
            mItemClickListener.onFilterItemClick(mIndex);
        }
    }

    private void onCloseIconClick(View view) {
        mSearchFilter.setIsItemUnselected(mIndex);
    }
}

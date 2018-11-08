package com.example.ivanovnv.spacex.launchSearch;

import android.support.annotation.NonNull;
import android.support.design.chip.Chip;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.domain.model.searchFilter.LaunchSearchFilter;
import com.example.ivanovnv.spacex.R;


public class SearchFilterViewHolder extends RecyclerView.ViewHolder {
    private Chip mChip;

    public SearchFilterViewHolder(@NonNull View itemView) {
        super(itemView);
        mChip = itemView.findViewById(R.id.chip);
    }

    public void bind(LaunchSearchFilter item) {
        mChip.setText(item.getValue());
    }
}

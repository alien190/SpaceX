package com.ivanovnv.ivanovnv.spaceanalytix.filterAdapter;

import androidx.annotation.NonNull;
import android.view.View;

import com.ivanovnv.domain.model.filter.IAnalyticsFilterItem;
import com.ivanovnv.domain.model.filter.IBaseFilterItem;
import com.ivanovnv.ivanovnv.spaceanalytix.R;

public class AnalyticsFilterViewHolderBySelected extends BaseFilterViewHolder {

    public AnalyticsFilterViewHolderBySelected(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void bind(IBaseFilterItem item, BaseFilterAdapter.IOnFilterItemClickListener onItemClickListener) {
        if (item instanceof IAnalyticsFilterItem) {
            mItem = item;
            mItemClickListener = onItemClickListener;
            mChip.setSelected(mItem.isSelected());
            setText((IAnalyticsFilterItem) mItem);
        } else {
            super.bind(item, onItemClickListener);
        }
    }

    private void setText(IAnalyticsFilterItem item) {
        StringBuilder typeStringBuilder = new StringBuilder();
        switch (item.getType()) {
            case PAYLOAD_WEIGHT: {
                typeStringBuilder.append(itemView.getContext().getString(R.string.payload_weight_analytics));
                break;
            }
            case LAUNCH_COUNT: {
                typeStringBuilder.append(itemView.getContext().getString(R.string.launch_count_analytics));
                break;
            }
        }
        typeStringBuilder.append(" ");
        typeStringBuilder.append(item.getValue());
        mChip.setText(typeStringBuilder.toString());
    }
}

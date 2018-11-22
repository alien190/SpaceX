package com.example.ivanovnv.spaceanalytix.customComponents;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import javax.inject.Inject;

public class FilterLayoutManager extends RecyclerView.LayoutManager {

    private int mPrevBottom;
    private int mWidthSpecSize;


    @Inject
    public FilterLayoutManager() {
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onMeasure(@NonNull RecyclerView.Recycler recycler, @NonNull RecyclerView.State state, int widthSpec, int heightSpec) {
        mWidthSpecSize = View.MeasureSpec.getSize(widthSpec);
        layoutChildren(recycler, true);
        setMeasuredDimension(mWidthSpecSize, mPrevBottom != 0 ? mPrevBottom + getPaddingBottom() : 0);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        layoutChildren(recycler, false);
    }

    private void layoutChildren(RecyclerView.Recycler recycler, boolean isMeasuring) {
        if (isMeasuring) {
            detachAndScrapAttachedViews(recycler);
        }
        int pos = 0;
        int itemCount = getItemCount();
        int viewHeight;
        int viewWidth;

        int left;
        int top;
        int right;
        int bottom;

        int prevTop = 0;
        int prevRight = 0;
        mPrevBottom = 0;

        while (pos < itemCount) {
            View view = recycler.getViewForPosition(pos);
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) view.getLayoutParams();
            if (isMeasuring) {
                addView(view);
            }
            view.measure(0, 0);

            if (prevTop == 0) {
                prevTop = lp.topMargin;
            }
            viewWidth = view.getMeasuredWidth();
            viewHeight = view.getMeasuredHeight();

            if (prevRight + viewWidth + lp.leftMargin < mWidthSpecSize) {
                left = prevRight + lp.leftMargin;
                top = prevTop;
                right = left + viewWidth;
                bottom = top + viewHeight;
            } else {
                left = lp.leftMargin;
                top = mPrevBottom + lp.topMargin;
                right = left + viewWidth;
                bottom = top + viewHeight;
            }
            if (isMeasuring) {
                layoutDecorated(view, left, top, right, bottom);
            }
            prevTop = top;
            prevRight = right;
            mPrevBottom = bottom;
            pos++;
        }

    }
}

package com.example.ivanovnv.spacex.common;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

public class LaunchLayoutManager extends RecyclerView.LayoutManager {

    private SparseArray<View> viewCache = new SparseArray<>();

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        fillDown(recycler);
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    private void fillDown(RecyclerView.Recycler recycler) {
        int pos;
        boolean fillDown = true;
        int height = getHeight();
        int viewTop = 0;
        int itemCount = getItemCount();
        //int viewHeight = (int) (getHeight() * VIEW_HEIGHT_PERCENT);
        //final int widthSpec = View.MeasureSpec.makeMeasureSpec(getWidth(), View.MeasureSpec.EXACTLY);
        //final int heightSpec = View.MeasureSpec.makeMeasureSpec(getHeight(), View.MeasureSpec.EXACTLY);
        //Rect viewBounds = new Rect();


        viewCache.clear();
        for (int i = 0, cnt = getChildCount(); i < cnt; i++) {
            View view = getChildAt(i);
            pos = getPosition(view);
            viewCache.put(pos, view);
        }

        for (int i = 0; i < viewCache.size(); i++) {
            detachView(viewCache.valueAt(i));
        }

        View topView = getChildAt(0);
        if (topView != null) {
            pos = getPosition(topView);
        } else {
            pos = 0;
        }

        while (fillDown && pos < itemCount) {
            View view = viewCache.get(pos);
            if (view == null) {
                view = recycler.getViewForPosition(pos);
                addView(view);
                measureChildWithMargins(view, 0, 0);
                int decoratedMeasuredWidth = getDecoratedMeasuredWidth(view);
                int decoratedMeasuredHeight = getDecoratedMeasuredHeight(view);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                layoutDecorated(view,
                        layoutParams.leftMargin,
                        viewTop + layoutParams.topMargin,
                        decoratedMeasuredWidth + layoutParams.rightMargin,
                        viewTop + decoratedMeasuredHeight + layoutParams.bottomMargin + layoutParams.topMargin);
                viewTop += decoratedMeasuredHeight + layoutParams.bottomMargin + layoutParams.topMargin;

            } else {
                attachView(view);
                viewCache.remove(pos);
                viewTop = getDecoratedBottom(view);
            }

            fillDown = viewTop <= height;
            pos++;
        }

        for (int i = 0; i < viewCache.size(); i++) {
            recycler.recycleView(viewCache.valueAt(i));
        }
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int delta = getScrollDelta(dy);
        //if (dy > 0) {
        offsetChildrenVertical(-delta);
        fillDown(recycler);
        return delta;
    }

    private int getScrollDelta(int dy) {
        if (dy < 0) {
            View topView = getChildAt(0);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) topView.getLayoutParams();
            int topPosition = getPosition(topView);
            if (topPosition > 0) {
                return dy;
            } else {
                return Math.max(getDecoratedTop(topView) - layoutParams.topMargin, dy);
            }
        } else {
            View bottomView = getChildAt(getChildCount() - 1);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) bottomView.getLayoutParams();
            int bottomPosition = getPosition(bottomView);
            if (bottomPosition < getItemCount() - 1) {
                return dy;
            } else {
                int bottom = getDecoratedBottom(bottomView) + layoutParams.bottomMargin - getHeight();

                return Math.min(bottom > 0 ? bottom : 0, dy);
            }
        }
    }
}

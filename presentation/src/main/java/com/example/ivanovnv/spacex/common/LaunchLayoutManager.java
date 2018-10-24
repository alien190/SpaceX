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
        doLayoutChildren(recycler);
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    private void doLayoutChildren(RecyclerView.Recycler recycler) {
        int top;
        View anchorView = getAnchorView();
        initializeCache();
        top = drawAnchorView(anchorView, recycler);
        fillDown(recycler, top, anchorView);

        for (int i = 0; i < viewCache.size(); i++) {
            recycler.recycleView(viewCache.valueAt(i));
        }
    }

    private void fillDown(RecyclerView.Recycler recycler, int viewTop, View anchorView) {
        int pos = 0;
        int height = getHeight();
        int itemCount = getItemCount();
        boolean fillDown = true;

        if (anchorView != null) {
            int anchorViewPosition = getPosition(anchorView);
            viewCache.remove(anchorViewPosition);
            pos = getPosition(anchorView) + 1;
        }

        while (fillDown && pos < itemCount) {
            View view = viewCache.get(pos);
            if (view == null) {
                view = recycler.getViewForPosition(pos);
                if (view instanceof LaunchItemView) {
                    if (anchorView == null && pos == 0) {
                        ((LaunchItemView) view).setScale(100);
                    } else {
                        ((LaunchItemView) view).setScale(0);
                    }
                }
                viewTop = drawView(view, viewTop);
            } else {
                attachView(view);
                viewCache.remove(pos);
                viewTop = getDecoratedBottom(view);
            }

            fillDown = viewTop <= height;
            pos++;
        }
    }

    private void initializeCache() {
        int pos;
        viewCache.clear();
        for (int i = 0, cnt = getChildCount(); i < cnt; i++) {
            View view = getChildAt(i);
            pos = getPosition(view);
            viewCache.put(pos, view);
        }

        for (int i = 0; i < viewCache.size(); i++) {
            detachView(viewCache.valueAt(i));
        }
    }

    private int drawAnchorView(View anchorView, RecyclerView.Recycler recycler) {
        if (anchorView != null) {
            int anchorPos = getPosition(anchorView);

            int newTop;

            View view = recycler.getViewForPosition(anchorPos);
            if (view instanceof LaunchItemView && anchorView instanceof LaunchItemView) {
                LaunchItemView launchItemView = (LaunchItemView) view;
                LaunchItemView launchItemAnchorView = (LaunchItemView) anchorView;

                int anchorTop = anchorView.getTop();
                float scale = ((float) launchItemView.getRootHeightWithMargins() +
                        getTopAndBottomMargins(view) - anchorTop) /
                        launchItemView.getRootHeightWithMargins() * 100;
                // Log.d("TAG", "drawAnchorView: scale: " + scale);
                launchItemView.setScale(scale);

                measureChildWithMargins(launchItemView, 0, 0);
                measureChildWithMargins(launchItemAnchorView, 0, 0);

                int itemHeight = getDecoratedMeasuredHeight(launchItemView);
                int anchorHeight = getDecoratedMeasuredHeight(launchItemAnchorView);

                newTop = anchorTop - getTopAndBottomMargins(launchItemView) -
                        (itemHeight - anchorHeight);
            } else {
                newTop = view.getTop();
            }
            return drawView(view, newTop);
        }
        return 0;
    }

    private int drawView(View view, int top) {
        addView(view);
        measureChildWithMargins(view, 0, 0);
        int decoratedMeasuredWidth = getDecoratedMeasuredWidth(view);
        int decoratedMeasuredHeight = getDecoratedMeasuredHeight(view);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();

        layoutDecorated(view,
                layoutParams.leftMargin,
                top + layoutParams.topMargin,
                decoratedMeasuredWidth + layoutParams.rightMargin,
                top + decoratedMeasuredHeight + layoutParams.bottomMargin + layoutParams.topMargin);
        return top + decoratedMeasuredHeight + layoutParams.bottomMargin + layoutParams.topMargin;
    }

    int getTopAndBottomMargins(View view) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        return layoutParams.bottomMargin + layoutParams.topMargin;
    }

    private View getAnchorView() {
        int childCount = getChildCount();
        //View anchorView = null;
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
            if (getDecoratedTop(view) - layoutParams.topMargin > 0) {
                return view;
            }
        }
        return null;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int delta = getScrollDelta(dy);
        offsetChildrenVertical(-delta);
        doLayoutChildren(recycler);
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

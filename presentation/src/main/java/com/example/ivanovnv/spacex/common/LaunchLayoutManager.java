package com.example.ivanovnv.spacex.common;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewParent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LaunchLayoutManager extends RecyclerView.LayoutManager {

    private SparseArray<View> mViewCache = new SparseArray<>();
    private Lock mLock = new ReentrantLock();

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
        View anchorView = getAnchorView();
        initializeCache();
        //drawAnchorView(anchorView, recycler);
        fillDown(recycler, anchorView);
        //fillUp(recycler, anchorView);
        recyclerCache(recycler);

    }

    private void recyclerCache(RecyclerView.Recycler recycler) {
        for (int i = 0; i < mViewCache.size(); i++) {
            recycler.recycleView(mViewCache.valueAt(i));
        }
    }

    private void fillDown(RecyclerView.Recycler recycler, View anchorView) {
        int pos = 0;
        int height = getHeight();
        int itemCount = getItemCount();
        boolean fillDown = true;
        int top = 0;
        int anchorViewPosition = 0;

        if (anchorView != null) {
            anchorViewPosition = getPosition(anchorView);
        }
//            View view = mViewCache.get(anchorViewPosition);
//            if (view != null) {
//                recycler.recycleView(view);
//                mViewCache.remove(anchorViewPosition);
//            }
//            pos = getPosition(anchorView) + 1;
//            top = getDecoratedBottom(anchorView);
//        }

        View firstView = mViewCache.get(0);
        if (firstView != null) {
            top = firstView.getTop() - getTopAndBottomMargins(firstView);
        } else {
            top = 0;
        }
        while (fillDown && pos < itemCount) {
            View view = mViewCache.get(pos);
            if (pos <= anchorViewPosition || view == null) {
                if (view != null) {
                    recycler.recycleView(view);
                    mViewCache.remove(pos);
                }
                view = recycler.getViewForPosition(pos);
                if (view instanceof LaunchItemView) {
                    if (pos <= anchorViewPosition) {
                        ((LaunchItemView) view).setScale(LaunchItemView.MAX_SCALE);
                    } else {
                        ((LaunchItemView) view).setScale(LaunchItemView.MIN_SCALE);
                    }
                }
                top = drawView(view, top, -1);
            } else {

                attachView(view);
                mViewCache.remove(pos);
                if (view instanceof LaunchItemView) {
                    if (pos <= anchorViewPosition) {
                        ((LaunchItemView) view).setScale(LaunchItemView.MAX_SCALE);
                    } else {
                        int delta = top - view.getTop() + getTopAndBottomMargins(view);
                         view.offsetTopAndBottom(delta);
                        ((LaunchItemView) view).setScale(80);

                        view.setBottom(view.getBottom() + 50);
                        view.requestLayout();
                        view.invalidate();

//                        ViewParent viewParent= view.getParent();
//                        Rect rect = new Rect();
//                        rect.top = view.getTop();
//                        rect.left = view.getLeft();
//                        rect.right = view.getRight();
//                        rect.bottom = view.getBottom();
//                        viewParent.invalidateChild(view, rect);
                    }
                }
                top = getDecoratedBottom(view);
            }


            fillDown = top <= height;
            pos++;
        }
        //updateViewScale();
    }

    private void updateViewScale() {
        View view = getChildAt(0);
        if (view instanceof LaunchItemView) {
            ((LaunchItemView) view).setScale(LaunchItemView.MAX_SCALE);
        }
//        int childCount = getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View view = getChildAt(i);
//        }

    }

    private void fillUp(RecyclerView.Recycler recycler, View anchorView) {
        if (anchorView != null) {
            int pos = getPosition(anchorView) - 1;
            if (pos >= 0) {
                View view = mViewCache.get(pos);
                if (view != null) {
                    recycler.recycleView(view);
                    mViewCache.remove(pos);
                }

                view = recycler.getViewForPosition(pos);
                if (view instanceof LaunchItemView) {
                    ((LaunchItemView) view).setScale(LaunchItemView.MAX_SCALE);
                }
                int anchorTop = anchorView.getTop();
                measureChildWithMargins(view, 0, 0);
                int top = anchorTop - getDecoratedMeasuredHeight(view)
                        - getTopAndBottomMargins(view) - getTopAndBottomMargins(anchorView);
                drawView(view, top, 0);
            }
        }
    }

    private void initializeCache() {
        int pos;
        mViewCache.clear();
        for (int i = 0, cnt = getChildCount(); i < cnt; i++) {
            View view = getChildAt(i);
            pos = getPosition(view);
            mViewCache.put(pos, view);
        }

        for (int i = 0; i < mViewCache.size(); i++) {
            detachView(mViewCache.valueAt(i));
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
            return drawView(view, newTop, -1);
        }
        return 0;
    }

    //    private int drawTopView(View view, int top) {
//        addView(view, 0);
//        return layoutView(view, top);
//    }
//    private int drawView(View view, int top) {
//        addView(view);
//        return layoutView(view, top);
//    }
    private int drawView(View view, int top, int index) {
        if (index != -1) {
            addView(view, index);
        } else {
            addView(view);
        }
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


    private View getAnchorView() {
        int childCount = getChildCount();
        int topMin = getHeight();
        int top;
        View retView = null;
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
            top = getDecoratedTop(view) - layoutParams.topMargin;
            if (top > 0 && top < topMin) {
                topMin = top;
                retView = view;
            }
        }
        return retView;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int delta = getScrollDelta(dy);
        Log.d("TAG", "scrollVerticallyBy: delta: " + delta);
        offsetChildrenVertical(-delta);
        doLayoutChildren(recycler);
        return delta;
    }

    private int getScrollDelta(int dy) {
        int childCount = getChildCount();
        int itemCount = getItemCount();
        if (childCount == 0) {
            return 0;
        }

        final View topView = getChildAt(0);
        final View bottomView = getChildAt(childCount - 1);

        if (dy < 0) {
            mLock.lock();
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) topView.getLayoutParams();
            int topPosition = getPosition(topView);
            Log.d("TAGgetScrollDelta", "topPosition: " + topPosition);
//            if (topPosition > 0) {
            //              return dy;
            //          } else {
            int top = getDecoratedTop(topView) - layoutParams.topMargin;
            int ret = Math.max(top < 0 ? top : 0, dy);
            Log.d("TAGgetScrollDelta", "getScrollDelta: top: " + top + " ret:" + ret);
            mLock.unlock();
            return Math.max(top < 0 ? top : 0, dy);
            //        }
        } else {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) bottomView.getLayoutParams();
            int bottomPosition = getPosition(bottomView);
            if (bottomPosition < itemCount - 1) {
                return dy;
            } else {
                int bottom = getDecoratedBottom(bottomView) + layoutParams.bottomMargin - getHeight();
                return Math.min(bottom > 0 ? bottom : 0, dy);
            }
        }
    }

    private int getViewHeightWithMargins(View view) {
        measureChildWithMargins(view, 0, 0);
        return getDecoratedMeasuredHeight(view) + getTopAndBottomMargins(view);
    }

    int getTopAndBottomMargins(View view) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        return layoutParams.bottomMargin + layoutParams.topMargin;
    }
//    private int getScrollDelta(int dy) {
//        int childCount = getChildCount();
//        int itemCount = getItemCount();
//        if (childCount == 0){
//            return 0;
//        }
//
//        final View topView = getChildAt(0);
//        final View bottomView = getChildAt(childCount - 1);
//
//        int viewSpan = getDecoratedBottom(bottomView) - getDecoratedTop(topView);
//        if (viewSpan <= getHeight()) {
//            return 0;
//        }
//
//        int delta = 0;
//        if (dy < 0){
//            View firstView = getChildAt(0);
//            int firstViewAdapterPos = getPosition(firstView);
//            if (firstViewAdapterPos > 0){
//                delta = dy;
//            } else {
//                int viewTop = getDecoratedTop(firstView);
//                delta = Math.max(viewTop, dy);
//            }
//        } else if (dy > 0){
//            View lastView = getChildAt(childCount - 1);
//            int lastViewAdapterPos = getPosition(lastView);
//            if (lastViewAdapterPos < itemCount - 1){
//                delta = dy;
//            } else {
//                int viewBottom = getDecoratedBottom(lastView);
//                int parentBottom = getHeight();
//                delta = Math.min(viewBottom - parentBottom, dy);
//            }
//        }
//        return delta;
//    }
}

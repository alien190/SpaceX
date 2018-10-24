package com.example.ivanovnv.spacex.common;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ivanovnv.spacex.R;

public class LaunchItemView extends CardView {
    private View mView;
    private ConstraintLayout mClTitle;
    private ConstraintLayout mClRoot;
    private ImageView mIvMissionIcon;
    private int mRootHeight;
    private int mRootHeightWithMargins;
    private int mTitleHeight;
    private int mTitleHeightWithMargins;

    public LaunchItemView(Context context) {
        this(context, null);
    }

    public LaunchItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LaunchItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mView = inflate(context, R.layout.launch_item_view, this);
        mClRoot = mView.findViewById(R.id.cl_root);
        mClTitle = mView.findViewById(R.id.cl_title);
        mIvMissionIcon = mView.findViewById(R.id.iv_mission_icon);
        measureHeight();
    }

    private void measureHeight() {
        measureChild(mClRoot, 0, 0);
        CardView.LayoutParams rootLayoutParams = (LayoutParams) mClRoot.getLayoutParams();
        ConstraintLayout.LayoutParams titleLayoutParams = (ConstraintLayout.LayoutParams) mClTitle.getLayoutParams();
        mTitleHeight = mClTitle.getMeasuredHeight();
        mTitleHeightWithMargins = mTitleHeight + titleLayoutParams.topMargin + titleLayoutParams.bottomMargin
                + rootLayoutParams.topMargin + rootLayoutParams.bottomMargin;
        mRootHeight = mClRoot.getMeasuredHeight();
        mRootHeightWithMargins = mRootHeight + rootLayoutParams.topMargin + rootLayoutParams.bottomMargin;
    }


    public void setCollapsed(boolean isCollapsed) {
        if (isCollapsed) {
            setViewSize(mView, mTitleHeightWithMargins, -1);
            setViewSize(mIvMissionIcon, mTitleHeight, mTitleHeight);
        } else {
            setViewSize(mView, mRootHeightWithMargins, -1);
        }
    }

    public void setScale(int percent) {
        if (percent >= 0 && percent <= 100) {
            int height = mTitleHeightWithMargins + (mRootHeightWithMargins - mTitleHeightWithMargins) * percent / 100;
            setViewSize(mView, height, -1);
            height = mTitleHeight + (mRootHeight - mTitleHeight) * percent / 100;
            setViewSize(mIvMissionIcon, height, height);
        }
    }

    private void setViewSize(View view, int height, int width) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (height != -1) {
            layoutParams.height = height;
        }
        if (width != -1) {
            layoutParams.width = width;
        }
        view.setLayoutParams(layoutParams);
    }
}

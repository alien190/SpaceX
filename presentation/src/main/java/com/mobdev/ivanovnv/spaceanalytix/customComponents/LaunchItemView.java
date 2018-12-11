package com.mobdev.ivanovnv.spaceanalytix.customComponents;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobdev.ivanovnv.spaceanalytix.R;

public class LaunchItemView extends CardView {
    private View mView;
    private LinearLayout mClTitle;
    private RelativeLayout mClRoot;
    private ScaledImageView mSivMissionIcon;
    private TextView mTvMissionName;
    private TextView mTvDetails;
    private TextView mTvLaunchDate;
    private View mAnimationHelperView;
    private int mRootHeight;
    private int mRootHeightWithMargins;
    private int mTitleHeight;
    private int mTitleHeightWithMargins;
    private int mTopAndBottomMargins;


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
        mClRoot = mView.findViewById(R.id.rl_root);
        mClTitle = mView.findViewById(R.id.ll_title);
        mSivMissionIcon = mView.findViewById(R.id.siv_mission_icon);
        mTvMissionName = mView.findViewById(R.id.tv_mission_name);
        mTvDetails = mView.findViewById(R.id.tv_details);
        mTvLaunchDate = mView.findViewById(R.id.tv_launch_date);
        mAnimationHelperView = mView.findViewById(R.id.view_animate_helper);
        measureHeight();
    }


    private void measureHeight() {
        measureChild(mClRoot, 0, 0);
        LayoutParams rootLayoutParams = (LayoutParams) mClRoot.getLayoutParams();
        LinearLayout.LayoutParams titleLayoutParams = (LinearLayout.LayoutParams) mClTitle.getLayoutParams();
        mTitleHeight = mClTitle.getMeasuredHeight();
        mTitleHeightWithMargins = mTitleHeight + titleLayoutParams.topMargin + titleLayoutParams.bottomMargin
                + rootLayoutParams.topMargin + rootLayoutParams.bottomMargin;
        mRootHeight = mClRoot.getMeasuredHeight();
        mRootHeightWithMargins = mRootHeight + rootLayoutParams.topMargin + rootLayoutParams.bottomMargin;
        mTopAndBottomMargins = rootLayoutParams.topMargin + rootLayoutParams.bottomMargin;
        mTvMissionName.offsetLeftAndRight(mRootHeightWithMargins + mTopAndBottomMargins);
        mTvMissionName.setRight(mSivMissionIcon.getLeft() + mRootHeightWithMargins);
        mTvMissionName.setBottom(mSivMissionIcon.getTop() + mRootHeightWithMargins);
    }


    public void setCollapsed(boolean isCollapsed) {
        if (isCollapsed) {
            setViewSize(mView, mTitleHeightWithMargins, -1);
            setViewSize(mSivMissionIcon, mTitleHeight, mTitleHeight);
        } else {
            setViewSize(mView, mRootHeightWithMargins, -1);
        }
    }

    public void setScale(float percent) {
        if (percent < 0) {
            percent = 0;
        } else if (percent > 100) {
            percent = 100;
        }
        int overallHeight = (int) (mTitleHeightWithMargins + (mRootHeightWithMargins - mTitleHeightWithMargins) * percent / 100);
        setViewSize(mView, overallHeight, -1);
        int iconHeight = (int) (mTitleHeight + (mRootHeight - mTitleHeight) * percent / 100);
        setViewSize(mSivMissionIcon, iconHeight, iconHeight);
    }

    public void updateContentSize(int value) {
        value -= 2 * mTopAndBottomMargins;
        mSivMissionIcon.setImageHeight(value);
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

    public int getRootHeightWithMargins() {
        measureHeight();
        return mRootHeightWithMargins;
    }

    public int getTitleHeightWithMargins() {
        measureHeight();
        return mTitleHeightWithMargins;
    }

    public void setMissionName(String name) {
        if (mTvMissionName != null && name != null) {
            mTvMissionName.setText(name);
        }
    }

    public void setDetails(String details) {
        if (mTvDetails != null && details != null) {
            mTvDetails.setText(details);
        }
    }

    public void setLaunchDate(String date) {
        if (mTvLaunchDate != null && date != null) {
            mTvLaunchDate.setText(date);
        }
    }

    public void setMissionIconBitmap(Bitmap bitmap) {
        mSivMissionIcon.setBitmap(bitmap);
    }


    public View getAnimationHelper() {
        int top = mSivMissionIcon.getTop();
        int left = mSivMissionIcon.getLeft();
        int height = mSivMissionIcon.getImageHeight();
        mAnimationHelperView.setTop(top);
        mAnimationHelperView.setLeft(left);
        mAnimationHelperView.setBottom(top + height);
        mAnimationHelperView.setRight(left + height);

        return mAnimationHelperView;
    }

}

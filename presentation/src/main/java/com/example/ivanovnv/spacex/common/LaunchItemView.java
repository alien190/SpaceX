package com.example.ivanovnv.spacex.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ivanovnv.spacex.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class LaunchItemView extends CardView {
    public static final int MIN_SCALE = 0;
    public static final int MAX_SCALE = 100;
    private View mView;
    private LinearLayout mClTitle;
    private RelativeLayout mClRoot;
    private ImageView mIvMissionIcon;
    private TextView mTvMissionName;
    private TextView mTvDetails;
    private TextView mTvLaunchDate;
    private int mRootHeight;
    private int mRootHeightWithMargins;
    private int mTitleHeight;
    private int mTitleHeightWithMargins;
    private int mTopAndBottomMargins;
    private int mIconHeight;
    private Bitmap mMissionIconBitmap;
    private Disposable mImageDisposable;

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
        mTvMissionName = mView.findViewById(R.id.tv_mission_name);
        mTvDetails = mView.findViewById(R.id.tv_details);
        mTvLaunchDate = mView.findViewById(R.id.tv_launch_date);
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
        mTvMissionName.setRight(mIvMissionIcon.getLeft() + mRootHeightWithMargins);
        mTvMissionName.setBottom(mIvMissionIcon.getTop() + mRootHeightWithMargins);
    }


    public void setCollapsed(boolean isCollapsed) {
        if (isCollapsed) {
            setViewSize(mView, mTitleHeightWithMargins, -1);
            setViewSize(mIvMissionIcon, mTitleHeight, mTitleHeight);
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
        setViewSize(mIvMissionIcon, iconHeight, iconHeight);
    }

    @SuppressLint("CheckResult")
    public void updateContentSize(int value) {
        final int newSize = value - 2 * mTopAndBottomMargins;
        final int viewSize = Math.min(mIvMissionIcon.getWidth(), mIvMissionIcon.getHeight());

        if (mMissionIconBitmap != null && mIconHeight != newSize && newSize > 0 && viewSize > 0) {
//            if (mImageDisposable != null) {
//                mImageDisposable.dispose();
//            }

            mImageDisposable = Single.fromCallable((Callable<Bitmap>) () -> {
                int originalSize = Math.max(mMissionIconBitmap.getHeight(), mMissionIconBitmap.getWidth());
                float scale = (float) (newSize) / originalSize;
                Bitmap dstBitmap = Bitmap.createBitmap(viewSize, viewSize, Bitmap.Config.ARGB_8888);
                int iOrig;
                int yOrig;
                for (int i = 0; i < viewSize; i++) {
                    for (int j = 0; j < viewSize; j++) {
                        iOrig = (int) (i / scale);
                        yOrig = (int) (j / scale);
                        if (iOrig < originalSize && yOrig < originalSize) {
                            dstBitmap.setPixel(i, j, mMissionIconBitmap.getPixel(iOrig, yOrig));
                        } else {
                            dstBitmap.setPixel(i, j, Color.argb(0, 255, 255, 255));
                        }
                    }
                }
                return dstBitmap;
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(bitmap -> {
                        if (mIvMissionIcon.getDrawable() instanceof BitmapDrawable) {
                            Bitmap oldBitmap = ((BitmapDrawable) mIvMissionIcon.getDrawable()).getBitmap();
                            if (oldBitmap != null) {
                                oldBitmap.recycle();
                            }
                        }
                        mIvMissionIcon.setImageBitmap(bitmap);
                        mIconHeight = newSize;
                    });


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

    public void setMissionIconURL(String url) {
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.ic_rocket_stub)
                .error(R.drawable.ic_rocket_stub)
                .into(mIvMissionIcon, new Callback() {
                    @Override
                    public void onSuccess() {
                        Timber.d("setMissionIconURL mIvMissionIcon.isLaidOut:%b", mIvMissionIcon.isLaidOut());
                        updateContentSize(mIconHeight);
                        onRequestLayout();
                    }

                    @Override
                    public void onError(Exception e) {
                        Timber.d("setMissionIconURL mIvMissionIcon.isLaidOut:%b", mIvMissionIcon.isLaidOut());
                        updateContentSize(mIconHeight);
                        onRequestLayout();
                    }
                });
    }

    public void setMissionIconBitmap(Bitmap bitmap) {
        mMissionIconBitmap = bitmap;
        updateContentSize(mIconHeight);
//        if (mIvMissionIcon != null && bitmap != null) {
//            try {
//                mIvMissionIcon.setImageBitmap(bitmap);
//                updateContentSize(mIconHeight);
//                onRequestLayout();
//                Timber.d("setMissionIconBitmap mIvMissionIcon.isLaidOut:%b", mIvMissionIcon.isLaidOut());
//            } catch (Throwable throwable) {
//                Timber.d(throwable);
//            }
//        }
    }

    public void setIconTransitionName(String name) {
        if (mIvMissionIcon != null && name != null) {
            mIvMissionIcon.setTransitionName(name);
        }
    }

    public ImageView getIvMissionIcon() {
        return mIvMissionIcon;
    }


    public void onRequestLayout() {
//        if (!mTvMissionName.isInLayout()) {
//        mIvMissionIcon.requestLayout();
//        Timber.d("onRequestLayout: mIvMissionIcon");
//        }
//        if (!mClTitle.isInLayout()) {
//        mClTitle.requestLayout();
//        }
//
//        if (!mTvMissionName.isInLayout()) {
//        mTvMissionName.requestLayout();
//        Timber.d("onRequestLayout: mTvMissionName");
//        }
    }

//    @Override
//    protected void onAttachedToWindow() {
//        ViewGroup parent = (ViewGroup) getParent();
//        if (parent != null) {
//            int width = (int)(0.5 * parent.getWidth());
//            setViewSize(mIvMissionIcon,width, width);
//        }
//        super.onAttachedToWindow();
//    }
}

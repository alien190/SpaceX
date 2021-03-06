package com.mobdev.ivanovnv.spaceanalytix.customComponents;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.view.View;

import com.crashlytics.android.Crashlytics;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import io.reactivex.BackpressureOverflowStrategy;
import io.reactivex.functions.Function;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class ScaledImageView extends View {
    private int mWidthSpecSize;
    private int mHeightSpecSize;
    private Bitmap mOriginalBitmap;
    private Bitmap mDrawBitmap;
    private Rect mDstRect;
    private PublishProcessor<Integer> mScalePublishProcessor;
    private Lock mBitmapLock;
    private int mBitmapHeight;

    public ScaledImageView(Context context) {
        super(context);
        init(context, null);
    }

    public ScaledImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ScaledImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mDstRect = new Rect(0, 0, 0, 0);
        mScalePublishProcessor = PublishProcessor.create();
        mBitmapLock = new ReentrantLock();
        mBitmapHeight = 0;
        initObserver();
    }

    @SuppressLint("CheckResult")
    private void initObserver() {
        mScalePublishProcessor
                .filter(value -> value > 0 && value != mBitmapHeight && mOriginalBitmap != null)
                .onBackpressureBuffer(1,
                        () -> Timber.d("initObserver: buffer overflow"),
                        BackpressureOverflowStrategy.DROP_OLDEST)
                .observeOn(Schedulers.computation(), false, 1)
                //.observeOn(Schedulers.computation())
                .map(this::mCreateScaledBitmap)
                .map(this::mSetScaledBitmap)
                .subscribe(
                        (value) -> {
                            invalidate();
                            Timber.d("initObserver invalidate ThreadId:%d", Thread.currentThread().getId());
                        }, throwable -> {
                            Timber.d(throwable);
                            Crashlytics.logException(throwable);
                            initObserver();
                        });
    }

    private Bitmap mCreateScaledBitmap(Integer value) {
        Timber.d("mCreateScaledBitmap value:%d", value);
        if (value > mHeightSpecSize) {
            value = mHeightSpecSize;
        }
        return Bitmap.createScaledBitmap(mOriginalBitmap, value, value, false);
    }

    private Boolean mSetScaledBitmap(Bitmap bitmap) {
        try {
            mBitmapLock.lock();
            if (bitmap != mDrawBitmap && mDrawBitmap != null) {
                mDrawBitmap.recycle();
            }
            mDrawBitmap = bitmap;
            mBitmapHeight = bitmap.getHeight();
        } catch (Throwable throwable) {
            Timber.d(throwable);
        }
        mBitmapLock.unlock();
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        mHeightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(mWidthSpecSize, mHeightSpecSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {
            mBitmapLock.lock();
            if (mDrawBitmap != null) {
                mDstRect.right = mDrawBitmap.getWidth();
                mDstRect.bottom = mDrawBitmap.getHeight();
                canvas.drawBitmap(mDrawBitmap, null, mDstRect, null);
            }
        } catch (Throwable throwable) {
            Timber.d(throwable);
        }
        mBitmapLock.unlock();
    }

    public void setBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            mOriginalBitmap = Bitmap.createScaledBitmap(bitmap, mWidthSpecSize, mHeightSpecSize, false);
            if (bitmap != mOriginalBitmap) {
                bitmap.recycle();
            }
            int height = mBitmapHeight;
            mBitmapHeight = 0;
            setImageHeight(height);
        }
    }

    public void setBitmap(Bitmap bitmap, int height) {
        mBitmapHeight = height;
        setBitmap(bitmap);
    }

    public Bitmap getBitmap() {
        return mOriginalBitmap;
    }

    public void setImageHeight(int height) {
        mScalePublishProcessor.onNext(height);
    }

    public int getImageHeight() {
        return mBitmapHeight;
    }
}

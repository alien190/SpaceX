package com.example.ivanovnv.spacex.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class ScaledImageView extends View {
    private int mWidthSpecSize;
    private int mHeightSpecSize;
    private Bitmap mBitmap;
    private Rect mDstRect;

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

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        mHeightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(mWidthSpecSize, mHeightSpecSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap != null) {
            mDstRect.right = mBitmap.getWidth();
            mDstRect.bottom = mBitmap.getHeight();
            canvas.drawBitmap(mBitmap, null, mDstRect, null);
        }
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }
}

package com.ivanovnv.ivanovnv.spaceanalytix.customComponents;

import android.content.Context;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;

public class SquareImageView extends AppCompatImageView {

    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mWidthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(mWidthSpecSize, mWidthSpecSize);
    }
}

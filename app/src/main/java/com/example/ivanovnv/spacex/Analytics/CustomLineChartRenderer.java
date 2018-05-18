package com.example.ivanovnv.spacex.Analytics;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.renderer.LineChartRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class CustomLineChartRenderer extends LineChartRenderer {

    private ViewPortHandler mViewPortHandler;

    public CustomLineChartRenderer(LineDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);

        mViewPortHandler = viewPortHandler;
    }

    @Override
    public void drawValue(Canvas c, IValueFormatter formatter, float value, Entry entry, int dataSetIndex, float x, float y, int color) {
        //super.drawValue(c, formatter, value, entry, dataSetIndex, x, y, color);

//        Paint paint = super.mDrawPaint;
//        paint.setColor(color);
//        paint.setTextSize(25f);
//        paint.setTextAlign(Paint.Align.CENTER);
       // paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        mValuePaint.setColor(color);
        Utils.drawXAxisValue(c, formatter.getFormattedValue(value, entry, dataSetIndex, mViewPortHandler),
                x, y, mValuePaint, MPPointF.getInstance(), -45);
    }
}

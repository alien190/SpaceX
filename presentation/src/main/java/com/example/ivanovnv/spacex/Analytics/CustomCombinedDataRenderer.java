package com.example.ivanovnv.spacex.Analytics;


import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.CombinedChart;

import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.renderer.BubbleChartRenderer;
import com.github.mikephil.charting.renderer.CandleStickChartRenderer;
import com.github.mikephil.charting.renderer.CombinedChartRenderer;
import com.github.mikephil.charting.renderer.ScatterChartRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class CustomCombinedDataRenderer extends CombinedChartRenderer {
    public CustomCombinedDataRenderer(CombinedChart chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);

    }



    @Override
    public void createRenderers() {
       // super.createRenderers();
        mRenderers.clear();

        CombinedChart chart = (CombinedChart)mChart.get();
        if (chart == null)
            return;

        CombinedChart.DrawOrder[] orders = chart.getDrawOrder();

        for (CombinedChart.DrawOrder order : orders) {

            switch (order) {
                case BAR:
                    if (chart.getBarData() != null)
                        mRenderers.add(new BarChartRenderer(chart, mAnimator, mViewPortHandler));
                    break;
                case BUBBLE:
                    if (chart.getBubbleData() != null)
                        mRenderers.add(new BubbleChartRenderer(chart, mAnimator, mViewPortHandler));
                    break;
                case LINE:
                    if (chart.getLineData() != null)
                        mRenderers.add(new CustomLineChartRenderer(chart, mAnimator, mViewPortHandler));
                    break;
                case CANDLE:
                    if (chart.getCandleData() != null)
                        mRenderers.add(new CandleStickChartRenderer(chart, mAnimator, mViewPortHandler));
                    break;
                case SCATTER:
                    if (chart.getScatterData() != null)
                        mRenderers.add(new ScatterChartRenderer(chart, mAnimator, mViewPortHandler));
                    break;
            }
        }
    }
}
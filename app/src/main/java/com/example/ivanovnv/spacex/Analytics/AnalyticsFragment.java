package com.example.ivanovnv.spacex.Analytics;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.CombinedChart.DrawOrder;
import com.example.ivanovnv.spacex.App;
import com.example.ivanovnv.spacex.DB.LaunchDao;
import com.example.ivanovnv.spacex.DB.LaunchYearStatistic;
import com.example.ivanovnv.spacex.R;
import com.example.ivanovnv.spacex.SpaceXAPI.Launch;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AnalyticsFragment extends Fragment implements OnChartGestureListener {

    private CombinedChart mChart;

    public static AnalyticsFragment newInstance() {

        Bundle args = new Bundle();

        AnalyticsFragment fragment = new AnalyticsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_analytics, container, false);

        // create a new chart object
//        mChart = new BarChart(getActivity());
//        mChart.getDescription().setEnabled(false);
//        mChart.setOnChartGestureListener(this);
//
//
//        mChart.setDrawGridBackground(false);
//        mChart.setDrawBarShadow(false);
//
//        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");
//
//        //mChart.setData(generateBarData(1, 20000, 12));
//
//        Legend l = mChart.getLegend();
//        l.setTypeface(tf);
//
//        YAxis leftAxis = mChart.getAxisLeft();
//        leftAxis.setTypeface(tf);
//        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//
//        mChart.getAxisRight().setEnabled(false);
//
//        XAxis xAxis = mChart.getXAxis();
//        xAxis.setEnabled(true);
//        xAxis.setValueFormatter((value, axis) -> "" + ((int)value));
//
//        // programatically add the chart
//        FrameLayout parent = v.findViewById(R.id.parentLayout);
//        parent.addView(mChart);

        mChart = v.findViewById(R.id.chart1);
        mChart.getDescription().setEnabled(false);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);

        // draw bars behind lines
        mChart.setDrawOrder(new DrawOrder[]{
                DrawOrder.BAR, DrawOrder.BUBBLE, DrawOrder.CANDLE, DrawOrder.LINE, DrawOrder.SCATTER
        });

        Legend l = mChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setAxisMinimum(2005f);
        xAxis.setAxisMaximum(2019f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return "" + ((int) value);
            }
        });

        setBarDataFromDb();

        return v;
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }


    private BarData setBarDataFromDb() {
        Single.create((SingleOnSubscribe<List<LaunchYearStatistic>>)
                emitter -> emitter.onSuccess(getLaunchDao().getLaunchYearStatistic()))
                .flatMap(launchYearStatistic -> Single.just(convertLaunchesToBarData(launchYearStatistic)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(barData -> mChart.setData(barData), Throwable::printStackTrace);
        //disposable.dispose();
        return null;
    }

    private CombinedData convertLaunchesToBarData(List<LaunchYearStatistic> launchYearStatistics) {
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        CombinedData combinedData = new CombinedData();

        ArrayList<IBarDataSet> sets = new ArrayList<>();
        ArrayList<BarEntry> entriesCount = new ArrayList<>();
        ArrayList<Entry> entriesWeight = new ArrayList<>();

        for (LaunchYearStatistic launchYearStatistic : launchYearStatistics) {
            int year = Integer.valueOf(launchYearStatistic.getLaunch_year());
            entriesCount.add(new BarEntry(year, launchYearStatistic.getCount()));
            entriesWeight.add(new Entry(year, launchYearStatistic.getPayload_mass_kg_sum()));
        }

        BarDataSet dsCount = new BarDataSet(entriesCount, "count");
        dsCount.setColors(Color.rgb(0, 0, 0));
        dsCount.setAxisDependency(YAxis.AxisDependency.RIGHT);
        sets.add(dsCount);

//        BarDataSet dsWight = new BarDataSet(entriesWeight, "weight");
//        dsWight.setColors(Color.rgb(0, 255, 0));
//        sets.add(dsWight);
//
//        BarData d = new BarData(sets);
//        d.setValueTypeface(tf);


        BarData barData = new BarData(sets);
        barData.setValueTypeface(tf);


        combinedData.setData(barData);


        LineDataSet set = new LineDataSet(entriesWeight, "Line DataSet");
        int color = Color.rgb(240, 150, 40);
        set.setColor(color);
        set.setLineWidth(2.5f);
        set.setCircleColor(color);
        set.setCircleRadius(5f);
        set.setFillColor(color);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(color);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        LineData lineData = new LineData(set);


        combinedData.setData(lineData);

        return combinedData;
//
//        ArrayList<Entry> entries = new ArrayList<>();
//        for (Launch launch : launches)  {
//            long x = launch.getLaunch_date_unix() / 86400;
//            int y = launch.getPayload_mass_kg_sum();
//            entries.add(new Entry(x, y));
//        }
//        LineDataSet dataSet = new LineDataSet(entries, "Time series");
//        LineData data = new LineData(dataSet);
//        return data;
    }

//    protected CombinedData generateBarData(int dataSets, float range, int count) {
//
//        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
//
//        ArrayList<IBarDataSet> sets = new ArrayList<IBarDataSet>();
//
//        for (int i = 0; i < dataSets; i++) {
//
//            ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
//
//            for (int j = 0; j < count; j++) {
//                entries.add(new BarEntry(j, (float) (Math.random() * range) + range / 4));
//            }
//
//            BarDataSet ds = new BarDataSet(entries, getLabel(i));
//            ds.setColors(Color.rgb(0, 0, 0));
//            sets.add(ds);
//        }
//
//        BarData barData = new BarData(sets);
//        barData.setValueTypeface(tf);
//
//        CombinedData combinedData = new CombinedData();
//
//        combinedData.setData(barData);
//        return combinedData;
//    }
//
//    private String[] mLabels = new String[]{"Company A", "Company B", "Company C", "Company D", "Company E", "Company F"};
////    private String[] mXVals = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec" };
//
//    private String getLabel(int i) {
//        return mLabels[i];
//    }

    private LaunchDao getLaunchDao() {
        return ((App) getActivity().getApplication()).getLaunchDataBase().getLaunchDao();
    }
}

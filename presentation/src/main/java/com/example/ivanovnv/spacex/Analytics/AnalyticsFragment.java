package com.example.ivanovnv.spacex.Analytics;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.AsyncLayoutInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.CombinedChart.DrawOrder;
import com.example.ivanovnv.spacex.App;
import com.example.ivanovnv.spacex.DB.LaunchDao;
import com.example.ivanovnv.spacex.DB.LaunchYearStatistic;
import com.example.ivanovnv.spacex.R;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AnalyticsFragment extends Fragment implements OnChartGestureListener {

    private String TAG = this.getClass().getSimpleName();
    private CombinedChart mChart;
    private View view;

    public static AnalyticsFragment newInstance() {

        Bundle args = new Bundle();

        AnalyticsFragment fragment = new AnalyticsFragment();
        fragment.setArguments(args);
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {

            view = inflater.inflate(R.layout.fr_analytics, container, false);

            mChart = view.findViewById(R.id.chart1);
            mChart.getDescription().setEnabled(false);
            mChart.setBackgroundColor(Color.WHITE);
            mChart.setDrawGridBackground(false);
            mChart.setDrawBarShadow(false);
            mChart.setHighlightFullBarEnabled(false);
            // mChart.setOnChartValueSelectedListener(this);


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
            xAxis.setValueFormatter((value, axis) -> "" + ((int) value));

            setChartDataFromDb();
        }

        return view;

//        Log.d(TAG, "onCreateView: ");
//        AsyncLayoutInflater asyncInflater = new AsyncLayoutInflater(getContext());
//        asyncInflater.inflate(R.layout.fr_analytics, container, new AsyncLayoutInflater.OnInflateFinishedListener() {
//            @Override
//            public void onInflateFinished(@NonNull View view, int resid, @Nullable ViewGroup parent) {
//                Log.d(TAG, "onInflateFinished: ");
//            }
//        });
//        View v = inflater.inflate(R.layout.fr_analytics_stub, container, false);
//        return v;
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

        Highlight highlight = mChart.getHighlightByTouchPoint(me.getX(), me.getY());
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DetailAnalyticsFragment.newInstance(highlight.getX()))
                .addToBackStack(DetailAnalyticsFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        int i = 1;
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }


    @SuppressLint("CheckResult")
    private void setChartDataFromDb() {

        Single<List<LaunchYearStatistic>> allLaunches = Single.create((SingleOnSubscribe<List<LaunchYearStatistic>>)
                emitter -> emitter.onSuccess(getLaunchDao().getLaunchYearStatistic()))
                //.flatMap(launchYearStatistic -> Single.just(convertLaunchesToBarData(launchYearStatistic)))
                .subscribeOn(Schedulers.io());

        Single<List<LaunchYearStatistic>> failedLaunches = Single.create((SingleOnSubscribe<List<LaunchYearStatistic>>)
                emitter -> emitter.onSuccess(getLaunchDao().getLaunchYearStatisticFailed()))
                //.flatMap(launchYearStatistic -> Single.just(convertLaunchesToBarData(launchYearStatistic)))
                .subscribeOn(Schedulers.io());

        Single.zip(allLaunches, failedLaunches, this::convertLaunchesToBarData).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(combinedData -> mChart.setData(combinedData), Throwable::printStackTrace);

//        Single.create((SingleOnSubscribe<List<LaunchYearStatistic>>)
//                emitter -> emitter.onSuccess(getLaunchDao().getLaunchYearStatistic()))
//                .flatMap(launchYearStatistic -> Single.just(convertLaunchesToBarData(launchYearStatistic)))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(barData -> mChart.setData(barData), Throwable::printStackTrace);
        //disposable.dispose();
        //return null;
    }

    private CombinedData convertLaunchesToBarData(List<LaunchYearStatistic> launchYearStatisticsAll, List<LaunchYearStatistic> launchYearStatisticsFailed) {
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        CombinedData combinedData = new CombinedData();

        ArrayList<IBarDataSet> sets = new ArrayList<>();
        ArrayList<BarEntry> entriesCountAll = new ArrayList<>();
        ArrayList<BarEntry> entriesCountFailed = new ArrayList<>();
        ArrayList<Entry> entriesWeight = new ArrayList<>();

        for (LaunchYearStatistic launchYearStatistic : launchYearStatisticsAll) {
            int year = Integer.valueOf(launchYearStatistic.getLaunch_year());
            entriesCountAll.add(new BarEntry(year, launchYearStatistic.getCount()));
            int payload_mass_kg_sum = launchYearStatistic.getPayload_mass_kg_sum();

            for (LaunchYearStatistic launchYearStatisticFailed : launchYearStatisticsFailed) {
                if (launchYearStatisticFailed.getLaunch_year().equals(launchYearStatistic.getLaunch_year())) {
                    payload_mass_kg_sum = payload_mass_kg_sum - launchYearStatisticFailed.getPayload_mass_kg_sum();
                }
            }

            entriesWeight.add(new Entry(year, payload_mass_kg_sum));
        }

        BarDataSet dsCount = new BarDataSet(entriesCountAll, getString(R.string.launch_count));
        dsCount.setColors(Color.rgb(0, 0, 0));
        dsCount.setAxisDependency(YAxis.AxisDependency.RIGHT);
        dsCount.setValueTextSize(10f);
        sets.add(dsCount);


        for (LaunchYearStatistic launchYearStatistic : launchYearStatisticsFailed) {
            int year = Integer.valueOf(launchYearStatistic.getLaunch_year());
            entriesCountFailed.add(new BarEntry(year, launchYearStatistic.getCount()));
        }

        BarDataSet dsCountFailed = new BarDataSet(entriesCountFailed, getString(R.string.launch_failed));
        dsCountFailed.setColors(Color.rgb(255, 0, 0));
        dsCountFailed.setValueTextColor(Color.rgb(255, 0, 0));
        dsCountFailed.setValueTextSize(10f);
        dsCountFailed.setAxisDependency(YAxis.AxisDependency.RIGHT);
        sets.add(dsCountFailed);

//        BarDataSet dsWight = new BarDataSet(entriesWeight, "weight");
//        dsWight.setColors(Color.rgb(0, 255, 0));
//        sets.add(dsWight);
//
//        BarData d = new BarData(sets);
//        d.setValueTypeface(tf);


        BarData barData = new BarData(sets);
        barData.setValueTypeface(tf);


        combinedData.setData(barData);


        LineDataSet set = new LineDataSet(entriesWeight, getString(R.string.weight));
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

    @Override
    public void onStart() {
        super.onStart();
        mChart.setOnChartGestureListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mChart.setOnChartGestureListener(null);
        Log.d("TAG", "AnalyticsFragment onPause: ");
    }
}

package com.example.ivanovnv.spacex.Analytics;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivanovnv.spacex.App;
import com.example.ivanovnv.spacex.DB.LaunchDao;
import com.example.ivanovnv.spacex.DB.LaunchYearStatistic;
import com.example.ivanovnv.spacex.R;
import com.example.ivanovnv.spacex.SpaceXAPI.Launch;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
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
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DetailAnalyticsFragment extends Fragment {

    private static final String YEAR_KEY = "com.example.ivanovnv.spacex.Analytics.YEAR_KEY";
    private CombinedChart mChart;

    public static DetailAnalyticsFragment newInstance(float year) {

        Bundle args = new Bundle();

        args.putFloat(YEAR_KEY, year);
        DetailAnalyticsFragment fragment = new DetailAnalyticsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fr_detail_analytics, container, false);
        float year = getArguments().getFloat(YEAR_KEY, 0);

        mChart = v.findViewById(R.id.detail_chart);
        mChart.getDescription().setEnabled(false);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);
        //mChart.setOnChartValueSelectedListener(this);

        // draw bars behind lines
        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.BUBBLE, CombinedChart.DrawOrder.CANDLE, CombinedChart.DrawOrder.LINE, CombinedChart.DrawOrder.SCATTER
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
        //xAxis.setAxisMinimum(2005f);
       // xAxis.setAxisMaximum(2019f);
        xAxis.setGranularity(1f);
        //xAxis.setValueFormatter((value, axis) -> "" + ((int) value));
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Date date = new Date((long) value * 86400000);
                DateFormat dateFormat = new SimpleDateFormat("dd.MM");
                return dateFormat.format(date);
            }
        });

        setChartDataFromDb((int)year);

        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @SuppressLint("CheckResult")
    private void setChartDataFromDb(int year) {

        Single.create((SingleOnSubscribe<List<Launch>>)
                emitter -> emitter.onSuccess(getLaunchDao().getLaunchesInYear(String.valueOf((year)))))
                .flatMap(launches -> Single.just(convertLaunchesToBarData(launches)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(barData -> mChart.setData(barData), Throwable::printStackTrace);
        //disposable.dispose();

    }
    private LaunchDao getLaunchDao() {
        return ((App) getActivity().getApplication()).getLaunchDataBase().getLaunchDao();
    }

    private CombinedData convertLaunchesToBarData(List<Launch> launches) {

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        CombinedData combinedData = new CombinedData();

        ArrayList<IBarDataSet> sets = new ArrayList<>();
        ArrayList<BarEntry> entriesCount = new ArrayList<>();
        ArrayList<Entry> entriesWeight = new ArrayList<>();

        for (Launch launch : launches) {
            int day = launch.getLaunch_date_unix() / 86400;
            entriesCount.add(new BarEntry(day, 1));
            entriesWeight.add(new Entry(day, launch.getPayload_mass_kg_sum()));
        }

        BarDataSet dsCount = new BarDataSet(entriesCount, "Launches");
        dsCount.setColors(Color.rgb(0, 0, 0));
        dsCount.setAxisDependency(YAxis.AxisDependency.RIGHT);
        dsCount.setValueTextSize(10f);
        sets.add(dsCount);

        BarData barData = new BarData(sets);
        barData.setValueTypeface(tf);


        combinedData.setData(barData);


        LineDataSet set = new LineDataSet(entriesWeight, "Weight");
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
    }
}

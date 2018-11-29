package com.example.ivanovnv.spaceanalytix.customComponents;

import androidx.databinding.BindingAdapter;

import android.graphics.Bitmap;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.view.View;
import android.widget.ImageView;

import com.example.domain.model.analytics.DomainAnalytics;
import com.example.domain.model.analytics.DomainAnalyticsItem;
import com.example.domain.model.filter.IAnalyticsFilter;
import com.example.ivanovnv.spaceanalytix.R;
import com.example.ivanovnv.spaceanalytix.currentPreferences.ICurrentPreferences;
import com.example.ivanovnv.spaceanalytix.ui.launchDetail.photos.PhotosListAdapter;
import com.example.ivanovnv.spaceanalytix.di.imageZoom.ImageZoomModule;
import com.example.ivanovnv.spaceanalytix.ui.imageZoom.ImageZoomActivity;
import com.example.ivanovnv.spaceanalytix.filterAdapter.BaseFilterAdapter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import toothpick.Scope;
import toothpick.Toothpick;

import static com.example.domain.model.filter.IAnalyticsFilter.ItemType.PAYLOAD_WEIGHT;

public class CustomBindingAdapter {

    @BindingAdapter("bind:imageBitmap")
    public static void setImageBitmap(ImageView imageView, Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    @BindingAdapter("bind:transitionName")
    public static void setTransitionName(ImageView imageView, Integer flightNumber) {
        imageView.setTransitionName("TransitionName" + String.valueOf(flightNumber));
    }

    @BindingAdapter("bind:visibilityValue")
    public static void setVisibilityValue(View view, Boolean isVisible) {
        if (isVisible != null) {
            if (view.getVisibility() == View.VISIBLE && !isVisible) {
                view.setVisibility(View.GONE);
            } else if (view.getVisibility() != View.VISIBLE && isVisible) {
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    @BindingAdapter("bind:photoSource")
    public static void setRecyclerViewPhotoSource(RecyclerView recyclerView, List<Bitmap> bitmapList) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null) {
            Scope scope = Toothpick.openScope("LaunchDetailFragment");
            initPhotoLayoutManager(scope, recyclerView);
            initPagerSnapHelper(scope, recyclerView);
            initPhotoListAdapter(scope, recyclerView, bitmapList);
        } else {
            if (bitmapList != null) {
                ((PhotosListAdapter) adapter).submitList(bitmapList);
                recyclerView.requestLayout();
            }
        }
    }

    private static void initPhotoListAdapter(Scope scope, RecyclerView recyclerView, List<Bitmap> bitmapList) {
        PhotosListAdapter photosListAdapter = scope.getInstance(PhotosListAdapter.class);
        photosListAdapter.setOnItemClickListener(image -> {
            Toothpick.closeScope("ImageZoom");
            Scope imageZoomScope = Toothpick.openScopes("Application", "ImageZoom");
            imageZoomScope.installModules(new ImageZoomModule(image));
            ImageZoomActivity.start(recyclerView.getContext());
        });
        photosListAdapter.submitList(bitmapList);
        recyclerView.setAdapter(photosListAdapter);
    }

    private static void initPhotoLayoutManager(Scope scope, RecyclerView recyclerView) {
        GridLayoutManager gridLayoutManager = scope.getInstance(GridLayoutManager.class);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    private static void initPagerSnapHelper(Scope scope, RecyclerView recyclerView) {
        PagerSnapHelper pagerSnapHelper = scope.getInstance(PagerSnapHelper.class);
        recyclerView.setOnFlingListener(null);
        pagerSnapHelper.attachToRecyclerView(recyclerView);
    }

    @BindingAdapter({"bind:scopeName"})
    public static void setRecyclerViewFilterItemSource(RecyclerView recyclerView,
                                                       String scopeName) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null) {
            Scope scope = Toothpick.openScope(scopeName);
            initFilterItemLayoutManager(scope, recyclerView);
            initFilterItemAdapter(
                    scope,
                    recyclerView);
        }
    }

    private static void initFilterItemLayoutManager(Scope scope, RecyclerView recyclerView) {
        FilterLayoutManager searchFilterLayoutManager = scope.getInstance(FilterLayoutManager.class);
        recyclerView.setLayoutManager(searchFilterLayoutManager);
    }

    private static void initFilterItemAdapter(Scope scope,
                                              RecyclerView recyclerView) {
        BaseFilterAdapter searchFilterAdapter = scope.getInstance(BaseFilterAdapter.class);
        recyclerView.setAdapter(searchFilterAdapter);
    }

    @BindingAdapter({"bind:data", "bind:currentPreferences"})
    public static void setData(Chart chart, DomainAnalytics domainAnalytics,
                               ICurrentPreferences currentPreferences) {
        if (domainAnalytics != null) {
            if (chart instanceof BarChart) {
                setBarData((BarChart) chart, domainAnalytics, currentPreferences);
            } else if (chart instanceof PieChart) {
                setPieData((PieChart) chart, domainAnalytics, currentPreferences);
            }
            chart.setVisibility(View.VISIBLE);
        } else {
            chart.setData(null);
            chart.setVisibility(View.GONE);
        }
    }

    private static void setBarData(BarChart chart, DomainAnalytics domainAnalytics,
                                   ICurrentPreferences currentPreferences) {
        try {
            List<IBarDataSet> sets = new ArrayList<>();
            List<BarEntry> entries = new ArrayList<>();

            for (DomainAnalyticsItem item : domainAnalytics.getItems()) {
                int year = Integer.valueOf(item.getBase());
                float value = getValue(item, domainAnalytics.getItemType(), currentPreferences);
                entries.add(new BarEntry(year, value));
            }

            BarDataSet dsCount = new BarDataSet(entries, "");
            dsCount.setAxisDependency(YAxis.AxisDependency.RIGHT);
            dsCount.setColor(chart.getContext().getResources().getColor(R.color.secondaryDarkColor));
            dsCount.setValueTextSize(14);
            sets.add(dsCount);

            XAxis xAxis = chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(true);
            xAxis.setGranularity(1f);
            xAxis.setTextSize(14);
            xAxis.setValueFormatter((v, a) -> String.valueOf((int) v));

            chart.getAxisLeft().setTextSize(14);
            chart.getAxisRight().setEnabled(false);

            chart.setData(new BarData(sets));
            chart.getDescription().setEnabled(false);
            chart.getLegend().setEnabled(false);
            chart.setExtraOffsets(0, 0, 0, 8);
            chart.animateXY(500, 500);
            chart.invalidate();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private static void setPieData(PieChart chart, DomainAnalytics domainAnalytics,
                                   ICurrentPreferences currentPreferences) {
        try {
            List<PieEntry> entries = new ArrayList<>();
            float total = 0;
            for (DomainAnalyticsItem item : domainAnalytics.getItems()) {
                float value = getValue(item, domainAnalytics.getItemType(), currentPreferences);
                entries.add(new PieEntry(value, item.getBase()));
                total += value;
            }

            PieDataSet dataSet = new PieDataSet(entries, "");
            dataSet.setXValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
            dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            dataSet.setValueLineVariableLength(true);
            dataSet.setValueLinePart1OffsetPercentage(90f);

            ArrayList<Integer> colors = new ArrayList<>();

            for (int c : ColorTemplate.MATERIAL_COLORS) {
                colors.add(c);
            }
            for (int c : ColorTemplate.COLORFUL_COLORS) {
                colors.add(c);
            }

            colors.add(ColorTemplate.getHoloBlue());
            dataSet.setColors(colors);
            dataSet.setValueTextSize(14);

            chart.setData(new PieData(dataSet));
            chart.getDescription().setEnabled(false);
            chart.setExtraOffsets(30, 30, 30, 30);

            chart.getLegend().setEnabled(false);
            chart.setEntryLabelColor(chart.getContext().getResources().getColor(R.color.primaryDarkColor));
            chart.animateY(500);
            String centerText = chart.getContext().getString(R.string.pie_chart_total) + "\n" + String.valueOf(total);
            chart.setCenterText(centerText);
            chart.setCenterTextSize(20);
            chart.invalidate();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private static float getValue(DomainAnalyticsItem item, IAnalyticsFilter.ItemType itemType,
                                  ICurrentPreferences currentPreferences) {
        float value;
        try {
            value = Float.valueOf(item.getValue());
            if (itemType == PAYLOAD_WEIGHT) {
                value = currentPreferences.getConverter().convertWeight(value);
            }
            return value;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return 0f;
    }
}
package com.example.ivanovnv.spacex.customComponents;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.domain.model.filter.ISearchFilter;
import com.example.ivanovnv.spacex.launchDetail.photos.PhotosListAdapter;
import com.example.ivanovnv.spacex.di.imageZoom.ImageZoomModule;
import com.example.ivanovnv.spacex.imageZoom.ImageZoomActivity;
import com.example.ivanovnv.spacex.launchSearch.adapter.BaseFilterAdapter;
import com.example.ivanovnv.spacex.launchSearch.adapter.SearchFilterAdapterByLaunchYear;
import com.example.ivanovnv.spacex.launchSearch.adapter.SearchFilterAdapterByRocketName;

import java.util.List;

import toothpick.Scope;
import toothpick.Toothpick;

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
            } else {
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
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    private static void initPagerSnapHelper(Scope scope, RecyclerView recyclerView) {
        PagerSnapHelper pagerSnapHelper = scope.getInstance(PagerSnapHelper.class);
        recyclerView.setOnFlingListener(null);
        pagerSnapHelper.attachToRecyclerView(recyclerView);
    }

    @BindingAdapter({"bind:scopeName", "bind:itemType"})
    public static void setRecyclerViewFilterItemSource(RecyclerView recyclerView,
                                                       String scopeName,
                                                       ISearchFilter.ItemType itemType) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null) {
            Scope scope = Toothpick.openScope(scopeName);
            initFilterItemLayoutManager(scope, recyclerView);
            initFilterItemAdapter(
                    scope,
                    itemType,
                    recyclerView);
        }
//        else {
//            recyclerView.requestLayout();
//        }
    }

    private static void initFilterItemLayoutManager(Scope scope, RecyclerView recyclerView) {
        SearchFilterLayoutManager searchFilterLayoutManager = scope.getInstance(SearchFilterLayoutManager.class);
        recyclerView.setLayoutManager(searchFilterLayoutManager);
    }

    private static void initFilterItemAdapter(Scope scope,
                                              ISearchFilter.ItemType itemType,
                                              RecyclerView recyclerView) {
        BaseFilterAdapter searchFilterAdapter = null;
        switch (itemType) {
            case BY_ROCKET_NAME:{
                searchFilterAdapter = scope.getInstance(SearchFilterAdapterByRocketName.class);
                break;
            }
            case BY_LAUNCH_YEAR:{
                searchFilterAdapter = scope.getInstance(SearchFilterAdapterByLaunchYear.class);
            }
        }
        recyclerView.setAdapter(searchFilterAdapter);
    }

}
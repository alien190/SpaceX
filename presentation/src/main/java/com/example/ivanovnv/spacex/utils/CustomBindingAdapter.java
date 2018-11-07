package com.example.ivanovnv.spacex.utils;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ivanovnv.spacex.detailLaunch.PhotosListAdapter;
import com.squareup.picasso.Picasso;

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

    @BindingAdapter("bind:source")
    public static void setRecyclerViewSource(RecyclerView recyclerView, List<String> stringList) {
        Scope scope = Toothpick.openScope("DetailLaunchFragment");
        LinearLayoutManager linearLayoutManager = scope.getInstance(LinearLayoutManager.class);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        PagerSnapHelper pagerSnapHelper = scope.getInstance(PagerSnapHelper.class);
        recyclerView.setOnFlingListener(null);
        pagerSnapHelper.attachToRecyclerView(recyclerView);
        PhotosListAdapter photosListAdapter = scope.getInstance(PhotosListAdapter.class);
        photosListAdapter.submitList(stringList);
        recyclerView.setAdapter(photosListAdapter);
    }
}

package com.example.ivanovnv.spacex.detailLaunch;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivanovnv.spacex.R;


public class PhotosListAdapter extends ListAdapter<String, PhotosListViewHolder> {

    private static DiffUtil.ItemCallback<String> DIFF_CALLBACK = new DiffUtil.ItemCallback<String>() {
        @Override
        public boolean areItemsTheSame(@NonNull String s, @NonNull String t1) {
            return s.equals(t1);
        }

        @Override
        public boolean areContentsTheSame(@NonNull String s, @NonNull String t1) {
            return s.equals(t1);
        }
    };

    public PhotosListAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public PhotosListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.li_photo, viewGroup, false);
        return new PhotosListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotosListViewHolder photosListViewHolder, int i) {
        photosListViewHolder.bind(getItem(i));
    }
}

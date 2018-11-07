package com.example.ivanovnv.spacex.detailLaunch;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivanovnv.spacex.R;

public class PhotosListAdapter extends ListAdapter<Bitmap, PhotosListViewHolder> {

    private IOnItemClickListener mOnItemClickListener;

    private static DiffUtil.ItemCallback<Bitmap> DIFF_CALLBACK = new DiffUtil.ItemCallback<Bitmap>() {
        @Override
        public boolean areItemsTheSame(@NonNull Bitmap s, @NonNull Bitmap t1) {
            return s.equals(t1);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Bitmap s, @NonNull Bitmap t1) {
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
        return new PhotosListViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotosListViewHolder photosListViewHolder, int i) {
        photosListViewHolder.bind(getItem(i));
    }

    public void setOnItemClickListener(IOnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface IOnItemClickListener {
        void onItemClick(Bitmap image);
    }

}

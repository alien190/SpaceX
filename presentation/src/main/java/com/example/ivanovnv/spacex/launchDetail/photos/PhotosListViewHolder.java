package com.example.ivanovnv.spacex.launchDetail.photos;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.R;

import javax.inject.Inject;

import toothpick.Scope;
import toothpick.Toothpick;

public class PhotosListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Bitmap mBitmap;
    ImageView mImageView;
    @Inject
    protected ILaunchService mLaunchService;
    private PhotosListAdapter.IOnItemClickListener mOnItemClickListener;

    PhotosListViewHolder(@NonNull View itemView, PhotosListAdapter.IOnItemClickListener listener) {
        super(itemView);
        mImageView = itemView.findViewById(R.id.iv_image);
        Scope scope = Toothpick.openScope("Application");
        Toothpick.inject(this, scope);
        mOnItemClickListener = listener;
        itemView.setOnClickListener(this);
    }

    public void bind(Bitmap value) {
        if (mBitmap != null && mBitmap != value) {
            mBitmap.recycle();
        }
        mBitmap = value;
        mImageView.setImageBitmap(mBitmap);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null && mBitmap != null) {
            mOnItemClickListener.onItemClick(mBitmap);
        }
    }
}

package com.mobdev.ivanovnv.spaceanalytix.ui.launchDetail.photos;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.mobdev.ivanovnv.spaceanalytix.R;

public class PhotosListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Bitmap mBitmap;
    private ImageView mImageView;
    private PhotosListAdapter.IOnItemClickListener mOnItemClickListener;

    PhotosListViewHolder(@NonNull View itemView, PhotosListAdapter.IOnItemClickListener listener) {
        super(itemView);
        mImageView = itemView.findViewById(R.id.iv_image);
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

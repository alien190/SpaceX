package com.example.ivanovnv.spacex.detailLaunch;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ivanovnv.spacex.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotosListViewHolder extends RecyclerView.ViewHolder {

    private String mValue;
    @BindView(R.id.tv_text)
    public TextView mTextView;

    public PhotosListViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(String value) {
        mValue = value;
        mTextView.setText(value);
    }
}

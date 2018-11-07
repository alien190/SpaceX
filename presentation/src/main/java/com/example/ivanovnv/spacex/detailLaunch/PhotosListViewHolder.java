package com.example.ivanovnv.spacex.detailLaunch;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.data.utils.DbBitmapUtility;
import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;
import toothpick.Scope;
import toothpick.Toothpick;

public class PhotosListViewHolder extends RecyclerView.ViewHolder {

    private Disposable mDisposable;
    private Bitmap mBitmap;
    @BindView(R.id.iv_image)
    ImageView mImageView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Inject
    protected ILaunchService mLaunchService;

    public PhotosListViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        Scope scope = Toothpick.openScope("Application");
        Toothpick.inject(this, scope);
    }

    public void bind(String value) {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        mDisposable = mLaunchService.loadImage(value)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::onSubscribe)
                .doFinally(this::onFinally)
                .subscribe(this::showImage, Timber::d);
    }

    private void onSubscribe(Disposable disposable) {
        mProgressBar.setVisibility(View.VISIBLE);
        mImageView.setImageResource(R.drawable.ic_rocket_stub);
    }

    private void onFinally() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void showImage(byte[] bytes) {
        Bitmap bitmap = DbBitmapUtility.getImage(bytes);
        if (mBitmap != null && mBitmap != bitmap) {
            mBitmap.recycle();
        }
        mBitmap = bitmap;
        mImageView.setImageBitmap(mBitmap);
    }
}

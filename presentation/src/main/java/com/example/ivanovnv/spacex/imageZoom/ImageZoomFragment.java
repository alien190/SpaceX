package com.example.ivanovnv.spacex.imageZoom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.data.utils.DbBitmapUtility;
import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.R;
import com.example.ivanovnv.spacex.di.imageZoom.ImageZoomModule;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;
import toothpick.Toothpick;

public class ImageZoomFragment extends Fragment {


    private View mView;
    @Inject
    @Named(ImageZoomModule.IMAGE_NAME)
    Bitmap mImage;
    @Inject
    protected ILaunchService mLaunchService;
    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector mGestureDetector;
    private Disposable mDisposable;
    private float mScaleFactor = 1.0f;
    @BindView(R.id.ivZoom)
    ImageView mImageView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    private Bitmap mBitmap;

    public static ImageZoomFragment newInstance() {

        Bundle args = new Bundle();

        ImageZoomFragment fragment = new ImageZoomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fr_image_zoom, container, false);
        ButterKnife.bind(this, mView);
        Toothpick.inject(this, Toothpick.openScope("ImageZoom"));
        mImageView.setImageBitmap(mImage);
        // loadImage();
        mScaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        mGestureDetector = new GestureDetector(getContext(), new ClickListener());

        return mView;
    }

//    private void loadImage() {
//        mDisposable = mLaunchService.loadImage(mImageUrl)
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe(this::onSubscribe)
//                .doFinally(this::onFinally)
//                .subscribe(this::showImage, Timber::d);
//    }


//    private void onSubscribe(Disposable disposable) {
//        mProgressBar.setVisibility(View.VISIBLE);
//        mImageView.setImageResource(R.drawable.ic_rocket_stub);
//    }
//
//    private void onFinally() {
//        mProgressBar.setVisibility(View.GONE);
//    }
//
//    private void showImage(byte[] bytes) {
//        Bitmap bitmap = DbBitmapUtility.getImage(bytes);
//        if (mBitmap != null && mBitmap != bitmap) {
//            mBitmap.recycle();
//        }
//        mBitmap = bitmap;
//        mImageView.setImageBitmap(mBitmap);
//    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onResume() {
        super.onResume();
        mView.setOnClickListener(this::onBackPressed);
        mView.setOnTouchListener((view, motionEvent) -> {
            mGestureDetector.onTouchEvent(motionEvent);
            return mScaleGestureDetector.onTouchEvent(motionEvent);
        });
    }

    private void onBackPressed(View view) {
        Activity activity = getActivity();
        if (activity != null) {
            if (mDisposable != null) {
                mDisposable.dispose();
            }
            activity.onBackPressed();
        }
    }

    @Override
    public void onPause() {
        mView.setOnClickListener(null);
        mView.setOnTouchListener(null);
        super.onPause();
    }


    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(1f, Math.min(mScaleFactor, 10.0f));
            mImageView.setScaleX(mScaleFactor);
            mImageView.setScaleY(mScaleFactor);
            Timber.d("pivot pivotY=%f, scaleY=%f", mImageView.getPivotY(), mImageView.getScaleY());
            return true;
        }
    }

    private class ClickListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            mView.performClick();
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float orgImageHeight = mImageView.getDrawable().getIntrinsicHeight();
            float scaledHeight = orgImageHeight * mImageView.getScaleX();

            float orgImageWidth = mImageView.getDrawable().getIntrinsicWidth();
            float scaledWidth = orgImageWidth * mImageView.getScaleX();

            if (mImageView.getScaleY() > 1) {
                float newPivotY = mImageView.getPivotY() + distanceY / (mImageView.getScaleY() - 1);

                if (newPivotY * (mImageView.getScaleY() - 1) / mImageView.getScaleY() >=
                        (mImageView.getHeight() - orgImageHeight) / 2
                        &&
                        newPivotY <= ((mImageView.getHeight() - orgImageHeight) / 2 *
                                mImageView.getScaleY() + scaledHeight - mImageView.getHeight()) / (mImageView.getScaleY() - 1)
                        ) {
                    mImageView.setPivotY(newPivotY);
                }
            }
            if (mImageView.getScaleX() > 1) {
                float newPivotX = mImageView.getPivotX() + distanceX / (mImageView.getScaleX() - 1);


                if (newPivotX * (mImageView.getScaleX() - 1) / mImageView.getScaleX() >=
                        (mImageView.getWidth() - orgImageWidth) / 2
                        &&
                        newPivotX <= ((mImageView.getWidth() - orgImageWidth) / 2 *
                                mImageView.getScaleX() + scaledWidth - mImageView.getWidth()) / (mImageView.getScaleX() - 1)
                        ) {
                    mImageView.setPivotX(newPivotX);
                }
            }
            return true;
        }
    }
}

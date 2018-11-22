package com.example.ivanovnv.spaceanalytix.di.imageZoom;

import android.graphics.Bitmap;

import toothpick.config.Module;

public class ImageZoomModule extends Module {

    public static final String IMAGE_NAME = "IMAGE";
    private Bitmap mImage;


    public ImageZoomModule(Bitmap image) {
        mImage = image;
        bind(Bitmap.class).withName(IMAGE_NAME).toInstance(mImage);
    }
}

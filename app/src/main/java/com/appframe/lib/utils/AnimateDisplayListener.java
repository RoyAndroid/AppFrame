package com.appframe.lib.utils;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Created by Roy
 * Date: 16/1/4
 */
public class AnimateDisplayListener extends SimpleImageLoadingListener {
    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        if (loadedImage != null) {
            ImageView imageView = (ImageView) view;
            FadeInBitmapDisplayer.animate(imageView, 500);
        }
    }
}

package com.xana.acg.com.loader;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.xana.acg.com.R;
import com.xana.acg.com.widget.RoundImageView;
import com.youth.banner.loader.ImageLoaderInterface;

public class GlideImageLoader implements ImageLoaderInterface {
    @Override
    public void displayImage(Context context, Object path, View imageView) {
        RoundImageView view =  (RoundImageView)imageView;
        Glide.with(context)
                .load(path)
                .placeholder(R.drawable.bg_loading)
                .centerCrop()
                .into(view);
    }
    @Override
    public View createImageView(Context context) {
        return new RoundImageView(context);
    }
}

package com.xana.acg.com.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.xana.acg.com.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class PortraitView extends CircleImageView {
    public PortraitView(Context context) {
        super(context);
    }

    public PortraitView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PortraitView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public void setSrc(String uri){
        Glide.with(getContext())
                .load(uri)
                .centerCrop()
                .placeholder(R.drawable.bg_ireina)
                .into(this);
    }

}

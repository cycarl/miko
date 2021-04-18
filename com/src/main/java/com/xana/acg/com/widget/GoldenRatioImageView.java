package com.xana.acg.com.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class GoldenRatioImageView extends AppCompatImageView {
    public GoldenRatioImageView(Context context) {
        super(context);
    }

    public GoldenRatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GoldenRatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /* 按宽度绘制正方形 */
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

}

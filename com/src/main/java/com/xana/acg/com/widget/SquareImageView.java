package com.xana.acg.com.widget;

import android.content.Context;
import android.util.AttributeSet;

public class SquareImageView extends RoundImageView {
    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /* 按宽度绘制正方形 */
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}

package com.xana.acg.mikomiko.actis;

import android.os.Bundle;
import com.xana.acg.com.app.Activity;
import com.xana.acg.com.widget.DragZoomImageView;
import com.xana.acg.mikomiko.R;

import butterknife.BindView;

public class ImageShowActivity extends Activity {

    @BindView(R.id.iv_img)
    DragZoomImageView mImg;

    private String uri;

    @Override
    protected boolean initArgs(Bundle bundle) {
        uri=bundle.getString("uri");
        return uri!=null;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_show;
    }
    @Override
    protected void initData() {
        super.initData();
        mImg.setFullSrc(uri);
    }
}
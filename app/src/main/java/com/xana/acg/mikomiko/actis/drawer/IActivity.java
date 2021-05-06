package com.xana.acg.mikomiko.actis.drawer;

import android.os.Bundle;
import androidx.annotation.LayoutRes;
import com.xana.acg.com.app.ToolbarActivity;

public class IActivity extends ToolbarActivity {

    private @LayoutRes int res;

    @Override
    protected boolean initArgs(Bundle bundle) {
        res =  Integer.parseInt(bundle.getString("layout", "0"));
        return res!=0;
    }
    @Override
    protected int getLayoutId() {
        return res;
    }
}
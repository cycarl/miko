package com.xana.acg.mikomiko;

import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xana.acg.com.app.TabViewPagerActivity;
import com.xana.acg.com.widget.PortraitView;
import com.xana.acg.com.widget.recycler.Recycler;
import com.xana.acg.com.widget.recycler.RecyclerAdapter;
import com.xana.acg.fac.priavte.Account;
import com.xana.acg.mikomiko.actis.AnimeSearchActivity;
import com.xana.acg.mikomiko.actis.GameSearchActivity;
import com.xana.acg.mikomiko.actis.MusicSearchActivity;
import com.xana.acg.mikomiko.actis.SearchActivity;
import com.xana.acg.mikomiko.actis.drawer.IActivity;
import com.xana.acg.mikomiko.frags.AnimeFragment;
import com.xana.acg.mikomiko.frags.MyFragment;
import com.xana.acg.mikomiko.frags.GameFragment;
import com.xana.acg.mikomiko.frags.ImageFragment;
import com.xana.acg.mikomiko.frags.MusicFragment;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

public class IndexActivity extends TabViewPagerActivity
        implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.drawer)
    DrawerLayout mDrawer;

    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mRefresh;

    @BindView(R.id.recycler)
    Recycler mRecycler;

    @BindView(R.id.pv_avatar)
    PortraitView mAvatar;

    @BindView(R.id.tv_nickname)
    TextView mNickname;


    @Override
    protected void setTitles() {
        mTitles = Arrays.asList("我的", "音乐", "游戏", "动漫", "美图");
    }

    @Override
    protected void setFrags() {
        mFrags = Arrays.asList(
                new MyFragment(),
                new MusicFragment(),
                new GameFragment(),
                new AnimeFragment(),
                new ImageFragment()
        );
    }

    private Adapter mAdapter;

    @Override
    protected void initWidget() {
        super.initWidget();
        mRefresh.setColorSchemeResources(R.color.colorAccent);
        mRefresh.setOnRefreshListener(this);
        mListener = (OnRefreshListenter) mFrags.get(1);
        mRecycler.setNestedScrollingEnabled(false);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter = new Adapter());
        mAdapter.setListener(new RecyclerAdapter.AdapterListener<Option>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Option option) {
                mDrawer.closeDrawer(Gravity.START);
                if(option.msg==R.string.label_drawer_about) {
                    navTo(IActivity.class, "layout", String.valueOf(R.layout.view_about));
                }
            }
            @Override
            public void onItemLongClick(RecyclerAdapter.ViewHolder holder, Option option) {

            }
        });

        verifyPermission(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_index;
    }


    @Override
    protected void initData() {
        super.initData();
        if (Account.getAvatar() != null)
            mAvatar.setSrc(Account.getAvatar());
        if (Account.getNick() != null)
            mNickname.setText(Account.getNick());
        mVp2.setCurrentItem(1, true);
        for (int i = 0; i < imgs.length; i++) {
            mAdapter.add(new Option(imgs[i], msgs[i]));
        }
    }


    private int[] imgs = {
            R.drawable.ic_drawer_show,
            R.drawable.ic_drawer_shopping,
            R.drawable.ic_drawer_near,
            R.drawable.ic_drawer_order,
            R.drawable.ic_drawer_timer,
            R.drawable.ic_drawer_scan,
            R.drawable.ic_drawer_clock,
            R.drawable.ic_drawer_cloud,
            R.drawable.ic_drawer_game,
            R.drawable.ic_drawer_live,
            R.drawable.ic_drawer_about};

    private int[] msgs = {
            R.string.label_drawer_show,
            R.string.label_drawer_shopping,
            R.string.label_drawer_near,
            R.string.label_drawer_order,
            R.string.label_drawer_timer,
            R.string.label_drawer_scan,
            R.string.label_drawer_clock,
            R.string.label_drawer_cloud,
            R.string.label_drawer_game,
            R.string.label_drawer_live,
            R.string.label_drawer_about,
    };


    static class Option {
        int img;
        int msg;

        public Option(int img, int msg) {
            this.img = img;
            this.msg = msg;
        }
    }

    static class Adapter extends RecyclerAdapter<Option> {
        @Override
        protected int getItemViewType(int position, Option option) {
            return R.layout.item_drawer_touch;
        }

        @Override
        protected ViewHolder<Option> onCreateViewHolder(View root, int viewType) {
            return new VH(root);
        }

        static class VH extends ViewHolder<Option> {

            @BindView(R.id.iv_icon)
            ImageView icon;
            @BindView(R.id.tv_title)
            TextView title;

            public VH(View itemView) {
                super(itemView);
            }

            @Override
            protected void onBind(Option option) {
                icon.setImageResource(option.img);
                title.setText(option.msg);
            }
        }
    }


    @SuppressLint("WrongConstant")
    @OnClick({R.id.iv_menu, R.id.iv_search})
    void click(View view) {
        switch (view.getId()) {
            case R.id.iv_menu:
                mDrawer.openDrawer(Gravity.START);
                break;
            case R.id.iv_search:
                switch (mVp2.getCurrentItem()) {
                    case 1:
                        navTo(MusicSearchActivity.class);
                        return;
                    case 2:
                        navTo(GameSearchActivity.class);
                        return;
                    case 3:
                        navTo(AnimeSearchActivity.class);
                        return;
                }
                navTo(SearchActivity.class);
        }
    }

    private OnRefreshListenter mListener;

    public void setListener(OnRefreshListenter mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onRefresh() {
        mListener = (OnRefreshListenter) mFrags.get(mVp2.getCurrentItem());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mListener.onRefresh(IndexActivity.this);
            }
        }, 300);
    }

    public void refreshEnd(String msg) {
        mRefresh.setRefreshing(false);
        if (msg != null)
            showToast(msg);
    }

    public void refreshStart() {
        mRefresh.setRefreshing(true);
    }

    public interface OnRefreshListenter {
        void onRefresh(IndexActivity ctx);
    }



    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public void verifyPermission(Context context){
        int permission = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    IndexActivity.this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
}
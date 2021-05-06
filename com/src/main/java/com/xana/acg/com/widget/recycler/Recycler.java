package com.xana.acg.com.widget.recycler;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Recycler extends RecyclerView {

    private OnMoreLoadListener mListener;
    public Recycler(@NonNull Context context) {
        super(context);
    }

    public Recycler(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Recycler(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setListener(OnMoreLoadListener listener){
        mListener = listener;
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        LayoutManager manager = getLayoutManager();
        if(manager instanceof LinearLayoutManager || manager instanceof GridLayoutManager){
            boolean flag = getAdapter().getItemCount()-1==((LinearLayoutManager)manager).findLastCompletelyVisibleItemPosition();
            if(flag&&mListener!=null){
                mListener.onMoreLoad();
            }
        }
    }
    public interface OnMoreLoadListener{
        void onMoreLoad();
    }
}

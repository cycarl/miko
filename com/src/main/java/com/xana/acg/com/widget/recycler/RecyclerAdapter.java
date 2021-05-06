package com.xana.acg.com.widget.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

import com.xana.acg.com.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressWarnings({"unchecked", "unused"})
public abstract class RecyclerAdapter<Data>
        extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder<Data>>
        implements View.OnClickListener, View.OnLongClickListener, AdapterCallback<Data> {
    protected final List<Data> mDataList;
    private AdapterListener<Data> mListener;

    /**
     * 构造函数模块
     */
    public RecyclerAdapter() {
        this(null);
    }

    public RecyclerAdapter(AdapterListener<Data> listener) {
        this(new ArrayList<Data>(), listener);
    }

    public RecyclerAdapter(List<Data> dataList, AdapterListener<Data> listener) {
        this.mDataList = dataList;
        this.mListener = listener;
    }

    public AdapterListener<Data> getListener() {
        return mListener;
    }

    /**
     * 复写默认的布局类型返回
     *
     * @param position 坐标
     * @return 类型，其实复写后返回的都是XML文件的ID
     */
    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mDataList.get(position));
    }

    /**
     * 得到布局的类型
     *
     * @param position 坐标
     * @param data     当前的数据
     * @return XML文件的ID，用于创建ViewHolder
     */
    @LayoutRes
    protected abstract int getItemViewType(int position, Data data);

    /**
     * 创建一个ViewHolder
     *
     * @param parent   RecyclerView
     * @param viewType 界面的类型,约定为XML布局的Id
     * @return ViewHolder
     */
    @Override
    public ViewHolder<Data> onCreateViewHolder(ViewGroup parent, int viewType) {
        // 得到LayoutInflater用于把XML初始化为View
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // 把XML id为viewType的文件初始化为一个root View
        View root = inflater.inflate(viewType, parent, false);
        // 通过子类必须实现的方法，得到一个ViewHolder
        ViewHolder<Data> holder = onCreateViewHolder(root, viewType);


        // 设置View的Tag为ViewHolder，进行双向绑定
        root.setTag(R.id.tag_recycler_holder, holder);
        // 设置事件点击
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);

        // 进行界面注解绑定
        holder.unbinder = ButterKnife.bind(holder, root);
        // 绑定callback
        holder.callback = this;

        return holder;
    }

    /**
     * 得到一个新的ViewHolder
     *
     * @param root     根布局
     * @param viewType 布局类型，其实就是XML的ID
     * @return ViewHolder
     */
    protected abstract ViewHolder<Data> onCreateViewHolder(View root, int viewType);

    /**
     * 绑定数据到一个Holder上
     *
     * @param holder   ViewHolder
     * @param position 坐标
     */
    @Override
    public void onBindViewHolder(ViewHolder<Data> holder, int position) {
        // 得到需要绑定的数据
        Data data = mDataList.get(position);
        // 触发Holder的绑定方法
        holder.bind(data);
    }

    /**
     * 得到当前集合的数据量
     */
    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * 得到数据集
     * @return
     */
    public List<Data> getItemList(){
        return mDataList;
    }

    /**
     * 插入一条数据并通知插入
     *
     * @param data Data
     */
    public void add(Data data) {
        mDataList.add(data);
        notifyItemInserted(mDataList.size() - 1);
    }

    public void add(Data data, int i){
        mDataList.add(i, data);
        notifyItemInserted(i);
    }

    /**
     * 插入一堆数据，并通知这段集合更新
     *
     * @param dataList Data
     */
    public void add(Data... dataList) {
        if (dataList != null && dataList.length > 0) {
            int startPos = mDataList.size();
            Collections.addAll(mDataList, dataList);
            notifyItemRangeInserted(startPos, dataList.length);
        }
    }

    /**
     * 插入一堆数据，并通知这段集合更新
     *
     * @param dataList Data
     */
    public void add(Collection<Data> dataList) {
        if (dataList != null && dataList.size() > 0) {
            int startPos = mDataList.size();
            mDataList.addAll(dataList);
            notifyItemRangeInserted(startPos, dataList.size());
        }
    }

    /**
     * 删除操作
     */
    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }
    /**
     * 替换
     */

    public void replace(int i, Data data){
        if(i>=mDataList.size()){
            add(data);
            return;
        }
        if(data==mDataList.get(i))
            return;
        mDataList.remove(i);
        mDataList.add(i, data);
        notifyItemChanged(i);
    }

    /**
     * 替换为一个新的集合，其中包括了清空
     *
     * @param dataList 一个新的集合
     */
    public void replace(Collection<Data> dataList) {
        mDataList.clear();
        if (dataList == null || dataList.size() == 0)
            return;
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public void update(Data data, ViewHolder<Data> holder) {
        // 得到当前ViewHolder的坐标
        int pos = holder.getAdapterPosition();
        if (pos >= 0) {
            // 进行数据的移除与更新
            mDataList.remove(pos);
            mDataList.add(pos, data);
            // 通知这个坐标下的数据有更新
            notifyItemChanged(pos);
        }
    }

    @Override
    public void onClick(View v) {
        ViewHolder viewHolder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null) {
            // 得到ViewHolder当前对应的适配器中的坐标
            int pos = viewHolder.getAdapterPosition();
            // 回掉方法
            this.mListener.onItemClick(viewHolder, mDataList.get(pos));
        }
    }

    @Override
    public boolean onLongClick(View v) {
        ViewHolder viewHolder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null) {
            // 得到ViewHolder当前对应的适配器中的坐标
            int pos = viewHolder.getAdapterPosition();
            // 回掉方法
            this.mListener.onItemLongClick(viewHolder, mDataList.get(pos));
            return true;
        }
        return false;
    }




    /**
     * 设置适配器的监听
     *
     * @param adapterListener AdapterListener
     */
    public void setListener(AdapterListener<Data> adapterListener) {
        this.mListener = adapterListener;
    }

    /**
     * 我们的自定义监听器
     *
     * @param <Data> 范型
     */
    public interface AdapterListener<Data> {
        // 当Cell点击的时候触发
        void onItemClick(ViewHolder holder, Data data);

        // 当Cell长按时触发
        void onItemLongClick(ViewHolder holder, Data data);
    }



    /**
     * 自定义的ViewHolder
     *
     * @param <Data> 范型类型
     */
    public static abstract class ViewHolder<Data> extends RecyclerView.ViewHolder {
        private Unbinder unbinder;
        private AdapterCallback<Data> callback;
        protected Data mData;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * 用于绑定数据的触发
         *
         * @param data 绑定的数据
         */
        void bind(Data data) {
            this.mData = data;
            onBind(data);
        }

        /**
         * 当触发绑定数据的时候，的回掉；必须复写
         *
         * @param data 绑定的数据
         */
        protected abstract void onBind(Data data);

        /**
         * Holder自己对自己对应的Data进行更新操作
         *
         * @param data Data数据
         */
        public void updateData(Data data) {
            if (this.callback != null) {
                this.callback.update(data, this);
            }
        }
    }

    /**
     * 对回调接口做一次实现AdapterListener
     *
     * @param <Data>
     */
    public abstract static class AdapterListenerImpl<Data> implements AdapterListener<Data> {

        @Override
        public void onItemClick(ViewHolder holder, Data data) {

        }

        @Override
        public void onItemLongClick(ViewHolder holder, Data data) {

        }
    }


}

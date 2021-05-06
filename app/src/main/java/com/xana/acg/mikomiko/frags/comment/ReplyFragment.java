package com.xana.acg.mikomiko.frags.comment;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.xana.acg.com.widget.PortraitView;
import com.xana.acg.com.widget.recycler.Recycler;
import com.xana.acg.com.widget.recycler.RecyclerAdapter;
import com.xana.acg.fac.model.Comment;
import com.xana.acg.fac.model.CommentRequest;
import com.xana.acg.fac.presenter.IView;
import com.xana.acg.fac.presenter.comment.ReplyPresenter;
import com.xana.acg.fac.priavte.Account;
import com.xana.acg.mikomiko.App;
import com.xana.acg.mikomiko.R;

import java.util.Date;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReplyFragment extends BottomSheetDialogFragment implements IView<Set<Comment>>, RecyclerAdapter.AdapterListener<Comment> {

    @BindView(R.id.recycle)
    Recycler mRecycler;
    private Comment c, cur;

    @BindView(R.id.pv_avatar)
    PortraitView mAvatar;

    @BindView(R.id.tv_nickname)
    TextView nickname;
    @BindView(R.id.tv_time)
    TextView time;
    @BindView(R.id.tv_comment)
    TextView cmt;
    @BindView(R.id.tv_reply_count)
    TextView replyC;

    @BindView(R.id.edit_comment)
    EditText commentText;


    public ReplyFragment(Comment c) {
        this.c = c;
        cur = c;
    }

    private static final String tmp0 = "回复 <font color='#DBA7E6'>@%s</font>: %s";
    private Adapter mAdapter;

    private View view;

    private void initWidgt(View root) {
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter = new Adapter(this));
        mRecycler.setNestedScrollingEnabled(false);
        mAdapter.setListener(this);
    }

    ReplyPresenter presenter;

    private void initData() {
        presenter = new ReplyPresenter(this);
        presenter.getReply(c.getCid());
        mAvatar.setSrc(c.getCpic());
        nickname.setText(c.getCuser());
        time.setText(DateFormat.format("yy-MM-dd HH:mm:ss", c.getCtime()));
        cmt.setText(Html.fromHtml(c.getCtext()));
        commentText.setHint(String.format("回复 @%s :", c.getCuser()));
    }


    @OnClick(R.id.iv_comment_send)
    void click(View view) {
        String ct = commentText.getText().toString();
        commentText.setText("");
        App.hintKb(commentText);
        Date date = new Date();
        String cid = date.getTime()+"";
        Comment comment = new Comment(cid, Account.getUserId(), Account.getNick(), Account.getAvatar(), cur.getCuserId(), cur.getCuser(), date, ct);
        CommentRequest req = new CommentRequest(cid, Account.getUserId(), "20782", ct, date, c.getCid());
        mAdapter.add(comment, 0);
        presenter.sendReply(req);
        cur = c;
        commentText.setHint(String.format("回复 @%s :", c.getCuser()));
    }


    @Override
    public void onLoad(Set<Comment> res) {
        mAdapter.add(res);
        replyC.setText(String.format(getString(R.string.label_reply_count), res.size()));
    }

    @Override
    public void onFail(String msg) {
        App.showToast(msg);
    }

    @Override
    public void onItemClick(RecyclerAdapter.ViewHolder holder, Comment comment) {
        commentText.setHint(String.format("回复 @%s :", comment.getCuser()));
        App.showKb(commentText);
        cur =  comment;
    }

    @Override
    public void onItemLongClick(RecyclerAdapter.ViewHolder holder, Comment comment) {
    }
    class Adapter extends CommentFragment.Adapter {

        public Adapter(Fragment frag) {
            super(frag);
        }

        @Override
        protected RecyclerAdapter.ViewHolder<Comment> onCreateViewHolder(View root, int viewType) {
            return new ViewHolder(root);
        }

        class ViewHolder extends CommentFragment.Adapter.ViewHoler {

            public ViewHolder(View itemView) {
                super(itemView);
            }

            @Override
            protected void onBind(Comment comment) {
                super.onBind(comment);
                if (c.getCuserId().equals(comment.getRuserId()))
                    cmt.setText(Html.fromHtml(comment.getCtext()));
                else
                    cmt.setText(Html.fromHtml(String.format(tmp0, comment.getRuser(), comment.getCtext())));
            }
        }
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        view = View.inflate(getContext(), R.layout.fragment_reply, null);
        ButterKnife.bind(this, view);
        initWidgt(view);
        initData();
        dialog.setContentView(view);
        dialog.getWindow().findViewById(R.id.design_bottom_sheet)
                .setBackgroundResource(android.R.color.transparent);
        BottomSheetBehavior.from((View) view.getParent());
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.77);//屏幕高的77%
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
        return dialog;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null)
            presenter.destory();
    }
}

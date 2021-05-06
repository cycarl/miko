package com.xana.acg.mikomiko.frags.comment;

import android.text.Html;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xana.acg.com.app.PresenterFragment;
import com.xana.acg.com.widget.PortraitView;
import com.xana.acg.com.widget.recycler.Recycler;
import com.xana.acg.com.widget.recycler.RecyclerAdapter;
import com.xana.acg.fac.model.Comment;
import com.xana.acg.fac.model.CommentRequest;
import com.xana.acg.fac.model.api.PageResult;
import com.xana.acg.fac.presenter.comment.CommentContract;
import com.xana.acg.fac.presenter.comment.CommentPresenter;
import com.xana.acg.fac.priavte.Account;
import com.xana.acg.mikomiko.App;
import com.xana.acg.mikomiko.R;

import java.util.Date;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

public class CommentFragment extends PresenterFragment<CommentContract.Presenter>
        implements CommentContract.View, NestedScrollView.OnScrollChangeListener {
    private  String acgId;

    private static final String tmp1 = "<font color='#DBA7E6'>%s</font> 回复 <font color='#DBA7E6'>@%s</font>: %s";
    private static final String tmp0 = "<font color='#DBA7E6'>%s</font>: %s";

    @BindView(R.id.recycler)
    Recycler mRecycler;

    @BindView(R.id.edit_comment)
    EditText commentText;

    @BindView(R.id.viewS)
    ViewStub viewS;

    @BindView(R.id.ns_page)
    NestedScrollView nspage;
    private Adapter mAdapter;


    public CommentFragment() {
    }

    public CommentFragment(String id) {
        acgId = id;
    }

    public void setAcgId(String acgId) {
        this.acgId = acgId;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_comment;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        nspage.setOnScrollChangeListener(this);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter = new Adapter(this));
        mRecycler.setNestedScrollingEnabled(false);
        mAdapter.setListener(new ItemClickListener());
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getComment(acgId, page);
    }

    private int page = 1;
    private boolean more = true;

    @Override
    protected CommentContract.Presenter initPresenter() {
        return new CommentPresenter(this);
    }

    @Override
    public void ok(PageResult<Comment> res) {
        ok(0);
        more = res.hasMore();
        mAdapter.add(res.getContent());
    }

    @Override
    public void empty(boolean ept) {
        if(ept) {
            super.empty(ept);
            viewS.inflate();
        }else viewS.setVisibility(View.GONE);
    }
    private Comment cur;

    private void onMoreLoad() {
        if (!more) {
            showMsg(R.string.tip_no_more);
            return;
        }
        mPresenter.getComment(acgId, ++page);
    }

    @OnClick(R.id.iv_comment_send)
    void click(View view){
        String ct = commentText.getText().toString();
        commentText.setText("");
        App.hintKb(commentText);

        Date date = new Date();
        String cid = date.getTime()+"";
        Comment comment = new Comment(cid, Account.getUserId(), Account.getNick(), Account.getAvatar(), date, ct);
        if(cur==null){
            mAdapter.add(comment, 0);
        }else {
            if(hd.rAdapter==null)
                hd.setPrev();
            if(hd.rAdapter.getItemCount()<3) {
                comment.setRuser(cur.getCuser());
                comment.setRuserId(cur.getCuserId());
                hd.rAdapter.add(comment);
            }
        }

        CommentRequest req = new CommentRequest(cid, Account.getUserId(), acgId, ct, date, cur==null?"0":cur.getCid());
        mPresenter.sendComment(req);
        cur = null;
        commentText.setHint(R.string.label_comment_input);
        empty(false);
    }
    CommentFragment.Adapter.ViewHoler hd;

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
            onMoreLoad();
        }
    };

    public class ItemClickListener implements RecyclerAdapter.AdapterListener<Comment>{

        @Override
        public void onItemClick(RecyclerAdapter.ViewHolder holder, Comment comment) {
            commentText.setHint(String.format("回复 @%s :", comment.getCuser()));
            App.showKb(commentText);
            cur =  comment;
            hd = (CommentFragment.Adapter.ViewHoler)holder;
        }
        @Override
        public void onItemLongClick(RecyclerAdapter.ViewHolder holder, Comment comment) {

        }
    }
    public static class Adapter extends RecyclerAdapter<Comment> {

        private Fragment frag;

        public Adapter(Fragment frag) {
            this.frag = frag;
        }

        @Override
        protected int getItemViewType(int position, Comment comment) {
            return R.layout.item_comment;
        }

        @Override
        protected ViewHolder<Comment> onCreateViewHolder(View root, int viewType) {
            return new ViewHoler(root);
        }

        class ViewHoler extends RecyclerAdapter.ViewHolder<Comment> {

            @BindView(R.id.pv_avatar)
            PortraitView avatar;

            @BindView(R.id.tv_nickname)
            TextView nickname;
            @BindView(R.id.tv_time)
            TextView time;
            @BindView(R.id.tv_comment)
            TextView cmt;
            @BindView(R.id.recycler)
            RecyclerView mRecycler;
            @BindView(R.id.reply)
            LinearLayout reply;

            ReplyAdapter rAdapter;

            public ViewHoler(View itemView) {
                super(itemView);
            }

            private Comment c;

            @Override
            protected void onBind(Comment comment) {
                c = comment;
                avatar.setSrc(comment.getCpic());
                nickname.setText(comment.getCuser());
                time.setText(DateFormat.format("yyyy-MM-dd HH:mm:ss", comment.getCtime()));
                cmt.setText(Html.fromHtml(comment.getCtext()));

                Set<Comment> replyList = comment.getReplyList();
                if (replyList == null || replyList.size() == 0)
                    return;
                setPrev();
            }

            public void setPrev() {
                reply.setVisibility(View.VISIBLE);
                mRecycler.setLayoutManager(new LinearLayoutManager(frag.getContext()));
                rAdapter = new ReplyAdapter(c.getCuserId());
                mRecycler.setAdapter(rAdapter);
                rAdapter.add(c.getReplyList());
            }

            @OnClick(R.id.comment_detail)
            void click(View view) {
                new ReplyFragment(c).show(frag.getChildFragmentManager(), "");
            }
        }
    }


    static class ReplyAdapter extends RecyclerAdapter<Comment> {
        // 被回复人id;
        private String rid;

        public ReplyAdapter(String id) {
            rid = id;
        }

        @Override
        protected int getItemViewType(int position, Comment comment) {
            return R.layout.item_reply_preview;
        }

        @Override
        protected ViewHolder<Comment> onCreateViewHolder(View root, int viewType) {
            return new ReplyViewHoler(root);
        }

        class ReplyViewHoler extends RecyclerAdapter.ViewHolder<Comment> {
            @BindView(R.id.preview)
            TextView prev;

            public ReplyViewHoler(View itemView) {
                super(itemView);
            }

            @Override
            protected void onBind(Comment comment) {
                if (rid.equals(comment.getRuserId()))
                    prev.setText(Html.fromHtml(String.format(tmp0, comment.getCuser(), comment.getCtext())));
                else
                    prev.setText(Html.fromHtml(String.format(tmp1, comment.getCuser(), comment.getRuser(), comment.getCtext())));
            }
        }
    }

}

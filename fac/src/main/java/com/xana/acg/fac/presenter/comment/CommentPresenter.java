package com.xana.acg.fac.presenter.comment;

import com.xana.acg.com.data.DataSource;
import com.xana.acg.com.presenter.BasePresenter;
import com.xana.acg.fac.helper.CommentHelper;
import com.xana.acg.fac.model.Comment;
import com.xana.acg.fac.model.CommentRequest;
import com.xana.acg.fac.model.api.PageResult;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

public class CommentPresenter extends BasePresenter<CommentContract.View>
 implements CommentContract.Presenter, DataSource.Callback<PageResult<Comment>> {
    public CommentPresenter(CommentContract.View view) {
        super(view);
    }

    @Override
    public void success(PageResult<Comment> data) {
        CommentContract.View view = getView();
        if(view==null) return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.ok(data);
            }
        });
    }

    @Override
    public void fail(String msg) {
        getView().showMsg(msg);
    }

    @Override
    public void getComment(String acgId, int page) {
        start();
        CommentHelper.getComment(acgId, page, this);
    }

    public void sendComment(CommentRequest comment){
        CommentHelper.sendComment(comment);
    }
}

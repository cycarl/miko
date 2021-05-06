package com.xana.acg.fac.presenter.comment;

import com.xana.acg.com.data.DataSource;
import com.xana.acg.fac.helper.CommentHelper;
import com.xana.acg.fac.model.Comment;
import com.xana.acg.fac.model.CommentRequest;
import com.xana.acg.fac.model.api.RespModel;
import com.xana.acg.fac.presenter.IView;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.Set;


public class ReplyPresenter implements DataSource.Callback<RespModel<Set<Comment>>> {
    private IView mView;
    public ReplyPresenter(IView view){
        mView = view;
    }
    public void getReply(String commentId){
        CommentHelper.getReply(commentId, this);
    }
    public void sendReply(CommentRequest req){
        CommentHelper.sendComment(req);
    }
    @Override
    public void fail(String msg) {
        mView.onFail(msg);
    }

    @Override
    public void success(RespModel<Set<Comment>> data) {
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                mView.onLoad(data.getResult());
            }
        });
    }

    public void destory(){
        mView = null;
    }
}

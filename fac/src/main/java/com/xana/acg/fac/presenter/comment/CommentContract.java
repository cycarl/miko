package com.xana.acg.fac.presenter.comment;

import com.xana.acg.com.presenter.BaseContract;
import com.xana.acg.fac.model.Comment;
import com.xana.acg.fac.model.CommentRequest;
import com.xana.acg.fac.model.api.PageResult;

public interface CommentContract {
    interface Presenter extends BaseContract.Presenter{
        void getComment(String acgId, int page);
        void sendComment(CommentRequest req);
    }
    interface View extends BaseContract.View<Presenter> {
        void ok(PageResult<Comment> res);
    }
}

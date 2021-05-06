package com.xana.acg.fac.helper;

import com.xana.acg.com.app.Application;
import com.xana.acg.com.data.DataSource;
import com.xana.acg.fac.model.Comment;
import com.xana.acg.fac.model.CommentRequest;
import com.xana.acg.fac.model.api.PageResult;
import com.xana.acg.fac.model.api.RespModel;
import com.xana.acg.fac.net.NetCallBack;
import com.xana.acg.fac.net.Network;
import com.xana.acg.fac.presenter.comment.CommentPresenter;
import com.xana.acg.fac.presenter.comment.ReplyPresenter;

import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.xana.acg.com.Common.SEVER.SELF;

public class CommentHelper {

    public static void getComment(String acgId, int page, DataSource.Callback<PageResult<Comment>> cb) {
        Network.remote(SELF).getComment(acgId, page, 3).enqueue(new NetCallBack<RespModel<PageResult<Comment>>>(){
            @Override
            public void onResponse(Call<RespModel<PageResult<Comment>>> call, Response<RespModel<PageResult<Comment>>> resp) {
                RespModel<PageResult<Comment>> body = resp.body();
                if(resp.isSuccessful()&&body.success()){
                    if(body.getResult().empty()){
                        cb.fail(null);
                        return;
                    }
                    cb.success(body.getResult());
                }else cb.fail("获取失败了!");
            }
        });
    }

    public static void getReply(String commentId, DataSource.Callback<RespModel<Set<Comment>>> cb) {
        Network.remote(SELF).getReply(commentId).enqueue(new NetCallBack<>(cb));
    }

    public static void sendComment(CommentRequest comment) {
        Network.remote(SELF).sendCommnet(comment).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Integer body = response.body();
                Application.showToast("评论成功了!");
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Application.showToast("评论失败了!");
            }
        });
    }
}

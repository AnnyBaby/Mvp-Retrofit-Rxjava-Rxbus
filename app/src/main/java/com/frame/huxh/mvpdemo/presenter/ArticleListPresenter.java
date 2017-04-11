package com.frame.huxh.mvpdemo.presenter;

import android.util.Log;

import com.frame.huxh.mvpdemo.bean.ArticleListBean;
import com.frame.huxh.mvpdemo.interfacepkg.IacticleModel;
import com.frame.huxh.mvpdemo.interfacepkg.RefreshViewInterface;
import com.frame.huxh.mvpdemo.model.ArticleListModel;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by Administrator on 2017/4/10.
 */

public class ArticleListPresenter {
    private RefreshViewInterface mActicleListInterface;
    private IacticleModel mIacticleModel;

    public ArticleListPresenter(RefreshViewInterface acticleInterface , String id) {
        mActicleListInterface = acticleInterface;
        this.mIacticleModel = new ArticleListModel(id);
    }

    public void fetchArticleLists(){
//         mActicleListInterface.showLoading();
        mIacticleModel.requestData(new Subscriber<ArticleListBean>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(ArticleListBean  articleListBean) {
                  mActicleListInterface.showData(articleListBean);
            }

            @Override
            public void onError(Throwable t) {
                Log.e("ArticleListPresenter",t.getMessage());
            }

            @Override
            public void onComplete() {
                mActicleListInterface.hideLoading();
            }
        });

    }

}

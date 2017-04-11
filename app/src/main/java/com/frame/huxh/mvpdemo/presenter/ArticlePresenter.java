package com.frame.huxh.mvpdemo.presenter;

import android.util.Log;

import com.frame.huxh.mvpdemo.bean.ActicleBean;
import com.frame.huxh.mvpdemo.interfacepkg.IacticleModel;
import com.frame.huxh.mvpdemo.interfacepkg.RefreshViewInterface;
import com.frame.huxh.mvpdemo.model.ArticleModel;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by Administrator on 2017/4/6.
 */

public class ArticlePresenter {

     RefreshViewInterface  mActicleInterface;
     IacticleModel mIacticleModel;


    public ArticlePresenter(RefreshViewInterface acticleInterface) {
        mActicleInterface = acticleInterface;
        this.mIacticleModel = new ArticleModel();
    }

    public void fetchArticles(){
        mIacticleModel.requestData(new Subscriber<ActicleBean>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(ActicleBean acticleBean) {
                mActicleInterface.showData(acticleBean);
            }

            @Override
            public void onError(Throwable t) {
                Log.e("ArticlePresenter",t.getMessage());
            }

            @Override
            public void onComplete() {
                mActicleInterface.hideLoading();
            }
        });

    }


}

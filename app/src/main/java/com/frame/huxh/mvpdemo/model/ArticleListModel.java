package com.frame.huxh.mvpdemo.model;

import com.frame.huxh.mvpdemo.api.Api;
import com.frame.huxh.mvpdemo.api.ApiPool;
import com.frame.huxh.mvpdemo.bean.ArticleListBean;
import com.frame.huxh.mvpdemo.interfacepkg.IacticleModel;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.reactivestreams.Subscriber;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/4/10.
 */

public class ArticleListModel implements IacticleModel<ArticleListBean> {
    private String mID ;

    public ArticleListModel(String id) {
        this.mID = id;
    }

    @Override
    public void requestData(Subscriber<ArticleListBean> subscriber) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiPool.sThemeUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        api.getThemeList(mID)
           .subscribeOn(Schedulers.newThread())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe(subscriber);
    }

    @Override
    public void saveDatas(ArticleListBean o) {

    }

    @Override
    public void loadDataFromDB(ArticleListBean o) {

    }
}

package com.frame.huxh.mvpdemo;

import android.util.Log;

import com.frame.huxh.mvpdemo.api.Api;
import com.frame.huxh.mvpdemo.bean.ActicleBean;
import com.frame.huxh.mvpdemo.bean.AllBankListBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/4/6.
 */

public class HttpService {

    public static void requsetArticleList(Subscriber<ActicleBean> subscriber){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://news-at.zhihu.com/api/7/")
                .addConverterFactory(GsonConverterFactory.create())//这个方法是利用gson网络解析json字符串
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())// 添加RxJava2的适配器支持
                .build();
        Api api =  retrofit.create(Api.class);
        api.getResult()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public static void requsetTest(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.cfycp.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())// 添加RxJava2的适配器支持
                .build();
        Api api =  retrofit.create(Api.class);
        api.getText()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<AllBankListBean>>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(List<AllBankListBean> acticleBean) {
                        Log.i("HttpService",acticleBean.get(0).getReturnCode());
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e("HttpService",t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("HttpService","onComplete");
                    }
                });
    }



}

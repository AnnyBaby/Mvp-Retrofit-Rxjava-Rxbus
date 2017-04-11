package com.frame.huxh.mvpdemo.model;

import com.frame.huxh.mvpdemo.api.Api;
import com.frame.huxh.mvpdemo.bean.ActicleBean;
import com.frame.huxh.mvpdemo.interfacepkg.IacticleModel;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.reactivestreams.Subscriber;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/4/6.
 */

public class ArticleModel implements IacticleModel<ActicleBean> {


    //获取文章，也是我们的业务逻辑
    public void requestData(Subscriber<ActicleBean> subscriber){
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

    @Override
    public void saveDatas(ActicleBean o) {
      //存数据库处理
    }

    @Override
    public void loadDataFromDB(ActicleBean o) {
     //从数据库获取数据
    }
}

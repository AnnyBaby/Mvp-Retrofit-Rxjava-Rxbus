package com.frame.huxh.mvpdemo.api;

import com.frame.huxh.mvpdemo.bean.ActicleBean;
import com.frame.huxh.mvpdemo.bean.AllBankListBean;
import com.frame.huxh.mvpdemo.bean.ArticleListBean;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/4/6.
 */

public interface Api {
    @GET("themes")
    Flowable<ActicleBean> getResult(
    );

    @GET("/")
    Call<ActicleBean> defaultBenefits(
            @Query("preorlast") String preorlast,
            @Query("time") String time
    );

    @POST("queryBankList.php")
    Flowable<List<AllBankListBean>> getText();

    @GET("theme/{id}")
    Flowable<ArticleListBean> getThemeList(
            @Path("id") String id
    );
}

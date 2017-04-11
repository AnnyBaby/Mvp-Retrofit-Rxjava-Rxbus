package com.frame.huxh.mvpdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.frame.huxh.mvpdemo.HttpService;
import com.frame.huxh.mvpdemo.R;
import com.frame.huxh.mvpdemo.bean.ActicleBean;
import com.frame.huxh.mvpdemo.bean.ArticleListBean;
import com.frame.huxh.mvpdemo.model.ArticleListModel;
import com.frame.huxh.mvpdemo.model.ArticleModel;
import com.frame.huxh.mvpdemo.utils.ToastUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.test_btn)
    Button mTestBtn;
    @BindView(R.id.test_btn2)
    Button mTestBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.test_btn, R.id.test_btn2,R.id.test_btn3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.test_btn:
                ArticleModel articleModel = new ArticleModel();
                articleModel.requestData(new Subscriber<ActicleBean>(){
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(ActicleBean acticleBean) {
                        ToastUtils.toast(TestActivity.this,acticleBean.getOthers().get(0).getDescription());
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e("TestActivity",t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("TestActivity","onComplete");
                    }
                });
                break;
            case R.id.test_btn2:
                HttpService.requsetTest();
                break;
            case R.id.test_btn3:
                ArticleListModel articleListModel = new ArticleListModel("13");
                articleListModel.requestData(new Subscriber<ArticleListBean>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(ArticleListBean  articleListBean) {
                        ToastUtils.toast(TestActivity.this,articleListBean.getDescription());
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e("ArticleListPresenter",t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("TestActivity","onComplete");
                    }
                });
                break;
        }
    }
}

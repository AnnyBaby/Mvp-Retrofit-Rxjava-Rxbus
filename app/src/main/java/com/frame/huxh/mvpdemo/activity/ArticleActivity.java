package com.frame.huxh.mvpdemo.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.frame.huxh.mvpdemo.R;
import com.frame.huxh.mvpdemo.bean.ActicleBean;
import com.frame.huxh.mvpdemo.interfacepkg.RefreshViewInterface;
import com.frame.huxh.mvpdemo.presenter.ArticlePresenter;
import com.frame.huxh.mvpdemo.pull.BaseViewHolder;
import com.frame.huxh.mvpdemo.pull.PullRecycler;
import com.frame.huxh.mvpdemo.rxbus.RxBus;

import java.util.ArrayList;

public class ArticleActivity extends BaseListActivity<ActicleBean.OthersBean> implements RefreshViewInterface<ActicleBean>{
    private int page = 1;
    private ActicleBean mActicleBean;
    private ArticlePresenter mArticlePresenter = new ArticlePresenter(this);


    @Override
    protected void setUpTitle(int titleResId) {
        super.setUpTitle(R.string.zhihu_daily);
    }

    @Override
    protected void setUpData() {
        super.setUpData();
        recycler.setRefreshing();
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_layout_item, parent, false);
        return new SampleViewHolder(view);
    }

    @Override
    public void onRefresh(int action) {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }

        if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
            page = 1;
        }

        mArticlePresenter.fetchArticles();
        //请求数据

    }

    @Override
    public void showData(ActicleBean acticleBean) {
      this.mActicleBean = acticleBean;
        mDataList.clear();
        mDataList.addAll(acticleBean.getOthers());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
   recycler.setRefreshing();
    }

    @Override
    public void hideLoading() {
   recycler.onRefreshCompleted();
    }



    class SampleViewHolder extends BaseViewHolder {

        ImageView mSampleListItemImg;
        TextView mSampleListItemLabel;
        TextView mTvDaily;

        public SampleViewHolder(View itemView) {
            super(itemView);
            mSampleListItemLabel = (TextView) itemView.findViewById(R.id.tv_context);
            mSampleListItemImg = (ImageView) itemView.findViewById(R.id.img_article);
            mTvDaily = (TextView) itemView.findViewById(R.id.tv_dailytype);
        }

        @Override
        public void onBindViewHolder(int position) {
            ActicleBean.OthersBean bean = mDataList.get(position);
            mTvDaily.setText(bean.getName());
            mSampleListItemLabel.setText(bean.getDescription());
            Glide.with(mSampleListItemImg.getContext())
                    .load(bean.getThumbnail())
                    .centerCrop()
                    .placeholder(R.color.app_primary_color)
                    .crossFade()
                    .into(mSampleListItemImg);
        }

        @Override
        public void onItemClick(View view, int position) {
//            ToastUtils.toast(ArticleActivity.this,"position"+position);
            ActicleBean.OthersBean bean = mDataList.get(position);
            RxBus.get().post(bean);
            startActivity(new Intent(ArticleActivity.this,ActicleListActivity.class));
        }

    }

}

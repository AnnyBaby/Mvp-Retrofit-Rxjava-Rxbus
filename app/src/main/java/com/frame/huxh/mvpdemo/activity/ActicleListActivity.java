package com.frame.huxh.mvpdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.frame.huxh.mvpdemo.R;
import com.frame.huxh.mvpdemo.bean.ActicleBean;
import com.frame.huxh.mvpdemo.bean.ArticleListBean;
import com.frame.huxh.mvpdemo.interfacepkg.RefreshViewInterface;
import com.frame.huxh.mvpdemo.presenter.ArticleListPresenter;
import com.frame.huxh.mvpdemo.pull.BaseListAdapter;
import com.frame.huxh.mvpdemo.pull.BaseViewHolder;
import com.frame.huxh.mvpdemo.pull.DividerItemDecoration;
import com.frame.huxh.mvpdemo.pull.PullRecycler;
import com.frame.huxh.mvpdemo.pull.layoutmanager.ILayoutManager;
import com.frame.huxh.mvpdemo.pull.layoutmanager.MyLinearLayoutManager;
import com.frame.huxh.mvpdemo.rxbus.RxBus;
import com.frame.huxh.mvpdemo.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static com.frame.huxh.mvpdemo.R.id.toolbar;
import static com.frame.huxh.mvpdemo.activity.BaseActivity.MODE_BACK;
import static com.frame.huxh.mvpdemo.activity.BaseActivity.MODE_NONE;


public class ActicleListActivity extends AppCompatActivity implements RefreshViewInterface<ArticleListBean>, PullRecycler.OnRecyclerRefreshListener {
    @BindView(R.id.image_top)
    ImageView mImageTop;
    @BindView(R.id.editor)
    TextView mEditor;
    @BindView(R.id.acticle_pullRecycler)
    PullRecycler mRecycler;
    @BindView(R.id.toolbar_title)
    TextView mToolbar_title;
    @BindView(toolbar)
    Toolbar mToolbar;
    private String mTitle;
    private int page = 1;
    private String mID;
    private ArticleListPresenter mArticleListPresenter;
    private List<ArticleListBean.StoriesBean> mDataList;
    private BaseListAdapter adapter;
    private static ActicleBean.OthersBean mOthersBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acticle_list);
        ButterKnife.bind(this);

        if (mOthersBean == null) {
            operateBus();
        }else {
            initView();
        }
    }


    private void setUpToolbar( String title,int mode) {
        if (mode != MODE_NONE) {
            mToolbar.setTitle("");
            mToolbar_title.setText(title);
            if (mode == MODE_BACK) {
                mToolbar.setNavigationIcon(R.mipmap.ic_toolbar_back);
            }
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        }
    }


    private void   initView(){
        setUpToolbar(mOthersBean.getName(),0);
        setUpData();
        Glide.with(mImageTop.getContext())
                .load(mOthersBean.getThumbnail())
                .centerCrop()
                .placeholder(R.color.app_primary_color)
                .crossFade()
                .into(mImageTop);
        mTitle = mOthersBean.getName();
        mID = String.valueOf(mOthersBean.getId());
        mArticleListPresenter = new ArticleListPresenter(ActicleListActivity.this, mID);
        mRecycler.setRefreshing();
    }

    protected void setUpData() {
        adapter = new ListAdapter();
        mRecycler.setOnRefreshListener(this);
        mRecycler.setLayoutManager(getLayoutManager());
        mRecycler.addItemDecoration(getItemDecoration());
        mRecycler.setAdapter(adapter);
    }

    protected ILayoutManager getLayoutManager() {
        return new MyLinearLayoutManager(getApplicationContext());
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new DividerItemDecoration(getApplicationContext(), R.drawable.list_divider);
    }


    /**
     * RxBus
     */
    private ActicleBean.OthersBean operateBus() {
        RxBus.get().toObservable()
                .map(new Function<Object, ActicleBean.OthersBean>() {
                    @Override
                    public ActicleBean.OthersBean apply(@NonNull Object o) throws Exception {
                        return (ActicleBean.OthersBean) o;
                    }

                })
                .subscribe(new Consumer<ActicleBean.OthersBean>() {


                    @Override
                    public void accept(@NonNull ActicleBean.OthersBean othersBean) throws Exception {
                        if (othersBean != null) {
//                            ToastUtils.toast(ActicleListActivity.this, "othersBean" + othersBean.getDescription());
                            mOthersBean = othersBean;
                        }
                    }

                });

        return mOthersBean;
    }


    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_layout_item, parent, false);
        return new SampleViewHolder(view);
    }

//
    public void onRefresh(int action) {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
            page = 1;
        }
        mArticleListPresenter.fetchArticleLists();
    }


    @Override
    public void showData(ArticleListBean articleListBean) {
        if (articleListBean.getEditors().size() > 0) {
            mEditor.setText("主编  " + articleListBean.getEditors().get(0).getName());
        }
        mDataList.clear();
        mDataList.addAll(articleListBean.getStories());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
        mRecycler.setRefreshing();
    }

    @Override
    public void hideLoading() {
        mRecycler.onRefreshCompleted();
    }

    class SampleViewHolder extends BaseViewHolder {

        ImageView mSampleListItemImg;
        TextView mSampleListItemLabel;
        TextView mTvDaily;
        TextView mTvContext;

        public SampleViewHolder(View itemView) {
            super(itemView);
            mSampleListItemLabel = (TextView) itemView.findViewById(R.id.tv_list_context);
            mSampleListItemImg = (ImageView) itemView.findViewById(R.id.img_article);
            mTvDaily = (TextView) itemView.findViewById(R.id.tv_dailytype);
            mTvContext = (TextView) itemView.findViewById(R.id.tv_context);
        }

        @Override
        public void onBindViewHolder(int position) {
            ArticleListBean.StoriesBean bean = mDataList.get(position);
            mSampleListItemImg.setVisibility(View.VISIBLE);
            mTvDaily.setVisibility(View.GONE);
            mTvContext.setVisibility(View.GONE);
            mSampleListItemLabel.setVisibility(View.VISIBLE);
            mSampleListItemLabel.setText(bean.getTitle());
            if (CollectionUtils.isEmpty(bean.getImages())) {
                mSampleListItemImg.setVisibility(View.GONE);
            } else {
                Glide.with(mSampleListItemImg.getContext())
                        .load(bean.getImages().get(0))
                        .centerCrop()
                        .placeholder(R.color.app_primary_color)
                        .crossFade()
                        .into(mSampleListItemImg);
            }

        }

        @Override
        public void onItemClick(View view, int position) {
//            ToastUtils.toast(ArticleActivity.this,"position"+position);
            String  id = String.valueOf(mDataList.get(position).getId());
            Intent intent = new Intent(ActicleListActivity.this,ArticleDetailActivity.class);
            intent.putExtra("ID",id);
            startActivity(intent);
        }

    }


    public class ListAdapter extends BaseListAdapter {

        @Override
        protected BaseViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
            return getViewHolder(parent, viewType);
        }

        @Override
        protected int getDataCount() {
            return mDataList != null ? mDataList.size() : 0;
        }

        @Override
        protected int getDataViewType(int position) {
            return getItemType(position);
        }

        @Override
        public boolean isSectionHeader(int position) {
            return ActicleListActivity.this.isSectionHeader(position);
        }
    }

    protected boolean isSectionHeader(int position) {
        return false;
    }

    protected int getItemType(int position) {
        return 0;
    }

}
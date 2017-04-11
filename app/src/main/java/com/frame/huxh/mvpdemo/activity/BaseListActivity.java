package com.frame.huxh.mvpdemo.activity;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.frame.huxh.mvpdemo.R;
import com.frame.huxh.mvpdemo.pull.BaseListAdapter;
import com.frame.huxh.mvpdemo.pull.BaseViewHolder;
import com.frame.huxh.mvpdemo.pull.DividerItemDecoration;
import com.frame.huxh.mvpdemo.pull.PullRecycler;
import com.frame.huxh.mvpdemo.pull.layoutmanager.ILayoutManager;
import com.frame.huxh.mvpdemo.pull.layoutmanager.MyLinearLayoutManager;

import java.util.List;

public abstract class BaseListActivity<T> extends BaseActivity implements PullRecycler.OnRecyclerRefreshListener {
    protected BaseListAdapter adapter;
    protected List<T> mDataList;
    protected PullRecycler recycler;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_base_list, -1);
    }

    @Override
    protected void setUpView() {
        recycler = (PullRecycler) findViewById(R.id.pullRecycler);
    }

    @Override
    protected void setUpData() {
        setUpAdapter();
        recycler.setOnRefreshListener(this);
        recycler.setLayoutManager(getLayoutManager());
        recycler.addItemDecoration(getItemDecoration());
        recycler.setAdapter(adapter);
    }

    protected void setUpAdapter() {
        adapter = new ListAdapter();
    }

    protected ILayoutManager getLayoutManager() {
        return new MyLinearLayoutManager(getApplicationContext());
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new DividerItemDecoration(getApplicationContext(), R.drawable.list_divider);
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
            return BaseListActivity.this.isSectionHeader(position);
        }
    }

    protected boolean isSectionHeader(int position) {
        return false;
    }
    protected int getItemType(int position) {
        return 0;
    }
    protected abstract BaseViewHolder getViewHolder(ViewGroup parent, int viewType);

}

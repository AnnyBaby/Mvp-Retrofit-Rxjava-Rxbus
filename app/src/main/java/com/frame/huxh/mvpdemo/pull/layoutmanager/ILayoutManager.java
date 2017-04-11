package com.frame.huxh.mvpdemo.pull.layoutmanager;

import android.support.v7.widget.RecyclerView;

import com.frame.huxh.mvpdemo.pull.BaseListAdapter;


/**
 * Created by Stay on 5/3/16.
 * Powered by www.stay4it.com
 */
public interface ILayoutManager {
    RecyclerView.LayoutManager getLayoutManager();
    int findLastVisiblePosition();
    void setUpAdapter(BaseListAdapter adapter);
}

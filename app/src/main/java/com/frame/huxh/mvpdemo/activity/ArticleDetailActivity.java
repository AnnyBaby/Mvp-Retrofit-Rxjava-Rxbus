package com.frame.huxh.mvpdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.frame.huxh.mvpdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.frame.huxh.mvpdemo.R.id.toolbar;
import static com.frame.huxh.mvpdemo.activity.BaseActivity.MODE_BACK;
import static com.frame.huxh.mvpdemo.activity.BaseActivity.MODE_NONE;

public class ArticleDetailActivity extends AppCompatActivity {

    @BindView(toolbar)
    Toolbar mToolbar;
    @BindView(R.id.article_webview)
    WebView mArticleWebview;

    @BindView(R.id.toolbar_title)
    TextView mToolbar_title;
    private String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        ButterKnife.bind(this);
        setUpToolbar(0);
        mId = getIntent().getStringExtra("ID");
        mArticleWebview.loadUrl("http://daily.zhihu.com/story/"+mId);
    }


    private void setUpToolbar( int mode) {
        if (mode != MODE_NONE) {
            mToolbar.setTitle("");
            mToolbar_title.setText("日报详情");
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

}

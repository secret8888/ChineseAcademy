package com.stroke.academy.activity.article;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.stroke.academy.R;
import com.stroke.academy.activity.base.BaseActivity;
import com.stroke.academy.adapter.ArticleListAdapter;
import com.stroke.academy.adapter.MeetingAdapter;
import com.stroke.academy.annotation.ViewId;
import com.stroke.academy.common.constant.Consts;
import com.stroke.academy.common.constant.IntentConsts;
import com.stroke.academy.common.http.AcademyHandler;
import com.stroke.academy.common.http.HttpManager;
import com.stroke.academy.common.util.IntentManager;
import com.stroke.academy.common.util.Utils;
import com.stroke.academy.model.ArticleData;
import com.stroke.academy.model.HandleInfo;
import com.stroke.academy.model.MeetingData;
import com.stroke.academy.view.refresh.RefreshListView;
import com.youdao.yjson.YJson;

/**
 * Created by emilyu on 6/11/15.
 */
public class ArticleListActivity extends BaseActivity implements
        View.OnClickListener, AdapterView.OnItemClickListener{
    @ViewId(R.id.tv_title)
    private TextView titleView;

    @ViewId(R.id.tv_back)
    private TextView backView;

    @ViewId(R.id.list_content)
    private RefreshListView contentView;

    private ArticleListAdapter mAdapter;

    private ArticleData mData;

    private int currentPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_list;
    }

    @Override
    protected void readIntent() {
    }

    @Override
    protected void initControls(Bundle savedInstanceState) {
        titleView.setText(R.string.doc_browse);
        contentView.setRefreshTime(Utils.getTime());
        contentView.setPullLoadEnable(false);
        onShowLoadingDialog();
        getArticleList();
    }

    @Override
    protected void setListeners() {
        backView.setOnClickListener(this);
        contentView.setOnItemClickListener(this);
        contentView.setListViewRefreshListener(new RefreshListView.ListViewRefreshListener() {

            @Override
            public void onRefresh() {
                mData = null;
                currentPage = 1;
                getArticleList();
            }

            @Override
            public void onLoadMore() {
                if (mData != null && currentPage < Integer.parseInt(mData.getTotalPage())) {
                    currentPage++;
                    getArticleList();
                } else {
                    onMessageLoad();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        IntentManager.startArticleInfoActivity(this, mData.getArticles().get(position).getId());
    }

    private void getArticleList() {
        HttpManager.getArticleList(new AcademyHandler(this) {
            @Override
            protected void handleSuccessMessage(Object object) {
                onMessageLoad();
                onDismissLoadingDialog();
                HandleInfo handleInfo = (HandleInfo) object;
                ArticleData articleData = YJson.getObj(handleInfo.getData(), ArticleData.class);
                if (mData == null) {
                    mData = articleData;
                    mAdapter = new ArticleListAdapter(ArticleListActivity.this, mData.getArticles());
                    contentView.setAdapter(mAdapter);
                    if (Integer.parseInt(mData.getTotalPage()) > 1) {
                        contentView.setPullLoadEnable(true);
                    }
                } else {
                    mData.addArticles(articleData.getArticles());
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void handleError(int errorCode, String errorMsg) {
                onMessageLoad();
                onDismissLoadingDialog();
            }
        }, currentPage, Consts.DEFAULT_PAGE_SIZE);
    }


    private void onMessageLoad() {
        contentView.stopRefresh();
        contentView.stopLoadMore();
        contentView.setRefreshTime(Utils.getTime());
    }
}

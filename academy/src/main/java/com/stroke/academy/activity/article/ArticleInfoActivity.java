package com.stroke.academy.activity.article;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.stroke.academy.R;
import com.stroke.academy.activity.base.BaseActivity;
import com.stroke.academy.adapter.ArticleListAdapter;
import com.stroke.academy.annotation.ViewId;
import com.stroke.academy.common.constant.Consts;
import com.stroke.academy.common.constant.IntentConsts;
import com.stroke.academy.common.http.AcademyHandler;
import com.stroke.academy.common.http.HttpManager;
import com.stroke.academy.common.util.AES256;
import com.stroke.academy.common.util.Logcat;
import com.stroke.academy.common.util.Utils;
import com.stroke.academy.model.ArticleData;
import com.stroke.academy.model.HandleInfo;
import com.stroke.academy.view.refresh.RefreshListView;
import com.youdao.yjson.YJson;

/**
 * Created by emilyu on 6/11/15.
 */
public class ArticleInfoActivity extends BaseActivity implements
        View.OnClickListener, AdapterView.OnItemClickListener{
    @ViewId(R.id.tv_title)
    private TextView titleView;

    @ViewId(R.id.tv_back)
    private TextView backView;

    @ViewId(R.id.tv_subject)
    private TextView subjectView;

    @ViewId(R.id.tv_author)
    private TextView authorView;

    @ViewId(R.id.tv_translator)
    private TextView translatorView;

    @ViewId(R.id.tv_time)
    private TextView timeView;

    @ViewId(R.id.tv_source)
    private TextView sourceView;

    @ViewId(R.id.tv_intro)
    private TextView introView;

    @ViewId(R.id.tv_detail)
    private TextView detailView;

    private ArticleData articleData;

    private String id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_info;
    }

    @Override
    protected void readIntent() {
        id = getIntent().getStringExtra(IntentConsts.ID_KEY);
    }

    @Override
    protected void initControls(Bundle savedInstanceState) {
        titleView.setText(R.string.detail);
        onShowLoadingDialog();
        getArticleList();
    }

    @Override
    protected void setListeners() {
        backView.setOnClickListener(this);
        detailView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                onBackPressed();
                break;
            case R.id.tv_detail:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    private void getArticleList() {
        HttpManager.getArticleInfo(new AcademyHandler(this) {
            @Override
            protected void handleSuccessMessage(Object object) {
                onDismissLoadingDialog();
                HandleInfo handleInfo = (HandleInfo) object;
                articleData = YJson.getObj(handleInfo.getData(), ArticleData.class);
                if (articleData != null) {
                    subjectView.setText(String.format(getString(R.string.subject), articleData.getArticle().getName()));
                    authorView.setText(String.format(getString(R.string.author), articleData.getArticle().getWriter()));
                    translatorView.setText(String.format(getString(R.string.translator), articleData.getArticle().getTranslator()));
                    timeView.setText(String.format(getString(R.string.time), articleData.getArticle().getTime()));
                    sourceView.setText(String.format(getString(R.string.source), articleData.getArticle().getSource()));
                    introView.setText(articleData.getArticle().getDescription());
                }
            }

            @Override
            protected void handleError(int errorCode, String errorMsg) {
                onDismissLoadingDialog();


                articleData = YJson.getObj(errorMsg, ArticleData.class);
                if (articleData != null) {
                    subjectView.setText(String.format(getString(R.string.subject), articleData.getArticle().getName()));
                    authorView.setText(String.format(getString(R.string.author), articleData.getArticle().getWriter()));
                    translatorView.setText(String.format(getString(R.string.translator), articleData.getArticle().getTranslator()));
                    timeView.setText(String.format(getString(R.string.time), articleData.getArticle().getTime()));
                    sourceView.setText(String.format(getString(R.string.source), articleData.getArticle().getSource()));
                    introView.setText("    " + articleData.getArticle().getDescription());
                }
            }
        }, id);
    }
}

package com.stroke.academy.activity.article;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
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
import com.stroke.academy.common.util.DownloadManager;
import com.stroke.academy.common.util.Logcat;
import com.stroke.academy.common.util.Toaster;
import com.stroke.academy.common.util.Utils;
import com.stroke.academy.model.ArticleData;
import com.stroke.academy.model.ArticleItem;
import com.stroke.academy.model.HandleInfo;
import com.stroke.academy.view.refresh.RefreshListView;
import com.youdao.yjson.YJson;

import java.io.File;

/**
 * Created by emilyu on 6/11/15.
 */
public class ArticleInfoActivity extends BaseActivity implements View.OnClickListener {
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

    @ViewId(R.id.pbar_download)
    private ProgressBar downloadBar;

    private ArticleItem articleItem;

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
        getArticleInfo();
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
                getInfoDetail();
                break;
        }
    }

    private void getArticleInfo() {
        HttpManager.getArticleInfo(new AcademyHandler(this) {
            @Override
            protected void handleSuccessMessage(Object object) {
                onDismissLoadingDialog();
                HandleInfo handleInfo = (HandleInfo) object;
                articleItem = YJson.getObj(handleInfo.getData(), ArticleItem.class);
                if (articleItem != null) {
                    subjectView.setText(String.format(getString(R.string.subject), articleItem.getName()));
                    authorView.setText(String.format(getString(R.string.author), articleItem.getWriter()));
                    translatorView.setText(String.format(getString(R.string.translator), TextUtils.isEmpty(articleItem.getTranslator())?"":articleItem.getTranslator()));
                    timeView.setText(String.format(getString(R.string.time), articleItem.getTime()));
                    sourceView.setText(String.format(getString(R.string.source), articleItem.getSource()));
                    introView.setText(articleItem.getDescription());
                }
            }

            @Override
            protected void handleError(int errorCode, String errorMsg) {
                onDismissLoadingDialog();
            }
        }, id);
    }

    private void getInfoDetail() {
        String url = articleItem.getArticleURL();
        String id = url.substring(url.lastIndexOf("/"));
        final File file = new File(Consts.ARTICLE_FILE_PATH + id + Consts.AUDIO_DOWNLOAD_SUF);

        if (file != null && file.exists()) {
            startActivity(getPdfFileIntent(file));
        } else {
            DownloadManager downloadManager = DownloadManager.getInstance();
            downloadManager.initAppDownloadInfo(Consts.ARTICLE_FILE_PATH, id, url);
            downloadManager.setDownloadListener(new DownloadManager.DownloadListener() {
                @Override
                public void onDownloadSuccess() {
                    downloadBar.setVisibility(View.INVISIBLE);
                    startActivity(getPdfFileIntent(file));
                }

                @Override
                public void onDownloadFail() {
                    downloadBar.setVisibility(View.INVISIBLE);
                    Toaster.show(ArticleInfoActivity.this, R.string.download_fail);
                }

                @Override
                public void onDownloadCancel() {
                    downloadBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onDownloadProgress(int position) {
                    downloadBar.setVisibility(View.VISIBLE);
                    downloadBar.setProgress(position);
                }
            });
            downloadManager.startDownload(false);
        }
    }

    public static Intent getPdfFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }
}

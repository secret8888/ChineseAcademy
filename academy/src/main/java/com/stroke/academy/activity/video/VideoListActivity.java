package com.stroke.academy.activity.video;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.stroke.academy.R;
import com.stroke.academy.activity.base.BaseActivity;
import com.stroke.academy.adapter.VideoAdapter;
import com.stroke.academy.annotation.ViewId;
import com.stroke.academy.common.constant.Consts;
import com.stroke.academy.common.constant.IntentConsts;
import com.stroke.academy.common.http.AcademyHandler;
import com.stroke.academy.common.http.HttpManager;
import com.stroke.academy.common.util.IntentManager;
import com.stroke.academy.common.util.Utils;
import com.stroke.academy.model.HandleInfo;
import com.stroke.academy.model.VideoData;
import com.stroke.academy.view.refresh.RefreshListView;
import com.youdao.yjson.YJson;

/**
 * Created by emilyu on 6/11/15.
 */
public class VideoListActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener{
    @ViewId(R.id.tv_title)
    private TextView titleView;

    @ViewId(R.id.tv_back)
    private TextView backView;

    @ViewId(R.id.list_content)
    private RefreshListView contentView;

    private VideoAdapter mAdapter;

    private VideoData mData;

    private String meetingId;

    private String dayId;

    private int currentPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_list;
    }

    @Override
    protected void readIntent() {
        meetingId = getIntent().getStringExtra(IntentConsts.MEETING_ID_KEY);
        dayId = getIntent().getStringExtra(IntentConsts.DAY_ID_KEY);
    }

    @Override
    protected void initControls(Bundle savedInstanceState) {
        titleView.setText(R.string.video_list);
        contentView.setRefreshTime(Utils.getTime());
        contentView.setPullLoadEnable(false);
        onShowLoadingDialog();
        getVideoList();
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
                getVideoList();
            }

            @Override
            public void onLoadMore() {
                if (mData != null && currentPage < Integer.parseInt(mData.getTotalPage())) {
                    currentPage++;
                    getVideoList();
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
        IntentManager.startVideoActivity(this, mData.getVideos().get(position - 1));
    }

    private void getVideoList() {
        HttpManager.getVideoList(new AcademyHandler(this) {
            @Override
            protected void handleSuccessMessage(Object object) {
                onMessageLoad();
                onDismissLoadingDialog();
                HandleInfo handleInfo = (HandleInfo) object;
                VideoData videoData = YJson.getObj(handleInfo.getData(), VideoData.class);
                if (mData == null) {
                    mData = videoData;
                    mAdapter = new VideoAdapter(VideoListActivity.this, mData.getVideos());
                    contentView.setAdapter(mAdapter);
                    if (Integer.parseInt(mData.getTotalPage()) > 1) {
                        contentView.setPullLoadEnable(true);
                    }
                } else {
                    mData.addVideos(videoData.getVideos());
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            protected void handleError(int errorCode, String errorMsg) {
                onMessageLoad();
                onDismissLoadingDialog();
            }
        }, dayId, meetingId, currentPage, Consts.DEFAULT_PAGE_SIZE);
    }


    private void onMessageLoad() {
        contentView.stopRefresh();
        contentView.stopLoadMore();
        contentView.setRefreshTime(Utils.getTime());
    }
}

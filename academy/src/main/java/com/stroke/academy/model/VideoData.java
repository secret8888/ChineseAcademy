package com.stroke.academy.model;

import java.util.ArrayList;

/**
 * Created by emilyu on 6/11/15.
 */
public class VideoData {

    private String totalPage;

    private ArrayList<VideoItem> videos;

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public ArrayList<VideoItem> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<VideoItem> videos) {
        this.videos = videos;
    }

    public void addVideo(VideoItem item) {
        if(videos == null) {
            videos = new ArrayList<>();
        } else {
            videos.add(item);
        }
    }

    public void addVideos(ArrayList<VideoItem> items) {
        if(videos == null) {
            videos = items;
        } else {
            videos.addAll(items);
        }
    }
}

package com.stroke.academy.model;

import java.util.ArrayList;

/**
 * Created by emilyu on 6/11/15.
 */
public class ArticleData {

    private String totalPage;

    private ArrayList<ArticleItem> articles;

    private ArticleItem article;

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public ArrayList<ArticleItem> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<ArticleItem> articles) {
        this.articles = articles;
    }

    public ArticleItem getArticle() {
        return article;
    }

    public void setArticle(ArticleItem article) {
        this.article = article;
    }

    public void addArticle(ArticleItem item) {
        if(articles == null) {
            articles = new ArrayList<>();
        } else {
            articles.add(item);
        }
    }

    public void addArticles(ArrayList<ArticleItem> items) {
        if(articles == null) {
            articles = items;
        } else {
            articles.addAll(items);
        }
    }
}

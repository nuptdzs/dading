package com.nupt.dzs.wordsreader.impl;

import com.nupt.dzs.wordsreader.common.IBaseActivity;
import com.nupt.dzs.wordsreader.model.ArticleModel;
import com.nupt.dzs.wordsreader.model.WordModel;

import java.util.List;

/**
 * Created by Administrator on 2016/4/23.
 *
 * @version 1.0
 * @copyright by ${COMPANY}
 */
public interface IArticleListView extends IBaseActivity {
    void viewDetail(ArticleModel articleModel);
    void loadWords(List<WordModel> wordModels);
    void loadArticles(List<ArticleModel> articleModels);
}

package com.nupt.dzs.wordsreader.impl;

import android.text.SpannableString;

import com.nupt.dzs.wordsreader.common.IBaseActivity;
import com.nupt.dzs.wordsreader.model.WordModel;
import com.nupt.dzs.wordsreader.ui.view.WordSpan;

/**
 * Created by Administrator on 2016/4/25.
 *
 * @version 1.0
 * @copyright by ${COMPANY}
 */
public interface IArticleView extends IBaseActivity{
    void showWordInfo(WordModel wordModel);
    String getArticle();
    void addWordSpan(WordSpan wordSpan);
    void clearWordSpans();
    void loadArticleBySpaned(SpannableString spannableString);
}

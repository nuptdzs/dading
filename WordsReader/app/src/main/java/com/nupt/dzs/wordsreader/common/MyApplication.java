package com.nupt.dzs.wordsreader.common;

import android.app.Application;

import com.nupt.dzs.wordsreader.model.ArticleModel;
import com.nupt.dzs.wordsreader.model.WordModel;

import java.util.List;

/**
 *
 * @version 1.0
 * @copyright by ${COMPANY}
 */
public class MyApplication extends Application {

    public static List<WordModel> wordModels;
    public static List<ArticleModel> articleModels;
}

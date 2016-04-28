package com.nupt.dzs.wordsreader.common;

import android.app.Application;
import android.content.Intent;

import com.nupt.dzs.wordsreader.model.ArticleModel;
import com.nupt.dzs.wordsreader.model.WordModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @version 1.0
 * @copyright by ${COMPANY}
 */
public class MyApplication extends Application {
    public static List<WordModel> wordModels;
    public static Map<String,Integer> wordParams;
    public static List<ArticleModel> articleModels;
    public static String access_token = "xc9aZh38dpfAkkTCkvDMwCgKf1miBP";
}

package com.nupt.dzs.wordsreader.utils;

import android.content.Context;
import android.util.Log;

import com.nupt.dzs.wordsreader.model.ArticleModel;
import com.nupt.dzs.wordsreader.model.WordModel;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class TextUtils {
    /**
     * 从文件读取文字信息
     * @param filename
     * @return
     */
    public static String getTxt(Context context, String filename) {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open("articles.txt");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("error","文件名错误");
        }
        byte[] bytes = new byte[0];
        try {
            bytes  = new byte[inputStream.available()];
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("error","读取错误");
        }
        try {
            inputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("error","读取错误");
        }
        String TEXT = null;
        try {
            TEXT = new String(bytes,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e("error","字节不匹配");
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return TEXT;
    }

    /**
     *
     * @param wordstxt
     * @return 单词列表
     */
    public static List<WordModel> getWords(String wordstxt) {
        List<WordModel> wordModels = new ArrayList<>();
        //按行读取总的单词信息
        String[] words = wordstxt.split("\n");
        Log.d("words",words.toString());
        //根据tab格式读取单词
        for (String wordtxt : words) {
            String[] text = wordtxt.split("\t");
            WordModel wordModel = new WordModel();
            wordModel.setWord(text[0]);
            //若单词不存在级别 则不读取
            if (text[1].matches("[0-9]")) {
                wordModel.setLevel(Integer.parseInt(text[1]));
                wordModels.add(wordModel);
            }
        }
        return wordModels;
    }

    /**
     *
     * @param articletxt
     * @return
     */
    public static List<ArticleModel> getArticles(String articletxt){
        List<ArticleModel> articleModels = new ArrayList<>();
        if(articletxt == null){
            return articleModels;
        }
        String[] articletxts = articletxt.split("Lesson [d{1-9}]");
        for(int i=1;i<articletxts.length;i++){
            String[] attr = articletxts[i].split("New words and expressions");
            String eng_title = null;
            String ch_title = null;
            String explain = null;
            StringBuilder content = new StringBuilder();
            if(attr.length>1){
                String articleandtitle = attr[0];
                String wordsandexplain = attr[1];
                String[] at = articleandtitle.split("\n");
                Log.d("at",at.toString());
                String[] we = wordsandexplain.split("参考译文");
                Log.d("we",we.toString());
                String newwords = we[0];//新单词和词组
                explain = we[1]; //译文
                eng_title = at[1].trim();
                ch_title = at[2].trim();
                for(int n=9;n<at.length;n++){
                    content.append(at[n]);
                }
            }
            ArticleModel articleModel = new ArticleModel();
            articleModel.setId(i);
            articleModel.setEngTitle(eng_title);
            articleModel.setEngContent(content.toString());
            articleModel.setChContent(explain);
            articleModel.setChTitle(ch_title);
            articleModels.add(articleModel);
        }
        return articleModels;
    }
}

package com.nupt.dzs.wordsreader.utils;

import android.content.Context;
import android.util.Log;

import com.nupt.dzs.wordsreader.model.ArticleModel;
import com.nupt.dzs.wordsreader.model.WordModel;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextUtils {
    /**
     * 从文件读取文字信息
     * @param filename
     * @return
     */
    public static String getTxt(Context context, String filename) {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(filename);
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
        //根据tab格式读取单词
        for (String wordtxt : words) {
            String[] text = wordtxt.split("\t");
            WordModel wordModel = new WordModel();
            wordModel.setWord(text[0]);
            //若单词不存在级别 则不读取
            if (text.length>1&&text[1].matches("[0-9]")) {
                wordModel.setLevel(Integer.parseInt(text[1]));
            }
            wordModels.add(wordModel);
        }
        return wordModels;
    }

    public static Map<String,Integer> getWordsbyMap(String wordstxt){
        Map<String,Integer> wordModels = new HashMap<>();
        //按行读取总的单词信息
        String[] words = wordstxt.split("\n");
        //根据tab格式读取单词
        for (String wordtxt : words) {
            String[] text = wordtxt.split("\t");

            //若单词不存在级别 则不读取
            if (text.length>1&&text[1].matches("[0-9]")) {
                wordModels.put(text[0], Integer.valueOf(text[1]));
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
            int wordsCount = 0;
            StringBuilder content = new StringBuilder();
            List<WordModel> wordModels = new ArrayList<>();
            if(attr.length>1){
                String articleandtitle = attr[0];
                String wordsandexplain = attr[1];
                String[] at = articleandtitle.split("\n");
                Log.d("at", Arrays.toString(at));
                String[] we = wordsandexplain.split("参考译文");
                Log.d("we", Arrays.toString(we));
                String newwords = we[0];//新单词和词组
                wordModels = parseNewWord(newwords);
                explain = we[1]; //译文
                eng_title = at[1].trim();//第二行是英文标题
                ch_title = at[2].trim();//第三行是中文标题
                //去除听力问答 从第九行开始是文章正文
                for(int n=9;n<at.length;n++){
                    content.append(at[n]).append("\n");
                    wordsCount+=at[n].split(" ").length;
                }
            }
            ArticleModel articleModel = new ArticleModel();
            articleModel.setId(i);
            articleModel.setEngTitle(eng_title);
            articleModel.setEngContent(content.toString());
            articleModel.setChContent(explain);
            articleModel.setChTitle(ch_title);
            articleModel.setNewWords(wordModels);
            articleModel.setWordCount(wordsCount);
            articleModels.add(articleModel);
        }
        return articleModels;
    }

    public static List<WordModel> parseNewWord(String newWords){
        List<WordModel> wordModels = new ArrayList<>();
        String[] wordtxtarr = newWords.split("\n");
        //新词格式为没两行一个单词或词组 形似
        // alienate
        //v.  便疏远
        for(int i=1;i<wordtxtarr.length;i+=2){
            if(!wordtxtarr[i].trim().equals("")){
                WordModel wordModel = new WordModel();
                wordModel.setWord(wordtxtarr[i].trim());
                wordModel.setExplain(wordtxtarr[i+1].trim());
                /*String[] ex = wordtxtarr[i+1].split(".");
                if(ex.length>1){
                    wordModel.setExplain(ex[1].trim());
                    wordModel.setType(ex[0].trim());
                }else {
                    wordModel.setExplain(wordtxtarr[i+1].trim());
                }*/
                wordModels.add(wordModel);
            }
        }
        return wordModels;
    }
}

package com.nupt.dzs.wordsreader.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2016/4/23.
 *
 * @version 1.0
 * @copyright by ${COMPANY}
 */
public class TextUtils {
    /**
     * 从文件读取文字信息
     * @param filename
     * @return
     */
    public static String getTxt(String filename) {
        File file = new File(filename);
        String encoding = "UTF-8";
        StringBuilder articles = new StringBuilder();
        try {
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(filename), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    Log.d("line:",lineTxt);
                    articles.append(lineTxt);
                }
                read.close();
                return articles.toString();
            }else {
                Log.e("readfile:","获取文件信息失败");
                return null;
            }
        } catch (Exception e) {
            Log.e("readfile:","获取文件信息失败");
            e.printStackTrace();
            return null;
        }
    }
}

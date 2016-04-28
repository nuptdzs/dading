package com.nupt.dzs.wordsreader.utils;

import android.graphics.Paint;
import android.text.*;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * 借鉴他人源码 稍加修改 使排版更为合理 增加段前缩进和段落之间的间距为一行
 */
public class TextJustification {
    public static String justify(TextView textView, float contentWidth) {
        String text=textView.getText().toString();
        String tempText;
        String resultText = "";
        Paint paint=textView.getPaint();

        ArrayList<String> paraList = new ArrayList<String>();
        paraList = paraBreak(text,textView);
        for(int i = 0; i<paraList.size(); i++) {
            ArrayList<String> lineList=lineBreak(paraList.get(i),paint,contentWidth);
            tempText = android.text.TextUtils.join(" ", lineList);
            resultText += tempText+ "\n\n";

        }
        return resultText;
    }
    //分开每个段落
    public static ArrayList<String> paraBreak(String text, TextView textview) {
        ArrayList<String> paraList = new ArrayList<String>();
        String[] paraArray = text.split("\\n+");
        for(String para:paraArray) {
            paraList.add("\t"+para.trim());
        }
        return paraList;
    }

    //分开每一行，使每一行填入最多的单词数
    private static ArrayList<String> lineBreak(String text, Paint paint, float contentWidth){
        String [] wordArray=text.split("\\s");
        ArrayList<String> lineList = new ArrayList<String>();
        String myText="";

        for(String word:wordArray){
            if(paint.measureText(myText+" "+word)<=contentWidth)
                myText=myText+" "+word;
            else{
                int totalSpacesToInsert=(int)((contentWidth-paint.measureText(myText))/paint.measureText(" "));
                lineList.add(justifyLine(myText,totalSpacesToInsert));
                myText=word;
            }
        }
        lineList.add(myText.trim());
        return lineList;
    }
    //已填入最多单词数的一行，插入对应的空格数直到该行满
    private static String justifyLine(String text,int totalSpacesToInsert){
        String[] wordArray=text.split("\\s");
        String toAppend=" ";

        while((totalSpacesToInsert)>=(wordArray.length-1)){
            toAppend=toAppend+" ";
            totalSpacesToInsert=totalSpacesToInsert-(wordArray.length-1);
        }

        String justifiedText="";
        for(int n = 0;n<wordArray.length;n++){
            String word = wordArray[n];
            if(n<totalSpacesToInsert)
                justifiedText=justifiedText+word+" "+toAppend;
            else
                justifiedText=justifiedText+word+toAppend;
        }

        return justifiedText;
    }


}

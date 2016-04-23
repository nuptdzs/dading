package com.nupt.dzs.wordsreader.model;

public class WordModel {
    /**
     * 单词
     */
    private String word = "";
    /**
     * 等级
     */
    private int level;

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }


    /**
     * 解释
     */

    private String explain ="";

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}

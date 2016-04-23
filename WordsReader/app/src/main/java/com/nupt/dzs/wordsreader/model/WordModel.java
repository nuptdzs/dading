package com.nupt.dzs.wordsreader.model;

public class WordModel {
    /**
     * 单词
     */
    private String word;
    /**
     * 等级
     */
    private String level;

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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * 解释
     */

    private String explain;
}

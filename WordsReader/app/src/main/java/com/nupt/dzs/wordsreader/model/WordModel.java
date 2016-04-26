package com.nupt.dzs.wordsreader.model;

import java.io.Serializable;


public class WordModel implements Serializable{
    /**
     * 单词
     */
    private String word = "";
    /**
     * 等级
     */
    private int level;
    /**
     * 解释
     */
    private String explain ="";
    /**
     * 词性
     */
    private String type;

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "WordModel{" +
                "word='" + word + '\'' +
                ", level=" + level +
                ", explain='" + explain + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

package com.nupt.dzs.wordsreader.model;

import java.io.Serializable;
import java.util.List;


public class ArticleModel implements Serializable{
    /**
     * 文章id
     */
    private int id;
    /**
     * 英文标题
     */
    private String engTitle = "";
    /**
     * 中文标题
     */
    private String chTitle = "";
    /**
     * 生词和短语
     */
    private List<WordModel> newWords;
    /**
     * 英文内容
     */
    private String engContent = "";
    /**
     * 中文内容
     */
    private String chContent ="";
    private int WordCount;

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ArticleModel{" +
                "id=" + id +
                ", engTitle='" + engTitle + '\'' +
                ", chTitle='" + chTitle + '\'' +
                ", newWords=" + newWords +
                ", engContent='" + engContent + '\'' +
                ", chContent='" + chContent + '\'' +
                ", WordCount=" + WordCount +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEngTitle() {
        return engTitle;
    }

    public void setEngTitle(String engTitle) {
        this.engTitle = engTitle;
    }

    public String getChTitle() {
        return chTitle;
    }

    public void setChTitle(String chTitle) {
        this.chTitle = chTitle;
    }

    public List<WordModel> getNewWords() {
        return newWords;
    }

    public void setNewWords(List<WordModel> newWords) {
        this.newWords = newWords;
    }

    public String getEngContent() {
        return engContent;
    }

    public void setEngContent(String engContent) {
        this.engContent = engContent;
    }

    public String getChContent() {
        return chContent;
    }

    public void setChContent(String chContent) {
        this.chContent = chContent;
    }

    public int getWordCount() {
        return WordCount;
    }

    public void setWordCount(int wordCount) {
        WordCount = wordCount;
    }
}

package com.nupt.dzs.wordsreader.presenter;


import android.util.Log;

import com.nupt.dzs.wordsreader.R;
import com.nupt.dzs.wordsreader.common.MyApplication;
import com.nupt.dzs.wordsreader.impl.IArticleListView;
import com.nupt.dzs.wordsreader.model.ArticleModel;
import com.nupt.dzs.wordsreader.model.WordModel;
import com.nupt.dzs.wordsreader.utils.TextUtils;


import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ArticleListPresenter {

    IArticleListView mView;
    public ArticleListPresenter(IArticleListView iArticleListView){
        mView = iArticleListView;
    }

    public void loadFile(){
        if(MyApplication.articleModels==null){
            mView.showLoading(mView.getContext().getString(R.string.hint_loading_articlelist));
            Observable.just(TextUtils.getTxt(mView.getContext(),"articles.txt"))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new Func1<String, List<ArticleModel>>() {
                        @Override
                        public List<ArticleModel> call(String s) {
                            return TextUtils.getArticles(s);
                        }
                    })
                    .subscribe(new Action1<List<ArticleModel>>() {
                        @Override
                        public void call(List<ArticleModel> articleModels) {
                            MyApplication.articleModels = articleModels;
                            mView.loadArticles(articleModels);
                            mView.hideLoading();
                        }
                    });
        }else {
            mView.loadArticles(MyApplication.articleModels);
        }

    }

    public void loadWords(){
        if(MyApplication.wordModels==null){
            Observable.just(TextUtils.getTxt(mView.getContext(),"nce4_words"))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new Func1<String, List<WordModel>>() {
                        @Override
                        public List<WordModel> call(String s) {
                            return TextUtils.getWords(s);
                        }
                    })
                    .subscribe(new Action1<List<WordModel>>() {
                        @Override
                        public void call(List<WordModel> wordModels) {
                            MyApplication.wordModels = wordModels;
                            Log.e("size",wordModels.size()+"");
                        }
                    });
        }

    }

    public void loadWordsMap(){
        if(MyApplication.wordModels==null){
            Observable.just(TextUtils.getTxt(mView.getContext(),"nce4_words"))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new Func1<String, Map<String,Integer>>() {
                        @Override
                        public Map<String,Integer> call(String s) {
                            return TextUtils.getWordsbyMap(s);
                        }
                    })
                    .subscribe(new Action1<Map<String,Integer>>() {
                        @Override
                        public void call(Map<String,Integer> wordModels) {
                            MyApplication.wordParams = wordModels;
                            Log.e("size",wordModels.size()+"");
                        }
                    });
        }
    }
}

package com.nupt.dzs.wordsreader.presenter;

import android.util.Log;

import com.nupt.dzs.wordsreader.impl.IArticleListView;
import com.nupt.dzs.wordsreader.model.ArticleModel;
import com.nupt.dzs.wordsreader.utils.TextUtils;


import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/4/23.
 *
 * @version 1.0
 * @copyright by ${COMPANY}
 */
public class ArticleListPresenter {

    IArticleListView mView;
    public ArticleListPresenter(IArticleListView iArticleListView){
        mView = iArticleListView;
    }

    public void loadFile(){
        mView.showLoading("正在加载文章列表");
        Observable.just(TextUtils.getTxt(mView.getContext(),"articles.txt"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String, List<ArticleModel>>() {
                    @Override
                    public List<ArticleModel> call(String s) {
                        Log.d("articletxt:",s);
                        return TextUtils.getArticles(s);
                    }
                })
                .subscribe(new Action1<List<ArticleModel>>() {
                    @Override
                    public void call(List<ArticleModel> articleModels) {
                        mView.hideLoading();
                        mView.loadArticles(articleModels);
                    }
                });
    }
}

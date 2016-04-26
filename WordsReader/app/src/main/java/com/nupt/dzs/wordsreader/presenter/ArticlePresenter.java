package com.nupt.dzs.wordsreader.presenter;

import android.media.MediaPlayer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;

import com.nupt.dzs.wordsreader.R;
import com.nupt.dzs.wordsreader.common.MyApplication;
import com.nupt.dzs.wordsreader.http.RetrofitUtils;
import com.nupt.dzs.wordsreader.http.ShanBayApi;
import com.nupt.dzs.wordsreader.http.request.IRequest;
import com.nupt.dzs.wordsreader.http.response.Response;
import com.nupt.dzs.wordsreader.http.response.WordResponse;
import com.nupt.dzs.wordsreader.impl.IArticleView;
import com.nupt.dzs.wordsreader.model.WordModel;
import com.nupt.dzs.wordsreader.ui.view.WordSpan;

import java.io.IOException;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/4/25.
 *
 * @version 1.0
 * @copyright by ${COMPANY}
 */
public class ArticlePresenter {
    IArticleView iArticleView;

    public ArticlePresenter(IArticleView view) {
        iArticleView = view;
    }

    /**
     * 添加对应等级的单词标注
     *
     * @param i
     */
    public void markWordBy(int i) {
        String s = iArticleView.getArticle();
        Observable.just(spliSpanableString(i, s))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<SpannableString>() {
                    @Override
                    public void call(SpannableString spannableString) {
                        iArticleView.loadArticleBySpaned(spannableString);
                    }
                });
    }

    /**
     * 文章内容和词汇级别选择标注文章
     * 此操作应置于逻辑处理层
     *
     * @param i
     * @param s
     * @return
     */
    public SpannableString spliSpanableString(int i, String s) {
        iArticleView.clearWordSpans();
        SpannableString spannableString = new SpannableString(s);
        for (WordModel wordModel : MyApplication.wordModels) {
            if (wordModel.getLevel() == i) {
                String[] arr = s.split(wordModel.getWord());
                if (arr.length > 1) {
                    int index = 0;
                    for (int n = 0; n < arr.length - 1; n++) {
                        int start = index + arr[n].length();
                        int end = index + arr[n].length() + wordModel.getWord().length();
                        if (s.charAt(start - 1) == ' ') {
                            WordSpan wordSpan = new WordSpan(iArticleView, wordModel);
                            iArticleView.addWordSpan(wordSpan);
                            spannableString.setSpan(wordSpan
                                    , start
                                    , end
                                    , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            index += arr[n].length() + wordModel.getWord().length();
                        }
                    }
                }
            }
        }
        return spannableString;
    }

    public void searchWord(String word) {
        RetrofitUtils.getBuilder(IRequest.baseUrl).build().create(ShanBayApi.class)
                .searchWord(word, MyApplication.access_token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<WordResponse>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Response<WordResponse> wordResponseResponse) {
                        prepareAudio(wordResponseResponse.getData());
                        iArticleView.showSearchResult(wordResponseResponse.getData());
                    }
                });
    }

    public void prepareAudio(final WordResponse wordResponse) {
        new Thread(){
            @Override
            public void run() {
                try {
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(wordResponse.getAudio());
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        }.start();

    }

    MediaPlayer mediaPlayer = new MediaPlayer();

    public void displayAudio() {
        Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                mediaPlayer.start();
                subscriber.onNext(true);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {

                    }
                });
    }
}

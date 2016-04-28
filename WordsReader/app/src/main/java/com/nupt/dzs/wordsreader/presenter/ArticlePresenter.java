package com.nupt.dzs.wordsreader.presenter;

import android.media.MediaPlayer;
import android.text.Spannable;
import android.text.SpannableString;


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
import java.util.StringTokenizer;

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
     * @param
     */
    public void markWordBy() {
        iArticleView.showLoading("正在加载");
        final String s = iArticleView.getArticle();
        Observable.just(spliSpanableString(s))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<SpannableString>() {
                    @Override
                    public void call(SpannableString spannableString) {
                        iArticleView.loadArticleBySpaned(spannableString);
                        iArticleView.hideLoading();
                    }
                });
    }

    /**
     * 文章内容和词汇级别选择标注文章
     * 此操作应置于逻辑处理层
     *
     * @param
     * @param s
     * @return
     */
    public SpannableString spliSpanableString(String s) {
        iArticleView.clearWordSpans();
        SpannableString spannableString = new SpannableString(s);
        StringTokenizer stringTokenizer = new StringTokenizer(s," \t\n\r\f,.!?-(){}[]:;'",true);
        int index = 0;
        while (stringTokenizer.hasMoreElements()){
            String token = stringTokenizer.nextToken();
            int start = index;
            index+=token.length();
            if(isWord(token)){
               WordModel wordmodel =  formWordList(token);
                WordSpan wordSpan = new WordSpan(iArticleView,wordmodel);
                spannableString.setSpan(wordSpan,start,index,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                iArticleView.addWordSpan(wordSpan);
            }
        }
        /*for (WordModel wordModel : MyApplication.wordModels) {
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
        }*/
        return spannableString;
    }

    /**
     * 采用map对四级词汇等级对应表进行优化，加快匹配速度
     * @param word
     * @return
     */
    public WordModel formWordList(String word){
        /*for(WordModel wordmodel:MyApplication.wordModels){
            if(word.matches(wordmodel.getWord())){
                return wordmodel;
            }
        }*/
        Integer level = MyApplication.wordParams.get(word);
        if(level == null){
            level = 0;
        }
        WordModel wrodmodel = new WordModel();
        wrodmodel.setWord(word);
        wrodmodel.setLevel(level);
        return wrodmodel;
    }
    public boolean isWord(String token){
        return token.matches("[a-zA-Z]*");
    }
    public void searchWord(final String word) {
        iArticleView.showLoading("正在查询...");
        RetrofitUtils.getBuilder(IRequest.baseUrl).build().create(ShanBayApi.class)
                .searchWord(word, MyApplication.access_token)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Response<WordResponse>, WordResponse>() {

                    @Override
                    public WordResponse call(Response<WordResponse> wordResponseResponse) {
                        if(wordResponseResponse.getStatus_code()==0){
                            if(wordResponseResponse.getData()!=null){
                                prepareAudio(wordResponseResponse.getData());
                                return wordResponseResponse.getData();
                            }
                            else return null;
                        }else return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WordResponse>() {
                    @Override
                    public void onCompleted() {
                        iArticleView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(WordResponse wordResponseResponse) {
                        if(wordResponseResponse!=null){
                            iArticleView.showSearchResult(wordResponseResponse);
                        }else {
                            iArticleView.showToast("暂无该单词相关信息");
                        }
                    }
                });
    }

    public void prepareAudio(final WordResponse wordResponse) {
        try {
            if(wordResponse.getAudio()!=null){
                mediaPlayer.reset();
                mediaPlayer.setDataSource(wordResponse.getAudio());
                mediaPlayer.prepare();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        /*new Thread(){
            @Override
            public void run() {

                super.run();
            }
        }.start();*/

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
        }).subscribeOn(Schedulers.io()).subscribe();
    }
}

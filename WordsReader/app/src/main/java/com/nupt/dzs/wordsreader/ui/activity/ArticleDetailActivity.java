package com.nupt.dzs.wordsreader.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.channguyen.rsv.RangeSliderView;
import com.nupt.dzs.wordsreader.R;
import com.nupt.dzs.wordsreader.common.BaseActivity;
import com.nupt.dzs.wordsreader.common.MyApplication;
import com.nupt.dzs.wordsreader.impl.IArticleView;
import com.nupt.dzs.wordsreader.model.ArticleModel;
import com.nupt.dzs.wordsreader.model.WordModel;
import com.nupt.dzs.wordsreader.presenter.ArticlePresenter;
import com.nupt.dzs.wordsreader.ui.view.WordSpan;
import com.nupt.dzs.wordsreader.utils.TextJustification;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArticleDetailActivity extends BaseActivity implements IArticleView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.tvContent)
    TextView tvContent;
    @Bind(R.id.seekBar)
    SeekBar seekBar;
    /**
     * 文章内容
     */
    private ArticleModel articleModel;
    /**
     * 用来对文章内容操作并展示结果的展示器
     */
    private ArticlePresenter articlePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        ButterKnife.bind(this);
        articlePresenter = new ArticlePresenter(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /**
         * 必须在view可见时才能获取到控件宽高属性进行排版操作
         */
        tvContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(!hasLoaded){
                    hasLoaded = true;
                    loadArticles();
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                articlePresenter.markWordBy(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    /**
     * 判断文章是否已经加载成功，避免反复回调
     */
    boolean hasLoaded = false;
    /**
     * 四级词汇等级，默认为1
     */
    private int level = 1;

    /**
     * 加载文章
     */
    private void loadArticles() {
        Intent intent = getIntent();
        articleModel = (ArticleModel) intent.getSerializableExtra("data");
        getSupportActionBar().setTitle("Lesson "+articleModel.getId());
        tvTitle.setText(articleModel.getEngTitle());
        tvContent.setText(articleModel.getEngContent());
        float width = tvContent.getWidth();
        String s= TextJustification.justify(tvContent,width);
        tvContent.setText(s);

        articlePresenter.markWordBy(level);
    }

    private List<WordSpan> wordSpanList = new ArrayList<>();


    @Override
    public void showWordInfo(WordModel wordModel) {
        resetWordSpan();
    }

    /**
     * 获得当前经过排版的文章内容
     * @return
     */
    @Override
    public String getArticle() {
        return tvContent.getText().toString();
    }

    /**
     * 添加span及标注，用以后期处理
     * @param wordSpan
     */
    @Override
    public void addWordSpan(WordSpan wordSpan) {
        wordSpanList.add(wordSpan);
    }

    @Override
    public void clearWordSpans() {
        wordSpanList.clear();
    }

    /**
     * 加载经过标注处理的文章内容
     * @param spannableString
     */
    @Override
    public void loadArticleBySpaned(SpannableString spannableString) {
        tvContent.setHighlightColor(getResources().getColor(R.color.colorPrimary));
        tvContent.setText(spannableString);
        tvContent.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * 重置标注
     */
    public void resetWordSpan(){
        for(WordSpan wordSpan:wordSpanList){
            wordSpan.checked = false;
        }
        tvContent.invalidate();
    }
}

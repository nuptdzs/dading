package com.nupt.dzs.wordsreader.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nupt.dzs.wordsreader.R;
import com.nupt.dzs.wordsreader.common.BaseActivity;
import com.nupt.dzs.wordsreader.http.response.WordResponse;
import com.nupt.dzs.wordsreader.impl.IArticleView;
import com.nupt.dzs.wordsreader.model.ArticleModel;
import com.nupt.dzs.wordsreader.model.WordModel;
import com.nupt.dzs.wordsreader.presenter.ArticlePresenter;
import com.nupt.dzs.wordsreader.ui.view.WordSpan;
import com.nupt.dzs.wordsreader.utils.TextJustification;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArticleDetailActivity extends BaseActivity implements IArticleView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.tvContent)
    TextView tvContent;
    @Bind(R.id.moveBar)
    FrameLayout moveBar;
    @Bind(R.id.tvPronunciation)
    TextView tvPronunciation;
    @Bind(R.id.tvDefin)
    TextView tvDefin;
    @Bind(R.id.tvExample)
    TextView tvExample;
    @Bind(R.id.llWordInfo)
    LinearLayout llWordInfo;
    @Bind(R.id.tvWordContent)
    TextView tvWordContent;
    @Bind(R.id.imgPlay)
    ImageView imgPlay;
    /**
     * 文章内容
     */
    private ArticleModel articleModel;
    /**
     * 用来对文章内容操作并展示结果的展示器
     */
    private ArticlePresenter articlePresenter;

    @SuppressLint("PrivateResource")
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
                if (!hasLoaded) {
                    hasLoaded = true;
                    loadArticles();
                }
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
        getSupportActionBar().setTitle("Lesson " + articleModel.getId());
        tvTitle.setText(articleModel.getEngTitle());
        tvContent.setText(articleModel.getEngContent());
        float width = tvContent.getWidth();
        String s = TextJustification.justify(tvContent, width);
        tvContent.setText(s);
        articlePresenter.markWordBy();
    }

    private List<WordSpan> wordSpanList = new ArrayList<>();


    @Override
    public void showWordInfo(WordModel wordModel) {
        resetWordSpan();
        articlePresenter.searchWord(wordModel.getWord());
    }

    @Override
    public void showSearchResult(WordResponse wordResponse) {
        llWordInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetWordSpan();
                Animation animation =AnimationUtils.loadAnimation(getContext(),R.anim.out_to_bottom);
                llWordInfo.setAnimation(animation);
                llWordInfo.setVisibility(View.GONE);
            }
        });
        if(llWordInfo.getVisibility()==View.GONE){
            llWordInfo.setVisibility(View.VISIBLE);
            llWordInfo.setAnimation(AnimationUtils.loadAnimation(this,R.anim.int_from_bottom));
        }
        tvWordContent.setText(wordResponse.getContent());
        tvPronunciation.setText(String.format("/%s/",wordResponse.getPronunciation()));
        tvDefin.setText(wordResponse.getDefinition().trim());
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                articlePresenter.displayAudio();
            }
        });
    }

    /**
     * 获得当前经过排版的文章内容
     *
     * @return
     */
    @Override
    public String getArticle() {
        return tvContent.getText().toString();
    }

    /**
     * 添加span及标注，用以后期处理
     *
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
     *
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
    public void resetWordSpan() {
        for (WordSpan wordSpan : wordSpanList){
            wordSpan.checked = false;
        }
        tvContent.invalidate();
    }

    public void markWordByLevel(int level){
        for(WordSpan wordSpan : wordSpanList){
            wordSpan.highLightLevel = level;
        }
        tvContent.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.article_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.nav_manage:
                showLevelChoosView();
                Log.e("hah","hahah");
                break;
        }
        return false;
    }

    private PopupWindow popupWindow;
    private View popView;
    public void showLevelChoosView(){
        if(popupWindow == null){
            popupWindow = new PopupWindow(this);
            popView = LayoutInflater.from(getContext()).inflate(R.layout.menu_choose_highlightlevel,null);
            RadioGroup rg = (RadioGroup) popView.findViewById(R.id.radioButton);
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId){
                        case R.id.lv1:
                            markWordByLevel(1);
                            break;
                        case R.id.lv2:
                            markWordByLevel(2);
                            break;
                        case R.id.lv3:
                            markWordByLevel(3);
                            break;
                        case R.id.lv4:
                            markWordByLevel(4);
                            break;
                    }
                    popupWindow.dismiss();
                }
            });
            popView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindow.setOutsideTouchable(false);
            popupWindow.setContentView(popView);
        }
        popupWindow.showAsDropDown(toolbar);
    }
}

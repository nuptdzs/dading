package com.nupt.dzs.wordsreader.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.nupt.dzs.wordsreader.R;
import com.nupt.dzs.wordsreader.common.MyApplication;
import com.nupt.dzs.wordsreader.model.ArticleModel;
import com.nupt.dzs.wordsreader.model.WordModel;
import com.nupt.dzs.wordsreader.ui.view.WordSpan;
import com.nupt.dzs.wordsreader.utils.TextJustification;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArticleDetailActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.tvContent)
    TextView tvContent;
    private ArticleModel articleModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loadArticles();
    }

    private void loadArticles() {
        Intent intent = getIntent();
        articleModel = (ArticleModel) intent.getSerializableExtra("data");
        /*Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        int width = dm.widthPixels;
        //根据屏幕调整文字大小
        tvContent.setLineSpacing(0f, 1.5f);
        tvContent.setTextSize(8*(float)width/320f);*/
        tvTitle.setText(articleModel.getEngTitle());
        tvContent.setText(articleModel.getEngContent());
        //TextJustification.justify(tvContent,tvContent.getWidth());
        markWordByLevel(1);
    }

    public void markWordByLevel(int i){
        String  s = articleModel.getEngContent();
        SpannableString spannableString = new SpannableString(articleModel.getEngContent());
        for(WordModel wordModel: MyApplication.wordModels){
            if(wordModel.getLevel() == i) {
                String[] arr =  s.split(wordModel.getWord());
                Log.e("size:",arr.length+"");
                if(arr.length>1){
                    int index = 0;
                    for(int n=0;n<arr.length-1;n++){
                        spannableString.setSpan(new WordSpan(this,wordModel)
                                ,index+arr[n].length()
                                ,index+arr[n].length()+wordModel.getWord().length()
                                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        index+=arr[n].length()+wordModel.getWord().length();
                    }
                }
            }
        }
        tvContent.setText(spannableString);
    }
}

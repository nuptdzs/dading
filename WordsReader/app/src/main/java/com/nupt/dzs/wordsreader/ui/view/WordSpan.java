package com.nupt.dzs.wordsreader.ui.view;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.nupt.dzs.wordsreader.R;
import com.nupt.dzs.wordsreader.model.WordModel;


public class WordSpan extends ClickableSpan {
    Context mContext;
    WordModel mWordModel;
    public WordSpan(Context context, WordModel wordModel){
        mContext = context;
        mWordModel = wordModel;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(mContext.getResources().getColor(R.color.colorPrimary));
        ds.setUnderlineText(false); //去掉下划线
    }

    @Override
    public void onClick(View widget) {

    }
}

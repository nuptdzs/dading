package com.nupt.dzs.wordsreader.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.text.style.UpdateLayout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.nupt.dzs.wordsreader.R;
import com.nupt.dzs.wordsreader.impl.IArticleView;
import com.nupt.dzs.wordsreader.model.WordModel;


public class WordSpan extends ClickableSpan implements UpdateLayout{
    IArticleView mArticleView;
    WordModel mWordModel;
    public boolean checked = false;
    public int highLightLevel = 1;
    public WordSpan(IArticleView iArticleView, WordModel wordModel){
        mArticleView = iArticleView;
        mWordModel = wordModel;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        Context mContext = mArticleView.getContext();
        if(checked){
            ds.bgColor = mContext.getResources().getColor(R.color.colorPrimary);
            ds.setColor(mContext.getResources().getColor(R.color.white));
            ds.setUnderlineText(false); //去掉下划线
        }else {
            ds.bgColor = mContext.getResources().getColor(R.color.transparent);
            if(mWordModel.getLevel()==highLightLevel){
                ds.setColor(mContext.getResources().getColor(R.color.colorPrimary));
                ds.setUnderlineText(true);
            }else {
                ds.setColor(mContext.getResources().getColor(R.color.text_color_content));
                ds.setUnderlineText(false);
            }

        }
    }

    @Override
    public void onClick(View widget) {
        if(!checked){
            Log.e("clicked",mWordModel.getWord());
            mArticleView.showWordInfo(mWordModel);
            checked = true;
            TextView textView = (TextView) widget;
            textView.invalidate();
        }
    }

}

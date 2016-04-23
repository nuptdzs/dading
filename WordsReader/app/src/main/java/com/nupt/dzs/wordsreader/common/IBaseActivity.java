package com.nupt.dzs.wordsreader.common;

import android.content.Context;

/**
 * Created by Administrator on 2016/4/23.
 *
 * @version 1.0
 * @copyright by ${COMPANY}
 */
public interface IBaseActivity {
    void showToast(String msg);
    void showLoading(String msg);
    void hideLoading();
    Context getContext();
}

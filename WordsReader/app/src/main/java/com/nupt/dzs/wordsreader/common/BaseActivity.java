package com.nupt.dzs.wordsreader.common;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2016/4/23.
 *
 * @version 1.0
 * @copyright by ${COMPANY}
 */
public class BaseActivity extends AppCompatActivity implements IBaseActivity{


    ProgressDialog progressDialog;
    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showLoading(String msg) {
        if(progressDialog!=null){

        }
    }

    @Override
    public void hideLoading() {

    }
}

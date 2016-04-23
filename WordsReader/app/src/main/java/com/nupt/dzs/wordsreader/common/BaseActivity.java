package com.nupt.dzs.wordsreader.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


public class BaseActivity extends AppCompatActivity implements IBaseActivity{

    Activity mActivity;
    ProgressDialog progressDialog;
    Toast toast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
    }

    @Override
    public void showToast(String msg) {
        if(null == toast){
            toast = Toast.makeText(getBaseContext(),msg,Toast.LENGTH_SHORT);
        }else {
            toast.setText(msg);
        }
        toast.show();
    }

    @Override
    public void showLoading(String msg) {
        if(null == progressDialog){
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if(null != progressDialog){
            progressDialog.dismiss();
        }
    }

    @Override
    public Context getContext() {
        return mActivity;
    }
}

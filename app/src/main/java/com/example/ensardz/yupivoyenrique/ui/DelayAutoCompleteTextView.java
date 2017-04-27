package com.example.ensardz.yupivoyenrique.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by ensardz on 26/04/2017.
 */

public class DelayAutoCompleteTextView extends AppCompatAutoCompleteTextView {
    private static final int TEXTO_HA_CAMBIADO = 100;
    private static final int DELAY_AUTOCOMPLETAR = 750;

    private int mDelayAutoCompletar = DELAY_AUTOCOMPLETAR;
    private ProgressBar mLoadingIndicator;

    private final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            DelayAutoCompleteTextView.super.performFiltering((CharSequence) msg.obj, msg.arg1);
        }
    };
    public DelayAutoCompleteTextView(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    public void setLoadingIndicator (ProgressBar progressBar){
        mLoadingIndicator = progressBar;
    }

    public void setDelayAutocompletar(int delayAutocompletar){
        mDelayAutoCompletar = delayAutocompletar;
    }

    @Override
    protected void performFiltering(CharSequence text, int keyCode) {
        if (mLoadingIndicator != null){
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }
        mHandler.removeMessages(TEXTO_HA_CAMBIADO);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(TEXTO_HA_CAMBIADO, text), mDelayAutoCompletar);
    }

    @Override
    public void onFilterComplete(int count) {
        if(mLoadingIndicator != null){
            mLoadingIndicator.setVisibility(View.GONE);
        }
        super.onFilterComplete(count);
    }
}

package com.example.livedatabus_demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.library.LiveDataBus;
import com.example.library.LiveDataBus2;
import com.example.library.LiveDataBus3;
import com.example.library.livedata.Observer;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SecondActivity";

    private Button mBtnSendBus3;
    private Button mBtnSendBus4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secend);
        initView();
        initLiveDataBus();
        initLiveDataBus2();
        initLiveDataBus3();
    }

    private void initLiveDataBus() {
        LiveDataBus.get()
                .getChannel("sendBus3", String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String o) {
                        Log.d(TAG, "LiveDataBus onChanged: sendBus3 " + Thread.currentThread().getName() + " value: " + o);
                    }
                });
    }

    private void initLiveDataBus2() {
        LiveDataBus2.get()
                .getChannel("sendBus3", String.class)
                .observe(this,new android.arch.lifecycle.Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String o) {
                        Log.d(TAG, "LiveDataBus2 onChanged: sendBus3 " + Thread.currentThread().getName() + " value: " + o);
                    }
                });
    }

    private void initLiveDataBus3() {
        LiveDataBus3.get()
                .with("sendBus3", String.class)
                .observe(this,new android.arch.lifecycle.Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String o) {
                        Log.d(TAG, "LiveDataBus3 onChanged: sendBus3 " + Thread.currentThread().getName() + " value: " + o);
                    }
                });
    }

    private void initView() {
        mBtnSendBus3 = (Button) findViewById(R.id.btn_sendBus3);
        mBtnSendBus4 = (Button) findViewById(R.id.btn_sendBus4);

        mBtnSendBus3.setOnClickListener(this);
        mBtnSendBus4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 测试LiveDataBus生命周期
            case R.id.btn_sendBus3:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        LiveDataBus.get().getChannel("sendBus3").postValue("异步消息");
                        LiveDataBus2.get().getChannel("sendBus3").postValue("google 异步消息");
                        LiveDataBus3.get().with("sendBus3").postValue("google修复之后的 异步消息");
                    }
                }).start();
                break;
            // 测试LiveDataBus缓冲池
            case R.id.btn_sendBus4:
                LiveDataBus.get().getChannel("sendBus4").setValue("缓存消息1");
                LiveDataBus.get().getChannel("sendBus4").setValue("缓存消息2");

                LiveDataBus2.get().getChannel("sendBus4").setValue("google 缓存消息1");
                LiveDataBus2.get().getChannel("sendBus4").setValue("google 缓存消息2");

                LiveDataBus3.get().with("sendBus4").setValue("google修复之后的 缓存消息1");
                LiveDataBus3.get().with("sendBus4").setValue("google修复之后的 缓存消息2");
                break;
        }
    }
}

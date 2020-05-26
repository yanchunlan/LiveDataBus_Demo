package com.example.livedatabus_demo;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.library.LiveDataBus;
import com.example.library.LiveDataBus2;
import com.example.library.LiveDataBus3;
import com.example.library.LiveDataViewModel;
import com.example.library.LiveDataViewModel2;
import com.example.library.livedata.Observer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private Button mBtnSendBus1;
    private Button mBtnSendBus2;
    private Button mBtnToSendAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initLiveDataBus1();
        initLiveDataBus2();
        initLiveDataBus3();

//        initViewModel1();
//        initViewModel2();
    }

    private void initViewModel1() {
        LiveDataViewModel2 viewMode = ViewModelProviders.of(this).get(LiveDataViewModel2.class);
        viewMode.getTime().observe(this, new android.arch.lifecycle.Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d(TAG, "ViewModel2 onChanged: sendBus1 " + Thread.currentThread().getName() + " value: " + s);
            }
        });
    }

    private void initViewModel2() {
        LiveDataViewModel viewMode = new LiveDataViewModel();
        viewMode.getTime().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d(TAG, "ViewModel1 onChanged: sendBus1 " + Thread.currentThread().getName() + " value: " + s);
            }
        });
    }

    private void initLiveDataBus3() {
        LiveDataBus3.get()
                .with("sendBus1", String.class)
                .observe(this, new android.arch.lifecycle.Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String o) {
                        Log.d(TAG, "LiveDataBus3 onChanged: sendBus1 " + Thread.currentThread().getName() + " value: " + o);
                    }
                });
        LiveDataBus3.get()
                .with("sendBus2", String.class)
                .observe(this, new android.arch.lifecycle.Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String o) {
                        Log.d(TAG, "LiveDataBus3 onChanged: sendBus2 " + Thread.currentThread().getName() + " value: " + o);
                    }
                });
        LiveDataBus3.get()
                .with("sendBus4", String.class)
                .observeForever( new android.arch.lifecycle.Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String o) {
                        Log.d(TAG, "LiveDataBus3 observeForever onChanged: sendBus4 " + Thread.currentThread().getName() + " value: " + o);
                    }
                });
    }
    private void initLiveDataBus2() {
        LiveDataBus2.get()
                .getChannel("sendBus1", String.class)
                .observe(this, new android.arch.lifecycle.Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String o) {
                        Log.d(TAG, "LiveDataBus2 onChanged: sendBus1 " + Thread.currentThread().getName() + " value: " + o);
                    }
                });
        LiveDataBus2.get()
                .getChannel("sendBus2", String.class)
                .observe(this, new android.arch.lifecycle.Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String o) {
                        Log.d(TAG, "LiveDataBus2 onChanged: sendBus2 " + Thread.currentThread().getName() + " value: " + o);
                    }
                });
        LiveDataBus2.get()
                .getChannel("sendBus4", String.class)
                .observe(this, new android.arch.lifecycle.Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String o) {
                        Log.d(TAG, "LiveDataBus2 onChanged: sendBus4 " + Thread.currentThread().getName() + " value: " + o);
                    }
                });
    }

    private void initLiveDataBus1() {
        LiveDataBus.get()
                .getChannel("sendBus1", String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String o) {
                        Log.d(TAG, "LiveDataBus onChanged: sendBus1 " + Thread.currentThread().getName() + " value: " + o);
                    }
                });
        LiveDataBus.get()
                .getChannel("sendBus2", String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String o) {
                        Log.d(TAG, "LiveDataBus onChanged: sendBus2 " + Thread.currentThread().getName() + " value: " + o);
                    }
                });
        LiveDataBus.get()
                .getChannel("sendBus4", String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String o) {
                        Log.d(TAG, "LiveDataBus onChanged: sendBus4 " + Thread.currentThread().getName() + " value: " + o);
                    }
                });
    }

    private void initView() {
        mBtnSendBus1 = (Button) findViewById(R.id.btn_sendBus1);
        mBtnSendBus2 = (Button) findViewById(R.id.btn_sendBus2);
        mBtnToSendAct = (Button) findViewById(R.id.btn_toSendAct);

        mBtnSendBus1.setOnClickListener(this);
        mBtnSendBus2.setOnClickListener(this);
        mBtnToSendAct.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 测试发送主线程消息
            case R.id.btn_sendBus1:
                LiveDataBus.get().getChannel("sendBus1").setValue("主线程消息");
                LiveDataBus2.get().getChannel("sendBus1").setValue("google 主线程消息");
                LiveDataBus3.get().with("sendBus1").setValue("google修复之后的 主线程消息 ");
                break;
            // 测试发送子线程消息
            case R.id.btn_sendBus2:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LiveDataBus.get().getChannel("sendBus2").postValue("子线程消息");
                        LiveDataBus2.get().getChannel("sendBus2").postValue("google 子线程消息");
                        LiveDataBus3.get().with("sendBus2").postValue("google修复之后的 子线程消息");
                    }
                }).start();
                break;
            case R.id.btn_toSendAct:
                startActivity(new Intent(this, SecondActivity.class));
                break;
        }
    }
}

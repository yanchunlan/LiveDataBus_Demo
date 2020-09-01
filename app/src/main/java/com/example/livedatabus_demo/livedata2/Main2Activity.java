package com.example.livedatabus_demo.livedata2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.livedatabus_demo.R;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Main2Activity";
    private Button mBtnToThreeAct;
    private Button mBtnMsg;


    private androidx.lifecycle.MutableLiveData liveData = new MutableLiveData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mBtnToThreeAct = (Button) findViewById(R.id.btn_toThreeAct);
        mBtnToThreeAct.setOnClickListener(this);
        mBtnMsg = (Button) findViewById(R.id.btn_msg);
        mBtnMsg.setOnClickListener(this);

        initData();
    }

    private void initData() {
        liveData.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                Log.d(TAG, "onChanged:   1111 " + o);
            }
        });
        liveData.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                Log.d(TAG, "onChanged:  2222 " + o);
            }
        });


        Global.liveDatax.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                Log.d(TAG, "onChanged:   1111 xxxx " + o);
            }
        });
        Global.liveDatax.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                Log.d(TAG, "onChanged:   2222 xxxx " + o);
            }
        });

        Global.liveData2.observe(this, new com.example.library.livedata.Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                Log.d(TAG, "onChanged:   1111 livedata2 " + o);
            }
        });
        Global.liveData2.observe(this, new com.example.library.livedata.Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                Log.d(TAG, "onChanged:   2222 livedata2 " + o);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_msg:
                liveData.postValue("post");
                Global.liveDatax.postValue("post");
                Global.liveData2.postValue("post");
                break;
            case R.id.btn_toThreeAct:
                startActivity(new Intent(this, ThreeActivity.class));
                break;
        }
    }
}

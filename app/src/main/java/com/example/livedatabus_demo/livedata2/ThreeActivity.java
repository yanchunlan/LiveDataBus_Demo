package com.example.livedatabus_demo.livedata2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.livedatabus_demo.R;

public class ThreeActivity extends AppCompatActivity {

    private static final String TAG = "ThreeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);

        Global.liveDatax.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                Log.d(TAG, "onChanged:  3333 x " + o);
            }
        });
       Global.liveData2.observe(this, new com.example.library.livedata.Observer() {
           @Override
           public void onChanged(@Nullable Object o) {
               Log.d(TAG, "onChanged:  3333 liveData2 " + o);
           }
       });


    }

    public void postMsg(View view) {
        Global.liveDatax.postValue("post three");
        Global.liveData2.postValue("post three");

    }
}

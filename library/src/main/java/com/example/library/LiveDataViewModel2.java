package com.example.library;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.Timer;
import java.util.TimerTask;

/**
 * author:  ycl
 * date:  2019/06/01 12:29
 * desc:  google官方的viewModel
 */
public class LiveDataViewModel2 extends ViewModel {

    private String data;
    static int i = 0;
    private MutableLiveData<String> mTime = new MutableLiveData<>();

    public LiveDataViewModel2() {
        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                i++;
                mTime.postValue(" i "+i);
            }
        },1000,1000);
    }

    public MutableLiveData<String> getTime() {
        return mTime;
    }
}

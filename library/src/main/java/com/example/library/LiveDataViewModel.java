package com.example.library;

import com.example.library.livedata.LiveData;

import java.util.Timer;
import java.util.TimerTask;

/**
 * author:  ycl
 * date:  2019/06/01 12:29
 * desc:  ziji
 */
public class LiveDataViewModel  {

    private String data;
    static int i = 0;
    private LiveData<String> mTime = new LiveData<>();

    public LiveDataViewModel() {
        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                i++;
                mTime.postValue(" i "+i);
            }
        },1000,1000);
    }

    public LiveData<String> getTime() {
        return mTime;
    }
}

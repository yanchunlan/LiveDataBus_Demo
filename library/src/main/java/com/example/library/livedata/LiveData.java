package com.example.library.livedata;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:  ycl
 * date:  2019/05/31 23:27
 * desc:  包含有生命周期管控逻辑，并不是每个界面去实现生命周期，是单独创建fragment去监听生命周期
 */
public class LiveData<T> {
    private static final String TAG = "com.ycl.livadata";


    private HashMap<Integer, Observer<T>> map = new HashMap<>();

    // 离开界面的缓冲池
    private HashMap<Integer, List<T>> mPendingDelayList = new HashMap<>();
    private Handler mHandler = new Handler(Looper.getMainLooper());


    public void postValue(final T value) {
        synchronized (this) {
//            if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
//                setValue(value);
//            } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    setValue(value);
                }
            });
//            }
        }
    }

    public void setValue(T value) {
        List<Observer> destroyList = new ArrayList<>();
        for (Map.Entry<Integer, Observer<T>> entry : map.entrySet()) {
            Observer<T> observer = entry.getValue();
            Integer activityCode = entry.getKey();
            if (observer.getState() == Observer.ONSTART) {
                observer.onChanged(value);
            }
            if (observer.getState() == Observer.ONPAUSE) {
                if (mPendingDelayList.get(activityCode )== null) {
                    mPendingDelayList.put(activityCode, new ArrayList<T>());
                }
                // 添加到缓冲消息中
                if (!mPendingDelayList.get(activityCode).contains(value)) {
                    mPendingDelayList.get(activityCode).add(value);
                }
            }
            if (observer.getState() == Observer.ONDETCH) {
                destroyList.add(observer);
            }
        }
        for (Observer<T> item : destroyList) {
            destroyList.remove(item);
        }
    }

    public void observe(AppCompatActivity activity, Observer<T> observer) {
        FragmentManager fm = activity.getSupportFragmentManager();
        HoldFragment current = (HoldFragment) fm.findFragmentByTag(TAG);
        if (current == null) {
            current = new HoldFragment();
            fm.beginTransaction().add(current, TAG).commitAllowingStateLoss();
        }
        current.setLifecycleListener(mLifecycleListener);
        map.put(activity.hashCode(), observer);
    }

    private LifecycleListener mLifecycleListener = new LifecycleListener() {
        @Override
        public void onAttach(int activityCode) {
            map.get(activityCode).setState(Observer.ONACCACH);
        }

        @Override
        public void onStart(int activityCode) {
            map.get(activityCode).setState(Observer.ONSTART);

            // 回来界面，从缓冲池取数据
            if (mPendingDelayList.get(activityCode) == null || mPendingDelayList.get(activityCode).size() == 0) {
                return;
            }
            for (T t : mPendingDelayList.get(activityCode)) {
                map.get(activityCode).onChanged(t);
            }
            mPendingDelayList.get(activityCode).clear();
        }

        @Override
        public void onPause(int activityCode) {
            map.get(activityCode).setState(Observer.ONPAUSE);
        }

        @Override
        public void onDetach(int activityCode) {
            map.get(activityCode).setState(Observer.ONDETCH);
            map.remove(activityCode);
        }
    };
}

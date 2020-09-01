package com.example.library.livedata2;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.example.library.livedata.Observer;

import java.util.ArrayList;
import java.util.List;

import static androidx.lifecycle.Lifecycle.State.DESTROYED;
import static androidx.lifecycle.Lifecycle.State.RESUMED;

/**
 * author:  ycl
 * date:  2020/07/31 23:27
 * desc:  模仿最新的liveData，生命周期使用官方的liveCycle控制
 */
public class LiveData2<T> {

    private T data = null;
    private List<ObserverWrapper> observers = new ArrayList<>();
    private int version = -1;


    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer) {
        if (owner.getLifecycle().getCurrentState() == DESTROYED) {
            return;
        }
        ObserverWrapper wrapper = new ObserverWrapper();

        // 此处设置版本一致主要是解决初始化之前有消息，旧消息就会被拦截，不会在初始化的时候分发消息给观察者
        wrapper.lastVersion = version;

        wrapper.observer = observer;
        wrapper.lifecycle = owner.getLifecycle();
        wrapper.lifecycleBound = new LifecycleBound();
        observers.add(wrapper);

        wrapper.lifecycle.addObserver(wrapper.lifecycleBound);
    }


    public void postValue(T value) {
        version++;
        data = value;
        dispatchingValue();
    }


    private void dispatchingValue() {
        for (ObserverWrapper wrapper : observers) {
            considerNotify(wrapper);
        }
    }

    private void considerNotify(ObserverWrapper observer) {
        if (observer.lifecycle.getCurrentState() != RESUMED) {
            return;
        }

        if (observer.lastVersion >= version) {
            return;
        }
        observer.lastVersion = version;
        observer.observer.onChanged((T) data);
    }

    private class ObserverWrapper {
        Observer<T> observer;
        LifecycleBound lifecycleBound;
        Lifecycle lifecycle;
        int lastVersion = -1;
    }


    @SuppressLint("RestrictedApi")
    private class LifecycleBound implements GenericLifecycleObserver {

        @Override
        public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
            if (source.getLifecycle().getCurrentState() == DESTROYED) {
                remove(source.getLifecycle());
            }
            if (data != null) {
                dispatchingValue();
            }
        }

        private void remove(Lifecycle lifecycle) {
            for (ObserverWrapper observer : observers) {
                if (observer.lifecycle == lifecycle) {
                    observer.lifecycle.removeObserver(observer.lifecycleBound);
                    observers.remove(observer);
                }
            }
        }
    }

}


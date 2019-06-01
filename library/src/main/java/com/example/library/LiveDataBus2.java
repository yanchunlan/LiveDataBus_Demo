package com.example.library;

import android.arch.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;

/**
 * author:  ycl
 * date:  2019/06/01 12:18
 * desc:  官方的google的liveData
 *          经测试google官方没有生命周期包含，如果需要有生命周期需要集合ViewModel
 */
public class LiveDataBus2 {
    private final Map<String, MutableLiveData<Object>> bus;

    public LiveDataBus2() {
        bus = new HashMap<>();
    }

    private static class SingletonHolder {
        private static final LiveDataBus2 DEFAULT_BUS = new LiveDataBus2();
    }

    public static LiveDataBus2 get() {
        return LiveDataBus2.SingletonHolder.DEFAULT_BUS;
    }

    public MutableLiveData<Object> getChannel(String target) {
        return getChannel(target, Object.class);
    }

    public <T> MutableLiveData<T> getChannel(String target, Class<T> clazz) {
        if (!bus.containsKey(target)) {
            bus.put(target, new MutableLiveData<>());
        }
        return (MutableLiveData<T>) bus.get(target);
    }

}

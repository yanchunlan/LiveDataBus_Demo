package com.example.library;

import com.example.library.livedata.LiveData;

import java.util.HashMap;
import java.util.Map;

/**
 * author:  ycl
 * date:  2019/05/31 20:54
 * desc:  使用自定义的liveData
 */
public class LiveDataBus {
    private final Map<String, LiveData<Object>> bus;

    public LiveDataBus() {
        bus = new HashMap<>();
    }

    private static class SingletonHolder {
        private static final LiveDataBus DEFAULT_BUS = new LiveDataBus();
    }

    public static LiveDataBus get() {
        return SingletonHolder.DEFAULT_BUS;
    }

    public LiveData<Object> getChannel(String target) {
        return getChannel(target, Object.class);
    }

    public <T> LiveData<T> getChannel(String target, Class<T> clazz) {
        if (!bus.containsKey(target)) {
            bus.put(target, new LiveData<>());
        }
        return (LiveData<T>) bus.get(target);
    }

}

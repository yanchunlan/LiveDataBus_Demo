package com.example.library.livedata;

import android.support.annotation.Nullable;

/**
 * author:  ycl
 * date:  2019/05/31 23:22
 * desc:
 */
public abstract class Observer<T> {

    public static final int ONACCACH = 0;
    public static final int ONSTART = 1;
    public static final int ONPAUSE = 2;
    public static final int ONDETCH = 3;

    private int state=ONACCACH;

    public abstract void onChanged(@Nullable T t);

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}

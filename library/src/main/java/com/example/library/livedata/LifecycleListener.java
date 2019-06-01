package com.example.library.livedata;

/**
 * author:  ycl
 * date:  2019/05/31 23:30
 * desc:
 */
public interface LifecycleListener {
    void onAttach(int activityCode);
    void onStart(int activityCode);
    void onPause(int activityCode);
    void onDetach(int activityCode);
}

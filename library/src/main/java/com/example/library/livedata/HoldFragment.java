package com.example.library.livedata;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * author:  ycl
 * date:  2019/05/31 23:39
 * desc:
 */
public class HoldFragment extends Fragment {

    private LifecycleListener mLifecycleListener;
    private int activityCode;

    public void setLifecycleListener(LifecycleListener lifecycleListener) {
        mLifecycleListener = lifecycleListener;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activityCode = context.hashCode();
        if (mLifecycleListener!=null) {
            mLifecycleListener.onAttach(activityCode);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mLifecycleListener!=null) {
            mLifecycleListener.onDetach(activityCode);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mLifecycleListener!=null) {
            mLifecycleListener.onStart(activityCode);
        }
    }  @Override
    public void onPause() {
        super.onPause();
        if (mLifecycleListener!=null) {
            mLifecycleListener.onPause(activityCode);
        }
    }

}

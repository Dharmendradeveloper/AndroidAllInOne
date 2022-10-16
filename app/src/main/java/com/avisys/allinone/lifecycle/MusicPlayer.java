package com.avisys.allinone.lifecycle;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class MusicPlayer implements LifecycleObserver {
    private final String TAG = "play";

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private void musicStart(){
        Log.e(TAG,"OnStart");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private void musicCreate(){
        Log.e(TAG,"OnCreate");
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void musicResume(){
        Log.e(TAG,"OnResume");
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void musicPause(){
        Log.e(TAG,"OnPause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private void musicStop(){
        Log.e(TAG,"OnStop");
    }

    private void musicRestart(){
        Log.e(TAG,"OnRestart");
    }
   @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void musicDestroy(){
        Log.e(TAG,"OnDestroy");
    }



}

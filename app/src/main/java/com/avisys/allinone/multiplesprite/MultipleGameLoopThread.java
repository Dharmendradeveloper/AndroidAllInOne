package com.avisys.allinone.multiplesprite;

import android.graphics.Canvas;
import android.util.Log;

public class MultipleGameLoopThread extends Thread{
    private boolean running = false;
    private MultipleGameView multipleGameView;

    public MultipleGameLoopThread(MultipleGameView multipleGameView){
        this.multipleGameView = multipleGameView;

    }


    @Override
    public void run() {
        while (running){
            Canvas canvas = null;
           try {
               canvas = multipleGameView.getHolder().lockCanvas();
               synchronized (multipleGameView.getHolder()){
                   Log.e("debug::","Run3");
                   multipleGameView.myDraw(canvas);
               }
           }finally {
               if (canvas!=null) {
                   multipleGameView.getHolder().unlockCanvasAndPost(canvas);
                   Log.e("debug::","run6");
               }
           }
        }
    }

    public void setRunning(boolean isThreadRunning){
        Log.e("debug::","seRunning2"+isThreadRunning);
        this.running = isThreadRunning;
    }
}

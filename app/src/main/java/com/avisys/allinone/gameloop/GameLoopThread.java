package com.avisys.allinone.gameloop;

import android.graphics.Canvas;
import android.util.Log;

import com.avisys.allinone.surfaceview.MyGameSurfaceView;

public class GameLoopThread extends Thread{

    private MyGameSurfaceView myGameSurfaceView;
    private boolean running  = false;//flag
    static final long FPS = 10;

    public GameLoopThread(MyGameSurfaceView myGameSurfaceView) {
        this.myGameSurfaceView = myGameSurfaceView;
    }

    public void setRunning(boolean run){
        this.running = run;
    }

    @Override
    public void run() {
        Log.e("Debug::"," GameThread Join :");
        long ticksPS = 1000/FPS;
        long startTime;
        long sleepTime;
        while (running){
            Canvas canvas = null;
            startTime = System.currentTimeMillis();
            try {
                canvas = myGameSurfaceView.getHolder().lockCanvas();
                synchronized (myGameSurfaceView.getHolder()) {
                    Log.e("Under Synchronized ::", " " + myGameSurfaceView.getHolder());
                    myGameSurfaceView.myDraw(canvas);
                }
            }finally {
                if (canvas!=null)
                    myGameSurfaceView.getHolder().unlockCanvasAndPost(canvas);
            }
            sleepTime = ticksPS - (System.currentTimeMillis()-startTime);
            try{
                if (sleepTime>0){
                   sleep(sleepTime);
                }else{
                    sleep(10);
                }
            }catch (Exception exception){

            }

        }
    }
}

package com.avisys.allinone.ontouchcollission;

import android.graphics.Canvas;

public class TouchGameLoopThread extends Thread{
    private boolean running  = false;
    private TouchGameView touchGameView;
    private final long FPS = 10;

    TouchGameLoopThread(TouchGameView gameView){
        this.touchGameView = gameView;
    }

    @Override
    public void run() {
        long ticksPS = 1000/FPS;
        long startTime = 0;
        long sleepTime;
        while (running){
            Canvas canvas = null;
            try{
                canvas = touchGameView.getHolder().lockCanvas();
                synchronized (touchGameView.getHolder()){
                    touchGameView.myDraw(canvas);
                }
            }finally {
                if (canvas!=null)
                    touchGameView.getHolder().unlockCanvasAndPost(canvas);
            }
            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);

            try {

                if (sleepTime > 0)

                    sleep(sleepTime);

                else

                    sleep(10);

            } catch (Exception e) {}
        }
    }

    public void setRunning(boolean isThreadRunning){
        this.running  = isThreadRunning;
    }

}

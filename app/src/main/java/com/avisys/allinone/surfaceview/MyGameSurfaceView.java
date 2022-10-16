package com.avisys.allinone.surfaceview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.avisys.allinone.R;
import com.avisys.allinone.gameloop.GameLoopThread;
import com.avisys.allinone.spriteloop.SpriteAnimation;

public class MyGameSurfaceView extends SurfaceView {

    private Bitmap bitmap;
    private SurfaceHolder surfaceHolder;
    private GameLoopThread gameLoopThread;
    private int x = 0;
    private int xSpeed = 1;
    private SpriteAnimation sprite;
    public MyGameSurfaceView(Context context) {
        super(context);
        gameLoopThread = new GameLoopThread(this);
        surfaceHolder = getHolder();/* getHolder() create object*/
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                Log.e("Surface ::","Surface Created");
//                Canvas canvas = surfaceHolder.lockCanvas(null);
//                myDraw(canvas);
//                surfaceHolder.unlockCanvasAndPost(canvas);
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                Log.e("Surface  ::","Surface Changed ");
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                Log.e("Surface "," Surface Destroyed :: ");
                boolean retry = true;
                gameLoopThread.setRunning(false);
                while (retry){
                    try {
                        Log.e("Debug::"," Bef Join :");
                        gameLoopThread.join();
                        Log.e("Debug::"," Aft Join :");
                        retry = false;
                    }catch (InterruptedException exception){
                        Log.e("Surface "," Surface Destroyed ::"+exception.getLocalizedMessage());
                    }
                }

            }
        });
        // Add Bitmap
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bad1);
//        sprite = new Sprite(this,bitmap);
        sprite = new SpriteAnimation(this,bitmap);

    }

    public void myDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        Log.e("System Resources ::"," X :"+x +" Width :"+getWidth()+" bitmapWidth() :"+bitmap.getWidth());
//        if (x<getWidth()-bitmap.getWidth())
//            x++;
//        else
//            gameLoopThread.setRunning(false);

       /* if (x == getWidth() - bitmap.getWidth())
            x=0;//xSpeed = -1;
        if (x == 0)
            xSpeed = 1;
        x = x + xSpeed;**/
        /**
         * @link Sprite
         * */
        sprite.onDraw(canvas);
//        canvas.drawBitmap(bitmap,x,10,null);
    }


}

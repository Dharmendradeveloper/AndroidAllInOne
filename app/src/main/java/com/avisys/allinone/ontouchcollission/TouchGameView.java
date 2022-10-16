package com.avisys.allinone.ontouchcollission;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.avisys.allinone.R;

import java.util.ArrayList;
import java.util.List;

public class TouchGameView extends SurfaceView {
    private SurfaceHolder surfaceHolder;
    private TouchGameLoopThread gameLoopThread;
    private List<TouchSprite> spriteList = new ArrayList<>();
    private List<TouchSprite> bornSpriteList = new ArrayList<>();
    private String TAG = TouchGameView.class.getSimpleName();
    private int size = 0;
    private long lastClick;
    private int deadCount=0;
    private boolean isEnemyMiss = false;
    public TouchGameView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        gameLoopThread = new TouchGameLoopThread(this);
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
               createSprites();
               gameLoopThread.setRunning(true);
               gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                gameLoopThread.setRunning(false);
                boolean retry = true;
                try {
                    while (retry){
                        gameLoopThread.join();
                        retry =false;
                    }
                }catch (InterruptedException exception){

                }

            }
        });
    }

    private void createSprites(){
        spriteList.add(createSprite(R.drawable.bad1));
        spriteList.add(createSprite(R.drawable.bad2));
        spriteList.add(createSprite(R.drawable.bad3));
        spriteList.add(createSprite(R.drawable.bad4));

        spriteList.add(createSprite(R.drawable.bad5));
        spriteList.add(createSprite(R.drawable.bad6));
        spriteList.add(createSprite(R.drawable.good1));
        spriteList.add(createSprite(R.drawable.good2));

        spriteList.add(createSprite(R.drawable.good3));
        spriteList.add(createSprite(R.drawable.good4));
        spriteList.add(createSprite(R.drawable.good5));
        spriteList.add(createSprite(R.drawable.good6));

    }

    public void createFiveSprites(){
        spriteList.add(createSprite(R.drawable.bad1));
        spriteList.add(createSprite(R.drawable.bad2));
        spriteList.add(createSprite(R.drawable.bad3));
        spriteList.add(createSprite(R.drawable.bad4));

        spriteList.add(createSprite(R.drawable.bad5));
    }

    private TouchSprite createSprite(int resource){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),resource);
        return new TouchSprite(this,bitmap);
    }

    public void myDraw(Canvas canvas){
        canvas.drawColor(Color.WHITE);
        for (TouchSprite sprite:spriteList){
            sprite.onDraw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        onCapturedPointerEvent(event);
        if (System.currentTimeMillis()-lastClick>500){
            lastClick = System.currentTimeMillis();
            synchronized (getHolder()){
                size = spriteList.size();
                for (int i=spriteList.size()-1;i>=0;i--){

                    /**
                     * @link sprite field variable returns the object of type TouchSprit based on indexing
                     * position and in that object fields are available.
                     * */
                    TouchSprite sprite = spriteList.get(i);
                    if (sprite.isCollation(event.getX(),event.getY())) {
                        Log.e(TAG,"if block");
                        spriteList.remove(sprite);
                        break;
                    }else {
                        Log.e(TAG,"else block");
                    }
                }// loop closed
            }
        }
        return true;
    }

    @Override
    public boolean onCapturedPointerEvent(MotionEvent event) {
//        if (System.currentTimeMillis()-lastClick>500){
//            lastClick = System.currentTimeMillis();
//            synchronized (getHolder()){
//                size = spriteList.size();
//                for (int i=spriteList.size()-1;i>=0;i--){
//                    /**
//                     * @link sprite field variable returns the object of type TouchSprit based on indexing
//                     * position and in that object fields are available.
//                     * */
//                    TouchSprite sprite = spriteList.get(i);
//                    addOrRemoveSprite(sprite);
//                    Log.e("isCollation:::"," "+sprite.isCollation(event.getX(),event.getY()));
//                    if (sprite.isCollation(event.getX(),event.getY())){
//                        Log.e("TouchEvent",""+true);
//                        spriteList.remove(sprite);
//                        deadCount++;
//                        break;
//                    }else{
//                        Log.e("TouchEvent",""+false);
//                        Log.e("size::"+size," spriteList :: "+spriteList.size());
//                        if (size>=spriteList.size()) {
////                            if (deadCount==2&&spriteList.size()>12){
////                                for (int dead=0;dead>5;dead++){
////                                    spriteList.remove(dead);
////                                }
////                            }
//                            createSprites();
//                        }
//
//
//                    }
//               }// loop closed
//            }
//        }
        return super.onCapturedPointerEvent(event);
    }

    private synchronized boolean addOrRemoveSprite(TouchSprite sprite,MotionEvent event) {
        if (sprite.isCollation(event.getX(),event.getY())){
            isEnemyMiss = true;
            Log.e("OnTouchEvent",""+true);
            spriteList.remove(sprite);
        }else{
            Log.e("sprite list size::"," "+size+"::"+spriteList.size());
            if (size>= spriteList.size()) {
                isEnemyMiss = false;
                Log.e("sprite list size::"," "+size+"::"+spriteList.size());
                createFiveSprites();
            }

        }
        return isEnemyMiss;
    }
}

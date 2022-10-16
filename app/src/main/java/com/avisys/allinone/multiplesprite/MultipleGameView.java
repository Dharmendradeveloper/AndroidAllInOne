package com.avisys.allinone.multiplesprite;

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

import java.util.ArrayList;
import java.util.List;

public class MultipleGameView extends SurfaceView {
    private SurfaceHolder surfaceHolder;
    private MultipleGameLoopThread gameLoopThread;
    private MultipleGameView multipleGameView;
    private List<MultipleSprite> listOfAddView = new ArrayList<MultipleSprite>();


    public MultipleGameView(Context context) {
        super(context);
        surfaceHolder = getHolder();// INSTANCE_VARIABLE:Object level
        gameLoopThread = new MultipleGameLoopThread(this);
        // Add callback
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                Log.e("debug::","SurfaceCreated__1");
                createSprites();
                gameLoopThread.setRunning(true);
                gameLoopThread.start();

            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                boolean retry = true;
                gameLoopThread.setRunning(false);
                try {
                    while (retry) {
                        retry = false;
                        gameLoopThread.join();
                    }
                }catch (InterruptedException exception){

                }
            }
        });
    }

    private void createSprites(){
        Log.e("debug::","createsSprites5");
        listOfAddView.add(createMultipleSprite(R.drawable.bad1));
        listOfAddView.add(createMultipleSprite(R.drawable.bad2));
        listOfAddView.add(createMultipleSprite(R.drawable.bad3));
        listOfAddView.add(createMultipleSprite(R.drawable.bad4));

        listOfAddView.add(createMultipleSprite(R.drawable.bad5));
        listOfAddView.add(createMultipleSprite(R.drawable.bad6));
        listOfAddView.add(createMultipleSprite(R.drawable.good1));
        listOfAddView.add(createMultipleSprite(R.drawable.good2));

        listOfAddView.add(createMultipleSprite(R.drawable.good3));
        listOfAddView.add(createMultipleSprite(R.drawable.good4));
        listOfAddView.add(createMultipleSprite(R.drawable.good5));
        listOfAddView.add(createMultipleSprite(R.drawable.good6));
    }

    private MultipleSprite createMultipleSprite(int resource){
        Log.e("debug::","createMultipleSprite6");
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),resource);
        return new MultipleSprite(bitmap,this);
    }

    public void myDraw(Canvas canvas){
        Log.e("debug::","first 4");
        canvas.drawColor(Color.WHITE);
        for (MultipleSprite sp:listOfAddView){
            Log.e("debug::","first 5");
            sp.onDraw(canvas);
        }
    }
}

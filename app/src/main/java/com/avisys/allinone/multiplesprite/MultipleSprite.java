package com.avisys.allinone.multiplesprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;

public class MultipleSprite {
    private Bitmap bitmap;
    private MultipleGameView myGameSurfaceView;
    private int height;
    private int width;
    private static final int BMP_ROW = 4;
    private static final int BMP_COLUMN = 3;
    private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;
    private int currentFrame;
    // direction = 0 up, 1 left, 2 down, 3 right,

    // animation = 3 back, 1 left, 0 front, 2 right

    int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };


    public MultipleSprite(Bitmap bitmap, MultipleGameView myGameSurfaceView) {
        this.bitmap = bitmap;
        this.myGameSurfaceView = myGameSurfaceView;
        height = bitmap.getHeight()/BMP_ROW;
        width = bitmap.getWidth()/BMP_COLUMN;

        Random random = new Random();
        x = random.nextInt(myGameSurfaceView.getWidth()-width);
        y = random.nextInt(myGameSurfaceView.getHeight()-height);
        xSpeed = random.nextInt(10)-5;// random value
        ySpeed = random.nextInt(10)-5;// random value
    }

    public void onDraw(Canvas canvas){
        Log.e("debug::","onDraw in multiple sprite");
        update();
        int srcX = currentFrame*width;
        int srcY = getRowAnimation()*height;
        Rect src = new Rect(srcX,srcY,srcX+width,srcY+height);
        Rect dst = new Rect(x,y,x+width,y+height);
        canvas.drawBitmap(bitmap,src,dst,null);
    }

    private void update(){
        Log.e("debug::","update");
        if (x>=myGameSurfaceView.getWidth()-width-xSpeed||x+xSpeed<=0)
            xSpeed = -xSpeed;
        x = x+xSpeed;
        if (y>=myGameSurfaceView.getHeight()-height-ySpeed||y+ySpeed<=0)
            ySpeed = -ySpeed;
        y = y+ySpeed;
        currentFrame = ++currentFrame%BMP_COLUMN;
    }

    private int getRowAnimation(){
        Log.e("debug::","getRowAnimation");
        double dirDouble = (Math.atan2(xSpeed,ySpeed)/(Math.PI/2)+2);
        int direction = (int) Math.round(dirDouble)%BMP_ROW;
        return DIRECTION_TO_ANIMATION_MAP[direction];
    }
}

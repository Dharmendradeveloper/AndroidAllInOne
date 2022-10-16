package com.avisys.allinone.spriteloop;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.avisys.allinone.surfaceview.MyGameSurfaceView;

import java.util.Random;

public class SpriteAnimation {
    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right
    int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };

    private MyGameSurfaceView myGameSurfaceView;
    private Bitmap bitmap;
    private int height;
    private int width;
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 3;
    private int xSpeed ;
    private int ySpeed ;
    private int currentFrame;
    private int x = 0;
    private int y = 0;

    public SpriteAnimation(MyGameSurfaceView myGameSurfaceView, Bitmap bitmap) {
        this.myGameSurfaceView = myGameSurfaceView;
        this.bitmap = bitmap;
        /**
         * Here width and height can be calculated for single images.
         * */
        this.width  = bitmap.getWidth()/BMP_COLUMNS;
        this.height = bitmap.getHeight()/BMP_ROWS;
       /** java.util.Random.nextInt(int bound): Returns a pseudo random, uniformly distributed int value
        between 0 (inclusive) and the specified value (exclusive), drawn from this random
        number generator’s sequence*/
        Random random = new Random();
        xSpeed = random.nextInt(10)-5;
        ySpeed = random.nextInt(10)-5;
    }

    public void onDraw(Canvas canvas){
        update();
        int srcX = currentFrame*width;
        int srcY = getAnimationRow()*height;
        Rect src = new Rect(srcX,srcY,srcX+width,srcY+height);
        Rect dst = new Rect(x,y,x+width,y+height);
        canvas.drawBitmap(bitmap,src,dst,null);


    }

    private void update(){
        if (x>myGameSurfaceView.getWidth()-width-xSpeed||x+xSpeed<0){
            xSpeed = -xSpeed;
        }
        x = x+xSpeed;
        if (y>myGameSurfaceView.getHeight()-height-ySpeed||y+ySpeed<0){
            ySpeed = -ySpeed;
        }
        y = y+ySpeed;
        currentFrame = ++currentFrame%BMP_COLUMNS;// BMP_COLUMNS=3
    }

    // direction = 0 up, 1 left, 2 down, 3 right,

    // animation = 3 back, 1 left, 0 front, 2 right

    private int getAnimationRow() {

        double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);

        int direction = (int) Math.round(dirDouble) % BMP_ROWS;

        return DIRECTION_TO_ANIMATION_MAP[direction];

    }
}

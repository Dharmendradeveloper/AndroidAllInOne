package com.avisys.allinone.ontouchcollission;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;
/**
 * @x is a field which store the random value of which is left by subtracting the bitmap image width.
 * @y is a field which store the random value of which is left by abstraction the bitmap image height.
 * @x2 is a local var that deals with the touch event of x position and store the value of that event
 * in x2.
 * @y2 is also a local var that deals with the touch of y position and store the value of that event
 * in y2.
 * */

public class TouchSprite {
    private TouchGameView gameView;
    private Bitmap bitmap;
    private int width;
    private int height;
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMN = 3;
    private int x = 0;
    private int y = 0;
    private int xSpeed;
    private int ySpeed;
    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right
    private int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
    private int currentFrame = 0;


    public TouchSprite(TouchGameView gameView, Bitmap bitmap) {
        this.gameView = gameView;
        this.bitmap = bitmap;
        this.width = bitmap.getWidth()/BMP_COLUMN;
        this.height = bitmap.getHeight()/BMP_ROWS;
        // Generate random number and init the variable
        Random random = new Random();
        x = random.nextInt(gameView.getWidth()-width);
        y = random.nextInt(gameView.getHeight()-height);
        xSpeed = random.nextInt(10)-5;
        ySpeed = random.nextInt(10)-5;
    }

    public void onDraw(Canvas canvas){
        update();
        int srcX = currentFrame*width;
        int srcY = getArrowAnimation()*height;
        Rect src = new Rect(srcX,srcY,srcX+width,srcY+height);
        Rect dst = new Rect(x,y,x+width,y+height);
        canvas.drawBitmap(bitmap,src,dst,null);
    }

    private void update(){
        if (x>=gameView.getWidth()-width-xSpeed||x+xSpeed<=0)
            xSpeed = -xSpeed;
        x = x+xSpeed;
        if (y>=gameView.getHeight()-height-ySpeed||y+ySpeed<=0)
            ySpeed = -ySpeed;
        y = y+ySpeed;
        currentFrame = ++currentFrame%BMP_COLUMN;
    }

    private int getArrowAnimation(){
        double dirDouble = (Math.atan2(xSpeed,ySpeed)/(Math.PI/2)+2);
        int direction = (int)Math.round(dirDouble)%BMP_ROWS;
        return DIRECTION_TO_ANIMATION_MAP[direction];
    }

    public synchronized boolean  isCollation(float x2, float y2) {
        Log.e("x2 event::"+x2,"y2 event ::"+y2);
//        if ((x2>=gameView.getWidth()-width)&&(x2<=gameView.getWidth()+width)){
//            if ((y2>=gameView.getHeight()-height)&&(y2<gameView.getHeight()+height)){
//                return true;
//            }else {
//                return true;
//            }
//        }else {
//            return false;
//        }

        return (x2 > x && x2 < (x + width)) && (y2 > y && y2 < (y + height));

    }


}

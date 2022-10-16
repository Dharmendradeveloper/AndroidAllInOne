package com.avisys.allinone.sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.avisys.allinone.surfaceview.MyGameSurfaceView;

public class Sprite {

    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 3;
    private int x = 0;
    private int y = 0;
    private int xSpeed = 5;
    private MyGameSurfaceView gameView;
    private Bitmap bmp;
    private int currentFrame = 0;

    private int width;
    private int height;

    public Sprite(MyGameSurfaceView gameView, Bitmap bmp) {
        this.gameView = gameView;
        this.bmp = bmp;
        this.width = bmp.getWidth()/BMP_COLUMNS; // BMP_COLUMNS;
        this.height = bmp.getHeight()/BMP_ROWS; // BMP_ROWS;
    }

    private void update() {
        Log.e("Sprite:: ","X value :"+x +" gameView.getWidth() :"+gameView.getWidth()+" bmp.getWidth() :"
                +bmp.getWidth()+" xSpeed:: "+xSpeed);
        if (x > gameView.getWidth() - width - xSpeed)// x = 823
            xSpeed = -5;
        if (x + xSpeed< 0)
            xSpeed = 5;

        x = x + xSpeed;
        currentFrame = ++currentFrame % BMP_COLUMNS;

    }

    public void onDraw(Canvas canvas) {
        update();
        int srcX = currentFrame * width;

        int srcY = 1 * height;

        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);

        Rect dst = new Rect(x, y, x + width, y + height);
//        canvas.drawBitmap(bmp, x , 10, null);
        canvas.drawBitmap(bmp, src , dst, null);

    }
}

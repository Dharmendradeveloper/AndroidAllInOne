package com.avisys.allinone.surfaceview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

import com.avisys.allinone.R;

public class GameView extends View {
    private Bitmap bitmap;

    public GameView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.marker_image);
        canvas.drawBitmap(bitmap,10,10,null);

    }
}

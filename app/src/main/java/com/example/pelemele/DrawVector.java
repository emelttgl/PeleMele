package com.example.pelemele;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class DrawVector extends View {

    protected Paint paint = new Paint();

    public DrawVector(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawLine(500,1000,500,500,paint);

    }
}

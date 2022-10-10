package com.example.pelemele;

import android.graphics.*;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


public class SelectActivity extends AppCompatActivity {

    protected ImageView imageView;
    protected SurfaceView surfaceView;
    protected SurfaceHolder surfaceHolder;
    protected DrawRectangle dr;
    protected int x0, y0, x1, y1;
    protected RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        imageView = findViewById(R.id.selectIV);
        surfaceView = findViewById(R.id.surfaceView);
        relativeLayout=findViewById(R.id.relative);

        surfaceHolder = surfaceView.getHolder();
        surfaceView.setZOrderOnTop(true);
        surfaceHolder.setFormat(PixelFormat.TRANSPARENT);


        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                relativeLayout.removeView(dr);
                if (event.getPointerCount() == 2) {
                    if (event.getActionIndex() == MotionEvent.ACTION_DOWN) {
                        x0 = (int) event.getX(0);
                        y0 = (int) event.getY(0);

                        Log.i("SelectActivity", "x0 : " + x0);
                        Log.i("SelectActivity", "y0 : " + y0);
                    }
                         x1 = (int) event.getX(1);
                        y1 = (int) event.getY(1);
                        Log.i("SelectActivity", "x1 : " + x1);
                        Log.i("SelectActivity", "y1 : " + y1);

                }
                //Log.i("SelectActivity", "rect " + rect.toString());
                Canvas canvas = new Canvas();
                Paint paint = new Paint();

                dr = new DrawRectangle(SelectActivity.this, paint, canvas, x0,y0,x1,y1);
                relativeLayout.addView(dr);
                    return true;
                }
        });
    }
}
package com.example.pelemele;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageActivity extends AppCompatActivity {

    protected ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imgView = (ImageView) findViewById(R.id.ImageV);
        FileInputStream fis = null;
        try {
            fis = openFileInput("image.data");
            Bitmap bm = BitmapFactory.decodeStream(fis);
            imgView.setImageBitmap(bm);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
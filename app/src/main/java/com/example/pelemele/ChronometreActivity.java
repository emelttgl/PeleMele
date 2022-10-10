package com.example.pelemele;


import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Calendar;


public class ChronometreActivity extends AppCompatActivity {

    protected ImageButton start;
    protected ImageButton stop;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometre);
        Toast.makeText(ChronometreActivity.this, "CHRONOMETRE", Toast.LENGTH_SHORT).show();
        Chronometer chronometer =  findViewById(R.id.chronometer);
        start =  findViewById(R.id.start);
        stop = findViewById(R.id.st);
        stop.setEnabled(false);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                Toast.makeText(ChronometreActivity.this,date,Toast.LENGTH_SHORT).show();
                chronometer.start();
                stop.setEnabled(true);
                start.setEnabled(false);

            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                Toast.makeText(ChronometreActivity.this,(SystemClock.elapsedRealtime()-chronometer.getBase())/1000+"s",Toast.LENGTH_SHORT).show();
                long elapsedRealtime = SystemClock.elapsedRealtime();
                chronometer.setBase(elapsedRealtime);
                start.setEnabled(true);
                stop.setEnabled(false);


            }
        });

            }

    }

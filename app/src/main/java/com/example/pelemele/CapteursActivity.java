package com.example.pelemele;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class CapteursActivity extends AppCompatActivity implements SensorEventListener {

    protected float[] gravity=new float[3];
    protected float[] magneto=new float[3];
    protected float[] orientation=new float[3];
    protected float[] matrix=new float[9];
    protected boolean acc=false;
    protected boolean magn=false;
    long time = 0;
    float degre = 0f;
    protected SensorManager sensorManager;
    protected TextView textViewON,textView,textView2;
    protected Sensor accelerometre,magnetometre;
    protected Switch button;
    protected RelativeLayout relativeLayout;
    protected ImageView imageView;
    boolean checked;
    protected DrawVector draw;
    protected RotateAnimation rotation ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_capteurs);

        button=findViewById(R.id.switch1);
        textView=findViewById(R.id.textView);
        textViewON=findViewById(R.id.textViewON);
        imageView=findViewById(R.id.imageBoussole);
        relativeLayout=findViewById(R.id.relativelayout);
        textView2=findViewById(R.id.textView2);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometre = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometre=sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        draw=new DrawVector(this);
        relativeLayout.addView(draw);
        draw.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.INVISIBLE);
        affichage();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked=((Switch) v).isChecked();
                if(checked && accelerometre!=null && magnetometre!=null) {
                    textViewON.setText("ON");
                    onResume();
                    draw.setVisibility(View.VISIBLE);
                    affichage();
                }else {
                    textViewON.setText("OFF");
                    draw.setVisibility(View.INVISIBLE);
                    onPause();
                    affichage();
                }

            }
        });

    }


    private void affichage() {
        if(checked){
            textView.setVisibility(View.VISIBLE);
            textView2.setVisibility(View.VISIBLE);
        }else{
            textView.setVisibility(View.INVISIBLE);
            textView2.setVisibility(View.INVISIBLE);

        }
    }


    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,accelerometre,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,magnetometre,SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (checked) {

            if (event.sensor == accelerometre) {

                System.arraycopy(event.values, 0, gravity, 0, event.values.length);
                acc = true;

                textView.setText("X: " + gravity[0] + "\n" + "Y: " + gravity[1] + "\n" + "Z: " + gravity[2]);
                //Toast.makeText(getApplicationContext(), String.valueOf(acceleration) + " " + SensorManager.GRAVITY_EARTH, Toast.LENGTH_SHORT).show();
            }
            if (event.sensor == magnetometre) {

                System.arraycopy(event.values, 0, magneto, 0, event.values.length);
                magn = true;

            }

            if (acc && magn && System.currentTimeMillis() - time > 250) {

                SensorManager.getRotationMatrix(matrix, null, gravity, magneto);
                SensorManager.getOrientation(matrix, orientation);

                float azimuth = orientation[0];
                float azimuthdegre = (float) Math.toDegrees(azimuth);

                RotateAnimation rotation = new RotateAnimation(degre, -azimuthdegre, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotation.setDuration(250);
                imageView.startAnimation(rotation);
                imageView.setRotation(110);

                degre = -azimuthdegre;
                time = System.currentTimeMillis();

                int x = (int) azimuthdegre;
                textView2.setText(x + "°");

            }



        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
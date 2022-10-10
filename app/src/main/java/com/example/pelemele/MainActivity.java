package com.example.pelemele;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.XmlRes;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.customview.widget.Openable;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

public class MainActivity extends AppCompatActivity {


    protected Button Bonjour;
    protected Button Photo;
    protected Button Chronometre;
    protected Button Image;
    protected Button MeteoAct;
    protected Button LongAct;
    protected Button Contatc;
    protected Button CapteurAcc;
    protected Button SelectionnerImage;



    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timer t = new Timer();

        Bonjour = findViewById(R.id.Bonjour);
        Photo =  findViewById(R.id.Photo);
        Chronometre =  findViewById(R.id.Chronometre);
        Image =  findViewById(R.id.Image);
        LongAct = findViewById(R.id.LongActivity);
        MeteoAct=findViewById(R.id.meteoActivity);
        Contatc =findViewById(R.id.contact);
        CapteurAcc=findViewById(R.id.capteur);
        SelectionnerImage=findViewById(R.id.tactile);

        Chronometre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChronometreActivity.class);
                startActivity(intent);

            }

        });

        SelectionnerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SelectActivity.class);
                startActivity(intent);
            }
        });

        Photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                launcher.launch(i);

            }
        });

        Contatc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                 startActivity(intent);

            }
        });
                Bonjour.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Bonjour", Toast.LENGTH_SHORT).show();

                    }
                });

        Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ImageActivity.class);
                startActivity(intent);

            }
        });

        Log.i("MainActivity", "une info"); //level info dans logcat


        LongAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LongActivity.class);
                startActivity(intent);

            }
        });

        MeteoAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MeteoActivity.class);
                startActivity(intent);

            }
        });
        CapteurAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CapteursActivity.class);
                startActivity(intent);

            }
        });


        launcher = registerForActivityResult(
                // Contrat qui détermine le type de l'interaction
                new ActivityResultContracts.StartActivityForResult(),
                // Callback appelé lorsque le résultat sera disponible
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                     Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                    Toast.makeText(MainActivity.this,"taille"+bitmap.getHeight() ,Toast.LENGTH_SHORT).show();
                        FileOutputStream fos = null;
                        try {
                            fos = openFileOutput("image.data", MODE_PRIVATE);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            fos.flush();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        int id = menuItem.getItemId();
        if(id == R.id.quitter){
            finish();
            return true;
        }
        else if (id == R.id.chrono){
                Intent intent = new Intent(MainActivity.this, ChronometreActivity.class);
                startActivity(intent);
        }
        return false;
    }


}





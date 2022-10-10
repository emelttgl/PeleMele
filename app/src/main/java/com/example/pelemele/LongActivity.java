package com.example.pelemele;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LongActivity extends AppCompatActivity {

    protected Button Lancer;
    protected ProgressBar progressBar;
    protected TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long);

        Lancer = (Button) findViewById(R.id.lancer);
        progressBar =(ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        textView=(TextView)findViewById(R.id.textV);

        Lancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExecutorService service = Executors.newSingleThreadExecutor();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        service.execute(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("debut tache");
                                try {
                                    Thread.sleep(10000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                System.out.println("fin tache");
                                textView.setText("FIN");
                                progressBar.setVisibility(View.INVISIBLE);
                            }

                        });
                        service.shutdown();
                        textView.setText("En cours d'execution");
                        System.out.println("Fin thread principal");
                        progressBar.setVisibility(View.VISIBLE);
                        Toast.makeText(LongActivity.this,"Tache en cours d'execution",Toast.LENGTH_SHORT).show();
                    }

                });

            }
        });
    }
}
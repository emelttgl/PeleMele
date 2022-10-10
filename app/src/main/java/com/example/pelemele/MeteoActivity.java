package com.example.pelemele;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;

public class MeteoActivity extends AppCompatActivity {


    protected Button LongLat;
    protected double longitude, latitude;
    protected TextView NomVille, meteoCondition, Temperature, tempMax, tempMin;
    protected ImageView IconMeteo;
    protected RelativeLayout NomDeLaVille;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteo);

        LongLat = findViewById(R.id.longLat);
        meteoCondition = findViewById(R.id.conditionTemps);
        NomVille = findViewById(R.id.nomVille);
        Temperature = findViewById(R.id.temperature);
        tempMax = findViewById(R.id.tempsMax);
        tempMin = findViewById(R.id.tempsMin);
        IconMeteo = findViewById(R.id.iconMeteo);
        NomDeLaVille = findViewById(R.id.nomV);


        Runnable runnable = () -> {
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, null).addOnSuccessListener(this, location -> {
                if (location != null) {
                    Log.i("MeteoActivity", "Location différent de null");
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                    ExecutorService service = Executors.newSingleThreadExecutor();
                    service.execute(() -> {
                        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&units=metric&appid=9aedc52bd825205c51c7a3297e4d212c";
                        try {
                            InputStream in = new java.net.URL(url).openStream();
                            JSONObject json = readStream(in);
                            JSONObject j = json.getJSONObject("main");
                            Temperature.setText(""+j.get("temp")+"°C");
                            NomVille.setText(""+json.get("name"));
                            tempMax.setText("Max : "+j.get("temp_max")+"°C");
                            tempMin.setText("Min : "+j.get("temp_min")+"°C");
                            meteoCondition.setText("Humidité "+j.get("humidity")+"%");
                        } catch (IOException | JSONException e) {
                            Log.i("MeteoActivity", "url marche pas");
                            throw new RuntimeException(e);
                        }
                    });

                    runOnUiThread(() -> {
                        Toast.makeText(MeteoActivity.this, "Longitude : " + longitude + " Latitude : " + latitude, Toast.LENGTH_SHORT).show();
                    });
                }
            });
        };
        ExecutorService service = Executors.newSingleThreadExecutor();
        LongLat.setOnClickListener(view -> service.execute(runnable));

    }


    private JSONObject readStream(InputStream is) throws IOException, JSONException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is), 1000);
        for (String line = r.readLine(); line != null; line = r.readLine()) {
            sb.append(line);
        }
        is.close();
        return new JSONObject(sb.toString());
    }
}


//https://api.openweathermap.org/data/2.5/weather?lat=37.4219983&lon=-122.084&appid=9aedc52bd825205c51c7a3297e4d212c
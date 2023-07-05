package com.example.androidupn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;     
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.androidupn.entities.Paisaje;
import com.example.androidupn.services.PaisajeService;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistroActivity extends AppCompatActivity {
    LocationManager locationManager;
    double latitud, longitud;
    ImageView image;
    String url;
    // VALORES ESTÁTICOS
    private static final int OPEN_CAMERA_REQUEST = 1001;
    private static final int OPEN_GALLERY_REQUEST = 1002;
    private static final int LOCATION_PERMISSION_REQUEST = 1003;
    private static final String urlFotoApi = "https://demo-upn.bit2bittest.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        image = findViewById(R.id.imgImageByCamera);

        Button btnOpenCamera = findViewById(R.id.btnOpenCamera);
        Button btnRegistro = findViewById(R.id.btnRegistro);

        EditText txtName = findViewById(R.id.txtName);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://63023858c6dda4f287b57c96.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PaisajeService servicio = retrofit.create(PaisajeService.class);

        btnOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOpenCamera();
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerCoordenadas();
            }
        });
    }

    // ALMACENAR COORDENADAS
    void obtenerCoordenadas() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    latitud = location.getLatitude();
                    longitud = location.getLongitude();
                    Log.i("MAIN_APP", "Latitud: " + latitud);
                    Log.i("MAIN_APP", "Longitud: " + longitud);

                    locationManager.removeUpdates(this);

                    // Realizar el registro del paisaje
                    registrarPaisaje();
                }
            };

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else {
            String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permissions, LOCATION_PERMISSION_REQUEST);
        }
    }

    void registrarPaisaje() {
        EditText txtName = findViewById(R.id.txtName);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://63023858c6dda4f287b57c96.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PaisajeService servicio = retrofit.create(PaisajeService.class);

        Paisaje paisaje = new Paisaje();
        paisaje.imageByCamera = url;
        paisaje.nombre = txtName.getText().toString();
        paisaje.coordLatitud = latitud;
        paisaje.coordLongitud = longitud;

        Call<Void> call = servicio.CrearPublicacion(paisaje);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i("MAIN_APP", "Se creó");
                    Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("MAIN_APP", "No sirve");
            }
        });

        Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
        startActivity(intent);
    }

    void handleOpenCamera() {
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            abrirCamara();
        } else {
            String[] permissions = new String[]{Manifest.permission.CAMERA};
            requestPermissions(permissions, OPEN_CAMERA_REQUEST);
        }
    }

    void abrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, OPEN_CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OPEN_CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            displayImage(imageBitmap);

            if (imageBitmap != null) {
                String imagenBase64 = convertirBitmapB64(imageBitmap);
                PaisajeService.ImageToSave imageToSave = new PaisajeService.ImageToSave(imagenBase64);

                enviarImageApi(imageToSave);
            }
        }
    }

    void enviarImageApi(PaisajeService.ImageToSave imageToSave) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlFotoApi)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PaisajeService service = retrofit.create(PaisajeService.class);

        Call<PaisajeService.ImageResponse> call = service.subirImagen(imageToSave);
        call.enqueue(new Callback<PaisajeService.ImageResponse>() {
            @Override
            public void onResponse(Call<PaisajeService.ImageResponse> call, Response<PaisajeService.ImageResponse> response) {
                if (response.isSuccessful()) {
                    url = urlFotoApi + response.body().getUrl();
                    Log.i("MAIN_APP", url);
                } else {
                    Log.i("MAIN_APP", "No se subió");
                }
            }

            @Override
            public void onFailure(Call<PaisajeService.ImageResponse> call, Throwable t) {
                Log.i("MAIN_APP", "La petición falló");
            }
        });
    }

    String convertirBitmapB64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private void displayImage(Bitmap bitmap) {
        ImageView image = findViewById(R.id.imgImageByCamera);
        RequestOptions requestOptions = new RequestOptions()
                .fitCenter()
                .override(image.getWidth(), image.getHeight());

        Glide.with(this)
                .load(bitmap)
                .apply(requestOptions)
                .into(image);
    }
}
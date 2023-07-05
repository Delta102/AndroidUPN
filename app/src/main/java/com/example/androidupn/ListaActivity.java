package com.example.androidupn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.androidupn.Adapters.PaisajeAdapter;
import com.example.androidupn.entities.Paisaje;
import com.example.androidupn.services.PaisajeService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        RecyclerView rvListaPaisajes = findViewById(R.id.rvListita);
        rvListaPaisajes.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://63023858c6dda4f287b57c96.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PaisajeService servicio = retrofit.create(PaisajeService.class);
        Call<List<Paisaje>> llamadoLista = servicio.ObtenerPaisajes();

        llamadoLista.enqueue(new Callback<List<Paisaje>>() {
            @Override
            public void onResponse(Call<List<Paisaje>> call, Response<List<Paisaje>> response) {
                List<Paisaje> listaPaisaje = response.body();
                PaisajeAdapter adapter = new PaisajeAdapter(listaPaisaje);
                rvListaPaisajes.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Paisaje>> call, Throwable t) {

            }
        });

    }
}
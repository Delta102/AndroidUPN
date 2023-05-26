package com.example.androidupn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.androidupn.Adapters.AnimeAdapter;
import com.example.androidupn.Clases.Anime;

import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        AnimeAdapter adapter = new AnimeAdapter(data());

        RecyclerView rvLista = findViewById(R.id.rvListita);
        rvLista.setLayoutManager(new LinearLayoutManager(this));
        rvLista.setAdapter(adapter);
    }

    private List<Anime> data(){
        List<Anime> anime = new ArrayList<>();

        Anime anime1 = new Anime("Dragon Ball", "Description Dragon Ball", "https://elcomercio.pe/resizer/agnne3tfHp7MsaZqVABZbMD8BJY=/1200x900/smart/filters:format(jpeg):quality(75)/cloudfront-us-east-1.images.arcpublishing.com/elcomercio/6FUBT6XQXNHHNFOMCHIT7I34NA.jpg", true);
        Anime anime2 = new Anime("Pokemon", "Description Pokemon", "https://cdn.vox-cdn.com/thumbor/f2bPL4CEtdsSLrGDxd4HKKKO6Gs=/0x0:1920x1080/1200x800/filters:focal(1035x326:1341x632)/cdn.vox-cdn.com/uploads/chorus_image/image/71812214/Ash_Ketchum_World_Champion_Screenshot_4.0.jpg", false);
        Anime anime3 = new Anime("Naruto", "Description Naruto", "https://www.mundodeportivo.com/alfabeta/hero/2021/03/naruto.1677590248.2134.jpg?width=1200", false);
        Anime anime4 = new Anime("Saint Seiya", "Description Saint Seiya", "https://i.pinimg.com/originals/a0/dc/bb/a0dcbb6b8d72c097f5a675f959a2d2c6.jpg", true);
        Anime anime5 = new Anime("Demon Slayer", "Description Demon Slayer", "https://regionps.com/wp-content/uploads/2020/03/Demon-Slayer-Kimetsu-no-Yaiba.jpg", true);
        anime.add(anime1);
        anime.add(anime2);
        anime.add(anime3);
        anime.add(anime4);
        anime.add(anime5);

        return anime;
    }
}
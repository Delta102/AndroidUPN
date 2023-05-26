package com.example.androidupn.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidupn.Clases.Anime;
import com.example.androidupn.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnimeAdapter extends RecyclerView.Adapter {

    List<Anime> items;

    public AnimeAdapter(List<Anime> items){
        this.items = items;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_anime, parent, false);

        AnimeViewHolder viewHolder = new AnimeViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Anime anime = items.get(position);
        View view = holder.itemView;

        TextView name = view.findViewById(R.id.txtName);
        TextView desc = view.findViewById(R.id.txtDescription);
        ImageView imageView = view.findViewById(R.id.imgFoto);
        ImageView imgEstrella = view.findViewById(R.id.imgEstrella);

        name.setText(anime.getName());
        desc.setText(anime.getDescripcion());

        String estrellaFav = "https://w7.pngwing.com/pngs/885/774/png-transparent-five-pointed-star-blackstar-black-star-logo-angle-triangle-symmetry-thumbnail.png";
        String estrellaNoFav = "https://i.pinimg.com/originals/df/54/8b/df548bc3ca6d3fc5164a4548cdaa3218.jpg";

        if(anime.getFavorito())
            Picasso.get().load(estrellaFav).into(imgEstrella);
        else
            Picasso.get().load(estrellaNoFav).into(imgEstrella);

        imgEstrella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!anime.getFavorito()) {
                    Picasso.get().load(estrellaFav).into(imgEstrella);
                    anime.setFavorito(true);
                }
                else {
                    Picasso.get().load(estrellaNoFav).into(imgEstrella);
                    anime.setFavorito(false);
                }
            }
        });

        Picasso.get().load(anime.getUrlFotito()).into(imageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class AnimeViewHolder extends RecyclerView.ViewHolder{
        public AnimeViewHolder(@NonNull View itemView){
            super(itemView);
        }
    }
}

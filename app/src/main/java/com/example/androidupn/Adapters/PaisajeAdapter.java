package com.example.androidupn.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidupn.MapsActivity;
import com.example.androidupn.R;
import com.example.androidupn.entities.Paisaje;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PaisajeAdapter extends RecyclerView.Adapter {

    private List<Paisaje> items;

    public PaisajeAdapter(List<Paisaje> items) {
        this.items = items;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_anime, parent, false);

        PaisajeViewHolder viewHolder = new PaisajeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //declaro variables
        Paisaje paisajes = items.get(position);
        View view = holder.itemView;

        TextView nombre = view.findViewById(R.id.txtName);
        ImageView foto = view.findViewById(R.id.imgFoto);

        Button btnOpenMap = view.findViewById(R.id.btnVerMapa);

        btnOpenMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(view.getContext(), MapsActivity.class);
                intent.putExtra("latitud", paisajes.coordLatitud);
                intent.putExtra("longitud", paisajes.coordLongitud);
                view.getContext().startActivity(intent);
            }
        });

        //mando los datos a las variable
        nombre.setText(paisajes.nombre);
        Picasso.get().load(paisajes.imageByCamera).into(foto);

    }
    @Override
    public int getItemCount() {
        return items.size();
    }
    public class PaisajeViewHolder extends RecyclerView.ViewHolder{
        public PaisajeViewHolder(@NonNull View itemView){
            super(itemView);
        }
    }
}

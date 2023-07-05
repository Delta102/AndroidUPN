package com.example.androidupn.services;

import com.example.androidupn.entities.Paisaje;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PaisajeService {
    @GET("Paisaje")
    Call<List<Paisaje>> ObtenerPaisajes();
    @POST("Paisaje")
    Call<Void> CrearPublicacion(@Body Paisaje paisaje);

    @POST("image")
    Call<ImageResponse> subirImagen(@Body ImageToSave image);

    class ImageResponse{
        @SerializedName("url")
        private String url;

        public String getUrl(){
            return url;
        }
    }

    class ImageToSave{
        String base64Image;

        public ImageToSave(String base64Image){
            this.base64Image = base64Image;
        }
    }
}

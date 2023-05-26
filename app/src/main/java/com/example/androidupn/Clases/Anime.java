package com.example.androidupn.Clases;

public class Anime {

    String name;
    String descripcion;
    String urlFotito;
    Boolean favorito;

    public Anime(){}

    public Anime(String name, String descripcion, String urlFotito, Boolean favorito) {
        this.name = name;
        this.descripcion = descripcion;
        this.urlFotito = urlFotito;
        this.favorito = favorito;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlFotito() {
        return urlFotito;
    }

    public void setUrlFotito(String urlFotito) {
        this.urlFotito = urlFotito;
    }

    public Boolean getFavorito() {
        return favorito;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }
}

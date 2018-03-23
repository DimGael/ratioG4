package com.android.unilim.ratiog4.ratiog4.sqlite.jeu;

import android.net.Uri;

//Pour l'instant on ne gère pas l'image du jeu
public class Jeu {

    private long id;
    private String nom;
    private Uri uri_image;


    public Jeu(long id, String nom, Uri uri) {
        this.id = id;
        this.nom = nom;
        this.uri_image = uri;
    }

    public Uri getUri_image() {
        return uri_image;
    }

    public void setUri_image(Uri uri_image) {
        this.uri_image = uri_image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString(){
        return "Jeu["+this.nom+"]";
    }
}

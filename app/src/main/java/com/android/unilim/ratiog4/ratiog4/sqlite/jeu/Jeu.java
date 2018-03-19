package com.android.unilim.ratiog4.ratiog4.sqlite.jeu;

//Pour l'instant on ne gère pas l'image du jeu
public class Jeu {

    private long id;
    private String nom;
    //Image


    public Jeu(long id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Jeu() {}

    public Jeu(String nom){
        this.nom=nom;
        this.id=-1;
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

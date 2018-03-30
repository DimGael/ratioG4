package com.android.unilim.ratiog4.ratiog4.sqlite.commentaire;

/**
 * Created by dimga on 30/03/2018.
 */

public class Commentaire {

    private long id;
    private String texte;

    public Commentaire(long id, String texte) {
        this.id = id;
        this.texte = texte;
    }

    public Commentaire(String texte) {
        this.id = -1;
        this.texte = texte;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

}

package com.android.unilim.ratiog4.ratiog4;

/**
 * Created by guigu on 13/03/2018.
 */

public class Ratio {
    private int ratioPourcentage;
    private int nbVictoire;
    private int nbDefaite;

    public int getNbDefaite() {
        return nbDefaite;
    }

    public Ratio(int nbVictoire, int nbDefaite){
        this.nbVictoire=nbVictoire;
        this.nbDefaite=nbDefaite;
        this.ratioPourcentage=100 *this.nbVictoire / (this.nbDefaite+this.nbVictoire);

    }

    public int getRatio(){
        return ratioPourcentage;
    }

    public int getNbVictoire() {
        return nbVictoire;
    }

    public void setNbVictoire(int nbVictoire) {
        this.nbVictoire = nbVictoire;
    }

    public void setNbDefaite(int nbDefaite) {
        this.nbDefaite = nbDefaite;
    }
}

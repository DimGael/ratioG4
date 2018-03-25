package com.android.unilim.ratiog4.ratiog4.sqlite.ratio;


import java.util.Date;
import java.util.GregorianCalendar;

public class Ratio {
    private int nbVictoire;
    private int nbDefaite;
    private long id;
    private Date date;
    private long idJeu;

    public int getNbDefaite() {
        return nbDefaite;
    }

    public Ratio(long id, int nbVictoire, int nbDefaite, Date date, long idJeu){
        this.nbVictoire=nbVictoire;
        this.nbDefaite=nbDefaite;
        this.date = date;
        this.idJeu = idJeu;
    }

    public Ratio(long id, int nbVictoire, int nbDefaite, long idJeu){
        this(id, nbVictoire, nbDefaite, GregorianCalendar.getInstance().getTime(), idJeu);
    }

    public double getRatio(){
        return (double)(100 *this.nbVictoire / (this.nbDefaite+this.nbVictoire));
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public Date getDate() {
        return this.date;
    }

    public long getIdJeu() {
        return idJeu;
    }

    public void setIdJeu(int idJeu) {
        this.idJeu = idJeu;
    }

    public void ajouterWin(){
        this.nbVictoire++;
    }

    public void ajouterLose(){
        this.nbDefaite++;
    }

    public void enleverWin(){
        if(nbVictoire > 0)
            nbVictoire--;
    }

    public void enleverLose(){
        if(nbDefaite > 0)
            nbDefaite--;
    }
}
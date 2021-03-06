package com.android.unilim.ratiog4.ratiog4.sqlite.ratio;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

public class Ratio {
    private long id;
    private final long idJeu;
    private String commentaire;

    private Calendar date;
    private int nbVictoire;
    private int nbDefaite;

    /**
     * Si enCours = vrai : l'utilisateur n'a pas encore enregistrer son ratio
     * Initialisé à true
     */
    private boolean enCours;

    public int getNbDefaite() {
        return nbDefaite;
    }


    public Ratio(long id, int nbVictoire, int nbDefaite, long idJeu){
        this(id, nbVictoire, nbDefaite, GregorianCalendar.getInstance(), idJeu);
    }

    public Ratio(long id, int nbVictoire, int nbDefaite, Calendar date, long idJeu){
        this.id = id;
        this.nbVictoire=nbVictoire;
        this.nbDefaite=nbDefaite;
        this.date = date;
        this.enCours = true;

        this.idJeu = idJeu;

        this.commentaire = "";
    }

    public boolean isEnCours() {
        return enCours;
    }

    public void finirEnregistrement(){
        this.enCours = false;
    }

    public double getRatio(){
        return (double)(100 *this.nbVictoire / (this.nbDefaite+this.nbVictoire));
    }

    public boolean aUnCommentaire(){
        return !this.commentaire.equals("");
    }

    public void setCommentaire(String commentaire){
        this.commentaire = commentaire;
    }

    public String getCommentaire() {
        return this.commentaire;
    }

    public int getNbVictoire() {
        return nbVictoire;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDate(Calendar date){
        this.date = date;
    }

    public Calendar getDate() {
        return this.date;
    }

    public long getIdJeu() {
        return idJeu;
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

    public void actualiserDate(){
        this.date = GregorianCalendar.getInstance();
    }

    public int getNbParties(){
        return this.nbDefaite + this.nbVictoire;
    }

    public String getPourcentage() {
        final double winrate = ((double)this.getNbVictoire()/(double)this.getNbParties())*100;
        String str = new Double(winrate).toString();


        String[] tab = str.split(Pattern.quote("."));
        if(winrate%1 > 0) {

            return tab[0] + ',' + tab[1].substring(0, 1)+"%";
        }

        return tab[0]+"%";
    }

    public boolean estPositif(){
        return this.getNbVictoire() >= this.getNbDefaite();
    }
}
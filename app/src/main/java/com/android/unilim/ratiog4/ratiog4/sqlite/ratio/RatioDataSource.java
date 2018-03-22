package com.android.unilim.ratiog4.ratiog4.sqlite.ratio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.android.unilim.ratiog4.ratiog4.sqlite.jeu.Jeu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RatioDataSource {

    private RatioSQLiteOpenHelper helper;
    private SQLiteDatabase database;
    private final String[] allColumns;


    public RatioDataSource(Context context) {
        this.allColumns = new String[]{
                RatioSQLiteOpenHelper.COLONNE_ID,
                RatioSQLiteOpenHelper.COLONNE_ID_JEU,
                RatioSQLiteOpenHelper.COLONNE_DATE,
                RatioSQLiteOpenHelper.COLONNE_NB_VICTOIRES,
                RatioSQLiteOpenHelper.COLONNE_NB_DEFAITES
        };

        helper = new RatioSQLiteOpenHelper(context);
    }

    public void open() throws SQLException{
        database = this.helper.getWritableDatabase();
    }

    public void close(){
        this.helper.close();
    }

    public List<Ratio> getAllJeux(){
        Cursor cursor = this.database.query(RatioSQLiteOpenHelper.TABLE_NOM, allColumns, null, null, null, null, null);

        List<Ratio> listeJeux = new ArrayList<Ratio>();
        while (cursor.moveToNext()) {
            listeJeux.add(creerRatio(cursor));
        }
        cursor.close();

        return listeJeux;
    }

    public void ajouterRatio(Ratio ratio, Jeu jeu){
        this.ajouterRatio(ratio, jeu.getId());
    }

    public void ajouterRatio(Ratio ratio, long idJeu){
        ContentValues values = new ContentValues();
        values.put(RatioSQLiteOpenHelper.COLONNE_DATE, ratio.getDate().toString());
        values.put(RatioSQLiteOpenHelper.COLONNE_ID_JEU, idJeu);
        values.put(RatioSQLiteOpenHelper.COLONNE_NB_VICTOIRES, ratio.getNbVictoire());
        values.put(RatioSQLiteOpenHelper.COLONNE_NB_DEFAITES, ratio.getNbDefaite());

        long id = this.database.insert(RatioSQLiteOpenHelper.TABLE_NOM, null, values);}

    public void supprimerJeu(Ratio ratio){
        this.supprimerJeu(ratio.getId());
    }

    public void supprimerJeu(long id) {
        this.database.delete(RatioSQLiteOpenHelper.TABLE_NOM, RatioSQLiteOpenHelper.COLONNE_ID +" = "+id, null);
    }

    private Ratio creerRatio(Cursor cursor) {
       return new Ratio(cursor.getInt(RatioSQLiteOpenHelper.NUM_COLONNE_ID),
                cursor.getInt(RatioSQLiteOpenHelper.NUM_COLONNE_NB_VICTOIRES),
                cursor.getInt(RatioSQLiteOpenHelper.NUM_COLONNE_NB_DEFAITES),
                new Date(),
                cursor.getInt(RatioSQLiteOpenHelper.NUM_COLONNE_ID_JEU)
       );
    }

    public void supprimerAllRatio(){
        this.database.delete(RatioSQLiteOpenHelper.TABLE_NOM, null, null);
    }

    //Retourne un Objet à partir du String date
    private Date recupDate(String date){
        return null;
    }

    //Retourne la date en String à partir de l'objet date
    private String recupDateString(Date date){
        return null;
    }
}

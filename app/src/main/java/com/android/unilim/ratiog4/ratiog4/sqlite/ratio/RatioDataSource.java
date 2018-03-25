package com.android.unilim.ratiog4.ratiog4.sqlite.ratio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.unilim.ratiog4.ratiog4.sqlite.jeu.Jeu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

    public List<Ratio> getAllRatio(){
        Cursor cursor = this.database.query(RatioSQLiteOpenHelper.TABLE_NOM, allColumns, null, null, null, null, null);

        List<Ratio> listeRatio = new ArrayList<Ratio>();
        while (cursor.moveToNext()) {
            listeRatio.add(creerRatio(cursor));
        }
        cursor.close();

        return listeRatio;
    }

    public long ajouterRatio(Ratio ratio){
        return this.ajouterRatio(ratio, ratio.getIdJeu());
    }

    public long ajouterRatio(Ratio ratio, long idJeu){
        ContentValues values = new ContentValues();

        values.put(RatioSQLiteOpenHelper.COLONNE_DATE, ratio.getDate().toString());
        values.put(RatioSQLiteOpenHelper.COLONNE_ID_JEU, idJeu);
        values.put(RatioSQLiteOpenHelper.COLONNE_NB_VICTOIRES, ratio.getNbVictoire());
        values.put(RatioSQLiteOpenHelper.COLONNE_NB_DEFAITES, ratio.getNbDefaite());

        return this.database.insert(RatioSQLiteOpenHelper.TABLE_NOM, null, values);
    }

    public void supprimerJeu(Ratio ratio){
        this.supprimerJeu(ratio.getId());
    }

    public void supprimerJeu(long id) {
        this.database.delete(RatioSQLiteOpenHelper.TABLE_NOM, RatioSQLiteOpenHelper.COLONNE_ID +" = "+id, null);
    }

    private Ratio creerRatio(Cursor cursor) {
       return new Ratio(cursor.getInt(cursor.getColumnIndex(RatioSQLiteOpenHelper.COLONNE_ID)),
                cursor.getInt(cursor.getColumnIndex(RatioSQLiteOpenHelper.COLONNE_NB_VICTOIRES)),
                cursor.getInt(cursor.getColumnIndex(RatioSQLiteOpenHelper.COLONNE_NB_DEFAITES)),
                recupDate(cursor.getString(cursor.getColumnIndex(RatioSQLiteOpenHelper.COLONNE_DATE))),
                cursor.getInt(cursor.getColumnIndex(RatioSQLiteOpenHelper.COLONNE_ID_JEU))
       );
    }

    public void supprimerAllRatio(){
        this.database.delete(RatioSQLiteOpenHelper.TABLE_NOM, null, null);
    }

    public long modifier(Ratio ratio){
        ContentValues values = new ContentValues();

        values.put(RatioSQLiteOpenHelper.COLONNE_NB_DEFAITES, ratio.getNbDefaite());
        values.put(RatioSQLiteOpenHelper.COLONNE_NB_VICTOIRES, ratio.getNbVictoire());
        values.put(RatioSQLiteOpenHelper.COLONNE_DATE, ratio.getDate().toString());

        return this.database.update(RatioSQLiteOpenHelper.TABLE_NOM, values, RatioSQLiteOpenHelper.COLONNE_ID+" = "+ratio.getId(), null);
    }

    //Retourne un Objet à partir du String date
    private Date recupDate(String str){
        Calendar calendar = GregorianCalendar.getInstance();
        final int year = Integer.parseInt(str.split(" ")[5]);
        final int day = Integer.parseInt(str.split(" ")[2]);

        final String[] hms = str.split(" ")[3].split(":");

        final String[] months = new String[] {
                "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        };

        final String month = str.split(" ")[1];
        int monthNumber = -1;

        for(int i = 0; i<months.length; i++){
            if(month.equals(months[i]))
                monthNumber = i;
        }

        calendar.set(year, monthNumber, day, Integer.parseInt(hms[0]), Integer.parseInt(hms[1]), Integer.parseInt(hms[2]));

        return calendar.getTime();
    }
}

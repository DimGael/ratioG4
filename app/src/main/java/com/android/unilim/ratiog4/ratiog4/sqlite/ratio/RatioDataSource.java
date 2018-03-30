package com.android.unilim.ratiog4.ratiog4.sqlite.ratio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.android.unilim.ratiog4.ratiog4.sqlite.DatabaseOpenHelper;
import com.android.unilim.ratiog4.ratiog4.sqlite.jeu.JeuDataSource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class RatioDataSource {

    public static final String TABLE_NOM = "table_ratio";
    public static final String COLONNE_ID = "_idRatio";
    public static final String COLONNE_ID_JEU = JeuDataSource.COLONNE_ID;
    //public static final String COLONNE_ID_COMMENTAIRE = "a rajouter";
    public static final String COLONNE_DATE = "date";
    public static final String COLONNE_NB_VICTOIRES = "nbVictoires";
    public static final String COLONNE_NB_DEFAITES = "nbDefaites";

    public static final String TABLE_CREATE = "create table "+ TABLE_NOM +" ("
            +COLONNE_ID+" integer primary key autoincrement,"
            +COLONNE_DATE+" text not null,"
            +COLONNE_NB_VICTOIRES+" integer,"
            +COLONNE_NB_DEFAITES+"integer,"
            +COLONNE_ID_JEU + " integer"
            +",FOREIGN KEY ("+COLONNE_ID_JEU+") references "+JeuDataSource.TABLE_JEU+"("+JeuDataSource.COLONNE_ID+")"
            +"); ";

    private DatabaseOpenHelper helper;
    private SQLiteDatabase database;
    private final String[] allColumns;


    public RatioDataSource(Context context) {
        this.allColumns = new String[]{
                COLONNE_ID,
                COLONNE_ID_JEU,
                COLONNE_DATE,
                COLONNE_NB_VICTOIRES,
                COLONNE_NB_DEFAITES
        };

        helper = new DatabaseOpenHelper(context);
    }

    public void open() throws SQLException{
        database = this.helper.getWritableDatabase();
    }

    public void close(){
        this.helper.close();
    }

    public List<Ratio> getAllRatio(){
        Cursor cursor = this.database.query(TABLE_NOM, allColumns, null, null, null, null, null);

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

        values.put(COLONNE_DATE, ratio.getDate().toString());
        values.put(COLONNE_ID_JEU, idJeu);
        values.put(COLONNE_NB_VICTOIRES, ratio.getNbVictoire());
        values.put(COLONNE_NB_DEFAITES, ratio.getNbDefaite());

        return this.database.insert(TABLE_NOM, null, values);
    }

    public void supprimerJeu(Ratio ratio){
        this.supprimerJeu(ratio.getId());
    }

    public void supprimerJeu(long id) {
        this.database.delete(TABLE_NOM, COLONNE_ID +" = "+id, null);
    }

    private Ratio creerRatio(Cursor cursor) {
       return new Ratio(cursor.getInt(cursor.getColumnIndex(COLONNE_ID)),
                cursor.getInt(cursor.getColumnIndex(COLONNE_NB_VICTOIRES)),
                cursor.getInt(cursor.getColumnIndex(COLONNE_NB_DEFAITES)),
                recupDate(cursor.getString(cursor.getColumnIndex(COLONNE_DATE))),
                cursor.getInt(cursor.getColumnIndex(COLONNE_ID_JEU))
       );
    }

    public void supprimerAllRatio(){
        this.database.delete(TABLE_NOM, null, null);
    }

    public long modifier(Ratio ratio){
        ContentValues values = new ContentValues();

        values.put(COLONNE_NB_DEFAITES, ratio.getNbDefaite());
        values.put(COLONNE_NB_VICTOIRES, ratio.getNbVictoire());
        values.put(COLONNE_DATE, ratio.getDate().toString());

        return this.database.update(TABLE_NOM, values, COLONNE_ID+" = "+ratio.getId(), null);
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

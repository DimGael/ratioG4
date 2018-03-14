package com.android.unilim.ratiog4.ratiog4.sqlite.jeu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class JeuDataSource {

    private JeuxSQLiteOpenHelper helper;
    private SQLiteDatabase database;
    private final String[] allColumns;


    public JeuDataSource(Context context) {
        this.allColumns = new String[]{
                JeuxSQLiteOpenHelper.COLONNE_ID,
                JeuxSQLiteOpenHelper.COLONNE_NOM
        };

        helper = new JeuxSQLiteOpenHelper(context);
    }

    public void open() throws SQLException{
        database = this.helper.getWritableDatabase();
    }

    public void close(){
        this.helper.close();
    }

    public List<Jeu> getAllJeux(){
        Cursor cursor = this.database.query(JeuxSQLiteOpenHelper.TABLE_JEU, allColumns, null, null, null, null, null);

        List<Jeu> listeJeux = new ArrayList<Jeu>();
        while (cursor.moveToNext()) {
            listeJeux.add(creerJeu(cursor));
        }
        cursor.close();

        return listeJeux;
    }

    public Jeu ajouterJeu(Jeu jeu){
        ContentValues values = new ContentValues();
        values.put(JeuxSQLiteOpenHelper.COLONNE_NOM, jeu.getNom());

        long id = this.database.insert(JeuxSQLiteOpenHelper.TABLE_JEU, JeuxSQLiteOpenHelper.COLONNE_NOM, values);

        Cursor cursor = this.database.query(JeuxSQLiteOpenHelper.TABLE_JEU, allColumns, JeuxSQLiteOpenHelper.COLONNE_ID +" = "+id, null, null, null, null);

        Jeu jeuAjoute = new Jeu();

        if(cursor.moveToFirst()){
            jeuAjoute = creerJeu(cursor);
        }

        return jeuAjoute;
    }

    public void supprimerJeu(Jeu jeu){
        this.supprimerJeu(jeu.getId());
    }

    public void supprimerJeu(long id) {
        this.database.delete(JeuxSQLiteOpenHelper.TABLE_JEU, JeuxSQLiteOpenHelper.COLONNE_ID +" = "+id, null);
    }

    private Jeu creerJeu(Cursor cursor) {
       return new Jeu(cursor.getInt(JeuxSQLiteOpenHelper.NUM_COLONNE_ID),
                cursor.getString(JeuxSQLiteOpenHelper.NUM_COLONNE_NOM));
    }

    public void supprimerAllJeux(){
        this.database.delete(JeuxSQLiteOpenHelper.TABLE_JEU, null, null);
    }
}

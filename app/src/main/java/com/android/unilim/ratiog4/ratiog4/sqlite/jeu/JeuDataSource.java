package com.android.unilim.ratiog4.ratiog4.sqlite.jeu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class JeuDataSource {

    private JeuxSQLiteOpenHelper helper;
    private SQLiteDatabase database;
    private final String[] allColumns;


    public JeuDataSource(Context context) {
        this.allColumns = new String[]{
                JeuxSQLiteOpenHelper.COLONNE_ID,
                JeuxSQLiteOpenHelper.COLONNE_NOM,
                JeuxSQLiteOpenHelper.COLONNE_IMG
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

    public Jeu getJeu(long id){
        Cursor cursor = this.database.query(JeuxSQLiteOpenHelper.TABLE_JEU, allColumns, JeuxSQLiteOpenHelper.COLONNE_ID +" = "+id, null, null, null, null);

        Jeu jeu = null;
        if(cursor.moveToFirst())
            jeu = creerJeu(cursor);

        cursor.close();

        return jeu;
    }

    public long ajouterJeu(Jeu jeu) {
        ContentValues values = new ContentValues();
        values.put(JeuxSQLiteOpenHelper.COLONNE_NOM, jeu.getNom());
        values.put(JeuxSQLiteOpenHelper.COLONNE_IMG, jeu.getUri_image().toString());

        return this.database.insert(JeuxSQLiteOpenHelper.TABLE_JEU, JeuxSQLiteOpenHelper.COLONNE_NOM, values);
    }

    public void supprimerJeu(Jeu jeu){
        this.supprimerJeu(jeu.getId());
    }

    public void supprimerJeu(long id) {
        this.database.delete(JeuxSQLiteOpenHelper.TABLE_JEU, JeuxSQLiteOpenHelper.COLONNE_ID +" = "+id, null);
    }

    private Jeu creerJeu(Cursor cursor) {
       return new Jeu(cursor.getInt(JeuxSQLiteOpenHelper.NUM_COLONNE_ID),
                cursor.getString(JeuxSQLiteOpenHelper.NUM_COLONNE_NOM),
               Uri.parse(cursor.getString(cursor.getColumnIndex(JeuxSQLiteOpenHelper.COLONNE_IMG)))
       );
    }

    public void supprimerAllJeux(){
        this.database.delete(JeuxSQLiteOpenHelper.TABLE_JEU, null, null);
    }

}

package com.android.unilim.ratiog4.ratiog4.sqlite.jeu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.android.unilim.ratiog4.ratiog4.sqlite.DatabaseOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class JeuDataSource {

    public static final String TABLE_JEU = "jeux";
    public static final String COLONNE_ID = "_idJeu";
    public static final String COLONNE_NOM = "nomJeu";
    public static final String COLONNE_IMG = "uri";

    public static final String TABLE_CREATE = "create table "+ TABLE_JEU +" ("
            + COLONNE_ID +" integer primary key autoincrement,"
            + COLONNE_NOM +" text not null," +
            COLONNE_IMG + " text);";

    private DatabaseOpenHelper helper;
    private SQLiteDatabase database;
    private final String[] allColumns;


    public JeuDataSource(Context context) {
        this.allColumns = new String[]{
                COLONNE_ID,
                COLONNE_NOM,
                COLONNE_IMG
        };

        helper = new DatabaseOpenHelper(context);
    }

    public void open() throws SQLException{
        database = this.helper.getWritableDatabase();
    }

    public void close(){
        this.helper.close();
    }

    public List<Jeu> getAllJeux(){
        Cursor cursor = this.database.query(TABLE_JEU, allColumns, null, null, null, null, null);

        List<Jeu> listeJeux = new ArrayList<Jeu>();
        while (cursor.moveToNext()) {
            listeJeux.add(creerJeu(cursor));
        }
        cursor.close();

        return listeJeux;
    }

    public Jeu getJeu(long id){
        Cursor cursor = this.database.query(TABLE_JEU, allColumns, COLONNE_ID +" = "+id, null, null, null, null);

        Jeu jeu = null;
        if(cursor.moveToFirst())
            jeu = creerJeu(cursor);

        cursor.close();

        return jeu;
    }

    public long ajouterJeu(Jeu jeu) {
        ContentValues values = new ContentValues();
        values.put(COLONNE_NOM, jeu.getNom());
        values.put(COLONNE_IMG, jeu.getUri_image().toString());

        return this.database.insert(TABLE_JEU, COLONNE_NOM, values);
    }

    public void supprimerJeu(Jeu jeu){
        this.supprimerJeu(jeu.getId());
    }

    public void supprimerJeu(long id) {
        this.database.delete(TABLE_JEU, COLONNE_ID +" = "+id, null);
    }

    private Jeu creerJeu(Cursor cursor) {
       return new Jeu(cursor.getInt(cursor.getColumnIndex(COLONNE_ID)),
                cursor.getString(cursor.getColumnIndex(COLONNE_NOM)),
               Uri.parse(cursor.getString(cursor.getColumnIndex(COLONNE_IMG)))
       );
    }

    public void supprimerAllJeux(){
        this.database.delete(TABLE_JEU, null, null);
    }

}

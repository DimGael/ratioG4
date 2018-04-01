package com.android.unilim.ratiog4.ratiog4.sqlite.commentaire;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.android.unilim.ratiog4.ratiog4.sqlite.DatabaseOpenHelper;

/**
 * Created by dimga on 30/03/2018.
 */

public class CommentaireDataSource {

    public static final String TABLE_COMMENTAIRES = "commentaires";

    public static final String COLONNE_ID = "_idCommentaire";
    public static final String COLONNE_TEXTE = "text";

    public static final String TABLE_CREATE = "create table "+ TABLE_COMMENTAIRES +" ("
            + COLONNE_ID +" integer primary key autoincrement,"
            + COLONNE_TEXTE + " text);";

    private DatabaseOpenHelper helper;
    private SQLiteDatabase database;
    private final String[] allColumns;

    public CommentaireDataSource(Context context) {
        this.allColumns = new String[]{
                COLONNE_ID,
                COLONNE_TEXTE
        };

        helper = new DatabaseOpenHelper(context);
    }

    public void open() throws SQLException {
        database = this.helper.getWritableDatabase();
    }

    public void close(){
        this.helper.close();
    }

    public Commentaire getCommentaire(long id) {
        Cursor cursor = database.query(TABLE_COMMENTAIRES, allColumns, COLONNE_ID +" = "+id, null, null, null, null);

        cursor.moveToFirst();
        Commentaire commentaire = creerCommentaire(cursor);
        cursor.close();

        return commentaire;
    }

    public int supprimerCommentaire(Commentaire commentaire){
        return this.supprimerCommentaire(commentaire.getId());
    }

    public int supprimerCommentaire(long id) {
        return database.delete(TABLE_COMMENTAIRES, COLONNE_ID+ " = "+id, null);
    }

    private Commentaire creerCommentaire(Cursor cursor) {
        return new Commentaire(
                cursor.getLong(cursor.getColumnIndex(COLONNE_ID)),
                cursor.getString(cursor.getColumnIndex(COLONNE_TEXTE))
        );
    }
}

package com.android.unilim.ratiog4.ratiog4.sqlite.jeu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by dimga on 13/03/2018.
 */

public class JeuxSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String TABLE_JEU = "jeux";

    public static final String COLONNE_ID = "_idJeu";
    public static final String COLONNE_NOM = "nomJeu";
    public static final String COLONNE_IMG = "uri";

    public static final int NUM_COLONNE_ID = 0;
    public static final int NUM_COLONNE_NOM = 1;

    private static final String TABLE_CREATE = "create table "+ TABLE_JEU +" ("
            +COLONNE_ID+" integer primary key autoincrement,"
            + COLONNE_NOM +" text not null," +
            COLONNE_IMG+ " text)";

    public JeuxSQLiteOpenHelper(Context context) {
        super(context, "ratiog4.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(this.TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){
        Log.w(JeuxSQLiteOpenHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_JEU);

        onCreate(sqLiteDatabase);
    }
}

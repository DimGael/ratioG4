package com.android.unilim.ratiog4.ratiog4.sqlite.ratio;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.unilim.ratiog4.ratiog4.sqlite.jeu.JeuxSQLiteOpenHelper;

/**
 * Created by dimga on 13/03/2018.
 */

public class RatioSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String TABLE_NOM = "table_ratio";

    public static final String COLONNE_ID = "_idRatio";
    public static final String COLONNE_ID_JEU = JeuxSQLiteOpenHelper.COLONNE_ID;
    //public static final String COLONNE_ID_COMMENTAIRE = "a rajouter";
    public static final String COLONNE_DATE = "date";
    public static final String COLONNE_NB_VICTOIRES = "nbVictoires";
    public static final String COLONNE_NB_DEFAITES = "nbDefaites";


    private static final String TABLE_CREATE = "create table "+ TABLE_NOM +" ("
            +COLONNE_ID+" integer primary key autoincrement,"
            +COLONNE_DATE+" text not null,"
            +COLONNE_NB_VICTOIRES+" integer,"
            +COLONNE_NB_DEFAITES+"integer,"
            +COLONNE_ID_JEU + " integer"
            //+",FOREIGN KEY ("+COLONNE_ID_JEU+") references "+JeuxSQLiteOpenHelper.TABLE_JEU+"("+JeuxSQLiteOpenHelper.COLONNE_ID
            +"); ";

    public RatioSQLiteOpenHelper(Context context) {
        super(context, "ratiog4ratio.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(this.TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){
        Log.w(RatioSQLiteOpenHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NOM);

        onCreate(sqLiteDatabase);
    }
}

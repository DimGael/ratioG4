package com.android.unilim.ratiog4.ratiog4.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.unilim.ratiog4.ratiog4.sqlite.commentaire.CommentaireDataSource;
import com.android.unilim.ratiog4.ratiog4.sqlite.jeu.JeuDataSource;
import com.android.unilim.ratiog4.ratiog4.sqlite.ratio.RatioDataSource;

/**
 * Created by dimga on 30/03/2018.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "ratiog4.db";

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(JeuDataSource.TABLE_CREATE);
        sqLiteDatabase.execSQL(RatioDataSource.TABLE_CREATE);
        sqLiteDatabase.execSQL(CommentaireDataSource.TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){
        Log.w(DatabaseOpenHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ JeuDataSource.TABLE_JEU);

        onCreate(sqLiteDatabase);
    }
}

package com.android.unilim.ratiog4.ratiog4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.unilim.ratiog4.ratiog4.sqlite.jeu.Jeu;
import com.android.unilim.ratiog4.ratiog4.sqlite.jeu.JeuDataSource;

/**
 * Created by dimga on 14/03/2018.
 */

public class MainActivity extends AppCompatActivity {

    private JeuDataSource jeuDataSource;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        jeuDataSource = new JeuDataSource(this);
        jeuDataSource.open();

        Jeu jeu1 = new Jeu();
        jeu1.setNom("Fortnite");

        Jeu jeu2 = new Jeu();
        jeu2.setNom("COUCOU");

        jeuDataSource.ajouterJeu(jeu1);
        jeuDataSource.ajouterJeu(jeu2);

        for(Jeu jeu : jeuDataSource.getAllJeux()){
            Log.d("Affichage jeu", jeu.toString());
        }


    }

    @Override
    public void onPause(){
        super.onPause();
        this.jeuDataSource.close();
    }

    @Override
    public void onResume(){
        super.onResume();
        this.jeuDataSource.open();
    }
}

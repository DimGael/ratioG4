package com.android.unilim.ratiog4.ratiog4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.android.unilim.ratiog4.ratiog4.sqlite.jeu.Jeu;
import com.android.unilim.ratiog4.ratiog4.sqlite.jeu.JeuDataSource;

import java.util.List;

/**
 * Created by dimga on 14/03/2018.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private JeuDataSource jeuDataSource;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        jeuDataSource = new JeuDataSource(this);
        jeuDataSource.open();
        List<Jeu> jeux = this.jeuDataSource.getAllJeux();

        final ListView viewJeux = (ListView)findViewById(R.id.listViewJeux);
        viewJeux.setAdapter(new JeuAdapter(this, jeux));

        final Button bouton_ajouter_jeu = (Button)findViewById(R.id.bouton_ajouter_jeu);
        bouton_ajouter_jeu.setOnClickListener(this);
    }

    @Override
    public void onPause(){
        super.onPause();
        this.jeuDataSource.close();
    }

    @Override
    public void onResume(){

        this.jeuDataSource.open();

        List<Jeu> jeux = this.jeuDataSource.getAllJeux();

        final ListView viewJeux = (ListView)findViewById(R.id.listViewJeux);
        viewJeux.setAdapter(new JeuAdapter(this, jeux));
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bouton_ajouter_jeu :
                final Intent intentAjouterJeu = new Intent(MainActivity.this, AjouterJeuActivity.class);
                this.startActivity(intentAjouterJeu);
                break;
        }
    }
}

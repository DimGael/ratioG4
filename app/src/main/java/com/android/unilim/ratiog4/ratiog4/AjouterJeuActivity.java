package com.android.unilim.ratiog4.ratiog4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.unilim.ratiog4.ratiog4.sqlite.jeu.Jeu;
import com.android.unilim.ratiog4.ratiog4.sqlite.jeu.JeuDataSource;

import java.util.List;

/**
 * Created by dimga on 14/03/2018.
 */

public class AjouterJeuActivity extends AppCompatActivity implements View.OnClickListener{

    private JeuDataSource jeuDataSource;
    private EditText ajouterJeu;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_ajouter_jeu);

        jeuDataSource = new JeuDataSource(this);
        jeuDataSource.open();




        final Button insertion = (Button)findViewById(R.id.insertion);
        ajouterJeu =(EditText)findViewById(R.id.ajouter_jeu);
        insertion.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        if(this.ajouterJeu.getText().toString().equals("") || this.ajouterJeu.getText()==null){
            Toast.makeText(this, "jeu vide", Toast.LENGTH_SHORT).show();
        }
        else{
            jeuDataSource.ajouterJeu(new Jeu(this.ajouterJeu.getText().toString()));
            // REDIRECTION VERS PAGE PRINCIPALE A FAIRE
        }
    }
}

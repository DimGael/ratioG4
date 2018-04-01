package com.android.unilim.ratiog4.ratiog4;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.unilim.ratiog4.ratiog4.sqlite.jeu.Jeu;
import com.android.unilim.ratiog4.ratiog4.sqlite.jeu.JeuDataSource;
import com.android.unilim.ratiog4.ratiog4.sqlite.ratio.RatioDataSource;

/**
 * Created by Gael on 22/03/2018.
 */

public class JeuActivity extends AppCompatActivity implements View.OnClickListener {


    public static final String KEY_ID_JEU = "idjeu";

    private JeuDataSource jeuDataSource;
    private RatioDataSource ratioDataSource;

    private Button boutonRatio;

    /**
     * Jeu séléctionné précédement dans Main Activity
     */
    private Jeu jeu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_jeu);

        jeuDataSource = new JeuDataSource(this);
        ratioDataSource = new RatioDataSource(this);
        ratioDataSource.open();
        jeuDataSource.open();

        final Intent intent = getIntent();

        this.jeu = jeuDataSource.getJeu(intent.getLongExtra(KEY_ID_JEU, -1));

        if(jeu == null) {
            Toast.makeText(this, "Jeu inexistant", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            final TextView tv_titreJeu = (TextView)findViewById(R.id.nomJeu);
            tv_titreJeu.setText(jeu.getNom());
        }

        this.boutonRatio = (Button)findViewById(R.id.bouton_creer_ratio);
        checkRatioEnCours();

        setListener();
    }

    private void checkRatioEnCours() {
        if(this.ratioDataSource.aUnRatioEnCoursJeu(this.jeu.getId())) {
            this.boutonRatio.setText("RATIO EN COURS");
            this.boutonRatio.setTextColor(Color.GREEN);
            this.boutonRatio.setBackgroundColor(Color.BLACK);
        }
        else {
            this.boutonRatio.setText("NOUVEAU RATIO");
            this.boutonRatio.setTextColor(Color.BLACK);
            this.boutonRatio.setBackgroundColor(getResources().getColor(R.color.colorBoutonJeuActivity));
        }
    }

    private void setListener() {
        this.boutonRatio.setOnClickListener(this);
        ((Button)findViewById(R.id.bouton_stats)).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.bouton_creer_ratio :
                final Intent intent = new Intent(this, RatioActivity.class);
                intent.putExtra(KEY_ID_JEU, this.jeu.getId());
                startActivity(intent);
                break;

            case R.id.bouton_stats :
                break;
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        this.jeuDataSource.close();
        this.ratioDataSource.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.jeuDataSource.open();
        this.ratioDataSource.open();
        checkRatioEnCours();
    }
}

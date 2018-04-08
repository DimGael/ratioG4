package com.android.unilim.ratiog4.ratiog4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.unilim.ratiog4.ratiog4.sqlite.jeu.Jeu;
import com.android.unilim.ratiog4.ratiog4.sqlite.jeu.JeuDataSource;
import com.android.unilim.ratiog4.ratiog4.sqlite.ratio.Ratio;
import com.android.unilim.ratiog4.ratiog4.sqlite.ratio.RatioDataSource;

import java.util.List;

public class StatActivity extends AppCompatActivity {

    /**
     * Le jeu sur lequel on va calculer et afficher les statistiques
     */
    private Jeu jeu;
    private List<Ratio> ratiosJeu;

    private JeuDataSource jeuDataSource;
    private RatioDataSource ratioDataSource;

    private ListRatioAnalyzer ratioAnalyzer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        jeuDataSource = new JeuDataSource(this);
        this.jeuDataSource.open();

        ratioDataSource = new RatioDataSource(this);
        this.ratioDataSource.open();

        final Intent intent = getIntent();
        final long idjeu = intent.getLongExtra(JeuActivity.KEY_ID_JEU, -1);

        //Si aucun id de jeu n'a été envoyé
        if(idjeu == -1){
            Toast.makeText(this, "Erreur lors de la selection du jeu", Toast.LENGTH_SHORT).show();
            finish();
        }

        this.jeu = this.jeuDataSource.getJeu(idjeu);
        this.ratiosJeu = this.ratioDataSource.getAllRatioJeu(jeu.getId());

        this.ratioAnalyzer = new ListRatioAnalyzer(this.ratiosJeu);

        //Affichage du titre du jeu
        ((TextView)findViewById(R.id.stats_titre_jeu)).setText(this.jeu.getNom());

        //Affichage de l'image du jeu
        ((ImageView)findViewById(R.id.stats_image_jeu)).setImageURI(this.jeu.getUri_image());

        //Affichage du nombre de victoires et de défaites
        //((TextView)findViewById(R.id.stats_total_wins)).setText(ratioAnalyzer.getNbWins());
        //((TextView)findViewById(R.id.stats_total_lose)).setText(ratioAnalyzer.getNbLoses());

        //Affichage du pourcentages de win
        ((TextView)findViewById(R.id.stats_pourcentage)).setText(ratioAnalyzer.getWinRate());
    }

    @Override
    public void onPause(){
        super.onPause();
        this.jeuDataSource.close();
        this.ratioDataSource.close();
    }

    @Override
    public void onResume(){
        super.onResume();
        this.jeuDataSource.open();
        this.ratioDataSource.open();
    }

}

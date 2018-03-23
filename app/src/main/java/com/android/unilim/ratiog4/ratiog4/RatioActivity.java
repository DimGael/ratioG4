package com.android.unilim.ratiog4.ratiog4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.unilim.ratiog4.ratiog4.sqlite.jeu.Jeu;
import com.android.unilim.ratiog4.ratiog4.sqlite.jeu.JeuDataSource;
import com.android.unilim.ratiog4.ratiog4.sqlite.ratio.Ratio;
import com.android.unilim.ratiog4.ratiog4.sqlite.ratio.RatioDataSource;

/**
 * Created by dimga on 23/03/2018.
 */

public class RatioActivity extends AppCompatActivity implements View.OnClickListener {

    private JeuDataSource jeuDataSource;
    private RatioDataSource ratioDataSource;
    private Jeu jeu;
    private TextView tv_win;
    private TextView tv_lose;
    private Ratio ratio;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_ajouter_ratio);

        jeuDataSource = new JeuDataSource(this);
        jeuDataSource.open();

        ratioDataSource = new RatioDataSource(this);
        ratioDataSource.open();

        tv_win = (TextView)findViewById(R.id.TextViewVictoires);
        tv_lose = (TextView)findViewById(R.id.TextViewDefaites);

        this.jeu = jeuDataSource.getJeu(getIntent().getLongExtra(JeuActivity.KEY_ID_JEU, -1));

        if(jeu == null) {
            Toast.makeText(this, "Jeu inexistant", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            final TextView tv_titreJeu = (TextView)findViewById(R.id.ratio_titre_jeu);
            tv_titreJeu.setText(jeu.getNom());
        }

        this.ratio = new Ratio(-1, 0, 0, jeu.getId());
        afficherRatio(ratio);
        this.ratioDataSource.ajouterRatio(this.ratio);

    }

    private void afficherRatio(Ratio ratio) {
        tv_win.setText(ratio.getNbVictoire());
        tv_lose.setText(ratio.getNbDefaite());
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bouton_ajouterLose :
                this.ratio.ajouterLose();
                break;

            case R.id.bouton_enleverLose :
                this.ratio.enleverLose();
                break;

            case R.id.bouton_ajouterWin :
                this.ratio.ajouterWin();
                break;

            case R.id.bouton_enleverWin :
                this.ratio.enleverWin();
                break;
        }

        afficherRatio(this.ratio);
        this.ratioDataSource.modifier(ratio);
    }
}

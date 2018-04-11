package com.android.unilim.ratiog4.ratiog4;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
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
    private Ratio ratio;

    private Button btn_win;
    private Button btn_lose;
    private TextInputEditText commentaireEditText;

    private static final long ID_RATIO_NON_ENREGISTRE = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_ajouter_ratio);

        //Instanciation des composants graphiques
        Button textViewWin = (Button)findViewById(R.id.nb_victoires);
        btn_win = textViewWin;

        Button textViewLose = (Button)findViewById(R.id.nb_defaites);
        btn_lose = textViewLose;

        this.commentaireEditText = (TextInputEditText)findViewById(R.id.edit_text_commentaire);

        this.setOnClickListener();


        //Ouverture des bases de données
        jeuDataSource = new JeuDataSource(this);
        jeuDataSource.open();

        ratioDataSource = new RatioDataSource(this);
        ratioDataSource.open();

        //Récupération des objets de la bd
        this.jeu = jeuDataSource.getJeu(getIntent().getLongExtra(JeuActivity.KEY_ID_JEU, -1));
        if(this.ratioDataSource.aUnRatioEnCoursJeu(jeu.getId())) {
            this.ratio = this.ratioDataSource.getRatioEnCoursJeu(jeu.getId());
        }
        else
            this.ratio = new Ratio(ID_RATIO_NON_ENREGISTRE, 0, 0, jeu.getId());


        if(jeu == null) {
            Toast.makeText(this, "Jeu inexistant", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            final TextView tv_titreJeu = (TextView)findViewById(R.id.ratio_titre_jeu);
            tv_titreJeu.setText(jeu.getNom());
        }

        afficherRatio(ratio);

    }

    private void setOnClickListener() {
        btn_win.setOnClickListener(this);
        btn_lose.setOnClickListener(this);
        ((Button)findViewById(R.id.bouton_enleverLose)).setOnClickListener(this);
        ((Button)findViewById(R.id.bouton_enleverWin)).setOnClickListener(this);

        ((Button)findViewById(R.id.btn_quitter)).setOnClickListener(this);
        ((Button)findViewById(R.id.btn_valider)).setOnClickListener(this);

        this.commentaireEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ratio.setCommentaire(editable.toString());
                sauvegarderRatioEnCours();
            }
        });
    }

    private void afficherRatio(Ratio ratio) {
        btn_win.setText(ratio.getNbVictoire()+"");
        btn_lose.setText(ratio.getNbDefaite()+"");
        this.commentaireEditText.setText(ratio.getCommentaire());
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

            case R.id.bouton_enleverLose :
                this.ratio.enleverLose();
                break;

            case R.id.bouton_enleverWin :
                this.ratio.enleverWin();
                break;

            case R.id.nb_victoires:
                this.ratio.ajouterWin();
                break;

            case R.id.nb_defaites:
                this.ratio.ajouterLose();
                break;

            case R.id.btn_quitter:
                actionBoutonQuitter();
                break;

            case R.id.btn_valider:
                actionBoutonValider();
                break;
        }


        sauvegarderRatioEnCours();

        afficherRatio(this.ratio);
    }

    private void sauvegarderRatioEnCours() {
        if(this.ratio.getId() != ID_RATIO_NON_ENREGISTRE) {
            //Si le ratio a déjà été enregistré, alors on le modifie
            this.ratioDataSource.modifier(ratio);
        }
        else {
            //Si le ratio n'a pas encore été enregistré
            this.ratio.setId(
                    this.ratioDataSource.ajouterRatio(ratio));
        }
    }

    private void actionBoutonValider() {
        this.ratio.finirEnregistrement();
        this.ratioDataSource.ajouterRatio(this.ratio);
        this.ratioDataSource.supprimerAllRatioEnCoursJeu(jeu.getId());
        Toast.makeText(this, "Ratio enregistré !", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void actionBoutonQuitter() {
        new AlertDialog.Builder(this)
                .setTitle("Quitter")
                .setMessage("Êtes-vous sur de vouloir quitter ? (Toute modification non enregistrée sera effacée)")
                .setNegativeButton("Quitter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        ratioDataSource.supprimerAllRatioEnCoursJeu(jeu.getId());
                    }
                }).setPositiveButton("Annuler", null)
                .create().show();
    }
}

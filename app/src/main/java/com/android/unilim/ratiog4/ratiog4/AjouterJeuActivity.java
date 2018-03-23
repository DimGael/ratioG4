package com.android.unilim.ratiog4.ratiog4;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    private ImageView imgView;
    private Uri imageUri;
    private static int RESULT_LOAD_IMAGE;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_ajouter_jeu);
        RESULT_LOAD_IMAGE=1;
        jeuDataSource = new JeuDataSource(this);
        jeuDataSource.open();




        final Button insertion = (Button)findViewById(R.id.insertion);
        final Button image = (Button)findViewById(R.id.image);
        imgView=(ImageView)findViewById(R.id.imageView);
        ajouterJeu =(EditText)findViewById(R.id.ajouter_jeu);
        insertion.setOnClickListener(this);
        image.setOnClickListener(this);
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
        switch (view.getId()) {
            case R.id.insertion :
                if (this.ajouterJeu.getText().toString().equals("") || this.ajouterJeu.getText() == null || this.imageUri==null) {
                    Toast.makeText(this, "jeu non valide", Toast.LENGTH_SHORT).show();
                } else {
                    jeuDataSource.ajouterJeu(new Jeu(-1,this.ajouterJeu.getText().toString(),this.imageUri));
                    // REDIRECTION VERS PAGE PRINCIPALE A FAIRE
                    finish();
                    Toast.makeText(this, "jeu inséré", Toast.LENGTH_SHORT).show();
                }
            break;

            case R.id.image :
                Toast.makeText(this, "coucou", Toast.LENGTH_SHORT).show();
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery, RESULT_LOAD_IMAGE);

                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK){
            imageUri = data.getData();
            imgView.setImageURI(imageUri);

        }
    }
}

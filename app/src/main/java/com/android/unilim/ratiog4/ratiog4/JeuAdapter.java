package com.android.unilim.ratiog4.ratiog4;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.unilim.ratiog4.ratiog4.sqlite.jeu.Jeu;
import com.android.unilim.ratiog4.ratiog4.sqlite.jeu.JeuDataSource;
import com.android.unilim.ratiog4.ratiog4.sqlite.ratio.RatioDataSource;

import java.util.List;

/**
 * Created by dimga on 14/03/2018.
 */

public class JeuAdapter extends BaseAdapter {

    private final List<Jeu> jeux;
    private final Context context;
    private final LayoutInflater layoutInflater;

    public JeuAdapter(Context context, List<Jeu> jeux){
        this.context = context;
        this.jeux = jeux;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return jeux.size();
    }

    @Override
    public Object getItem(int i) {
        return jeux.get(i);
    }

    @Override
    public long getItemId(int i) {
        return jeux.get(i).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Jeu jeu = this.jeux.get(position);

        convertView = layoutInflater.inflate(R.layout.ligne_jeu, null);

        TextView titre_jeu = (TextView)convertView.findViewById(R.id.main_titre_jeu);
        titre_jeu.setText(jeu.getNom());

        ImageView imageView=(ImageView)convertView.findViewById(R.id.main_image_jeu);
        imageView.getLayoutParams().height=150;
        imageView.getLayoutParams().width=150;

        imageView.setImageURI(jeu.getUri_image());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intentJeu = new Intent(context, JeuActivity.class);
                intentJeu.putExtra(JeuActivity.KEY_ID_JEU, jeu.getId());
                context.startActivity(intentJeu);
            }
        });

        final Button boutonJeu = (Button)convertView.findViewById(R.id.button_supprimer);
        boutonJeu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(context)
                        .setCancelable(true)
                        .setTitle("Suppression de "+jeu.getNom())
                        .setMessage("Êtes vous sûr de vouloir supprimer "+jeu.getNom()+" ?\n(Tous les ratios associés vont être supprimés)")
                        .setNegativeButton("Annuler", null)
                        .setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Toast.makeText(context, jeu.getNom()+" supprimé", Toast.LENGTH_SHORT).show();

                                supprimerJeuBdd(jeu);

                                jeux.remove(position);

                                notifyDataSetChanged();


                            }
                        }).create().show();
            }
        });

        return convertView;
    }

    private void supprimerJeuBdd(Jeu jeu){
        final JeuDataSource jeuDataSource = new JeuDataSource(context);
        final RatioDataSource ratioDataSource = new RatioDataSource(context);
        ratioDataSource.open();
        jeuDataSource.open();
        ratioDataSource.supprimerJeu(jeu);
        jeuDataSource.supprimerJeu(jeu);

        jeuDataSource.close();
        ratioDataSource.close();
    }
}

package com.android.unilim.ratiog4.ratiog4;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.unilim.ratiog4.ratiog4.sqlite.jeu.Jeu;
import com.android.unilim.ratiog4.ratiog4.sqlite.jeu.JeuDataSource;

import java.util.List;

/**
 * Created by dimga on 14/03/2018.
 */

public class JeuAdapter extends BaseAdapter {

    private final List<Jeu> jeux;
    private final Context context;
    private final LayoutInflater layoutInflater;
    private final ListView listView;

    public JeuAdapter(Context context, List<Jeu> jeux, ListView viewJeux){
        this.context = context;
        this.jeux = jeux;
        this.layoutInflater = LayoutInflater.from(context);
        this.listView = viewJeux;
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

        TextView titre_jeu = (TextView)convertView.findViewById(R.id.titre_jeu);
        titre_jeu.setText(jeu.getNom());

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
                                final JeuDataSource jeuDataSource = new JeuDataSource(context);
                                jeuDataSource.open();
                                jeuDataSource.supprimerJeu(jeu);
                                jeux.remove(position);

                                listView.setAdapter(new JeuAdapter(context, jeux, listView));

                                jeuDataSource.close();
                            }
                        }).create().show();
            }
        });

        return convertView;
    }
}

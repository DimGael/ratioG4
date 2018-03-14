package com.android.unilim.ratiog4.ratiog4.sqlite;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.unilim.ratiog4.ratiog4.MainActivity;
import com.android.unilim.ratiog4.ratiog4.R;
import com.android.unilim.ratiog4.ratiog4.sqlite.jeu.Jeu;
import com.android.unilim.ratiog4.ratiog4.sqlite.jeu.JeuDataSource;

import java.util.List;

/**
 * Created by dimga on 14/03/2018.
 */

public class JeuAdapter extends BaseAdapter {

    private List<Jeu> jeux;
    private Context context;
    private LayoutInflater layoutInflater;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        final Jeu jeu = this.jeux.get(position);

        convertView = layoutInflater.inflate(R.layout.ligne_jeu, null);

        TextView titre_jeu = (TextView)convertView.findViewById(R.id.titre_jeu);
        titre_jeu.setText(jeu.getNom());

        final Button boutonJeu = (Button)convertView.findViewById(R.id.button_supprimer);
        boutonJeu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final JeuDataSource jeuDataSource = new JeuDataSource(context);
                jeuDataSource.open();
                jeuDataSource.supprimerJeu(jeu);
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                jeuDataSource.close();

            }
        });

        return convertView;
    }
}

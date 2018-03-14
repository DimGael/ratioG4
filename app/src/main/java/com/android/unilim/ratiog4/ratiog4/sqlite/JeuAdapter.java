package com.android.unilim.ratiog4.ratiog4.sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.unilim.ratiog4.ratiog4.R;
import com.android.unilim.ratiog4.ratiog4.sqlite.jeu.Jeu;

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



        return convertView;
    }
}

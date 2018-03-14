package com.android.unilim.ratiog4.ratiog4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.android.unilim.ratiog4.ratiog4.sqlite.JeuAdapter;
import com.android.unilim.ratiog4.ratiog4.sqlite.jeu.Jeu;
import com.android.unilim.ratiog4.ratiog4.sqlite.jeu.JeuDataSource;

import java.util.List;

/**
 * Created by dimga on 14/03/2018.
 */

public class MainActivity extends AppCompatActivity {

    private JeuDataSource jeuDataSource;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        jeuDataSource = new JeuDataSource(this);
        jeuDataSource.open();

        List<Jeu> jeux = this.jeuDataSource.getAllJeux();


        final ListView viewJeux = (ListView)findViewById(R.id.listViewJeux);
        viewJeux.setAdapter(new JeuAdapter(this, jeux));
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
}

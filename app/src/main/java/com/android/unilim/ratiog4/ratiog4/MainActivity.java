package com.android.unilim.ratiog4.ratiog4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    final GridView grille = (GridView)findViewById(R.id.grille);

    Ratio[] ratio = {new Ratio(100,50),new Ratio(10,100),new Ratio(50,50)};



    ArrayAdapter<Ratio> arrayAdapter = new ArrayAdapter<Ratio>(this, android.R.layout.simple_list_item_1 , ratio);

    grille.setAdapter(arrayAdapter);

    }
}

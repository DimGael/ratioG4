package com.android.unilim.ratiog4.ratiog4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.unilim.ratiog4.ratiog4.sqlite.ratio.Ratio;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;

public class RatioAdapter extends BaseAdapter {

    private List<Ratio> ratios;
    private Context context;
    private LayoutInflater layoutInflater;

    public RatioAdapter(Context context, List<Ratio> ratios){
        this.context = context;
        this.ratios = ratios;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return ratios.size();
    }

    @Override
    public Object getItem(int i) {
        return ratios.get(i);
    }

    @Override
    public long getItemId(int i) {
        return ratios.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final Ratio ratio = ratios.get(i);

        view = layoutInflater.inflate(R.layout.ligne_ratio, null);

        final TextView tv_date = (TextView)view.findViewById(R.id.ligne_ratio_date);
        tv_date.setText(afficherDate(ratio.getDate()));

        final TextView tv_ratio_win = (TextView)view.findViewById(R.id.ligne_ratio_win);
        tv_ratio_win.setText("W: "+ratio.getNbVictoire());

        final TextView tv_ratio_lose = (TextView)view.findViewById(R.id.ligne_ratio_lose);
        tv_ratio_lose.setText("L: "+ratio.getNbDefaite());

        final TextView tv_winrate = (TextView)view.findViewById(R.id.ligne_ratio_winrate);
        tv_winrate.setText(ratio.getPourcentage());

        if(!ratio.estPositif()){
            tv_winrate.setTextColor(context.getResources().getColor(R.color.colorLose));
        }

        view.setBackgroundColor(context.getResources().getColor(R.color.ratioUnselected));


        final TextView tv_commentaire = (TextView) view.findViewById(R.id.ligne_ratio_commentaire);
        tv_commentaire.setText("Commentaire : " + ratio.getCommentaire());


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(tv_commentaire.getVisibility() == View.VISIBLE){
                    tv_commentaire.setVisibility(View.GONE);
                    view.setBackgroundColor(context.getResources().getColor(R.color.ratioUnselected));
                }
                else if(!ratio.getCommentaire().equals(""))
                {
                    tv_commentaire.setVisibility(View.VISIBLE);
                    view.setBackgroundColor(context.getResources().getColor(R.color.ratioSelected));
                }
            }
        });



        return view;
    }

    private String afficherDate(Calendar date) {
        String jour;
        if(date.get(Calendar.DAY_OF_MONTH) < 10)
            jour = "0"+date.get(Calendar.DAY_OF_MONTH);
        else
            jour = date.get(Calendar.DAY_OF_MONTH)+"";

        String mois;

        if(date.get(Calendar.MONTH) < 10)
            mois = "0"+date.get(Calendar.MONTH);
        else
            mois = date.get(Calendar.MONTH)+"";


        String annee = new Integer(date.get(Calendar.YEAR)).toString().substring(2);


        return jour+"/"+mois+"/"+annee;
    }
}

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
import android.widget.EditText;
import android.widget.TextView;

import com.android.unilim.ratiog4.ratiog4.sqlite.jeu.Jeu;
import com.android.unilim.ratiog4.ratiog4.sqlite.ratio.Ratio;
import com.android.unilim.ratiog4.ratiog4.sqlite.ratio.RatioDataSource;

import java.util.Calendar;
import java.util.List;

public class RatioAdapter extends BaseAdapter {

    private List<Ratio> ratios;
    private Context context;
    private LayoutInflater layoutInflater;
    private Jeu jeu;

    public RatioAdapter(Context context, List<Ratio> ratios, Jeu jeu){
        this.context = context;
        this.ratios = ratios;
        this.jeu = jeu;
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

        final String modif_comm;
        if(ratio.aUnCommentaire())
            modif_comm = "Modifier Commentaire";
        else
            modif_comm = "Ajouter un commentaire";


        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context)
                        .setItems(new CharSequence[]{modif_comm, "Partager"},
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int wich) {
                                        switch (wich){
                                            case 0:
                                                modifierCommentaire(ratio);
                                                break;
                                            case 1:
                                                partager(ratio, jeu.getNom());
                                                break;
                                        }
                                    }
                                })
                        .create().show();
                return false;
            }
        });


        return view;
    }

    private void partager(Ratio ratio, String nomJeu) {
        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("text/plain");
        intentShare.putExtra(Intent.EXTRA_TEXT, "Dans mes dernières game de "+nomJeu+" j'ai fait "+ratio.getNbVictoire()+" wins sur "+ratio.getNbParties()+" parties !");
        context.startActivity(Intent.createChooser(intentShare, "Partager son ratio avec "));
    }

    /**
     * Méthode appeler lorsqu'on appuie longtemps sur un ratio et que l'on choisit modifier commentaire
     * @param ratio
     */
    private void modifierCommentaire(final Ratio ratio) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.dialog_box_commentaire, null);
        final EditText edt_comm = (EditText) dialogView.findViewById(R.id.stats_modifcomm_edit_text_commentaire);

        edt_comm.setText(ratio.getCommentaire());

        final Button bouton_annuler = (Button) dialogView.findViewById(R.id.stats_modifcom_annuler);
        final Button bouton_valider = (Button) dialogView.findViewById(R.id.stats_modifcom_valider);



        final AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setView(dialogView)
                .setTitle("Modifier commentaire")
                .setMessage("Modifier votre commentaire")
                .create();

        alertDialog.show();

        bouton_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validerModificationCommentaire(ratio, edt_comm.getText().toString());
                alertDialog.cancel();
                notifyDataSetChanged();
            }
        });

        bouton_annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
    }

    private void validerModificationCommentaire(Ratio ratio, String nouv_commentaire) {
        ratio.setCommentaire(nouv_commentaire);

        final RatioDataSource dataSource = new RatioDataSource(context);
        dataSource.open();

        dataSource.modifier(ratio);

        dataSource.close();
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


        String annee = Integer.valueOf(date.get(Calendar.YEAR)).toString().substring(2);


        return jour+"/"+mois+"/"+annee;
    }
}

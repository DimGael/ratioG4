package com.android.unilim.ratiog4.ratiog4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.unilim.ratiog4.ratiog4.sqlite.ratio.Ratio;

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
        tv_date.setText(ratio.getDate().toString());

        final TextView tv_ratio_win = (TextView)view.findViewById(R.id.ligne_ratio_win);
        tv_ratio_win.setText("W: "+ratio.getNbVictoire());

        final TextView tv_ratio_lose = (TextView)view.findViewById(R.id.ligne_ratio_lose);
        tv_ratio_lose.setText("L: "+ratio.getNbDefaite());

        final TextView tv_winrate = (TextView)view.findViewById(R.id.ligne_ratio_winrate);
        tv_winrate.setText(ratio.getPourcentage());

        return view;
    }
}

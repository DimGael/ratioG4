package com.android.unilim.ratiog4.ratiog4;

import com.android.unilim.ratiog4.ratiog4.sqlite.ratio.Ratio;

import java.util.List;
import java.util.regex.Pattern;

class ListRatioAnalyzer {
    private final List<Ratio> ratioList;

    public ListRatioAnalyzer(List<Ratio> ratioList) {
        this.ratioList = ratioList;
    }

    public int getNbWins(){
        int nbWins = 0;

        for(Ratio ratio : ratioList){
            nbWins += ratio.getNbVictoire();
        }

        return nbWins;
    }

    public int getNbLoses(){
        int nbLoses = 0;

        for(Ratio ratio : ratioList){
            nbLoses += ratio.getNbDefaite();
        }

        return nbLoses;
    }

    public int getNbParties(){
        return this.getNbLoses() + this.getNbWins();
    }

    public String getWinRate(){
        if(this.ratioList.size() == 0)
            return "";

        final double winrate = ((double)this.getNbWins()/(double)this.getNbParties())*100;
        String str = new Double(winrate).toString();


        String[] tab = str.split(Pattern.quote("."));
        if(winrate%1 > 0) {

            return tab[0] + ',' + tab[1].substring(0, 1)+"%";
        }

        return tab[0]+"%";
    }

    public boolean estPositif(){
        return this.getNbWins() >= this.getNbLoses();
    }
}

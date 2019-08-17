package com.kaprofi.keepmind;

import android.app.Activity;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class PauseMenu extends Activity {
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause_menu);



        Button button = (Button) findViewById(R.id.buttonStop);
        final ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.layoutMain);


        Intent i = getIntent();


        TextView txtlvl = (TextView) findViewById(R.id.txtLvl);
        int lvl = i.getIntExtra("lvl", 0);
        txtlvl.setText((lvl + 1) + "");


        layout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                    Database db = new Database(getApplicationContext());
                    Level lvl = new Level();

                    lvl = db.getirOyunBilgileri();
                    int tekrar = lvl.getTekrar();
                    int acilacakKart = lvl.getAcilacakKartSayisi();
                    int score = lvl.getScore();

                    int level = lvl.getLevel();
                    int Numrows = lvl.getNumRows();
                    int numcols = lvl.getNumCols();
                    int combo = lvl.getCombo();

                    tekrar++;

                    if (combo == 0) score += 10;
                    else score = score + (10 * combo);

                    //  yazdir();
        /*TableLayout table = (TableLayout) findViewById(R.id.layoutTable);
        table.removeAllViews();*/
                    if (tekrar <= 2) {
                        tekrar++;  //Aynı kartla 2 bölüm oynasın


                        level++;

                        lvl.setTekrar(tekrar);
                        lvl.setAcilacakKartSayisi(acilacakKart);
                        lvl.setScore(score);
                        lvl.setLevel(level);
                        lvl.setNumRows(Numrows);
                        lvl.setNumCols(numcols);
                        lvl.setCombo(combo);

                        db.ekleBilgi(lvl);

                        // TODO Auto-generated method stub
                        Intent i = new Intent(getApplicationContext(), GameActivity.class);
                        //  i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(i);
                        overridePendingTransition(0, 0);


                    } else {

                        tekrar = 0;


                        acilacakKart++;
                        if (acilacakKart > (Numrows * numcols)) {
                            Numrows++;
                            numcols++;

                        }
                        level++;

                        lvl.setTekrar(tekrar);
                        lvl.setAcilacakKartSayisi(acilacakKart);
                        lvl.setScore(score);
                        lvl.setLevel(level);
                        lvl.setNumRows(Numrows);
                        lvl.setNumCols(numcols);
                        lvl.setCombo(combo);

                        db.ekleBilgi(lvl);

if (mInterstitialAd.isLoaded()){
    mInterstitialAd.show();
}
else {

    Intent i = new Intent(getApplicationContext(), GameActivity.class);
    //  i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
    startActivity(i);
}
mInterstitialAd.setAdListener(new AdListener(){



    @Override
    public void onAdClosed() {
        super.onAdClosed();


        Intent i = new Intent(getApplicationContext(), GameActivity.class);
        //  i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
    }


});

                    }
                }



        });
        MobileAds.initialize(getApplicationContext(),
                "ca-app-pub-2069111242783992~2846208001");
        mInterstitialAd = new InterstitialAd(PauseMenu.this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2069111242783992/9397328000");
        AdRequest adRequest=new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);

    }


}

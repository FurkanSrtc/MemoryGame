package com.kaprofi.keepmind;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class GameOverActivity extends Activity implements RewardedVideoAdListener {
    private RewardedAd rewardedAd;
    private RewardedVideoAd mRewardedVideoAd;
    @Override
    public void onBackPressed() {
        Retry();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Button btnAds=(Button) findViewById(R.id.btnAds);


        btnAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  createAndLoadRewardedAd();

             if (mRewardedVideoAd.isLoaded()){
                 mRewardedVideoAd.show();
             }
             else
             {
                 Toast.makeText(getApplicationContext(),"Try Again",Toast.LENGTH_LONG).show();


             }

            }
        });
        MobileAds.initialize(this, "ca-app-pub-2069111242783992~2846208001");

        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();



        Button btnRetry=(Button) findViewById(R.id.btnRetry);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        Retry();

       /* populateButtons();
        ButtonTimer();*/

                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        Button btnPlayAgain = (Button) findViewById(R.id.btnPlayAgain);
        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Database db=new Database(getApplicationContext());
               db.silTabloVerileri();
               db.ekleBilgi(new Level(0,0,0,1,1,1,2,2));
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Retry()
    {

        Database db=new Database(getApplicationContext());
        Level lvl=new Level();

        lvl= db.getirOyunBilgileri();
        int tekrar=lvl.getTekrar();
        int acilacakKart=lvl.getAcilacakKartSayisi();
        int score=lvl.getScore();
        int life=lvl.getLife();
        int level=lvl.getLevel();
        int Numrows=lvl.getNumRows();
        int numcols=lvl.getNumCols();


        lvl.setScore(score-50);

        if (tekrar<=1)
            lvl.setLevel(level-2);
        else   lvl.setLevel(level-3);

        if (level<=2 || acilacakKart<=2) {

            lvl.setAcilacakKartSayisi(1);
            lvl.setNumRows(2);
            lvl.setNumCols(2);
            lvl.setLevel(1);
            lvl.setScore(0);
        }
        else {


            lvl.setAcilacakKartSayisi(acilacakKart-1);

            if (acilacakKart == ((Numrows-1) * (numcols-1)))
            {
                lvl.setNumRows(Numrows-1);
                lvl.setNumCols(numcols-1);

            }

        }

        db.ekleBilgi(lvl);
    }

   /* public RewardedAd createAndLoadRewardedAd() {
        RewardedAd rewardedAd = new RewardedAd(this,
                String.valueOf(R.string.app_RewardedAd));
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                // Ad failed to load.
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
        return rewardedAd;
    }*/

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-2069111242783992/4145001324",
                new AdRequest.Builder().build());
    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
loadRewardedVideoAd();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

        Database db=new Database(getApplicationContext());
        Level lvl=new Level();

        lvl= db.getirOyunBilgileri();
        int tekrar=lvl.getTekrar();
        int acilacakKart=lvl.getAcilacakKartSayisi();
        int score=lvl.getScore();
        int life=lvl.getLife();
        int level=lvl.getLevel();
        int Numrows=lvl.getNumRows();
        int numcols=lvl.getNumCols();

        life++;
        lvl.setLevel(level);
       lvl.setAcilacakKartSayisi(acilacakKart);
       lvl.setCombo(0);
       lvl.setLife(life);
       lvl.setNumCols(numcols);
       lvl.setNumRows(Numrows);
       lvl.setScore(score);
       lvl.setTekrar(tekrar);

        db.ekleBilgi(lvl);


        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
loadRewardedVideoAd();
    }

    @Override
    public void onRewardedVideoCompleted() {

    }

    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }
}

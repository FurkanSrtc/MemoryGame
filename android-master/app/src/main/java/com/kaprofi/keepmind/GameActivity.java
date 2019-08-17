package com.kaprofi.keepmind;

import android.content.Intent;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.google.android.gms.ads.*;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;

    private static int AcilacakKartSayisi = 1;
    private static int NUM_ROWS = 2;
    private static int NUM_COLS = 2;
    int sayac = 0; //dizilere veri atamak için
    int randomRow;
    int randomCol;
    int kartKapatanSayac = 0;

    Random rnd = new Random();
    int[] acilacakRowlar = new int[AcilacakKartSayisi];
    int[] acilacakColumnlar = new int[AcilacakKartSayisi];
    int clicksayac = 0;
    private AdView mAdView;
    Handler handler;
    Runnable runnable;
    Button buttons[][] = new Button[NUM_ROWS][NUM_COLS];
    List<Button> btnList = new ArrayList<Button>();

    int tekrar = 0; //Seviye Tekrar
    int level = 1;
    int score = 0;
    int life = 1;  // Ekranda görüntülenecek CAN
    int combo = 0; //Combo adedi
    int combohata = 0; //Yanlış seçim yapıldığında artıyor

    Database db;
    Level levelClass;
    Level oyunBilgileri;

    TextView textView;
    TextView txtCombo;
    TextView txtSkor;
    TextView txtLife;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        try {
            OyunBilgileri();
        } catch (Exception e) {
        }

        textView = (TextView) findViewById(R.id.textView6);
        txtCombo = (TextView) findViewById(R.id.txtCombo);
        txtSkor = (TextView) findViewById(R.id.txtScore);
        txtLife = (TextView) findViewById(R.id.txtLife);





     /*   if (tekrar == 0) {
prepaperAD();

                    if (mInterstitialAd.isLoaded()) {
                        Toast.makeText(getApplicationContext(), "Ad Load !",
                                Toast.LENGTH_SHORT).show();
                        mInterstitialAd.show();
                    }
                    else
                    {Toast.makeText(getApplicationContext(), "Ad Not Loaded !",
                            Toast.LENGTH_SHORT).show();}
                    prepaperAD();

        }
        else
        {}*/


        //region REKLAMLAR
        MobileAds.initialize(this,
                "ca-app-pub-2069111242783992~2846208001");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //endregion

        Seviye();
    }

   /* public void prepaperAD() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }

    }*/

    private void yazdir() {
        textView.setText("Level " + level);
        txtCombo.setText("COMBO: " + combo);
        txtSkor.setText("SCORE: " + score);
        txtLife.setText("LIFE: " + life);
    }

    private void Seviye() {
        tekrar++;
        sayac = 0;
        clicksayac = 0;
        kartKapatanSayac = 0;
        combohata = 0;


        life = (int) Math.ceil(AcilacakKartSayisi / 2) + 1;

        yazdir();


        TableLayout table = (TableLayout) findViewById(R.id.layoutTable);
        table.removeAllViews();

        acilacakRowlar = new int[AcilacakKartSayisi];
        acilacakColumnlar = new int[AcilacakKartSayisi];
        btnList = new ArrayList<Button>();
        buttons = new Button[NUM_ROWS][NUM_COLS];

        TextView textView = (TextView) findViewById(R.id.textView6);
        textView.setText("Level " + level);
        Log.d("Tag", "onCreate: Furkan Test YENİMETOD");

        VerileriAktar();

        populateButtons();
        ButtonTimer();
    }


    private void VerileriAktar() {
        levelClass = new Level(tekrar, combo, score, level, life, AcilacakKartSayisi, NUM_ROWS, NUM_COLS);
        db = new Database(getApplicationContext());
        db.ekleBilgi(levelClass);
    }

    private void OyunBilgileri() {
        db = new Database(getApplicationContext());
        oyunBilgileri = db.getirOyunBilgileri();
        tekrar = oyunBilgileri.getTekrar();
        combo = oyunBilgileri.getCombo();
        score = oyunBilgileri.getScore();
        level = oyunBilgileri.getLevel();
        life = oyunBilgileri.getLife();
        AcilacakKartSayisi = oyunBilgileri.getAcilacakKartSayisi();
        NUM_ROWS = oyunBilgileri.getNumRows();
        NUM_COLS = oyunBilgileri.getNumCols();
    }

    /*
    public void YeniSeviye() { //Yeni seviyeye geçtiğinde
        tekrar++;
        sayac = 0;
        clicksayac = 0;
        kartKapatanSayac = 0;
        combohata = 0;

        if (combo == 0) score += 10;
        else score = score + (10 * combo);

        life = (int) (Math.ceil(AcilacakKartSayisi / 2)) + 1;

        yazdir();

        TableLayout table = (TableLayout) findViewById(R.id.layoutTable);
        table.removeAllViews();

        if (tekrar <= 2) tekrar++;  //Aynı kartla 2 bölüm oynasın

        else {
            tekrar = 0;
            AcilacakKartSayisi++;
            if (AcilacakKartSayisi > (NUM_ROWS * NUM_COLS)) {
                NUM_ROWS++;
                NUM_COLS++;

            }
        }
        acilacakRowlar = new int[AcilacakKartSayisi];
        acilacakColumnlar = new int[AcilacakKartSayisi];
        btnList = new ArrayList<Button>();
        buttons = new Button[NUM_ROWS][NUM_COLS];
        level++;

        TextView textView = (TextView) findViewById(R.id.textView6);
        textView.setText("Level " + level);
        Log.d("Tag", "onCreate: Furkan Test YENİMETOD");
        VerileriAktar();
    }
*/


    private void kontrol(int row, int col) {
        if (buttons[row][col].getTag() == "acik") {
            row = rnd.nextInt(NUM_ROWS);
            col = rnd.nextInt(NUM_COLS);
            kontrol(row, col);
        } else {
            buttons[row][col].setBackground(getDrawable(R.drawable.buttonacik));
            buttons[row][col].setTag("acik");
            acilacakRowlar[sayac] = row;
            acilacakColumnlar[sayac] = col;
        }
    }

    public void ButtonTimer() { //RANDOM YANAN BUTON SIRALAMASI
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                if (sayac < AcilacakKartSayisi) {

                    randomRow = rnd.nextInt(NUM_ROWS);
                    randomCol = rnd.nextInt(NUM_COLS);

                    kontrol(randomRow, randomCol);
                    if (sayac == 0) {
                        handler.postDelayed(this, 1000);
                    } else {
                        buttons[acilacakRowlar[sayac - 1]][acilacakColumnlar[sayac - 1]].setBackground(getDrawable(R.drawable.buttonkapalikart));
                        handler.postDelayed(this, 400);
                    }
                    sayac++;

                } else {
                    if (sayac == AcilacakKartSayisi)
                        buttons[acilacakRowlar[sayac - 1]][acilacakColumnlar[sayac - 1]].setBackground(getDrawable(R.drawable.buttonkapalikart));

                  /*  Toast.makeText(getApplicationContext(), "SIRA SENDE !",
                            Toast.LENGTH_SHORT).show();*/
                    handler.removeCallbacks(runnable);
                    KartEnabled(true);

                }

            }
        };
        handler.post(runnable);
    }

    private void KartEnabled(boolean b) {
        for (int row = 0; row < NUM_ROWS; row++) {

            for (int col = 0; col < NUM_COLS; col++) {
                buttons[row][col].setEnabled(b);
            }
        }
    }

    private void populateButtons() {   //TABLO SATIR SÜTUN ve BUTON AYARI
        TableLayout table = (TableLayout) findViewById(R.id.layoutTable);

        for (int row = 0; row < NUM_ROWS; row++) {
            final TableRow tableRow = new TableRow(this);

            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);

            for (int col = 0; col < NUM_COLS; col++) {

                final int FINAL_COL = col;
                final int FINAL_ROW = row;

                final Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));

                button.setId(((col + 1) * 10) + (row + 1));
                // Make text not clip on small buttons
                button.setPadding(0, 0, 0, 0);
                button.setEnabled(false);
                button.setTag("kapali");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gridButtonClicked(FINAL_COL, FINAL_ROW);
                    }
                });

                button.setBackground(getDrawable(R.drawable.buttonkapalikart));
                tableRow.addView(button);
                buttons[row][col] = button;
            }
        }
    }

    private void gridButtonClicked(int col, int row) {  //BUTONA TIKLANDIĞINDA
        Button button = buttons[row][col];

        int newWidth = button.getWidth();
        int newHeight = button.getHeight();
        if (col == acilacakColumnlar[clicksayac] && row == acilacakRowlar[clicksayac])  //DOĞRU KARTI SEÇERSE
        {
            button.setBackground(getDrawable(R.drawable.buttonacik));
            button.setEnabled(false);

            clicksayac++;
            if (clicksayac == AcilacakKartSayisi)  //TÜM KARTLARI DOĞRU SEÇERSE
            {
                final Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
               /* Toast.makeText(this, "KAZANDIN",
                        Toast.LENGTH_SHORT).show();*/
                vibrator.vibrate(200);
                vibrator.vibrate(500);

                if (combohata == 0) combo++;


                VerileriAktar();
                Intent intent = new Intent(getApplicationContext(), PauseMenu.class);
                intent.putExtra("lvl", level);
                //    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                overridePendingTransition(0, 0);
                //  YeniSeviye();
            }
        } else // YANLIŞ KARTI SEÇERSE
        {
            YanlisKart(button);
        }

    }


    private void YanlisKart(final Button buton) {
        //region TIMER (KAPALI - Kartı Gri Yapıp Söndürüyor)
        /*
         button.setBackground(getDrawable(R.drawable.buttonhatakart));
        final Vibrator vibrator=(Vibrator) getSystemService(VIBRATOR_SERVICE);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                  buton.setBackground(getDrawable(R.drawable.buttonkapalikart));
                    handler.removeCallbacks(runnable);
vibrator.vibrate(50);

            }
        };
        handler.postDelayed(runnable,200);
        */
//endregion

        final Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(50);
        combohata++;
        combo = 0;
        life--;
        yazdir();
        if (life <= 0) {
            VerileriAktar();
            Intent intent = new Intent(getApplicationContext(), GameOverActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }

    private void lockButtonSizes() {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                Button button = buttons[row][col];


                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }


}

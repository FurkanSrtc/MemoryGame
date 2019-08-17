package com.kaprofi.keepmind;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;
    final boolean[] vol = {true};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button volume = (Button) findViewById(R.id.volume);
        final Drawable high_volume = getResources().getDrawable(R.drawable.highvolume);
        final Drawable mute_volume = getResources().getDrawable(R.drawable.mutevolume);

        if (vol[0]) {
            volume.setBackground(high_volume); }
        else if (!vol[0]) {
            volume.setBackground(mute_volume); }
//veriyi getSharedPreferences ile tutucaz. sonra yapılcak.



// TODO: Add adView to your view hierarchy.
        MobileAds.initialize(this, "ca-app-pub-2069111242783992~2846208001");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


       /* AdView adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId("ca-app-pub-2069111242783992/1132342093");*/


        Button btnStart=(Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });


        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vol[0]) {
                    volume.setBackground(mute_volume);
                    AudioManager amanager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
                    amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
                    amanager.setStreamMute(AudioManager.STREAM_ALARM, true);
                    amanager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                    amanager.setStreamMute(AudioManager.STREAM_RING, true);
                    amanager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
                    vol[0] = false;}
                else {
                    volume.setBackground(high_volume);
                    AudioManager amanager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
                    amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
                    amanager.setStreamMute(AudioManager.STREAM_ALARM, false);
                    amanager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                    amanager.setStreamMute(AudioManager.STREAM_RING, false);
                    amanager.setStreamMute(AudioManager.STREAM_SYSTEM, false);
                    vol[0] = true;
                }
            }
        });

    }
}

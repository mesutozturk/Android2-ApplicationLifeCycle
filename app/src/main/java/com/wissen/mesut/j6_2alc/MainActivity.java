package com.wissen.mesut.j6_2alc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity
        extends AppCompatActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    Button btnSay;
    int sayac = 0;
    SharedPreferences preferences, ayarlar;
    boolean sesAcikMi = false, titresimAcikMi = false;
    TextView txtDurum;
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
        btnSay = (Button) findViewById(R.id.btnSay);
        txtDurum = (TextView) findViewById(R.id.txtDurum);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ayarlar = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ayarlar.registerOnSharedPreferenceChangeListener(this);
        ayarlariYukle(ayarlar);

        sayac = preferences.getInt("sayac", 0);

        btnSay.setText(sayac + "");
        btnSay.setTextSize(15);

        final MediaPlayer player = MediaPlayer.create(this, R.raw.buttonclick);
        final Vibrator titresimci = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);



        btnSay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSay.setText(String.format("%d", ++sayac));
                if (sesAcikMi)
                    player.start();
                if (titresimAcikMi)
                    titresimci.vibrate(1000);
            }
        });
        btnSay.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                sayac = 0;
                btnSay.setText(String.format("%d", sayac));
                titresimci.vibrate(2000);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ayarlar) {
            //Toast.makeText(this, "Ayarlara bastın", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, AyarlarActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Toast.makeText(this, "Ayar Değişti", Toast.LENGTH_SHORT).show();
        ayarlariYukle(ayarlar);
    }

    public void ayarlariYukle(SharedPreferences ayar) {
        sesAcikMi = ayar.getBoolean("sesdurum", false);
        titresimAcikMi = ayar.getBoolean("titresim", false);
        String mesaj = "Ses : " + (sesAcikMi ? "Açık" : "Kapalı");
        mesaj += " - Titreşim : " + (titresimAcikMi ? "Açık" : "Kapalı");
        txtDurum.setText(mesaj);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        //Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("sayac", sayac);
        editor.commit();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
    }


}

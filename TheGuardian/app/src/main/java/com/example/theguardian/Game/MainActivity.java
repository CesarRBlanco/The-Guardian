package com.example.theguardian.Game;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.theguardian.R;

import java.util.Locale;

import static com.example.theguardian.Game.Game_Control.context;

public class MainActivity extends AppCompatActivity {

    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;
    public static SensorManager sensorManager;
    public static Sensor sennsorA;
    public static Sensor sennsorG;
    public static SensorEventListener sensorEventListener;
    int whip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Game_Control gameControl = new Game_Control(this);
        gameControl.setKeepScreenOn(true);
        setContentView(gameControl);

        preferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        editor = preferences.edit();
        Locale locale = new Locale(preferences.getString("LanguageConfig", "es"));
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        editor.putInt("actualScene", 1);
        editor.commit();

        manageDecorationView(context, true);
    }

    public void manageDecorationView(Context context, boolean onCreate) {
        final int viewOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        View decorationView = ((Activity) context).getWindow().getDecorView();
        decorationView.setSystemUiVisibility(viewOptions);
        //Desde API 16 oculta la Status Bar
        ((Activity) context).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (onCreate) {
            //En onResume no se coloca, salta excepción - Esta linea hace que no se inicie en Samsungs
//            ((Activity) context).getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        manageDecorationView(context, false);
    }


    @Override
    protected void onPause() {
        super.onPause();

    }




}




package com.example.theguardian.Game.Menus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.theguardian.Game.Game_Control;
import com.example.theguardian.Game.Scene_Control;
import com.example.theguardian.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;


public class Options_Scene extends Scene_Control {

    int screenWidth = 0, screenHeight = 0;
    Context context;
    Paint textPaint, blackPaint, boxtPaint;
    Bitmap botonL, backImg,backgroundImg;
    Rect vibrationBtn, soundBtn, musicBtn, languageBtn, backBtn, menuBtn,fondo;


    public Options_Scene(Context context, int altoPantalla, int anchoPantalla) {
        super(context);
        this.context = context;
/*
        if (preferences.getInt("lastScene", 1) != 1) {

        modal=true;
        }*/

        screenWidth = anchoPantalla;
        screenHeight = altoPantalla;

        boxtPaint = new Paint();
        boxtPaint.setColor(Color.WHITE);
        boxtPaint.setTextSize(60);

        textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(60);

        blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);
        blackPaint.setTextSize(60);

        // Imagenes
        backgroundImg = getBitmapFromAssets("pausa.png");

        backImg = getBitmapFromAssets("back.png");
        backImg = escalaAltura(backImg, screenHeight/6 );


        // Rectangulos
        fondo=new Rect(0,0,screenWidth,screenHeight);
        vibrationBtn = new Rect(screenWidth / 3, 50, screenWidth / 2, screenHeight / 5 + 50);
        soundBtn = new Rect(screenWidth / 3, vibrationBtn.bottom + 50, screenWidth / 2, vibrationBtn.bottom + 50 + screenHeight / 5);
        musicBtn = new Rect(screenWidth / 3, soundBtn.bottom + 50, screenWidth / 2, soundBtn.bottom + 50 + screenHeight / 5);
        languageBtn = new Rect(screenWidth / 3, musicBtn.bottom + 50, screenWidth / 2, musicBtn.bottom + 50 + screenHeight / 5);
        languageBtn = new Rect(screenWidth - (screenWidth / 3), musicBtn.bottom + 50, screenWidth - (screenWidth / 5), musicBtn.bottom + 50 + screenHeight / 5);
        backBtn = new Rect(0, 0, backImg.getWidth(), backImg.getHeight());//right y bottom de la imagen que va por encima
        if (preferences.getInt("lastScene", 1) != 1) {
            menuBtn = new Rect(0, screenHeight - backImg.getHeight(), backImg.getWidth(), screenHeight);
        }
    }


    public void draw(Canvas c) {
        super.draw(c);

        if (preferences.getInt("lastScene", 1) == 1) {
            c.drawColor(Color.BLACK);
        }else{
            blackPaint.setAlpha(50);
            c.drawBitmap(backgroundImg, 0, 0, blackPaint);
            blackPaint.setAlpha(100);
        }

        c.drawRect(vibrationBtn, textPaint);
        c.drawRect(soundBtn, textPaint);
        c.drawRect(musicBtn, textPaint);
        c.drawRect(languageBtn, textPaint);
        c.drawRect(backBtn, textPaint);


        if (preferences.getInt("lastScene", 1) != 1) {
            c.drawRect(menuBtn, textPaint);
        }
        c.drawText(context.getResources().getString(R.string.vibration), screenWidth / 3, 100, blackPaint);
        c.drawText(context.getResources().getString(R.string.sound), screenWidth / 3, vibrationBtn.bottom + 100, blackPaint);
        c.drawText(context.getResources().getString(R.string.music), screenWidth / 3, soundBtn.bottom + 100, blackPaint);
        c.drawText(context.getResources().getString(R.string.language), languageBtn.left, languageBtn.top + 100, blackPaint);
        c.drawText("" + preferences.getInt("SoundVolume", 0), screenWidth / 3, screenHeight / 4, boxtPaint);
        c.drawText("" + preferences.getInt("MusicVolume", 0), screenWidth / 3, screenHeight / 3, boxtPaint);
        c.drawText("" + preferences.getBoolean("Vibration", false), screenWidth / 3, screenHeight / 5, boxtPaint);
        c.drawText("" + preferences.getString("LanguageConfig", ""), screenWidth / 3, screenHeight / 2, boxtPaint);
    }

    public void updatePhysics() {
        super.updatePhysics();
    }


    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int action = event.getActionMasked();
        int indice = event.getActionIndex();
        int x = (int) event.getX(indice);
        int y = (int) event.getY(indice);


        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (backBtn.contains(x, y)) {

                    Game_Control.sceneChange(preferences.getInt("lastScene", 1));
                }
                if (vibrationBtn.contains(x, y)) {
                    if (preferences.getBoolean("Vibration", true)) {
                        editor = preferences.edit();
                        editor.putBoolean("Vibration", false);
                        editor.commit();
                    } else {
                        editor = preferences.edit();
                        editor.putBoolean("Vibration", true);
                        editor.commit();
                        Game_Control.v.vibrate(300);

                    }
                    if (preferences.getBoolean("Vibration", true)) {
                    }
                }
                if (soundBtn.contains(x, y)) {
                    editor = preferences.edit();
                    if (preferences.getInt("SoundVolume", 0) == 0) {
                        editor.putInt("SoundVolume", 1);
                    } else {
                        editor.putInt("SoundVolume", 0);
                    }
                    editor.commit();
                }

                if (musicBtn.contains(x, y)) {
                    editor = preferences.edit();
                    if (preferences.getInt("MusicVolume", 0) == 0) {
                        editor.putInt("MusicVolume", 1);
                        mediaPlayer.setVolume(1,1);
                    } else {
                        editor.putInt("MusicVolume", 0);
                        mediaPlayer.setVolume(0,0);
                    }
                    editor.commit();
                }

                if (languageBtn.contains(x, y)) {
                    editor = preferences.edit();


                    if (preferences.getString("LanguageConfig", "en").equals("es")) {
                        setLocale("en");
                        editor.putString("LanguageConfig", "en");
//                        Log.i("IDIOMA",preferences.getString("LanguageConfig",""));
                    } else {
                        setLocale("es");
                        editor.putString("LanguageConfig", "es");
//                        Log.i("IDIOMA",preferences.getString("LanguageConfig",""));
                    }
                    editor.commit();
                }

                if (preferences.getInt("lastScene", 1) != 1) {
                    if (menuBtn.contains(x, y)) {
                        Game_Control.sceneChange(1);
                    }
                }

                return true;
        }
        return true;
    }

    public void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        Toast.makeText(context, locale + "", Toast.LENGTH_LONG).show();

    }


    public Bitmap getBitmapFromAssets(String fichero) {
        try {
            InputStream is = context.getAssets().open(fichero);
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            return null;
        }
    }

    public Bitmap escalaAltura(Bitmap bitmapAux, int nuevoAlto) {
        if (nuevoAlto == bitmapAux.getHeight()) return bitmapAux;
        return Bitmap.createScaledBitmap(bitmapAux, (bitmapAux.getWidth() * nuevoAlto) /
                bitmapAux.getHeight(), nuevoAlto, true);
    }

    public Bitmap escalaAncho(Bitmap bitmapAux, int nuevoAncho) {
        if (nuevoAncho == bitmapAux.getWidth()) return bitmapAux;
        return Bitmap.createScaledBitmap(bitmapAux, nuevoAncho, (bitmapAux.getHeight() * nuevoAncho) / bitmapAux.getWidth(), true);
    }

    public Bitmap espejo(Bitmap imagen, Boolean horizontal) {
        Matrix matrix = new Matrix();
        if (horizontal) matrix.preScale(-1, 1);
        else matrix.preScale(1, -1);
        return Bitmap.createBitmap(imagen, 0, 0, imagen.getWidth(),
                imagen.getHeight(), matrix, false);
    }


}

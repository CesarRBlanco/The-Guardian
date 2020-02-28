package com.example.theguardian.Game.Menus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.theguardian.Game.Game_Control;
import com.example.theguardian.Game.Scene_Control;
import com.example.theguardian.R;

import java.io.IOException;
import java.io.InputStream;


public class Menu_Scene extends Scene_Control {

    int widthScreen = 0, heightScreen = 0;
    Context context;
    Paint textPaint, blackPaint;
    Bitmap botonL, menuBackground;
    Rect playBtn, optionsBtn, creditsBtn, helpBtn, exitBtn;


    public Menu_Scene(Context context, int altoPantalla, int anchoPantalla) {
        super(context);
        this.context = context;
        widthScreen = anchoPantalla;
        heightScreen = altoPantalla;
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(60);
        blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);
        blackPaint.setTextSize(60);
        super.musicChange(1);
        modal = false;

        // Imagenes
        botonL = getBitmapFromAssets("movement.png");
        botonL = escalaAltura(botonL, heightScreen / 6);
        botonL = espejo(botonL, true);
        menuBackground = getBitmapFromAssets("menu.png");
        menuBackground = Bitmap.createScaledBitmap(menuBackground, widthScreen, heightScreen, false);

        // Rectangulos
        playBtn = new Rect(widthScreen / 3, heightScreen / 3 + 20, widthScreen - (widthScreen / 3), heightScreen / 2);
        optionsBtn = new Rect(widthScreen / 3, heightScreen / 2 + 20, widthScreen - (widthScreen / 3), heightScreen - (heightScreen / 3));
        creditsBtn = new Rect(widthScreen / 3, heightScreen - (heightScreen / 3) + 20, widthScreen - (widthScreen / 3), heightScreen - (heightScreen / 6));
        helpBtn = new Rect(20, heightScreen - 20 - botonL.getHeight(), botonL.getWidth() + 20, heightScreen - 20);
        exitBtn = new Rect(widthScreen - botonL.getWidth(), heightScreen - 20 - botonL.getHeight(), widthScreen, heightScreen);
    }


    public void draw(Canvas c) {
        super.draw(c);
        c.drawBitmap(menuBackground, 0, 0, null);
        c.drawRect(playBtn, textPaint);
        c.drawText(context.getResources().getString(R.string.play), widthScreen / 3, (heightScreen / 3 + 20) + 100, blackPaint);
        c.drawRect(optionsBtn, textPaint);
        c.drawText(context.getResources().getString(R.string.options), widthScreen / 3, (heightScreen / 2 + 20) + 100, blackPaint);
        c.drawRect(creditsBtn, textPaint);
        c.drawText(context.getResources().getString(R.string.credits), widthScreen / 3, (heightScreen - (heightScreen / 3) + 20) + 100, blackPaint);
        c.drawRect(helpBtn, textPaint);
        c.drawText(context.getResources().getString(R.string.help), 20, heightScreen - 20 - botonL.getHeight() + 100, blackPaint);
        c.drawRect(exitBtn, textPaint);
        c.drawText(context.getResources().getString(R.string.exit), widthScreen - botonL.getWidth(), heightScreen - 20 - botonL.getHeight() + 100, blackPaint);

    }

    public void updateConfig() {
        super.updateConfig();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int action = event.getActionMasked();
        int indice = event.getActionIndex();
        int x = (int) event.getX(indice);
        int y = (int) event.getY(indice);

        switch (action) {
            case MotionEvent.ACTION_DOWN:

                if (playBtn.contains(x, y)) {
//                    editor = preferences.edit();
//                    editor.putInt("actualScene", 2);
//                    editor.commit();

                    Game_Control.sceneChange(10);
                }
                if (optionsBtn.contains(x, y)) {
                    editor = preferences.edit();
                    editor.putInt("lastScene", 1);
                    editor.commit();
                    Game_Control.sceneChange(2);
                }
                if (creditsBtn.contains(x, y)) {
//                    editor = preferences.edit();
//                    editor.putInt("actualScene", 4);
//                    editor.commit();
                    Game_Control.sceneChange(3);
                }
                if (helpBtn.contains(x, y)) {
//                    editor = preferences.edit();
//                    editor.putInt("actualScene", 5);
//                    editor.commit();
                    Game_Control.sceneChange(4);
                }
                if (exitBtn.contains(x, y)) {
//                    editor = preferences.edit();
//                    editor.putInt("actualScene", 6);
//                    editor.commit();
                    Game_Control.sceneChange(0);
                }


                return true;
        }
        return true;
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
        return bitmapAux.createScaledBitmap(bitmapAux, (bitmapAux.getWidth() * nuevoAlto) /
                bitmapAux.getHeight(), nuevoAlto, true);
    }

    public Bitmap escalaAncho(Bitmap bitmapAux, int nuevoAncho) {
        if (nuevoAncho == bitmapAux.getWidth()) return bitmapAux;
        return bitmapAux.createScaledBitmap(bitmapAux, nuevoAncho, (bitmapAux.getHeight() * nuevoAncho) / bitmapAux.getWidth(), true);
    }

    public Bitmap espejo(Bitmap imagen, Boolean horizontal) {
        Matrix matrix = new Matrix();
        if (horizontal) matrix.preScale(-1, 1);
        else matrix.preScale(1, -1);
        return Bitmap.createBitmap(imagen, 0, 0, imagen.getWidth(),
                imagen.getHeight(), matrix, false);
    }


}

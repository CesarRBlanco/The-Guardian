package com.example.theguardian.Game.Menus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.example.theguardian.Game.Scene_Control;

import java.io.IOException;
import java.io.InputStream;


public class Options_Scene extends Scene_Control {

    int screenWidth = 0, screenHeight = 0;
    Context context;
    Paint textPaint, blackPaint;
    Bitmap botonL, menuBackground;
    Rect vibrationBtn, soundBtn, musicBtn,languajeBtn, backBtn;


    public Options_Scene(Context context, int altoPantalla, int anchoPantalla) {
        super(context);
        this.context = context;
        screenWidth = anchoPantalla;
        screenHeight = altoPantalla;

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(60);
        blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);
        blackPaint.setTextSize(60);

        // Imagenes

        // Rectangulos
        vibrationBtn=new Rect(screenWidth/3,50,screenWidth/2,screenHeight/5+50);
        soundBtn=new Rect(screenWidth/3,vibrationBtn.bottom+50,screenWidth/2,vibrationBtn.bottom+50+screenHeight/5);
        musicBtn=new Rect(screenWidth/3,soundBtn.bottom+50,screenWidth/2,soundBtn.bottom+50+screenHeight/5);
languajeBtn=new Rect(screenWidth/3,musicBtn.bottom+50,screenWidth/2,musicBtn.bottom+50+screenHeight/5);
backBtn=new Rect(0,0,60,60);//right y bottom de la imagen que va por encima
    }


    public void draw(Canvas c) {
        super.draw(c);
        c.drawRect(vibrationBtn,textPaint);
        c.drawRect(soundBtn,textPaint);
        c.drawRect(musicBtn,textPaint);
     }

    public void updatePhysics() {

    }

    public int onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int action = event.getActionMasked();
        int indice = event.getActionIndex();
        int x = (int) event.getX(indice);
        int y = (int) event.getY(indice);

        switch (action) {
            case MotionEvent.ACTION_DOWN:

        }
        return 0;
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

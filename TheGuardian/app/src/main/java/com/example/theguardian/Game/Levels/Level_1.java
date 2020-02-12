package com.example.theguardian.Game.Levels;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.theguardian.Game.Character;
import com.example.theguardian.Game.Escenario_Objects;
import com.example.theguardian.Game.Scene_Control;
import com.example.theguardian.R;

import java.io.IOException;
import java.io.InputStream;


public class Level_1 extends Scene_Control {

    int screenWidth = 0, screenHeight = 0;
    Context context;
    Paint textPaint, blackPaint;
    Bitmap botonL, menuBackground;
    Rect playBtn, optionsBtn, creditsBtn, helpBtn;

    Paint invisiblePaint;
    Character character;
    Bitmap background, botonR, luces, actionButton_W, actionButton_B, dialogImg, dialogBack, dialogArrow, spriteRef, box, backOptions;
    Rect lMoveBtn, rMoveBtn, actionBtn, ladderInteract, backOptsBtn;
    int charEnd, musicVol;
    Escenario_Objects iniEO, boxObj;
    MediaPlayer mp;
    Scene_Control escenaActual;
    boolean showActionBlack = true;
    boolean dialog = false;
    boolean movementD = false;
    boolean movementI = false;
    boolean colisionI = false;
    boolean colisionD = false;


    public Level_1(Context context, int altoPantalla, int anchoPantalla) {
        super(context);
        this.context = context;
        screenWidth = anchoPantalla;
        screenHeight = altoPantalla;
        super.musicChange(2);


        invisiblePaint = new Paint();
        //Color.argb(0,0,0,0)
        invisiblePaint.setColor(Color.argb(0, 0, 0, 0));
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(60);
        blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);
        blackPaint.setTextSize(60);
        Bitmap[] bitmaps = new Bitmap[3];
        for (int i = 0; i < bitmaps.length; i++) {
            bitmaps[i] = getBitmapFromAssets("sprite" + i + ".png");
            bitmaps[i] = escalaAltura(bitmaps[i], altoPantalla / 6);
        }


        // Imagenes
        character = new Character(bitmaps, 1, 400, anchoPantalla, altoPantalla);
        spriteRef = getBitmapFromAssets("sprite0.png");
        spriteRef = escalaAltura(spriteRef, altoPantalla / 6);
        background = getBitmapFromAssets("fondo.png");
        background = Bitmap.createScaledBitmap(background, anchoPantalla, altoPantalla, false);
        luces = getBitmapFromAssets("sombras.png");
        luces = Bitmap.createScaledBitmap(luces, anchoPantalla, altoPantalla, false);
        botonL = getBitmapFromAssets("movement.png");
        botonL = escalaAltura(botonL, altoPantalla / 6);
        botonL = espejo(botonL, true);
        botonR = getBitmapFromAssets("movement.png");
        botonR = escalaAltura(botonR, altoPantalla / 6);
        actionButton_W = getBitmapFromAssets("actionButtonWhite.png");
        actionButton_W = escalaAltura(actionButton_W, altoPantalla / 6);
        actionButton_B = getBitmapFromAssets("actionButtonBlack.png");
        actionButton_B = escalaAltura(actionButton_B, altoPantalla / 6);
        dialogImg = getBitmapFromAssets("dialog.png");
        dialogImg = escalaAltura(dialogImg, altoPantalla / 2);
        dialogBack = getBitmapFromAssets("dialog_background.png");
        dialogBack = escalaAncho(dialogBack, anchoPantalla);
        dialogArrow = getBitmapFromAssets("dialog_arrow.png");
        dialogArrow = escalaAltura(dialogArrow, altoPantalla / 6);
        backOptions = getBitmapFromAssets("backOptions.png");
        backOptions = escalaAltura(backOptions, altoPantalla / 6);


        // Rectangulos
        lMoveBtn = new Rect(20, altoPantalla - 20 - botonL.getHeight(), botonL.getWidth() + 20, altoPantalla - 20);
        rMoveBtn = new Rect(60 + botonL.getWidth(), altoPantalla - 20 - botonR.getHeight(), botonR.getWidth() + 60 + botonL.getWidth(), altoPantalla - 20);
        actionBtn = new Rect(anchoPantalla - actionButton_B.getWidth() - 20, altoPantalla - 20 - botonR.getHeight(), anchoPantalla, altoPantalla);
        ladderInteract = new Rect(anchoPantalla / 4 - 10, altoPantalla / 2 - 90, anchoPantalla / 4 + 90, altoPantalla - altoPantalla / 4 + 30);
        backOptsBtn = new Rect(anchoPantalla - botonL.getWidth(), 0, anchoPantalla, botonL.getHeight());


        // Auxiliares

    }


    public void draw(Canvas c) {
        super.draw(c);
        Log.i("musicChange", "escena0");
        c.drawBitmap(background, 0, 0, null);
        c.drawRect(lMoveBtn, invisiblePaint);
        c.drawRect(rMoveBtn, invisiblePaint);
        c.drawRect(actionBtn, textPaint);
        c.drawRect(backOptsBtn, textPaint);

        if (dialog == false) {
            c.drawBitmap(botonL, 20, screenHeight - 20 - botonL.getHeight(), null);
            c.drawBitmap(botonR, 60 + botonL.getWidth(), screenHeight - 20 - botonR.getHeight(), null);
            if (showActionBlack) {
                c.drawBitmap(actionButton_B, screenHeight - actionButton_W.getWidth() - 20, screenHeight - 20 - botonR.getHeight(), null);
            } else {

                c.drawBitmap(actionButton_W, screenWidth - actionButton_W.getWidth() - 20, screenHeight - 20 - botonR.getHeight(), null);
            }
            c.drawBitmap(backOptions, screenWidth - actionButton_W.getWidth(), 0, null);
        } else {
            c.drawBitmap(dialogBack, 0, screenHeight - dialogBack.getHeight(), null);
            c.drawBitmap(dialogImg, -100, screenHeight - dialogImg.getHeight(), null);
            c.drawBitmap(dialogArrow, screenWidth - actionButton_W.getWidth() - 20, screenHeight - 20 - botonR.getHeight(), null);
            c.drawText(context.getResources().getString(R.string.dialogTest), dialogImg.getWidth() + 40, screenHeight - 150, textPaint);
        }
        character.dibuja(c);
        c.drawBitmap(luces, 0, 0, null);

    }

    public void updatePhysics() {
        super.updatePhysics();

        collisionSystem();

//
//        if (movementD) {
//            character.cambiaFrame();
//            if (colisionI == false) {
//                character.setVelocidad(20);
//                character.moverR();
//
//            }
//            colisionD = false;
//        }
//        if (movementI) {
//            character.cambiaFrame();
//            if (colisionD == false) {
//                character.setVelocidad(-20);
//                character.moverL();
//            }
//            colisionI = false;
//        }


    }


    public void collisionSystem() {


    }

    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
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

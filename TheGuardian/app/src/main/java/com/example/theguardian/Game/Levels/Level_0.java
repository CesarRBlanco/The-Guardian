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

import com.example.theguardian.Game.Character;
import com.example.theguardian.Game.Game_Control;
import com.example.theguardian.Game.Scenario_Objects;
import com.example.theguardian.Game.Scene_Control;

import java.io.IOException;
import java.io.InputStream;


public class Level_0 extends Scene_Control {

    int widthScreen = 0, heightScreen = 0;
    Context context;
    Paint textPaint, blackPaint;
    Bitmap botonL, menuBackground;
    Rect playBtn, optionsBtn, creditsBtn, helpBtn;

    Paint invisiblePaint;
    Character character;
    Bitmap fondo1, botonR, luces, actionButton_W, actionButton_B, dialogImg, dialogBack, dialogArrow, spriteRef, box, backOptions;
    Rect lMoveBtn, rMoveBtn, actionBtn, ladderInteract, backOptsBtn;
    int charEnd, musicVol;
    Scenario_Objects iniEO, boxObj;
    MediaPlayer mp;
    Scene_Control escenaActual;


    public Level_0(Context context, int altoPantalla, int anchoPantalla) {
        super(context);
        this.context = context;
        widthScreen = anchoPantalla;
        heightScreen = altoPantalla;
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
        fondo1 = getBitmapFromAssets("background.png");
        fondo1 = Bitmap.createScaledBitmap(fondo1, anchoPantalla, altoPantalla, false);
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
        box = getBitmapFromAssets("stoneDoor.png");
        box = escalaAltura(box, altoPantalla / 6);
        menuBackground = getBitmapFromAssets("menu.png");
        menuBackground = Bitmap.createScaledBitmap(menuBackground, anchoPantalla, altoPantalla, false);
        backOptions = getBitmapFromAssets("backOptions.png");
        backOptions = escalaAltura(backOptions, altoPantalla / 6);


        // Rectangulos
        lMoveBtn = new Rect(20, altoPantalla - 20 - botonL.getHeight(), botonL.getWidth() + 20, altoPantalla - 20);
        rMoveBtn = new Rect(60 + botonL.getWidth(), altoPantalla - 20 - botonR.getHeight(), botonR.getWidth() + 60 + botonL.getWidth(), altoPantalla - 20);
        actionBtn = new Rect(anchoPantalla - actionButton_B.getWidth() - 20, altoPantalla - 20 - botonR.getHeight(), anchoPantalla, altoPantalla);
        ladderInteract = new Rect(anchoPantalla / 4 - 10, altoPantalla / 2 - 90, anchoPantalla / 4 + 90, altoPantalla - altoPantalla / 4 + 30);


        boxObj = new Scenario_Objects(anchoPantalla / 2, altoPantalla - box.getHeight(), anchoPantalla / 2 + box.getWidth(), altoPantalla, box,heightScreen,widthScreen);


        playBtn = new Rect(anchoPantalla / 3, altoPantalla / 3 + 20, anchoPantalla - (anchoPantalla / 3), altoPantalla / 2);
        optionsBtn = new Rect(anchoPantalla / 3, altoPantalla / 2 + 20, anchoPantalla - (anchoPantalla / 3), altoPantalla - (altoPantalla / 3));
        creditsBtn = new Rect(anchoPantalla / 3, altoPantalla - (altoPantalla / 3) + 20, anchoPantalla - (anchoPantalla / 3), altoPantalla - (altoPantalla / 6));
        helpBtn = new Rect(20, altoPantalla - 20 - botonL.getHeight(), botonL.getWidth() + 20, altoPantalla - 20);
        backOptsBtn = new Rect(anchoPantalla - botonL.getWidth(), 0, anchoPantalla, botonL.getHeight());


        // Auxiliares


        //  escenaActual = new Menu_Scene(context, altoPantalla, anchoPantalla);
    }


    public void draw(Canvas c) {
        super.draw(c);
        Log.i("musicChange", "escena0");
        c.drawBitmap(fondo1, 0, 0, null);
//            c.drawRect(btnMove_L, invisiblePaint);
//            c.drawRect(btnMove_R, invisiblePaint);
//            c.drawRect(btnAction, textPaint);
//            c.drawRect(ladderInteract, textPaint);
//            c.drawRect(btnOptions, textPaint);
//
//            if (dialogStart == false) {
//                c.drawBitmap(botonL, 20, altoPantalla - 20 - botonL.getHeight(), null);
//                c.drawBitmap(imgMove_R, 60 + botonL.getWidth(), altoPantalla - 20 - imgMove_R.getHeight(), null);
//                if (showActionRed) {
//                    c.drawBitmap(imgAction_R, anchoPantalla - imgAction_W.getWidth() - 20, altoPantalla - 20 - imgMove_R.getHeight(), null);
//                } else {
//
//                    c.drawBitmap(imgAction_W, anchoPantalla - imgAction_W.getWidth() - 20, altoPantalla - 20 - imgMove_R.getHeight(), null);
//                }
//                c.drawBitmap(imgOptions, anchoPantalla - imgAction_W.getWidth(), 0, null);
//
//
//            } else {
//                c.drawBitmap(imgDialogBckgrnd, 0, altoPantalla - imgDialogBckgrnd.getHeight(), null);
//                c.drawBitmap(imgCharacDialog, -100, altoPantalla - imgCharacDialog.getHeight(), null);
//                c.drawBitmap(imgDialogAction, anchoPantalla - imgAction_W.getWidth() - 20, altoPantalla - 20 - imgMove_R.getHeight(), null);
//                c.drawText(getResources().getString(R.string.dialogTest), imgCharacDialog.getWidth() + 40, altoPantalla - 150, textPaint);
//            }

//        c.drawBitmap(box, anchoPantalla / 2, 1100 - box.getHeight(), null);
        boxObj.draw(c);
//            charEnd = _player.x + _playerSprite.getWidth();
        c.drawText("" + charEnd + " // " + ladderInteract.left, 10, 50 + invisiblePaint.getTextSize(), textPaint);
//        c.drawText("" + ladderInteract.right, 100, 50 + invisiblePaint.getTextSize(), textPaint);
        character.dibuja(c);
        c.drawBitmap(luces, 0, 0, null);

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
                Game_Control.sceneChange(1);
                if (playBtn.contains(x, y)) {
                    return true;
                }
                if (optionsBtn.contains(x, y)) {
                    return true;
                }
                if (creditsBtn.contains(x, y)) {
                    return true;
                }
                if (helpBtn.contains(x, y)) {
                    return true;
                }
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

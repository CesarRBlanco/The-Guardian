package com.example.theguardian.Game.Levels;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.theguardian.Game.Background;
import com.example.theguardian.Game.Character;
import com.example.theguardian.Game.Enemy;
import com.example.theguardian.Game.Game_Control;
import com.example.theguardian.Game.MainActivity;
import com.example.theguardian.Game.Scenario_Objects;
import com.example.theguardian.Game.Scene_Control;
import com.example.theguardian.R;

import java.io.IOException;
import java.io.InputStream;

import static android.content.Context.SENSOR_SERVICE;


public class Level_2 extends Scene_Control {

    int screenWidth = 0, screenHeight = 0;
    Context context;
    Paint textPaint, blackPaint;
    Bitmap botonL;

    Paint invisiblePaint;
    Character character;
    Enemy enemy1;
    Bitmap background, botonR, luces, actionButton_W, actionButton_B, dialogImg, dialogBack, dialogArrow, spriteRef, timeSkipPresent, timeSkipPast, backOptions;
    Rect lMoveBtn, rMoveBtn, actionBtn, timeSkipBtn, backOptsBtn, floor, wall, pilar;
    int charEnd, dialogCont = 0;

    boolean showActionRed = false;
    boolean dialogStart = false;
    boolean dialogEnd = false;
    boolean movementD = false;
    boolean movementI = false;
    boolean colisionI = false;
    boolean colisionD = false;
    boolean stoneClose = true;
    boolean buttonsEnabled = true;
    boolean presente = true;
    boolean pilarInteract = false;


    public Level_2(final Context context, int altoPantalla, int anchoPantalla) {
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
            bitmaps[i] = escalaAltura(bitmaps[i], altoPantalla / 4);
        }


        // Imagenes

        spriteRef = getBitmapFromAssets("sprite0.png");
        spriteRef = escalaAltura(spriteRef, altoPantalla / 6);
        character = new Character(bitmaps, 1, altoPantalla - (altoPantalla / 3) - spriteRef.getHeight(), anchoPantalla, altoPantalla);
        enemy1 = new Enemy(bitmaps, 200, altoPantalla - (altoPantalla / 3) - spriteRef.getHeight(), anchoPantalla, altoPantalla);
        background = getBitmapFromAssets("back2.png");
        background = ajustaAltura(background, screenHeight);
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
        timeSkipPast = getBitmapFromAssets("time_red.png");
        timeSkipPast = escalaAltura(timeSkipPast, altoPantalla / 6);
        timeSkipPresent = getBitmapFromAssets("time_white.png");
        timeSkipPresent = escalaAltura(timeSkipPresent, altoPantalla / 6);

        // Rectangulos
        lMoveBtn = new Rect(20, altoPantalla - 20 - botonL.getHeight(), botonL.getWidth() + 20, altoPantalla - 20);
        rMoveBtn = new Rect(60 + botonL.getWidth(), altoPantalla - 20 - botonR.getHeight(), botonR.getWidth() + 60 + botonL.getWidth(), altoPantalla - 20);
        actionBtn = new Rect(anchoPantalla - actionButton_B.getWidth() - 20, altoPantalla - 20 - botonR.getHeight(), anchoPantalla, altoPantalla);
        timeSkipBtn = new Rect(anchoPantalla - actionButton_B.getWidth() - 20 - actionButton_B.getWidth(), altoPantalla - 20 - botonR.getHeight() - actionButton_B.getHeight(), anchoPantalla, altoPantalla);
        backOptsBtn = new Rect(anchoPantalla - botonL.getWidth(), 0, anchoPantalla, botonL.getHeight());
        floor = new Rect(0, altoPantalla - (altoPantalla / 3), anchoPantalla, altoPantalla);
        wall = new Rect(screenWidth - spriteRef.getWidth(), 0, screenWidth, screenHeight);
        pilar = new Rect((screenWidth / 2) - screenWidth / 15, (screenHeight / 2) - screenHeight / 8, (screenWidth / 2) + screenWidth / 15, screenHeight);
        // Auxiliares




    }


    public void draw(Canvas c) {
        super.draw(c);
        if (presente) {
            c.drawColor(Color.BLUE);
            c.drawRect(lMoveBtn, invisiblePaint);
            c.drawRect(rMoveBtn, invisiblePaint);
            c.drawRect(actionBtn, invisiblePaint);
            c.drawRect(backOptsBtn, invisiblePaint);
            c.drawRect(floor, textPaint);
            c.drawRect(wall, textPaint);
            c.drawRect(pilar, textPaint);
        } else {
            c.drawColor(Color.BLUE);
            c.drawRect(lMoveBtn, invisiblePaint);
            c.drawRect(rMoveBtn, invisiblePaint);
            c.drawRect(actionBtn, invisiblePaint);
            c.drawRect(backOptsBtn, invisiblePaint);
            c.drawRect(floor, textPaint);
            c.drawRect(wall, textPaint);
            c.drawRect(pilar, textPaint);
        }

        charEnd = character.getX() + spriteRef.getWidth();
        character.dibuja(c);
        enemy1.dibuja(c);
        if (dialogStart == true && dialogEnd == false) {
            buttonsEnabled = false;
            c.drawBitmap(dialogBack, 0, screenHeight - dialogBack.getHeight(), null);
            c.drawBitmap(dialogArrow, screenWidth - actionButton_W.getWidth() - 20, screenHeight - 20 - botonR.getHeight(), null);
            switch (dialogCont) {
                case 1:
                    c.drawText(context.getResources().getString(R.string.dialog_02_01), dialogImg.getWidth() + 40, screenHeight - 150, textPaint);
                    break;
                case 2:
                    c.drawText(context.getResources().getString(R.string.dialog_02_02), dialogImg.getWidth() + 40, screenHeight - 150, textPaint);
                    break;
                case 3:
                    c.drawText(context.getResources().getString(R.string.dialog_02_03), dialogImg.getWidth() + 40, screenHeight - 150, textPaint);
                    break;
                case 4:
                    c.drawBitmap(dialogImg, -100, screenHeight - dialogImg.getHeight(), null);
                    c.drawText(context.getResources().getString(R.string.dialog_02_04), dialogImg.getWidth() + 40, screenHeight - 150, textPaint);
                    break;
            }
        } else {
            buttonsEnabled = true;
            c.drawBitmap(botonL, 20, screenHeight - 20 - botonL.getHeight(), null);
            c.drawBitmap(botonR, 60 + botonL.getWidth(), screenHeight - 20 - botonR.getHeight(), null);
            c.drawBitmap(backOptions, screenWidth - actionButton_W.getWidth(), 0, null);
            if (showActionRed) {
                c.drawBitmap(actionButton_B, screenWidth - actionButton_W.getWidth() - 20, screenHeight - 20 - botonR.getHeight(), null);
            } else {
                c.drawBitmap(actionButton_W, screenWidth - actionButton_W.getWidth() - 20, screenHeight - 20 - botonR.getHeight(), null);
            }
            if (!presente) {
                c.drawBitmap(timeSkipPast, screenWidth - actionButton_W.getWidth() - 20 - actionButton_B.getWidth(), screenHeight - 20 - botonR.getHeight() - actionButton_B.getHeight(), null);
            } else {
                c.drawBitmap(timeSkipPresent, screenWidth - actionButton_W.getWidth() - 20 - actionButton_B.getWidth(), screenHeight - 20 - botonR.getHeight() - actionButton_B.getHeight(), null);
            }
        }

    }


    public void updatePhysics() {
        super.updatePhysics();
        collisionSystem();





        enemy1.cambiaFrame();

        character.setVelocidad(-15);
        character.moverL();

        if (colisionD == false) {
            character.setVelocidad(-15);
            character.moverL();
        }

        if (character.stance) {
            character.cambiaFrame();
        } else {
            if (movementD) {
                character.cambiaFrame();
                if (colisionI == false) {
                    character.setVelocidad(15);
                    character.moverR();
                }
                colisionD = false;
            }
            if (movementI) {
                character.cambiaFrame();
                if (colisionD == false) {
                    character.setVelocidad(-15);
                    character.moverL();
                }
                colisionI = false;
            }
        }


    }


    public void collisionSystem() {

        if (character.getY() + spriteRef.getHeight() < floor.top || character.getX() + spriteRef.getWidth() > floor.right) {
            character.setVelocidad(40);
            character.moverY();
        }

        if (character.getY() > screenHeight) {
            Game_Control.sceneChange(11);
        }

        if (character.getX() + spriteRef.getWidth() > pilar.left && character.getX() + spriteRef.getWidth() < pilar.right) {
            showActionRed = true;
            pilarInteract = true;
        } else {
            showActionRed = false;
        }

    }

    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        int action = event.getActionMasked();
        int indice = event.getActionIndex();
        int x = (int) event.getX(indice);
        int y = (int) event.getY(indice);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (buttonsEnabled) {
                    if (rMoveBtn.contains(x, y)) {
                        Log.i("pulso ", "onTouchEvent: abajo");
                        movementD = true;
                        character.stance = false;
                    }
                    if (lMoveBtn.contains(x, y)) {
                        movementI = true;
                        character.stance = false;
                    }
                    if (backOptsBtn.contains(x, y)) {
                        editor = preferences.edit();
                        editor.putInt("lastScene", 11);
                        editor.commit();
                        Game_Control.sceneChange(2);
                    }
                }
                if (actionBtn.contains(x, y) && showActionRed == true) {
                    if (dialogStart && dialogCont == 4) {
                        dialogEnd = true;
                        presente = false;
                    }
                    dialogCont++;
                    dialogStart = true;
                    if (dialogEnd) {
                        if (preferences.getBoolean("Vibration", true)) {
                            Game_Control.v.vibrate(1000);
                        }
                        Log.i("doorOpen", "yes");
                        colisionI = false;
                        stoneClose = false;
                    }
                }

                if (timeSkipBtn.contains(x, y)) {
                    presente = !presente;
                }


                return true;

            case MotionEvent.ACTION_MOVE:
                Log.i("pulso ", "onTouchEvent: muevo");
                return true;

            case MotionEvent.ACTION_UP:
                Log.i("pulso ", "onTouchEvent: arriba");
                movementD = false;
                movementI = false;
                character.stance = true;
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


    public Bitmap ajustaAltura(Bitmap bitmapAux, int nuevoAlto) {
        if (nuevoAlto == bitmapAux.getHeight()) {
            return bitmapAux;
        }
        return bitmapAux.createScaledBitmap(bitmapAux, bitmapAux.getWidth(), nuevoAlto, true);
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

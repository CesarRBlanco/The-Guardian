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
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.MotionEvent;

import com.example.theguardian.Game.Character;
import com.example.theguardian.Game.Enemy;
import com.example.theguardian.Game.Game_Control;
import com.example.theguardian.Game.Scenario_Objects;
import com.example.theguardian.Game.Scene_Control;
import com.example.theguardian.R;

import java.io.IOException;
import java.io.InputStream;


public class Level_5_1 extends Scene_Control {


    int screenWidth, screenHeight;
    Context context;
    Paint textPaint, blackPaint;
    Bitmap botonL;
    Paint invisiblePaint;
    Character character;
    Bitmap background_past, background_present, botonR, spirals, spirals2, actionButton_W, actionButton_B, dialogImg,
            dialogBack, dialogArrow, spriteRef, timeSkipPresent, timeSkipPast, backOptions, pastFilter, doorSprite, keySprite,invisibleObject;
    Rect lMoveBtn, rMoveBtn, actionBtn, timeSkip_Btn, backOptsBtn, floor, floor_past, floor_past2, floor_past3, wallL, collapse;
    int charEnd, dialogCont = 0;
    boolean showActionRed = false;
    boolean dialogStart = false;
    boolean dialogEnd = false;
    boolean movementD = false;
    boolean movementI = false;
    boolean colisionI = false;
    boolean colisionD = false;
    boolean colisionY = false;
    boolean buttonsEnabled = true;
    boolean presente = true;
    boolean keyInteract = false;
    boolean keyTake = false;
    boolean enemyColision = false;
    boolean enemyCatch = false;
    boolean doorInteract = false;
    Scenario_Objects key, door;

    public Level_5_1(final Context context, int altoPantalla, int anchoPantalla) {
        super(context);
        this.context = context;
        screenWidth = anchoPantalla;
        screenHeight = altoPantalla;
        super.musicChange(2);


        invisiblePaint = new Paint();
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
        spriteRef = escalaAltura(spriteRef, altoPantalla / 4);
        character = new Character(bitmaps, screenWidth - 20 - spriteRef.getWidth(), 200, anchoPantalla, altoPantalla);
        background_past = getBitmapFromAssets("level_5_1_past.png");
        background_past = Bitmap.createScaledBitmap(background_past, screenWidth, screenHeight, false);
        background_present = getBitmapFromAssets("level_5_1_present.png");
        background_present = Bitmap.createScaledBitmap(background_present, screenWidth, screenHeight, false);
        pastFilter = getBitmapFromAssets("pastFilter.png");
        pastFilter = Bitmap.createScaledBitmap(pastFilter, screenWidth, screenHeight, false);
        spirals = getBitmapFromAssets("spiral_door.png");
        spirals = escalaAltura(spirals, altoPantalla / 3);
        spirals2 = getBitmapFromAssets("spiral_door.png");
        spirals2 = escalaAltura(spirals, altoPantalla / 3);
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
        doorSprite = getBitmapFromAssets("level_5_door.png");
        doorSprite = escalaAltura(doorSprite, altoPantalla / 4);
        keySprite = getBitmapFromAssets("generic_key.png");
        keySprite = escalaAltura(keySprite, screenHeight / 9);
        key = new Scenario_Objects(spriteRef.getWidth()-30, screenHeight - spriteRef.getHeight(), spriteRef.getWidth()+keySprite.getWidth()-30, screenHeight, keySprite,screenHeight,screenWidth);
        door = new Scenario_Objects(screenWidth / 4 - 20, screenHeight / 4 - 60, screenWidth / 4 - 20 + doorSprite.getWidth(), screenHeight / 4 - 60 + doorSprite.getHeight(), doorSprite,screenHeight,screenWidth);
        invisibleObject = getBitmapFromAssets("invisible_object.png");
        invisibleObject = Bitmap.createScaledBitmap(invisibleObject, anchoPantalla, altoPantalla, false);



        // Rectangulos
        lMoveBtn = new Rect(20, altoPantalla - 20 - botonL.getHeight(), botonL.getWidth() + 20, altoPantalla - 20);
        rMoveBtn = new Rect(60 + botonL.getWidth(), altoPantalla - 20 - botonR.getHeight(), botonR.getWidth() + 60 + botonL.getWidth(), altoPantalla - 20);
        actionBtn = new Rect(anchoPantalla - actionButton_B.getWidth() - 20, altoPantalla - 20 - botonR.getHeight(), anchoPantalla, altoPantalla);
        timeSkip_Btn = new Rect(anchoPantalla - timeSkipPresent.getWidth() - 20 - actionButton_B.getWidth(), altoPantalla - 20 - botonR.getHeight(), anchoPantalla - 20 - actionButton_B.getWidth(), altoPantalla);
        backOptsBtn = new Rect(anchoPantalla - botonL.getWidth(), 0, anchoPantalla, botonL.getHeight());
        wallL = new Rect(0, screenHeight / 2, spriteRef.getWidth() / 2, screenHeight);
        collapse = new Rect((screenWidth / 5), 0, ((screenWidth / 5) + spirals.getWidth()), screenHeight / 2);
        floor = new Rect(0, (screenHeight / 2), screenWidth, screenHeight);
        floor_past = new Rect(0, (screenHeight / 2), screenWidth - (screenWidth / 3) - 100, screenHeight);
        floor_past2 = new Rect(screenWidth - (screenWidth / 7) - 50, (screenHeight / 2), screenWidth, screenHeight);
        floor_past3 = new Rect(0, screenHeight - (screenHeight / 10), screenWidth, screenHeight);
        // Auxiliares


    }


    public void draw(Canvas c) {
        super.draw(c);
        if (!presente) {
            c.drawBitmap(background_past, 0, 0, null);
//            c.drawRect(timeSkip_Btn, textPaint);
            textPaint.setAlpha(50);
//            c.drawRect(floor_past, textPaint);
//            c.drawRect(floor_past2, textPaint);
//            c.drawRect(floor_past3, textPaint);
//            c.drawRect(collapse, textPaint);
            c.drawRect(wallL, textPaint);
            textPaint.setAlpha(100);
            door.draw(c);
        } else {

            c.drawBitmap(background_present, 0, 0, null);
//            c.drawRect(timeSkip_Btn, textPaint);
            textPaint.setAlpha(50);
//            c.drawRect(_floor, textPaint);
//            c.drawRect(collapse, textPaint);
            c.drawRect(wallL, textPaint);
            textPaint.setAlpha(100);
            if (!keyTake) {
                key.draw(c);
            }
        }


        charEnd = character.getX() + spriteRef.getWidth();
        character.dibuja(c);

        if (dialogStart == true && dialogEnd == false) {
            buttonsEnabled = false;
            c.drawBitmap(dialogBack, 0, screenHeight - dialogBack.getHeight(), null);
            c.drawBitmap(dialogArrow, screenWidth - actionButton_W.getWidth() - 20, screenHeight - 20 - botonR.getHeight(), null);
            switch (dialogCont) {
                case 1:
                    c.drawBitmap(dialogImg, -100, screenHeight - dialogImg.getHeight(), null);
                    c.drawText(context.getResources().getString(R.string.dialog_03_01), dialogImg.getWidth() + 40, screenHeight - 150, textPaint);
                    break;
            }


        } else {
            if (!presente) {
                c.drawBitmap(pastFilter, 0, 0, null);
            }

            c.drawBitmap(botonL, 20, screenHeight - 20 - botonL.getHeight(), null);
            c.drawBitmap(botonR, 60 + botonL.getWidth(), screenHeight - 20 - botonR.getHeight(), null);
            c.drawBitmap(backOptions, screenWidth - actionButton_W.getWidth(), 0, null);
            if (showActionRed) {
                c.drawBitmap(actionButton_B, screenWidth - actionButton_W.getWidth() - 20, screenHeight - 20 - botonR.getHeight(), null);
            } else {
                c.drawBitmap(actionButton_W, screenWidth - actionButton_W.getWidth() - 20, screenHeight - 20 - botonR.getHeight(), null);
            }
            if (presente) {
                c.drawBitmap(timeSkipPresent, screenWidth - actionButton_W.getWidth() - 20 - timeSkipPresent.getWidth(), screenHeight - 20 - botonR.getHeight(), null);
            } else {
                c.drawBitmap(timeSkipPast, screenWidth - actionButton_W.getWidth() - 20 - timeSkipPresent.getWidth(), screenHeight - 20 - botonR.getHeight(), null);

            }
        }

        c.drawText("Player:" + charEnd + " Floor" + screenWidth, 50, 50, textPaint);
    }


    public void updatePhysics() {
        super.updatePhysics();
        collisionSystem();

        if (colisionY == false) {
            character.setVelocidad(40);
            character.moverY();
        }

        if (Character.stance) {
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
        if (presente) {
            if (character.getBottom() >= floor.top ) {
                colisionY = true;

            }
        } else {
            if ((character.getBottom() >= floor_past.top && character.getX() <= floor_past.right) || (character.getBottom() >= floor_past.top && charEnd >= floor_past2.left)) {
                colisionY = true;
            } else {
                colisionY = character.getBottom() >= floor_past3.top;
            }

        }

        if ((character.getX() <= collapse.right) && character.getY() < screenHeight / 2) {
            colisionD = true;
        }

        if (character.getX() + spriteRef.getWidth() > collapse.left && character.getX() + spriteRef.getWidth() < collapse.right) {
            showActionRed = true;
            keyInteract = true;
        } else {
            showActionRed = false;
        }


        if (character.getX() <= wallL.right && character.getY() > screenHeight / 2) {
            colisionD = true;
        }

        if (character.getX() <= wallL.right && character.getY() < screenHeight / 2) {
            Game_Control.sceneChange(15);
        }

        if (charEnd >= screenWidth) {
            character.setX(screenWidth - spriteRef.getWidth() - 20);
            character.setY(screenHeight / 2 - (spriteRef.getHeight()));
        }

        if (character.getX() + spriteRef.getWidth() > key.getLeft() && character.getX() + spriteRef.getWidth() < key.getRight()) {
            showActionRed = true;
            keyInteract = true;
        } else {
            showActionRed = false;
        }

        if ((character.getX() <= door.getRight()) && character.getY() < screenHeight / 2) {
            doorInteract = true;
            colisionD = true;
            showActionRed = true;
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
                        Character.stance = false;
                    }
                    if (lMoveBtn.contains(x, y)) {
                        movementI = true;
                        Character.stance = false;
                    }
                    if (backOptsBtn.contains(x, y)) {
                        editor = preferences.edit();
                        editor.putInt("lastScene", 11);
                        editor.commit();
                        Game_Control.sceneChange(2);
                    }
                }
                if (actionBtn.contains(x, y) && keyInteract) {
                    keyTake = true;
                }

                if (actionBtn.contains(x, y) && doorInteract && keyTake) {
                    colisionD = false;
                    door = new Scenario_Objects(0, 0, 0, 0, invisibleObject,screenHeight,screenWidth);
                }


                if (timeSkip_Btn.contains(x, y)) {
                    presente = !presente;
                    if (presente) {
                        floor = new Rect(0, (screenHeight / 2), screenWidth, screenHeight);
                        collapse = new Rect((screenWidth / 5), 0, ((screenWidth / 5) + spirals.getWidth()), screenHeight / 2);
                    } else {
                        floor = new Rect(0, 0, 0, 0);
                        collapse = new Rect(0, 0, 0, 0);
                        colisionD = false;
                    }
                }


                return true;

            case MotionEvent.ACTION_MOVE:
                Log.i("pulso ", "onTouchEvent: muevo");
                return true;

            case MotionEvent.ACTION_UP:
                Log.i("pulso ", "onTouchEvent: arriba");
                movementD = false;
                movementI = false;
                Character.stance = true;
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
        return Bitmap.createScaledBitmap(bitmapAux, bitmapAux.getWidth(), nuevoAlto, true);
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

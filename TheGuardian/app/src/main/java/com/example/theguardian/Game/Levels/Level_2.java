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

import com.example.theguardian.Game.Character;
import com.example.theguardian.Game.Enemy;
import com.example.theguardian.Game.Game_Control;
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
    Bitmap background_past, background_present, botonR, luces, actionButton_W, actionButton_B, dialogImg, dialogBack, dialogArrow, spriteRef, timeSkipPresent, timeSkipPast, backOptions, pastFilter;
    Rect lMoveBtn, rMoveBtn, actionBtn, timeSkip_Btn, backOptsBtn, floor, wallR, wallL, pilar;
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
    boolean presente = false;
    boolean pilarInteract = false;
    boolean enemyColision = false;
    boolean enemyTouch = false;
    boolean enemyCatch = false;
    public int cont = 0;

    public static SensorManager mSensorManager;
    public static Sensor mAccelerometer;
    public static SensorEventListener sensorEventListener;
    static boolean shakeUpDown = true;
    int shake = 0;


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
        character = new Character(bitmaps, spriteRef.getWidth() +20, altoPantalla - (altoPantalla / 3) - spriteRef.getHeight(), anchoPantalla, altoPantalla);
        enemy1 = new Enemy(bitmaps, -200, altoPantalla - (altoPantalla / 3) - spriteRef.getHeight(), anchoPantalla, altoPantalla);
        background_past = getBitmapFromAssets("level_2_past.jpg");
        background_past = Bitmap.createScaledBitmap(background_past, screenWidth, screenHeight, false);
        background_present = getBitmapFromAssets("level_2_present.jpg");
        background_present = Bitmap.createScaledBitmap(background_present, screenWidth, screenHeight, false);
        pastFilter = getBitmapFromAssets("pastFilter.png");
        pastFilter = ajustaAltura(pastFilter, screenHeight);
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
        backOptsBtn = new Rect(anchoPantalla - botonL.getWidth(), 0, anchoPantalla, botonL.getHeight());
        floor = new Rect(0, altoPantalla - (altoPantalla / 3), anchoPantalla, altoPantalla);
        wallL = new Rect(0, 0, spriteRef.getWidth()-20, screenHeight);
        wallR = new Rect(screenWidth - spriteRef.getWidth(), 0, screenWidth, screenHeight);
        pilar = new Rect((screenWidth / 2) - screenWidth / 15, (screenHeight / 2) - screenHeight / 8, (screenWidth / 2) + screenWidth / 15, screenHeight);
        // Auxiliares


        mSensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float y = event.values[1];
                float x = event.values[0];
                float z = event.values[2];
                Log.i("acele","x:"+x);

                if (enemyCatch) {
                    if (x > 2 && shakeUpDown) {
                        shake++;
//                    Log.i("acelero", "arriba");
                        shakeUpDown = false;
                    } else if (x < 0 && !shakeUpDown) {
//                    Log.i("acelero", "abajo");
                        shake++;
                        shakeUpDown = true;
                    }
                    if (shake == 4) {
                        Log.i("acelero", "shake");
                        shake = 0;
                        enemy1.setX(-2000);
                        buttonsEnabled = true;
                        enemyCatch = false;
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

    }

    private void start() {

        mSensorManager.registerListener(sensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }


    public void draw(Canvas c) {
        super.draw(c);
        if (presente) {
            c.drawBitmap(background_present, 0, 0, null);
        } else {

            c.drawBitmap(background_past, 0, 0, null);
        }

//            c.drawRect(rMoveBtn, invisiblePaint);
//            c.drawRect(actionBtn, invisiblePaint);
//            c.drawRect(backOptsBtn, invisiblePaint);

//            c.drawRect(floor, textPaint);
//            c.drawRect(wallR, textPaint);
//            c.drawRect(wallL, textPaint);


        charEnd = character.getX() + spriteRef.getWidth();
        character.dibuja(c);

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

            if (dialogEnd) {

                enemy1.dibuja(c);
            }

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
        }
        int sum = enemy1.getX() + spriteRef.getWidth();
        c.drawText("Enemy:" + sum + " Player" + character.getX(), 50, 50, textPaint);
    }


    public void updatePhysics() {
        super.updatePhysics();
        collisionSystem();

        start();

        if (enemyCatch) {
            cont++;
            Log.i("cont", "" + cont);
            if (cont >= 200) {
                Log.i("cont", "Moriste");
            Game_Control.sceneChange(1);
            }
        }

        if (!enemyColision && dialogEnd) {
            enemy1.setVelocidad(15);
            enemy1.moverR();

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

        if (character.getY() + spriteRef.getHeight() < floor.top || character.getX() + spriteRef.getWidth() > floor.right) {
            character.setVelocidad(40);
            character.moverY();
        }

        if (character.getY() > screenHeight) {
            Game_Control.sceneChange(11);
        }

        if (charEnd >= wallR.left ) {
            colisionI = true;
        }

        if (character.getX() <= wallL.right) {
            colisionD = true;
        }

        if (character.getX() + spriteRef.getWidth() > pilar.left && character.getX() + spriteRef.getWidth() < pilar.right) {
            showActionRed = true;
            pilarInteract = true;
        } else {
            showActionRed = false;
        }

        if (enemy1.getX() + spriteRef.getWidth() > character.getX()) {
            enemyColision = true;
            buttonsEnabled = false;
            enemyCatch = true;
        }

        if(character.getX()<=0){
            Game_Control.sceneChange(12);
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
                if (actionBtn.contains(x, y) && showActionRed == true) {
                    if (dialogStart && dialogCont == 4) {
                        dialogEnd = true;
                        buttonsEnabled = true;
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
                        presente = true;
                        wallL=new Rect(0,0,0,0);
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

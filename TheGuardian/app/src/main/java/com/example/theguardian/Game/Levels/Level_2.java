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


    public Level_2(final Context context, int altoPantalla, int anchoPantalla) {
        super(context);
        this.context = context;
        _screenWidth = anchoPantalla;
        _screenHeight = altoPantalla;
        super.musicChange(2);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(60);
        Bitmap[] bitmaps = new Bitmap[3];
        for (int i = 0; i < bitmaps.length; i++) {
            bitmaps[i] = getBitmapFromAssets("sprite" + i + ".png");
            bitmaps[i] = escalaAltura(bitmaps[i], altoPantalla / 4);
        }


        // Imagenes

        _playerSprite = getBitmapFromAssets("sprite0.png");
        _playerSprite = escalaAltura(_playerSprite, altoPantalla / 6);
        _player = new Character(bitmaps, _playerSprite.getWidth() + 20, altoPantalla - (altoPantalla / 3) - _playerSprite.getHeight(), anchoPantalla, altoPantalla);
        _enemy = new Enemy(bitmaps, -200, altoPantalla - (altoPantalla / 3) - _playerSprite.getHeight(), anchoPantalla, altoPantalla);
        _backgroundPast = getBitmapFromAssets("level_2_past.jpg");
        _backgroundPast = Bitmap.createScaledBitmap(_backgroundPast, _screenWidth, _screenHeight, false);
        _backgroundPresent = getBitmapFromAssets("level_2_present.jpg");
        _backgroundPresent = Bitmap.createScaledBitmap(_backgroundPresent, _screenWidth, _screenHeight, false);
        pastFilter = getBitmapFromAssets("pastFilter.png");
        pastFilter = ajustaAltura(pastFilter, _screenHeight);
        imgMove_L = getBitmapFromAssets("movement.png");
        imgMove_L = escalaAltura(imgMove_L, altoPantalla / 6);
        imgMove_L = espejo(imgMove_L, true);
        imgMove_R = getBitmapFromAssets("movement.png");
        imgMove_R = escalaAltura(imgMove_R, altoPantalla / 6);
        imgAction_W = getBitmapFromAssets("actionButtonWhite.png");
        imgAction_W = escalaAltura(imgAction_W, altoPantalla / 6);
        imgAction_R = getBitmapFromAssets("actionButtonBlack.png");
        imgAction_R = escalaAltura(imgAction_R, altoPantalla / 6);
        imgDialog = getBitmapFromAssets("dialog.png");
        imgDialog = escalaAltura(imgDialog, altoPantalla / 2);
        imgDialogBckgrnd = getBitmapFromAssets("dialog_background.png");
        imgDialogBckgrnd = escalaAncho(imgDialogBckgrnd, anchoPantalla);
        imgDialogAction = getBitmapFromAssets("dialog_arrow.png");
        imgDialogAction = escalaAltura(imgDialogAction, altoPantalla / 6);
        imgOptions = getBitmapFromAssets("backOptions.png");
        imgOptions = escalaAltura(imgOptions, altoPantalla / 6);

        // Rectangulos
        btnMove_L = new Rect(20, altoPantalla - 20 - imgMove_L.getHeight(), imgMove_L.getWidth() + 20, altoPantalla - 20);
        btnMove_R = new Rect(60 + imgMove_L.getWidth(), altoPantalla - 20 - imgMove_R.getHeight(), imgMove_R.getWidth() + 60 + imgMove_L.getWidth(), altoPantalla - 20);
        btnAction = new Rect(anchoPantalla - imgAction_R.getWidth() - 20, altoPantalla - 20 - imgMove_R.getHeight(), anchoPantalla, altoPantalla);
        btnOptions = new Rect(anchoPantalla - imgMove_L.getWidth(), 0, anchoPantalla, imgMove_L.getHeight());
        _floor = new Rect(0, altoPantalla - (altoPantalla / 3), anchoPantalla, altoPantalla);
        _wall_L = new Rect(0, 0, _playerSprite.getWidth() - 20, _screenHeight);
        _wall_R = new Rect(_screenWidth - _playerSprite.getWidth(), 0, _screenWidth, _screenHeight);
        _interact = new Rect((_screenWidth / 2) - _screenWidth / 15, (_screenHeight / 2) - _screenHeight / 8, (_screenWidth / 2) + _screenWidth / 15, _screenHeight);

        // Auxiliares
        present = false;
        mSensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float y = event.values[1];
                float x = event.values[0];
                float z = event.values[2];
                Log.i("acele", "x:" + x);

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
                        _enemy.setX(-2000);
                        btnsEnabled = true;
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
        if (present) {
            c.drawBitmap(_backgroundPresent, 0, 0, null);
        } else {
            c.drawBitmap(_backgroundPast, 0, 0, null);
        }

        _player.dibuja(c);

        if (dialogStart && !dialogEnd) {
            btnsEnabled = false;
            c.drawBitmap(imgDialogBckgrnd, 0, _screenHeight - imgDialogBckgrnd.getHeight(), null);
            c.drawBitmap(imgDialogAction, _screenWidth - imgAction_W.getWidth() - 20, _screenHeight - 20 - imgMove_R.getHeight(), null);
            switch (dialogCont) {
                case 1:
                    c.drawText(context.getResources().getString(R.string.dialog_02_01), imgDialog.getWidth() + 40, _screenHeight - 150, textPaint);
                    break;
                case 2:
                    c.drawText(context.getResources().getString(R.string.dialog_02_02), imgDialog.getWidth() + 40, _screenHeight - 150, textPaint);
                    break;
                case 3:
                    c.drawText(context.getResources().getString(R.string.dialog_02_03), imgDialog.getWidth() + 40, _screenHeight - 150, textPaint);
                    break;
                case 4:
                    c.drawBitmap(imgDialog, -100, _screenHeight - imgDialog.getHeight(), null);
                    c.drawText(context.getResources().getString(R.string.dialog_02_04), imgDialog.getWidth() + 40, _screenHeight - 150, textPaint);
                    break;
            }


        } else {

            if (dialogEnd) {

                _enemy.dibuja(c);
            }

            if (!present) {

                c.drawBitmap(pastFilter, 0, 0, null);
            }

            c.drawBitmap(imgMove_L, 20, _screenHeight - 20 - imgMove_L.getHeight(), null);
            c.drawBitmap(imgMove_R, 60 + imgMove_L.getWidth(), _screenHeight - 20 - imgMove_R.getHeight(), null);
            c.drawBitmap(imgOptions, _screenWidth - imgAction_W.getWidth(), 0, null);
            if (showActionRed) {
                c.drawBitmap(imgAction_R, _screenWidth - imgAction_W.getWidth() - 20, _screenHeight - 20 - imgMove_R.getHeight(), null);
            } else {
                c.drawBitmap(imgAction_W, _screenWidth - imgAction_W.getWidth() - 20, _screenHeight - 20 - imgMove_R.getHeight(), null);
            }
        }
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
            _enemy.setVelocidad(15);
            _enemy.moverR();

        }


            if (_movementR) {
                _player.cambiaFrame();
                if (_collisionL == false) {
                    _player.setVelocidad(15);
                    _player.moverR();
                }
                _collisionR = false;
            }
            if (_movementL) {
                _player.cambiaFrame();
                if (_collisionR == false) {
                    _player.setVelocidad(-15);
                    _player.moverL();
                }
                _collisionL = false;
            }
        }





    public void collisionSystem() {

        if (_player.getY() + _playerSprite.getHeight() < _floor.top || _player.getX() + _playerSprite.getWidth() > _floor.right) {
            _player.setVelocidad(40);
            _player.moverY();
        }

        if (_player.getY() > _screenHeight) {
            Game_Control.sceneChange(11);
        }

        if (_player.getRight() >= _wall_R.left) {
            _collisionL = true;
        }

        if (_player.getX() <= _wall_L.right) {
            _collisionR = true;
        }

        if (_player.getX() + _playerSprite.getWidth() > _interact.left && _player.getX() + _playerSprite.getWidth() < _interact.right) {
            showActionRed = true;
            _enableInteraction = true;
        } else {
            showActionRed = false;
        }

        if (_enemy.getX() + _playerSprite.getWidth() > _player.getX()) {
            enemyColision = true;
            btnsEnabled = false;
            enemyCatch = true;
        }

        if (_player.getX() <= 0) {
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
                if (btnsEnabled) {
                    if (btnMove_R.contains(x, y)) {
                        Log.i("pulso ", "onTouchEvent: abajo");
                        _movementR = true;

                    }
                    if (btnMove_L.contains(x, y)) {
                        _movementL = true;

                    }
                    if (btnOptions.contains(x, y)) {
                        editor = preferences.edit();
                        editor.putInt("lastScene", 11);
                        editor.apply();
                        Game_Control.sceneChange(2);
                    }
                }
                if (btnAction.contains(x, y) && showActionRed) {
                    if (dialogStart && dialogCont == 4) {
                        dialogEnd = true;
                        btnsEnabled = true;
                    }
                    dialogCont++;
                    dialogStart = true;
                    if (dialogEnd) {
                        if (preferences.getBoolean("Vibration", true)) {
                            Game_Control.v.vibrate(1000);
                        }
                        Log.i("doorOpen", "yes");
                        _collisionL = false;
                        stoneClose = false;
                        present = true;
                        _wall_L = new Rect(0, 0, 0, 0);
                    }
                }


                return true;

            case MotionEvent.ACTION_MOVE:
                Log.i("pulso ", "onTouchEvent: muevo");
                return true;

            case MotionEvent.ACTION_UP:
                Log.i("pulso ", "onTouchEvent: arriba");
                _movementR = false;
                _movementL = false;
                _player.stance = true;
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

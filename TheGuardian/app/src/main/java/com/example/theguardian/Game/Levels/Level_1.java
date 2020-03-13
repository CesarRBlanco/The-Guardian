package com.example.theguardian.Game.Levels;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.view.MotionEvent;

import com.example.theguardian.Game.Character;
import com.example.theguardian.Game.Game_Control;
import com.example.theguardian.Game.Scenario_Objects;
import com.example.theguardian.Game.Scene_Control;
import com.example.theguardian.R;

import java.io.IOException;
import java.io.InputStream;


public class Level_1 extends Scene_Control {


    public Level_1(Context context, int _screenHeight, int _screenWidth) {
        super(context);
        this.context = context;
        super.musicChange(2);

        this._screenHeight = _screenHeight;
        this._screenWidth = _screenWidth;

        invisiblePaint = new Paint();
        invisiblePaint.setColor(Color.argb(0, 0, 0, 0));
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(60);
        Bitmap[] bitmaps = new Bitmap[2];
        for (int i = 0; i < bitmaps.length; i++) {
            bitmaps[i] = getBitmapFromAssets("player" + i + ".png");
            bitmaps[i] = escalaAltura(bitmaps[i], _screenHeight / 4);
        }


        // Imagenes
        _player = new Character(bitmaps, 1,
                _screenHeight / 2 + 50, _screenWidth, _screenHeight);
        _playerSprite = getBitmapFromAssets("playerStance.png");
        _playerSprite = escalaAltura(_playerSprite, _screenHeight / 4);
        _background = getBitmapFromAssets("level_1.png");
        _background = Bitmap.createScaledBitmap(_background, _screenWidth, _screenHeight, false);
        imgMove_L = getBitmapFromAssets("movement.png");
        imgMove_L = escalaAltura(imgMove_L, _screenHeight / 6);
        imgMove_L = espejo(imgMove_L, true);
        imgMove_R = getBitmapFromAssets("movement.png");
        imgMove_R = escalaAltura(imgMove_R, _screenHeight / 6);
        imgAction_W = getBitmapFromAssets("actionButtonWhite.png");
        imgAction_W = escalaAltura(imgAction_W, _screenHeight / 6);
        imgAction_R = getBitmapFromAssets("actionButtonBlack.png");
        imgAction_R = escalaAltura(imgAction_R, _screenHeight / 6);
        imgCharacDialog = getBitmapFromAssets("dialog.png");
        imgCharacDialog = escalaAltura(imgCharacDialog, _screenHeight / 2);
        imgDialogBckgrnd = getBitmapFromAssets("dialog_background.png");
        imgDialogBckgrnd = escalaAncho(imgDialogBckgrnd, _screenWidth);
        imgDialogAction = getBitmapFromAssets("dialog_arrow.png");
        imgDialogAction = escalaAltura(imgDialogAction, _screenHeight / 6);
        imgOptions = getBitmapFromAssets("backOptions.png");
        imgOptions = escalaAltura(imgOptions, _screenHeight / 6);
        box = getBitmapFromAssets("stoneDoor.png");
        box = escalaAltura(box, _screenHeight / 2);
        invisibleObject = getBitmapFromAssets("invisible_object.png");
        invisibleObject = Bitmap.createScaledBitmap(invisibleObject, _screenWidth, _screenHeight, false);


        // Rectangulos
        btnMove_L = new Rect(20, _screenHeight - 20 - imgMove_L.getHeight(),
                imgMove_L.getWidth() + 20, _screenHeight - 20);
        btnMove_R = new Rect(60 + imgMove_L.getWidth(), _screenHeight - 20 - imgMove_R.getHeight(),
                imgMove_R.getWidth() + 60 + imgMove_L.getWidth(), _screenHeight - 20);
        btnAction = new Rect(_screenWidth - imgAction_R.getWidth() - 20,
                _screenHeight - 20 - imgMove_R.getHeight(), _screenWidth, _screenHeight);
        btnOptions = new Rect(_screenWidth - imgMove_L.getWidth(), 0, _screenWidth,
                imgMove_L.getHeight());
        _floor = new Rect(0, _player.getBottom(),
                _screenWidth - (_screenWidth / 5), _screenHeight);

        // Objects
        objStoneDoor = new Scenario_Objects(_screenWidth / 2 + (_screenWidth / 20),
                _screenHeight / 3 - 50, _screenWidth - (_screenWidth / 4),
                _screenHeight - (_screenHeight / 4), box, _screenHeight, _screenWidth);

        // Auxiliares
        AudioAttributes attrs = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        sp = new SoundPool.Builder()
                .setMaxStreams(10)
                .setAudioAttributes(attrs)
                .build();
        soundIds = new int[1];
        soundIds[0] = sp.load(context, R.raw.effect, 1);
        stoneClose = true;

    }


    public void draw(Canvas c) {
        super.draw(c);

        c.drawBitmap(_background, 0, 0, null);

        _player.dibuja(c);
        objStoneDoor.draw(c);

        if (dialogStart && !dialogEnd) {
            btnsEnabled = false;
            c.drawBitmap(imgDialogBckgrnd, 0, _screenHeight - imgDialogBckgrnd.getHeight(), null);
            c.drawBitmap(imgCharacDialog, -100, _screenHeight - imgCharacDialog.getHeight(), null);
            c.drawBitmap(imgDialogAction, _screenWidth - imgAction_W.getWidth() - 20,
                    _screenHeight - 20 - imgMove_R.getHeight(), null);
            switch (dialogCont) {
                case 1:
                    c.drawText(context.getResources().getString(R.string.dialog_01_01),
                            imgCharacDialog.getWidth() + 40, _screenHeight - 150, textPaint);
                    break;
                case 2:
                    c.drawText(context.getResources().getString(R.string.dialog_01_02),
                            imgCharacDialog.getWidth() + 40, _screenHeight - 150, textPaint);
                    break;
            }
        } else {
            btnsEnabled = true;
            c.drawBitmap(imgOptions, _screenWidth - imgAction_W.getWidth(), 0, null);
            c.drawBitmap(imgMove_L, 20, _screenHeight - 20 - imgMove_L.getHeight(), null);
            c.drawBitmap(imgMove_R, 60 + imgMove_L.getWidth(),
                    _screenHeight - 20 - imgMove_R.getHeight(), null);
            if (showActionRed) {
                c.drawBitmap(imgAction_R, _screenWidth - imgAction_W.getWidth() - 20,
                        _screenHeight - 20 - imgMove_R.getHeight(), null);
            } else {
                c.drawBitmap(imgAction_W, _screenWidth - imgAction_W.getWidth() - 20,
                        _screenHeight - 20 - imgMove_R.getHeight(), null);
            }
        }
        if (!stoneClose) {
            c.drawColor(Color.WHITE);
        }
    }

    public void updatePhysics() {
        super.updatePhysics();
        collisionSystem();
        if (Character.stance) {
            _player.cambiaFrame();
        } else {
            if (_movementR) {
                _player.cambiaFrame();
                if (!_collisionR) {
                    _player.setVelocidad(15);
                    _player.moverR();
                }
                _collisionL = false;
            }
            if (_movementL) {
                _player.cambiaFrame();
                if (!_collisionL) {
                    _player.setVelocidad(-15);
                    _player.moverL();
                }
                _collisionR = false;
            }
        }
    }

    public void collisionSystem() {

        if (_player.getBottom() < _floor.top || _player.getRight() > _floor.right) {
            _player.setVelocidad(40);
            _player.moverY();
        }
        if (_player.getRight() >= objStoneDoor.getLeft() && stoneClose) {
            _collisionR = true;
            showActionRed = true;
        } else {
            showActionRed = false;
        }
        if (_player.getY() > _screenHeight) {
            Game_Control.sceneChange(11);
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
                        _movementR = true;
                        Character.stance = false;
                    }
                    if (btnMove_L.contains(x, y)) {
                        _movementL = true;
                        Character.stance = false;
                    }
                    if (btnOptions.contains(x, y)) {
                        editor = preferences.edit();
                        editor.putInt("lastScene", 10);
                        editor.apply();
                        Game_Control.sceneChange(2);
                    }
                }
                if (btnAction.contains(x, y) && showActionRed) {
                    if (dialogStart && dialogCont == 2) {
                        dialogEnd = true;
                    }
                    dialogCont++;
                    dialogStart = true;
                    if (dialogEnd) {
                        if (preferences.getBoolean("Vibration", true)) {
                            Game_Control.v.vibrate(1000);
                        }
//                        sp.play(soundIds[0], preferences.getInt("SoundVolume",1), preferences.getInt("SoundVolume",1), 1, 0, 1);

                        _collisionR = false;
                        stoneClose = false;
                        objStoneDoor = new Scenario_Objects(0, 0, 0, 0, invisibleObject, _screenHeight, _screenWidth);

                        stoneClose = false;
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Game_Control.sceneChange(11);
                    }
                }
                return true;

            case MotionEvent.ACTION_MOVE:
                return true;

            case MotionEvent.ACTION_UP:
                _movementR = false;
                _movementL = false;
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

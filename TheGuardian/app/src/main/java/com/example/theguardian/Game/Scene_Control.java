package com.example.theguardian.Game;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.theguardian.R;

import static com.example.theguardian.Game.Game_Control.context;

public class Scene_Control {

    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;
    public static MediaPlayer mediaPlayer;
    public boolean modal = false;
    public static SoundPool sp;
    public static int[] soundIds;
    public int _screenWidth = 0, _screenHeight = 0, dialogCont = 0;
    public Context context;
    public Paint textPaint, invisiblePaint;
    public Bitmap imgMove_L, imgMove_R, _background, imgAction_W, imgAction_R, imgCharacDialog,
            imgDialog, imgDialogBckgrnd, imgDialogAction, _playerSprite, box, imgOptions, invisibleObject, _backgroundPast, _backgroundPresent, pastFilter;
    public Rect btnMove_L, btnMove_R, btnAction, btnOptions, _floor, _wall_L, _wall_R, _interact;
    public Character _player;
    public Enemy _enemy;
    public Scenario_Objects objStoneDoor;
    public boolean showActionRed;
    public boolean dialogStart;
    public boolean dialogEnd;
    public boolean _movementR;
    public boolean _movementL;
    public boolean _collisionR;
    public boolean _collisionL;
    public boolean stoneClose;
    public boolean present;
    public boolean btnsEnabled;
    public boolean _enableInteraction;
    public static boolean gem1Take;
    public static boolean gem2Take;
    public boolean enemyColision = false;
    public boolean enemyTouch = false;
    public boolean enemyCatch = false;
    public int cont = 0;

    public static SensorManager mSensorManager;
    public static Sensor mAccelerometer;
    public static SensorEventListener sensorEventListener;
    public static boolean shakeUpDown = true;
    public int shake = 0;


    public Scene_Control(Context context) {

        preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);

    }

    public void draw(Canvas c) {

    }


    public void updatePhysics() {

    }


    public void musicChange(int song) {
//        Log.i("mucadasd", "musicChange: " + song);
//        switch (song) {
//            case 1:
//                if (mediaPlayer != null && mediaPlayer.isPlaying()) mediaPlayer.stop();
//                mediaPlayer = MediaPlayer.create(context, R.raw.music2);
//                mediaPlayer.setVolume(preferences.getInt("MusicVolume", 1), preferences.getInt("MusicVolume", 1));
//                break;
//            case 2:
//                if (mediaPlayer != null && mediaPlayer.isPlaying()) mediaPlayer.stop();
//                mediaPlayer = MediaPlayer.create(context, R.raw.music);
//                mediaPlayer.setVolume(preferences.getInt("MusicVolume", 1), preferences.getInt("MusicVolume", 1));
//                break;
//        }
//        mediaPlayer.start();
    }

    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }


}

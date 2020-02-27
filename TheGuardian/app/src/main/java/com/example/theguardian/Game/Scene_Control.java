package com.example.theguardian.Game;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
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
    public boolean menu = false;
    public static SoundPool sp;
    public static int soundIds[];

    public Scene_Control(Context context) {

        preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);

    }

    public void draw(Canvas c) {

    }


    public void updateConfig() {


//        int newScene = preferences.getInt("actualScene", 1);
//        Game_Control.sceneChange(newScene);
//
//        int musicVol = preferences.getInt("MusicVolume", 1);
//        Log.i("VOLGAME", musicVol + "");
//        mediaPlayer.setVolume(musicVol, musicVol);
    }


    public void updatePhysics() {

    }


    public void musicChange(int song) {
        Log.i("mucadasd", "musicChange: " + song);
        switch (song) {
            case 1:
                if (mediaPlayer != null && mediaPlayer.isPlaying()) mediaPlayer.stop();
                mediaPlayer = MediaPlayer.create(context, R.raw.music2);
                mediaPlayer.setVolume(preferences.getInt("MusicVolume", 1), preferences.getInt("MusicVolume", 1));
                break;
            case 2:
                if (mediaPlayer != null && mediaPlayer.isPlaying()) mediaPlayer.stop();
                mediaPlayer = MediaPlayer.create(context, R.raw.music);
                mediaPlayer.setVolume(preferences.getInt("MusicVolume", 1), preferences.getInt("MusicVolume", 1));
                break;
        }
        mediaPlayer.start();
    }

    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }


}

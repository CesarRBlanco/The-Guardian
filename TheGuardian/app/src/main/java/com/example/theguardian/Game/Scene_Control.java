package com.example.theguardian.Game;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.theguardian.R;

import static com.example.theguardian.Game.Game_Control.context;

public class Scene_Control {


    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;
    public static MediaPlayer mediaPlayer;

    public Scene_Control(Context context) {

        preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
    }

    public void draw(Canvas c) {

    }

    public void updatePhysics() {

<<<<<<< 5c6b95f90e38d7f9c98dd4efbec2f9c92f6a3c01
<<<<<<< 5c6b95f90e38d7f9c98dd4efbec2f9c92f6a3c01
        int newScene = preferences.getInt("actualScene",1);
=======



        int newScene = preferences.getInt("actualScene", 1);
>>>>>>> [Movement works and Gravity 0.1]
        Game_Control.sceneChange(newScene);

        int musicVol = preferences.getInt("MusicVolume", 1);
        Log.i("VOLGAME", musicVol + "");
        mediaPlayer.setVolume(musicVol, musicVol);
=======



//        int newScene = preferences.getInt("actualScene", 1);
//        Game_Control.sceneChange(newScene);
//
//        int musicVol = preferences.getInt("MusicVolume", 1);
//        Log.i("VOLGAME", musicVol + "");
//        mediaPlayer.setVolume(musicVol, musicVol);
>>>>>>> [Level 1 almost complete]**
    }


    public void musicChange(int song) {
        Log.i("mucadasd", "musicChange: " + song);
        switch (song) {
            case 1:
                if (mediaPlayer != null && mediaPlayer.isPlaying()) mediaPlayer.stop();
                mediaPlayer = MediaPlayer.create(context, R.raw.music2);
                break;
            case 2:
                if (mediaPlayer != null && mediaPlayer.isPlaying()) mediaPlayer.stop();
                mediaPlayer = MediaPlayer.create(context, R.raw.music);
                break;
        }
        mediaPlayer.start();
    }

    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }


}

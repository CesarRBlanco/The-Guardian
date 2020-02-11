package com.example.theguardian.Game;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;

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
        int musicVol = preferences.getInt("MusicVolume", 1);
        Log.i("VOLGAME", musicVol + "");
        mediaPlayer.setVolume(musicVol, musicVol);
    }


    public void musicChange(int song) {
        Log.i("mucadasd", "musicChange: "+song);
        switch (song) {
            case 1:
                if (mediaPlayer!=null && mediaPlayer.isPlaying())mediaPlayer.stop();
                mediaPlayer = MediaPlayer.create(context, R.raw.music2);
                break;
            case 2:
                if (mediaPlayer!=null && mediaPlayer.isPlaying())mediaPlayer.stop();
                mediaPlayer=MediaPlayer.create(context,R.raw.music);
                break;
        }
        mediaPlayer.start();
    }

    public int onTouchEvent(MotionEvent event) {

        return 1;
    }
}

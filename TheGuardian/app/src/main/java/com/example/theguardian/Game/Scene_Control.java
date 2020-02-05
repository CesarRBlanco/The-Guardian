package com.example.theguardian.Game;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.view.MotionEvent;

public class Scene_Control {


    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;

    public Scene_Control(Context context) {

        preferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
    }

    public void draw(Canvas c) {

    }

    public void updatePhysics() {

    }

    public int onTouchEvent(MotionEvent event) {

        return 1;
    }
}

package com.example.theguardian.Game.Menus;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Escena {

    int widhtScreen;
    int heightScreen;

    public Escena(Context context,int widthScreen,int heightScreen) {
this.widhtScreen=widthScreen;
this.heightScreen=heightScreen;
    }

    public Escena(Context context) {
    }

    public void draw(Canvas c) {


    }
public void actualizaFisica(){

    }



    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.anchoPantalla = width;
        this.altoPantalla = height;
        Log.i("ancho2", width + ":" + height);
        inicializao();

    }
}

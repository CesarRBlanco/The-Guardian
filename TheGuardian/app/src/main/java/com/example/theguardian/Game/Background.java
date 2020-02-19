package com.example.theguardian.Game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Background {
    Bitmap img;
    Bitmap img2;
    int y;


    int x1, x2;

    int velocidad;
    int anchoPantalla;

    public void setDerecha(boolean derecha) {
        this.derecha = derecha;
    }

    boolean derecha = true;

    public Background(Bitmap img, int y, int velocidad, int anchoPantalla) {
        this.img = img;
        this.img2 = espejo(img, true);
        this.y = y;
        this.x1 = 0;
        this.x2 = img.getWidth();
        this.velocidad = velocidad;
        this.anchoPantalla = anchoPantalla;
    }


    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }


    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }


    public void move() {
        if (derecha) {
            x1 += velocidad;
            x2 += velocidad;
            if (x1 > anchoPantalla) {
                x1 = x2 - img.getWidth();
            }
            if (x2 > anchoPantalla) {
                x2 = x1 - img.getWidth();
            }
        }
    }


    public void dibuja(Canvas c) {
        c.drawBitmap(img, x1, y, null);

    }


    public Bitmap espejo(Bitmap imagen, Boolean horizontal) {
        Matrix matrix = new Matrix();
        if (horizontal) matrix.preScale(-1, 1);
        else matrix.preScale(1, -1);
        return Bitmap.createBitmap(imagen, 0, 0, imagen.getWidth(),
                imagen.getHeight(), matrix, false);
    }
}


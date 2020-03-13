package com.example.theguardian.Game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;





public class Scenario_Objects {

    int top, left, right, bottom, x, y, velocidad = 10, altoPantalla, anchoPantalla;
    Bitmap sprite;
    Rect hitbox;
    int x1, x2;
    public void setDerecha(boolean derecha) {
        this.derecha = derecha;
    }

    boolean derecha = true;



    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    Paint pincelRect;
//    int tickFrame = 100;
//    long tiempoFrame;
//    int tickVelocidad = 30;
//    long tiempoVelocidad;


    public Scenario_Objects(int left, int top, int right, int bottom, Bitmap sprite,int altoPantalla, int anchoPantalla) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.sprite = sprite;
        this.hitbox = new Rect(left, top, right, bottom);
        this.x = left;
        this.y = top;
pincelRect=new Paint();
        pincelRect.setColor(Color.RED);
        pincelRect.setStyle(Paint.Style.STROKE);
        pincelRect.setStrokeWidth(10);
        this.altoPantalla = altoPantalla;
        this.anchoPantalla = anchoPantalla;
    }

    public void hitboxRefresh() {
        this.hitbox = new Rect(x, y, x + sprite.getWidth(), y + sprite.getHeight());
    }




    public void draw(Canvas c) {
        c.drawBitmap(sprite, x, y, null);
        c.drawRect(hitbox,pincelRect);
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }




}

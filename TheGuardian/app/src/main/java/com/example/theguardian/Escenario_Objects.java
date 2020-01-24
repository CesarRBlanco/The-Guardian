package com.example.theguardian;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Escenario_Objects {

    int top, left, right, bottom, x, y, velocidad = 10, altoPantalla, anchoPantalla;
    Bitmap sprite;
    Rect hitbox;
    Paint p=new Paint(Color.RED);
    int tickFrame = 100;
    long tiempoFrame;
    int tickVelocidad = 30;
    long tiempoVelocidad;

    public Escenario_Objects(int altoPantalla, int anchoPantalla) {
        this.altoPantalla = altoPantalla;
        this.anchoPantalla = anchoPantalla;
    }

    public Escenario_Objects(int left, int top, int right, int bottom, Bitmap sprite) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.sprite = sprite;
        this.hitbox = new Rect(left, top, right, bottom);
        this.x = left;
        this.y = top;
        tiempoFrame = System.currentTimeMillis();
        tiempoVelocidad = System.currentTimeMillis();
    }

    public void hitboxRefresh() {
        this.hitbox = new Rect(x, y, x + sprite.getWidth(), y + sprite.getHeight());
    }

    public void move() {
        if (System.currentTimeMillis() - tiempoVelocidad > tickVelocidad) {

            this.x += velocidad;
            if (this.x > anchoPantalla -sprite.getWidth()) {
                this.x = anchoPantalla -sprite.getWidth();

                velocidad = 0;
            }
            if (this.y > altoPantalla) this.y = 0;
            if (this.y < 0) this.y = altoPantalla;
            this.tiempoVelocidad = System.currentTimeMillis();
            hitboxRefresh();
        }
    }


    public void draw(Canvas c) {
        c.drawBitmap(sprite, left, top, null);
        c.drawRect(hitbox,p);
    }
}

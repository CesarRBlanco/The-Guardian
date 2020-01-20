package com.example.theguardian;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class Character {

    int anchoPantalla = 0, altoPantalla = 0;
    Bitmap[] frames, framesD, framesI;
    int frameActual = 0;
    int x, y;
    int cont = 0;

    int tickFrame = 100;
    long tiempoFrame;

    int velocidad = 50;
    int tickVelocidad = 50;
    long tiempoVelocidad;

    Rect cuadrado;
    Paint pincelRect;

    public Character(Bitmap[] frames, int x, int y, int anchoPantalla, int altoPantalla) {
        this.frames = frames;
        framesD = frames;
        framesI = new Bitmap[frames.length];
        for (int i = 0; i < framesI.length; i++) {
            framesI[i] = espejo(frames[i], true);
        }
        this.x = x;
        this.y = y;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        tiempoFrame = System.currentTimeMillis();
        tiempoVelocidad = System.currentTimeMillis();
        pincelRect = new Paint();
        pincelRect.setColor(Color.RED);
        pincelRect.setStyle(Paint.Style.STROKE);
        pincelRect.setStrokeWidth(10);
        cuadrado = new Rect(x, y, x + frames[frameActual].getWidth(), y + frames[frameActual].getHeight());
    }

    //actualiza rectangulo
    public void hitbox() {
        cuadrado = new Rect(x, y, x + frames[frameActual].getWidth(), y + frames[frameActual].getHeight());
    }

    public void dibuja(Canvas c) {
        c.drawBitmap(frames[frameActual], x, y, null);
        c.drawRect(cuadrado, pincelRect);
    }


    public void moverR() {
        if (System.currentTimeMillis() - tiempoVelocidad > tickVelocidad) {

            this.x += velocidad;
            if (this.x + this.frames[frameActual].getWidth() > anchoPantalla) {
                this.x = anchoPantalla - this.frames[frameActual].getWidth();
                this.x = anchoPantalla - cuadrado.width();
                velocidad = 0;
            }
            if (this.y > altoPantalla) this.y = 0;
            if (this.y < 0) this.y = altoPantalla;
            this.tiempoVelocidad = System.currentTimeMillis();
            hitbox();
        }
    }

    public void moverL() {
        if (System.currentTimeMillis() - tiempoVelocidad > tickVelocidad) {

            this.x += velocidad;

            if (this.x < 0) {
                this.x = anchoPantalla - this.frames[frameActual].getWidth();
                this.x = 0;
                velocidad = 0;
            }

            if (this.y > altoPantalla) this.y = 0;
            if (this.y < 0) this.y = altoPantalla;
            this.tiempoVelocidad = System.currentTimeMillis();
            hitbox();
        }
    }

    public void cambiaFrame() {
        if (System.currentTimeMillis() - tiempoFrame > tickFrame) {
            cont++;
            frameActual = cont % frames.length;
            tiempoFrame = System.currentTimeMillis();
        }
    }

    public Bitmap[] getFrames() {
        return frames;
    }

    public void setFrames(Bitmap[] frames) {
        this.frames = frames;
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

    public int getTickFrame() {
        return tickFrame;
    }

    public void setTickFrame(int tickFrame) {
        this.tickFrame = tickFrame;
    }

    public long getTiempoFrame() {
        return tiempoFrame;
    }

    public void setTiempoFrame(long tiempoFrame) {
        this.tiempoFrame = tiempoFrame;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = (int) (velocidad * 1.5f);


        if (velocidad > 0) {
            frames = framesD;
        } else {
            frames = framesI;
        }
    }

    public int getTickVelocidad() {
        return tickVelocidad;
    }

    public void setTickVelocidad(int tickVelocidad) {
        this.tickVelocidad = tickVelocidad;
    }

    public long getTiempoVelocidad() {
        return tiempoVelocidad;
    }

    public void setTiempoVelocidad(long tiempoVelocidad) {
        this.tiempoVelocidad = tiempoVelocidad;
    }

    public Rect getCuadrado() {
        return cuadrado;
    }

    public void setCuadrado(Rect cuadrado) {
        this.cuadrado = cuadrado;
    }


    public Bitmap espejo(Bitmap imagen, Boolean horizontal) {
        Matrix matrix = new Matrix();
        if (horizontal) matrix.preScale(-1, 1);
        else matrix.preScale(1, -1);
        return Bitmap.createBitmap(imagen, 0, 0, imagen.getWidth(),
                imagen.getHeight(), matrix, false);
    }
}


//           if (System.currentTimeMillis() - tiempoVelocidad > tickVelocidad) {
//        this.x += velocidad;
//        if (this.x+this.frames[frameActual].getWidth() > anchoPantalla) {
//            this.x=anchoPantalla-this.frames[frameActual].getWidth();
//            this.x = anchoPantalla;
//            velocidad *= -1 * 1.5f;
//            frames = framesI;
//        }
//        if(this.y>altoPantalla)this.y=0;
//        if(this.y<0)this.y=altoPantalla;
//        if (this.x < 0) {
//            this.x = 0;
//            velocidad *= -1*1.5f;
//            frames = framesD;
//        }
//        this.tiempoVelocidad = System.currentTimeMillis();
//        if(velocidad>0 && velocidad>200)velocidad=200;
//        if(velocidad<0 && velocidad<-200)velocidad=-200;
//        hitbox();
//    }
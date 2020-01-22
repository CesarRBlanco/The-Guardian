package com.example.theguardian;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

class Game extends SurfaceView implements SurfaceHolder.Callback {
    boolean movementD = false;
    boolean movementI = false;
    boolean dialog = false;
    boolean colisionI = false;
    boolean colisionD = false;
    boolean colSwitch= true;
    int anchoPantalla = 0, altoPantalla = 0;
    SurfaceHolder surfaceHolder;
    Context context;
    GameThread gameThread;
    Paint invisiblePaint,textPaint;
    Character character;
    Bitmap fondo1, botonR, botonL, botonAction, dialogImg, dialogBack, dialogArrow,spriteRef;
    HashMap<Integer, Point> dedos = new HashMap<>();
    Background f1, f2;
    MediaPlayer mp;
    Rect lMoveBtn, rMoveBtn, actionBtn, ladderInteract;

    public Game(Context context) {
        super(context);
        this.context = context;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            windowManager.getDefaultDisplay().getRealMetrics(dm); // métricas de la pantalla con tamaño reducido
        } else {
            windowManager.getDefaultDisplay().getMetrics(dm); // métricas de la pantalla con tamaño reducido
        }
        anchoPantalla = dm.widthPixels;
        altoPantalla = dm.heightPixels;
        gameThread = new GameThread();
        invisiblePaint = new Paint();
        //Color.argb(0,0,0,0)
        invisiblePaint.setColor(Color.argb(0,0,0,0));
        textPaint=new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(60);
        Bitmap[] bitmaps = new Bitmap[3];
        for (int i = 0; i < bitmaps.length; i++) {
            bitmaps[i] = getBitmapFromAssets("sprite" + i + ".png");
            bitmaps[i] = escalaAltura(bitmaps[i], altoPantalla / 6);
        }

        character = new Character(bitmaps, 0, 380, anchoPantalla, altoPantalla);
        spriteRef=getBitmapFromAssets("sprite0.png");
        spriteRef=escalaAltura(spriteRef,altoPantalla/6);
        fondo1 = getBitmapFromAssets("background.png");
        fondo1 = Bitmap.createScaledBitmap(fondo1, anchoPantalla, altoPantalla, false);
        botonL = getBitmapFromAssets("button movement.png");
        botonL = escalaAltura(botonL, altoPantalla / 6);
        botonR = getBitmapFromAssets("button movement.png");
        botonR = escalaAltura(botonR, altoPantalla / 6);
        botonR = espejo(botonR, true);
        botonAction = getBitmapFromAssets("action.png");
        botonAction = escalaAltura(botonAction, altoPantalla / 6);
        dialogImg = getBitmapFromAssets("dialog.png");
        dialogImg = escalaAltura(dialogImg, altoPantalla / 2);
        dialogBack = getBitmapFromAssets("dialog_background.png");
        dialogBack = escalaAncho(dialogBack, anchoPantalla);
        dialogArrow = getBitmapFromAssets("dialog_arrow.png");
        dialogArrow = escalaAltura(dialogArrow, altoPantalla / 6);
        lMoveBtn = new Rect(20, altoPantalla - 20 - botonL.getHeight(), botonL.getWidth() + 20, altoPantalla - 20);
        rMoveBtn = new Rect(60 + botonL.getWidth(), altoPantalla - 20 - botonR.getHeight(), botonR.getWidth() + 60 + botonL.getWidth(), altoPantalla - 20);
        actionBtn = new Rect(anchoPantalla / 2, 0, anchoPantalla, altoPantalla);
        ladderInteract = new Rect(anchoPantalla / 4 - 10, altoPantalla/2-100, anchoPantalla / 4 + 110, altoPantalla-altoPantalla/4+30);
    }

    public void dibujar(Canvas c) {
        c.drawBitmap(fondo1, 0, 0, null);
        c.drawRect(lMoveBtn, invisiblePaint);
        c.drawRect(rMoveBtn, invisiblePaint);
        c.drawRect(actionBtn, invisiblePaint);
        c.drawRect(ladderInteract, textPaint);
        if (dialog == false) {
            c.drawBitmap(botonL, 20, altoPantalla - 20 - botonL.getHeight(), null);
            c.drawBitmap(botonR, 60 + botonL.getWidth(), altoPantalla - 20 - botonR.getHeight(), null);
            c.drawBitmap(botonAction, anchoPantalla - botonAction.getWidth() - 20, altoPantalla - 20 - botonR.getHeight(), null);
        } else {
            c.drawBitmap(dialogBack, 0, altoPantalla - dialogBack.getHeight(), null);
            c.drawBitmap(dialogImg, -100, altoPantalla - dialogImg.getHeight(), null);
            c.drawBitmap(dialogArrow, anchoPantalla - botonAction.getWidth() - 20, altoPantalla - 20 - botonR.getHeight(), null);
            c.drawText(getResources().getString(R.string.dialogTest), dialogImg.getWidth() + 40, altoPantalla - 150, invisiblePaint);
        }
        c.drawText(""+character.x, 10, 50 + invisiblePaint.getTextSize(), textPaint);
        character.dibuja(c);
    }
    public void actualizaFisica() {

colisionStsten();

        if (movementD) {
            character.cambiaFrame();
            if (colisionI == false) {
                character.setVelocidad(50);
                character.moverR();
            }
            colisionD=false;
        }

        if (movementI) {
            character.cambiaFrame();
            if(colisionD==false){
            character.setVelocidad(-50);
            character.moverL();
            }
            colisionI = false;
        }
    }

    public void colisionStsten(){

        //Columna de escaleras - ARRIBA
        if (character.x > ladderInteract.right-spriteRef.getWidth()-10 && colSwitch==true) {
            colisionI = true;
        }

        //Columna de escaleras - ABAJO
        if(character.x<ladderInteract.left+40 && colSwitch==false){
            colisionD = true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int accion = event.getActionMasked();
        int indice = event.getActionIndex();
        int id = event.getPointerId(indice);
        float x = event.getX(indice);
        float y = event.getY(indice);
        int xI = (int) event.getX(indice);
        int yI = (int) event.getY(indice);

        switch (accion) {
            case MotionEvent.ACTION_DOWN:
                // Pulsación Izquierda y Derecha
                if (dialog == false) {
                    if (rMoveBtn.contains(xI, yI)) {
                        movementD = true;
                    }
                    if (lMoveBtn.contains(xI, yI)) {
                        movementI = true;
                    }
                }

                //Interacción con objeto
                if (actionBtn.contains(xI, yI) && (character.x > ladderInteract.left - (ladderInteract.right - ladderInteract.left))) {
                    if(character.y == ladderInteract.bottom-spriteRef.getHeight()){
                        character.y = ladderInteract.top-spriteRef.getHeight();
                 colisionD=false;
                        colSwitch=true;
                    }else{
                        character.y = ladderInteract.bottom-spriteRef.getHeight();
                        colisionI=false;
                        colSwitch=false;
                    }
                }
                return true;

            case MotionEvent.ACTION_MOVE:
                // Movimiento y desplazamiento Izquierda y Derecha
                if (dialog == false) {
                    if (!rMoveBtn.contains(xI, yI)) {
                        movementD = false;
                    }
                    if (!lMoveBtn.contains(xI, yI)) {
                        movementI = false;
                    }
                    if (rMoveBtn.contains(xI, yI)) {
                        movementD = true;
                    }
                    if (lMoveBtn.contains(xI, yI)) {
                        movementI = true;
                    }
                }
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                movementD = false;
                movementI = false;
                return true;
        }
        return super.onTouchEvent(event);
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.gameThread.setFuncionando(true);
        if (this.gameThread.getState() == Thread.State.NEW) this.gameThread.start();
        if (this.gameThread.getState() == Thread.State.TERMINATED) {
            this.gameThread = new GameThread();
            this.gameThread.start();
        }
        mp = MediaPlayer.create(context, R.raw.music);
        mp.start();
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.anchoPantalla = width;
        this.altoPantalla = height;
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        this.gameThread.setFuncionando(false);
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mp.pause();
    }

    public class GameThread extends Thread {
        public boolean funcionando = false;

        public GameThread() {

        }

        public boolean isFuncionando() {
            return funcionando;
        }

        public void setFuncionando(boolean funcionando) {
            this.funcionando = funcionando;
        }

        @Override
        public void run() {
            while (funcionando) {
                Canvas c = null;
                if (!surfaceHolder.getSurface().isValid()) {
                    continue;
                }
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        c = surfaceHolder.lockHardwareCanvas();
                    } else {
                        c = surfaceHolder.lockCanvas();
                    }
                    synchronized (surfaceHolder) {
                        actualizaFisica();
                        dibujar(c);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (c != null) {
                        surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }
    }

    //Métodos para imágenes
    public Bitmap getBitmapFromAssets(String fichero) {
        try {
            InputStream is = context.getAssets().open(fichero);
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            return null;
        }
    }
    public Bitmap escalaAltura(int res, int nuevoAlto) {
        Bitmap bitmapAux = BitmapFactory.decodeResource(context.getResources(), res);
        return escalaAltura(bitmapAux, nuevoAlto);
    }
    public Bitmap escalaAltura(Bitmap bitmapAux, int nuevoAlto) {
        if (nuevoAlto == bitmapAux.getHeight()) return bitmapAux;
        return bitmapAux.createScaledBitmap(bitmapAux, (bitmapAux.getWidth() * nuevoAlto) /
                bitmapAux.getHeight(), nuevoAlto, true);
    }
    public Bitmap escalaAncho(Bitmap bitmapAux, int nuevoAncho) {
        if (nuevoAncho == bitmapAux.getWidth()) return bitmapAux;
        return bitmapAux.createScaledBitmap(bitmapAux, nuevoAncho, (bitmapAux.getHeight() * nuevoAncho) / bitmapAux.getWidth(), true);
    }
    public Bitmap espejo(Bitmap imagen, Boolean horizontal) {
        Matrix matrix = new Matrix();
        if (horizontal) matrix.preScale(-1, 1);
        else matrix.preScale(1, -1);
        return Bitmap.createBitmap(imagen, 0, 0, imagen.getWidth(),
                imagen.getHeight(), matrix, false);
    }


}

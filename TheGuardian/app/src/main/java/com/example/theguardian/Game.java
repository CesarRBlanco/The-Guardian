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
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

class Game extends SurfaceView implements SurfaceHolder.Callback {

    //LLaves
    boolean movementD = false;
    boolean movementI = false;
    boolean dialog = false;
    boolean colisionI = false;
    boolean colisionD = false;
    boolean colSwitch = true;
    boolean boxPush = false;
    boolean boxMove = false;
    boolean boxColSwitch = true;
    boolean ladderUpDown = true;


    int anchoPantalla = 0, altoPantalla = 0;
    SurfaceHolder surfaceHolder;
    Context context;
    GameThread gameThread;
    Paint invisiblePaint, textPaint;
    Character character;
    Bitmap fondo1, botonR, botonL,luces, botonAction, dialogImg, dialogBack, dialogArrow, spriteRef, box;
    HashMap<Integer, Point> dedos = new HashMap<>();
    Background f1, f2;
    MediaPlayer mp;
    Rect lMoveBtn, rMoveBtn, actionBtn, ladderInteract;
    int charEnd;
    Escenario_Objects iniEO, boxObj;

    public Game(Context context) {
        super(context);
        this.context = context;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        gameThread = new GameThread();

//        DisplayMetrics dm = new DisplayMetrics();
//        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            windowManager.getDefaultDisplay().getRealMetrics(dm);
//        } else {
//            windowManager.getDefaultDisplay().getMetrics(dm);
//        }
//        anchoPantalla = dm.widthPixels;
//        altoPantalla = dm.heightPixels;
//        Log.i("ancho1",dm.widthPixels+":"+dm.heightPixels);
    }

    public void inicializao() {
        invisiblePaint = new Paint();
        //Color.argb(0,0,0,0)
        invisiblePaint.setColor(Color.argb(0, 0, 0, 0));
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(60);
        Bitmap[] bitmaps = new Bitmap[3];
        for (int i = 0; i < bitmaps.length; i++) {
            bitmaps[i] = getBitmapFromAssets("sprite" + i + ".png");
            bitmaps[i] = escalaAltura(bitmaps[i], altoPantalla / 6);
        }


        // Imagenes
        character = new Character(bitmaps, 1, 400, anchoPantalla, altoPantalla);
        spriteRef = getBitmapFromAssets("sprite0.png");
        spriteRef = escalaAltura(spriteRef, altoPantalla / 6);
        fondo1 = getBitmapFromAssets("fondo.png");
        fondo1 = Bitmap.createScaledBitmap(fondo1, anchoPantalla, altoPantalla, false);
        luces= getBitmapFromAssets("sombras.png");
        luces=Bitmap.createScaledBitmap(luces,anchoPantalla,altoPantalla,false);
        botonL = getBitmapFromAssets("movement.png");
        botonL = escalaAltura(botonL, altoPantalla / 6);
        botonL = espejo(botonL, true);
        botonR = getBitmapFromAssets("movement.png");
        botonR = escalaAltura(botonR, altoPantalla / 6);
        botonAction = getBitmapFromAssets("action.png");
        botonAction = escalaAltura(botonAction, altoPantalla / 6);
        dialogImg = getBitmapFromAssets("dialog.png");
        dialogImg = escalaAltura(dialogImg, altoPantalla / 2);
        dialogBack = getBitmapFromAssets("dialog_background.png");
        dialogBack = escalaAncho(dialogBack, anchoPantalla);
        dialogArrow = getBitmapFromAssets("dialog_arrow.png");
        box = getBitmapFromAssets("box.png");
        box = escalaAltura(box, altoPantalla / 6);

        // Rectangulos
        dialogArrow = escalaAltura(dialogArrow, altoPantalla / 6);
        lMoveBtn = new Rect(20, altoPantalla - 20 - botonL.getHeight(), botonL.getWidth() + 20, altoPantalla - 20);
        rMoveBtn = new Rect(60 + botonL.getWidth(), altoPantalla - 20 - botonR.getHeight(), botonR.getWidth() + 60 + botonL.getWidth(), altoPantalla - 20);
        actionBtn = new Rect(anchoPantalla / 2, 0, anchoPantalla, altoPantalla);
        ladderInteract = new Rect(anchoPantalla / 4 - 10, altoPantalla / 2 - 90, anchoPantalla / 4 + 90, altoPantalla - altoPantalla / 4 + 30);
        boxObj = new Escenario_Objects(anchoPantalla / 2, 1100 - box.getHeight(), anchoPantalla / 2 + box.getWidth(), altoPantalla - 300, box);

        // Auxiliares
        iniEO = new Escenario_Objects(altoPantalla, anchoPantalla);


    }

    public void dibujar(Canvas c) {
        c.drawBitmap(fondo1, 0, 0, null);
        c.drawRect(lMoveBtn, invisiblePaint);
        c.drawRect(rMoveBtn, invisiblePaint);
        c.drawRect(actionBtn, invisiblePaint);
        c.drawRect(ladderInteract, invisiblePaint);
//        c.drawRect(boxInteract, textPaint);
        if (dialog == false) {
            c.drawBitmap(botonL, 20, altoPantalla - 20 - botonL.getHeight(), null);
            c.drawBitmap(botonR, 60 + botonL.getWidth(), altoPantalla - 20 - botonR.getHeight(), null);
            c.drawBitmap(botonAction, anchoPantalla - botonAction.getWidth() - 20, altoPantalla - 20 - botonR.getHeight(), null);

        } else {
            c.drawBitmap(dialogBack, 0, altoPantalla - dialogBack.getHeight(), null);
            c.drawBitmap(dialogImg, -100, altoPantalla - dialogImg.getHeight(), null);
            c.drawBitmap(dialogArrow, anchoPantalla - botonAction.getWidth() - 20, altoPantalla - 20 - botonR.getHeight(), null);
            c.drawText(getResources().getString(R.string.dialogTest), dialogImg.getWidth() + 40, altoPantalla - 150, textPaint);
        }

//        c.drawBitmap(box, anchoPantalla / 2, 1100 - box.getHeight(), null);
        boxObj.draw(c);
        charEnd = character.x + spriteRef.getWidth();
        c.drawText("" + charEnd, 10, 50 + invisiblePaint.getTextSize(), textPaint);
//        c.drawText("" + ladderInteract.right, 100, 50 + invisiblePaint.getTextSize(), textPaint);
        character.dibuja(c);
        c.drawBitmap(luces,0,0,null);
    }

    public void actualizaFisica() {

        collisionSystem();

        if (movementD) {
            character.cambiaFrame();
            if (colisionI == false) {
                character.setVelocidad(20);
                character.moverR();
            }
            colisionD = false;
        }

        if (movementI) {
            character.cambiaFrame();
            if (colisionD == false) {
                character.setVelocidad(-20);
                character.moverL();
            }
            colisionI = false;
        }

        if (boxMove) {
            boxObj.move();
        }
    }

    public void collisionSystem() {

        //Columna de escaleras - ARRIBA
        if (charEnd > ladderInteract.right && colSwitch == true) {
//            character.x=ladderInteract.left;
            ladderUpDown = true;
            colisionI = true;
        }

        //Columna de escaleras - ABAJO
//        if (character.x < ladderInteract.left && colSwitch == false) {
//           ladderUpDown=true;
//            colisionD = true;
//        }


        //Caja arrastrable
        if (charEnd > boxObj.left && boxColSwitch == true) {
            colisionI = true;
            boxPush = true;
        } else {
            boxPush = false;
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
                // Boton Movimiento Izquierda y Derecha
                if (dialog == false) {
                    if (rMoveBtn.contains(xI, yI)) {
                        movementD = true;
                    }
                    if (lMoveBtn.contains(xI, yI)) {
                        movementI = true;
                    }
                }

                //Interacción con escalera izquierda
                if (actionBtn.contains(xI, yI) && (character.x > ladderInteract.left - (ladderInteract.right - ladderInteract.left))) {

                    // Subir
//                    if (ladderUpDown) {
//                        character.y = 400;
//                        colisionD = false;
//                        colSwitch = true;
//                    }

                    // Bajar
                    if (ladderUpDown) {
                        character.y = ladderInteract.bottom - spriteRef.getHeight();
                        colisionI = false;
                        colSwitch = false;
                    }

                    if (boxPush) {
                        boxMove = true;
                        boxColSwitch = false;
//                                            dialog = !dialog;
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
        Log.i("ancho2", width + ":" + height);
        inicializao();
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

package com.example.theguardian.Game;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.theguardian.Game.Levels.Level_0;
import com.example.theguardian.Game.Menus.Menu_Scene;


public class Game_Control extends SurfaceView implements SurfaceHolder.Callback {
    //LLaves
//    boolean movementD = false;
//    boolean movementI = false;
//    boolean dialog = false;
//    boolean colisionI = false;
//    boolean colisionD = false;
//    boolean colSwitch = true;
//    boolean boxPush = false;
//    boolean boxMove = false;
//    boolean boxColSwitch = true;
//    boolean ladderUpDown = true;
//    boolean pauseMenu = false;
//    boolean gameRunning = false;
//    boolean showActionBlack = false;
//    boolean menuPrincipal = true;


    int screenWidth = 0, screenHeight = 0;
    SurfaceHolder surfaceHolder;
    Context context;
    GameThread gameThread;
    Scene_Control actualScene;

    public Game_Control(Context context) {
        super(context);
        this.context = context;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        gameThread = new GameThread();

    }

//    public void inicializao() {
//        invisiblePaint = new Paint();
//        //Color.argb(0,0,0,0)
//        invisiblePaint.setColor(Color.argb(0, 0, 0, 0));
//        textPaint = new Paint();
//        textPaint.setColor(Color.WHITE);
//        textPaint.setTextSize(60);
//        blackPaint = new Paint();
//        blackPaint.setColor(Color.BLACK);
//        blackPaint.setTextSize(60);
//        Bitmap[] bitmaps = new Bitmap[3];
//        for (int i = 0; i < bitmaps.length; i++) {
//            bitmaps[i] = getBitmapFromAssets("sprite" + i + ".png");
//            bitmaps[i] = escalaAltura(bitmaps[i], screenHeight / 6);
//        }
//
//
//        // Imagenes
//        character = new Character(bitmaps, 1, 400, screenWidth, screenHeight);
//        spriteRef = getBitmapFromAssets("sprite0.png");
//        spriteRef = escalaAltura(spriteRef, screenHeight / 6);
//        fondo1 = getBitmapFromAssets("background.png");
//        fondo1 = Bitmap.createScaledBitmap(fondo1, screenWidth, screenHeight, false);
//        luces = getBitmapFromAssets("sombras.png");
//        luces = Bitmap.createScaledBitmap(luces, screenWidth, screenHeight, false);
//        botonL = getBitmapFromAssets("movement.png");
//        botonL = escalaAltura(botonL, screenHeight / 6);
//        botonL = espejo(botonL, true);
//        botonR = getBitmapFromAssets("movement.png");
//        botonR = escalaAltura(botonR, screenHeight / 6);
//        actionButton_W = getBitmapFromAssets("actionButtonWhite.png");
//        actionButton_W = escalaAltura(actionButton_W, screenHeight / 6);
//        actionButton_B = getBitmapFromAssets("actionButtonBlack.png");
//        actionButton_B = escalaAltura(actionButton_B, screenHeight / 6);
//        dialogImg = getBitmapFromAssets("dialog.png");
//        dialogImg = escalaAltura(dialogImg, screenHeight / 2);
//        dialogBack = getBitmapFromAssets("dialog_background.png");
//        dialogBack = escalaAncho(dialogBack, screenWidth);
//        dialogArrow = getBitmapFromAssets("dialog_arrow.png");
//        dialogArrow = escalaAltura(dialogArrow, screenHeight / 6);
//        box = getBitmapFromAssets("box.png");
//        box = escalaAltura(box, screenHeight / 6);
//        menuBackground = getBitmapFromAssets("menu.png");
//        menuBackground = Bitmap.createScaledBitmap(menuBackground, screenWidth, screenHeight, false);
//        backOptions = getBitmapFromAssets("backOptions.png");
//        backOptions = escalaAltura(backOptions, screenHeight / 6);
//
//
//        // Rectangulos
//        lMoveBtn = new Rect(20, screenHeight - 20 - botonL.getHeight(), botonL.getWidth() + 20, screenHeight - 20);
//        rMoveBtn = new Rect(60 + botonL.getWidth(), screenHeight - 20 - botonR.getHeight(), botonR.getWidth() + 60 + botonL.getWidth(), screenHeight - 20);
//        actionBtn = new Rect(screenWidth - actionButton_B.getWidth() - 20, screenHeight - 20 - botonR.getHeight(), screenWidth, screenHeight);
//        ladderInteract = new Rect(screenWidth / 4 - 10, screenHeight / 2 - 90, screenWidth / 4 + 90, screenHeight - screenHeight / 4 + 30);
//        boxObj = new Escenario_Objects(screenWidth / 2, 1100 - box.getHeight(), screenWidth / 2 + box.getWidth(), screenHeight - 300, box);
//        playBtn = new Rect(screenWidth / 3, screenHeight / 3 + 20, screenWidth - (screenWidth / 3), screenHeight / 2);
//        optionsBtn = new Rect(screenWidth / 3, screenHeight / 2 + 20, screenWidth - (screenWidth / 3), screenHeight - (screenHeight / 3));
//        creditsBtn = new Rect(screenWidth / 3, screenHeight - (screenHeight / 3) + 20, screenWidth - (screenWidth / 3), screenHeight - (screenHeight / 6));
//        helpBtn = new Rect(20, screenHeight - 20 - botonL.getHeight(), botonL.getWidth() + 20, screenHeight - 20);
//        backOptsBtn = new Rect(screenWidth - botonL.getWidth(), 0, screenWidth, botonL.getHeight());
//
//
//        // Auxiliares
//        iniEO = new Escenario_Objects(screenHeight, screenWidth);
//

//
//
//    }

//    public void dibujar(Canvas c) {
//
//        if (menuPrincipal) {
//
//        } else {
//            c.drawBitmap(fondo1, 0, 0, null);
////            c.drawRect(lMoveBtn, invisiblePaint);
////            c.drawRect(rMoveBtn, invisiblePaint);
////            c.drawRect(actionBtn, textPaint);
////            c.drawRect(ladderInteract, textPaint);
////            c.drawRect(backOptsBtn, textPaint);
//
//            if (dialog == false) {
//                c.drawBitmap(botonL, 20, screenHeight - 20 - botonL.getHeight(), null);
//                c.drawBitmap(botonR, 60 + botonL.getWidth(), screenHeight - 20 - botonR.getHeight(), null);
//                if (showActionBlack) {
//                    c.drawBitmap(actionButton_B, screenWidth - actionButton_W.getWidth() - 20, screenHeight - 20 - botonR.getHeight(), null);
//                } else {
//
//                    c.drawBitmap(actionButton_W, screenWidth - actionButton_W.getWidth() - 20, screenHeight - 20 - botonR.getHeight(), null);
//                }
//                c.drawBitmap(backOptions, screenWidth - actionButton_W.getWidth(), 0, null);
//
//
//            } else {
//                c.drawBitmap(dialogBack, 0, screenHeight - dialogBack.getHeight(), null);
//                c.drawBitmap(dialogImg, -100, screenHeight - dialogImg.getHeight(), null);
//                c.drawBitmap(dialogArrow, screenWidth - actionButton_W.getWidth() - 20, screenHeight - 20 - botonR.getHeight(), null);
//                c.drawText(getResources().getString(R.string.dialogTest), dialogImg.getWidth() + 40, screenHeight - 150, textPaint);
//            }
//
////        c.drawBitmap(box, screenWidth / 2, 1100 - box.getHeight(), null);
//            boxObj.draw(c);
//            charEnd = character.x + spriteRef.getWidth();
//            c.drawText("" + charEnd + " // " + ladderInteract.left, 10, 50 + invisiblePaint.getTextSize(), textPaint);
////        c.drawText("" + ladderInteract.right, 100, 50 + invisiblePaint.getTextSize(), textPaint);
//            character.dibuja(c);
//            c.drawBitmap(luces, 0, 0, null);
//        }
//    }

    public void updatePhysics() {


//        if (pauseMenu || menuPrincipal) {
//
//
//        } else {
//            collisionSystem();
//
//            if (movementD) {
//                character.cambiaFrame();
//                if (colisionI == false) {
//                    character.setVelocidad(20);
//                    character.moverR();
//
//                }
//                colisionD = false;
//            }
//
//            if (movementI) {
//                character.cambiaFrame();
//                if (colisionD == false) {
//                    character.setVelocidad(-20);
//                    character.moverL();
//                }
//                colisionI = false;
//            }
//
//            if (boxMove) {
//                boxObj.move();
//            }
//        }
    }

//    public void collisionSystem() {
//
//
//        //Columna de escaleras - ARRIBA
//
//        if (charEnd > ladderInteract.left && character.x < ladderInteract.right) {
//            showActionBlack = true;
//
//        } else {
//            showActionBlack = false;
//        }
//
//        if (charEnd > ladderInteract.right && colSwitch == true) {
////            character.x=ladderInteract.left;
////            showActionBlack = true;
//            ladderUpDown = true;
//            colisionI = true;
//        } else {
////            showActionBlack=false;
//        }
//
//        //Columna de escaleras - ABAJO
//        if (character.x < ladderInteract.left && colSwitch == false) {
//            ladderUpDown = true;
//            colisionD = true;
//        }
//
//
//        //Caja arrastrable
//        if (charEnd > boxObj.left && boxColSwitch == true) {
//            showActionBlack = true;
//            colisionI = true;
//            boxPush = true;
//        } else {
//            boxPush = false;
//        }
//    }


    public void sceneChange(int escena) {
        switch (escena) {
            case 1:
                break;
            case 2:
                actualScene = new Level_0(context, screenHeight, screenWidth);
                Log.i("escenaSelec", "Scene_Control 2");
                break;
            case 3:
                Log.i("escenaSelec", "Scene_Control 3");
                break;
            case 4:
                Log.i("escenaSelec", "Scene_Control 4");
                break;
            case 5:
                Log.i("escenaSelec", "Scene_Control 5");
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int accion = event.getActionMasked();
        int indice = event.getActionIndex();
        int id = event.getPointerId(indice);
        int x = (int) event.getX(indice);
        int y = (int) event.getY(indice);
        int newScene;
        newScene = actualScene.onTouchEvent(event);
        sceneChange(newScene);
//        switch (accion) {
//            case MotionEvent.ACTION_DOWN:
//
//
//                if (!gameRunning) {
//
//                    if (playBtn.contains(xI, yI)) {
//                        menuPrincipal = false;
//                        pauseMenu = false;
//                        gameRunning = true;
//                    }
//
//                } else {
//
//                    if (backOptsBtn.contains(xI, yI)) {
//                        pauseMenu = true;
//                        menuPrincipal = true;
//                        gameRunning = false;
//                    }
//
//
//                    // Boton Movimiento Izquierda y Derecha
//                    if (dialog == false) {
//                        if (rMoveBtn.contains(xI, yI)) {
//                            movementD = true;
//                        }
//                        if (lMoveBtn.contains(xI, yI)) {
//                            movementI = true;
//                        }
//                    }
//
//
//                    //Interacción con escalera izquierda
//                    if (actionBtn.contains(xI, yI) && (character.x > ladderInteract.left - (ladderInteract.right - ladderInteract.left))) {
//
//                        // Subir
//                        if (ladderUpDown && (character.y > ladderInteract.top)) {
//                            character.y = 400;
//                            colisionD = false;
//                            colSwitch = true;
//                        }
//
//                        // Bajar
//                        if (ladderUpDown && (character.y < ladderInteract.top)) {
//                            character.y = ladderInteract.bottom - spriteRef.getHeight();
//                            colisionI = false;
//                            colSwitch = false;
//                        }
//
//                        if (boxPush) {
//
//                            boxMove = true;
//                            boxColSwitch = false;
////                                            dialog = !dialog;
//                        }
//                    }
//                }
//
//
//                return true;
//
//            case MotionEvent.ACTION_MOVE:
//                // Movimiento y desplazamiento Izquierda y Derecha
//                if (dialog == false) {
//                    if (!rMoveBtn.contains(xI, yI)) {
//                        movementD = false;
//                    }
//                    if (!lMoveBtn.contains(xI, yI)) {
//                        movementI = false;
//                    }
//                    if (rMoveBtn.contains(xI, yI)) {
//                        movementD = true;
//                    }
//                    if (lMoveBtn.contains(xI, yI)) {
//                        movementI = true;
//                    }
//                }
//                return true;
//
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_POINTER_UP:
//                movementD = false;
//                movementI = false;
//                return true;
//        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.gameThread.setRunning(true);
        if (this.gameThread.getState() == Thread.State.NEW) this.gameThread.start();
        if (this.gameThread.getState() == Thread.State.TERMINATED) {
            this.gameThread = new GameThread();
            this.gameThread.start();
        }
        //Musica en base a escena igual que el draw y el fisica
//        mp = MediaPlayer.create(context, R.raw.music);
//        mp.start();
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height;

        actualScene = new Menu_Scene(context, screenHeight, screenWidth);
        Log.i("dimensiones", width + ":" + height);


    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        this.gameThread.setRunning(false);
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        mp.pause();
    }

    public class GameThread extends Thread {
        public boolean running = false;

        public GameThread() {
        }

        public boolean isRunning() {
            return running;
        }

        public void setRunning(boolean running) {
            this.running = running;
        }

        @Override
        public void run() {
            while (running) {

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
                        if (c != null) {



                            actualScene.updatePhysics();
                            actualScene.draw(c);
                        }
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


//    public void getImagenes(Bitmap img, int numPerX, int numPerY, int numFramesH, int numFramesV) {
//        int sizeX = img.getWidth() / numFramesH;
//        int sizeY = img.getHeight() / numFramesV;
//        for (int i = (numPerX -1)* 3; i <(numPerX-1)*3+3; i++) {
//
//        }
//
//    for(int i=numPerY-1)*4;i<(numPerY-1)*4+4;i++){
//
//    }
//    }


//    public Bitmap getBitmapFromAssets(String fichero) {
//        try {
//            InputStream is = context.getAssets().open(fichero);
//            return BitmapFactory.decodeStream(is);
//        } catch (IOException e) {
//            return null;
//        }
//    }
//
//    public Bitmap escalaAltura(Bitmap bitmapAux, int nuevoAlto) {
//        if (nuevoAlto == bitmapAux.getHeight()) return bitmapAux;
//        return bitmapAux.createScaledBitmap(bitmapAux, (bitmapAux.getWidth() * nuevoAlto) /
//                bitmapAux.getHeight(), nuevoAlto, true);
//    }
//
//    public Bitmap escalaAncho(Bitmap bitmapAux, int nuevoAncho) {
//        if (nuevoAncho == bitmapAux.getWidth()) return bitmapAux;
//        return bitmapAux.createScaledBitmap(bitmapAux, nuevoAncho, (bitmapAux.getHeight() * nuevoAncho) / bitmapAux.getWidth(), true);
//    }
//
//    public Bitmap espejo(Bitmap imagen, Boolean horizontal) {
//        Matrix matrix = new Matrix();
//        if (horizontal) matrix.preScale(-1, 1);
//        else matrix.preScale(1, -1);
//        return Bitmap.createBitmap(imagen, 0, 0, imagen.getWidth(),
//                imagen.getHeight(), matrix, false);
//    }
//
//
}

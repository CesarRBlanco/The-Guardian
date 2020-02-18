package com.example.theguardian.Game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.theguardian.Game.Levels.Level_0;
import com.example.theguardian.Game.Levels.Level_1;
import com.example.theguardian.Game.Menus.Menu_Scene;
import com.example.theguardian.Game.Menus.Options_Scene;
import com.example.theguardian.R;

import static com.example.theguardian.Game.Scene_Control.editor;
import static com.example.theguardian.Game.Scene_Control.preferences;


public class Game_Control extends SurfaceView implements SurfaceHolder.Callback {


    public static Vibrator v;
    static Scene_Control old;

    static int screenWidth = 0, screenHeight = 0;
    int newScene;
    SurfaceHolder surfaceHolder;
    static Context context;
    GameThread gameThread;
    static Scene_Control actualScene;
    Paint textPaint;
    MediaPlayer mediaPlayer;
 static    boolean firstTime = true;

    public Game_Control(Context context) {
        super(context);
        this.context = context;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        gameThread = new GameThread();
        v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);


        textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(60);
    }


    public void drawM(Canvas c) {

    }


    public static void sceneChange(int escena) {
        Log.i("escenaSelec", "Scene_Control " + escena);
        if (actualScene instanceof Level_1){
            old=actualScene;
        }

        switch (escena) {
            case 1:
                actualScene = new Menu_Scene(context, screenHeight, screenWidth);
                break;
            case 2:
                if (actualScene instanceof Options_Scene && old!=null){
                    actualScene=old;
                }else {
                    if (firstTime) {
//                        editor = preferences.edit();
//                        editor.putInt("playerX", 0);
//                        editor.putInt("playerY", screenHeight / 2);
//                        editor.commit();
                        firstTime = false;
                    }
                    actualScene = new Level_1(context, screenHeight, screenWidth);
                }Log.i("escenaSelec", "Scene_Control 2");
                break;
            case 3:
                actualScene = new Options_Scene(context, screenHeight, screenWidth);
                Log.i("escenaSelec", "Scene_Control 2");
                break;
            case 4:
                Log.i("escenaSelec", "Scene_Control 4");
                break;
            case 5:
                actualScene = new Level_0(context, screenHeight, screenWidth);
                Log.i("escenaSelec", "Scene_Control 5");
                break;
            case 6:
                Log.i("escenaSelec", "Scene_Control 6");
                System.exit(0);
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        actualScene.onTouchEvent(event);
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.gameThread.setRunning(true);
        if (this.gameThread.getState() == Thread.State.NEW) this.gameThread.start();
        if (this.gameThread.getState() == Thread.State.TERMINATED) {
            this.gameThread = new GameThread();
            this.gameThread.start();
        }


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

                            actualScene.updateConfig();
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

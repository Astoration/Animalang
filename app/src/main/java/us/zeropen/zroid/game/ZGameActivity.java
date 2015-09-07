package us.zeropen.zroid.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.*;
import android.util.DisplayMetrics;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;

import java.util.ArrayList;
import java.util.HashMap;

import us.zeropen.zroid.audio.ZAudio;
import us.zeropen.zroid.audio.ZMusic;
import us.zeropen.zroid.audio.ZSound;
import us.zeropen.zroid.device.ZDeviceMgr;
import us.zeropen.zroid.device.ZToast;
import us.zeropen.zroid.game.scene.ZScene;
import us.zeropen.zroid.game.scene.ZSceneMgr;
import us.zeropen.zroid.graphic.ZGraphic;
import us.zeropen.zroid.graphic.ZObject;
import us.zeropen.zroid.graphic.ZSprite;
import us.zeropen.zroid.input.TouchEvent;
import us.zeropen.zroid.input.ZInputMgr;

/**
 * Created by 병걸 on 2015-03-16.
 */
public class ZGameActivity extends Activity {
    boolean isRun;
    ZGameView zGameView;
    ZInputMgr zInputMgr;
    GameTask gameTask;
    Canvas canvas;
    WakeLock wakeLock;
    DisplayMetrics metrics;

    ArrayList<String> playingMusics;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ZDefine.GAME_WIDTH <= 0 || ZDefine.GAME_HEIGHT <= 0) {
            throw new RuntimeException("ZDefine.GAME_WIDTH 과 ZDefine.GAME_HEIGHT 값은 0보다 큰 값이어야 합니다");
        }

        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        Bitmap frameBuffer;

        if (isPortrait) {
            frameBuffer = Bitmap.createBitmap(ZDefine.GAME_WIDTH, ZDefine.GAME_HEIGHT, Bitmap.Config.RGB_565);
        }
        else {
            frameBuffer =  Bitmap.createBitmap(ZDefine.GAME_HEIGHT, ZDefine.GAME_WIDTH, Bitmap.Config.RGB_565);
        }

        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        ZDefine.GAME_SCALE_X = (float) ZDefine.GAME_WIDTH  / metrics.widthPixels;
        ZDefine.GAME_SCALE_Y = (float) ZDefine.GAME_HEIGHT / metrics.heightPixels;

        ZGraphic.assets = getAssets();

        ZGraphic.textures = new HashMap<String, Bitmap>();
        ZGraphic.fonts = new HashMap<String, Typeface>();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        ZAudio.assets = getAssets();
        ZAudio.sounds = new HashMap<String, ZSound>();
        ZAudio.musics = new HashMap<String, ZMusic>();
        ZAudio.soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
        ZSceneMgr.game = this;
        ZDeviceMgr.game = this;
        canvas = new Canvas(frameBuffer);
        zGameView = new ZGameView(this, frameBuffer);
        zInputMgr = new ZInputMgr(zGameView);
        setContentView(zGameView);

        if (isPortrait) {
            ZGraphic.addTexture("ZEROPEN_LOGO", "zroid/zer0pen_logo_portrait.png");
            ZGraphic.addTexture("ZROID_LOGO", "zroid/zroid_logo_portrait.png");
        }
        else {
            ZGraphic.addTexture("ZEROPEN_LOGO", "zroid/zer0pen_logo_landscape.png");
            ZGraphic.addTexture("ZROID_LOGO", "zroid/zroid_logo_landscape.png");
        }

        isRun = true;
        ZSceneMgr.setScene(new Scene_Default(this));

        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "ZGame");
    }

    class GameTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            long currentTime = System.currentTimeMillis();
            float eTime;

            while (isRun) {
                eTime = (float)(System.currentTimeMillis() - currentTime);
                currentTime = System.currentTimeMillis();

                ZSceneMgr.update(eTime);
                zGameView.draw();

                long et;

                if ((et = (System.currentTimeMillis() - currentTime)) <= 1000 / ZDefine.GAME_FPS) {
                    try {
                        Thread.sleep(1000 / ZDefine.GAME_FPS - et);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        public void stop() {
            isRun = false;
        }
    }

    @Override
    public void onBackPressed() {
        if (ZSceneMgr.getScene().onBackPressed()) {
            new AlertDialog.Builder(this)
                    .setMessage("종료하시겠습니까?")
                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ZAudio.stopAllMusic();
                            finish();
                        }
                    })
                    .setNegativeButton("아니오", null)
                    .show();
        }
    }

    @Override
    protected void onResume() {
        ZSceneMgr.onResume();
        isRun = true;
        gameTask = new GameTask();
        gameTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        wakeLock.acquire();
        if (playingMusics != null) {
            for (String m : playingMusics) {
                ZAudio.playMusic(m);
            }
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        ZSceneMgr.onPause();
        if (ZDeviceMgr.vibrator != null) {
            ZDeviceMgr.vibrator.cancel();
            ZDeviceMgr.vibrator = null;
        }

        playingMusics = ZAudio.getPlayingMusicList();
        ZAudio.stopAllMusic();
        gameTask.stop();
        wakeLock.release();
        super.onPause();
    }

    @Override
    protected void onStart() {
        ZSceneMgr.onStart();
        super.onStart();
    }

    @Override
    protected void onStop() {
        ZSceneMgr.onStop();
        super.onStop();
    }

    public ZScene getInitScene() {
        return null;
    }

    public ZInputMgr getInputMgr() {
        return zInputMgr;
    }

    private class Scene_Default extends ZScene {
        float wait;

        ZSprite logo_zer0pen;
        ZSprite logo_zroid;
        ZSprite logo_RG;

        public Scene_Default(ZGameActivity game) {
            super(game);

            wait = 0;

            logo_zer0pen = addSprite(new ZSprite("logo_zer0pen", "ZEROPEN_LOGO", 0, 0))
                    .setWidth(ZDefine.GAME_WIDTH)
                    .setHeight(ZDefine.GAME_HEIGHT);

            logo_zroid = addSprite(new ZSprite("logo_zroid", "ZROID_LOGO", 0, 0))
                    .setWidth(ZDefine.GAME_WIDTH)
                    .setHeight(ZDefine.GAME_HEIGHT)
                    .setAlpha(0);
            addTexture("rglogo","zroid/RG_LOGO.png");
            logo_RG = addSprite(new ZSprite("logo_rg", "rglogo", 0, 0))
                    .setWidth(ZDefine.GAME_WIDTH)
                    .setHeight(ZDefine.GAME_HEIGHT)
                    .setAlpha(0);

        }

        @Override
        public void update(float eTime) {
            if (wait < 3500) {
                wait += eTime;
            }
            else if (wait >= 3500 && logo_zer0pen.getAlpha() > 0 && wait < 3600) {
                logo_zer0pen.addAlpha(-0.55f * eTime);
            }
            else if (logo_zer0pen.getAlpha() == 0 && wait < 3600) {
                wait += eTime;
            }
            else if (wait >= 3600 && logo_zroid.getAlpha() < 255 && wait < 7100) {
                logo_zroid.addAlpha(0.55f * eTime);
            }
            else if (wait >= 3600 && logo_zroid.getAlpha() == 255 && wait < 7100) {
                wait += eTime;
            }
            else if (wait >= 7100 && logo_zroid.getAlpha() > 0) {
                logo_zroid.addAlpha(-0.55f * eTime);
            }
            else if (wait >= 7100 && logo_zroid.getAlpha() == 0 && wait < 7300) {
                wait += eTime;
            }
            else if (wait >= 7300 && logo_RG.getAlpha() < 255 && wait < 7300+1500) {
                logo_RG.addAlpha(0.55f * eTime);
            }
            else if (wait >= 7300 && logo_RG.getAlpha() == 255 && wait < 7300+1500) {
                wait += eTime;
            }
            else if (wait >= 7300 && logo_RG.getAlpha() > 0) {
                logo_RG.addAlpha(-0.55f * eTime);
            }
            else if (wait >= 7300 && logo_RG.getAlpha() == 0 && wait < 7300+1500) {
                wait += eTime;
            }
            else {
                setScene(getInitScene());
            }
            super.update(eTime);
        }

        @Override
        public void executeEvent(int touchType, TouchEvent event, String id, ZObject object, int pointer, float eTime) {
            if (touchType == TOUCH_DOWN) {
                if (wait > 500 && wait < 3500) {
                    wait = 3500;
                }
                else if (wait > 3700 && wait < 7100) {
                    wait = 7100;
                }
            }
        }
    }
}

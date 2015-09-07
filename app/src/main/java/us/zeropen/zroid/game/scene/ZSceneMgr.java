package us.zeropen.zroid.game.scene;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

import us.zeropen.zroid.game.ZGameActivity;
import us.zeropen.zroid.game.camera.ZCameraMgr;
import us.zeropen.zroid.graphic.ZGraphic;
import us.zeropen.zroid.graphic.ZPos;
import us.zeropen.zroid.graphic.ZPosF;
import us.zeropen.zroid.input.ZInputMgr;

/**
 * Created by 병걸 on 2015-03-16.
 */
public class ZSceneMgr {
    private static boolean isUpdate = true;
    private static boolean isRender = true;
    private static ZScene scene = null;
    public static ZGameActivity game = new ZGameActivity();
    static public Canvas canvas;

    public static void update(float eTime) {
        if (isUpdate && scene != null) {
            scene.update(eTime);
            ZCameraMgr.update(eTime);
        }
    }

    public static void render() {
        if (isRender && scene != null) {
            scene.render();
        }
    }

    public static void setScene(ZScene scene) {
        if (ZSceneMgr.scene != null) {
            final String[] rs = ZSceneMgr.scene.removeLoadedResources();
            game.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (String r : rs) {
                        ZGraphic.removeTexture(r);
                    }
                }
            });
        }

        ZSceneMgr.scene = scene;
        System.gc();
    }

    public static ZScene getScene() {
        return scene;
    }

    public static void setRender(boolean option) {
        isRender = option;
    }

    public static void setUpdate(boolean option) {
        isUpdate = option;
    }

    public static void onSwipe(int swipeType, MotionEvent start, MotionEvent end, ZPosF velocity) {
        if (scene != null) {
            scene.onSwipe(swipeType, start, end, velocity);
        }
    }

    public static void onDoubleTap(MotionEvent e) {
        if (scene != null) {
            scene.onDoubleTap(e);
        }
    }

    public static void onSingleTap(MotionEvent e) {
        if (scene != null) {
            scene.onSingleTap(e);
        }
    }

    public static void onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (scene != null)
            scene.onScroll(e1, e2, distanceX, distanceY);
    }

    public static void onStop() {
        if (scene != null)
            scene.onStop();
    }

    public static void onPause() {
        if (scene != null)
            scene.onPause();
    }

    public static void onStart() {
        if (scene != null)
            scene.onStart();
    }

    public static void onResume() {
        if (scene != null)
            scene.onResume();
    }
}

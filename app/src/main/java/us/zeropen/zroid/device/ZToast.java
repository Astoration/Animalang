package us.zeropen.zroid.device;

import android.widget.Toast;

import us.zeropen.zroid.game.scene.ZSceneMgr;
import us.zeropen.zroid.graphic.ZPos;
import us.zeropen.zroid.graphic.ZPosF;

/**
 * Created by 병걸 on 2015-05-12.
 */
public class ZToast {
    public static void show(final String text, final int duration) {
        ZSceneMgr.game.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ZSceneMgr.game, text, duration).show();
            }
        });
    }

    public static void show(String text) {
        show(text, Toast.LENGTH_SHORT);
    }

    public static void show(int number, int duration) {
        show(Integer.toString(number), duration);
    }

    public static void show(int number) {
        show(Integer.toString(number), Toast.LENGTH_SHORT);
    }

    public static void show(long number, int duration) {
        show(Long.toString(number), duration);
    }

    public static void show(long number) {
        show(Long.toString(number), Toast.LENGTH_SHORT);
    }

    public static void show(short number, int duration) {
        show(Short.toString(number), duration);
    }

    public static void show(short number) {
        show(Short.toString(number), Toast.LENGTH_SHORT);
    }

    public static void show(boolean bool, int duration) {
        show(Boolean.toString(bool), duration);
    }

    public static void show(boolean bool) {
        show(Boolean.toString(bool), Toast.LENGTH_SHORT);
    }

    public static void show(char character, int duration) {
        show(Character.toString(character), duration);
    }

    public static void show(char character) {
        show(Character.toString(character), Toast.LENGTH_SHORT);
    }

    public static void show(float number, int duration) {
        show(Float.toString(number), duration);
    }

    public static void show(float number) {
        show(Float.toString(number), Toast.LENGTH_SHORT);
    }

    public static void show(double number, int duration) {
        show(Double.toString(number), duration);
    }

    public static void show(double number) {
        show(Double.toString(number), Toast.LENGTH_SHORT);
    }

    public static void show(ZPos pos, int duration) {
        show("x: " + pos.x + ", y: " + pos.y, duration);
    }

    public static void show(ZPos pos) {
        show("x: " + pos.x + ", y: " + pos.y, Toast.LENGTH_SHORT);
    }

    public static void show(ZPosF pos, int duration) {
        show("x: " + pos.x + ", y: " + pos.y, duration);
    }

    public static void show(ZPosF pos) {
        show("x: " + pos.x + ", y: " + pos.y, Toast.LENGTH_SHORT);
    }
}

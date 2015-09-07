package us.zeropen.zroid.device;

import android.content.Context;
import android.os.Vibrator;

import us.zeropen.zroid.game.ZGameActivity;

/**
 * Created by 병걸 on 2015-04-13.
 */
public class ZDeviceMgr {
    public static ZGameActivity game = new ZGameActivity();
    public static Vibrator vibrator;

    public static void vibrate() {
        vibrator = (Vibrator)game.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);
        vibrator = null;
    }

    public static void vibrate(int millisecond) {
        vibrator = (Vibrator)game.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(millisecond);
        vibrator = null;
    }
}

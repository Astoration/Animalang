package us.zeropen.zroid.device;

import android.content.SharedPreferences;

import us.zeropen.zroid.game.scene.ZSceneMgr;

/**
 * Created by 병걸 on 2015-04-13.
 */
public class ZSharedPreferencesMgr {
    public static void save(String data_name, String data) {
        SharedPreferences pref = ZSceneMgr.game.getSharedPreferences("pref", ZSceneMgr.game.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(data_name, data);
        editor.apply();
    }

    public static String get(String data_name) {
        SharedPreferences pref = ZSceneMgr.game.getSharedPreferences("pref", ZSceneMgr.game.MODE_PRIVATE);
        return pref.getString(data_name, "default");
    }
}

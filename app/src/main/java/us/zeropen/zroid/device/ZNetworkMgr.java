package us.zeropen.zroid.device;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import us.zeropen.zroid.game.scene.ZSceneMgr;

/**
 * Created by 병걸 on 2015-05-12.
 */
public class ZNetworkMgr {
    public static final int NETWORK_WIFI = 0;
    public static final int NETWORK_DATA = 1;
    public static final int NETWORK_NONE = 2;

    public static int checkNetworkStatus() {
        final ConnectivityManager connectivityManager = (ConnectivityManager) ZSceneMgr.game.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (null != networkInfo) {
            if (networkInfo.getType() == connectivityManager.TYPE_WIFI) {
                return NETWORK_WIFI;
            }
            if (networkInfo.getType() == connectivityManager.TYPE_MOBILE) {
                return NETWORK_DATA;
            }
        }

        return NETWORK_NONE;
    }
}

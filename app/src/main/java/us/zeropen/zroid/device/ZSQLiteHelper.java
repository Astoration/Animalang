package us.zeropen.zroid.device;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import us.zeropen.zroid.game.scene.ZSceneMgr;

/**
 * Created by 병걸 on 2015-05-12.
 */
public class ZSQLiteHelper extends SQLiteOpenHelper {
    private String onCreateQuery;

    public ZSQLiteHelper(String dbName, int dbVersion, String query) {
        super(ZSceneMgr.game, dbName, null, dbVersion);
        onCreateQuery = query;
    }

    public ZSQLiteHelper(String dbName, String query) {
        this(dbName, 1, query);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(onCreateQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

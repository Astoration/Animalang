package us.zeropen.zroid.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import us.zeropen.zroid.game.scene.ZSceneMgr;

/**
 * Created by 병걸 on 2015-03-16.
 */
public class ZGameView extends SurfaceView {
    ZGameActivity game;
    SurfaceHolder surfaceHolder;
    Bitmap frameBuffer;
    Rect dstRect;

    public ZGameView(ZGameActivity _game, Bitmap _frameBuffer) {
        super(_game);
        game = _game;
        frameBuffer = _frameBuffer;
        surfaceHolder = getHolder();
        dstRect = new Rect();
    }

    public void draw() {
        if (!surfaceHolder.getSurface().isValid())
            return;

        Canvas canvas = surfaceHolder.lockCanvas();
        canvas.getClipBounds(dstRect);
        canvas.drawColor(Color.WHITE);
        ZSceneMgr.canvas = canvas;
        ZSceneMgr.render();
        surfaceHolder.unlockCanvasAndPost(canvas);
    }
}

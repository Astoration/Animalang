package us.zeropen.zroid.game.camera;

import android.util.Log;

import us.zeropen.zroid.game.ZDefine;
import us.zeropen.zroid.graphic.ZObject;
import us.zeropen.zroid.graphic.ZPos;
import us.zeropen.zroid.graphic.ZPosF;
import us.zeropen.zroid.graphic.ZRect;

/**
 * Created by Dohyung on 2015-05-25.
 */
public class ZCameraMgr {
	private static ZPosF pos = new ZPosF(0, 0);
	private static ZObject target = null;
	private static ZRect bound = new ZRect("bound", 0, 0, ZDefine.GAME_WIDTH, ZDefine.GAME_HEIGHT);

	public static void update(float eTime) {
		if (target != null) {
			setCenterPos(target.getCenterPosX(), target.getCenterPosY());
		}
		if (pos.x < bound.getPosX()) pos.x = bound.getPosX();
		if (pos.y < bound.getPosY()) pos.y = bound.getPosY();
		if (pos.x + ZDefine.GAME_WIDTH > bound.getPosX() + bound.getWidth()) {
			pos.x = bound.getPosX() + bound.getWidth() - ZDefine.GAME_WIDTH;
		}
		if (pos.y + ZDefine.GAME_HEIGHT > bound.getPosY() + bound.getHeight()) {
			pos.y = bound.getPosY() + bound.getHeight() - ZDefine.GAME_HEIGHT;
		}
	}

	public static ZPosF getPos() {
		return pos;
	}

	public static float getPosX() {
		return pos.x;
	}

	public static float getPosY() {
		return pos.y;
	}

	public static ZPosF getCenterPos() {
		return new ZPosF(pos.x + ZDefine.GAME_WIDTH / 2, pos.y + ZDefine.GAME_HEIGHT);
	}

	public static float getCenterPosX() {
		return pos.x + ZDefine.GAME_WIDTH / 2;
	}

	public static float getCenterPosY() {
		return pos.y + ZDefine.GAME_HEIGHT / 2;
	}

	public static void setPos(ZPos pos) {
		ZCameraMgr.pos.x = pos.x;
		ZCameraMgr.pos.y = pos.y;
	}

	public static void setPos(ZPosF pos) {
		ZCameraMgr.pos.x = pos.x;
		ZCameraMgr.pos.y = pos.y;
	}

	public static void setPosX(float x) {
		ZCameraMgr.pos.x = x;
	}

	public static void setPosY(float y) {
		ZCameraMgr.pos.y = y;
	}

	public static void setCamera(float x, float y) {
		pos.x = x;
		pos.y = y;
	}

	public static void setCameraX(float x) {
		pos.x = x;
	}

	public static void setCameraY(float y) {
		pos.y = y;
	}

	public static void setCenterPos(ZPos pos) {
		ZCameraMgr.pos.x = pos.x - ZDefine.GAME_WIDTH / 2;
		ZCameraMgr.pos.y = pos.y - ZDefine.GAME_HEIGHT / 2;
	}

	public static void setCenterPos(ZPosF pos) {
		ZCameraMgr.pos.x = pos.x - ZDefine.GAME_WIDTH / 2;
		ZCameraMgr.pos.y = pos.y - ZDefine.GAME_HEIGHT / 2;
	}

	public static void setCenterPos(float x, float y) {
		pos.x = x - ZDefine.GAME_WIDTH / 2;
		pos.y = y - ZDefine.GAME_HEIGHT / 2;
	}

	public static void setCenterPosX(float x) {
		pos.x = x - ZDefine.GAME_WIDTH / 2;
	}

	public static void setCenterPosY(float y) {
		pos.y = y - ZDefine.GAME_HEIGHT / 2;
	}

	public static void addPos(ZPos pos) {
		ZCameraMgr.pos.x += pos.x;
		ZCameraMgr.pos.y += pos.y;
	}

	public static void addPos(ZPosF pos) {
		ZCameraMgr.pos.x += pos.x;
		ZCameraMgr.pos.y += pos.y;
	}

	public static void addPos(float x, float y) {
		pos.x += x;
		pos.y += y;
	}

	public static void addPosX(float x) {
		pos.x += x;
	}

	public static void addPosY(float y) {
		pos.y += y;
	}

	public static ZObject getTarget() {
		return target;
	}

	public static void setTarget(ZObject target) {
		ZCameraMgr.target = target;
	}

	public static void setBound(ZRect r) {
		bound.setPosX(r.getPosX());
		bound.setPosY(r.getPosY());
		bound.setWidth(r.getScaledWidth());
		bound.setHeight(r.getScaledHeight());
		if (bound.getWidth() < ZDefine.GAME_WIDTH || bound.getHeight() < ZDefine.GAME_HEIGHT)
			Log.w("Camera Boundary", "Smaller boundary than screen size");
	}

	public static void setBound(float x, float y, float width, float height) {
		bound.setPosX(x);
		bound.setPosY(y);
		bound.setWidth(width);
		bound.setHeight(height);
		if (bound.getWidth() < ZDefine.GAME_WIDTH || bound.getHeight() < ZDefine.GAME_HEIGHT)
			Log.w("Camera Boundary", "Smaller boundary than screen size");
	}

    public static void checkInBound() {
        if (pos.x < bound.getPosX()) pos.x = bound.getPosX();
        if (pos.y < bound.getPosY()) pos.y = bound.getPosY();
        if (pos.x + ZDefine.GAME_WIDTH > bound.getPosX() + bound.getWidth()) {
            pos.x = bound.getPosX() + bound.getWidth() - ZDefine.GAME_WIDTH;
        }
        if (pos.y + ZDefine.GAME_HEIGHT > bound.getPosY() + bound.getHeight()) {
            pos.y = bound.getPosY() + bound.getHeight() - ZDefine.GAME_HEIGHT;
        }
    }
}

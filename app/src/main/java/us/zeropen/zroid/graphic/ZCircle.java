package us.zeropen.zroid.graphic;

import android.graphics.Color;
import android.util.Log;

import us.zeropen.zroid.game.ZDefine;
import us.zeropen.zroid.game.scene.ZSceneMgr;
import us.zeropen.zroid.input.TouchEvent;
import us.zeropen.zroid.util.ZMath;

/**
 * Created by 병걸 on 2015-06-01.
 */
public class ZCircle extends ZObject {
    private float radius;

    public ZCircle(String id, float centerX, float centerY, float radius) {
        this(id, centerX, centerY, radius, Color.BLACK);
    }

    public ZCircle(String id, ZPosF centerPos, float radius) {
        this(id, centerPos.x, centerPos.y, radius, Color.BLACK);
    }

    public ZCircle(String id, float centerX, float centerY, float radius, int color) {
        super("ZCircle", id, centerX, centerY);
        setColor(color);
        this.radius = radius;
        width = radius * 2;
        height = radius * 2;
    }

    public ZCircle(String id, ZPosF centerPos, float radius, int color) {
        this(id, centerPos.x, centerPos.y, radius, color);
    }

    @Override
    public void render() {
        ZSceneMgr.canvas.rotate(degree, rotationCenter.x / ZDefine.GAME_SCALE_X, rotationCenter.y / ZDefine.GAME_SCALE_Y);
        ZSceneMgr.canvas.save();
        ZSceneMgr.canvas.scale(scaleX, scaleY, (getCameraPosX() + scalingCenter.x) / ZDefine.GAME_SCALE_X, (getCameraPosY() + scalingCenter.y) / ZDefine.GAME_SCALE_Y);

        ZSceneMgr.canvas.drawCircle(getCameraPosX() / ZDefine.GAME_SCALE_X, getCameraPosY() / ZDefine.GAME_SCALE_Y, radius  / ((ZDefine.GAME_SCALE_X + ZDefine.GAME_SCALE_Y) / 2), paint);

        ZSceneMgr.canvas.restore();

        super.render();
        ZSceneMgr.canvas.rotate(-degree, rotationCenter.x / ZDefine.GAME_SCALE_X, rotationCenter.y / ZDefine.GAME_SCALE_Y);
    }

    @Override
    public boolean isTouched(TouchEvent event) {
        return touchAble && contains(event.pos);
    }

    @Override
    public ZCircle setHeight(float height) {
        Log.e(type, "(id: " + id + ") / setHeight(float height) - 이 클래스에서는 사용할 수 없는 함수입니다");
        return null;
    }

    @Override
    public ZCircle setWidth(float width) {
        Log.e(type, "(id: " + id + ") / setTouchPointer(float width) - 이 클래스에서는 사용할 수 없는 함수입니다");
        return null;
    }

    @Override
    public boolean intersects(ZObject obj) {
        switch (obj.getType()) {
            case "ZCircle":
                return radius + ((ZCircle) obj).getRadius() >= ZMath.getDistance(obj.getCameraPos(), getCameraPos());

            case "ZLine":
            case "ZText":
                Log.e(type, "(id: " + id + ") / intersects(ZObject obj) - " + obj.getType() + " 타입의 object 와는 이 함수를 사용할 수 없습니다");
                return false;

            default :
                ZPosF closet = new ZPosF(getCameraCenterPos());

                if (getCenterPosX() < obj.getCameraPosX()) {
                    closet.x = obj.getCameraPosX();
                } else if (getCenterPosX() > obj.getCameraPosX() + obj.getScaledWidth()) {
                    closet.x = obj.getCameraPosX() + obj.getScaledWidth();
                }

                if (getCameraCenterPosY() < obj.getCameraPosY()) {
                    closet.y = obj.getCameraPosY();
                } else if (getCenterPosY() > obj.getCameraPosY() + obj.getScaledHeight()) {
                    closet.y = obj.getCameraPosY() + obj.getScaledHeight();
                }
                return (ZMath.getDistSquared(getCameraPos(), closet) < radius * radius)
                        || obj.contains(getCenterPos());
        }
    }

    @Override
    public boolean contains(ZPos pos) {
        return ZMath.getDistance(getCameraPosX(), getCameraPosY(), pos.x, pos.y) <= radius;
    }

    @Override
    public boolean contains(ZPosF pos) {
        return ZMath.getDistance(getCameraPos(), pos) <= radius;
    }

    @Override
    public boolean contains(int posX, int posY) {
        return ZMath.getDistance(getCameraPosX(), getCameraPosY(), pos.x, pos.y) <= radius;
    }

    @Override
    public boolean contains(float posX, float posY) {
        return ZMath.getDistance(getCameraPosX(), getCameraPosY(), pos.x, pos.y) <= radius;
    }

    @Override
    public ZCircle setCamera(boolean option) {
        return (ZCircle) super.setCamera(option);
    }

    @Override
    public ZRect getIntersects(ZObject obj) {
        Log.e(type, "(id: " + id + ") / getIntersects - 이 클래스에서는 사용할 수 없는 함수입니다");
        return null;
    }

    public int getColor() {
        return color;
    }

    public float getRadius() {
        return radius;
    }

    @Override
    public ZCircle addRotate(float degree) {
        return (ZCircle) super.addRotate(degree);
    }

    public ZCircle setRadius(float radius) {
        this.radius = radius;
        height = width = radius * 2;
        return this;
    }

    public ZCircle addRadius(float radius) {
        this.radius -= radius;
        if (radius < 0) radius = 0;
        height = width = radius * 2;

        return this;
    }

    @Override
    public ZCircle setTouchPointer(int pointer) {
        return (ZCircle) super.setTouchPointer(pointer);
    }

    @Override
    public ZCircle setDownPos(ZPosF pos) {
        return (ZCircle) super.setDownPos(pos);
    }

    @Override
    public ZCircle setDownPos(float x, float y) {
        return (ZCircle) super.setDownPos(x, y);
    }

    @Override
    public float getCenterPosY() {
        return pos.y;
    }

    @Override
    public float getCenterPosX() {
        return pos.x;
    }

    @Override
    public ZCircle setPos(float x, float y) {
        return (ZCircle) super.setPos(x, y);
    }

    @Override
    public ZCircle setPosX(float x) {
        return (ZCircle) super.setPosX(x);
    }

    @Override
    public ZCircle setPosY(float y) {
        return (ZCircle) super.setPosY(y);
    }

    @Override
    public ZCircle setPos(ZPosF pos) {
        return (ZCircle) super.setPos(pos);
    }

    @Override
    public ZCircle setColor(int color) {
        return (ZCircle) super.setColor(color);
    }

    @Override
    public ZCircle setRotate(float degree) {
        return (ZCircle) super.setRotate(degree);
    }

    @Override
    public ZCircle setRotate(float degree, int rotationCenterX, int rotationCenterY) {
        return (ZCircle) super.setRotate(degree, rotationCenterX, rotationCenterY);
    }

    @Override
    public ZCircle setRotate(float degree, ZPosF rotationCenter) {
        return (ZCircle) super.setRotate(degree, rotationCenter);
    }

    @Override
    public ZCircle setRotate(float degree, boolean option) {
        return (ZCircle) super.setRotate(degree, option);
    }

    @Override
    public ZCircle setRotationCenter(ZPos pos) {
        return (ZCircle) super.setRotationCenter(pos);
    }

    @Override
    public ZCircle setRotationCenter(ZPosF pos) {
        return (ZCircle) super.setRotationCenter(pos);
    }

    @Override
    public ZCircle setRotationCenter(float x, float y) {
        return (ZCircle) super.setRotationCenter(x, y);
    }

    @Override
    public ZCircle setRotationCenter(int x, int y) {
        return (ZCircle) super.setRotationCenter(x, y);
    }

    @Override
    public ZCircle addPos(float x, float y) {
        return (ZCircle) super.addPos(x, y);
    }

    @Override
    public ZCircle addPosX(float x) {
        return (ZCircle) super.addPosX(x);
    }

    @Override
    public ZCircle addPosY(float y) {
        return (ZCircle) super.addPosY(y);
    }



    @Override
    public ZCircle setAlpha(float alpha) {
        return (ZCircle) super.setAlpha(alpha);
    }

    @Override
    public ZCircle addAlpha(float alpha) {
        return (ZCircle) super.addAlpha(alpha);
    }

    @Override
    public ZCircle setScale(float scaleX, float scaleY) {
        return (ZCircle)super.setScale(scaleX, scaleY);
    }

    @Override
    public ZCircle setScale(float scale) {
        return (ZCircle)super.setScale(scale);
    }

    @Override
    public ZCircle setScaleX(float scaleX) {
        return (ZCircle)super.setScaleX(scaleX);
    }

    @Override
    public ZCircle setScaleY(float scaleY) {
        return (ZCircle)super.setScaleX(scaleY);
    }

    @Override
    public ZCircle addScale(float scaleX, float scaleY) {
        return (ZCircle)super.addScale(scaleX, scaleY);
    }

    @Override
    public ZCircle addScale(float scale) {
        return (ZCircle)super.addScale(scale);
    }

    @Override
    public ZCircle addScaleX(float scaleX) {
        return (ZCircle)super.addScaleX(scaleX);
    }

    @Override
    public ZCircle addScaleY(float scaleY) {
        return (ZCircle)super.addScaleY(scaleY);
    }

    @Override
    public ZCircle setUpdate(boolean option) {
        return (ZCircle) super.setUpdate(option);
    }

    @Override
    public ZCircle setRender(boolean option) {
        return (ZCircle) super.setRender(option);
    }

    @Override
    public ZCircle setDowned(boolean option) {
        return (ZCircle) super.setDowned(option);
    }

    @Override
    public ZCircle clearChild() {
        return (ZCircle) super.clearChild();
    }

    @Override
    public ZCircle removeChild(ZObject object) {
        return (ZCircle) super.removeChild(object);
    }

    @Override
    public ZCircle removeChild(String id) {
        return (ZCircle) super.removeChild(id);
    }

    @Override
    public ZCircle setChildIndexToLast(int before) {
        return (ZCircle)super.setChildIndexToLast(before);
    }

    @Override
    public ZCircle setChildIndex(int before, int after) {
        return (ZCircle)super.setChildIndex(before, after);
    }

    @Override
    public ZCircle setScalingCenter(ZPosF pos) {
        return (ZCircle)super.setScalingCenter(pos);
    }

    @Override
    public ZCircle setScalingCenter(float x, float y) {
        return (ZCircle)super.setScalingCenter(x, y);
    }

    @Override
    public ZCircle setScalingCenterX(float x) {
        return (ZCircle)super.setScalingCenterX(x);
    }

    @Override
    public ZCircle setScalingCenterY(float y) {
        return (ZCircle)super.setScalingCenterY(y);
    }

    @Override
    public ZCircle setTouchAble(boolean option) {
        return (ZCircle)super.setTouchAble(option);
    }
}

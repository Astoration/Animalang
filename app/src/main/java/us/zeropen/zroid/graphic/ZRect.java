package us.zeropen.zroid.graphic;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;

import us.zeropen.zroid.game.ZDefine;
import us.zeropen.zroid.game.scene.ZSceneMgr;
import us.zeropen.zroid.input.TouchEvent;

/**
 * Created by 병걸 on 2015-03-16.
 */
public class ZRect extends ZObject {
    public ZRect(String id, float x, float y, float width, float height, int color) {
        super("ZRect", id, x, y);
        this.width = width;
        this.height = height;
        setColor(color);
    }

    public ZRect(String id, float x, float y, float width, float height) {
        this(id, x, y, width, height, Color.BLACK);
    }

    public ZRect(String id, ZPosF leftTop, ZPosF rightBottom) {
        this(
                id,
                leftTop.x < rightBottom.x ? leftTop.x : rightBottom.x,
                leftTop.y < rightBottom.y ? leftTop.y : rightBottom.y,
                (leftTop.x < rightBottom.x ? rightBottom.x : leftTop.x) - (leftTop.x < rightBottom.x ? leftTop.x : rightBottom.x),
                (leftTop.y < rightBottom.y ? rightBottom.y : leftTop.y) - (leftTop.y < rightBottom.y ? leftTop.y : rightBottom.y)
        );
    }

    public ZRect(String id, RectF rect) {
        this(id, rect.left, rect.top, rect.width(), rect.height(), Color.BLACK);
    }

    @Override
    public ZPosF getCameraCenterPos() {
        return super.getCameraCenterPos();
    }

    @Override
    public float getCameraCenterPosX() {
        return super.getCameraCenterPosX();
    }

    @Override
    public float getCameraCenterPosY() {
        return super.getCameraCenterPosY();
    }

    @Override
    public void render() {
        ZSceneMgr.canvas.rotate(degree, rotationCenter.x / ZDefine.GAME_SCALE_X, rotationCenter.y / ZDefine.GAME_SCALE_Y);

        ZSceneMgr.canvas.save();
        ZSceneMgr.canvas.scale(scaleX, scaleY, (getCameraPosX() + scalingCenter.x) / ZDefine.GAME_SCALE_X, (getCameraPosY() + scalingCenter.y) / ZDefine.GAME_SCALE_Y);

        int sL = (int) (getCameraPosX() / ZDefine.GAME_SCALE_X);
        int sT = (int) (getCameraPosY() / ZDefine.GAME_SCALE_Y);
        int sR = (int) ((getCameraPosX() + width) / ZDefine.GAME_SCALE_X);
        int sB = (int) ((getCameraPosY() + height) / ZDefine.GAME_SCALE_Y);
        Rect r = new Rect(sL, sT, sR, sB);
        ZSceneMgr.canvas.drawRect(r, paint);

        ZSceneMgr.canvas.restore();

        super.render();
        ZSceneMgr.canvas.rotate(-degree, rotationCenter.x / ZDefine.GAME_SCALE_X, rotationCenter.y / ZDefine.GAME_SCALE_Y);
    }

    @Override
    public ZRect setCamera(boolean option) {
        return (ZRect) super.setCamera(option);
    }

    @Override
    public boolean isTouched(TouchEvent event) {
        if (!touchAble) return false;

        RectF r = new RectF(getCameraPosX(), getCameraPosY(), getCameraPosX() + getScaledWidth(), getCameraPosY() + getScaledHeight());
        return r.contains(event.pos.x, event.pos.y);
    }

    @Override
    public ZRect setColor(int color) {
        return (ZRect) super.setColor(color);
    }

    @Override
    public ZRect setPos(float x, float y) {
        return (ZRect) super.setPos(x, y);
    }

    @Override
    public ZRect setPosX(float x) {
        return (ZRect) super.setPosX(x);
    }

    @Override
    public ZRect setPosY(float y) {
        return (ZRect) super.setPosY(y);
    }

    @Override
    public ZRect setPos(ZPosF pos) {
        return (ZRect) super.setPos(pos);
    }

    @Override
    public ZRect addRotate(float degree) {
        return (ZRect) super.addRotate(degree);
    }

    @Override
    public ZRect setRotate(float degree) {
        return (ZRect) super.setRotate(degree);
    }

    @Override
    public ZRect addPos(float x, float y) {
        return (ZRect) super.addPos(x, y);
    }

    @Override
    public ZRect setRotate(float degree, boolean option) {
        return (ZRect) super.setRotate(degree, option);
    }

    @Override
    public ZRect setRotate(float degree, ZPosF rotationCenter) {
        return (ZRect) super.setRotate(degree, rotationCenter);
    }

    @Override
    public ZRect setRotate(float degree, int rotationCenterX, int rotationCenterY) {
        return (ZRect) super.setRotate(degree, rotationCenterX, rotationCenterY);
    }

    @Override
    public ZRect setRotationCenter(ZPosF pos) {
        return (ZRect) super.setRotationCenter(pos);
    }

    @Override
    public ZRect setRotationCenter(ZPos pos) {
        return (ZRect) super.setRotationCenter(pos);
    }

    @Override
    public ZRect setRotationCenter(float x, float y) {
        return (ZRect) super.setRotationCenter(x, y);
    }

    @Override
    public ZRect setRotationCenter(int x, int y) {
        return (ZRect) super.setRotationCenter(x, y);
    }

    @Override
    public ZRect addPosY(float y) {
        return (ZRect) super.addPosY(y);
    }

    @Override
    public ZRect addPosX(float x) {
        return (ZRect) super.addPosX(x);
    }

    @Override
    public ZRect setWidth(float width) {
        return (ZRect) super.setWidth(width);
    }

    @Override
    public ZRect setHeight(float height) {
        return (ZRect) super.setHeight(height);
    }

    @Override
    public ZRect setAlpha(float alpha) {
        return (ZRect) super.setAlpha(alpha);
    }

    @Override
    public ZRect addAlpha(float alpha) {
        return (ZRect) super.addAlpha(alpha);
    }

    @Override
    public ZRect setScale(float scale) {
        return (ZRect) super.setScale(scale);
    }

    @Override
    public ZRect setScaleX(float scaleX) {
        return (ZRect) super.setScaleX(scaleX);
    }

    @Override
    public ZRect setScaleY(float scaleY) {
        return (ZRect) super.setScaleY(scaleY);
    }

    @Override
    public ZRect setScale(float scaleX, float scaleY) {
        return (ZRect)super.setScale(scaleX, scaleY);
    }

    @Override
    public ZRect addScale(float scaleX, float scaleY) {
        return (ZRect)super.addScale(scaleX, scaleY);
    }

    @Override
    public ZRect addScale(float scale) {
        return (ZRect)super.addScale(scale);
    }

    @Override
    public ZRect addScaleX(float scaleX) {
        return (ZRect)super.addScaleX(scaleX);
    }

    @Override
    public ZRect addScaleY(float scaleY) {
        return (ZRect)super.addScaleY(scaleY);
    }

    @Override
    public ZRect setUpdate(boolean option) {
        return (ZRect) super.setUpdate(option);
    }

    @Override
    public ZRect setRender(boolean option) {
        return (ZRect) super.setRender(option);
    }

    @Override
    public ZRect setDowned(boolean option) {
        return (ZRect) super.setDowned(option);
    }

    @Override
    public ZRect removeChild(ZObject object) {
        return (ZRect) super.removeChild(object);
    }

    @Override
    public ZRect removeChild(String id) {
        return (ZRect) super.removeChild(id);
    }

    public float left() {
        return pos.x;
    }

    public float top() {
        return pos.y;
    }

    public float right() {
        return pos.x + width;
    }

    public float bottom() {
        return pos.y + height;
    }

    @Override
    public ZRect setChildIndexToLast(int before) {
        return (ZRect)super.setChildIndexToLast(before);
    }

    @Override
    public ZRect setChildIndex(int before, int after) {
        return (ZRect)super.setChildIndex(before, after);
    }

    public ZRect setScalingCenter(ZPosF pos) {
        scalingCenter = pos;
        return this;
    }

    public ZRect setScalingCenter(float x, float y) {
        scalingCenter.setPos(x, y);
        return this;
    }

    public ZRect setScalingCenterX(float x) {
        scalingCenter.x = x;
        return this;
    }

    public ZRect setScalingCenterY(float y) {
        scalingCenter.y = y;
        return this;
    }

    public ZPosF getScalingCenter() {
        return scalingCenter;
    }

    public float getScalingCenterX() {
        return scalingCenter.x;
    }

    public float getScalingCenterY() {
        return scalingCenter.y;
    }

    @Override
    public ZRect setTouchPointer(int pointer) {
        return (ZRect)super.setTouchPointer(pointer);
    }

    @Override
    public ZRect setTouchAble(boolean option) {
        return (ZRect)super.setTouchAble(option);
    }
}

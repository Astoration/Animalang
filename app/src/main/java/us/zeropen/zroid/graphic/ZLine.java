package us.zeropen.zroid.graphic;

import android.graphics.Color;
import android.util.Log;

import us.zeropen.zroid.game.ZDefine;
import us.zeropen.zroid.game.scene.ZSceneMgr;
import us.zeropen.zroid.input.TouchEvent;

/**
 * Created by 병걸 on 2015-03-21.
 */
public class ZLine extends ZObject {
    private float lineWidth;
    private ZPosF endPos;

    public ZLine(String id, float startX, float startY, float endX, float endY, float lineWidth, int color) {
        super("ZLine", id, startX, startY);
        this.lineWidth = lineWidth;
        endPos = new ZPosF(endX, endY);
        width = endX - startX;
        height = endY - startY;
        setColor(color);
        paint.setStrokeWidth(lineWidth / ZDefine.GAME_SCALE_X);
        touchAble = false;
    }

    public ZLine(String id, ZPosF startPos, ZPosF endPos, float lineWidth, int color) {
        this(id, startPos.x, startPos.y, endPos.x, endPos.y, lineWidth, color);
    }

    public ZLine(String id, float startX, float startY, float endX, float endY, float lineWidth) {
        this(id, startX, startY, endX, endY, lineWidth, Color.BLACK);
    }

    public ZLine(String id, ZPos startPos, ZPos endPos, float lineWidth) {
        this(id, startPos.x, startPos.y, endPos.x, endPos.y, lineWidth, Color.BLACK);
    }

    public ZLine(String id, float startX, float startY, float endX, float endY) {
        this(id, startX, startY, endX, endY, 2, Color.BLACK);
    }

    public ZLine(String id, ZPosF startPos, ZPosF endPos, float lineWidth) {
        this(id, startPos.x, startPos.y, endPos.x, endPos.y, lineWidth, Color.BLACK);
    }

    public ZLine(String id, ZPosF startPos, ZPosF endPos) {
        this(id, startPos.x, startPos.y, endPos.x, endPos.y, 2, Color.BLACK);
    }

    @Override
    public void update(float eTime) {
        super.update(eTime);
    }

    @Override
    public void render() {
        ZSceneMgr.canvas.rotate(degree, rotationCenter.x / ZDefine.GAME_SCALE_X, rotationCenter.y / ZDefine.GAME_SCALE_Y);
        ZSceneMgr.canvas.save();
        ZSceneMgr.canvas.scale(scaleX, scaleY, (getCameraPosX() + scalingCenter.x) / ZDefine.GAME_SCALE_X, (getCameraPosY() + scalingCenter.y) / ZDefine.GAME_SCALE_Y);
        ZSceneMgr.canvas.drawLine(getCameraPos().x / ZDefine.GAME_SCALE_X, getCameraPos().y / ZDefine.GAME_SCALE_Y, (getCameraPosX() + width) / ZDefine.GAME_SCALE_X, (getCameraPosY() + height) / ZDefine.GAME_SCALE_Y, paint);
        ZSceneMgr.canvas.restore();
        super.render();
        ZSceneMgr.canvas.rotate(-degree, rotationCenter.x / ZDefine.GAME_SCALE_X, rotationCenter.y / ZDefine.GAME_SCALE_Y);
    }

    @Override
    public ZLine setRotationCenter(ZPos pos) {
        return (ZLine) super.setRotationCenter(pos);
    }

    @Override
    public ZLine setRotationCenter(ZPosF pos) {
        return (ZLine) super.setRotationCenter(pos);
    }

    @Override
    public ZLine setRotationCenter(float x, float y) {
        return (ZLine) super.setRotationCenter(x, y);
    }

    @Override
    public ZLine setRotationCenter(int x, int y) {
        return (ZLine) super.setRotationCenter(x, y);
    }

    @Override
    public boolean isTouched(TouchEvent event) {
        Log.e(type, "(id: " + id + ") isTouched(TouchEvent event) - 이 클래스에서는 사용할 수 없는 함수입니다");
        return false;
    }

    @Override
    public int getTouchPointer() {
        Log.e(type, "(id: " + id + ") / getTouchPointer() - 이 클래스에서는 사용할 수 없는 함수입니다");
        return -1;
    }

    @Override
    public ZLine setDowned(boolean option) {
        Log.e(type, "(id: " + id + ") / setDownPos(boolean option) - 이 클래스에서는 사용할 수 없는 함수입니다");
        return null;
    }

    @Override
    public ZPosF getDownPos() {
        Log.e(type, "(id: " + id + ") / getDownPos() - 이 클래스에서는 사용할 수 없는 함수입니다");
        return null;
    }

    public ZLine setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
        paint.setStrokeWidth(lineWidth / ZDefine.GAME_SCALE_X);
        return this;
    }

    public float getLineWidth() {
        return lineWidth;
    }

    @Override
    public ZLine clearChild() {
        return (ZLine) super.clearChild();
    }

    @Override
    public ZLine removeChild(ZObject object) {
        return (ZLine) super.removeChild(object);
    }

    @Override
    public ZLine removeChild(String id) {
        return (ZLine) super.removeChild(id);
    }

    @Override
    public ZLine setRender(boolean option) {
        return (ZLine) super.setRender(option);
    }

    @Override
    public ZLine setUpdate(boolean option) {
        return (ZLine) super.setUpdate(option);
    }

    @Override
    public ZLine setScaleY(float scaleY) {
        return (ZLine) super.setScaleY(scaleY);
    }

    @Override
    public ZLine setScaleX(float scaleX) {
        return (ZLine) super.setScaleX(scaleX);
    }

    @Override
    public ZLine setScale(float scale) {
        return (ZLine) super.setScale(scale);
    }

    @Override
    public ZLine setScale(float scaleX, float scaleY) {
        return (ZLine) super.setScale(scaleX, scaleY);
    }

    @Override
    public ZLine addScale(float scaleX, float scaleY) {
        return (ZLine)super.addScale(scaleX, scaleY);
    }

    @Override
    public ZLine addScale(float scale) {
        return (ZLine)super.addScale(scale);
    }

    @Override
    public ZLine addScaleX(float scaleX) {
        return (ZLine)super.addScaleX(scaleX);
    }

    @Override
    public ZLine addScaleY(float scaleY) {
        return (ZLine)super.addScaleY(scaleY);
    }

    @Override
    public ZLine setAlpha(float alpha) {
        return (ZLine) super.setAlpha(alpha);
    }

    @Override
    public ZLine addAlpha(float alpha) {
        return (ZLine) super.addAlpha(alpha);
    }

    @Override
    public ZLine setHeight(float height) {
        Log.e("ZLine", "(id: " + id + ") / setHeight(float height) - 이 클래스에서는 사용할 수 없는 함수입니다");
        return null;
    }

    @Override
    public ZLine setWidth(float width) {
        Log.e("ZLine", "(id: " + id + ") / setWidth(float width) - 이 클래스에서는 사용할 수 없는 함수입니다");
        return null;
    }

    @Override
    public float getWidth() {
        Log.e("ZLine", "(id: " + id + ") / getWidth() - 이 클래스에서는 사용할 수 없는 함수입니다");
        return -1;
    }

    @Override
    public float getHeight() {
        Log.e("ZLine", "(id: " + id + ") / getHeight() - 이 클래스에서는 사용할 수 없는 함수입니다");
        return -1;
    }

    @Override
    public ZLine addPosY(float y) {
        return (ZLine) super.addPosY(y);
    }

    @Override
    public ZLine addPosX(float x) {
        return (ZLine) super.addPosX(x);
    }

    @Override
    public ZLine setTouchPointer(int pointer) {
        Log.e(type, "(id: " + id + ") / setTouchPointer(int pointer) - 이 클래스에서는 사용할 수 없는 함수입니다");
        return null;
    }

    @Override
    public ZLine setDownPos(ZPosF pos) {
        Log.e(type, "(id: " + id + ") / setDownPos(ZPosF pos) - 이 클래스에서는 사용할 수 없는 함수입니다");
        return null;
    }

    @Override
    public ZLine setDownPos(float x, float y) {
        Log.e(type, "(id: " + id + ") / setDownPos(float x, float y) - 이 클래스에서는 사용할 수 없는 함수입니다");
        return null;
    }

    @Override
    public ZLine setPos(float x, float y) {
        return (ZLine) super.setPos(x, y);
    }

    @Override
    public ZLine setPosX(float x) {
        return (ZLine) super.setPosX(x);
    }

    @Override
    public ZLine setPosY(float y) {
        return (ZLine) super.setPosY(y);
    }

    @Override
    public ZLine setPos(ZPosF pos) {
        return (ZLine) super.setPos(pos);
    }

    public ZLine setEndPos(ZPos pos) {
        width = pos.x - this.pos.x;
        height = pos.y - this.pos.y;

        return this;
    }

    public ZLine setEndPos(ZPosF pos) {
        width = pos.x - this.pos.x;
        height = pos.y - this.pos.y;

        return this;
    }

    public ZLine addEndPos(ZPos pos) {
        width += pos.x;
        height += pos.y;

        return this;
    }

    public ZLine addEndPos(ZPosF pos) {
        width += pos.x;
        height += pos.y;

        return this;
    }

    public ZLine addEndPos(float addX, float addY) {
        width += addX;
        height += addY;
        return this;
    }

    public ZPosF getEndPos() {
        return new ZPosF(pos.x + width, pos.y + height);
    }

    @Override
    public ZLine setCamera(boolean option) {
        return (ZLine)super.setCamera(option);
    }

    @Override
    public ZLine setColor(int color) {
        return (ZLine) super.setColor(color);
    }

    @Override
    public ZLine setRotate(float degree) {
        return (ZLine) super.setRotate(degree);
    }

    @Override
    public ZLine setRotate(float degree, int rotationCenterX, int rotationCenterY) {
        return (ZLine) super.setRotate(degree, rotationCenterX, rotationCenterY);
    }

    @Override
    public ZLine setRotate(float degree, ZPosF rotationCenter) {
        return (ZLine) super.setRotate(degree, rotationCenter);
    }

    @Override
    public ZLine setRotate(float degree, boolean option) {
        return (ZLine) super.setRotate(degree, option);
    }

    @Override
    public ZLine addRotate(float degree) {
        return (ZLine) super.addRotate(degree);
    }

    @Override
    public ZLine addPos(float x, float y) {
        return (ZLine) super.addPos(x, y);
    }

    @Override
    public boolean contains(ZPos pos) {
        Log.e(type, "(id: " + id + ") / contains(ZPos pos) - 이 클래스에서는 사용할 수 없는 함수입니다");
        return false;
    }

    @Override
    public boolean contains(ZPosF pos) {
        Log.e(type, "(id: " + id + ") / contains(ZPosF pos) - 이 클래스에서는 사용할 수 없는 함수입니다");
        return false;
    }

    @Override
    public boolean contains(int posX, int posY) {
        Log.e(type, "(id: " + id + ") / contains(int posX, int posY) - 이 클래스에서는 사용할 수 없는 함수입니다");
        return false;
    }

    @Override
    public boolean contains(float posX, float posY) {
        Log.e(type, "(id: " + id + ") / contains(float posX, float posY) - 이 클래스에서는 사용할 수 없는 함수입니다");
        return false;
    }

    @Override
    public ZLine setChildIndexToLast(int before) {
        return (ZLine)super.setChildIndexToLast(before);
    }

    @Override
    public ZLine setChildIndex(int before, int after) {
        return (ZLine)super.setChildIndex(before, after);
    }

    @Override
    public ZLine setScalingCenter(ZPosF pos) {
        return (ZLine)super.setScalingCenter(pos);
    }

    @Override
    public ZLine setScalingCenter(float x, float y) {
        return (ZLine)super.setScalingCenter(x, y);
    }

    @Override
    public ZLine setScalingCenterX(float x) {
        return (ZLine)super.setScalingCenterX(x);
    }

    @Override
    public ZLine setScalingCenterY(float y) {
        return (ZLine)super.setScalingCenterY(y);
    }

    @Override
    public ZLine setTouchAble(boolean option) {
        Log.e(type, "(id: " + id + ") / setTouchAble(boolean option) - 이 클래스에서는 사용할 수 없는 함수입니다");
        return this;
    }
}

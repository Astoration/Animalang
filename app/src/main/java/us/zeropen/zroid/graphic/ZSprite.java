package us.zeropen.zroid.graphic;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.MaskFilter;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.Log;

import us.zeropen.zroid.device.ZToast;
import us.zeropen.zroid.game.ZDefine;
import us.zeropen.zroid.game.scene.ZSceneMgr;
import us.zeropen.zroid.input.TouchEvent;

/**
 * Created by 병걸 on 2015-03-16.
 * <p/>
 * ZSprite 는 Scene 에서 이미지를 출력하도록 도와주는 클래스입니다
 */
public class ZSprite extends ZObject {
    private String textureId;
    private ColorFilter colorFilter = null;
    private MaskFilter maskFilter = null;
    private Rect renderBound;

    public ZSprite(String id, String textureId, float x, float y) {
        super("ZSprite", id, x, y);
        this.textureId = textureId;
        scaleX = scaleY = 1.0f;
        width = ZGraphic.getTexture(textureId).getWidth();
        height = ZGraphic.getTexture(textureId).getHeight();
        renderBound = new Rect(0, 0, (int)width, (int)height);
        scalingCenter = new ZPosF();
    }

    public ZSprite setTexture(String textureId) {
        setWidth(ZGraphic.getTexture(textureId).getWidth());
        setHeight(ZGraphic.getTexture(textureId).getHeight());
        this.textureId = textureId;
        return this;
    }

    public ZSprite setTexture(String textureId, boolean changeWidthAndHeight) {
        if (changeWidthAndHeight) {
            setWidth(ZGraphic.getTexture(textureId).getWidth());
            setHeight(ZGraphic.getTexture(textureId).getHeight());
        }
        this.textureId = textureId;
        return this;
    }

    public String getTextureId() {
        return textureId;
    }

    public Bitmap getTexture() {
        return ZGraphic.getTexture(textureId);
    }

    @Override
    public ZSprite setColor(int color) {
        colorFilter = new LightingColorFilter(color, 0);
        paint.setColorFilter(colorFilter);
        return this;
    }

    public ZSprite setColor(int color, int add) {
        colorFilter = new LightingColorFilter(color, add);
        paint.setColorFilter(colorFilter);
        return this;
    }

    public ZSprite disableColor() {
        colorFilter = null;
        paint.setColorFilter(null);
        return this;
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
    public ZSprite setTouchPointer(int pointer) {
        return (ZSprite) super.setTouchPointer(pointer);
    }

    @Override
    public ZSprite setDownPos(ZPosF pos) {
        return (ZSprite) super.setDownPos(pos);
    }

    @Override
    public ZSprite setDownPos(float x, float y) {
        return (ZSprite) super.setDownPos(x, y);
    }

    @Override
    public ZSprite setPos(float x, float y) {
        return (ZSprite) super.setPos(x, y);
    }

    @Override
    public ZSprite setPosX(float x) {
        return (ZSprite) super.setPosX(x);
    }

    @Override
    public ZSprite setPosY(float y) {
        return (ZSprite) super.setPosY(y);
    }

    @Override
    public ZSprite setPos(ZPosF pos) {
        return (ZSprite) super.setPos(pos);
    }

    @Override
    public ZSprite setCamera(boolean option) {
        return (ZSprite)super.setCamera(option);
    }

    @Override
    public ZSprite setRotate(float degree) {
        return (ZSprite) super.setRotate(degree);
    }

    @Override
    public ZSprite setRotate(float degree, int rotationCenterX, int rotationCenterY) {
        return (ZSprite) super.setRotate(degree, rotationCenterX, rotationCenterY);
    }

    @Override
    public ZSprite setRotate(float degree, ZPosF rotationCenter) {
        return (ZSprite) super.setRotate(degree, rotationCenter);
    }

    @Override
    public ZSprite setRotate(float degree, boolean option) {
        return (ZSprite) super.setRotate(degree, option);
    }

    @Override
    public ZSprite setRotationCenter(ZPos pos) {
        return (ZSprite) super.setRotationCenter(pos);
    }

    @Override
    public ZSprite setRotationCenter(ZPosF pos) {
        return (ZSprite) super.setRotationCenter(pos);
    }

    @Override
    public ZSprite setRotationCenter(float x, float y) {
        return (ZSprite) super.setRotationCenter(x, y);
    }

    @Override
    public ZSprite setRotationCenter(int x, int y) {
        return (ZSprite) super.setRotationCenter(x, y);
    }

    @Override
    public ZSprite addRotate(float degree) {
        return (ZSprite) super.addRotate(degree);
    }

    @Override
    public ZSprite addPos(float x, float y) {
        return (ZSprite) super.addPos(x, y);
    }

    @Override
    public ZSprite addPosX(float x) {
        return (ZSprite) super.addPosX(x);
    }

    @Override
    public ZSprite addPosY(float y) {
        return (ZSprite) super.addPosY(y);
    }

    @Override
    public ZSprite setWidth(float width) {
        return (ZSprite) super.setWidth(width);
    }

    @Override
    public ZSprite setHeight(float height) {
        return (ZSprite) super.setHeight(height);
    }

    @Override
    public ZSprite setScale(float scaleX, float scaleY) {
        super.setScale(scaleX, scaleY);
        return this;
    }

    @Override
    public ZSprite setScale(float scale) {
        super.setScale(scale);
        return this;
    }

    @Override
    public ZSprite addScale(float scaleX, float scaleY) {
        super.addScale(scaleX, scaleY);
        return this;
    }

    @Override
    public ZSprite addScale(float scale) {
        super.addScale(scale);
        return this;
    }

    @Override
    public ZSprite addScaleX(float scaleX) {
        super.addScaleX(scaleX);
        return this;
    }

    @Override
    public ZSprite addScaleY(float scaleY) {
        super.addScaleY(scaleY);
        return this;
    }

    @Override
    public ZSprite setAlpha(float alpha) {
        return (ZSprite) super.setAlpha(alpha);
    }

    @Override
    public ZSprite addAlpha(float alpha) {
        return (ZSprite) super.addAlpha(alpha);
    }

    @Override
    public ZSprite setScaleX(float scaleX) {
        super.setScaleX(scaleX);
        return this;
    }

    @Override
    public ZSprite setScaleY(float scaleY) {
        super.setScaleY(scaleY);
        return this;
    }

    @Override
    public ZSprite setUpdate(boolean option) {
        return (ZSprite) super.setUpdate(option);
    }

    @Override
    public ZSprite setRender(boolean option) {
        return (ZSprite) super.setRender(option);
    }

    @Override
    public ZSprite setDowned(boolean option) {
        return (ZSprite) super.setDowned(option);
    }

    @Override
    public ZSprite removeChild(String id) {
        return (ZSprite) super.removeChild(id);
    }

    @Override
    public ZSprite removeChild(ZObject object) {
        return (ZSprite) super.removeChild(object);
    }

    @Override
    public ZSprite clearChild() {
        return (ZSprite) super.clearChild();
    }

    public ZSprite setBlur(float radius) {
        maskFilter = new BlurMaskFilter(radius, BlurMaskFilter.Blur.NORMAL);
        paint.setMaskFilter(maskFilter);

        return this;
    }

    public ZSprite setBlur(float radius, BlurMaskFilter.Blur style) {
        maskFilter = new BlurMaskFilter(radius, style);
        paint.setMaskFilter(maskFilter);

        return this;
    }

    public ZSprite disableBlur() {
        maskFilter = null;
        paint.setMaskFilter(null);

        return this;
    }

    public ZSprite setAntiAlias(boolean option) {
        paint.setAntiAlias(option);
        return this;
    }

    public ZSprite setFilterBitmap(boolean option) {
        paint.setFilterBitmap(option);
        return this;
    }

    @Override
    public void update(float eTime) {
        super.update(eTime);
    }

    @Override
    public void render() {
        ZSceneMgr.canvas.rotate(degree, rotationCenter.x / ZDefine.GAME_SCALE_X, rotationCenter.y / ZDefine.GAME_SCALE_Y);

        int x = (int) (getCameraPosX() / ZDefine.GAME_SCALE_X);
        int y = (int) (getCameraPosY() / ZDefine.GAME_SCALE_Y);
        int r = x + (int) (width / ZDefine.GAME_SCALE_X);
        int b = y + (int) (height / ZDefine.GAME_SCALE_Y);

        RectF dstRect = new RectF(x, y, r, b);
        ZSceneMgr.canvas.save();
        ZSceneMgr.canvas.scale(scaleX, scaleY, (getCameraPosX() + scalingCenter.x) / ZDefine.GAME_SCALE_X, (getCameraPosY() + scalingCenter.y) / ZDefine.GAME_SCALE_Y);
        ZSceneMgr.canvas.drawBitmap(ZGraphic.getTexture(textureId), renderBound, dstRect, paint);

        ZSceneMgr.canvas.restore();

        super.render();
        ZSceneMgr.canvas.rotate(-degree, rotationCenter.x / ZDefine.GAME_SCALE_X, rotationCenter.y / ZDefine.GAME_SCALE_Y);
    }

    @Override
    public boolean isTouched(TouchEvent event) {
        if (!touchAble) return false;

        RectF r = new RectF(getCameraPos().x, getCameraPos().y, getCameraPos().x + getScaledWidth(), getCameraPos().y + getScaledHeight());
        return r.contains(event.pos.x, event.pos.y);
    }

    @Override
    public ZSprite setChildIndexToLast(int before) {
        return (ZSprite)super.setChildIndexToLast(before);
    }

    @Override
    public ZSprite setChildIndex(int before, int after) {
        return (ZSprite)super.setChildIndex(before, after);
    }

    public ZSprite setRenderBound(int left, int top, int right, int bottom) {
        if (left < 0) left = 0;
        if (top < 0) top = 0;
        if (right > ZGraphic.getTexture(textureId).getWidth()) right = ZGraphic.getTexture(textureId).getWidth();
        if (bottom > ZGraphic.getTexture(textureId).getHeight()) bottom = ZGraphic.getTexture(textureId).getHeight();
        if (left > right) {
            int temp = left;
            left = right;
            right = temp;
        }
        if (top > bottom) {
            int temp = top;
            top = bottom;
            bottom = temp;
        }

        this.width = Math.abs(right - left);
        this.height = Math.abs(bottom -left);
        renderBound = new Rect(left, top, right, bottom);
        return this;
    }

    public ZSprite setRenderBound(Rect bound) {
        int left = bound.left;
        int top = bound.top;
        int right = bound.right;
        int bottom = bound.bottom;

        if (left < 0) left = 0;
        if (top < 0) top = 0;
        if (right > ZGraphic.getTexture(textureId).getWidth()) right = ZGraphic.getTexture(textureId).getWidth();
        if (bottom > ZGraphic.getTexture(textureId).getHeight()) bottom = ZGraphic.getTexture(textureId).getHeight();
        if (left > right) {
            int temp = left;
            left = right;
            right = temp;
        }
        if (top > bottom) {
            int temp = top;
            top = bottom;
            bottom = temp;
        }
        renderBound = new Rect(left, top, right, bottom);
        return this;
    }

    @Override
    public ZSprite setScalingCenter(ZPosF pos) {
        return (ZSprite)super.setScalingCenter(pos);
    }

    @Override
    public ZSprite setScalingCenter(float x, float y) {
        return (ZSprite)super.setScalingCenter(x, y);
    }

    @Override
    public ZSprite setScalingCenterX(float x) {
        return (ZSprite)super.setScalingCenterX(x);
    }

    @Override
    public ZSprite setScalingCenterY(float y) {
        return (ZSprite)super.setScalingCenterY(y);
    }

    @Override
    public ZSprite setTouchAble(boolean option) {
        return (ZSprite)super.setTouchAble(option);
    }
}

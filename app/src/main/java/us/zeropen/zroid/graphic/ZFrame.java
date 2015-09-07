package us.zeropen.zroid.graphic;

import android.util.Log;

/**
 * Created by 병걸 on 2015-06-12.
 */
public class ZFrame {
    protected float duration;
    protected String textureId;
    protected float width;
    protected float height;
    protected float scaleX, scaleY;

    public ZFrame(float duration, String textureId) {
        this.duration = duration;
        this.textureId = textureId;
        width = ZGraphic.getTexture(textureId).getWidth();
        height = ZGraphic.getTexture(textureId).getHeight();
        scaleX = scaleY = 1;
    }

    public float getDuration() {
        return duration;
    }

    public String getTextureId() {
        return textureId;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getScaledWidth() {
        return width * scaleX;
    }

    public float getScaledHeight() {
        return height * scaleY;
    }

    public ZFrame setWidth(float width) {
        this.width = width;
        return this;
    }

    public ZFrame setHeight(float height) {
        this.height = height;
        return this;
    }

    public ZFrame setScale(float scale) {
        scaleX = scaleY = scale;
        return this;
    }

    public ZFrame setScale(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        return this;
    }

    public ZFrame setScaleX(float scaleX) {
        this.scaleX = scaleX;
        return this;
    }

    public ZFrame setScaleY(float scaleY) {
        this.scaleY = scaleY;
        return this;
    }

    public ZFrame addScale(float scale) {
        scaleX += scale;
        scaleY += scale;
        return this;
    }

    public ZFrame addScale(float scaleX, float scaleY) {
        this.scaleX += scaleX;
        this.scaleY += scaleY;
        return this;
    }

    public ZFrame addScaleX(float scaleX) {
        this.scaleX += scaleX;
        return this;
    }

    public ZFrame addScaleY(float scaleY) {
        this.scaleY += scaleY;
        return this;
    }

    public ZPosF getScale() {
        return new ZPosF(scaleX, scaleY);
    }

    public float getScaleX() {
        return scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

}
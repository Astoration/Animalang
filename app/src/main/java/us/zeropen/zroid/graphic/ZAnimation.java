package us.zeropen.zroid.graphic;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import us.zeropen.zroid.game.ZDefine;
import us.zeropen.zroid.game.scene.ZSceneMgr;
import us.zeropen.zroid.input.TouchEvent;

/**
 * Created by 병걸 on 2015-06-08.
 */
public class ZAnimation extends ZObject {
    private ArrayList<ZFrame> frames;
    private int currentFrame;
    private float currentPlayTime;
    private boolean pause;
    private boolean loop;
    private float playSpeed;

    public ZAnimation(String id, float x, float y) {
        super("ZAnimation", id, x, y);
        pause = false;
        loop = true;
        frames = new ArrayList<ZFrame>();
        currentFrame = 0;
        currentPlayTime = 0;
        playSpeed = 1;
        scalingCenter = new ZPosF();
    }

    @Override
    public void update(float eTime) {
        if (!pause && frames.size() > 0) {
            if (currentPlayTime >= frames.get(currentFrame).getDuration()) {
                if (currentFrame >= frames.size() -1) {
                    if (!loop) {
                        pause = true;
                    }
                    else {
                        currentFrame = 0;
                        currentPlayTime = 0;
                        width = ZGraphic.getTexture(frames.get(currentFrame).getTextureId()).getWidth();
                        height = ZGraphic.getTexture(frames.get(currentFrame).getTextureId()).getHeight();
                    }
                }
                else {
                    ++currentFrame;
                    currentPlayTime = 0;
                    width = ZGraphic.getTexture(frames.get(currentFrame).getTextureId()).getWidth();
                    height = ZGraphic.getTexture(frames.get(currentFrame).getTextureId()).getHeight();
                }
            }
            super.update(eTime);
            currentPlayTime += eTime * playSpeed;
        }
    }

    @Override
    public void render() {
        ZSceneMgr.canvas.rotate(degree, rotationCenter.x / ZDefine.GAME_SCALE_X, rotationCenter.y / ZDefine.GAME_SCALE_Y);
        if (frames.size() > 0) {
            int x = (int) (getCameraPosX() / ZDefine.GAME_SCALE_X);
            int y = (int) (getCameraPosY() / ZDefine.GAME_SCALE_Y);
            ZSceneMgr.canvas.save();
            ZSceneMgr.canvas.scale(scaleX, scaleY, (getCameraPosX() + scalingCenter.x) / ZDefine.GAME_SCALE_X, (getCameraPosY() + scalingCenter.y) / ZDefine.GAME_SCALE_Y);
            ZSceneMgr.canvas.scale(getFrame().scaleX, getFrame().scaleY, (getCameraPosX() + scalingCenter.x) / ZDefine.GAME_SCALE_X, (getCameraPosY() + scalingCenter.y) / ZDefine.GAME_SCALE_Y);

            Rect srcRect = new Rect(0, 0, ZGraphic.getTexture(frames.get(currentFrame).getTextureId()).getWidth(), ZGraphic.getTexture(frames.get(currentFrame).getTextureId()).getHeight());
            Rect dstRect = new Rect(x, y, x + (int) (width * frames.get(currentFrame).getScaleX() / ZDefine.GAME_SCALE_X), y + (int) (height * frames.get(currentFrame).getScaleY() / ZDefine.GAME_SCALE_Y));
            ZSceneMgr.canvas.drawBitmap(ZGraphic.getTexture(frames.get(currentFrame).getTextureId()), srcRect, dstRect, paint);

            ZSceneMgr.canvas.restore();
        }
        super.render();
        ZSceneMgr.canvas.rotate(-degree, rotationCenter.x / ZDefine.GAME_SCALE_X, rotationCenter.y / ZDefine.GAME_SCALE_Y);
    }

    public boolean isPaused() {
        return pause;
    }

    public ZAnimation setPause(boolean option) {
        pause = option;
        return this;
    }

    public ZAnimation setFrameWidth(int index, float width) {
        frames.get(index).setWidth(width);
        return this;
    }

    public ZAnimation setFrameHeight(int index, float height) {
        frames.get(index).setHeight(height);
        return this;
    }

    public ZAnimation addFrame(float duration, String textureId) {
        if (frames.size() == 0) {
            width = ZGraphic.getTexture(textureId).getWidth();
            height = ZGraphic.getTexture(textureId).getHeight();
        }
        frames.add(new ZFrame(duration, textureId));
        return this;
    }

    public ZAnimation removeFrame(int index) {
        if (index < 0 || index >= frames.size()) {
            Log.e(type, "(id: " + id + ") / removeFrame(int index) - " + index + "번째 인덱스에 frame 객체가 존재하지 않습니다");
            return null;
        }

        frames.remove(index);
        return this;
    }

    public ZFrame getFrame() {
        if (frames.size() == 0) {
            Log.e(type, "getFrame() - frame 이 존재하지 않습니다");
            return null;
        }

        return frames.get(currentFrame);
    }

    public ZFrame getFrame(int frameIndex) {
        if (frameIndex < 0 || frameIndex >= frames.size()) {
            Log.e(type, "getFrame(int frame" +
                    "*Index) " + frameIndex + " 번째 인덱스에 frame 이 존재하지 않습니다");
            return null;
        }
        return frames.get(frameIndex);
    }

    public ZFrame getFrame(String frameTextureId) {
        for (int i = 0; i < frames.size(); i++) {
            if (frames.get(i).getTextureId().equals(frameTextureId)) {
                return frames.get(i);
            }
        }
        Log.e(type, "getFrame(String frameTextureId) " + frameTextureId + " 라는 textureId를 가진 frame 이 존재하지 않습니다");
        return null;
    }

    public int getFrameIndex(String frameTextureId) {
        for (int i = 0; i < frames.size(); i++) {
            if (frames.get(i).getTextureId().equals(frameTextureId)) {
                return i;
            }
        }
        Log.e(type, "getFrameIndex(String frameTextureId) " + frameTextureId + " 라는 textureId를 가진 frame 이 존재하지 않습니다");
        return -1;
    }

    public int getCurrentFrameIndex() {
        return currentFrame;
    }

    public ZAnimation setCurrentFrame(int frameIndex) {
        if (frameIndex >= frames.size() || frameIndex < 0) {
            Log.e(type, "(id: " + id + ") / setCurrentFrame(int frameIndex) - " + frameIndex + " 번째 프레임이 존재하지 않습니다");
            return null;
        }
        currentFrame = frameIndex;
        currentPlayTime = 0;
        return this;
    }

    public Bitmap getFrameTexture(int frameIndex) {
        return ZGraphic.getTexture(frames.get(frameIndex).getTextureId());
    }

    public ZAnimation setPlaySpeed(float playSpeed) {
        if (playSpeed <= 0)
            Log.e(type, "(id: " + id + ") / setPlaySpeed(float playSpeed) - 함수 인자는 0보다 큰 값이어야 합니다");
         else
            this.playSpeed = playSpeed;

        return this;
    }

   public float getPlaySpeed() {
       return playSpeed;
   }

    public ZAnimation addPlaySpeed(float playSpeed) {
        if (this.playSpeed + playSpeed <= 0)
            Log.e(type, "(id: " + id + ") / addPlaySpeed(float playSpeed) - 재생 속도는 0보다 커야 합니다");

        this.playSpeed += playSpeed;
        return this;
    }

    @Override
    public boolean isTouched(TouchEvent event) {
        if (!touchAble) return false;

        RectF r = new RectF(getCameraPosX(), getCameraPosY(), getCameraPosX() + getScaledWidth(), getCameraPosY() + getScaledHeight());
        return r.contains(event.pos.x, event.pos.y);
    }

    @Override
    public float getScaledHeight() {
        return getFrame().getHeight() * scaleY;
    }

    @Override
    public float getScaledWidth() {
        return getFrame().getWidth() * scaleX;
    }

    @Override
    public float getWidth() {
        return getFrame().getWidth();
    }

    @Override
    public float getHeight() {
        return getFrame().getHeight();
    }

    @Override
    public ZObject setTouchPointer(int pointer) {
        return super.setTouchPointer(pointer);
    }

    @Override
    public int getTouchPointer() {
        return super.getTouchPointer();
    }

    @Override
    public ZAnimation setDownPos(ZPosF pos) {
        return (ZAnimation)super.setDownPos(pos);
    }

    @Override
    public ZAnimation setDownPos(float x, float y) {
        return (ZAnimation)super.setDownPos(x, y);
    }

    @Override
    public ZAnimation setPos(float x, float y) {
        return (ZAnimation)super.setPos(x, y);
    }

    @Override
    public ZAnimation setPosX(float x) {
        return (ZAnimation)super.setPosX(x);
    }

    @Override
    public ZAnimation setPosY(float y) {
        return (ZAnimation)super.setPosY(y);
    }

    @Override
    public ZAnimation setPos(ZPosF pos) {
        return (ZAnimation)super.setPos(pos);
    }

    @Override
    public ZAnimation setColor(int color) {
        return (ZAnimation)super.setColor(color);
    }

    @Override
    public ZAnimation setRotationCenter(ZPos pos) {
        return (ZAnimation)super.setRotationCenter(pos);
    }

    @Override
    public ZAnimation setRotationCenter(ZPosF pos) {
        return (ZAnimation)super.setRotationCenter(pos);
    }

    @Override
    public ZAnimation setRotationCenter(float x, float y) {
        return (ZAnimation)super.setRotationCenter(x, y);
    }

    @Override
    public ZAnimation setRotationCenter(int x, int y) {
        return (ZAnimation)super.setRotationCenter(x, y);
    }

    @Override
    public ZAnimation addRotate(float degree) {
        return (ZAnimation)super.addRotate(degree);
    }

    @Override
    public ZAnimation setRotate(float degree) {
        return (ZAnimation)super.setRotate(degree);
    }

    @Override
    public ZAnimation setRotate(float degree, int rotationCenterX, int rotationCenterY) {
        return (ZAnimation)super.setRotate(degree, rotationCenterX, rotationCenterY);
    }

    @Override
    public ZAnimation setRotate(float degree, ZPosF rotationCenter) {
        return (ZAnimation)super.setRotate(degree, rotationCenter);
    }

    @Override
    public ZAnimation setRotate(float degree, boolean option) {
        return (ZAnimation)super.setRotate(degree, option);
    }

    @Override
    public ZAnimation addPos(float x, float y) {
        return (ZAnimation)super.addPos(x, y);
    }

    @Override
    public ZAnimation addPosX(float x) {
        return (ZAnimation)super.addPosX(x);
    }

    @Override
    public ZAnimation addPosY(float y) {
        return (ZAnimation)super.addPosY(y);
    }

    @Override
    public ZAnimation setWidth(float width) {
        frames.get(currentFrame).setWidth(width);
        return this;
    }

    @Override
    public ZAnimation setHeight(float height) {
        frames.get(currentFrame).setHeight(height);
        return this;
    }

    @Override
    public ZAnimation setAlpha(float alpha) {
        return (ZAnimation)super.setAlpha(alpha);
    }

    @Override
    public ZAnimation addAlpha(float alpha) {
        return (ZAnimation)super.addAlpha(alpha);
    }

    @Override
    public ZAnimation setScale(float scaleX, float scaleY) {
        super.setScale(scaleX, scaleY);
        return this;
    }

    @Override
    public ZAnimation setScale(float scale) {
       super.setScale(scale);
        return this;
    }

    @Override
    public ZAnimation setScaleX(float scaleX) {
        super.setScaleX(scaleX);
        return this;
    }

    @Override
    public ZAnimation setScaleY(float scaleY) {
        super.setScaleY(scaleY);
        return this;
    }

    @Override
    public ZAnimation addScale(float scaleX, float scaleY) {
        super.addScale(scaleX, scaleY);
        return this;
    }

    @Override
    public ZAnimation addScale(float scale) {
        super.addScale(scale);
        return this;
    }

    @Override
    public ZAnimation addScaleX(float scaleX) {
        super.addScaleX(scaleX);
        return this;
    }

    @Override
    public ZAnimation addScaleY(float scaleY) {
        super.addScaleY(scaleY);
        return this;
    }

    public ZAnimation setFrameScale(int index, float scaleX, float scaleY) {
        frames.get(index).setScale(scaleX, scaleY);
        return this;
    }

    public ZAnimation setFrameScale(int index, float scale) {
        frames.get(index).setScale(scale);
        return this;
    }

    public ZAnimation setFrameScaleX(int index, float scaleX) {
        frames.get(index).setScaleX(scaleX);
        return this;
    }

    public ZAnimation setFrameScaleY(int index, float scaleY) {
        frames.get(index).setScaleY(scaleY);
        return this;
    }

    public ZAnimation addFrameScale(int index, float scaleX, float scaleY) {
        frames.get(index).addScale(scaleX, scaleY);
        return this;
    }

    public ZAnimation addFrameScale(int index, float scale) {
        frames.get(index).addScale(scale);
        return this;
    }

    public ZAnimation addFrameScaleX(int index, float scaleX) {
        frames.get(index).addScaleX(scaleX);
        return this;
    }

    public ZAnimation addFrameScaleY(int index, float scaleY) {
        frames.get(index).addScaleY(scaleY);
        return this;
    }

    @Override
    public ZAnimation setUpdate(boolean option) {
        return (ZAnimation)super.setUpdate(option);
    }

    @Override
    public ZAnimation setRender(boolean option) {
        return (ZAnimation)super.setRender(option);
    }

    @Override
    public ZAnimation setDowned(boolean option) {
        return (ZAnimation)super.setDowned(option);
    }

    @Override
    public ZAnimation setCamera(boolean option) {
        return (ZAnimation)super.setCamera(option);
    }

    @Override
    public ZAnimation setChildIndex(int before, int after) {
        return (ZAnimation)super.setChildIndex(before, after);
    }

    @Override
    public ZAnimation setChildIndexToLast(int before) {
        return (ZAnimation)super.setChildIndexToLast(before);
    }

    @Override
    public ZAnimation removeChild(int index) {
        return (ZAnimation)super.removeChild(index);
    }

    @Override
    public ZAnimation removeChild(String id) {
        return (ZAnimation)super.removeChild(id);
    }

    @Override
    public ZAnimation removeChild(ZObject object) {
        return (ZAnimation)super.removeChild(object);
    }

    @Override
    public ZAnimation clearChild() {
        return (ZAnimation)super.clearChild();
    }

    public boolean isLoop() {
        return loop;
    }

    public ZAnimation setLoop(boolean option) {
        if (option) pause = false;
        loop = option;
        return this;
    }

    @Override
    public float getCameraCenterPosX() {
        return super.getCameraCenterPosX();
    }

    @Override
    public ZPosF getCameraCenterPos() {
        return super.getCameraCenterPos();
    }

    @Override
    public float getCameraCenterPosY() {
        return super.getCameraCenterPosY();
    }

    @Override
    public ZAnimation setScalingCenterY(float y) {
        return (ZAnimation)super.setScalingCenterY(y);
    }

    @Override
    public ZAnimation setScalingCenter(ZPosF pos) {
        return (ZAnimation)super.setScalingCenter(pos);
    }

    @Override
    public ZAnimation setScalingCenter(float x, float y) {
        return (ZAnimation)super.setScalingCenter(x, y);
    }

    @Override
    public ZAnimation setScalingCenterX(float x) {
        return (ZAnimation)super.setScalingCenterX(x);
    }

    @Override
    public ZAnimation setTouchAble(boolean option) {
        return (ZAnimation)super.setTouchAble(option);
    }
}

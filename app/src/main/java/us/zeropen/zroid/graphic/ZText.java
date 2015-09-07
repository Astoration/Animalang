package us.zeropen.zroid.graphic;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;

import us.zeropen.zroid.game.ZDefine;
import us.zeropen.zroid.game.scene.ZSceneMgr;
import us.zeropen.zroid.input.TouchEvent;

/**
 * Created by 병걸 on 2015-04-07.
 */
public class ZText extends ZObject {
    public static final int ALIGN_CENTER = 2;
    public static final int ALIGN_RIGHT = 1;
    public static final int ALIGN_LEFT = 0;
    protected String text;
    protected String font;
    protected float size;
    protected Paint.Align align;
    protected Paint outLinePaint;
    protected boolean isOutLine;
    protected float lineHeight;

    public ZText(String id, String text, float x, float y, float size) {
        this(id, text, x, y, size, Color.BLACK, null);
    }

    public ZText(String id, String text, float x, float y, float size, int color) {
        this(id, text, x, y, size, color, null);
    }

    public ZText(String id, String text, float x, float y, float size, int color, String font) {
        super("ZText", id, x, y);
        this.text = text;
        align = Paint.Align.LEFT;
        outLinePaint = new Paint();
        outLinePaint.setStyle(Paint.Style.STROKE);
        outLinePaint.setAntiAlias(true);
        setColor(color);
        setSize(size);
        setFont(font);
        lineHeight = 10;
        touchAble = false;
        isOutLine = false;
    }

    public ZText setText(String text) {
        this.text = text;
        return this;
    }

    public String getText() {
        return text;
    }

    @Override
    public ZText setTouchPointer(int pointer) {
        Log.e("ZText", "(id: " + id + ") / setTouchPointer(int pointer) - 이 클래스에서 사용할 수 없는 함수입니다");
        return null;
    }

    @Override
    public ZText setDownPos(float x, float y) {
        Log.e("ZText", "(id: " + id + ") / setDownPos(float x, float y) - 이 클래스에서 사용할 수 없는 함수입니다");
        return null;
    }

    @Override
    public ZText setDownPos(ZPosF pos) {
        Log.e("ZText", "(id: " + id + ") / setDownPos(ZPosF pos) - 이 클래스에서 사용할 수 없는 함수입니다");
        return null;
    }

    @Override
    public ZPosF getDownPos() {
        Log.e("ZText", "(id: " + id + ") / getDownPos() - 이 클래스에서 사용할 수 없는 함수입니다");
        return null;
    }

    @Override
    public ZText setPos(float x, float y) {
        return (ZText) super.setPos(x, y);
    }

    @Override
    public ZText setPosX(float x) {
        return (ZText) super.setPosX(x);
    }

    @Override
    public ZText setPosY(float y) {
        return (ZText) super.setPosY(y);
    }

    @Override
    public ZText setCamera(boolean option) {
        return (ZText)super.setCamera(option);
    }

    @Override
    public ZText addRotate(float degree) {
        return (ZText) super.addRotate(degree);
    }

    @Override
    public ZText setPos(ZPosF pos) {
        return (ZText) super.setPos(pos);
    }

    @Override
    public ZText setRotate(float degree) {
        return (ZText) super.setRotate(degree);
    }

    @Override
    public ZText setRotate(float degree, int rotationCenterX, int rotationCenterY) {
        return (ZText) super.setRotate(degree, rotationCenterX, rotationCenterY);
    }

    @Override
    public ZText setRotate(float degree, ZPosF rotationCenter) {
        return (ZText) super.setRotate(degree, rotationCenter);
    }

    @Override
    public ZText setRotate(float degree, boolean option) {
        return (ZText) super.setRotate(degree, option);
    }

    @Override
    public ZText clearChild() {
        return (ZText) super.clearChild();
    }

    @Override
    public ZText removeChild(int index) {
        return (ZText)super.removeChild(index);
    }

    @Override
    public ZText removeChild(ZObject object) {
        return (ZText) super.removeChild(object);
    }

    @Override
    public ZText removeChild(String id) {
        return (ZText) super.removeChild(id);
    }

    @Override
    public ZText setDowned(boolean option) {
        Log.e("ZText", "(id: " + id + ") / setDowned(boolean option) - 이 클래스에서는 사용할 수 없는 함수입니다");
        return null;
    }

    @Override
    public ZText setRender(boolean option) {
        return (ZText) super.setRender(option);
    }

    @Override
    public ZText setUpdate(boolean option) {
        return (ZText) super.setUpdate(option);
    }

    @Override
    public ZText setAlpha(float alpha) {
        return (ZText) super.setAlpha(alpha);
    }

    @Override
    public ZText addAlpha(float alpha) {
        return (ZText) super.addAlpha(alpha);
    }

    @Override
    public ZText addPosY(float y) {
        return (ZText) super.addPosY(y);
    }

    @Override
    public ZText addPosX(float x) {
        return (ZText) super.addPosX(x);
    }

    @Override
    public ZText addPos(float x, float y) {
        return (ZText) super.addPos(x, y);
    }

    @Override
    public ZText setColor(int color) {
        return (ZText) super.setColor(color);
    }

    public ZText setFont(String font) {
        this.font = font;
        if (font == null) {
            paint.setTypeface(Typeface.DEFAULT);
            outLinePaint.setTypeface(Typeface.DEFAULT);
        } else {
            paint.setTypeface(ZGraphic.getFont(font));
            outLinePaint.setTypeface(ZGraphic.getFont(font));
        }

        return this;
    }

    public String getFont() {
        return font;
    }

    public ZText setSize(float size) {
        this.size = size;
        paint.setTextSize(size / ZDefine.GAME_SCALE_Y);
        outLinePaint.setTextSize(size / ZDefine.GAME_SCALE_Y);
        return this;
    }

    public ZText setAlign(int ALIGN_TYPE) {
        switch (ALIGN_TYPE) {
            case ALIGN_CENTER:
                this.align = Paint.Align.CENTER;
                break;
            case ALIGN_RIGHT:
                this.align = Paint.Align.RIGHT;
                break;
            case ALIGN_LEFT:
                this.align = Paint.Align.LEFT;
                break;
            default:
                Log.e("ZText", "(id: " + id + ") / setAlign(int ALIGN_TYPE) - 잘못된 인자입니다");
                return null;
        }
        paint.setTextAlign(align);
        outLinePaint.setTextAlign(align);

        return this;
    }

    public float getTextSize() {
        return size;
    }

    @Override
    public ZText setRotationCenter(ZPos pos) {
        return (ZText) super.setRotationCenter(pos);
    }

    @Override
    public ZText setRotationCenter(ZPosF pos) {
        return (ZText) super.setRotationCenter(pos);
    }

    @Override
    public ZText setRotationCenter(float x, float y) {
        return (ZText) super.setRotationCenter(x, y);
    }

    @Override
    public ZText setRotationCenter(int x, int y) {
        return (ZText) super.setRotationCenter(x, y);
    }

    @Override
    public ZText setScale(float scale) {
        return (ZText)super.setScale(scale);
    }

    @Override
    public ZText setScale(float scaleX, float scaleY) {
        return (ZText)super.setScale(scaleX, scaleY);
    }

    @Override
    public ZText setScaleX(float scaleX) {
        return (ZText)super.setScaleX(scaleX);
    }

    @Override
    public ZText setScaleY(float scaleY) {
        return (ZText)super.setScaleY(scaleY);
    }

    @Override
    public ZText addScale(float scaleX, float scaleY) {
        return (ZText)super.addScale(scaleX, scaleY);
    }

    @Override
    public ZText addScale(float scale) {
        return (ZText)super.addScale(scale);
    }

    @Override
    public ZText addScaleX(float scaleX) {
        return (ZText)super.addScaleX(scaleX);
    }

    @Override
    public ZText addScaleY(float scaleY) {
        return (ZText)super.addScaleY(scaleY);
    }

    @Override
    public boolean intersects(ZObject obj) {
        Log.e("ZText", "(id: " + id + ") / intersects(ZObject obj) - 이 클래스에서는 사용할 수 없는 함수입니다");
        return false;
    }

    @Override
    public ZRect getIntersects(ZObject obj) {
        Log.e("ZText", "(id: " + id + ") / getIntersects(ZObject obj) - 이 클래스에서는 사용할 수 없는 함수입니다");
        return null;
    }

    @Override
    public float getScaledWidth() {
        Log.e("ZText", "(id: " + id + ") / getScaledWidth() - 이 클래스에서는 사용할 수 없는 함수입니다");
        return 0;
    }

    @Override
    public float getScaledHeight() {
        Log.e("ZText", "(id: " + id + ") / getScaledHeight() - 이 클래스에서는 사용할 수 없는 함수입니다");
        return 0;
    }

    @Override
    public ZPosF getCenterPos() {
        Log.e("ZText", "(id: " + id + ") / getCenterPos() - 이 클래스에서는 사용할 수 없는 함수입니다");
        return pos;
    }

    @Override
    public float getCenterPosX() {
        Log.e("ZText", "(id: " + id + ") / getCenterPosX() - 이 클래스에서는 사용할 수 없는 함수입니다");
        return pos.x;
    }

    @Override
    public float getCenterPosY() {
        Log.e("ZText", "(id: " + id + ") / getCenterPosY() - 이 클래스에서는 사용할 수 없는 함수입니다");
        return pos.y;
    }

    @Override
    public ZPosF getCameraCenterPos() {
        Log.e("ZText", "(id: " + id + ") / getCameraCenterPos() - 이 클래스에서는 사용할 수 없는 함수입니다");
        return getCameraPos();
    }

    @Override
    public float getCameraCenterPosX() {
        Log.e("ZText", "(id: " + id + ") / getCameraCenterPosX() - 이 클래스에서는 사용할 수 없는 함수입니다");
        return getCameraPosX();
    }

    @Override
    public float getCameraCenterPosY() {
        Log.e("ZText", "(id: " + id + ") / getCameraCenterPosY() - 이 클래스에서는 사용할 수 없는 함수입니다");
        return getCameraPosY();
    }

    @Override
    public void render() {
        String[] texts = text.split("\n");

        ZSceneMgr.canvas.rotate(degree, rotationCenter.x / ZDefine.GAME_SCALE_X, rotationCenter.y / ZDefine.GAME_SCALE_Y);
        ZSceneMgr.canvas.save();
        ZSceneMgr.canvas.scale(scaleX, scaleY, (getCameraPosX() + scalingCenter.x) / ZDefine.GAME_SCALE_X, (getCameraPosY() + scalingCenter.y) / ZDefine.GAME_SCALE_Y);

        for (int i = 0; i<texts.length; i++) {
            if (isOutLine) {
                ZSceneMgr.canvas.drawText(texts[i], getCameraPosX() / ZDefine.GAME_SCALE_X, (getCameraPosY() + (size + lineHeight) * i) / ZDefine.GAME_SCALE_Y, outLinePaint);
            }
            ZSceneMgr.canvas.drawText(texts[i], getCameraPosX() / ZDefine.GAME_SCALE_X, (getCameraPosY() + (size + lineHeight) * i) / ZDefine.GAME_SCALE_Y, paint);
        }
        ZSceneMgr.canvas.restore();
        super.render();
        ZSceneMgr.canvas.rotate(-degree, rotationCenter.x / ZDefine.GAME_SCALE_X, rotationCenter.y / ZDefine.GAME_SCALE_Y);
    }

    @Override
    public ZText setWidth(float width) {
        Log.e("ZText", "(id: " + id + ") / getWidth(float width) - 이 클래스에서는 사용할 수 없는 함수입니다");
        return null;
    }

    @Override
    public ZText setHeight(float height) {
        Log.e("ZText", "(id: " + id + ") / getHeight(float height) - 이 클래스에서는 사용할 수 없는 함수입니다");
        return null;
    }

    @Override
    public boolean isTouched(TouchEvent event) {
        Log.e("ZText", "(id: " + id + ") / isTouched(TouchEvent event) - 이 클래스에서는 사용할 수 없는 함수입니다");
        return false;
    }

    @Override
    public float getWidth() {
        Log.e("ZText", "(id: " + id + ") / getWidth() - 이 클래스에서는 사용할 수 없는 함수입니다");
        return 0;
    }

    @Override
    public float getHeight() {
        Log.e("ZText", "(id: " + id + ") / getHeight() - 이 클래스에서는 사용할 수 없는 함수입니다");
        return 0;
    }

    @Override
    public boolean contains(ZPos pos) {
        Log.e("ZText", "(id: " + id + ") / contains(ZPos pos) - 이 클래스에서는 사용할 수 없는 함수입니다");
        return false;
    }

    @Override
    public boolean contains(ZPosF pos) {
        Log.e("ZText", "(id: " + id + ") / contains(ZPosF pos) - 이 클래스에서는 사용할 수 없는 함수입니다");
        return false;
    }

    @Override
    public boolean contains(int posX, int posY) {
        Log.e("ZText", "(id: " + id + ") / contains(int posX, int posY) - 이 클래스에서는 사용할 수 없는 함수입니다");
        return false;
    }

    @Override
    public boolean contains(float posX, float posY) {
        Log.e("ZText", "(id: " + id + ") / contains(float posX, float posY) - 이 클래스에서는 사용할 수 없는 함수입니다");
        return false;
    }

    @Override
    public ZText setChildIndex(int before, int after) {
        return (ZText)super.setChildIndex(before, after);
    }

    @Override
    public ZText setChildIndexToLast(int before) {
        return (ZText)super.setChildIndexToLast(before);
    }

    @Override
    public ZText setScalingCenterY(float y) {
        return (ZText)super.setScalingCenterY(y);
    }

    @Override
    public ZText setScalingCenter(float x, float y) {
        return (ZText)super.setScalingCenter(x, y);
    }

    @Override
    public ZText setScalingCenterX(float x) {
        return (ZText)super.setScalingCenterX(x);
    }

    @Override
    public ZText setScalingCenter(ZPosF pos) {
        return (ZText)super.setScalingCenter(pos);
    }

    @Override
    public ZText setTouchAble(boolean option) {
        Log.e("ZText", "(id: " + id + ") / setTouchAble(boolean option) - 이 클래스에서는 사용할 수 없는 함수입니다");
        return this;
    }

    public ZText setOutLine(boolean option) {
        isOutLine = option;
        return this;
    }

    public boolean isOutLine() {
        return isOutLine;
    }

    public ZText setLineHeight(float height) {
        lineHeight = height;
        return this;
    }

    public float getLineHeight() {
        return lineHeight;
    }

    public ZText setOutLineWidth(float width) {
        outLinePaint.setStrokeWidth(width / ZDefine.GAME_SCALE_X);
        isOutLine = true;
        return this;
    }

    public ZText setOutLineColor(int color) {
        outLinePaint.setColor(color);
        isOutLine = true;
        return this;
    }

    public Paint getOutLinePaint() {
        return outLinePaint;
    }
}
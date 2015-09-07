package us.zeropen.zroid.graphic;

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

import us.zeropen.zroid.game.camera.ZCameraMgr;
import us.zeropen.zroid.input.TouchEvent;

/**
 * Created by 병걸 on 2015-03-16.
 * <p/>
 * ZObject 는 Scene 에서 출력되는 모든 객체의 조상 클래스입니다
 */
public class ZObject {
    final protected String id;
    final protected String type;
    protected ZPosF pos;
    protected ZPosF downPos;
    protected ZPosF rotationCenter;
    protected ArrayList<ZObject> child;
    protected Paint paint;
    protected float alpha;
    protected int color;
    protected float scaleX, scaleY;
    protected float width;
    protected float height;
    protected float degree;
    protected boolean isRender;
    protected boolean isUpdate;
    protected boolean isDowned;
    protected boolean isCamera;
    protected int touchPointer;
    protected ZPosF scalingCenter;
    protected boolean touchAble;

    public ZObject(String type, String id, float x, float y) {
        this.id = id;
        this.type = type;
        pos = new ZPosF(x, y);
        downPos = new ZPosF(-1, -1);
        rotationCenter = new ZPosF(x, y);
        child = new ArrayList<>();
        alpha = 255;
        scaleX = scaleY = 1.0f;
        degree = 0;
        isRender = true;
        isUpdate = true;
        isDowned = false;
        isCamera = true;
        touchPointer = -1;
        paint = new Paint();
        scalingCenter = new ZPosF();

        touchAble = true;
        paint.setAntiAlias(true);
    }

    public void update(float eTime) {
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).isUpdate()) {
                child.get(i).update(eTime);
            }
        }
    }

    public void render() {
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).isRender()) {
                child.get(i).render();
            }
        }
    }

    public boolean isTouched(TouchEvent event) {
        return false;
    }

    public ZObject setTouchPointer(int pointer) {
        touchPointer = pointer;
        return this;
    }

    public int getTouchPointer() {
        return touchPointer;
    }

    public ZObject setDownPos(ZPosF pos) {
        downPos = pos;
        return this;
    }

    public ZObject setDownPos(float x, float y) {
        downPos = new ZPosF(x, y);
        return this;
    }

    public ZPosF getDownPos() {
        return downPos;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public ZPosF getPos() {
        return pos;
    }

    public float getPosX() {
        return pos.x;
    }

    public float getPosY() {
        return pos.y;
    }

    public ZPosF getCenterPos() {
        return new ZPosF(getCenterPosX(), getCenterPosY());
    }

    public ZPosF getCameraPos() {
        if (isCamera) return new ZPosF(pos.x - ZCameraMgr.getPosX(), pos.y - ZCameraMgr.getPosY());
        return pos;
    }

    public float getCameraPosX() {
        if (isCamera) return pos.x - ZCameraMgr.getPosX();
        return pos.x;
    }

    public float getCameraPosY() {
        if (isCamera) return pos.y - ZCameraMgr.getPosY();
        return pos.y;
    }

    public ZPosF getCameraCenterPos() {
        if (isCamera) return new ZPosF(getCenterPosX() - ZCameraMgr.getPosX(), getCenterPosY() - ZCameraMgr.getPosY());
        return getCenterPos();
    }

    public float getCameraCenterPosX() {
        if (isCamera) return getCenterPosX() - ZCameraMgr.getPosX();
        return pos.x;
    }

    public float getCameraCenterPosY() {
        if (isCamera) return getCenterPosY() - ZCameraMgr.getPosY();
        return pos.y;
    }

    public float getCenterPosX() {
        return getPosX() + getScaledWidth() / 2;
    }

    public float getCenterPosY() {
        return getPosY() + getScaledHeight() / 2;
    }


    public ZObject setPos(float x, float y) {
        float addX = x - pos.x;
        float addY = y - pos.y;
        pos.setPos(x, y);
        for (int i = 0; i < child.size(); i++) {
            child.get(i).addPos(addX, addY);
        }
        return this;
    }

    public ZObject setPosX(float x) {
        float addX = x - pos.x;
        pos.x = x;
        for (int i = 0; i < child.size(); i++) {
            child.get(i).addPosX(addX);
        }
        return this;
    }

    public ZObject setPosY(float y) {
        float addY = y - pos.y;
        pos.y = y;
        for (int i = 0; i < child.size(); i++) {
            child.get(i).addPosY(addY);
        }
        return this;
    }

    public ZObject setPos(ZPosF pos) {
        float addX = pos.x - this.pos.x;
        float addY = pos.y - this.pos.y;
        this.pos.setPos(pos.x, pos.y);
        for (int i = 0; i < child.size(); i++) {
            child.get(i).addPos(addX, addY);
        }
        return this;
    }

    public ZObject setColor(int color) {
        this.color = color;
        paint.setColor(color);

        return this;
    }

    public int getColor() {
        return color;
    }

    public ZObject setRotationCenter(ZPos pos) {
        rotationCenter.x = pos.x;
        rotationCenter.y = pos.y;

        for (int i=0; i<child.size(); i++) {
            child.get(i).setRotationCenter(pos);
        }

        return this;
    }

    public ZObject setRotationCenter(ZPosF pos) {
        rotationCenter.x = pos.x;
        rotationCenter.y = pos.y;

        for (int i=0; i<child.size(); i++) {
            child.get(i).setRotationCenter(pos);
        }

        return this;
    }

    public ZObject setRotationCenter(float x, float y) {
        rotationCenter.x = x;
        rotationCenter.y = y;

        for (int i=0; i<child.size(); i++) {
            child.get(i).setRotationCenter(rotationCenter);
        }

        return this;
    }

    public ZObject setRotationCenter(int x, int y) {
        rotationCenter.x = x;
        rotationCenter.y = y;

        for (int i=0; i<child.size(); i++) {
            child.get(i).setRotationCenter(rotationCenter);
        }

        return this;
    }

    public ZPosF getRotationCenter() {
        return rotationCenter;
    }

    public ZObject addRotate(float degree) {
        for (int i=0; i<child.size(); i++) {
            child.get(i).addRotate(degree);
        }
        this.degree += degree;
        return this;
    }

    public ZObject setRotate(float degree) {
        for (int i=0; i<child.size(); i++) {
            child.get(i).addRotate(degree - this.degree);
        }
        this.degree = degree;
        return this;
    }

    public ZObject setRotate(float degree, int rotationCenterX, int rotationCenterY) {
        for (int i=0; i<child.size(); i++) {
            child.get(i).addRotate(degree - this.degree).setRotationCenter(rotationCenterX, rotationCenterY);
        }
        this.degree = degree;
        rotationCenter.x = rotationCenterX;
        rotationCenter.y = rotationCenterY;

        return this;
    }

    public ZObject setRotate(float degree, ZPosF rotationCenter) {
        for (int i=0; i<child.size(); i++) {
            child.get(i).addRotate(degree - this.degree).setRotationCenter(rotationCenter);
        }
        this.degree = degree;
        this.rotationCenter = rotationCenter;

        return this;
    }

    public ZObject setRotate(float degree, boolean option) {
        this.degree = degree;
        if (option) {
            rotationCenter = getCenterPos();
            for (int i=0; i<child.size(); i++) {
                child.get(i).setRotationCenter(rotationCenter);
            }
        }

        return this;
    }

    public float getRotate() {
        return degree;
    }

    public ZObject addPos(float x, float y) {
        pos.addPos(x, y);
        for (int i = 0; i < child.size(); i++) {
            child.get(i).addPos(x, y);
        }

        return this;
    }

    public ZObject addPosX(float x) {
        pos.addPos(x, 0);
        for (int i = 0; i < child.size(); i++) {
            child.get(i).addPos(x, 0);
        }

        return this;
    }

    public ZObject addPosY(float y) {
        pos.addPos(0, y);
        for (int i = 0; i < child.size(); i++) {
            child.get(i).addPos(0, y);
        }

        return this;
    }

    public ZObject setWidth(float width) {
        this.width = width;
        return this;
    }

    public ZObject setHeight(float height) {
        this.height = height;
        return this;
    }

    public float getWidth() {
        return width;
    }

    public float getScaledWidth() {
        return Math.abs(width * scaleX);
    }

    public float getHeight() {
        return height;
    }

    public float getScaledHeight() {
        return (height * scaleY);
    }

    public ZObject setAlpha(float alpha) {
        if (alpha >= 0 && alpha <= 255) {
            this.alpha = alpha;
            paint.setAlpha((int)alpha);

            for (int i = 0; i < child.size(); i++) {
                child.get(i).setAlpha(alpha);
            }
        }
        return this;
    }

    public ZObject addAlpha(float alpha) {
        this.alpha += alpha;
        if (this.alpha < 0) {
            this.alpha = 0;
        }
        if (this.alpha > 255) {
            this.alpha = 255;
        }

        paint.setAlpha((int)this.alpha);

        for (int i = 0; i < child.size(); i++) {
            child.get(i).addAlpha(this.alpha);
        }

        return this;
    }

    public float getAlpha() {
        return alpha;
    }

    public ZObject setScale(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        for (int i = 0; i < child.size(); i++) {
            child.get(i).setScale(scaleX, scaleY);
        }
        return this;
    }

    public ZObject setScale(float scale) {
        this.scaleX = scale;
        this.scaleY = scale;
        for (int i = 0; i < child.size(); i++) {
            child.get(i).setScale(scale);
        }
        return this;
    }

    public ZObject setScaleX(float scaleX) {
        this.scaleX = scaleX;
        for (int i = 0; i < child.size(); i++) {
            child.get(i).setScaleX(scaleX);
        }
        return this;
    }

    public ZObject setScaleY(float scaleY) {
        this.scaleY = scaleY;
        for (int i = 0; i < child.size(); i++) {
            child.get(i).setScaleY(scaleY);
        }
        return this;
    }


    public ZObject addScale(float scaleX, float scaleY) {
        this.scaleX += scaleX;
        this.scaleY += scaleY;
        for (int i = 0; i < child.size(); i++) {
            child.get(i).addScale(scaleX, scaleY);
        }
        return this;
    }

    public ZObject addScale(float scale) {
        this.scaleX += scale;
        this.scaleY += scale;
        for (int i = 0; i < child.size(); i++) {
            child.get(i).addScale(scale);
        }
        return this;
    }

    public ZObject addScaleX(float scaleX) {
        this.scaleX += scaleX;
        for (int i = 0; i < child.size(); i++) {
            child.get(i).addScaleX(scaleX);
        }
        return this;
    }

    public ZObject addScaleY(float scaleY) {
        this.scaleY += scaleY;
        for (int i = 0; i < child.size(); i++) {
            child.get(i).addScaleY(scaleY);
        }
        return this;
    }

    public boolean intersects(ZObject obj) {
        if (type.equals("ZText") || type.equals("ZLine")) {
            Log.e(type, "(id: " + id + ") / intersects - 이 클래스에서는 사용할 수 없는 함수입니다.");
        }

        switch (obj.getType()) {
            case "ZCircle":
                return contains(obj.getPos());

            case "ZLine":
            case "ZText":
                Log.e(type, "(id: " + id + ") / intersects - " + obj.getType() + " 타입의 object 와는 이 함수를 사용할 수 없습니다");

            default:
                return !(getCameraPosX() + getScaledWidth() < obj.getCameraPosX()
                        || getCameraPosY() + getScaledHeight() < obj.getCameraPosY()
                        || obj.getCameraPosX() + obj.getScaledWidth() < getCameraPosX()
                        || obj.getCameraPosY() + obj.getScaledHeight() < getCameraPosY());
        }
    }

    public ZRect getIntersects(ZObject obj) {
        if (type.equals("ZText") || type.equals("ZLine") || type.equals("ZCircle")) {
            Log.e(type, "(id: " + id + ") / getIntersects - 이 클래스에서는 사용할 수 없는 함수입니다.");
        }

        switch (obj.getType()) {
            case "ZLine":
            case "ZText":
            case "ZCircle":
                Log.e(type, "(id: " + id + ") / getIntersects - " + obj.getType() + " 타입의 object 와는 이 함수를 사용할 수 없습니다");

            default:
                if (intersects(obj)) {
                    float x = Math.max(getCameraPosX(), obj.getCameraPosX());
                    float y = Math.max(getCameraPosY(), obj.getCameraPosY());
                    float w = Math.min(getCameraPosX() + getScaledWidth(), obj.getCameraPosX() + obj.getScaledWidth()) - x;
                    float h = Math.min(getCameraPosY() + getScaledHeight(), obj.getCameraPosY() + obj.getScaledHeight()) - y;

                    return new ZRect("area", x, y, w, h, Color.BLACK);
                }
        }
        return null;
    }

    public boolean contains(ZPos pos) {
        return pos.x >= getCameraPosX() && pos.x <= getCameraPosX() + getScaledWidth()
                && pos.y >= getCameraPosY() && pos.y <= getCameraPosY() + getScaledHeight();
    }

    public boolean contains(ZPosF pos) {
        return pos.x >= getCameraPosX() && pos.x <= getCameraPosX() + getScaledWidth()
                && pos.y >= getCameraPosY() && pos.y <= getCameraPosY() + getScaledHeight();
    }

    public boolean contains(int posX, int posY) {
        return posX >= getCameraPosX() && posX <= getCameraPosX() + getScaledWidth()
                && posY >= getCameraPosY() && posY <= getCameraPosY() + getScaledHeight();
    }

    public boolean contains(float posX, float posY) {
        return posX >= getCameraPosX() && posX <= getCameraPosX() + getScaledWidth()
                && posY >= getCameraPosY() && posY <= getCameraPosY() + getScaledHeight();
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

    public ZObject setUpdate(boolean option) {
        isUpdate = option;
        return this;
    }

    public ZObject setRender(boolean option) {
        isRender = option;
        return this;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public boolean isRender() {
        return isRender;
    }

    public ZObject setDowned(boolean option) {
        isDowned = option;
        return this;
    }

    public boolean isDowned() {
        return isDowned;
    }

    public boolean isCamera() {
        return isCamera;
    }

    public ZObject setCamera(boolean option) {
        this.isCamera = option;
        for (ZObject obj : child) {
            obj.isCamera = option;
        }
        return this;
    }

    public ZObject addChild(ZObject obj) {
        return addChild(obj, child.size());
    }

    public ZObject addChild(ZObject obj, int index) {
        if (index < 0 || index > child.size()) {
            Log.e(type, "(id: " + id + ") / addChild - " + index + "번째 인덱스에 child 객체를 삽입할 수 없습니다");
            return null;
        }
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).getId().equals(obj.getId())) {
                Log.e(type, "(id: " + id + ") / addChild - 이미 " + obj.getId() + "라는 id를 가진 객체가 존재합니다");
                return null;
            }
        }

        obj.addPos(pos.x, pos.y);
        obj.setCamera(isCamera);
        child.add(obj);
        return child.get(child.size() - 1);
    }

    public ZRect addRect(ZRect obj) {
        return addRect(obj, child.size());
    }

    public ZRect addRect(ZRect obj, int index) {
        if (index < 0 || index > child.size()) {
            Log.e(type, "(id: " + id + ") / addRect - " + index + "번째 인덱스에 child 객체를 삽입할 수 없습니다");
            return null;
        }
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).getId().equals(obj.getId())) {
                Log.e(type, "(id: " + id + ") / addRect - 이미 " + obj.getId() + "라는 id를 가진 객체가 존재합니다");
                return null;
            }
        }

        obj.addPos(pos.x, pos.y);
        obj.setCamera(isCamera);
        child.add(obj);
        return obj;
    }

    public ZSprite addSprite(ZSprite obj) {
        return addSprite(obj, child.size());
    }

    public ZSprite addSprite(ZSprite obj, int index) {
        if (index < 0 || index > child.size()) {
            Log.e(type, "(id: " + id + ") / addSprite - " + index + "번째 인덱스에 child 객체를 삽입할 수 없습니다");
            return null;
        }
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).getId().equals(obj.getId())) {
                Log.e(type, "(id: " + id + ") / addSprite - 이미 " + obj.getId() + "라는 id를 가진 객체가 존재합니다");
                return null;
            }
        }

        obj.addPos(pos.x, pos.y);
        obj.setCamera(isCamera);
        child.add(obj);
        return obj;
    }

    public ZText addText(ZText obj) {
        return addText(obj, child.size());
    }

    public ZText addText(ZText obj, int index) {
        if (index < 0 || index > child.size()) {
            Log.e(type, "(id: " + id + ") / addText - " + index + "번째 인덱스에 child 객체를 삽입할 수 없습니다");
            return null;
        }
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).getId().equals(obj.getId())) {
                Log.e(type, "(id: " + id + ") / addText - 이미 " + obj.getId() + "라는 id를 가진 객체가 존재합니다");
                return null;
            }
        }

        obj.addPos(pos.x, pos.y);
        obj.setCamera(isCamera);
        child.add(obj);
        return obj;
    }

    public ZLine addLine(ZLine obj) {
        return addLine(obj, child.size());
    }

    public ZLine addLine(ZLine obj, int index) {
        if (index < 0 || index > child.size()) {
            Log.e(type, "(id: " + id + ") / addLine - " + index + "번째 인덱스에 child 객체를 삽입할 수 없습니다");
            return null;
        }
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).getId().equals(obj.getId())) {
                Log.e(type, "(id: " + id + ") / addLine - 이미 " + obj.getId() + "라는 id를 가진 객체가 존재합니다");
                return null;
            }
        }

        obj.addPos(pos.x, pos.y);
        obj.setCamera(isCamera);
        child.add(obj);
        return obj;
    }

    public ZCircle addCircle(ZCircle obj) {
        return addCircle(obj, child.size());
    }

    public ZCircle addCircle(ZCircle obj, int index) {
        if (index < 0 || index > child.size()) {
            Log.e(type, "(id: " + id + ") / addCircle - " + index + "번째 인덱스에 child 객체를 삽입할 수 없습니다");
            return null;
        }
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).getId().equals(obj.getId())) {
                Log.e(type, "(id: " + id + ") / addCircle - 이미 " + obj.getId() + "라는 id를 가진 객체가 존재합니다");
                return null;
            }
        }

        obj.addPos(pos.x, pos.y);
        obj.setCamera(isCamera);
        child.add(obj);
        return obj;
    }

    public ZAnimation addAnimation(ZAnimation obj) {
        return addAnimation(obj, child.size());
    }

    public ZAnimation addAnimation(ZAnimation obj, int index) {
        if (index < 0 || index > child.size()) {
            Log.e(type, "(id: " + id + ") / addAnimation - " + index + "번째 인덱스에 child 객체를 삽입할 수 없습니다");
            return null;
        }
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).getId().equals(obj.getId())) {
                Log.e(type, "(id: " + id + ") / addAnimation - 이미 " + obj.getId() + "라는 id를 가진 객체가 존재합니다");
                return null;
            }
        }

        obj.addPos(pos.x, pos.y);
        obj.setCamera(isCamera);
        child.add(obj);
        return obj;
    }

    public ArrayList<ZObject> getChildList() {
        return child;
    }

    public ZObject getChild(int index) {
        if (index < child.size()) {
            return child.get(index);
        }
        else {
            Log.e(type, "(id: " + id + ") / getChild(int index) - " + index + "번째 child 가 존재하지 않습니다");
            return null;
        }
    }

    public ZObject findChild(String id) {
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).getId().equals(id)) {
                return child.get(i);
            }
        }
        Log.e(type, "(id: " + id + ") / findChild(String id) - " + id + " 라는 id를 가진 child 가 존재하지 않습니다");
        return null;
    }

    public ZRect findRect(String id) {
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).getId().equals(id)
                    && child.get(i).getType().equals("ZRect")) {
                return (ZRect) child.get(i);
            }
        }
        Log.e(type, "(id: " + id + ") / findRect(String id) - " + id + " 라는 id를 가진 ZRect 타입 child 가 존재하지 않습니다");
        return null;
    }

    public ZSprite findSprite(String id) {
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).getId().equals(id)
                    && child.get(i).getType().equals("ZSprite")) {
                return (ZSprite) child.get(i);
            }
        }
        Log.e(type, "(id: " + id + ") / findSprite(String id) - " + id + " 라는 id를 가진 ZSprite 타입 child 가 존재하지 않습니다");
        return null;
    }

    public ZText findText(String id) {
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).getId().equals(id)
                    && child.get(i).getType().equals("ZText")) {
                return (ZText) child.get(i);
            }
        }
        Log.e(type, "(id: " + id + ") / findLine(String id) - " + id + " 라는 id를 가진 ZLine 타입 child 가 존재하지 않습니다");
        return null;
    }

    public ZLine findLine(String id) {
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).getId().equals(id)
                    && child.get(i).getType().equals("ZLine")) {
                return (ZLine) child.get(i);
            }
        }
        Log.e(type, "(id: " + id + ") / findLine(String id) - " + id + " 라는 id를 가진 ZLine 타입 child 가 존재하지 않습니다");
        return null;
    }

    public ZCircle findCircle(String id) {
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).getId().equals(id)
                    && child.get(i).getType().equals("ZCircle")) {
                return (ZCircle) child.get(i);
            }
        }
        Log.e(type, "(id: " + id + ") / findCircle(String id) - " + id + " 라는 id를 가진 ZCircle 타입 child 가 존재하지 않습니다");
        return null;
    }

    public int getChildIndex(String id) {
        for (int i=0; i<child.size(); i++) {
            if (child.get(i).getId().equals(id)) {
                return i;
            }
        }
        Log.e(type, "(id: " + id + ") / getChildIndex(String id) - " + id + "라는 id를 가진 child 객체가 존재하지 않습니다");
        return -1;
    }

    public int getChildIndex(ZObject object) {
        for (int i=0; i<child.size(); i++) {
            if (child.get(i).getId().equals(object.getId())
                    && child.get(i).getType().equals(object.getType())) {
                return i;
            }
        }
        Log.e(type, "(id: " + id + ") / getChildIndex(ZObject object) - " + object.getId() + "라는 id를 가진 " + object.getType() + "타입 child 객체가 존재하지 않습니다");
        return -1;
    }

    public ZObject setChildIndex(int before, int after) {
        if (before < 0 || before >= child.size()) {
            Log.e(type, "(id: " + id + ") / setChildIndex(int before, int after) - (before)index  " + before + "번째에 child 객체가 존재하지 않습니다");
            return null;
        }
        ZObject obj = child.get(before);
        child.remove(before);

        if (after < 0 || after > child.size()) {
            Log.e(type, "(id: " + id + ") / setChildIndex(int before, int after) - (after)index " + after + "번째에 child 객체를 삽입할 수 없습니다");
            return null;
        }
        child.add(after, obj);
        return obj;
    }

    public ZObject setChildIndexToLast(int before) {
        if (before < 0 || before >= child.size()) {
            Log.e(type, "(id: " + id + ") / setChildIndex(int before) - (before)index  " + before + "번째에 child 객체가 존재하지 않습니다");
            return null;
        }
        ZObject obj = child.get(before);
        child.remove(before);
        child.add(obj);
        return obj;
    }

    public int getChildSIze() {
        return child.size();
    }

    public ZObject removeChild(int index) {
        if (index < 0 || index >= child.size()) {
            Log.e(type, "(id: " + id + ") / removeChild(int index) - " + index + "번째 인덱스에 child 객체가 존재하지 않습니다");
            return null;
        }

        child.remove(index);
        return this;
    }

    public ZObject removeChild(String id) {
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).getId().equals(id)) {
                child.remove(i);
                return this;
            }
        }

        return null;
    }

    public ZObject removeChild(ZObject object) {
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).getId().equals(object.getId())) {
                child.remove(i);
                return this;
            }
        }

        Log.e(type, "(id: " + id + ") / removeChild(ZObject object) - " + id + " 라는 id를 가진 " + object.getType() + "타입의 child 가 존재하지 않습니다");
        return null;
    }

    public ZObject clearChild() {
        child.clear();
        System.gc();

        return this;
    }

    public ZObject setScalingCenter(ZPosF pos) {
        scalingCenter = pos;
        return this;
    }

    public ZObject setScalingCenter(float x, float y) {
        scalingCenter.setPos(x, y);
        return this;
    }

    public ZObject setScalingCenterX(float x) {
        scalingCenter.x = x;
        return this;
    }

    public ZObject setScalingCenterY(float y) {
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

    public ZObject setTouchAble(boolean option) {
        touchAble = option;
        return this;
    }

    public boolean getTouchAble() {
        return touchAble;
    }

    public Paint getPaint() {
        return paint;
    }
}
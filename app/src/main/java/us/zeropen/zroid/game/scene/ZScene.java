package us.zeropen.zroid.game.scene;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;

import us.zeropen.zroid.audio.ZAudio;
import us.zeropen.zroid.device.ZToast;
import us.zeropen.zroid.game.ZDefine;
import us.zeropen.zroid.game.ZGameActivity;
import us.zeropen.zroid.game.camera.ZCameraMgr;
import us.zeropen.zroid.graphic.ZAnimation;
import us.zeropen.zroid.graphic.ZCircle;
import us.zeropen.zroid.graphic.ZLine;
import us.zeropen.zroid.graphic.ZObject;
import us.zeropen.zroid.graphic.ZPos;
import us.zeropen.zroid.graphic.ZGraphic;
import us.zeropen.zroid.graphic.ZPosF;
import us.zeropen.zroid.graphic.ZRect;
import us.zeropen.zroid.graphic.ZSprite;
import us.zeropen.zroid.graphic.ZText;
import us.zeropen.zroid.input.TouchEvent;
import us.zeropen.zroid.input.TouchType;
import us.zeropen.zroid.input.ZInputMgr;

/**
 * Created by 병걸 on 2015-03-16.
 * <p/>
 * ZScene 은 게임 내에서 이미지의 출력, 이벤트 처리 등 게임 진행에 필수적인 요소인
 * Scene 클래스입니다.
 * <p/>
 * update 함수에서는 매 프레임 업데이트(변수 값 등의 변경) 할 내용들을,
 * render 함수에서는 매 프레임 ZObject 자손 클래스 객체들을 출력해줍니다
 * <p/>
 * update, render 함수 둘 다 오버라이드 하여 사용할 수 있지만,
 * 두 함수 모두 오버라이드 했을 경우 함수가 끝나기 전에
 * <p/>
 * super.update(eTime);
 * super.render(canvas);
 * <p/>
 * 와 같은 방법으로 상위 클래스의 update, render 함수를 실행해 주시기 바랍니다
 * <p/>
 * update 함수의 eTime 은 정수형 값으로, 한 프레임을 도는데 걸린 시간을 ms 단위로 반환합니다
 */

public class ZScene {
    protected final ZGameActivity game;
    protected ArrayList<ZObject> child;
    protected ArrayList<TouchEvent> touchEvents;
    protected final int TOUCH_DOWN = TouchType.TOUCH_DOWN;
    protected final int TOUCH_MOVE = TouchType.TOUCH_MOVE;
    protected final int TOUCH_UP = TouchType.TOUCH_UP;
    protected final int TOUCH_RELEASE = TouchType.TOUCH_RELEASE;
    private ZRect fade;
    private int isFade;                 // fadeIn : 1, fadeOut : 2
    private boolean fadeFinished;
    private int fadeSpeed;
    protected boolean fadeAutoRender;

    public ZScene(ZGameActivity game) {
        this.game = game;
        isFade = 0;
        fadeFinished = false;
        fade = new ZRect("fade", 0, 0, ZDefine.GAME_WIDTH, ZDefine.GAME_HEIGHT).setAlpha(0).setCamera(false);
        child = new ArrayList<>();
        ZCameraMgr.setBound(0, 0, ZDefine.GAME_WIDTH, ZDefine.GAME_HEIGHT);
        ZCameraMgr.setTarget(null);
        fadeAutoRender = true;
    }

    public void update(float eTime) {
        eventCheck(eTime);
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).isUpdate()) {
                child.get(i).update(eTime);
            }
        }

        switch (isFade) {
            case 1:
                if (fade.getAlpha() <= 0) {
                    isFade = 0;
                    fadeFinished = true;
                }
                else {
                    fade.addAlpha(-fadeSpeed * eTime / 1000);
                }
                break;

            case 2:
                if (fade.getAlpha() >= 255) {
                    isFade = 0;
                    fadeFinished = true;
                }
                else {
                    fade.addAlpha(fadeSpeed * eTime / 1000);
                }
        }
    }

    public void render() {
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).isRender()) {
                child.get(i).render();
            }
        }

        if (fadeAutoRender)
            fade.render();
    }

    public void eventCheck(float eTime) {
        touchEvents = ZInputMgr.getTouchEvents();


        for (int i = 0; i < touchEvents.size(); i++) {
            TouchEvent event = touchEvents.get(i);

            for (int j = child.size() - 1; j >= 0; j--) {
                ZObject object = child.get(j);

                if (object.getTouchAble() && !object.getType().equals("ZLine") && !object.getType().equals("ZText")) {

                    for (int k = object.getChildList().size() - 1; k >= 0; k--) {
                        ZObject childObj = object.getChild(k);

                        try {
                            if (childObj.getTouchAble() && !childObj.getType().equals("ZLine") && !childObj.getType().equals("ZText")) {
                                switch (event.type) {
                                    case TOUCH_DOWN:
                                        if (!childObj.isDowned() && childObj.isTouched(event)) {
                                            childObj.setDowned(true);
                                            childObj.setDownPos(event.pos);
                                            childObj.setTouchPointer(event.pointer);
                                            executeEvent(TOUCH_DOWN, event, childObj.getId(), childObj, event.pointer, eTime);
                                        }
                                        break;

                                    case TOUCH_UP:
                                        if (childObj.isDowned() && childObj.getTouchPointer() == event.pointer) {
                                            childObj.setDowned(false);
                                            childObj.setDownPos(-1, -1);
                                            childObj.setTouchPointer(-1);

                                            if (childObj.isTouched(event)) {
                                                executeEvent(TOUCH_RELEASE, event, childObj.getId(), childObj, event.pointer, eTime);
                                            } else {
                                                executeEvent(TOUCH_UP, event, childObj.getId(), childObj, event.pointer, eTime);
                                            }
                                        } else if (childObj.isTouched(event)) {
                                            if (childObj.isDowned()) {
                                                childObj.setDowned(false);
                                                childObj.setDownPos(-1, -1);
                                                childObj.setTouchPointer(-1);
                                            }
                                            executeEvent(TOUCH_UP, event, childObj.getId(), childObj, event.pointer, eTime);
                                        }
                                        break;

                                    case TOUCH_MOVE:
                                        if (childObj.isDowned() && childObj.getTouchPointer() == event.pointer
                                                && !childObj.isTouched(event)) {
                                            childObj.setDowned(false);
                                            childObj.setDownPos(-1, -1);
                                            childObj.setTouchPointer(-1);
                                        } else {
                                            executeEvent(TOUCH_MOVE, event, childObj.getId(), childObj, event.pointer, eTime);
                                        }
                                }
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        switch (event.type) {
                            case TOUCH_DOWN:
                                if (!object.isDowned() && object.isTouched(event)) {
                                    object.setDowned(true);
                                    object.setDownPos(event.pos);
                                    object.setTouchPointer(event.pointer);
                                    executeEvent(TOUCH_DOWN, event, object.getId(), object, event.pointer, eTime);
                                }
                                break;

                            case TOUCH_UP:
                                if (object.isDowned() && object.getTouchPointer() == event.pointer) {
                                    object.setDowned(false);
                                    object.setDownPos(-1, -1);
                                    object.setTouchPointer(-1);

                                    if (object.isTouched(event)) {
                                        executeEvent(TOUCH_RELEASE, event, object.getId(), object, event.pointer, eTime);
                                    } else {
                                        executeEvent(TOUCH_UP, event, object.getId(), object, event.pointer, eTime);
                                    }
                                } else if (object.isTouched(event)) {
                                    if (object.isDowned()) {
                                        object.setDowned(false);
                                        object.setDownPos(-1, -1);
                                        object.setTouchPointer(-1);
                                    }
                                    executeEvent(TOUCH_UP, event, object.getId(), object, event.pointer, eTime);
                                }
                                break;

                            case TOUCH_MOVE:
                                if (object.isDowned() && object.getTouchPointer() == event.pointer
                                        && !object.isTouched(event)) {
                                    object.setDowned(false);
                                    object.setDownPos(-1, -1);
                                    object.setTouchPointer(-1);
                                } else {
                                    executeEvent(TOUCH_MOVE, event, object.getId(), object, event.pointer, eTime);
                                }
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                executeEvent(event.type, event, "SCREEN", null, event.pointer, eTime);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void executeEvent(int touchType, TouchEvent event, String id, ZObject object, int pointer, float eTime) {

    }

    public void onSwipe(int swipeType, MotionEvent start, MotionEvent end, ZPosF velocity) {

    }

    public void onDoubleTap(MotionEvent e) {

    }

    public void onSingleTap(MotionEvent e) {

    }

    public ZObject addChild(ZObject obj) {
        return addChild(obj, child.size());
    }

    public ZObject addChild(ZObject obj, int index) {
        if (index < 0 || index > child.size()) {
            Log.e("ZScene", "addChild - " + index + "번째 인덱스에 child 객체를 삽입할 수 없습니다");
            return null;
        }
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).getId().equals(obj.getId())) {
                Log.e("ZScene", "addChild - 이미 " + obj.getId() + "라는 id를 가진 객체가 존재합니다");
                return null;
            }
        }

        child.add(obj);
        return child.get(child.size() - 1);
    }

    public ZRect addRect(ZRect obj) {
        return addRect(obj, child.size());
    }

    public ZRect addRect(ZRect obj, int index) {
        if (index < 0 || index > child.size()) {
            Log.e("ZScene", "addRect - " + index + "번째 인덱스에 child 객체를 삽입할 수 없습니다");
            return null;
        }
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).getId().equals(obj.getId())) {
                Log.e("ZScene", "addRect - 이미 " + obj.getId() + "라는 id를 가진 객체가 존재합니다");
                return null;
            }
        }

        child.add(obj);
        return obj;
    }

    public ZSprite addSprite(ZSprite obj) {
        return addSprite(obj, child.size());
    }

    public ZSprite addSprite(ZSprite obj, int index) {
        if (index < 0 || index > child.size()) {
            Log.e("ZScene", "addSprite - " + index + "번째 인덱스에 child 객체를 삽입할 수 없습니다");
        }
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).getId().equals(obj.getId())) {
                Log.e("ZScene", "addSprite - 이미 " + obj.getId() + "라는 id를 가진 객체가 존재합니다");
            }
        }

        child.add(obj);
        return obj;
    }

    public ZText addText(ZText obj) {
        return addText(obj, child.size());
    }

    public ZText addText(ZText obj, int index) {
        if (index < 0 || index > child.size()) {
            Log.e("ZScene", "addText - " + index + "번째 인덱스에 child 객체를 삽입할 수 없습니다");
        }
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).getId().equals(obj.getId())) {
                Log.e("ZScene", "addText - 이미 " + obj.getId() + "라는 id를 가진 객체가 존재합니다");
            }
        }

        child.add(obj);
        return obj;
    }

    public ZLine addLine(ZLine obj) {
        return addLine(obj, child.size());
    }

    public ZLine addLine(ZLine obj, int index) {
        if (index < 0 || index > child.size()) {
            Log.e("ZScene", "addLine - " + index + "번째 인덱스에 child 객체를 삽입할 수 없습니다");
        }
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).getId().equals(obj.getId())) {
                Log.e("ZScene", "addLine - 이미 " + obj.getId() + "라는 id를 가진 객체가 존재합니다");
            }
        }

        child.add(obj);
        return obj;
    }

    public ZCircle addCircle(ZCircle obj) {
        return addCircle(obj, child.size());
    }

    public ZCircle addCircle(ZCircle obj, int index) {
        if (index < 0 || index > child.size()) {
            Log.e("ZScene", "addCircle - " + index + "번째 인덱스에 child 객체를 삽입할 수 없습니다");
        }
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).getId().equals(obj.getId())) {
                Log.e("ZScene", "addCircle - 이미 " + obj.getId() + "라는 id를 가진 객체가 존재합니다");
            }
        }

        child.add(obj);
        return obj;
    }

    public ZAnimation addAnimation(ZAnimation obj) {
        return addAnimation(obj, child.size());
    }

    public ZAnimation addAnimation(ZAnimation obj, int index) {
        if (index < 0 || index > child.size()) {
            Log.e("ZScene", "addAnimation - " + index + "번째 인덱스에 child 객체를 삽입할 수 없습니다");
        }
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).getId().equals(obj.getId())) {
                Log.e("ZScene", "addAnimation - 이미 " + obj.getId() + "라는 id를 가진 객체가 존재합니다");
            }
        }

        child.add(obj);
        return obj;

    }

    public ZObject findChild(String id) {
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).getId().equals(id)) {
                return child.get(i);
            }
        }
        Log.e("ZScene", "findChild - " + id + " 라는 id를 가진 child 객체가 존재하지 않습니다");
        return null;
    }

    public ArrayList<ZObject> getChildList() {
        return child;
    }

    public ZObject getChild(int index) {
        if (index < child.size()) {
            return child.get(index);
        }
        else {
            Log.e("ZScene", "getChild - " + index + "번째 인덱스에 child 객체가 존재하지 않습니다");
            return null;
        }
    }

    public ZRect findRect(String id) {
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).getId().equals(id)
                    && child.get(i).getType().equals("ZRect")) {
                return (ZRect) child.get(i);
            }
        }
        Log.e("ZScene", "findRect - " + id + " 라는 id를 가진 ZRect 타입 child 객체가 존재하지 않습니다");
        return null;
    }

    public ZSprite findSprite(String id) {
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).getId().equals(id)
                    && child.get(i).getType().equals("ZSprite")) {
                return (ZSprite) child.get(i);
            }
        }
        Log.e("ZScene", "findSprite - " + id + " 라는 id를 가진 ZSprite 타입 child 객체가 존재하지 않습니다");
        return null;
    }

    public ZText findText(String id) {
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).getId().equals(id)
                    && child.get(i).getType().equals("ZText")) {
                return (ZText) child.get(i);
            }
        }
        Log.e("ZScene", "findText - " + id + " 라는 id를 가진 ZText 타입 child 객체가 존재하지 않습니다");
        return null;
    }

    public ZLine findLine(String id) {
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).getId().equals(id)
                    && child.get(i).getType().equals("ZLine")) {
                return (ZLine) child.get(i);
            }
        }
        Log.e("ZScene", "findLine - " + id + " 라는 id를 가진 ZLine 타입 child 객체가 존재하지 않습니다");
        return null;
    }

    public ZCircle findCircle(String id) {
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).getId().equals(id)
                    && child.get(i).getType().equals("ZCircle")) {
                return (ZCircle) child.get(i);
            }
        }
        Log.e("ZScene", "findCircle - " + id + " 라는 id를 가진 ZCircle 타입 child 객체가 존재하지 않습니다");
        return null;
    }

    public ZAnimation findAnimation(String id) {
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).getId().equals(id)
                    && child.get(i).getType().equals("ZAnimation")) {
                return (ZAnimation) child.get(i);
            }
        }
        Log.e("ZScene", "findAnimation - " + id + " 라는 id를 가진 ZAnimation 타입 child 객체가 존재하지 않습니다");
        return null;
    }

    public int getChildIndex(String id) {
        for (int i=0; i<child.size(); i++) {
            if (child.get(i).getId().equals(id)) {
                return i;
            }
        }
        Log.e("ZScene", "getChildIndex - " + id + "라는 id를 가진 child 객체가 존재하지 않습니다");
        return -1;
    }

    public int getChildIndex(ZObject object) {
        for (int i=0; i<child.size(); i++) {
            if (child.get(i).getId().equals(object.getId())
                    && child.get(i).getType().equals(object.getType())) {
                return i;
            }
        }
        Log.e("ZScene", "getChildIndex - " + object.getId() + "라는 id를 가진 " + object.getType() + "타입 child 객체가 존재하지 않습니다");
        return -1;
    }

    public ZObject setChildIndex(int before, int after) {
        if (before < 0 || before >= child.size()) {
            Log.e("ZScene", "setChildIndex - (before) index  " + before + "번째에 child 객체가 존재하지 않습니다");
            return null;
        }
        ZObject obj = child.get(before);
        child.remove(before);

        if (after < 0 || after > child.size()) {
            Log.e("ZScene", "setChildIndex - (after) index " + after + "번째에 child 객체를 삽입할 수 없습니다");
            return null;
        }
        child.add(after, obj);
        return obj;
    }

    public ZObject setChildIndexToLast(int before) {
        if (before < 0 || before >= child.size()) {
            Log.e("ZScene", "setChildIndex - (before) index  " + before + "번째에 child 객체가 존재하지 않습니다");
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

    public void removeChild(int index) {
        if (index < 0 || index >= child.size()) {
            Log.e("ZScene", "removeChild(int index) - " + index + "번째 인덱스에 child 객체가 존재하지 않습니다");
            return;
        }

        child.remove(index);
    }

    public void removeChild(String id) {
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).getId().equals(id)) {
                child.remove(i);
                return;
            }
        }
        Log.e("ZScene", "removeChild(String id) - " + id + " 라는 id를 가진 child 객체가 존재하지 않습니다");
    }

    public void removeChild(ZObject object) {
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).getId().equals(object.getId())
                    && child.get(i).getType().equals(object.getType())) {
                child.remove(i);
                return;
            }
        }
        Log.e("ZScene", "removeChild(ZObject object) - " + object.getId() + " 라는 id를 가진 " + object.getType() + "타입 child 객체가 존재하지 않습니다");
    }

    public void clearChild() {
        child.clear();
    }

    public void addTexture(String id, String fileName, ZGraphic.ImageFormat imageFormat) {
        ZGraphic.addTexture(id, fileName, imageFormat);
    }

    public void addTexture(String id, String fileName) {
        ZGraphic.addTexture(id, fileName);
    }

    public void removeTexture(String id) {
        ZGraphic.removeTexture(id);
    }

    public void clearTexture() {
        ZGraphic.clearTexture();
    }

    public void addFont(String id, String fileName) {
        ZGraphic.addFont(id, fileName);
    }

    public void removeFont(String id) {
        ZGraphic.removeFont(id);
    }

    public void clearFont() {
        ZGraphic.clearFont();
    }

    public void addSound(String id, String fileName) {
        ZAudio.addSound(id, fileName);
    }

    public void addMusic(String id, String fileName) {
        ZAudio.addMusic(id, fileName);
    }

    public void removeSound(String id) {
        ZAudio.removeSound(id);
    }

    public void removeMusic(String id) {
        ZAudio.removeMusic(id);
    }

    public void playSound(String id) {
        ZAudio.playSound(id);
    }

    public void playSound(String id, float volume) {
        ZAudio.playSound(id, volume);
    }

    public void playMusic(String id) {
        ZAudio.playMusic(id);
    }

    public void playMusic(String id, boolean isLooping) {
        ZAudio.playMusic(id, isLooping);
    }

    public void playMusic(String id, float volume) {
        ZAudio.playMusic(id, volume);
    }

    public void playMusic(String id, float volume, boolean isLooping) {
        ZAudio.playMusic(id, volume, isLooping);
    }
    public void setVolume(String id, float volume){
        ZAudio.setVolume(id,volume);
    }

    public void stopAllMusic() {
        ZAudio.stopAllMusic();
    }

    public void stopMusic(String id) {
        ZAudio.stopMusic(id);
    }

    public boolean onBackPressed() {
        return true;
    }

    public void setScene(ZScene scene) {
        ZSceneMgr.setScene(scene);
    }

    public void finish() {
        game.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                game.finish();
            }
        });
    }

    public void fadeIn() {
        if (isFade == 0) {
            fade.setAlpha(255);
            isFade = 1;
            fadeSpeed = 5;
            fadeFinished = false;
        }
        else {
            Log.e("ZScene", "fadeIn - 이미 fade 가 진행중입니다");
        }
    }

    public void fadeIn(int fadeSpeed) {
        if (isFade == 0) {
            fade.setAlpha(255);
            isFade = 1;
            this.fadeSpeed = fadeSpeed;
            fadeFinished = false;
        }
        else {
            Log.e("ZScene", "fadeIn - 이미 fade 가 진행중입니다");
        }
    }

    public void fadeOut() {
        if (isFade == 0) {
            fade.setAlpha(0);
            isFade = 2;
            this.fadeSpeed = 5;
            fadeFinished = false;
        }
        else {
            Log.e("ZScene", "fadeOut - 이미 fade 가 진행중입니다");
        }
    }

    public void fadeOut(int fadeSpeed) {
        if (isFade == 0) {
            fade.setAlpha(0);
            isFade = 2;
            this.fadeSpeed = fadeSpeed;
            fadeFinished = false;
        }
        else {
            Log.e("ZScene", "fadeOut - 이미 fade 가 진행중입니다");
        }
    }

    public boolean isFadeFinished() {
        return fadeFinished;
    }

    public void disableFade() {
        isFade = 0;
        fadeFinished = true;
    }

    public void setFadeFinished(boolean option) {
        fadeFinished = option;
    }

    public void setFadeColor(int color) {
        fade.setColor(color);
    }

    public void setFadeAlpha(float alpha) {
        fade.setAlpha(alpha);
    }

    public ZRect getFade() {
        return fade;
    }

    public void onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

    }

    public void onStart() {

    }

    public void onPause() {

    }

    public void onStop() {

    }

    public void onResume() {

    }

    public String[] removeLoadedResources() {
        return new String[0];
    }
}
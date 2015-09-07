package us.zeropen.zroid.input;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import java.util.ArrayList;
import java.util.HashMap;

import us.zeropen.zroid.device.ZToast;
import us.zeropen.zroid.game.ZDefine;
import us.zeropen.zroid.game.camera.ZCameraMgr;
import us.zeropen.zroid.game.scene.ZSceneMgr;
import us.zeropen.zroid.graphic.ZPos;
import us.zeropen.zroid.graphic.ZPosF;

/**
 * Created by 병걸 on 2015-03-21.
 */

public class ZInputMgr implements OnTouchListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    private final int MAX_TOUCH_POINTS;
    private static ArrayList<TouchEvent> touchEvents;
    private static ArrayList<TouchEvent> touchEventsBuffer;
    private boolean[] isTouched;
    private int[] pointerId;
    private ZPosF[] touchPos;
    private ZPosF[] startTouchPos;
    private ZPosF[] startCameraTouchPos;

//    public static boolean isBubbling;

    public static int SWIPE_MIN_DISTANCE = 120;
    public static int SWIPE_MAX_OFF_PATH = 450;
    public static int SWIPE_THRESHOLD_VELOCITY = 200;

    public final int SWIPE_LEFT_TO_RIGHT = 0;
    public final int SWIPE_RIGHT_TO_LEFT = 1;
    public final int SWIPE_UP_TO_DOWN = 2;
    public final int SWIPE_DOWN_TO_UP = 3;

    private GestureDetector gestureDetector;

    /**
     * OnTouchListener
     */

    private static Pool<TouchEvent> pool;

    public ZInputMgr(View view) {
        this(view, 10);
    }

    public ZInputMgr(View view, int MAX_TOUCH_POINTS) {
        view.setOnTouchListener(this);

        this.MAX_TOUCH_POINTS = MAX_TOUCH_POINTS;
        isTouched = new boolean[MAX_TOUCH_POINTS];
        touchPos = new ZPosF[MAX_TOUCH_POINTS];
        pointerId = new int[MAX_TOUCH_POINTS];
        touchEvents = new ArrayList<>();
        touchEventsBuffer = new ArrayList<>();
        startTouchPos = new ZPosF[MAX_TOUCH_POINTS];
        startCameraTouchPos = new ZPosF[MAX_TOUCH_POINTS];
//        isBubbling = false;

        Pool.PoolObjectFactory<TouchEvent> factory = new Pool.PoolObjectFactory<TouchEvent>() {
            @Override
            public TouchEvent createObject() {
                return new TouchEvent();
            }
        };
        pool = new Pool<>(factory, 100);

        /**
         * Swipe
         */

        gestureDetector = new GestureDetector(ZSceneMgr.game, this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        int pointerCount = event.getPointerCount();
        int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK)
                >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;

        if (pointerIndex >= 7) {
            ZToast.show("ZROID made by 하병걸, ZER0PEN 11th president");
        }

        float sx = ZDefine.GAME_SCALE_X;
        float sy = ZDefine.GAME_SCALE_Y;

        for (int i=0; i<MAX_TOUCH_POINTS; i++) {
            TouchEvent touchEvent;
           try {
               if (i >= pointerCount) {
                   isTouched[i] = false;
                   pointerId[i] = -1;
                   continue;
               }
               if (event.getAction() != MotionEvent.ACTION_MOVE && i != pointerIndex) {
                   continue;
               }
               int pId = event.getPointerId(i);

               switch (action) {
                   case MotionEvent.ACTION_DOWN:
                   case MotionEvent.ACTION_POINTER_DOWN:
                       touchEvent = pool.newObject();
                       touchEvent.type = TouchType.TOUCH_DOWN;
                       touchEvent.pointer = pId;
                       touchEvent.pos = new ZPosF(touchPos[i] = new ZPosF(event.getX(i) * sx, event.getY(i) * sy));
                       touchEvent.cameraPos = new ZPosF(event.getX(i) * sx + ZCameraMgr.getPosX() , event.getY(i) * sy + ZCameraMgr.getPosY());
                       touchEvent.startPos = new ZPosF(touchEvent.pos);
                       touchEvent.startCameraPos = new ZPosF(touchEvent.cameraPos);
                       isTouched[i] = true;
                       pointerId[i] = pId;
                       touchEventsBuffer.add(touchEvent);
                       startTouchPos[pId] = new ZPosF(event.getX(i) * sx, event.getY(i) * sy);
                       startCameraTouchPos[pId] = new ZPosF(event.getX(i), event.getY(i));

                       break;

                   case MotionEvent.ACTION_MOVE:
                       touchEvent = pool.newObject();
                       touchEvent.type = TouchType.TOUCH_MOVE;
                       touchEvent.pointer = pId;
                       touchEvent.pos = new ZPosF(touchPos[i] = new ZPosF(event.getX(i) * sx, event.getY(i) * sy));
                       touchEvent.cameraPos = new ZPosF(event.getX(i) * sx + ZCameraMgr.getPosX() , event.getY(i) * sy + ZCameraMgr.getPosY());
                       touchEvent.startPos = new ZPosF(startTouchPos[pId]);
                       touchEvent.startCameraPos = new ZPosF(startCameraTouchPos[pId]);
                       isTouched[i] = true;
                       pointerId[i] = pId;
                       touchEventsBuffer.add(touchEvent);

                       break;

                   case MotionEvent.ACTION_UP:
                   case MotionEvent.ACTION_POINTER_UP:
                       touchEvent = pool.newObject();
                       touchEvent.type = TouchType.TOUCH_UP;
                       touchEvent.pointer = pId;
                       touchEvent.pos = new ZPosF(touchPos[i] = new ZPosF(event.getX(i) * sx, event.getY(i) * sy));
                       touchEvent.cameraPos = new ZPosF(event.getX(i) * sx + ZCameraMgr.getPosX() , event.getY(i) * sy + ZCameraMgr.getPosY());
                       touchEvent.startPos = new ZPosF(startTouchPos[pId]);
                       touchEvent.startCameraPos = new ZPosF(startCameraTouchPos[pId]);
                       isTouched[i] = false;
                       pointerId[i] = -1;
                       touchEventsBuffer.add(touchEvent);

                       break;
               }
           }
           catch (Exception e) {
               e.printStackTrace();
           }
        }
        return gestureDetector.onTouchEvent(event);
    }

    public static ArrayList<TouchEvent> getTouchEvents() {
        for (int i=0; i<touchEvents.size(); i++) {
            pool.free(touchEvents.get(i));
        }

        touchEvents.clear();
        touchEvents.addAll(touchEventsBuffer);
        touchEventsBuffer.clear();

        return touchEvents;
    }

    /**
     * OnGestureListener
     */

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        ZSceneMgr.onScroll(e1, e2, distanceX, distanceY);
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                return false;

            // right to left swipe
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                ZSceneMgr.onSwipe(SWIPE_RIGHT_TO_LEFT, e1, e2, new ZPosF(velocityX, velocityY));
            }
            // left to right swipe
            else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                ZSceneMgr.onSwipe(SWIPE_LEFT_TO_RIGHT, e1, e2, new ZPosF(velocityX, velocityY));
            }
            // down to up swipe
            else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                ZSceneMgr.onSwipe(SWIPE_DOWN_TO_UP, e1, e2, new ZPosF(velocityX, velocityY));
            }
            // up to down swipe
            else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                ZSceneMgr.onSwipe(SWIPE_UP_TO_DOWN, e1, e2, new ZPosF(velocityX, velocityY));
            }
        } catch (Exception e) {

        }

        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        ZSceneMgr.onSingleTap(e);
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        ZSceneMgr.onDoubleTap(e);
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return true;
    }
}
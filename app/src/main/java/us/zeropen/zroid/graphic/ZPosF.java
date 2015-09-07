package us.zeropen.zroid.graphic;

import android.util.Log;

/**
 * Created by 병걸 on 2015-03-16.
 *
 * ZPosF 는 x, y 실수형 좌표를 다루기 쉽게 도와주는 클래스입니다
 */
public class ZPosF {
    public float x;
    public float y;

    public ZPosF() {
        x = 0;
        y = 0;
    }

    public ZPosF(ZPosF pos) {
        x = pos.x;
        y = pos.y;
    }

    public ZPosF(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void addPos(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void addPos(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setPos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setPos(ZPos pos) {
        x = pos.x;
        y = pos.y;
    }

    public void setPos(ZPosF pos) {
        x = pos.x;
        y = pos.y;
    }

    public void logPos() {
        Log.i("Pos", "(" + x + ", " + y + ")");
    }

    public void logPos(char option) {
        switch (option) {
            case 'v':
                Log.v("Pos", "(" + x +", " + y + ")");
                break;

            case 'd':
                Log.d("Pos", "(" + x +", " + y + ")");
                break;

            case 'i':
                Log.i("Pos", "(" + x +", " + y + ")");
                break;

            case 'w':
                Log.w("Pos", "(" + x +", " + y + ")");
                break;

            case 'e':
                Log.e("Pos", "(" + x +", " + y + ")");
        }
    }
}

package us.zeropen.zroid.graphic;

import android.util.Log;

/**
 * Created by 병걸 on 2015-03-16.
 *
 * ZPos 는 x, y 정수형 좌표를 다루기 쉽게 도와주는 클래스입니다
 */
public class ZPos {
    public int x;
    public int y;

    public ZPos() {
        x = 0;
        y = 0;
    }

    public ZPos(ZPos pos) {
        x = pos.x;
        y = pos.y;
    }

    public ZPos(ZPosF pos) {
        x = (int)pos.x;
        y = (int)pos.y;
    }

    public ZPos(int x, int y) {
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
        this.x = (int)x;
        this.y = (int)y;
    }

    public void setPos(ZPos pos) {
        x = pos.x;
        y = pos.y;
    }

    public void setPos(ZPosF pos) {
        x = (int)pos.x;
        y = (int)pos.y;
    }

    public void logPos() {
        Log.i("Pos", "(" + x +", " + y + ")");
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

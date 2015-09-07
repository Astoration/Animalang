package us.zeropen.zroid.util;

import us.zeropen.zroid.graphic.ZPos;
import us.zeropen.zroid.graphic.ZPosF;
import us.zeropen.zroid.graphic.ZRect;

/**
 * Created by 병걸 on 2015-06-02.
 */
public class ZMath {
    public static float getDistance(float p1, float p2) {
        return Math.abs(p1) > Math.abs(p2) ? Math.abs(p1) - Math.abs(p2) : Math.abs(p2) - Math.abs(p1);
    }

    public static float getDistance(ZPosF pos1, ZPosF pos2) {
        return (float)Math.sqrt(Math.pow(pos2.x - pos1.x, 2) + Math.pow(pos2.y - pos1.y, 2));
    }

    public static float getDistance(ZPos pos1, ZPos pos2) {
        return (float)Math.sqrt(Math.pow(pos2.x - pos1.x, 2) + Math.pow(pos2.y - pos1.y, 2));
    }

    public static float getDistance(float pos1X, float pos1Y, float pos2X, float pos2Y) {
        return (float)Math.sqrt(Math.pow(pos2X - pos1X, 2) + Math.pow(pos2Y - pos1Y, 2));
    }

    public static float getDistArea(ZPosF pos1, ZPosF pos2) {
        return getDistance(pos1.x, pos2.x) * getDistance(pos1.y, pos2.y);
    }

    public static float getDistArea(ZPos pos1, ZPos pos2) {
        return getDistance(pos1.x, pos2.x) * getDistance(pos1.y, pos2.y);
    }

    public static float getDistArea(float pos1X, float pos1Y, float pos2X, float pos2Y) {
        return getDistance(pos1X, pos2X) * getDistance(pos1Y, pos2Y);
    }

    public static ZRect getDistRect(ZPosF pos1, ZPosF pos2) {
        return new ZRect("distRect", pos1, pos2);
    }

    public static ZRect getDistRect(ZPos pos1, ZPos pos2) {
        return new ZRect("distRect", new ZPosF(pos1.x, pos1.y), new ZPosF(pos2.x, pos2.y));
    }

    public static ZRect getDistRect(float pos1X, float pos1Y, float pos2X, float pos2Y) {
        return new ZRect("distRect", new ZPosF(pos1X, pos1Y), new ZPosF(pos2X, pos2Y));
    }

    public static float getDistSquared(ZPosF pos1, ZPosF pos2) {
        return (float)(Math.pow(pos2.x - pos1.x, 2) + Math.pow(pos2.y - pos1.y, 2));
    }

    public static float getDistSquared(ZPos pos1, ZPos pos2) {
        return (float)(Math.pow(pos2.x - pos1.x, 2) + Math.pow(pos2.y - pos1.y, 2));
    }

    public static float getDistSquared(float pos1X, float pos1Y, float pos2X, float pos2Y) {
        return (float)(Math.pow(pos2X - pos1X, 2) + Math.pow(pos2Y - pos1Y, 2));
    }

    public static float degreeToRadian(float degree) {
        float angle;

        if (degree > 180) {
            angle = 360 - degree;
        }
        else {
            angle = -degree;
        }

        return (float)(angle * Math.PI / -180);
    }

    public static float radianToDegree(float radian) {
        float angle = (float)(-180 / Math.PI * radian);

        if (angle < 0) {
            return Math.abs(angle);
        }
        else if (angle == 0) {
            return 0;
        }
        else {
            return 360 - angle;
        }
    }

    public static float getDegree(ZPosF pos1, ZPosF pos2) {
        float dx = pos2.x - pos1.x;
        float dy = pos2.y - pos1.y;
        double radian = Math.atan2(dy , dx);
        float angle = (float)(-180 / Math.PI * radian);

        if (angle < 0) {
            return Math.abs(angle);
        }
        else if (angle == 0) {
            return 0;
        }
        else {
            return 360 - angle;
        }
    }

    public static float getDegree(ZPos pos1, ZPos pos2) {
        float dx = pos2.x - pos1.x;
        float dy = pos2.y - pos1.y;
        double radian = Math.atan2(dy , dx);
        float angle = (float)(-180 / Math.PI * radian);

        if (angle < 0) {
            return Math.abs(angle);
        }
        else if (angle == 0) {
            return 0;
        }
        else {
            return 360 - angle;
        }
    }

    public static float getDegree(int pos1X, int pos1Y, int pos2X, int pos2Y) {
        float dx = pos2X - pos1X;
        float dy = pos2Y - pos1Y;
        double radian = Math.atan2(dy , dx);
        float angle = (float)(-180 / Math.PI * radian);

        if (angle < 0) {
            return Math.abs(angle);
        }
        else if (angle == 0) {
            return 0;
        }
        else {
            return 360 - angle;
        }
    }

    public static float getDegree(float pos1X, float pos1Y, float pos2X, float pos2Y) {
        float dx = pos2X - pos1X;
        float dy = pos2Y - pos1Y;
        double radian = Math.atan2(dy , dx);
        float angle = (float)(-180 / Math.PI * radian);

        if (angle < 0) {
            return Math.abs(angle);
        }
        else if (angle == 0) {
            return 0;
        }
        else {
            return 360 - angle;
        }
    }

    public static float getRadian(ZPos pos1, ZPos pos2) {
        float dx = pos2.x - pos1.x;
        float dy = pos2.y - pos1.y;

        return (float)Math.atan2(dy , dx);
    }

    public static float getRadian(ZPosF pos1, ZPosF pos2) {
        float dx = pos2.x - pos1.x;
        float dy = pos2.y - pos1.y;

        return (float)Math.atan2(dy , dx);
    }

    public static float getRadian(int pos1X, int pos1Y, int pos2X, int pos2Y) {
        float dx = pos2X - pos1X;
        float dy = pos2Y - pos1Y;

        return (float)Math.atan2(dy , dx);
    }

    public static float getRadian(float pos1X, float pos1Y, float pos2X, float pos2Y) {
        float dx = pos2X - pos1X;
        float dy = pos2Y - pos1Y;

        return (float)Math.atan2(dy , dx);
    }

    public static ZPosF getPosByDegree (float x, float y, float degree, float length) {
        return new ZPosF((float)(x + length * Math.cos(degreeToRadian(degree))), (float)(y + length * Math.sin(degreeToRadian(degree))));
    }

    public static ZPosF getPosByRadian (float x, float y, float radian, float length) {
        return new ZPosF((float)(x + length * Math.cos(radian)), (float)(y + length * Math.sin(radian)));
    }
}

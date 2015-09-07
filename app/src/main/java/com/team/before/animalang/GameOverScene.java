package com.team.before.animalang;

import android.graphics.Color;

import us.zeropen.zroid.game.ZDefine;
import us.zeropen.zroid.game.ZGameActivity;
import us.zeropen.zroid.game.scene.ZScene;
import us.zeropen.zroid.graphic.ZObject;
import us.zeropen.zroid.graphic.ZSprite;
import us.zeropen.zroid.graphic.ZText;
import us.zeropen.zroid.input.TouchEvent;

/**
 * Created by Astora on 2015-08-16.
 */
public class GameOverScene extends ZScene {
    public GameOverScene(ZGameActivity game, int buildCount, int point, int day) {
        super(game);
        addMusic("SeriousPiano", "SeriousPiano.mp3");
        addSound("gameover", "gameover.mp3");
        playMusic("SeriousPiano", true);
        playSound("gameover");
        addTexture("gameover", "gameover.png");
        addSprite(new ZSprite("otaku","gameover",0,0)).setWidth(ZDefine.GAME_WIDTH+10).setHeight(ZDefine.GAME_HEIGHT);
        addText(new ZText("score", "살린 마을 수 : " + (buildCount + 3) + "\n포인트 : " + point + "포인트\n진행 일차 : " + day + "일\n총 점 : " + (buildCount * ((day * 10) + point)), 50, 500, 70, Color.WHITE, "cherry"));
    }
    int time=0;
    @Override
    public void update(float eTime) {
        super.update(eTime);
        time+=eTime;
    }

    @Override
    public void executeEvent(int touchType, TouchEvent event, String id, ZObject object, int pointer, float eTime) {
        super.executeEvent(touchType, event, id, object, pointer, eTime);
        if(time>2000) {
            stopMusic("SeriousPiano");
            setScene(new TitleScene(game));
        }
    }
}
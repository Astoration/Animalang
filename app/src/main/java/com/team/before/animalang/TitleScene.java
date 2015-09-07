package com.team.before.animalang;

import us.zeropen.zroid.device.ZSharedPreferencesMgr;
import us.zeropen.zroid.game.ZDefine;
import us.zeropen.zroid.game.ZGameActivity;
import us.zeropen.zroid.game.scene.ZScene;
import us.zeropen.zroid.graphic.ZGraphic;
import us.zeropen.zroid.graphic.ZObject;
import us.zeropen.zroid.graphic.ZSprite;
import us.zeropen.zroid.input.TouchEvent;

/**
 * Created by Astora on 2015-08-09.
 */
public class TitleScene extends ZScene {
    public TitleScene(ZGameActivity game) {
        super(game);
        addTexture("tt", "title.png");
        addSprite(new ZSprite("asdd","tt",0,0)).setScale(ZDefine.GAME_WIDTH / (float) ZGraphic.getTexture("tt").getWidth(), ZDefine.GAME_HEIGHT / (float) ZGraphic.getTexture("tt").getHeight());
        addMusic("ga", "title.mp3");
        playMusic("ga");
        if(ZSharedPreferencesMgr.get("volume1").equals("default"))ZSharedPreferencesMgr.save("volume1","100");
        if(ZSharedPreferencesMgr.get("volume2").equals("default"))ZSharedPreferencesMgr.save("volume2","100");
        if(ZSharedPreferencesMgr.get("slide").equals("default"))ZSharedPreferencesMgr.save("slide","1.0");
        if(ZSharedPreferencesMgr.get("sound").equals("default"))ZSharedPreferencesMgr.save("sound","on");

        setVolume("ga", Integer.parseInt(ZSharedPreferencesMgr.get("volume1"))/100.00f);
    }

    @Override
    public void executeEvent(int touchType, TouchEvent event, String id, ZObject object, int pointer, float eTime) {
        super.executeEvent(touchType, event, id, object, pointer, eTime);
        stopMusic("ga");
        setScene(new TutorialScene(game));
        //setScene(new GameReady(game));
    }
}

package com.team.before.animalang;

import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;

import us.zeropen.zroid.device.ZDeviceMgr;
import us.zeropen.zroid.device.ZSharedPreferencesMgr;
import us.zeropen.zroid.game.ZDefine;
import us.zeropen.zroid.game.ZGameActivity;
import us.zeropen.zroid.game.scene.ZScene;
import us.zeropen.zroid.graphic.ZCircle;
import us.zeropen.zroid.graphic.ZObject;
import us.zeropen.zroid.graphic.ZRect;
import us.zeropen.zroid.graphic.ZSprite;
import us.zeropen.zroid.graphic.ZText;
import us.zeropen.zroid.input.TouchEvent;

/**
 * Created by Astora on 2015-08-16.
 */
public class GameReady extends ZScene {
    int j=1;
    int k=1;
    boolean isAction= true;
    public GameReady(ZGameActivity game) {
        super(game);
        addTexture("gameready", "gameready.png");
        addSprite(new ZSprite("asdf","gameready",0,0)).setWidth(ZDefine.GAME_WIDTH).setHeight(ZDefine.GAME_HEIGHT);
        addTexture("g1", "gamestart.png");
        addSprite(new ZSprite("otaku1", "g1", 420, 810)).setScale(0.9f);
        addTexture("g2", "gamehowto.png");
        addSprite(new ZSprite("otaku2", "g2", 1070, 810)).setScale(0.9f);
        addTexture("g4", "makers.png");
        addSprite(new ZSprite("otaku3","g4",0,610)).setScale(0.9f);
        addTexture("on", "on.png");
        addTexture("off", "off.png");
        addTexture("setting", "setting.png");
        addSprite(new ZSprite("otaku5", "setting", ZDefine.GAME_WIDTH-500*0.9f,610)).setScale(0.9f);
        addTexture("box", "box2.png");
        addSprite(new ZSprite("settings", "box", ZDefine.GAME_WIDTH / 2 - 800, 540 - 600)).setScale(2f).setRender(false);
        addSprite(new ZSprite("otaku4", "on", ZDefine.GAME_WIDTH/2-400, 50)).setScale(0.9f).setRender(false).setTouchAble(false);
        if(ZSharedPreferencesMgr.get("slide").equals("default"))ZSharedPreferencesMgr.save("slide","1.0");
        addRect(new ZRect("bar1", ZDefine.GAME_WIDTH / 2 - 300, 500, 500, 10, Color.rgb(0xC2, 0xC2, 0xC2))).setRender(false);
        addCircle(new ZCircle("bar1-1", ZDefine.GAME_WIDTH / 2 - 300, 505, 30, Color.rgb(0x84, 0xd9, 0x64))).setRender(false).addPosX((Float.parseFloat(ZSharedPreferencesMgr.get("slide")) / 2.0f) * findRect("bar1").getScaledWidth());
        if(ZSharedPreferencesMgr.get("volume1").equals("default"))ZSharedPreferencesMgr.save("volume1","100");
        addRect(new ZRect("bar2", ZDefine.GAME_WIDTH / 2 - 300, 600, 500, 10, Color.rgb(0xC2, 0xC2, 0xC2))).setRender(false);
        addCircle(new ZCircle("bar2-1", ZDefine.GAME_WIDTH / 2 - 300, 605, 30, Color.rgb(0x84, 0xd9, 0x64))).setRender(false).addPosX(((Integer.parseInt(ZSharedPreferencesMgr.get("volume1"))/100.0f) * findRect("bar2").getScaledWidth()));
        if(ZSharedPreferencesMgr.get("volume2").equals("default"))ZSharedPreferencesMgr.save("volume2","100");
        addRect(new ZRect("bar3", ZDefine.GAME_WIDTH / 2 - 300, 700, 500, 10, Color.rgb(0xC2, 0xC2, 0xC2))).setRender(false);
        addCircle(new ZCircle("bar3-1", ZDefine.GAME_WIDTH / 2 - 300, 705, 30, Color.rgb(0x84, 0xd9, 0x64))).setRender(false).addPosX(((Integer.parseInt(ZSharedPreferencesMgr.get("volume2")) / 100.0f) * findRect("bar3").getScaledWidth()));
        addFont("cherry", "cherry.ttf");
        addText(new ZText("interface1", "CV :", ZDefine.GAME_WIDTH / 2 - 600, 290, 100, Color.BLACK, "cherry")).setRender(false);
        addText(new ZText("interface2", "스크롤 감도 :", ZDefine.GAME_WIDTH / 2 - 670, 520, 60, Color.BLACK, "cherry")).setRender(false);
        addText(new ZText("sensation","0",findRect("bar1").getPosX()+findRect("bar1").getScaledWidth()+100,520,60,Color.BLACK,"cherry")).setRender(false);
        findText("sensation").setText(ZSharedPreferencesMgr.get("slide"));
        addText(new ZText("interface3","배경음 볼륨 :",ZDefine.GAME_WIDTH/2-670,620,60,Color.BLACK,"cherry")).setRender(false);
        addText(new ZText("bgsound","0",findRect("bar2").getPosX()+findRect("bar2").getScaledWidth()+100,620,60,Color.BLACK,"cherry")).setRender(false);
        findText("bgsound").setText(ZSharedPreferencesMgr.get("volume1")+"%");
        addText(new ZText("interface4","효과음 볼륨 :",ZDefine.GAME_WIDTH/2-670,720,60,Color.BLACK,"cherry")).setRender(false);
        addText(new ZText("fxsound","0",findRect("bar3").getPosX()+findRect("bar3").getScaledWidth()+100,720,60,Color.BLACK,"cherry")).setRender(false);
        findText("fxsound").setText(ZSharedPreferencesMgr.get("volume2")+"%");
        if(ZSharedPreferencesMgr.get("sound").equals("default"))ZSharedPreferencesMgr.save("sound","on");
        if(ZSharedPreferencesMgr.get("sound").equals("off"))findSprite("otaku4").setTexture("off");
        else findSprite("otaku4").setTexture("on");
        addTexture("ok","ok.png");
        addSprite(new ZSprite("OKbutton", "ok", (float)(ZDefine.GAME_WIDTH/2-470*0.9/2),ZDefine.GAME_HEIGHT-350)).setScale(0.9f).setRender(false).setTouchAble(false);
        addMusic("g3", "title.mp3");
        addTexture("info1", "info1.png");
        addTexture("info2","info2.png");
        for(int i=0;i<8;i++)addTexture("howtoplay"+i,"howtoplay"+i+".png");
        addSprite(new ZSprite("howtoplay","howtoplay0",ZDefine.GAME_WIDTH/2-800,540-600)).setScale(2f);
        addSprite(new ZSprite("info","howtoplay0",ZDefine.GAME_WIDTH/2-800,540-600)).setScale(2f);
        playMusic("g3", true);
        setVolume("g3", Integer.parseInt(ZSharedPreferencesMgr.get("volume1")) / 100.00f);
    }

    @Override
    public void onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        super.onScroll(e1, e2, distanceX, distanceY);
//        Log.d("Pos1", "(" + e1.getX() + "," + e1.getY() + ")");
//        Log.d("Pos2" , "("+e2.getX()+","+e2.getY()+")");
        if(Math.abs(distanceX)<2)return;
        if(
                findSprite("settings").isRender()
                &&
                findCircle("bar1-1").getPosY()-findCircle("bar1-1").getRadius()-50<=e1.getY()*ZDefine.GAME_SCALE_Y
                &&
                findCircle("bar1-1").getPosY()+findCircle("bar1-1").getRadius()+50>=e1.getY()*ZDefine.GAME_SCALE_Y
                &&
                findCircle("bar1-1").getPosX()-findCircle("bar1-1").getRadius()-800<=e1.getX()*ZDefine.GAME_SCALE_X
                &&
                findCircle("bar1-1").getPosX()+findCircle("bar1-1").getRadius()+800>=e1.getX()*ZDefine.GAME_SCALE_X
                ) {
            if(findCircle("bar1-1").getPosX()+(-1*distanceX*ZDefine.GAME_SCALE_X)<=findRect("bar1").getPosX()||findCircle("bar1-1").getPosX()+(-1*distanceX*ZDefine.GAME_SCALE_X)>findRect("bar1").getPosX()+findRect("bar1").getScaledWidth())return;
            findCircle("bar1-1").addPosX(-1 * distanceX * ZDefine.GAME_SCALE_X);
            float temp=2*((findCircle("bar1-1").getPosX()-findRect("bar1").getPosX()) / ( findRect("bar1").getScaledWidth()));
            temp=(float)Math.ceil(temp*10f)/10f;
            ZSharedPreferencesMgr.save("slide", "" + temp);
            findText("sensation").setText(ZSharedPreferencesMgr.get("slide"));
        }
        else if(
                findSprite("settings").isRender()
                        &&
                        findCircle("bar2-1").getPosY()-findCircle("bar2-1").getRadius()-50<=e1.getY()*ZDefine.GAME_SCALE_Y
                        &&
                        findCircle("bar2-1").getPosY()+findCircle("bar2-1").getRadius()+50>=e1.getY()*ZDefine.GAME_SCALE_Y
                        &&
                        findCircle("bar2-1").getPosX()-findCircle("bar2-1").getRadius()-800<=e1.getX()*ZDefine.GAME_SCALE_X
                        &&
                        findCircle("bar2-1").getPosX()+findCircle("bar2-1").getRadius()+800>=e1.getX()*ZDefine.GAME_SCALE_X
                ) {
            if(findCircle("bar2-1").getPosX()+(-1*distanceX*ZDefine.GAME_SCALE_X)<=findRect("bar2").getPosX()||findCircle("bar2-1").getPosX()+(-1*distanceX*ZDefine.GAME_SCALE_X)>=findRect("bar2").getPosX()+findRect("bar2").getScaledWidth()+5)return;
            findCircle("bar2-1").addPosX(-1 * distanceX * ZDefine.GAME_SCALE_X);
            int temp=(int)(100*((findCircle("bar2-1").getPosX()-findRect("bar2").getPosX()) / ( findRect("bar2").getScaledWidth())));
            ZSharedPreferencesMgr.save("volume1", "" + temp);
            findText("bgsound").setText(ZSharedPreferencesMgr.get("volume1")+"%");
        }else if(
                findSprite("settings").isRender()
                        &&
                        findCircle("bar3-1").getPosY()-findCircle("bar3-1").getRadius()-50<=e1.getY()*ZDefine.GAME_SCALE_Y
                        &&
                        findCircle("bar3-1").getPosY()+findCircle("bar3-1").getRadius()+50>=e1.getY()*ZDefine.GAME_SCALE_Y
                        &&
                        findCircle("bar3-1").getPosX()-findCircle("bar3-1").getRadius()-800<=e1.getX()*ZDefine.GAME_SCALE_X
                        &&
                        findCircle("bar3-1").getPosX()+findCircle("bar3-1").getRadius()+800>=e1.getX()*ZDefine.GAME_SCALE_X
                ) {
            if(findCircle("bar3-1").getPosX()+(-1*distanceX*ZDefine.GAME_SCALE_X)<=findRect("bar3").getPosX()||findCircle("bar3-1").getPosX()+(-1*distanceX*ZDefine.GAME_SCALE_X)>=findRect("bar3").getPosX()+findRect("bar3").getScaledWidth()+5)return;
            findCircle("bar3-1").addPosX(-1 * distanceX * ZDefine.GAME_SCALE_X);
            int temp=(int)(100*((findCircle("bar3-1").getPosX()-findRect("bar3").getPosX()) / ( findRect("bar3").getScaledWidth())));
            ZSharedPreferencesMgr.save("volume2", "" + temp);
            findText("fxsound").setText(ZSharedPreferencesMgr.get("volume2")+"%");
        }

    }

    @Override
    public void update(float eTime) {
        super.update(eTime);
        isAction=true;
    }

    @Override
    public void executeEvent(int touchType, TouchEvent event, String id, ZObject object, int pointer, float eTime) {
        super.executeEvent(touchType, event, id, object, pointer, eTime);
        if(touchType==TOUCH_RELEASE) {
            if(findSprite("settings").isRender()&&isAction&&findCircle("bar1-1").getPosY()-findCircle("bar1-1").getRadius()-50<=event.pos.y&& findCircle("bar1-1").getPosY()+findCircle("bar1-1").getRadius()+50>=event.pos.y) {
                if(event.pos.x<=findRect("bar1").getPosX()||event.pos.x>=findRect("bar1").getPosX()+findRect("bar1").getScaledWidth())return;
                findCircle("bar1-1").setPosX(event.pos.x);
                float temp=2*((findCircle("bar1-1").getPosX()-findRect("bar1").getPosX()) / ( findRect("bar1").getScaledWidth()));
                temp=(float)Math.ceil(temp*10f)/10f;
                ZSharedPreferencesMgr.save("slide", "" + temp);
                findText("sensation").setText(ZSharedPreferencesMgr.get("slide"));
            }else if(findSprite("settings").isRender()&&isAction&&findCircle("bar2-1").getPosY()-findCircle("bar2-1").getRadius()-50<=event.pos.y&& findCircle("bar2-1").getPosY()+findCircle("bar2-1").getRadius()+50>=event.pos.y) {
                if(event.pos.x<=findRect("bar2").getPosX()||event.pos.x>=findRect("bar2").getPosX()+findRect("bar2").getScaledWidth())return;
                findCircle("bar2-1").setPosX(event.pos.x);
                int temp=(int)(100*((findCircle("bar2-1").getPosX()-findRect("bar2").getPosX()) / ( findRect("bar2").getScaledWidth())));
                ZSharedPreferencesMgr.save("volume1", "" + temp);
                findText("bgsound").setText(ZSharedPreferencesMgr.get("volume1")+"%");
            }else if(findSprite("settings").isRender()&&isAction&&findCircle("bar3-1").getPosY()-findCircle("bar3-1").getRadius()-50<=event.pos.y&& findCircle("bar3-1").getPosY()+findCircle("bar3-1").getRadius()+50>=event.pos.y) {
                if(event.pos.x<=findRect("bar3").getPosX()||event.pos.x>=findRect("bar3").getPosX()+findRect("bar3").getScaledWidth())return;
                findCircle("bar3-1").setPosX(event.pos.x);
                int temp=(int)(100*((findCircle("bar3-1").getPosX()-findRect("bar3").getPosX()) / ( findRect("bar3").getScaledWidth())));
                ZSharedPreferencesMgr.save("volume2", "" + temp);
                findText("fxsound").setText(ZSharedPreferencesMgr.get("volume2") + "%");
            }else if(isAction&&findSprite("settings").isRender()&&id.equals("OKbutton")){
                findSprite("settings").setRender(false);
                findSprite("otaku4").setRender(false).setTouchAble(false);
                findRect("bar1").setRender(false);
                findCircle("bar1-1").setRender(false);
                findText("interface1").setRender(false);
                findText("interface2").setRender(false);
                findText("sensation").setRender(false);
                findCircle("bar2-1").setRender(false);
                findRect("bar2").setRender(false);
                findText("interface3").setRender(false);
                findText("bgsound").setRender(false);
                findText("interface4").setRender(false);
                findText("fxsound").setRender(false);
                findRect("bar3").setRender(false);
                findCircle("bar3-1").setRender(false);
                findSprite("OKbutton").setRender(false).setTouchAble(false);
            } else if ((findSprite("info").getTextureId().equals("howtoplay0"))&&(findSprite("howtoplay").getTextureId().equals("howtoplay0")) && (!findSprite("settings").isRender())&&isAction&&id.equals("otaku1")) {
                stopMusic("g3");
                isAction=false;
                setScene(new GameScene(game));
            } else if ( (findSprite("info").getTextureId().equals("howtoplay0"))&&(!findSprite("settings").isRender())&&isAction&&findSprite("howtoplay").getTextureId().equals("howtoplay0")&&id.equals("otaku2")) {
                findSprite("howtoplay").setTexture("howtoplay1");
                isAction=false;
            } else if( (findSprite("howtoplay").getTextureId().equals("howtoplay0"))&&(!findSprite("settings").isRender())&&isAction&& id.equals("otaku3")&&findSprite("info").getTextureId().equals("howtoplay0")) {
                findSprite("info").setTexture("info1");
                isAction = false;
            } else if(isAction&&id.equals("otaku4")) {
                if (ZSharedPreferencesMgr.get("sound").equals("on")) {
                    ZSharedPreferencesMgr.save("sound", "off");
                    findSprite("otaku4").setTexture("off");
                } else {
                    ZSharedPreferencesMgr.save("sound", "on");
                    findSprite("otaku4").setTexture("on");
                }
            }else if(isAction&&id.equals("otaku5")&&!findSprite("settings").isRender()){
                findSprite("settings").setRender(true);
                findSprite("otaku4").setRender(true).setTouchAble(true);
                findRect("bar1").setRender(true);
                findCircle("bar1-1").setRender(true);
                findText("interface1").setRender(true);
                findText("interface2").setRender(true);
                findText("sensation").setRender(true);
                findCircle("bar2-1").setRender(true);
                findRect("bar2").setRender(true);
                findText("interface3").setRender(true);
                findText("bgsound").setRender(true);
                findText("interface4").setRender(true);
                findText("fxsound").setRender(true);
                findRect("bar3").setRender(true);
                findCircle("bar3-1").setRender(true);
                findSprite("OKbutton").setRender(true).setTouchAble(true);
            } else if (isAction&&!findSprite("howtoplay").getTextureId().equals("howtoplay0")) {
                if (j + 1 > 8) {findSprite("howtoplay").setTexture("howtoplay0");j=1;isAction=false;return;}
                findSprite("howtoplay").setTexture("howtoplay" + (j++));
                isAction=false;
            }else if(isAction&&!findSprite("info").getTextureId().equals("howtoplay0")){
                if(k+1>2){findSprite("info").setTexture("howtoplay0");k=1;isAction=false;return;}
                findSprite("info").setTexture("info"+(++k));
                isAction=false;
            }
        }
    }
}

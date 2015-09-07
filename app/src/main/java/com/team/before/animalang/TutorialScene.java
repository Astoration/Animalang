package com.team.before.animalang;

import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Random;

import us.zeropen.zroid.device.ZSharedPreferencesMgr;
import us.zeropen.zroid.game.ZDefine;
import us.zeropen.zroid.game.ZGameActivity;
import us.zeropen.zroid.game.scene.ZScene;
import us.zeropen.zroid.graphic.ZGraphic;
import us.zeropen.zroid.graphic.ZObject;
import us.zeropen.zroid.graphic.ZSprite;
import us.zeropen.zroid.graphic.ZText;
import us.zeropen.zroid.input.TouchEvent;

/**
 * Created by Astora on 2015-08-28.
 */
public class TutorialScene extends ZScene {
    float time = 0;
    int step=0;
    ZSprite interface1;
    ZSprite interface2;
    ZText interface3;
    ZText interface4;
    ZText interface5;
    ZText interface6;
    ZText interface7;
    ZSprite menu1;
    ZSprite menu2;
    ZSprite menu3;
    ZSprite menu4;
    ZSprite menu5;
    ZSprite menu6;
    ZText menu7;
    ZSprite menu8;
    ZSprite menu9;
    ZSprite menu10;
    ZText menu11;
    String chatlist[][][] = new String[3][2][9];
    int[] BuildCount = new int[1];
    int cooltime=0;
    int tempNum = 0;
    boolean isTurn = true;
    boolean noStep = true;
    public class Area {
        ZSprite senani;
        int kind;
        int arr;
        boolean isBuild;
        int x, y;
        int range;
        int status;
        int happyness;
        int fire;
        int rain;
        int wind;
        int animal;
        boolean isChat;
        boolean TurnAct;

        public Area(int kind, int arr, int x, int y) {
            this.kind = kind;
            this.arr = arr;
            this.x = x;
            this.y = y;
            this.range = 100;
            this.TurnAct=true;
            this.isBuild = false;
            this.happyness = 100;
            this.fire = 0;
            this.rain = 0;
            this.wind = 0;
            this.animal = 0;
            this.senani = addSprite(new ZSprite("temp" + tempNum++, "tempIMG", x - 75, y - 50)).setScale(0.5f);
        }

        public boolean SetBuild(int[] BuildCount, int[] point) {//포인트 구현 하려고 했다.
            if (point[0] - (100 + 50 * BuildCount[0]) < 0) return false;
            point[0] -= (100 + 50 * BuildCount[0]);
            BuildCount[0]+=1;
            int rnd = rand.nextInt(2);
            for(int i=0;i<buildArea.size();i++){
                Area tempArea = buildArea.get(i);
                if(tempArea.happyness<100)tempArea.happyness+=5;
            }
            this.animal = 2 * kind - rnd;
            removeChild(this.senani);
            this.senani = addSprite(new ZSprite("animal" + tempNum++, "sd" + this.animal, this.x - 125, findSprite("map1").getPosY() + this.y - 200)).setScale(1f);
            setChildIndexToLast(getChildIndex(interface1));
            setChildIndexToLast(getChildIndex(interface2));
            setChildIndexToLast(getChildIndex(interface3));
            setChildIndexToLast(getChildIndex(interface4));
            setChildIndexToLast(getChildIndex(interface5));
            setChildIndexToLast(getChildIndex(interface6));
            setChildIndexToLast(getChildIndex(interface7));
            setChildIndexToLast(getChildIndex(menu1));
            setChildIndexToLast(getChildIndex(menu2));
            setChildIndexToLast(getChildIndex(menu3));
            setChildIndexToLast(getChildIndex(menu4));
            setChildIndexToLast(getChildIndex(menu5));
            setChildIndexToLast(getChildIndex(menu6));
            setChildIndexToLast(getChildIndex(menu7));
            setChildIndexToLast(getChildIndex(menu8));
            setChildIndexToLast(getChildIndex(menu9));
            setChildIndexToLast(getChildIndex(menu10));
            setChildIndexToLast(getChildIndex(menu11));
            UpdateStatus();
            this.isBuild = true;
            return true;
        }

        public void addWether(int cases) {
            switch (cases) {
                case 1:
                    fire++;
                    break;
                case 2:
                    rain++;
                    break;
                case 3:
                    wind++;
                    break;
                default:
                    break;
            }
            if (fire == 0 && rain == 0 & wind == 0) {
                status = 0;
            } else if (
                    (rain == 1 && fire == 0 & wind == 0)
                            ||
                            (fire == 1 && wind == 0 & rain == 0)
                            ||
                            (wind == 1 && rain == 0 && fire == 0)
                    ) {
                if (rain == 1) status = 1;
                if (wind == 1) status = 2;
                if (fire == 1) status = 3;
            } else if (
                    (fire == 1 && wind == 1 && rain == 0)
                            ||
                            (fire >= 1 && wind >= 1 && rain >= 1)
                            ||
                            (fire == 1 && wind == 0 && rain == 1)
                    ) {
                status = 4;
                fire = 0;
                wind = 0;
                rain = 0;
            } else if (fire == 0 && wind == 1 && rain == 1) {
                status = 5;
            } else if (
                    (fire >= 2 && rain == 0 && wind == 0)
                            ||
                            (rain >= 2 && wind == 0 && fire == 0)
                            ||
                            (wind >= 2 && fire == 0 && rain == 0)
                    ) {
                if (fire >= 2) status = 6;
                if (wind >= 2) status = 7;
                if (rain >= 2) status = 8;
            }
        }

        public void CheckWether(int[] point) {
            if (status == 0) {
                if (happyness < 100) happyness += 5;
                else point[0] = point[0]+20;
            } else if (status >= 1 && status <= 3) {
                happyness -= 5;
                point[0] = point[0]- 20;
            } else if (status == 4) {
                if(happyness<100)happyness += 5;
                point[0] = point[0]+ 30;
            } else if (status == 5) {
                happyness -= 10;
                point[0] = point[0]-40;
            } else if (status >= 6 && status <= 8) {
                happyness -= 20;
                point[0] = point[0]- 60;
            }
            findText("points").setText(point[0] + " 포인트");
        }
    }

    ArrayList<Area> buildArea = new ArrayList<>();
    Area ars[][] = new Area[3][10];
    Area selectArea = null;
    int[] point = new int[1];
    int days = 1;
    Random rand;
    boolean isStop[]=new boolean[10];
    int TutorialScroll=0;

    public TutorialScene(ZGameActivity game) {
        super(game);
        for(int i=0;i<10;i++){
            isStop[i]=true;
        }
        point[0]=0;
        BuildCount[0]=0;
        chatlist[0][0][0] = "평소와 다름 없는 날이네요.";
        chatlist[0][0][1] = "이렇게 물이 많아버리면...";
        chatlist[0][0][2] = "바람이 너무 강해서 눈을 뜰 수 없어요.";
        chatlist[0][0][3] = "더워서 우... 움직일 수 없어요.";
        chatlist[0][0][4] = "후우우... 다행이에요...";
        chatlist[0][0][5] = "이대로 가면 집이 없어질지도 몰라요";
        chatlist[0][0][6] = "너무 더워서 더 이상은...(말을 잇지 못하고 있다.)";
        chatlist[0][0][7] = "날라가 버려요오오오";
        chatlist[0][0][8] = "하늘이 울적해보여요... 언제쯤 비가 멈춰질까요...";
        chatlist[0][1][0] = "...우웅...안녕";
        chatlist[0][1][1] = "...비 ...싫어";
        chatlist[0][1][2] = "날라... 가버려...";
        chatlist[0][1][3] = "더...워... 무지무지...";
        chatlist[0][1][4] = "(미소를 짓는다)";
        chatlist[0][1][5] = "잠겨버릴거야... 분명";
        chatlist[0][1][6] = "(너무 더워 말을 안함)...";
        chatlist[0][1][7] = "비랑 바람... 너무 많아... 강해... 싫어...";
        chatlist[0][1][8] = "오랫동안 와버리면... 수영... 하며 살아야 하는걸까...";
        chatlist[1][0][0] = "오늘도 좋은 날씨에요(웃음)";
        chatlist[1][0][1] = "비가 오네요...";
        chatlist[1][0][2] = "바람에 나무들이 쓰러져버렸어요...";
        chatlist[1][0][3] = "너무... 더운데요?";
        chatlist[1][0][4] = "앗, 다시 평소처럼 좋은 날씨가 되는 걸까요?";
        chatlist[1][0][5] = "비 때문에 강이 넘쳐버렸어요(울먹)";
        chatlist[1][0][6] = "헥헥...";
        chatlist[1][0][7] = "아앗?! 지붕이 날아가버렸어요!!";
        chatlist[1][0][8] = "이렇게 오래 비가 온다면 햇빛이 그리워지고 말거에요...";
        chatlist[1][1][0] = "뭐, 오늘도 좋은 하루!";
        chatlist[1][1][1] = "난 비 오는 날은 싫은데 말이지";
        chatlist[1][1][2] = "바람 때문에 머리 헝클어졌잖아!";
        chatlist[1][1][3] = "더워어어어어어";
        chatlist[1][1][4] = "이제야 좀 조용해 지겠네";
        chatlist[1][1][5] = "으앗!? 집에 물이 새잖아!";
        chatlist[1][1][6] = "너무 더워... 이대로는 못 살아...";
        chatlist[1][1][7] = "샤아아악...!!! 바람에 날아가버리겠어!";
        chatlist[1][1][8] = "계속 비만 오니까 우울해졌어...";
        chatlist[2][0][0] = "쾌적한 날씨!!";
        chatlist[2][0][1] = "아으... 비 정말 싫어!";
        chatlist[2][0][2] = "이거이거 이러다가 집까지 날아가 버린다구...!";
        chatlist[2][0][3] = "아으... 이건 너무 덥잖아...";
        chatlist[2][0][4] = "좋아 다시 돌아왔다!";
        chatlist[2][0][5] = "이러다가 떠내려가 버린다구";
        chatlist[2][0][6] = "으아아아아아아 이건 너무 심하게 덥잖아아아";
        chatlist[2][0][7] = "집이 날라가 버린다구 날라가 버려!";
        chatlist[2][0][8] = "이렇게 오래 지속되면 집이 사라져 버려...";
        chatlist[2][1][0] = "좋은 날이네요";
        chatlist[2][1][1] = "정말... 비라니 시원하지만 젖어서 싫다구요.";
        chatlist[2][1][2] = "윽... 이건 너무 강한걸요";
        chatlist[2][1][3] = "정말 심한 더위군요... 말라 죽어버릴지도...";
        chatlist[2][1][4] = "이제 평범한 일상을 즐길 수 있겠군요";
        chatlist[2][1][5] = "이거... 피해가 너무 심하겠어요...";
        chatlist[2][1][6] = "하루라도 빨리... 이 더위는 위험합니다.";
        chatlist[2][1][7] = "저까지 날아가 버린다구요?";
        chatlist[2][1][8] = "이제 비는 지긋지긋합니다.";
        addTexture("tempIMG", "noBuild.png");
        rand = new Random();
        addTexture("bar", "bar.png");
        addTexture("blackbar", "blackbar.png");
        addTexture("fire", "fire.png");
        addTexture("selBuild", "selBuild.png");
        addTexture("rain", "rain.png");
        addTexture("talk", "talk.png");
        addTexture("top", "top.png");
        addTexture("wether", "wether.png");
        addTexture("wind", "wind.png");
        addTexture("map", "map.png");
        addTexture("charactor1", "bear.png");
        addTexture("sd1", "bear_sd.png");
        addTexture("charactor2", "penguin.png");
        addTexture("sd2", "peng_sd.png");
        addTexture("charactor3", "rabbit.png");
        addTexture("sd3", "rabbit_sd.png");
        addTexture("charactor4", "cat.png");
        addTexture("sd4", "cat_sd.png");
        addTexture("charactor5", "fox.png");
        addTexture("sd5", "fox_sd.png");
        addTexture("charactor6", "kirin.png");
        addTexture("sd6", "kirin_sd.png");
        addTexture("isBuild", "build.png");
        addTexture("box1","box1.png");
        addFont("cherry", "cherry.ttf");
        addSprite(new ZSprite("map1", "map", 0, 0)).setScale(((float) ZDefine.GAME_WIDTH / (float) ZGraphic.getTexture("map").getWidth()) + 0.01f);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                int x, y;
                switch (i + 1) {
                    case 1:
                        switch (j + 1) {
                            case 1:
                                x = 371;
                                y = 85;
                                break;
                            case 2:
                                x = 298;
                                y = 130;
                                break;
                            case 3:
                                x = 332;
                                y = 169;
                                break;
                            case 4:
                                x = 211;
                                y = 229;
                                break;
                            case 5:
                                x = 248;
                                y = 296;
                                break;
                            case 6:
                                x = 328;
                                y = 266;
                                break;
                            case 7:
                                x = 440;
                                y = 208;
                                break;
                            case 8:
                                x = 565;
                                y = 231;
                                break;
                            case 9:
                                x = 510;
                                y = 313;
                                break;
                            case 10:
                                x = 418;
                                y = 370;
                                break;
                            default:
                                x = 0;
                                y = 0;
                                break;
                        }
                        break;
                    case 2:
                        switch (j + 1) {
                            case 1:
                                x = 103;
                                y = 480;
                                break;
                            case 2:
                                x = 225;
                                y = 498;
                                break;
                            case 3:
                                x = 86;
                                y = 604;
                                break;
                            case 4:
                                x = 173;
                                y = 601;
                                break;
                            case 5:
                                x = 274;
                                y = 601;
                                break;
                            case 6:
                                x = 358;
                                y = 610;
                                break;
                            case 7:
                                x = 468;
                                y = 528;
                                break;
                            case 8:
                                x = 205;
                                y = 689;
                                break;
                            case 9:
                                x = 323;
                                y = 724;
                                break;
                            case 10:
                                x = 477;
                                y = 654;
                                break;
                            default:
                                x = 0;
                                y = 0;
                                break;
                        }
                        break;
                    case 3:
                        switch (j + 1) {
                            case 1:
                                x = 316;
                                y = 903;
                                break;
                            case 2:
                                x = 401;
                                y = 903;
                                break;
                            case 3:
                                x = 487;
                                y = 898;
                                break;
                            case 4:
                                x = 566;
                                y = 914;
                                break;
                            case 5:
                                x = 546;
                                y = 1004;
                                break;
                            case 6:
                                x = 499;
                                y = 1057;
                                break;
                            case 7:
                                x = 413;
                                y = 1015;
                                break;
                            case 8:
                                x = 447;
                                y = 1143;
                                break;
                            case 9:
                                x = 367;
                                y = 1093;
                                break;
                            case 10:
                                x = 300;
                                y = 1056;
                                break;
                            default:
                                x = 0;
                                y = 0;
                                break;
                        }
                        break;
                    default:
                        x = 0;
                        y = 0;
                        break;
                }
                x *= ZDefine.GAME_WIDTH / 635.0f+0.01f;
                y *= ZDefine.GAME_WIDTH / 635.0+0.01f;
                ars[i][j] = new Area(i + 1, j + 1, x, y);
            }
        }
        //ZCameraMgr.setBound(-1000, -1000, 1920 * 3, 1080 * 3);
        interface1 = addSprite(new ZSprite("bar1", "bar", 0, 0));
        findSprite("bar1").setScale(((float) ZDefine.GAME_WIDTH / (float) ZGraphic.getTexture("bar").getWidth())+0.01f, 1.0f);
        interface2 = addSprite(new ZSprite("top1", "top", ZDefine.GAME_WIDTH / 2.0f - ZGraphic.getTexture("top").getWidth() / 2, 0));
        interface3 = addText(new ZText("animal", "동물 : ??", 5, 70, 50, Color.BLACK, "cherry"));
        interface4 = addText(new ZText("happyness", "행복도 : 100%", 360, 70, 50, Color.BLACK, "cherry"));
        interface5 = addText(new ZText("dyas", "1 일차", 900, 70, 50, Color.BLACK, "cherry"));
        interface6 = addText(new ZText("status", "상태 : 양호", 1300, 70, 50, Color.BLACK, "cherry"));
        interface7 = addText(new ZText("points", "250 포인트", 1680, 70, 50, Color.BLACK, "cherry"));
        menu1 = addSprite(new ZSprite("blBar", "blackbar", 0, 400)).setScaleX(ZDefine.GAME_WIDTH / (float) ZGraphic.getTexture("blackbar").getWidth()).setRender(false).setScaleY(2.0f).setTouchAble(false);
        menu2 = addSprite(new ZSprite("button1", "wether", 450, 550)).setRender(false).setTouchAble(false).setScale(1.3f);
        menu3 = addSprite(new ZSprite("button2", "talk", 1000, 550)).setRender(false).setTouchAble(false).setScale(1.3f);
        menu4 = addSprite(new ZSprite("button3", "isBuild", ZDefine.GAME_WIDTH / 2f - ZGraphic.getTexture("isBuild").getWidth()*1.3f / 2, 550)).setRender(false).setTouchAble(false).setScale(1.3f);
        menu5 = addSprite(new ZSprite("charactor", "charactor1", 0, 1080 - 390 * 2.3f)).setRender(false).setScale(2.3f);
        menu6 = addSprite(new ZSprite("talkbar", "box1", 30, 800)).setRender(false).setTouchAble(false);
        menu7 = addText(new ZText("chats", "", 100, 1010, 70, Color.BLACK, "cherry"));
        menu8 = addSprite(new ZSprite("bt1", "fire", 400, 470)).setRender(false).setTouchAble(false);
        menu9 = addSprite(new ZSprite("bt2", "rain", 800, 470)).setRender(false).setTouchAble(false);
        menu10 = addSprite(new ZSprite("bt3", "wind", 1200, 470)).setRender(false).setTouchAble(false);
        menu11 = addText(new ZText("message","",1000,830,50,Color.RED,"cherry")).setAlign(ZText.ALIGN_CENTER);
        point[0] = 9999;
        Area temp1 = ars[0][rand.nextInt(10)];
        temp1.SetBuild(BuildCount, point);
        addSound("voice", "1/1.mp3");
        Area temp2 = ars[1][rand.nextInt(10)];
        temp2.SetBuild(BuildCount, point);
        Area temp3 = ars[2][rand.nextInt(10)];
        temp3.SetBuild(BuildCount, point);
        buildArea.add(temp1);
        buildArea.add(temp2);
        buildArea.add(temp3);
        point[0] = 250;
        BuildCount[0] = 0;
        addMusic("gs", "gamesong.mp3");
        Log.d("sdasd", ZSharedPreferencesMgr.get("volume1"));
        playMusic("gs", true);
        setVolume("gs", (Integer.parseInt(ZSharedPreferencesMgr.get("volume1")) / 100.0f));
        addSound("f", "fx.mp3");
        findSprite("charactor").setRender(true).setTexture("charactor6");
        findSprite("talkbar").setRender(true).setTouchAble(true);
        findText("chats").setText("안녕, 처음 보는 얼굴이네?");
        step=1;
    }

    @Override
    public void update(float eTime) {
        super.update(eTime);
        cooltime++;
        noStep=true;
        if(step==4)noStep=false;
        isTurn = true;
//        time += eTime;
//            time = 0;
//            step++;
//            days++;
//            //for (int i = 0; i < buildArea.size(); i++) {
//                Area tempArea = buildArea.get(1);
//                if (true) {
//                    tempArea.addWether(rand.nextInt(3) + 1);
//                }
//                tempArea.TurnAct=true;
//                tempArea.CheckWether(point);
//            //}
//            findText("dyas").setText(days + " 일차");
    }

    @Override
    public void onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        super.onScroll(e1, e2, distanceX, distanceY);
        if(step==5)return;
        if(step==4)TutorialScroll+=Math.abs(distanceY*ZDefine.GAME_SCALE_Y);
        if(TutorialScroll>=2000&&step==4){
            step++;
            cooltime=0;
            noStep=false;
            findSprite("talkbar").setRender(true);
            findSprite("charactor").setRender(true);
            findText("chats").setText("뭐, 잘 둘러봤어?");
        }
        if (!(findText("chats").getText().equals(""))) return;
        if (findSprite("map1").getPosY() - ((distanceY*Float.parseFloat(ZSharedPreferencesMgr.get("slide")))*ZDefine.GAME_SCALE_Y) >= 116) return;
        if (findSprite("map1").getPosY() - ((distanceY*Float.parseFloat(ZSharedPreferencesMgr.get("slide")))*ZDefine.GAME_SCALE_Y) <= -2506) return;
        Log.d("scroll", "" + distanceY);
        if(Math.abs(distanceY)<3)return;
        findSprite("map1").addPosY(-1 * ((distanceY*Float.parseFloat(ZSharedPreferencesMgr.get("slide")))*ZDefine.GAME_SCALE_Y));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                ars[i][j].senani.addPosY(-1 * ((distanceY*Float.parseFloat(ZSharedPreferencesMgr.get("slide"))))*ZDefine.GAME_SCALE_Y);
            }
        }
        Log.d("s", "" + findSprite("map1").getPosY());
    }

    @Override
    public void render() {
        super.render();
    }

    public void UpdateStatus(){
        if(selectArea==null)return;
        findText("happyness").setText("행복도 : " + selectArea.happyness + "%");
        String tempString;
        switch (selectArea.animal){
            case 1:
                tempString = "북극곰";
                break;
            case 2:
                tempString = "펭귄";
                break;
            case 3:
                tempString = "토끼";
                break;
            case 4:
                tempString = "고양이";
                break;
            case 5:
                tempString = "사막여우";
                break;
            case 6:
                tempString = "기린";
                break;
            default:
                tempString = "??";
        }
        findText("animal").setText("동물 : "+tempString);
        String tempString2;
        switch (selectArea.happyness/10){
            case 10:
            case 9:
            case 8:
                tempString2 = "양호";
                break;
            case 7:
            case 6:
            case 5:
                tempString2 ="경고";
                break;
            default:
                tempString2 = "위험";
        }
        findText("status").setText("상태 : "+tempString2);
        findText("points").setText(point[0] + " 포인트");
    }
    @Override
    public void executeEvent(int touchType, TouchEvent event, String id, ZObject object, int pointer, float eTime) {
        super.executeEvent(touchType, event, id, object, pointer, eTime);
        boolean temp=false;
        if (touchType == TOUCH_RELEASE&&noStep) {
            switch (step) {
                case 1:
                    noStep=false;
                    findText("chats").setText("뭐, 간단하게 이곳에 대해 설명하도록 할게");
                    step++;
                    break;
                case 2:
                    noStep=false;
                    findText("chats").setText("일단 화면을 스크롤해서 이곳을 좀 둘러볼래?");
                    step++;
                    break;
                case 3:
                    noStep=false;
                    findText("chats").setText("");
                    findSprite("charactor").setRender(false);
                    findSprite("talkbar").setRender(false).setTouchAble(false);
                    step++;
                    break;
                case 5:
                    if(cooltime<60)return;
                    noStep=false;
                    findText("chats").setText("이런 식으로 이곳에는 세 가지 기후를 가진 세 대륙이 존재해");
                    step++;
                    break;
                case 6:
                    noStep=false;
                    step++;
                    findText("chats").setText("지역마다 다른 친구들이 살고 있는건 확인했어?");
                    break;
                case 7:
                    noStep=false;
                    step++;
                    findText("chats").setText("뭐 그럼 일단 온대 지역 친구를 터치해서 말을 걸어볼래?");
                    break;
                case 8:
                    noStep=false;
                    step++;
                    findSprite("talkbar").setRender(false).setTouchAble(false);
                    findSprite("charactor").setRender(false);
                    findText("chats").setText("");
                    break;
                case 13:
                    noStep=false;
                    step++;
                    findText("chats").setText("물론 이렇게 평소 같은 날씨에는 괜찮지만");
                    break;
                case 14:
                    noStep=false;
                    step++;
                    findText("chats").setText("우리 같은 동물들은 기후에 민감하기 때문에");
                    break;
                case 15:
                    noStep=false;
                    step++;
                    findText("chats").setText("조금만 기후가 나빠져도 피해를 입게 되");
                    break;
                case 16:
                    noStep=false;
                    days++;
                    step++;
                    Area tempArea = buildArea.get(2);
                    if (true) {
                        tempArea.addWether(rand.nextInt(3) + 1);
                    }
                    tempArea.TurnAct=true;
                    tempArea.CheckWether(point);
                    //}
                    findText("dyas").setText(days + " 일차");
                    findText("chats").setText("어라 그 사이에 하루가 지났네");
                    break;
                case 17:
                    noStep=false;
                    findText("chats").setText("그럼 여러 동물들에게서 어떤 변화가 생겼는지 확인해볼래?");
                    if(!selectArea.isBuild)selectArea.senani.setTexture("tempIMG");
                    selectArea=null;
                    step++;
                    break;
                case 18:
                    noStep=false;
                    step++;
                    findText("chats").setText("");
                    findSprite("talkbar").setRender(false).setTouchAble(false);
                    findSprite("charactor").setRender(false);
                    break;
                case 20:
                    noStep=false;
                    step++;
                    findText("chats").setText("그 지역의 행복도가 감소한거 눈치챘어?");
                    break;
                case 21:
                    noStep=false;
                    step++;
                    findText("chats").setText("분명 그 지역에 무슨일이 생긴거겠지? 한번 대화해볼래?");
                    break;
                case 24:
                    noStep=false;
                    step++;
                    findText("chats").setText("그럼 우리가 도와줘야겠지? 동물을 눌러볼래?");
                    break;
            }
        }
        if (id.equals("charactor")&&findSprite("button1").isRender())return;
        if (touchType == TOUCH_RELEASE&&noStep) {
            if (!findSprite("blBar").isRender()&&!findSprite("talkbar").isRender() && selectArea != null && id.equals(selectArea.senani.getId()) && selectArea.isBuild) {
                playSound("f",Integer.parseInt(ZSharedPreferencesMgr.get("volume2"))/100.0f);
                findSprite("blBar").setRender(true).setTouchAble(true);
                findSprite("button1").setRender(true).setTouchAble(true);
                findSprite("button2").setRender(true).setTouchAble(true);
                if(step==10){
                    findSprite("charactor").setTexture("charactor6").setRender(true);
                    findSprite("talkbar").setRender(true).setTouchAble(true);
                    findText("chats").setText("잘했어 이제 대화버튼을 눌러서 대화하면 되");
                    step++;
                }
                if(step==25){
                    findSprite("charactor").setTexture("charactor6").setRender(true);
                    findSprite("talkbar").setRender(true).setTouchAble(true);
                    findText("chats").setText("")
                }
                isTurn = false;
            } else if (!findSprite("blBar").isRender()&&!findSprite("talkbar").isRender() && selectArea != null && id.equals(selectArea.senani.getId()) && !selectArea.isBuild) {
                playSound("f",Integer.parseInt(ZSharedPreferencesMgr.get("volume2"))/100.0f);
                findSprite("blBar").setRender(true).setTouchAble(true);
                findSprite("button3").setRender(true).setTouchAble(true);
                isTurn = false;

            } else if (id.equals("button1")) {
                playSound("f",Integer.parseInt(ZSharedPreferencesMgr.get("volume2"))/100.0f);
                if(step==11){
                    findSprite("talkbar").setRender(true).setTouchAble(true);
                    findSprite("charactor").setRender(true).setTexture("charactor6");
                    findText("chats").setText("아니아니 대화버튼을 눌러야지");
                }
                findSprite("button1").setRender(false).setTouchAble(false);
                findSprite("button2").setRender(false).setTouchAble(false);
                findSprite("bt1").setRender(true).setTouchAble(true);
                findSprite("bt2").setRender(true).setTouchAble(true);
                findSprite("bt3").setRender(true).setTouchAble(true);
                isTurn = false;

            } else if (id.equals("bt1")) {
                playSound("f",Integer.parseInt(ZSharedPreferencesMgr.get("volume2"))/100.0f);
                if(!selectArea.TurnAct){
                    isTurn=false;
                    findText("message").setText("이 지역은 오늘 이미 관리하였습니다.");
                    return;
                }
                selectArea.TurnAct=false;
                selectArea.addWether(1);
                selectArea.CheckWether(point);
                UpdateStatus();
                findSprite("blBar").setRender(false).setTouchAble(false);
                findSprite("bt1").setRender(false).setTouchAble(false);
                findSprite("bt2").setRender(false).setTouchAble(false);
                findSprite("bt3").setRender(false).setTouchAble(false);
                isTurn = false;

            } else if (id.equals("bt2")) {
                playSound("f",Integer.parseInt(ZSharedPreferencesMgr.get("volume2"))/100.0f);
                if(!selectArea.TurnAct){
                    isTurn=false;
                    findText("message").setText("이 지역은 오늘 이미 관리하였습니다.");
                    return;
                }
                selectArea.TurnAct=false;
                selectArea.addWether(2);
                selectArea.CheckWether(point);
                UpdateStatus();
                findSprite("blBar").setRender(false).setTouchAble(false);
                findSprite("bt1").setRender(false).setTouchAble(false);
                findSprite("bt2").setRender(false).setTouchAble(false);
                findSprite("bt3").setRender(false).setTouchAble(false);
                isTurn = false;

            } else if (id.equals("bt3")) {
                playSound("f",Integer.parseInt(ZSharedPreferencesMgr.get("volume2"))/100.0f);
                if(!selectArea.TurnAct){
                    isTurn=false;
                    findText("message").setText("이 지역은 오늘 이미 관리하였습니다.");
                    return;
                }
                selectArea.TurnAct=false;
                selectArea.addWether(3);
                selectArea.CheckWether(point);
                UpdateStatus();
                findSprite("blBar").setRender(false).setTouchAble(false);
                findSprite("bt1").setRender(false).setTouchAble(false);
                findSprite("bt2").setRender(false).setTouchAble(false);
                findSprite("bt3").setRender(false).setTouchAble(false);
                isTurn = false;

            } else if (id.equals("talkbar")) {
                playSound("f",Integer.parseInt(ZSharedPreferencesMgr.get("volume2"))/100.0f);
                findSprite("charactor").setTexture("charactor1").setRender(false);
                findSprite("talkbar").setRender(false).setTouchAble(false);
                findText("chats").setText("");
                selectArea.isChat = false;
                isTurn = false;

            } else if (id.equals("button2")) {
                playSound("f",Integer.parseInt(ZSharedPreferencesMgr.get("volume2"))/100.0f);

                findSprite("button1").setRender(false).setTouchAble(false);
                findSprite("button2").setRender(false).setTouchAble(false);
                findSprite("blBar").setRender(false).setTouchAble(false);
                int c = (selectArea.animal - (2 * selectArea.kind - 1)
                );
                findSprite("charactor").setTexture("charactor" + selectArea.animal).setRender(true);
                findSprite("talkbar").setRender(true).setTouchAble(true);
                findText("chats").setText(chatlist[selectArea.kind - 1][c][selectArea.status]);
                String path = selectArea.animal+"/"+(selectArea.status+1)+".mp3";
                if(!(selectArea.animal==2&&(selectArea.status==4||selectArea.status==6))&& ZSharedPreferencesMgr.get("sound").equals("on")){
                    removeSound("voice");
                    addSound("voice",path);
                    playSound("voice");
                }
                if(step==11)step++;
                if(step==22)step++;
                isTurn = false;

            } else if (id.equals("button3")) {
                playSound("f",Integer.parseInt(ZSharedPreferencesMgr.get("volume2"))/100.0f);
                if (selectArea.SetBuild(BuildCount, point)) buildArea.add(selectArea);
                else {findText("message").setText("포인트가 부족합니다.");isTurn=false;return;}
                findSprite("blBar").setRender(false).setTouchAble(false);
                findSprite("button3").setRender(false).setTouchAble(false);
            } else if (!findSprite("blBar").isRender() && !findSprite("talkbar").isRender()) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 10; j++) {
                        if (ars[i][j].happyness <= 0){stopMusic("gs"); setScene(new GameOverScene(game, BuildCount[0],point[0],days));}
                        if (
                                (ars[i][j].x - ars[i][j].range < event.pos.x)
                                        &&
                                        (event.pos.x < ars[i][j].x + ars[i][j].range)
                                        &&
                                        (findSprite("map1").getPosY() + ars[i][j].y - ars[i][j].range < event.pos.y)
                                        &&
                                        (event.pos.y < findSprite("map1").getPosY() + ars[i][j].y + ars[i][j].range)
                                ) {
                            playSound("f",Integer.parseInt(ZSharedPreferencesMgr.get("volume2"))/100.0f);

                            if (selectArea != null && !selectArea.isBuild) {
                                selectArea.senani.setTexture("tempIMG");
                            }
                            selectArea = ars[i][j];
                            if (!selectArea.isBuild) selectArea.senani.setTexture("selBuild");
                            UpdateStatus();
                            isTurn = false;
                            temp = true;
                        }
                    }
                }
            } else if ((findSprite("blBar").isRender()||findSprite("talkbar").isRender()) && isTurn) {
                if (!id.equals("button1") && !id.equals("button2") && !id.equals("button3") && !id.equals("bt1") && !id.equals("bt2") && !id.equals("bt3")) {
                    playSound("f",Integer.parseInt(ZSharedPreferencesMgr.get("volume2"))/100.0f);
                    findSprite("talkbar").setRender(false).setTouchAble(false);
                    findSprite("blBar").setRender(false).setTouchAble(false);
                    findSprite("button1").setRender(false).setTouchAble(false);
                    findSprite("button2").setRender(false).setTouchAble(false);
                    findSprite("button3").setRender(false).setTouchAble(false);
                    findSprite("bt1").setRender(false).setTouchAble(false);
                    findSprite("bt2").setRender(false).setTouchAble(false);
                    findSprite("bt3").setRender(false).setTouchAble(false);
                    findSprite("charactor").setRender(false);
                    findText("chats").setText("");
                    findText("message").setText("");
                    if(step==12){
                        step++;
                        findSprite("charactor").setRender(true).setTexture("charactor6");
                        findSprite("talkbar").setRender(true).setTouchAble(true);
                        findText("chats").setText("이렇게 대화를 통해서 그 지역의 기후를 알 수 있어");
                        return;
                    }
                    if(step==23){
                        step++;
                        findSprite("charactor").setRender(true).setTexture("charactor6");
                        findSprite("talkbar").setRender(true).setTouchAble(true);
                        findText("chats").setText("이런, 갑자기 변한 날씨로 곤란한가본데");
                        return;
                    }
                }
            }
        }
        if(noStep&&step==19&&selectArea.happyness!=100&&selectArea.isBuild){
            findSprite("charactor").setTexture("charactor6").setRender(true);
            findSprite("talkbar").setRender(true).setTouchAble(true);
            findText("chats").setText("잠시만 위에 상단의 메뉴좀 주목해 줄래?");
            noStep=false;
            step++;
            return;
        }
        if(noStep&&step==9&&temp&&selectArea!=null){
            if(selectArea.kind==2&&selectArea.isBuild){
                findSprite("talkbar").setRender(true).setTouchAble(true);
                findSprite("charactor").setRender(true).setTexture("charactor6");
                findText("chats").setText("그럼 이제 선택한 동물을 다시 눌러서 대화해볼래?");
                step++;
            }
            else if(selectArea.isBuild==true){
                selectArea=null;
                findSprite("talkbar").setRender(true).setTouchAble(true);
                findSprite("charactor").setRender(true).setTexture("charactor6");
                findText("chats").setText("아니아니 온대지역에 살고 있는 친구에게 말을 걸어야지");
            }
            else {
                selectArea.senani.setTexture("tempIMG");
                selectArea=null;
            }
        }
    }
}

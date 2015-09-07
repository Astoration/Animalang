package us.zeropen.zroid.game;

/**
 * Created by 병걸 on 2015-03-16.
 *
 * ZDefine 은 게임 구성에 필요한 상수값들을 저장하는 클래스입니다
 * ZDefine.변수 로 접근할 수 있지만, 직접 접근하여 값을 수정하는 행위는 권장하지 않습니다
 */
public class ZDefine {
    // 게임화면 가로 길이
    static public int GAME_WIDTH;
    // 게임화면 세로 길이
    static public int GAME_HEIGHT;
    // 게임 FPS
    static public int GAME_FPS = 60;

    /*
    아래는 자동으로 설정되는 값들이니 다른 값을 대입하지는 말자
     */
    static public float GAME_SCALE_X;
    static public float GAME_SCALE_Y;
}

package com.team.before.animalang;

import android.os.Bundle;

import us.zeropen.zroid.game.ZDefine;
import us.zeropen.zroid.game.ZGameActivity;
import us.zeropen.zroid.game.scene.ZScene;

/**
 * Created by Astora on 2015-08-08.
 */
public class GameActivity extends ZGameActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ZDefine.GAME_WIDTH= 1920;
        ZDefine.GAME_HEIGHT= 1080;
        super.onCreate(savedInstanceState);
    }

    @Override
    public ZScene getInitScene() {
        return new TitleScene(this);
    }
}

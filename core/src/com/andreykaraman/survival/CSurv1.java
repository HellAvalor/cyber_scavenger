package com.andreykaraman.survival;

import com.andreykaraman.survival.Game.Assets;
import com.andreykaraman.survival.screens.DirectedGame;
import com.andreykaraman.survival.screens.MainGameScreen;
import com.andreykaraman.survival.screens.transition.ScreenTransition;
import com.andreykaraman.survival.screens.transition.ScreenTransitionFade;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;


/**
 * Created by KaramanA on 25.09.2014.
 */
public class CSurv1 extends DirectedGame {

    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        // Load assets
        Assets.instance.init(new AssetManager());
        // Load preferences for audio settings and start playing music
//        GamePreferences.instance.load();
//        AudioManager.instance.play(Assets.instance.music.song01);
        // Start game at menu screen
        ScreenTransition transition = ScreenTransitionFade.init(2);
        setScreen(new MainGameScreen(this), transition);

    }
}

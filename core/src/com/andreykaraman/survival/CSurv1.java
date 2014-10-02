package com.andreykaraman.survival;

import com.andreykaraman.survival.screens.NewGameScreen;
import com.badlogic.gdx.Game;


/**
 * Created by KaramanA on 25.09.2014.
 */
public class CSurv1 extends Game {

    public NewGameScreen gameScreen;
//    AndroidLauncher app;
//
//    public CSurv1(AndroidLauncher app){
//        this.app = app;
//    }

    public void create() {
        gameScreen = new NewGameScreen(this);
        setScreen(gameScreen);
        gameScreen.preLoad();
    }
}

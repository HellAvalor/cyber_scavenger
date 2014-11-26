package com.andrewkaraman.survival.core;

import com.andrewkaraman.survival.core.actors.AbsActorImpl;
import com.andrewkaraman.survival.core.screens.AbstractScreen;
import com.andrewkaraman.survival.core.screens.ScreenManager;
import com.andrewkaraman.survival.core.screens.Screens;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;

/**
 * Created by KaramanA on 15.10.2014.
 */
public class MyGame extends Game {
    // constant useful for logging
    private final String LOG_CLASS_NAME = this.getClass().getName();

    // a libgdx helper class that logs the current FPS each second
    private FPSLogger fpsLogger;

    public MyGame() {
    }

    @Override
    public void create() {
        ScreenManager.getInstance().initialize(this);
        Gdx.app.log(LOG_CLASS_NAME, "Creating game");
        fpsLogger = new FPSLogger();
        ScreenManager.getInstance().show(Screens.LOADING);
    }

    @Override
    public void resize(
            int width,
            int height) {
        super.resize(width, height);
        Gdx.app.log(LOG_CLASS_NAME, "Resizing game to: " + width + " x " + height);
    }

    @Override
    public void render() {
        super.render();

        // output the current FPS
//        fpsLogger.log();
    }

    @Override
    public void pause() {
        super.pause();
        Gdx.app.log(LOG_CLASS_NAME, "Pausing game");
    }

    @Override
    public void resume() {
        super.resume();
        Gdx.app.log(LOG_CLASS_NAME, "Resuming game");
    }

    public void setScreen(AbstractScreen oldScreen, AbstractScreen newScreen) {

        if (oldScreen != null) {
            Gdx.app.log(LOG_CLASS_NAME, "oldScreen " + oldScreen + "/" + oldScreen.isNeedToSave() + "/" + oldScreen.isHidden() + " newScreen " + newScreen + "/" + newScreen.isNeedToSave() + "/" + newScreen.isHidden());
        }

        if (oldScreen != null && oldScreen.isNeedToSave()){
            oldScreen.pause();
            oldScreen.setHideState(true);
        }

        if (newScreen.isHidden() && newScreen.isNeedToSave()){

            newScreen.resume();
        } else {
            super.setScreen(newScreen);
        }
        newScreen.setHideState(false);
    }

    @Override
    public void dispose() {
        super.dispose();
        ScreenManager.getInstance().dispose();
        Gdx.app.log(LOG_CLASS_NAME, "Disposing game");
    }
}

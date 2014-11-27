package com.andrewkaraman.survival.core;

import com.andrewkaraman.survival.core.screens.AbstractScreen;
import com.andrewkaraman.survival.core.screens.ScreenManager;
import com.andrewkaraman.survival.core.screens.Screens;
import com.badlogic.gdx.Application;
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

    public static GameWorld world; // contains the game world's bodies and actors.

    // a libgdx helper class that logs the current FPS each second
    private FPSLogger fpsLogger;

    public MyGame() {
    }

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_INFO);
        ScreenManager.getInstance().initialize(this);
        Gdx.app.log(LOG_CLASS_NAME, "Creating game");
        fpsLogger = new FPSLogger();
        ScreenManager.getInstance().show(Screens.LOADING);
        world = new GameWorld();
    }

    @Override
    public void resize(
            int width,
            int height) {
        super.resize(width, height);
        Gdx.app.debug(LOG_CLASS_NAME, "Resizing game to: " + width + " x " + height);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void pause() {
        super.pause();
        Gdx.app.debug(LOG_CLASS_NAME, "Pausing game");
    }

    @Override
    public void resume() {
        super.resume();
        Gdx.app.debug(LOG_CLASS_NAME, "Resuming game");
    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
    }

    @Override
    public void dispose() {
        super.dispose();
        ScreenManager.getInstance().dispose();
        Gdx.app.debug(LOG_CLASS_NAME, "Disposing game");
    }
}

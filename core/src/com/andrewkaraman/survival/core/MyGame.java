package com.andrewkaraman.survival.core;

import com.andrewkaraman.survival.core.screens.GameScreen;
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

    // Screen methods

    public GameScreen getGameScreen() {
        return new GameScreen(this);
    }

    // Game methods

    @Override
    public void create() {
        Gdx.app.log(LOG_CLASS_NAME, "Creating game");
        fpsLogger = new FPSLogger();
        setScreen(getGameScreen());
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

    @Override
    public void setScreen(
            Screen screen) {
        super.setScreen(screen);
        Gdx.app.log(LOG_CLASS_NAME, "Setting screen: " + screen.getClass().getSimpleName());
    }

    @Override
    public void dispose() {
        super.dispose();
        Gdx.app.log(LOG_CLASS_NAME, "Disposing game");
    }
}

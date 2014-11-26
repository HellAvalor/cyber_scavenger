package com.andrewkaraman.survival.core.screens;

import com.andrewkaraman.survival.core.MyGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.IntMap;

/**
 * Created by KaramanA on 25.11.2014.
 */
public final class ScreenManager {

    private final String LOG_CLASS_NAME = this.getClass().getName();

    private static ScreenManager instance;
    private IntMap<AbstractScreen> screens;
    private AbstractScreen currentScreen;
    private MyGame game;

    private ScreenManager() {
        screens = new IntMap<AbstractScreen>();
    }

    public static ScreenManager getInstance() {
        if (null == instance) {
            instance = new ScreenManager();
        }
        return instance;
    }

    public void initialize(MyGame game) {
        this.game = game;
    }

    public void show(Screens screen) {

        AbstractScreen oldScreen = currentScreen;
        if (null == game) return;

        if (!screens.containsKey(screen.ordinal())) {
            currentScreen = screen.getScreenInstance();
            screens.put(screen.ordinal(), currentScreen);
        } else {
            currentScreen = screens.get(screen.ordinal());
        }

        if (currentScreen != null) {
            game.setScreen(oldScreen, currentScreen);
        } else {
            Gdx.app.error(LOG_CLASS_NAME, "Null screen");
        }
    }

    public void dispose(Screens screen) {
        if (!screens.containsKey(screen.ordinal()))
            return;
        screens.remove(screen.ordinal()).dispose();
    }

    public void dispose() {
        for (AbstractScreen screen : screens.values()) {
            screen.dispose();
        }
        screens.clear();
        instance = null;
    }
}
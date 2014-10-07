package com.andreykaraman.survival;

import com.andreykaraman.survival.screens.AbstractScreen;
import com.andreykaraman.survival.screens.GameScreen;
import com.andreykaraman.survival.screens.LoadingScreen;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by KaramanA on 07.10.2014.
 */
public class NewScavengerGame extends Game {

    public int GAME_WIDTH = 1024;
    public int GAME_HEIGHT = 600;
    private static NewScavengerGame instance;
    private TextureAtlas textureAtlas;
    private LoadingScreen loadingScreen;
    private AssetManager manager;


    public void create() {

        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        Gdx.app.debug("NewScavengerGame", "init");
        if (getScreen() == null) {
            loadScreen(new GameScreen());
        }
    }

    public static NewScavengerGame getInstance() {
        if (instance == null) {
            instance = new NewScavengerGame();
        }
        return instance;
    }

    public NewScavengerGame() {
        super();
    }

    public AssetManager getManager() {
        if (manager == null) {
            this.manager = new AssetManager();
        }
        return this.manager;
    }


    public TextureRegion getTexture(String name) {
        if (textureAtlas == null) {
           this.textureAtlas = new TextureAtlas(Gdx.files.internal("img/texture.pack"));
        }
        return this.textureAtlas.findRegion(name);
    }

    public LoadingScreen getLoadingScreen() {
        return this.loadingScreen;
    }

    public LoadingScreen getLoadingScreen(AbstractScreen nextScreen) {
        if (this.loadingScreen == null) {
            this.loadingScreen = new LoadingScreen(nextScreen);
        } else {
            this.loadingScreen.setNextScreen(nextScreen);
        }
        return this.loadingScreen;
    }

//    public void loadSavedScreen() {
//        this.loadScreen(this.getGameScreen(this.getSave().getInteger("current_chapter"), this.getSave().getString("current_screen")));
//    }

    public void loadScreen(final AbstractScreen newScreen) {
        this.setScreen(this.getLoadingScreen(newScreen));
        this.getManager().clear();
        newScreen.loadResources();
    }

    public void dispose() {
        super.dispose();
        if (this.getScreen() != this.loadingScreen && this.loadingScreen != null) this.loadingScreen.dispose();
//        if (this.font != null) this.font.dispose();
        if (this.textureAtlas != null) this.textureAtlas.dispose();
//        this.preferences = null;
//        this.chapters = null;
    }

    public AbstractScreen getGameScreen() {
            return new GameScreen();
    }
}

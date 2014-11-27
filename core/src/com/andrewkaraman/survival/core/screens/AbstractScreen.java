package com.andrewkaraman.survival.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by KaramanA on 15.10.2014.
 */
public abstract class AbstractScreen implements Screen {

    public static float SCREEN_WIDTH = Gdx.graphics.getWidth();
    public static float SCREEN_HEIGHT = Gdx.graphics.getHeight();

    private OrthographicCamera guiCam; // camera for the GUI. It's the stage default camera.
    private boolean hideState;

    protected final BitmapFont font;
    protected final SpriteBatch batch;
    protected Skin skin;
    protected Stage stage; // stage that holds the GUI. Pixel-exact size.

    public AbstractScreen() {
        this.font = new BitmapFont();
        this.batch = new SpriteBatch();
    }

    @Override
    public void show() {
        stage = new Stage(); // create the GUI stage
        stage.setViewport(new ScreenViewport()); // set the GUI stage viewport to the pixel size
        stage.setDebugAll(true);
        initSkin();
        init();
    }

    protected void init() {
        guiCam = (OrthographicCamera) stage.getCamera();
        guiCam.position.set(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, 0);
        initUI();
    }

    protected abstract void initUI();

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        guiCam.position.set(width / 2, height / 2, 0);
    }

    @Override
    public void render(float delta) {
        // the following code clears the screen with the given RGB color (black)
        Gdx.gl.glClearColor(0f, 0f, 0.1f, 0.2f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        stage.draw(); // draw the GUI
    }

    protected void initSkin() {
        if (skin == null) {
            FileHandle skinFile = Gdx.files.internal("uiskin.json");
            skin = new Skin(skinFile);
        }
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {

        Gdx.app.debug("AbstractScreen", "dispose screens" );

        if (font != null) font.dispose();
        if (batch != null) batch.dispose();
        if (skin != null) skin.dispose();
        if (stage != null) stage.dispose();
    }

    public void update(float delta){
        guiCam.update();
        stage.act(delta); // update GUI
    };

    public abstract boolean isNeedToSave();

    public boolean isHidden() {
        return hideState;
    }

    public void setHideState(boolean hideState) {
        this.hideState = hideState;
    }
}

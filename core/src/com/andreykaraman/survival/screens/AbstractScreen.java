package com.andreykaraman.survival.screens;

import com.andreykaraman.survival.NewScavengerGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.Viewport;


public class AbstractScreen implements Screen {
    protected Stage stage;
    private Music music;

    public AbstractScreen() {
    }

    public AbstractScreen(Stage stage) {
        this.setStage(stage);
    }

//    public BitmapFont getFont() {
//        return NewScavengerGame.getInstance().getFont();
//    }

    public AssetManager getManager() {
        return NewScavengerGame.getInstance().getManager();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(this.stage);
    }

    public Stage getStage() {
        if (stage == null) {
            this.setStage(new Stage());
        }
        return stage;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public Music getMusic() {
        return this.music;
    }

    public void loadResources() {
        // Do loading
    }


    @Override
    public void render(float delta) {
        getStage().act(delta);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        getStage().draw();
    }


    @Override
    public void hide() {
        //Gdx.app.log(this.getClass().getName(), "hide");
        this.dispose();
    }


    @Override
    public void pause() {
        //Gdx.app.log(this.getClass().getName(), "pause");
    }


    @Override
    public void resume() {
        //Gdx.app.log(this.getClass().getName(), "resume");
    }


    @Override
    public void resize(int width, int height) {
        Vector2 size = Scaling.fit.apply(NewScavengerGame.getInstance().GAME_WIDTH, NewScavengerGame.getInstance().GAME_HEIGHT, width, height);
        int viewportX = (int)(width - size.x) / 2;
        int viewportY = (int)(height - size.y) / 2;
        int viewportWidth = (int)size.x;
        int viewportHeight = (int)size.y;
        Gdx.gl.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);
        Viewport v = this.stage.getViewport();
//        v.setScreenWidth(NewScavengerGame.getInstance().GAME_WIDTH);
//        v.setScreenHeight(NewScavengerGame.getInstance().GAME_HEIGHT);
        v.setWorldSize(NewScavengerGame.getInstance().GAME_WIDTH, NewScavengerGame.getInstance().GAME_HEIGHT);
        v.setScreenSize(viewportWidth,viewportHeight);
        v.setScreenPosition(viewportX, viewportY);
        this.stage.setViewport(v);
        //this.stage.setViewport(NewScavengerGame.getInstance().GAME_WIDTH, NewScavengerGame.getInstance().GAME_HEIGHT, true, viewportX, viewportY, viewportWidth, viewportHeight);
        //Gdx.app.log(this.getClass().getName(), "resize");
    }

    @Override
    public void show() {
        //Gdx.app.log(this.getClass().getName(), "show");
//        if (this.music != null) {
//            this.music.setLooping(true);
//            this.music.setVolume(NewScavengerGame.getInstance().getPreferences().getFloat("musicVolume"));
//            this.music.play();
//        }
    }


    @Override
    public void dispose() {
        if (stage != null) {
            stage.clear();
            stage.dispose();
        }
//        if (this.music != null) {
//            this.music.stop();
//            this.music.dispose();
//        }
        //Gdx.app.log(this.getClass().getName(), "dispose");
    }


}
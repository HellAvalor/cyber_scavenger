package com.andrewkaraman.survival.core.screens;

import com.andrewkaraman.survival.core.MyGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by KaramanA on 15.10.2014.
 */
public abstract class AbstractScreen implements Screen {

    protected final MyGame game;
    protected final BitmapFont font;
    protected final SpriteBatch batch;

    public AbstractScreen(MyGame game) {
        this.game = game;
        this.font = new BitmapFont();
        this.batch = new SpriteBatch();
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(
            int width,
            int height) {
    }

    @Override
    public void render(float delta) {
        // the following code clears the screen with the given RGB color (black)
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
        font.dispose();
        batch.dispose();
    }
}

package com.andrewkaraman.survival.core.screens;

import com.andrewkaraman.survival.core.MyGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by KaramanA on 15.10.2014.
 */
abstract class AbstractScreen implements Screen {

//    protected final MyGame game;
    protected final BitmapFont font;
    protected final SpriteBatch batch;
    private Skin skin;

    public AbstractScreen(/*MyGame game*/) {
//        this.game = game;
        this.font = new BitmapFont();
        this.batch = new SpriteBatch();
    }

    @Override
    public void show() { }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void render(float delta) {
        // the following code clears the screen with the given RGB color (black)
        Gdx.gl.glClearColor(0f, 0f, 0.1f, 0.2f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        draw(delta);
    }

    protected Skin getSkin()
    {
        if( skin == null ) {
            FileHandle skinFile = Gdx.files.internal( "uiskin.json" );
            skin = new Skin( skinFile );
        }
        return skin;
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
        if( font != null ) font.dispose();
        if( batch != null ) batch.dispose();
        if( skin != null ) skin.dispose();
    }

    public abstract void update(float delta);

    public abstract void draw (float delta);

    public abstract String getScreenIdentifier();

    public abstract boolean isStickyScreen();
}

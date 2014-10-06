package com.andreykaraman.survival.screens;

import com.andreykaraman.survival.Game.Assets;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;

/**
 * Created by Andrew on 05.10.2014.
 */
public abstract class AbstractGameScreen implements Screen {
    protected DirectedGame game;


    public AbstractGameScreen(DirectedGame  game) {
        this.game = game;
    }

    public abstract void render(float delta);


    public abstract void resize(int width, int height);


    public abstract void show();


    public abstract void hide();


    public abstract void pause();


    public abstract InputProcessor getInputProcessor ();

    @Override
    public void resume() {
        Assets.instance.init(new AssetManager());
    }


    @Override
    public void dispose() {
        Assets.instance.dispose();
    }
}

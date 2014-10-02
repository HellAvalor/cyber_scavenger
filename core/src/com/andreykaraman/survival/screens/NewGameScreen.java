package com.andreykaraman.survival.screens;

import com.andreykaraman.survival.CSurv1;
import com.andreykaraman.survival.model.NewWorld;
import com.andreykaraman.survival.view.NewWorldRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

/**
 * Created by KaramanA on 02.10.2014.
 */
public class NewGameScreen implements Screen, InputProcessor {

    CSurv1 game;
    private NewWorldRenderer renderer;
    private NewWorld world;
    private int width, height;

    public NewGameScreen(CSurv1 cSurv1) {
        this.game = cSurv1;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0, 118F/255, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
    }

    @Override
    public void resize(int width, int height) {
        renderer.setSize(width, height);
        this.width = width;
        this.height = height;
    }

    public void preLoad(){
        renderer = new NewWorldRenderer();
    }

    @Override
    public void show() {
        float CAMERA_WIDTH = NewWorldRenderer.CAMERA_WIDTH;
        float CAMERA_HEIGHT = NewWorldRenderer.CAMERA_HEIGHT;
        CAMERA_WIDTH =  CAMERA_HEIGHT* Gdx.graphics.getWidth()/Gdx.graphics.getHeight();
        world = new NewWorld();
        renderer.init(world, CAMERA_WIDTH,CAMERA_HEIGHT,false);
        Gdx.input.setInputProcessor(this);
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
        renderer.dispose();
        Gdx.input.setInputProcessor(null);
        Gdx.input.setCatchBackKey(false);
    }
}

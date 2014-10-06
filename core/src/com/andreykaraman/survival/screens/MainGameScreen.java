package com.andreykaraman.survival.screens;

import com.andreykaraman.survival.view.ANewWorldController;
import com.andreykaraman.survival.view.ANewWorldRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;

/**
 * Created by Andrew on 05.10.2014.
 */
public class MainGameScreen extends AbstractGameScreen {
    private static final String TAG = MainGameScreen.class.getName();


    private ANewWorldController worldController;
    private ANewWorldRenderer worldRenderer;
    private boolean paused;

    public MainGameScreen(DirectedGame game) {
        super(game);
    }


    @Override
    public void render(float deltaTime) {
        // Do not update game world when paused.
        if(!paused) {
            // Update game world by the time that has passed
            // since last rendered frame.
            worldController.update(deltaTime);
        }

        // Set clear screen color to: CornFlower blue
        Gdx.gl.glClearColor(0x64/255.f, 0x95/255.f, 0xed/255.f, 0xff/255.f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Render game world to screen
        worldRenderer.render();
    }


    @Override
    public void resize(int width, int height) {
        worldRenderer.resize(width, height);
    }


    @Override
    public void show() {
        // Initialize controller and renderer
        worldController = new ANewWorldController(game);
        worldRenderer = new ANewWorldRenderer(worldController);
        Gdx.input.setCatchBackKey(true);
    }


    @Override
    public void hide() {
        worldRenderer.dispose();
        worldController.dispose();
        Gdx.input.setCatchBackKey(false);
    }


    @Override
    public void pause() {
        paused = true;
    }


    @Override
    public void resume() {
        super.resume();
        // Only called on Android!
        paused = false;
    }


    @Override
    public InputProcessor getInputProcessor() {
        return worldController;
    }
}

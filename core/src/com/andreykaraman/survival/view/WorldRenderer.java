package com.andreykaraman.survival.view;

import com.andreykaraman.survival.Game.Assets;
import com.andreykaraman.survival.utils.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Andrew on 05.10.2014.
 */
public class WorldRenderer implements Disposable {
    private static final String TAG = WorldRenderer.class.getName();
    private WorldController worldController;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private OrthographicCamera cameraGUI;
    private static final boolean DEBUG = true;
    private Stage stage;

    public WorldRenderer(WorldController worldController) {
        this.worldController = worldController;
        init();
    }

    private void init() {

        batch = new SpriteBatch();
        stage = new Stage();

//        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
//        camera.position.set(0, 0, 0);
//        camera.setToOrtho(true);
//        camera.update();

//        cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
//        cameraGUI.position.set(0, 0, 0);
//        cameraGUI.setToOrtho(true);
//        cameraGUI.update();
    }

    public void render() {
        renderWorld(batch);
//        renderGui(batch);
//        if (DEBUG) {
//            renderDebug(batch);
//        }
    }

    private void renderGui(SpriteBatch batch) {
        batch.setProjectionMatrix(cameraGUI.combined);
        batch.begin();
        renderGuiFpsCounter(batch);
        batch.end();
    }

    private void renderDebug(SpriteBatch batch) {
        batch.setProjectionMatrix(cameraGUI.combined);
        batch.begin();
        float x = cameraGUI.viewportWidth - 130;
        float y = cameraGUI.viewportHeight - 35;

        BitmapFont fpsFont = Assets.instance.fonts.defaultNormal;

//        fpsFont.draw(batch, "coord: x " + (int) camera.position.x + " y " + (int) camera.position.y, x, y);
//        fpsFont.setColor(1, 1, 1, 1);

        batch.end();
    }

    private void renderGuiFpsCounter(SpriteBatch batch) {
        float x = cameraGUI.viewportWidth - 55;
        float y = cameraGUI.viewportHeight - 15;
        int fps = Gdx.graphics.getFramesPerSecond();
        BitmapFont fpsFont = Assets.instance.fonts.defaultNormal;
        if (fps >= 45) {
            // 45 or more FPS show up in green
            fpsFont.setColor(0, 1, 0, 1);
        } else if (fps >= 30) {
            // 30 or more FPS show up in yellow
            fpsFont.setColor(1, 1, 0, 1);
        } else {
            // less than 30 FPS show up in red
            fpsFont.setColor(1, 0, 0, 1);
        }
        fpsFont.draw(batch, "FPS: " + fps, x, y);
        fpsFont.setColor(1, 1, 1, 1);
    }

    private void renderWorld(SpriteBatch batch) {
//        worldController.cameraHelper.applyTo(camera);
//        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        worldController.level.render(batch);
        batch.setShader(null);
        batch.end();
    }

    public void resize(int width, int height) {
//        camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
//        camera.update();
//        cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
//        cameraGUI.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT / (float) height) * (float) width;
//        cameraGUI.position.set(cameraGUI.viewportWidth / 2, cameraGUI.viewportHeight / 2, 0);
//        cameraGUI.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
    }
}
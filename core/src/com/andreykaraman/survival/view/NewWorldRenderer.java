package com.andreykaraman.survival.view;

import com.andreykaraman.survival.model.NewWorld;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;

/**
 * Created by KaramanA on 02.10.2014.
 */
public class NewWorldRenderer {

    public static float CAMERA_WIDTH = 10f;
    public static float CAMERA_HEIGHT = 14f;
    public OrthographicCamera cam;
    private SpriteBatch spriteBatch;

    private boolean debug = false;
    /**
     * for debug rendering *
     */
    ShapeRenderer debugRenderer = new ShapeRenderer();
    ShapeRenderer bgRenderer = new ShapeRenderer();
    /**
     * Textures *
     */
    private NewWorld world;

    public int width;
    public int height;
    public float ppuX;    // pixels per unit on the X axis
    public float ppuY;    // pixels per unit on the Y axis

    public void setSize(int w, int h) {
        this.width = w;
        this.height = h;
        ppuX = (float) width / CAMERA_WIDTH;
        ppuY = (float) height / CAMERA_HEIGHT;
    }

    public void SetCamera(float x, float y) {
        this.cam.position.set(x, y, 0);
        this.cam.update();
    }

    public void init(NewWorld world, float w, float h, boolean debug) {

        CAMERA_WIDTH = w;
        CAMERA_HEIGHT = h;
        this.world = world;
        this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
        SetCamera(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f);
        this.cam.update();

        this.debug = debug;
        spriteBatch = new SpriteBatch();
    }

    public void render() {

        spriteBatch.begin();
        spriteBatch.disableBlending();
//---------------        draw here            --------------
        spriteBatch.enableBlending();
        spriteBatch.end();

        if (debug) drawDebug();
    }

    private void drawDebug() {
        debugRenderer.setProjectionMatrix(cam.combined);
        debugRenderer.setColor(new Color(0, 1, 0, 1));
        debugRenderer.end();
    }

    public void dispose() {
        try {
            spriteBatch.dispose();
            debugRenderer.dispose();
            bgRenderer.dispose();
            world.dispose();
        } catch (Exception e) {

        }

    }
}

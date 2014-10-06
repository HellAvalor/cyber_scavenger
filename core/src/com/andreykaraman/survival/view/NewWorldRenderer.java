package com.andreykaraman.survival.view;

import com.andreykaraman.survival.model.NewWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by KaramanA on 02.10.2014.
 */
public class NewWorldRenderer {

    public static float cameraWidthTiles = 10f;
    public static float cameraHeightTiles = 14f;
    public OrthographicCamera cam;
    private SpriteBatch spriteBatch;
    FPSLogger fps = new FPSLogger();

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
        ppuX = (float) width / cameraWidthTiles;
        ppuY = (float) height / cameraHeightTiles;
    }

    public void SetCamera(float x, float y) {
        this.cam.position.set(x, y, 0);
        this.cam.update();
    }

    public void init(NewWorld world, float w, float h, boolean debug) {

        cameraWidthTiles = w;
        cameraHeightTiles = h;
        this.world = world;
        this.cam = new OrthographicCamera(cameraWidthTiles, cameraHeightTiles);
        SetCamera(cameraWidthTiles / 2f, cameraHeightTiles / 2f);
        this.cam.update();
        this.debug = debug;
        spriteBatch = new SpriteBatch();

    }

    public void render() {
        processKeys();
        cam.update();

        spriteBatch.begin();
        drawMap();
        if (debug) {
            drawDebug();
        }
//        spriteBatch.disableBlending();
////---------------        draw here            --------------
//        spriteBatch.enableBlending();
        spriteBatch.end();
        fps.log();
//        if (debug) drawDebug();
    }


    private void processKeys () {

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
           cam.position.set(cam.position.x - 20, cam.position.y, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            cam.position.set(cam.position.x + 20, cam.position.y, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            cam.position.set(cam.position.x, cam.position.y+20, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            cam.position.set(cam.position.x, cam.position.y-20, 0);
        }
    }


    private void drawMap() {

        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 24; j++) {
                TextureRegion texture = world.getMap()[i][j].getTextureRegion();
                spriteBatch.draw(texture, i * ppuX, j * ppuY);
            }
        }

//        int i = 0;
//        for (Brick brick : world.getBricks()) {
//        //ради интереса для отрисовки используем разные изображения (регионы)
//            spriteBatch.draw(textureRegions.get("brick" + (i % 3 + 1)), brick.getPosition().x * ppuX, brick.getPosition().y * ppuY, Brick.SIZE * ppuX, Brick.SIZE * ppuY);
//            ++i;
//        }

    }

    private void drawDebug() {
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

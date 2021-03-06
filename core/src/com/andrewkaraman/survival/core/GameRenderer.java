package com.andrewkaraman.survival.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

/**
 * Created by Andrew on 26.10.2014.
 */
public class GameRenderer {
    GameWorld world;
    OrthographicCamera camera;
    Box2DDebugRenderer renderer;

    public GameRenderer(GameWorld world)
    {
        this.world = world;
        this.renderer = new Box2DDebugRenderer();

        // we obtain a reference to the game stage camera. The camera is scaled to box2d meter units
        this.camera = (OrthographicCamera) world.stage.getCamera();

        // center the camera on newPlayer (optional)
        camera.position.x = world.player.getX();
        camera.position.y = world.player.getY();
    }

    public void render()
    {
        // have the camera follow newPlayer
        camera.position.x = world.player.getX();
        camera.position.y = world.player.getY();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // box2d debug renderering (optional)
        camera.update();
        renderer.render(world.box2dWorld, camera.combined);

        // game stage rendering
        world.stage.draw();
    }

    public void resize(){
        this.camera = (OrthographicCamera) world.stage.getCamera();

        // center the camera on newPlayer (optional)
        camera.position.x = world.player.getX();
        camera.position.y = world.player.getY();
    }
}

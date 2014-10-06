package com.andreykaraman.survival.utils;

import com.andreykaraman.survival.model.Objects.AbstractGameObject;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Andrew on 05.10.2014.
 */
public class CameraHelper {
    private static final String TAG = CameraHelper.class.getName();
    private final float MAX_ZOOM_IN = 0.25f;
    private final float MIN_ZOOM_OUT = 10.f;
    private Vector2 position;
    private float zoom;
    private AbstractGameObject target;
    private final float FOLLOW_SPEED = 4.0f;

    public CameraHelper() {
        position = new Vector2();
        zoom = 1.0f;
    }

    public CameraHelper(float x, float y) {
        position = new Vector2(x, y);
        zoom = 1.0f;
    }

    public void update(double deltaTime) {
        if(!hasTarget())
            return;
        position.lerp(target.position, FOLLOW_SPEED*(float)deltaTime);
        // prevent camera from moving down too far
        //position.y = Math.max(-1f, position.y);
    }


    public boolean hasTarget() {
        return target != null;
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void addZoom(float amount) {
        setZoom(zoom + amount);
    }

    public void setZoom(float amount) {
        zoom = MathUtils.clamp(amount, MAX_ZOOM_IN, MIN_ZOOM_OUT);
    }

    public float getZoom() {
        return zoom;
    }

    public void setTarget(AbstractGameObject target) {
        this.target = target;
    }

    public AbstractGameObject getTarget() {
        return target;
    }

    public boolean hasTarget(AbstractGameObject target) {
        return this.target != null && this.target.equals(target);
    }

    public void applyTo(OrthographicCamera camera) {
        chechBorgers(camera);
        camera.position.x = position.x;
        camera.position.y = position.y;
        camera.zoom = zoom;
        camera.update();
    }

    private void chechBorgers (OrthographicCamera camera){
        if (position.x < camera.viewportWidth / 2) position.x = camera.viewportWidth / 2;
        if (position.y < camera.viewportHeight/ 2) position.y = camera.viewportHeight / 2;
        if (position.x > Constants.TEXTURE_TILE_SIZE*(Constants.MAP_SIZE) - camera.viewportWidth / 2) position.x = Constants.TEXTURE_TILE_SIZE*(Constants.MAP_SIZE)- camera.viewportWidth / 2;
        if (position.y > Constants.TEXTURE_TILE_SIZE*(Constants.MAP_SIZE)- camera.viewportHeight/ 2) position.y = Constants.TEXTURE_TILE_SIZE*(Constants.MAP_SIZE) - camera.viewportHeight / 2;
    }
}
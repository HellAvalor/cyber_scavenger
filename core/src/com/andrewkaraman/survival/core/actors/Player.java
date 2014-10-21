package com.andrewkaraman.survival.core.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by KaramanA on 15.10.2014.
 */
public class Player extends Actor {

    Texture region;
    Stage stage;
    private long shootingSpeed = 100000000;
    private long lastBulletTime;

    boolean moveRight;
    boolean moveLeft;
    boolean moveUp;
    boolean moveDown;
    boolean isShooting;

    float horisontalSpeed;
    float verticalSpeed;

    float velocity = 2;
    private final String LOG_CLASS_NAME = this.getClass().getName();
    private static final float MAX_MOVEMENT_SPEED = 5;
    private static final float MIN_MOVEMENT_SPEED = 0.1f;

    public Player(Stage stage) {
        this.stage = stage;
        region = new Texture(Gdx.files.internal("ship-model.png"));
        lastBulletTime = TimeUtils.nanoTime();
        isShooting = false;
    }

    public long getShootingSpeed() {
        return shootingSpeed;
    }

    public void setShootingSpeed(long shootingSpeed) {
        this.shootingSpeed = shootingSpeed;
    }

    public long getLastBulletTime() {
        return lastBulletTime;
    }

    public void setLastBulletTime(long lastBulletTime) {
        this.lastBulletTime = lastBulletTime;
    }

    public boolean isShooting() {
        return isShooting;
    }

    public void setShooting(boolean isShooting) {
        this.isShooting = isShooting;
    }

    public boolean isMoveRight() {
        return moveRight;
    }

    public void setMoveRight(boolean moveRight) {
        if (moveLeft && moveRight) moveLeft = false;
        this.moveRight = moveRight;
    }

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(boolean moveLeft) {
        if (moveLeft && moveRight) moveRight = false;
        this.moveLeft = moveLeft;
    }

    public boolean isMoveUp() {
        return moveUp;
    }

    public void setMoveUp(boolean moveUp) {
        if (moveUp && moveDown) moveDown = false;
        this.moveUp = moveUp;
    }

    public boolean isMoveDown() {
        return moveDown;
    }

    public void setMoveDown(boolean moveDown) {
        if (moveUp && moveDown) moveUp = false;
        this.moveDown = moveDown;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        updateMotion();
        batch.draw(region, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public Touchable getTouchable() {
        return super.getTouchable();
    }

    @Override
    public float getWidth() {
        return region.getWidth();
    }

    @Override
    public float getHeight() {
        return region.getHeight();
    }

    private void updateMotion() {
        if (isMoveLeft()) {
            horisontalSpeed -= velocity * Gdx.graphics.getDeltaTime();
        }
        if (isMoveRight()) {
            horisontalSpeed += velocity * Gdx.graphics.getDeltaTime();
        }
        if (Math.abs(horisontalSpeed) > MAX_MOVEMENT_SPEED) {
            horisontalSpeed = Math.signum(horisontalSpeed) * MAX_MOVEMENT_SPEED;
        }

        if (!isMoveLeft() && !isMoveRight()) {
            if ((Math.abs(horisontalSpeed) > MIN_MOVEMENT_SPEED)) {
                horisontalSpeed += -Math.signum(horisontalSpeed) * velocity * Gdx.graphics.getDeltaTime();
            } else {
                horisontalSpeed = 0;
            }
        }

        if (isMoveUp()) {
            verticalSpeed += velocity * Gdx.graphics.getDeltaTime();
        }
        if (isMoveDown()) {
            verticalSpeed -= velocity * Gdx.graphics.getDeltaTime();
        }

        if (Math.abs(verticalSpeed) > MAX_MOVEMENT_SPEED) {
            verticalSpeed = Math.signum(verticalSpeed) * MAX_MOVEMENT_SPEED;
        }

        if (!isMoveUp() && !isMoveDown()) {
            if ((Math.abs(verticalSpeed) > MIN_MOVEMENT_SPEED)) {
                verticalSpeed += -Math.signum(verticalSpeed) * velocity * Gdx.graphics.getDeltaTime();
            } else {
                verticalSpeed = 0;
            }
        }
        moveBy(horisontalSpeed, verticalSpeed);

    }

    public float getVerticalSpeed() {
        return verticalSpeed;
    }

    public void setVerticalSpeed(float verticalSpeed) {
        this.verticalSpeed = verticalSpeed;
    }

    public float getHorisontalSpeed() {
        return horisontalSpeed;
    }

    public void setHorisontalSpeed(float horisontalSpeed) {
        this.horisontalSpeed = horisontalSpeed;
    }

}

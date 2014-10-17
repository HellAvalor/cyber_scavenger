package com.andrewkaraman.survival.core.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Created by KaramanA on 15.10.2014.
 */
public class Player extends Actor {

    InputListener listener;
    Texture region;

    boolean moveRight;
    boolean moveLeft;
    boolean moveUp;
    boolean moveDown;

    float horisontalSpeed;



    float verticalSpeed;

    float velocity = 2;
    private final String LOG_CLASS_NAME = this.getClass().getName();
    private static final float MAX_MOVEMENT_SPEED = 5;
    private static final float MIN_MOVEMENT_SPEED = 0.1f;

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

    public Player() {
        region = new Texture(Gdx.files.internal("ship-model.png"));
        listener = new CustomListener();
        addListener(listener);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        updateMotion();
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(region, getX(), getY(), getWidth() * 5, getHeight() * 5);

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

    public class CustomListener extends InputListener {

        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.log(LOG_CLASS_NAME, "down " + x + " / " + y);
//                actor.moveBy(10,10);
            return true;
        }

        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.log(LOG_CLASS_NAME, "up");
        }

        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            Gdx.app.log(LOG_CLASS_NAME, "touchDragged " + x + " / " + y + " actor " + getCenterX() + " / " + getCenterY());
            setCenterPosition(x, y);
        }


        @Override
        public boolean keyDown(InputEvent event, int keycode) {

            Gdx.app.log(LOG_CLASS_NAME, "keyDown " + Input.Keys.toString(keycode));

            switch (keycode) {
                case Input.Keys.LEFT:
                    setMoveLeft(true);
                    break;

                case Input.Keys.RIGHT:
                    setMoveRight(true);
                    break;

                case Input.Keys.UP:
                    setMoveUp(true);
                    break;

                case Input.Keys.DOWN:
                    setMoveDown(true);
                    break;
            }
            return false;
        }

        @Override
        public boolean keyUp(InputEvent event, int keycode) {
            Gdx.app.log(LOG_CLASS_NAME, "keyup " + Input.Keys.toString(keycode));
            switch (keycode) {
                case Input.Keys.LEFT:
                    setMoveLeft(false);
                    break;

                case Input.Keys.RIGHT:
                    setMoveRight(false);
                    break;

                case Input.Keys.UP:
                    setMoveUp(false);
                    break;

                case Input.Keys.DOWN:
                    setMoveDown(false);
                    break;
            }
            return false;
        }
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

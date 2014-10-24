package com.andrewkaraman.survival.core.actors;

import com.andrewkaraman.survival.core.WorldProcessor;
import com.andrewkaraman.survival.core.screens.GameScreenBox;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.TimeUtils;

import aurelienribon.bodyeditor.BodyEditorLoader;

/**
 * Created by KaramanA on 15.10.2014.
 */
public class Player extends Actor {

    private World world;
    TextureRegion region;

    private long shootingSpeed = 100000000;
    private long lastBulletTime;
    private Body body = null;

    boolean moveRight;
    boolean moveLeft;
    boolean moveUp;
    boolean moveDown;
    boolean isShooting;

    float horisontalSpeed;
    float verticalSpeed;

    float velocity = 5;
    private final String LOG_CLASS_NAME = this.getClass().getName();
    private static final float MAX_MOVEMENT_SPEED = 5;
    private static final float MIN_MOVEMENT_SPEED = 0.1f;

    public Player(World world) {
        this(world, 0, 0);
    }

    public Player(World world, int x, int y) {
        this.world = world;

        region = new TextureRegion(new Texture(Gdx.files.internal("ship-model.png")));
        lastBulletTime = TimeUtils.nanoTime();
        isShooting = false;

        setPosition(WorldProcessor.WORLD_WIDTH / 2, WorldProcessor.WORLD_HEIGHT / 2);
        setOrigin(getWidth()/2, getHeight()/2);

        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("testPhysSettings.json"));

        // 1. Create a BodyDef, as usual.
        BodyDef bd = new BodyDef();
        bd.position.set(getX(), getY());
        bd.type = BodyDef.BodyType.DynamicBody;


        // 2. Create a FixtureDef, as usual.
        FixtureDef fd = new FixtureDef();
        fd.friction = 0.1f;
        fd.restitution = 0.3f;
//        fd.density = 5;

//        if (body!=null) removeBodySafely(body);

        // 3. Create a Body, as usual.
        body = world.createBody(bd);
//        body.setLinearVelocity(0f, 0f);
        // 4. Create the body fixture automatically by using the loader.
        loader.attachFixture(body, "player-ship", fd, 2);

        setWidth(2);
        setHeight(2);

        Gdx.app.log(LOG_CLASS_NAME, "Player generated at " + getX() + " / " + getY());
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
        batch.draw(region, getX(), getY(), 0, 0, getWidth(), getHeight(),
                1, 1, getRotation());

        //Not go over camera by X
//        if (getX() < 0) setX(0);
//        else if (getX() > GameScreenBox.WORLD_WIDTH) setX(GameScreenBox.WORLD_WIDTH - getWidth());

//        if (fallingManState == FallingManState.Falling) {
//
//
//            TextureRegion keyFrame = Assets.fallingManAnim
//                    .getKeyFrame(stateTime, true);
//
//
//            batch.draw(keyFrame, getX(), getY(), 0, 0, getWidth(), getHeight(),
//                    1, 1, getRotation());
//
//
//        } else if (fallingManState == FallingManState.Splashed) {
//
//
//            batch.draw(Assets.fallingManSplash, getX(), getY(), 0, 0, getWidth(),
//                    getHeight(), 1, 1, getRotation());
//
//
//        }

    }

    @Override
    public Touchable getTouchable() {
        return super.getTouchable();
    }


    private void updateMotion() {

        if (isMoveLeft()) {
            body.applyForceToCenter(-velocity, 0, true);
        }
        if (isMoveRight()) {
            body.applyForceToCenter(velocity, 0, true);
        }

        if (isMoveUp()) {
            body.applyForceToCenter(0, velocity, true);

        }
        if (isMoveDown()) {
            body.applyForceToCenter(0, -velocity, true);
        }

        setRotation(MathUtils.radiansToDegrees * body.getAngle());
        // TODO fix origin position of texture setOrigin();
        setPosition(body.getPosition().x, body.getPosition().y);
        Gdx.app.log(LOG_CLASS_NAME, "Player texture position at " + getCenterX() + " / " + getCenterY() +" body " + body.getPosition().x +" / "+ body.getPosition().y);
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

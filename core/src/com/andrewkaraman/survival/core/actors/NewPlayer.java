package com.andrewkaraman.survival.core.actors;

import com.andrewkaraman.survival.core.GameWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.TimeUtils;

import aurelienribon.bodyeditor.BodyEditorLoader;

/**
 * Created by Andrew on 26.10.2014.
 */
public class NewPlayer extends Image {
    private final String LOG_CLASS_NAME = this.getClass().getName();
    public final Body body; // newPlayer's box2d body
    private final int SHIP_WIDTH = 1;
    float DEG_TO_RAD =0.017453292519943295769236907684886f;
    private final float MAX_SPEED = 10;
    private long shootingSpeed = 100000000;
    private long lastBulletTime;
    public float angle;

    boolean isShooting;

    boolean rotating;
    boolean moveRight;
    boolean moveLeft;
    boolean moveUp;
    boolean moveDown;
    float velocity = 5;

    public NewPlayer(GameWorld world) {
        lastBulletTime = TimeUtils.nanoTime();
        // newPlayer is an Image, so we load the graphics from the assetmanager
//            Texture tex = Assets.manager.get("characters.png", Texture.class);
        Texture tex = new Texture(Gdx.files.internal("ship-model.png"));
//            this.setDrawable(new TextureRegionDrawable(new TextureRegion(tex, 0, 256, 128, 128)));
        this.setDrawable(new TextureRegionDrawable(new TextureRegion(tex)));

        // generate newPlayer's box2d body
//        CircleShape circle = new CircleShape();
//        circle.setRadius(RADIUS);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.x = 0;
        bodyDef.position.y = 0;
        bodyDef.linearDamping = 0.1f;
        bodyDef.angularDamping = 0.1f;
        bodyDef.angle = angle;


        this.body = world.box2dWorld.createBody(bodyDef);
//            this.body.setUserData(ElementType.BOB);
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("testPhysSettings.json"));

        FixtureDef fd = new FixtureDef();
        fd.friction = 0.1f;
        fd.restitution = 0.3f;
        fd.density = 2;

        body.getMassData().center.set(SHIP_WIDTH/2, SHIP_WIDTH*(tex.getHeight()/tex.getWidth()) /2);
//        Fixture fix = body.createFixture(circle, 50);
//        fix.setDensity(1);
//        fix.setFriction(1f);
//        fix.setRestitution(0.8f);
//            fix.setFilterData(filter);
        loader.attachFixture(body, "player-ship", fd, 1);

        // generate newPlayer's actor
        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y- getHeight()/2); // set the actor position at the box2d body position
        setSize(SHIP_WIDTH, SHIP_WIDTH * (tex.getHeight() / tex.getWidth())); // scale actor to body's size
        setScaling(Scaling.stretch); // stretch the texture
//        setAlign(Align.center);
//        setOrigin(SHIP_WIDTH/2, SHIP_WIDTH*(tex.getHeight()/tex.getWidth()) /2);
    }

    @Override
    public void act(float delta) {
        // here we override Actor's act() method to make the actor follow the box2d body
        super.act(delta);
        updateMotion();

        setRotation(MathUtils.radiansToDegrees * body.getAngle());
        setPosition(body.getPosition().x, body.getPosition().y);
//        Gdx.app.log(LOG_CLASS_NAME, "Player texture position at " + getX() + " / " + getY() +" body " + body.getPosition().x +" / "+ body.getPosition().y);
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

        //TODO idea for max speed
        Vector2 velocity = body.getLinearVelocity();
        float speed = velocity.len();
        if (speed > MAX_SPEED) {
            body.setLinearVelocity(velocity.scl(MAX_SPEED / speed));
        }

        checkNeedRotating();
        updateAngle();
//        Gdx.app.log(LOG_CLASS_NAME, "Player texture position at " + getX() + " / " + getY() +" body " + body.getPosition().x +" / "+ body.getPosition().y);


    }


    private void updateAngle() {
        if (rotating) {
//        angle = body.getLinearVelocity().x / (float) Math.sqrt(Math.pow(body.getLinearVelocity().x, 2) + (Math.pow(body.getLinearVelocity().y, 2)));
//
//        int y = 5;
//        int x = 5;
            float desiredAngle = (float) Math.atan2(-body.getLinearVelocity().x, body.getLinearVelocity().y);
//        float desiredAngle = (float) Math.atan2( -y, x );
            float nextAngle = body.getAngle() + body.getAngularVelocity() / 60f;
            float totalRotation = desiredAngle - nextAngle;
            while (totalRotation < -180 * DEG_TO_RAD) totalRotation += 360 * DEG_TO_RAD;
            while (totalRotation > 180 * DEG_TO_RAD) totalRotation -= 360 * DEG_TO_RAD;
            float desiredAngularVelocity = totalRotation * 60;
            float change = 10 * DEG_TO_RAD; //allow 1 degree rotation per time step
            desiredAngularVelocity = Math.min(change, Math.max(-change, desiredAngularVelocity));
            float impulse = body.getAngularDamping() * desiredAngularVelocity;
//        body.applyAngularImpulse(impulse, true);
            body.setTransform(body.getPosition(), body.getAngle() + impulse);
        }
    }

    public boolean isRotating() {
        return rotating;
    }

    public void setRotating(boolean rotating) {
        this.rotating = rotating;
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

    public boolean isShooting() {
        return isShooting;
    }

    public void setShooting(boolean isShooting) {
        this.isShooting = isShooting;
    }


    public long getLastBulletTime() {
        return lastBulletTime;
    }

    public void setLastBulletTime(long lastBulletTime) {
        this.lastBulletTime = lastBulletTime;
    }

    public long getShootingSpeed() {
        return shootingSpeed;
    }

    public void setShootingSpeed(long shootingSpeed) {
        this.shootingSpeed = shootingSpeed;
    }

    public void stop(){
        body.setLinearVelocity(0,0);
    }

    private void checkNeedRotating(){
       setRotating(moveDown || moveLeft ||moveRight || moveUp);
    }
}



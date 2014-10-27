package com.andrewkaraman.survival.core.actors;

import com.andrewkaraman.survival.core.GameWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
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
    public static final float RADIUS = 0.5f; // newPlayer is a ball with 0.8m diameter
    public final Body body; // newPlayer's box2d body
    private final int SHIP_WIDTH = 1;


    private long shootingSpeed = 100000000;
    private long lastBulletTime;

    boolean isShooting;
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
        bodyDef.position.x = 4f;
        bodyDef.position.y = 4f;
        bodyDef.linearDamping = 0.1f;
        bodyDef.angularDamping = 0.5f;

        this.body = world.box2dWorld.createBody(bodyDef);
//            this.body.setUserData(ElementType.BOB);

        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("testPhysSettings.json"));

        FixtureDef fd = new FixtureDef();
        fd.friction = 0.1f;
        fd.restitution = 0.3f;

//        Fixture fix = body.createFixture(circle, 50);
//        fix.setDensity(1);
//        fix.setFriction(1f);
//        fix.setRestitution(0.8f);
//            fix.setFilterData(filter);
        loader.attachFixture(body, "player-ship", fd, 1);

        // generate newPlayer's actor
        setPosition(body.getPosition().x, body.getPosition().y); // set the actor position at the box2d body position
        setSize(SHIP_WIDTH, SHIP_WIDTH * (tex.getHeight() / tex.getWidth())); // scale actor to body's size
        setScaling(Scaling.stretch); // stretch the texture
        setAlign(Align.center);
        setOrigin(SHIP_WIDTH/2, SHIP_WIDTH*(tex.getHeight()/tex.getWidth()) /2);
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

//        Gdx.app.log(LOG_CLASS_NAME, "Player texture position at " + getX() + " / " + getY() +" body " + body.getPosition().x +" / "+ body.getPosition().y);
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
}



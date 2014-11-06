package com.andrewkaraman.survival.core.actors;

import com.andrewkaraman.survival.core.GameWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.TimeUtils;

import aurelienribon.bodyeditor.BodyEditorLoader;

import static com.badlogic.gdx.math.MathUtils.atan2;

/**
 * Created by Andrew on 26.10.2014.
 */
public class NewPlayer extends Image {
    private final String LOG_CLASS_NAME = this.getClass().getName();
    public final Body body; // newPlayer's box2d body
    private final int SHIP_WIDTH = 1;

    public final static int TURN_LEFT = 1;
    public final static int TURN_RIGHT = -1;
    public final static int SPEED_UP = 1;
    public final static int STOP = 0;
    public final static int FORCE_STOP = -1;
    public float angleDiff;

    float DEG_TO_RAD = 0.017453292519943295769236907684886f;
    private final float MAX_SPEED = 10;
    private final float THRUST = 5;
    private long shootingSpeed = 100000000;
    private long lastBulletTime;
    public float angle;
    float directionX;
    float directionY;

    boolean isShooting;

    Vector2 shootingPoint;

    int turning;
    int speedUp;


    boolean rotating;

    public NewPlayer(GameWorld world) {
        lastBulletTime = TimeUtils.nanoTime();
        // newPlayer is an Image, so we load the graphics from the assetmanager
//            Texture tex = Assets.manager.get("characters.png", Texture.class);
        Texture tex = new Texture(Gdx.files.internal("ship-model.png"));
        this.setDrawable(new TextureRegionDrawable(new TextureRegion(tex)));

        // generate newPlayer's box2d body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.x = 0;
        bodyDef.position.y = 0;
        bodyDef.linearDamping = 0.3f;
        bodyDef.angularDamping = 0.1f;
        bodyDef.angle = angle;


        this.body = world.box2dWorld.createBody(bodyDef);
//        this.body.setUserData(ActorsCategories.USER);
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("testPhysSettings.json"));

        FixtureDef fd = new FixtureDef();
        fd.friction = 0.1f;
        fd.restitution = 0.3f;
        fd.filter.categoryBits = (short) ActorsCategories.USER.getTypeMask();
        fd.filter.maskBits = (short) (ActorsCategories.ENEMY_SHIP.getTypeMask() | ActorsCategories.ENEMY_BULLET.getTypeMask());

        body.getMassData().center.set(SHIP_WIDTH / 2, SHIP_WIDTH * (tex.getHeight() / tex.getWidth()) / 2);
        loader.attachFixture(body, "player-ship", fd, 1);
        // generate newPlayer's actor
        setSize(SHIP_WIDTH, SHIP_WIDTH * (tex.getHeight() / tex.getWidth())); // scale actor to body's size
        setScaling(Scaling.stretch); // stretch the texture
//        setAlign(Align.center);
//        setOrigin(SHIP_WIDTH/2, SHIP_WIDTH*(tex.getHeight()/tex.getWidth()) /2);
        setOrigin(getWidth() / 2, getHeight() / 2);
        shootingPoint = new Vector2(getCenterX(), getCenterY());
    }

    @Override
    public void act(float delta) {
        // here we override Actor's act() method to make the actor follow the box2d body
        super.act(delta);
        updateMotion();
        shootingPoint.set(getCenterX(), getCenterY());
        setRotation(MathUtils.radiansToDegrees * body.getAngle());
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
//        Gdx.app.log(LOG_CLASS_NAME, "Player texture position at " + getX() + " / " + getY() +" body " + body.getPosition().x +" / "+ body.getPosition().y);
    }

    private void updateMotion() {

        Vector2 v = new Vector2(directionX, directionY);

        angleDiff = normalizeAngle(body.getAngle()) - atan2(-directionX, directionY);

        if (angleDiff>Math.PI) angleDiff = -1*(angleDiff-(float)Math.PI);
        if (angleDiff<-Math.PI) angleDiff = -1*(angleDiff+(float)Math.PI);

        if (angleDiff < -0.1 || angleDiff > 0.1){
            turning = (int) Math.signum(-1 * angleDiff);
        } else {
            turning = STOP;
        }

        if (v.len() > 0.7f) {
            speedUp = SPEED_UP;
        } else if (v.len() < 0.1f) {
            speedUp = STOP;
            turning = STOP;
        } else {
            speedUp = STOP;
        }

        turnCheck(turning);
        speedCheck(speedUp);

        Vector2 velocity = body.getLinearVelocity();
        float speed = velocity.len();
        if (speed > MAX_SPEED) {
            body.setLinearVelocity(velocity.scl(MAX_SPEED / speed));
        }
    }

    public float normalizeAngle(float angle){
        return (angle %= (float)Math.PI*2) >= 0 ? (angle < (float)Math.PI) ? angle : angle - (float)Math.PI*2 : (angle >= -(float)Math.PI) ? angle : angle + (float) Math.PI*2;
    }

    private void turnCheck(int rotation) {
        float impulse = body.getAngularDamping() * rotation / 1;
        body.setTransform(body.getPosition(), body.getAngle() + impulse);
    }

    private void speedCheck(int speedUp) {

        if (speedUp != 0) {
            Vector2 v = new Vector2(-(float) Math.sin(body.getAngle()), (float) Math.cos(body.getAngle()));
            if (speedUp < 0) speedUp /= 5;
            v.nor().scl(THRUST * speedUp);
            body.applyForceToCenter(v, true);
        }
    }

    public void setJoystickMove(float knobPercentX, float knobPercentY) {
        directionX = knobPercentX;
        directionY = knobPercentY;
    }

    public boolean isRotating() {
        return rotating;
    }

    public void setRotating(boolean rotating) {
        this.rotating = rotating;
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

    public void stop() {
        body.setLinearVelocity(0, 0);
    }

    public Vector2 getShootingPoint() {
        return shootingPoint;
    }

    public void setShootingPoint(Vector2 shootingPoint) {
        this.shootingPoint = shootingPoint;
    }

    public int getSpeedUp() {
        return speedUp;
    }

    public void setSpeedUp(int speedUp) {
        this.speedUp = speedUp;
    }

    public int getTurning() {
        return turning;
    }

    public void setTurning(int turning) {
        this.turning = turning;
    }
}



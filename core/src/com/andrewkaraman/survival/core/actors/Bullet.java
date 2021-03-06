package com.andrewkaraman.survival.core.actors;

import com.andrewkaraman.survival.core.model.BulletCharacteristic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Scaling;

import aurelienribon.bodyeditor.BodyEditorLoader;

/**
 * Created by KaramanA on 17.10.2014.
 */
public class Bullet extends AbsActor implements Pool.Poolable {

    private final String LOG_CLASS_NAME = this.getClass().getName();

    private final float MAX_BULLET_DISTANCE = 5;
    private float startPosX, startPosY;

    private int speed = 10;

    public Bullet(World world) {
        this(world, 0, 0, 0.2f, 0, new Vector2());
    }

    public Bullet(World world, float startPosX, float startPosY, float angle, Vector2 velocity) {
        this(world, startPosX, startPosY, 0.2f, angle, velocity);
    }

    public Bullet(World world, float startPosX, float startPosY, float actorWidth, float angle, Vector2 velocity) {
        Texture tex = new Texture(Gdx.files.internal("bullet.png"));
        this.setDrawable(new TextureRegionDrawable(new TextureRegion(tex)));

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);
        body.setBullet(true);
        characteristic = new BulletCharacteristic();
        body.setUserData(this);
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("testPhysSettings.json"));

        loader.attachFixture(body, "Bullet", getFixture(), actorWidth);

        setSize(actorWidth, actorWidth * (tex.getHeight() / tex.getWidth())); // scale actor to body's size
        setOrigin(Align.center);
        setScaling(Scaling.stretch); // stretch the texture

        init(startPosX, startPosY, angle, velocity);
        alive = false;
        setVisible(alive);
        textureSetup();
    }

    protected FixtureDef getFixture(){
        FixtureDef fd = new FixtureDef();
        fd.density = 0.2f;
        fd.filter.categoryBits = (short) (ActorsCategories.BULLET.getTypeMask());
        fd.filter.maskBits = (short) (ActorsCategories.ENEMY_SHIP.getTypeMask());
        return  fd;
    }

    @Override
    protected void textureSetup() {
        origin = new Vector2();
    }

    public void init(float posX, float posY, float angle, Vector2 velocity) {
        posX -= getWidth() / 2;
        posY -= getHeight() / 2;

        Vector2 v = new Vector2(-(float) Math.sin(angle), (float) Math.cos(angle));
        v.set(v.nor());
        startPosX = posX;
        startPosY = posY;

        body.setTransform(startPosX, startPosY, 0);
        v.scl(speed);

        body.setLinearVelocity(v.add(velocity));

        body.setActive(true);
        characteristic.setAlive(true);
        alive = true;
        setVisible(alive);
        setPosition(body.getPosition()); // set the actor position at the box2d body position
    }

    private void checkDistance() {
        double distance = Math.sqrt(Math.pow(startPosX - body.getPosition().x, 2) + Math.pow(startPosY - body.getPosition().y, 2));

        if (distance > MAX_BULLET_DISTANCE) {
            body.setAngularVelocity(0);
            characteristic.setAlive(false);
            alive = false;
            setVisible(alive);
        }
    }

    @Override
    protected void updateMotion(float delta) {
        checkDistance();
    }
}

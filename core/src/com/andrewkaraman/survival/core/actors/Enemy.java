package com.andrewkaraman.survival.core.actors;

import com.andrewkaraman.survival.core.model.EnemyCharacteristic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Pool;

import aurelienribon.bodyeditor.BodyEditorLoader;

/**
 * Created by Andrew on 26.10.2014.
 */
public class Enemy extends AbsActor implements Pool.Poolable{

    private final String LOG_CLASS_NAME = this.getClass().getName();

    public boolean alive;
    public EnemyCharacteristic characteristic;

    public Enemy(World world) {
        this(world, 2, 2, 1);
    }

    public Enemy(World world, float startPosX, float startPosY, float actorWidth) {

        Texture tex = new Texture(Gdx.files.internal("front-gun-proton-launcher.png"));
        this.setDrawable(new TextureRegionDrawable(new TextureRegion(tex)));

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.x = startPosX;
        bodyDef.position.y = startPosY;
        bodyDef.linearDamping = 0.1f;
        bodyDef.angularDamping = 0.5f;

        body = world.createBody(bodyDef);
        characteristic = new EnemyCharacteristic();
        body.setUserData(characteristic);
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("testPhysSettings.json"));

        FixtureDef fd = new FixtureDef();
        fd.friction = 0.1f;
        fd.restitution = 0.3f;
        fd.density = 5;

        fd.filter.categoryBits = (short) ActorsCategories.ENEMY_SHIP.getTypeMask();
        fd.filter.maskBits = (short) (ActorsCategories.BULLET.getTypeMask()|ActorsCategories.USER.getTypeMask());

//        body.getMassData().center.set(actorWidth / 2, actorWidth * (tex.getHeight() / tex.getWidth()) / 2);
        loader.attachFixture(body, "Enemy", fd, 1);

        setSize(actorWidth, actorWidth * (tex.getHeight() / tex.getWidth())); // scale actor to body's size
//        setScaling(Scaling.stretch); // stretch the texture
//        setAlign(Align.topRight);
//        setOrigin(actorWidth/2, actorWidth*(tex.getHeight()/tex.getWidth()) /2);
//        setOrigin(Align.center);

        init(startPosX, startPosY);
        alive = false;
        setVisible(alive);
    }

    public void init(float posX, float posY) {
        body.setTransform(posX, posY, 0);
        setRotation(MathUtils.radiansToDegrees * body.getAngle());
        setPosition(body.getPosition().x-getOriginX(), body.getPosition().y-getOriginY()); // set the actor position at the box2d body position

        characteristic.setAlive(true);
        body.setActive(true);
        alive = true;
        setVisible(alive);
    }

//    @Override
//    public void act(float delta) {
//        super.act(delta);
//        setRotation(MathUtils.radiansToDegrees * body.getAngle());
//        setPosition(body.getPosition().x, body.getPosition().y);
//    }

    @Override
    protected void updateMotion(float delta) {
    }

    @Override
    public void reset() {
        alive = false;
        setVisible(alive);
        body.setActive(false);
        body.setLinearVelocity(0,0);
    }
}
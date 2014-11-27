package com.andrewkaraman.survival.core.actors;

import com.andrewkaraman.survival.core.GameWorld;
import com.andrewkaraman.survival.core.model.LootCharacteristic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Scaling;

import java.util.Random;

import aurelienribon.bodyeditor.BodyEditorLoader;

/**
 * Created by Andrew on 24.11.2014.
 */
public class Loot extends AbsActor implements Pool.Poolable {

    private GameWorld gameWorld;

    public Loot(GameWorld world) {

        this.gameWorld = world;

        Texture tex = new Texture(Gdx.files.internal("metal-crate.png"));
        this.setDrawable(new TextureRegionDrawable(new TextureRegion(tex)));

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.linearDamping = 0.8f;
//        bodyDef.fixedRotation = true;

        body = gameWorld.box2dWorld.createBody(bodyDef);
        characteristic = new LootCharacteristic();
        body.setUserData(this);
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("testPhysSettings.json"));

        FixtureDef fd = new FixtureDef();
        fd.friction = 0.2f;
        fd.restitution = 0.3f;
        fd.density = 0.2f;

        fd.filter.categoryBits = (short) ActorsCategories.LOOT.getTypeMask();
        fd.filter.maskBits = (short) (ActorsCategories.USER.getTypeMask());

        loader.attachFixture(body, "Crate", fd, 1f);

        setSize(1f, 1f * (tex.getHeight() / tex.getWidth())); // scale actor to body's size

        setOrigin(Align.center);
        setScaling(Scaling.stretch);
        alive = false;
        setVisible(alive);
        textureSetup();
    }

    public void init(float posX, float posY) {
        body.setTransform(posX, posY, 0);
        Random random = new Random();
        body.setLinearVelocity(-3 + random.nextFloat()*6, -3 + random.nextFloat()*6);
        setRotation(MathUtils.radiansToDegrees * body.getAngle());
        setPosition(body.getPosition().x, body.getPosition().y); // set the actor position at the box2d body position

        characteristic.setAlive(true);
        characteristic.setHealth(1);
        body.setActive(true);
        alive = true;
        setVisible(alive);
    }

    @Override
    protected void updateMotion(float delta) {

    }
}

package com.andrewkaraman.survival.core.actors;

import com.andrewkaraman.survival.core.screens.GameScreenBox;
import com.andrewkaraman.survival.core.utils.ObjectGenerators;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Pool;

import java.util.Random;

import aurelienribon.bodyeditor.BodyEditorLoader;

/**
 * Created by KaramanA on 16.10.2014.
 */
public class Enemy extends Actor implements Pool.Poolable {

    private final String LOG_CLASS_NAME = this.getClass().getName();

    public boolean alive;
    private Body body = null;

    private World world;
    private TextureRegion region;

    public Enemy(World world) {

        this(world, 110, 110);
    }

    public Enemy(World world, float x, float y) {

        this.world = world;
        Vector2 pos = new Vector2(x,y);
//                ObjectGenerators.getRandomPosition();

        setPosition(pos.x, pos.y);
        region = new TextureRegion (new Texture(Gdx.files.internal("front-gun-proton-launcher.png")));

        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("testPhysSettings.json"));

        // 1. Create a BodyDef, as usual.
        BodyDef bd = new BodyDef();
        bd.position.set(getX(), getY());
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.angle=MathUtils.radiansToDegrees * 180f;

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
        loader.attachFixture(body, "Enemy", fd, 2);

        setWidth(2);
        setHeight(2);


        //TODO add factory
        alive = true;
        setVisible(alive);
        Gdx.app.log(LOG_CLASS_NAME, "Enemy generated at " + getX() + " / " + getY() + " actor " + pos.x + " / " + pos.y);
    }
//
//    public void init(float posX, float posY) {
//        setPosition(posX,  posY);
//        alive = true;
//        setVisible(alive);
//    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        updateMotion();
        batch.draw(region, getX(), getY(), 0, 0, getWidth(), getHeight(),
                1, 1, getRotation());
    }

    private void updateMotion() {
        setRotation(MathUtils.radiansToDegrees * body.getAngle());
        setPosition(body.getPosition().x, body.getPosition().y);
    }

    @Override
    public void reset() {
//        alive = false;
//        setVisible(alive);
    }
}

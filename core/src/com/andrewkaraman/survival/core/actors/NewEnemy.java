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

import aurelienribon.bodyeditor.BodyEditorLoader;

/**
 * Created by Andrew on 26.10.2014.
 */
public class NewEnemy extends Image {

    private final String LOG_CLASS_NAME = this.getClass().getName();
    public final Body body;
    private final int SHIP_WIDTH = 1;

    public NewEnemy(GameWorld world) {

        Texture tex = new Texture(Gdx.files.internal("front-gun-proton-launcher.png"));
        this.setDrawable(new TextureRegionDrawable(new TextureRegion(tex)));

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.x = 6f;
        bodyDef.position.y = 6f;
        bodyDef.linearDamping = 0.1f;
        bodyDef.angularDamping = 0.5f;

        this.body = world.box2dWorld.createBody(bodyDef);

        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("testPhysSettings.json"));

        FixtureDef fd = new FixtureDef();
        fd.friction = 0.1f;
        fd.restitution = 0.3f;
        fd.density = 5;

        loader.attachFixture(body, "Enemy", fd, 1);

        setPosition(body.getPosition().x, body.getPosition().y); // set the actor position at the box2d body position
        setSize(SHIP_WIDTH, SHIP_WIDTH * (tex.getHeight() / tex.getWidth())); // scale actor to body's size
        setScaling(Scaling.stretch); // stretch the texture
        setAlign(Align.center);
        setOrigin(SHIP_WIDTH/2, SHIP_WIDTH*(tex.getHeight()/tex.getWidth()) /2);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        setRotation(MathUtils.radiansToDegrees * body.getAngle());
        setPosition(body.getPosition().x, body.getPosition().y);
    }

}

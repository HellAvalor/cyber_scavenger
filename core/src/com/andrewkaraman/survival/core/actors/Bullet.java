package com.andrewkaraman.survival.core.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Scaling;

import aurelienribon.bodyeditor.BodyEditorLoader;

/**
 * Created by KaramanA on 17.10.2014.
 */
public class Bullet extends Image implements Pool.Poolable {

    private final String LOG_CLASS_NAME = this.getClass().getName();

    private final float MAX_BULLET_DISTANCE = 5;
    private float startPosX, startPosY;
    public boolean alive;
    public final Body body;
    private int speed = 10;
    private final float actorWidth = 0.2f;

    public Bullet(World world, float posX, float posY, float angle, Vector2 velocity) {

        Texture tex = new Texture(Gdx.files.internal("bullet.png"));
        this.setDrawable(new TextureRegionDrawable(new TextureRegion(tex)));

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        this.body = world.createBody(bodyDef);

        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("testPhysSettings.json"));

        FixtureDef fd = new FixtureDef();
        loader.attachFixture(body, "Bullet", fd, actorWidth);

        setSize(actorWidth, actorWidth * (tex.getHeight() / tex.getWidth())); // scale actor to body's size
        setScaling(Scaling.stretch); // stretch the texture
        setAlign(Align.center);
        setOrigin(actorWidth / 2, actorWidth * (tex.getHeight() / tex.getWidth()) / 2);

        init(posX, posY, angle, velocity);
        alive = false;
        setVisible(alive);
    }

    public void init(float posX, float posY, float angle, Vector2 velocity) {
        posX -= getWidth()/2;
        posY -= getHeight()/2;

        Vector2 v = new Vector2(- (float) Math.sin(angle), (float) Math.cos(angle));
        v.set(v.nor());
        startPosX = v.x+posX;
        startPosY = v.y+posY;

        body.setTransform(startPosX, startPosY, 0);

        v.scl(speed);
        body.setLinearVelocity(v.add(velocity));

        setPosition(body.getPosition().x, body.getPosition().y); // set the actor position at the box2d body position
        body.setActive(true);
        alive = true;
        setVisible(alive);
    }

    private void checkDistance(){

        double distance = Math.sqrt(Math.pow(startPosX - body.getPosition().x, 2) + Math.pow(startPosY - body.getPosition().y, 2));

        if (distance > MAX_BULLET_DISTANCE) {
            alive = false;
            setVisible(alive);
        }
    }

    @Override
    public void reset() {
        alive = false;
        setVisible(alive);
        body.setActive(false);
        body.setLinearVelocity(0,0);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        checkDistance();
        setRotation(MathUtils.radiansToDegrees * body.getAngle());
        setPosition(body.getPosition().x, body.getPosition().y);
    }
}

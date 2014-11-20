package com.andrewkaraman.survival.core.actors;

import com.andrewkaraman.survival.core.model.AbsCharacteristics;
import com.andrewkaraman.survival.core.model.EnemyCharacteristic;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by KaramanA on 13.11.2014.
 */
public abstract class AbsActor extends Image {

    protected final String LOG_CLASS_NAME = this.getClass().getName();

    protected Vector2 origin;
    protected Body body;


    public AbsCharacteristics characteristic;

    @Override
    public void act(float delta) {
        super.act(delta);
        updateMotion(delta);
        setRotation(MathUtils.radiansToDegrees * body.getAngle());
        setPosition(body.getPosition().sub(getOrigin())); // set the actor position at the box2d body position
    }

    public Body getBody() {
        return body;
    }


    protected abstract void updateMotion(float delta);

    public void setPosition(Vector2 position){
        setPosition(position.x, position.y);
    }

    public Vector2 getOrigin() {
        return origin;
    }

    public void setOrigin(Vector2 origin) {
        this.origin = origin;
    }

    protected void textureSetup(){
        origin = new Vector2(getOriginX(), getOriginY());
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        super.drawDebug(shapes);
        shapes.setColor(0, 1, 1, 1);
        shapes.line(body.getPosition().x, body.getPosition().y, body.getPosition().x + body.getLinearVelocity().x, body.getPosition().y + body.getLinearVelocity().y);
    }

    public AbsCharacteristics getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(AbsCharacteristics characteristic) {
        this.characteristic = characteristic;
    }

}

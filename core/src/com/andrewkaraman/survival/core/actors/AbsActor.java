package com.andrewkaraman.survival.core.actors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by KaramanA on 13.11.2014.
 */
public abstract class AbsActor extends Image {

    protected final String LOG_CLASS_NAME = this.getClass().getName();

    protected Body body;

    @Override
    public void act(float delta) {
        super.act(delta);
        updateMotion();
        setRotation(MathUtils.radiansToDegrees * body.getAngle());
        setPosition(body.getPosition().x-getOriginX(), body.getPosition().y-getOriginY()); // set the actor position at the box2d body position
    }

    public Body getBody() {
        return body;
    }


    protected abstract void updateMotion();

}

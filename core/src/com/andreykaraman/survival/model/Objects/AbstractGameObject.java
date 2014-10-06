package com.andreykaraman.survival.model.Objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Andrew on 05.10.2014.
 */
public abstract class AbstractGameObject {
    public Vector2 position;
    public Vector2 dimension;
    public Vector2 origin;
    public Vector2 scale;
    public float rotation;
    public Vector2 velocity;
    public Vector2 terminalVelocity;
    public Vector2 friction;
    public Vector2 acceleration;
    public Rectangle bounds;
//    public Body body;
    public float stateTime;
    public Animation animation;


    public AbstractGameObject() {
        position = new Vector2();
        dimension = new Vector2(1, 1);
        origin = new Vector2();
        scale = new Vector2(1, 1);
        rotation = 0;
        velocity = new Vector2();
        terminalVelocity = new Vector2();
        friction = new Vector2();
        acceleration = new Vector2();
        bounds = new Rectangle();
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
        stateTime = 0;
    }


    public void update(double deltaTime) {
        stateTime += deltaTime;
//        if(body == null) {
//            updateMotionX(deltaTime);
//            updateMotionY(deltaTime);
//            // move to new position
//            position.x += velocity.x * deltaTime;
//            position.y += velocity.y * deltaTime;
//        } else {
//            position.set(body.getPosition());
//            rotation = body.getAngle() * MathUtils.radiansToDegrees;
//        }
    }

    public abstract void render(SpriteBatch batch);

//    protected void updateMotionX(double deltaTime) {
//        if(velocity.x != 0) {
//            // apply friction
//            if(velocity.x > 0) {
//                velocity.x = Math.max(velocity.x - friction.x*(float)deltaTime, 0);
//            } else {
//                velocity.x = Math.min(velocity.x + friction.x*(float)deltaTime, 0);
//            }
//        }
//        // apply acceleration
//        velocity.x += acceleration.x*deltaTime;
//        // make sure the object's velocity does not exceed the positive
//        // or negative terminal velocity
//        velocity.x = MathUtils.clamp(velocity.x, -terminalVelocity.x, terminalVelocity.x);
//    }
//
//    protected void updateMotionY(double deltaTime) {
//        if(velocity.y != 0) {
//            // apply friction
//            if(velocity.y > 0) {
//                velocity.y = Math.max(velocity.y - friction.y*(float)deltaTime, 0);
//            } else {
//                velocity.y = Math.min(velocity.y + friction.y*(float)deltaTime, 0);
//            }
//        }
//        // apply acceleration
//        velocity.y += acceleration.y*deltaTime;
//        // make sure the object's velocity does not exceed the positive
//        // or negative terminal velocity
//        velocity.y = MathUtils.clamp(velocity.y, -terminalVelocity.y, terminalVelocity.y);
//    }
}

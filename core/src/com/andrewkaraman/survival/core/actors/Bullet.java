package com.andrewkaraman.survival.core.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by KaramanA on 17.10.2014.
 */
public class Bullet extends Actor {

    private enum Type {
        BULLET(1),
        MISSILE(2),
        FIREBALL(3),
        PROTON(4),
        WAVE(5);

        Type(int type) {
        }
    }

    private final int damage;
    private final float speed;
    private Texture texture;
    private final int angle;
    float horisontalSpeed;
    float verticalSpeed;
    private float x,y;

    public Bullet(int damage, float x, float y) {
        this.x = x;
        this.y = y;
        this.damage = damage;
        speed = 0.01f;
        angle = 0;
        texture = new Texture(Gdx.files.internal("bullet.png"));
        setPosition(x, y);
    }

    /**
     * Retrieves the damage caused by this shot.
     */
    public int getDamage() {
        return damage;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        updateMotion();
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(texture, x, y, getWidth(), getHeight());
    }

    private void updateMotion(){
        moveBy(0, speed);
    }

    public float getVerticalSpeed() {
        return verticalSpeed;
    }

    public void setVerticalSpeed(float verticalSpeed) {
        this.verticalSpeed = verticalSpeed;
    }

    public float getHorisontalSpeed() {
        return horisontalSpeed;
    }

    public void setHorisontalSpeed(float horisontalSpeed) {
        this.horisontalSpeed = horisontalSpeed;
    }

}

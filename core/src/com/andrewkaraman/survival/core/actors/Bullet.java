package com.andrewkaraman.survival.core.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by KaramanA on 17.10.2014.
 */
public class Bullet extends Actor implements Pool.Poolable {

    private final String LOG_CLASS_NAME = this.getClass().getName();

    public boolean alive;
    private final float speed;
    private Texture texture;
    private final int angle;
    float horisontalSpeed;
    float verticalSpeed;
    private float x,y;

    public Bullet(float x, float y) {
        this.x = x;
        this.y = y;
        speed = 5f;
        angle = 0;
        texture = new Texture(Gdx.files.internal("bullet.png"));
        setPosition(x, y);
        alive = false;
        this.setVisible(alive);
    }

    public void init(float posX, float posY) {
        setPosition(posX,  posY);
        alive = true;
        this.setVisible(alive);
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
        batch.draw(texture, getX(), getY(), texture.getWidth(), texture.getHeight());
    }

    private void updateMotion(){
        moveBy(0, speed);
        if (isOutOfScreen()) alive = false;
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

    public boolean isOutOfScreen(){
        if ((getX()<0) ||
            (getX()<0) ||
            (getRight() > this.getParent().getStage().getWidth()) ||
            (getTop() > this.getParent().getStage().getHeight())){
            return true;
        }
        else return false;
    }

    @Override
    public void reset() {
        alive = false;
        this.setVisible(alive);
    }


}

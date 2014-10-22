package com.andrewkaraman.survival.core.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by KaramanA on 16.10.2014.
 */
public class Enemy extends Actor implements Pool.Poolable {

    public boolean alive;
    Texture region;
    private final String LOG_CLASS_NAME = this.getClass().getName();

    public Enemy(float x, float y) {
        region = new Texture(Gdx.files.internal("front-gun-proton-launcher.png"));
        setRotation(180);
        setBounds(0, 0, getWidth(), getHeight());
        setPosition(x, y);
        alive = false;
        setVisible(alive);
    }

    public void init(float posX, float posY) {
        setPosition(posX,  posY);
        alive = true;
        setVisible(alive);
    }


    @Override
    public void draw (Batch batch, float parentAlpha) {
        updateMotion();
        batch.draw(region, getX(), getY());
    }

    private void updateMotion() {

    }

    @Override
    public void reset() {
        alive = false;
        setVisible(alive);
    }
}

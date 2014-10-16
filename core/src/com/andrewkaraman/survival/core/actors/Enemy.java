package com.andrewkaraman.survival.core.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by KaramanA on 16.10.2014.
 */
public class Enemy extends Actor {

    Texture region;
    private final String LOG_CLASS_NAME = this.getClass().getName();

    public Enemy() {
        region = new Texture(Gdx.files.internal("front-gun-proton-launcher.png"));
        setRotation(180);
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(region, getX(), getY());
    }
}

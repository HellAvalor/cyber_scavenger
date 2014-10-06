package com.andreykaraman.survival.screens.transition;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Andrew on 05.10.2014.
 */
public interface ScreenTransition {
    public float getDuration();
    public void render(SpriteBatch batch, Texture curScreen, Texture nextScreen, float alpha);
}


package com.andreykaraman.survival.providers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Andrew on 03.10.2014.
 */
public class ImageProvider {

    private TextureAtlas landAtlas;

    public ImageProvider() {

    }

    public void load() {
        landAtlas = new TextureAtlas(Gdx.files.internal("packed/land.atlas"));
    }

    public void dispose() {
        landAtlas.dispose();

    }

    public TextureRegion getTexture(String name) {
        return landAtlas.findRegion(name);
    }
}

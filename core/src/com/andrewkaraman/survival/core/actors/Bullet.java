package com.andrewkaraman.survival.core.actors;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by KaramanA on 17.10.2014.
 */
public enum Bullet {
    BULLET(1),
    MISSILE(2),
    FIREBALL(3),
    PROTON(4),
    WAVE(5);

    private final int damage;
    private Texture texture;

    private Bullet(int damage) {
        this.damage = damage;
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

}

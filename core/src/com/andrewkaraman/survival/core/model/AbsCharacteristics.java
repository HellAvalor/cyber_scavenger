package com.andrewkaraman.survival.core.model;

import com.andrewkaraman.survival.core.actors.ActorsCategories;

/**
 * Created by KaramanA on 07.11.2014.
 */
public class AbsCharacteristics {
    private int health;
    private ActorsCategories category;
    private boolean alive = true;

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public ActorsCategories getCategory() {
        return category;
    }

    public void setCategory(ActorsCategories category) {
        this.category = category;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

}

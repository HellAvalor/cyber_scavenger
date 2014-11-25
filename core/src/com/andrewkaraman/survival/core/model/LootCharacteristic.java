package com.andrewkaraman.survival.core.model;

import com.andrewkaraman.survival.core.actors.ActorsCategories;

/**
 * Created by Andrew on 24.11.2014.
 */
public class LootCharacteristic  extends AbsCharacteristics {

    public LootCharacteristic() {
        setHealth(1);
        setCategory(ActorsCategories.LOOT);
    }
}
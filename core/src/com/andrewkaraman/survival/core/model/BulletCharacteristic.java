package com.andrewkaraman.survival.core.model;

import com.andrewkaraman.survival.core.actors.ActorsCategories;

/**
 * Created by KaramanA on 07.11.2014.
 */
public class BulletCharacteristic extends AbsCharacteristics {

    public BulletCharacteristic() {
        setHealth(1);
        setCategory(ActorsCategories.BULLET);
    }
}

package com.andrewkaraman.survival.core.model;

import com.andrewkaraman.survival.core.actors.ActorsCategories;

/**
 * Created by KaramanA on 07.11.2014.
 */
public class EnemyCharacteristic extends AbsCharacteristics {

    public EnemyCharacteristic() {
        setHealth(10);
        setCategory(ActorsCategories.ENEMY_SHIP);
    }
}

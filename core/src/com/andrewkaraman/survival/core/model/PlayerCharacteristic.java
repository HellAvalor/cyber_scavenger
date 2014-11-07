package com.andrewkaraman.survival.core.model;

import com.andrewkaraman.survival.core.actors.ActorsCategories;

/**
 * Created by KaramanA on 07.11.2014.
 */
public class PlayerCharacteristic  extends AbsCharacteristics {

    public PlayerCharacteristic() {
        setHealth(10);
        setCategory(ActorsCategories.USER);
    }
}

package com.andrewkaraman.survival.core.actors;

/**
 * Created by KaramanA on 06.11.2014.
 */
public enum ActorsCategories {

    BOUNDARY (0x0001),
    USER (0x0002),
    FRIENDLY_SHIP (0x0004),
    ENEMY_SHIP (0x0008),
    BULLET (0x0010),
    ENEMY_BULLET (0x0020),
    RADAR_SENSOR (0x0100),
    SHOOTING_SENSOR (0x0200);

    public final int typeMask;

    ActorsCategories(int type) {
        typeMask = type;
    }

    public int getTypeMask() { return typeMask; }

}

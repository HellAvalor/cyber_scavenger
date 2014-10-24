package com.andrewkaraman.survival.core.utils;

import com.andrewkaraman.survival.core.WorldProcessor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by KaramanA on 24.10.2014.
 */
public class ObjectGenerators {

    private final String LOG_CLASS_NAME = this.getClass().getName();

    public static Vector2 getRandomPosition(){

        Vector2 coordinates = new Vector2();
        int center = (int) (WorldProcessor.WORLD_WIDTH / 2 + 5);
        coordinates.x = randInt(center - 5, center + 5);
        coordinates.y = randInt(center - 5, center + 5);

        return coordinates;
    }

    public static int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        return rand.nextInt((max - min) + 1) + min;

    }
}

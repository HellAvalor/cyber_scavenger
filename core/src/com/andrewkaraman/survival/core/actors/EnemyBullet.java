package com.andrewkaraman.survival.core.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Andrew on 23.11.2014.
 */
public class EnemyBullet extends Bullet {

    public EnemyBullet(World world) {
        super(world);
    }

    public EnemyBullet(World world, float startPosX, float startPosY, float angle, Vector2 velocity) {
        super(world, startPosX, startPosY, angle, velocity);
    }

    public EnemyBullet(World world, float startPosX, float startPosY, float actorWidth, float angle, Vector2 velocity) {
        super(world, startPosX, startPosY, actorWidth, angle, velocity);
    }

    @Override
    protected FixtureDef getFixture(){
        FixtureDef fd = new FixtureDef();
        fd.density = 0.2f;
        fd.filter.categoryBits = (short) (ActorsCategories.ENEMY_BULLET.getTypeMask());
        fd.filter.maskBits = (short) (ActorsCategories.USER.getTypeMask());
        return  fd;
    }
}

package com.andrewkaraman.survival.core;

import com.andrewkaraman.survival.core.actors.AbsActor;
import com.andrewkaraman.survival.core.actors.ActorsCategories;
import com.andrewkaraman.survival.core.actors.Bullet;
import com.andrewkaraman.survival.core.actors.Loot;
import com.andrewkaraman.survival.core.actors.Player;
import com.andrewkaraman.survival.core.actors.SmartEnemy;
import com.andrewkaraman.survival.core.model.BulletCharacteristic;
import com.andrewkaraman.survival.core.model.EnemyCharacteristic;
import com.andrewkaraman.survival.core.model.LootCharacteristic;
import com.andrewkaraman.survival.core.model.PlayerCharacteristic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Created by KaramanA on 07.11.2014.
 */
public class CustomizedContactListener implements ContactListener {

    private final String LOG_CLASS_NAME = CustomizedContactListener.class.getName();

    public final static int USER_VS_LOOT = 0x0002 | 0x1000;
    public final static int USER_VS_ENEMY_BULLET = 0x0002 | 0x0020;
    public final static int ENEMY_VS_USER_BULLET = 0x0008 | 0x0010;

    public final static int USER_VS_ENEMY_RADAR = 0x0002 | 0x0100 |  0x0008;
    public final static int USER_VS_ENEMY_SHOOTING_RADAR = 0x0002 | 0x0200 | 0x0008;

    private Fixture fixtureA;
    private Fixture fixtureB;

    private AbsActor bodyAUserData;
    private AbsActor bodyBUserData;

    @Override
    public void beginContact(Contact contact) {
        Gdx.app.log(LOG_CLASS_NAME, " beginContact ");
        fixtureA = contact.getFixtureA();
        fixtureB = contact.getFixtureB();

        //make sure only one of the fixtures was a sensor
        boolean sensorA = fixtureA.isSensor();
        boolean sensorB = fixtureB.isSensor();

        if (!(sensorA && sensorB)) {

            bodyAUserData = (AbsActor) fixtureA.getBody().getUserData();
            bodyBUserData = (AbsActor) fixtureB.getBody().getUserData();

            switch (fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits) {

                case ENEMY_VS_USER_BULLET:
                    if (bodyAUserData.characteristic.getCategory() == ActorsCategories.ENEMY_SHIP) {
                        handleEnemyBulletCollision(fixtureA, fixtureB);
                    } else {
                        handleEnemyBulletCollision(fixtureB, fixtureA);
                    }
                    break;

                case USER_VS_LOOT:
                    if (bodyAUserData.characteristic.getCategory() == ActorsCategories.USER) {
                        handleUserLootCollision(fixtureA, fixtureB);
                    } else {
                        handleUserLootCollision(fixtureB, fixtureA);
                    }
                    break;

                case USER_VS_ENEMY_BULLET:
                    if (bodyAUserData.characteristic.getCategory() == ActorsCategories.USER) {
                        handleUserBulletCollision(fixtureA, fixtureB);
                    } else {
                        handleUserBulletCollision(fixtureB, fixtureA);
                    }
                    break;

                case USER_VS_ENEMY_RADAR:
                    if (bodyAUserData.characteristic.getCategory().getTypeMask() == (ActorsCategories.RADAR_SENSOR.getTypeMask() | ActorsCategories.ENEMY_SHIP.getTypeMask())) {
                        handleEnemyRadarUserCollision(fixtureA, fixtureB, true);
                    } else {
                        handleEnemyRadarUserCollision(fixtureB, fixtureA, true);
                    }
                    break;

                case USER_VS_ENEMY_SHOOTING_RADAR:
                    if (bodyAUserData.characteristic.getCategory().getTypeMask() == (ActorsCategories.SHOOTING_SENSOR.getTypeMask() | ActorsCategories.ENEMY_SHIP.getTypeMask())) {
                        handleEnemySootingRadarUserCollision(fixtureA, fixtureB, true);
                    } else {
                        handleEnemySootingRadarUserCollision(fixtureB, fixtureA, true);
                    }
                    break;
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

        fixtureA = contact.getFixtureA();
        fixtureB = contact.getFixtureB();

        //make sure only one of the fixtures was a sensor
        boolean sensorA = fixtureA.isSensor();
        boolean sensorB = fixtureB.isSensor();

        if (!(sensorA && sensorB)) {

            bodyAUserData = (AbsActor) fixtureA.getBody().getUserData();
            bodyBUserData = (AbsActor) fixtureB.getBody().getUserData();

            switch (fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits) {

                case USER_VS_ENEMY_RADAR:
                    if (bodyAUserData.characteristic.getCategory() == ActorsCategories.RADAR_SENSOR) {
                        handleEnemyRadarUserCollision(fixtureA, fixtureB, false);
                    } else {
                        handleEnemyRadarUserCollision(fixtureB, fixtureA, false);
                    }
                    break;

                case USER_VS_ENEMY_SHOOTING_RADAR:
                    if (bodyAUserData.characteristic.getCategory().getTypeMask() == (ActorsCategories.SHOOTING_SENSOR.getTypeMask() | ActorsCategories.ENEMY_SHIP.getTypeMask())) {
                        handleEnemySootingRadarUserCollision(fixtureA, fixtureB, false);
                    } else {
                        handleEnemySootingRadarUserCollision(fixtureB, fixtureA, false);
                    }
                    break;
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

    private void handleEnemyBulletCollision(Fixture fixtureShip, Fixture fixtureBullet){

        EnemyCharacteristic enemyCharacteristic = (EnemyCharacteristic)((SmartEnemy)fixtureShip.getBody().getUserData()).getCharacteristic();
        enemyCharacteristic.setHealth(enemyCharacteristic.getHealth()-1);

        BulletCharacteristic bulletCharacteristic = (BulletCharacteristic)((Bullet)fixtureBullet.getBody().getUserData()).getCharacteristic();
        bulletCharacteristic.setHealth(bulletCharacteristic.getHealth()-1);

        if (enemyCharacteristic.getHealth() <=0){
            enemyCharacteristic.setAlive(false);
        }

        if (bulletCharacteristic.getHealth() <=0){
            bulletCharacteristic.setAlive(false);
        }
    }

    private void handleUserBulletCollision(Fixture fixtureShip, Fixture fixtureBullet){

        PlayerCharacteristic userCharacteristic = (PlayerCharacteristic)((Player)fixtureShip.getBody().getUserData()).getCharacteristic();
        userCharacteristic.setHealth(userCharacteristic.getHealth()-1);

        BulletCharacteristic bulletCharacteristic = (BulletCharacteristic)((Bullet)fixtureBullet.getBody().getUserData()).getCharacteristic();
        bulletCharacteristic.setHealth(bulletCharacteristic.getHealth()-1);

        if (userCharacteristic.getHealth() <=0){
            userCharacteristic.setAlive(false);
        }

        if (bulletCharacteristic.getHealth() <=0){
            bulletCharacteristic.setAlive(false);
        }
    }

    private void handleUserLootCollision(Fixture fixtureShip, Fixture fixtureBullet){

        PlayerCharacteristic userCharacteristic = (PlayerCharacteristic)((Player)fixtureShip.getBody().getUserData()).getCharacteristic();
        userCharacteristic.setHealth(userCharacteristic.getHealth()-1);

        LootCharacteristic lootCharacteristic = (LootCharacteristic)((Loot)fixtureBullet.getBody().getUserData()).getCharacteristic();
        lootCharacteristic.setHealth(lootCharacteristic.getHealth()-1);

        if (userCharacteristic.getHealth() <=0){
            userCharacteristic.setAlive(false);
        }

        if (lootCharacteristic.getHealth() <=0){
            lootCharacteristic.setAlive(false);
        }
    }

    private void handleEnemyRadarUserCollision(Fixture fixtureShip, Fixture fixtureUser, boolean isSeeUser){

        SmartEnemy enemy = (SmartEnemy) fixtureShip.getBody().getUserData();
        enemy.setTargetInRadarRange(isSeeUser);
    }

    private void handleEnemySootingRadarUserCollision(Fixture fixtureShip, Fixture fixtureUser, boolean isInShootingRange){

        SmartEnemy enemy = (SmartEnemy) fixtureShip.getBody().getUserData();
        enemy.setTargetInShootingRange(isInShootingRange);
    }
}

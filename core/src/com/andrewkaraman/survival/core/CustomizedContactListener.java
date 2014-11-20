package com.andrewkaraman.survival.core;

import com.andrewkaraman.survival.core.actors.AbsActor;
import com.andrewkaraman.survival.core.actors.ActorsCategories;
import com.andrewkaraman.survival.core.actors.Bullet;
import com.andrewkaraman.survival.core.actors.Player;
import com.andrewkaraman.survival.core.actors.SmartEnemy;
import com.andrewkaraman.survival.core.model.AbsCharacteristics;
import com.andrewkaraman.survival.core.model.BulletCharacteristic;
import com.andrewkaraman.survival.core.model.EnemyCharacteristic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by KaramanA on 07.11.2014.
 */
public class CustomizedContactListener implements ContactListener {

    private final String LOG_CLASS_NAME = CustomizedContactListener.class.getName();

    public final static int ENEMY_BULLET =  0x0008 | 0x0010 ;
    public final static int USER_ENEMY_RADAR = 0x0100 | 0x0002 ;

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

                case ENEMY_BULLET:
                    Gdx.app.log(LOG_CLASS_NAME, " beginContact ENEMY_BULLET collision ");
                    if (bodyAUserData.characteristic.getCategory() == ActorsCategories.ENEMY_SHIP) {
                        handleEnemyBulletCollision(fixtureA, fixtureB);
                    } else {
                        handleEnemyBulletCollision(fixtureB, fixtureA);
                    }
                    break;

                case USER_ENEMY_RADAR:
                    Gdx.app.log(LOG_CLASS_NAME, " beginContact USER_ENEMY_RADAR collision ");
                    if (bodyAUserData.characteristic.getCategory() == ActorsCategories.RADAR_SENSOR) {
                        handleEnemyRadarUserCollision(fixtureA, fixtureB, true);
                    } else {
                        handleEnemyRadarUserCollision(fixtureB, fixtureA, true);
                    }
                    break;
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Gdx.app.log(LOG_CLASS_NAME, "endContact ");

        fixtureA = contact.getFixtureA();
        fixtureB = contact.getFixtureB();

        //make sure only one of the fixtures was a sensor
        boolean sensorA = fixtureA.isSensor();
        boolean sensorB = fixtureB.isSensor();

        if (!(sensorA && sensorB)) {

            bodyAUserData = (AbsActor) fixtureA.getBody().getUserData();
            bodyBUserData = (AbsActor) fixtureB.getBody().getUserData();

            switch (fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits) {

                case USER_ENEMY_RADAR:
                    Gdx.app.log(LOG_CLASS_NAME, " endContact USER_ENEMY_RADAR collision ");
                    if (bodyAUserData.characteristic.getCategory() == ActorsCategories.RADAR_SENSOR) {
                        handleEnemyRadarUserCollision(fixtureA, fixtureB, false);
                    } else {
                        handleEnemyRadarUserCollision(fixtureB, fixtureA, false);
                    }
                    break;
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
//        Gdx.app.log(LOG_CLASS_NAME, "preSolve ");
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
//        Gdx.app.log(LOG_CLASS_NAME, "postSolve ");
    }

    private void handleEnemyBulletCollision(Fixture fixtureShip, Fixture fixtureBullet){

        EnemyCharacteristic enemyCharacteristic = (EnemyCharacteristic)((SmartEnemy)fixtureShip.getBody().getUserData()).getCharacteristic();
        Gdx.app.log(LOG_CLASS_NAME, "handleEnemyBulletCollision" + enemyCharacteristic.getHealth());
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

    private void handleEnemyRadarUserCollision(Fixture fixtureShip, Fixture fixtureBullet, boolean isSeeUser){

        Gdx.app.log(LOG_CLASS_NAME, "handleEnemyRadarUserCollision " + isSeeUser);

        SmartEnemy enemy = (SmartEnemy) fixtureShip.getBody().getUserData();
//        enemyCharacteristic.setHealth(enemyCharacteristic.getHealth()-1);
        enemy.setSeeTarget(isSeeUser);
    }

}

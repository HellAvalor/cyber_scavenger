package com.andrewkaraman.survival.core;

import com.andrewkaraman.survival.core.actors.ActorsCategories;
import com.andrewkaraman.survival.core.model.AbsCharacteristics;
import com.andrewkaraman.survival.core.model.BulletCharacteristic;
import com.andrewkaraman.survival.core.model.EnemyCharacteristic;
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

    public final static int ENEMY_BULLET =  0x0008 | 0x0010 ;
    private Fixture fixtureA;
    private Fixture fixtureB;

    private AbsCharacteristics bodyAUserData;
    private AbsCharacteristics bodyBUserData;

    @Override
    public void beginContact(Contact contact) {

        fixtureA = contact.getFixtureA();
        fixtureB = contact.getFixtureB();

        bodyAUserData = (AbsCharacteristics) fixtureA.getBody().getUserData();
        bodyBUserData = (AbsCharacteristics) fixtureB.getBody().getUserData();

        switch (bodyAUserData.getCategory().getTypeMask() | bodyBUserData.getCategory().getTypeMask()){
            case ENEMY_BULLET :
                if (bodyAUserData.getCategory()==ActorsCategories.ENEMY_SHIP) {
                    handleEnemyBulletCollision(fixtureA, fixtureB);
                } else {
                    handleEnemyBulletCollision(fixtureB, fixtureA);
                }

            break;
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private void handleEnemyBulletCollision(Fixture fixtureShip, Fixture fixtureBullet){

        Gdx.app.log(LOG_CLASS_NAME, "handleEnemyBulletCollision ");

        EnemyCharacteristic enemyCharacteristic = (EnemyCharacteristic) fixtureShip.getUserData();
        enemyCharacteristic.setHealth(enemyCharacteristic.getHealth()-1);

        BulletCharacteristic bulletCharacteristic= (BulletCharacteristic) fixtureBullet.getUserData();
        bulletCharacteristic.setHealth(bulletCharacteristic.getHealth()-1);

        if (enemyCharacteristic.getHealth() <=0){
            enemyCharacteristic.setAlive(false);
        }

        if (bulletCharacteristic.getHealth() <=0){
            bulletCharacteristic.setAlive(false);
        }
    }

}

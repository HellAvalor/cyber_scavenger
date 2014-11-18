package com.andrewkaraman.survival.core;

import com.andrewkaraman.survival.core.actors.Bullet;
import com.andrewkaraman.survival.core.actors.Enemy;
import com.andrewkaraman.survival.core.actors.Player;
import com.andrewkaraman.survival.core.actors.SmartEnemy;
import com.andrewkaraman.survival.core.model.ProximityEntity;
import com.andrewkaraman.survival.core.screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.BlendedSteering;
import com.badlogic.gdx.ai.steer.behaviors.CollisionAvoidance;
import com.badlogic.gdx.ai.steer.behaviors.Face;
import com.badlogic.gdx.ai.steer.behaviors.PrioritySteering;
import com.badlogic.gdx.ai.steer.behaviors.Pursue;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.ai.steer.limiters.LinearAccelerationLimiter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.ArrayList;

/**
 * Created by Andrew on 26.10.2014.
 */
public class GameWorld {

    private final String LOG_CLASS_NAME = GameWorld.class.getName();

    // here we set up the actual viewport size of the game in meters.
    public static float UNIT_WIDTH = 40;
    public static float UNIT_HEIGHT = getUnitHeight(); // 3.75 meters height

    public static final Vector2 GRAVITY = new Vector2(0, 0);

    public Stage stage; // stage containing game actors (not GUI, but actual game elements)
    public World box2dWorld; // box2d world
    public Player player; // our playing character
    public SmartEnemy smartEnemy; // our playing character
    public SmartEnemy targetEnemy; // our playing character
    public boolean isResized = false;
    private boolean resetGame = false;

    // bullet pool.
    public ArrayList<Bullet> bullets;
    public Pool<Bullet> bulletPool;
    public ArrayList<Enemy> enemies;
    public Pool<Enemy> enemyPool;

    public GameWorld() {
        createWorld();
    }

    private void createWorld() {
        stage = new Stage(); // create the game stage
        box2dWorld = new World(GRAVITY, true);
        box2dWorld.setContactListener(new CustomizedContactListener());
        Gdx.app.log(LOG_CLASS_NAME, "Unit size " + UNIT_WIDTH + " / " + UNIT_HEIGHT);
        stage.setViewport(new ExtendViewport(UNIT_WIDTH, UNIT_HEIGHT, 0, 0)); // set the game stage viewport to the meters size
        stage.setDebugAll(true);
        initPools();
        // create box2d bodies and the respective actors here.
        player = new Player(this);
        stage.addActor(player);
        stage.setDebugAll(true);
        player.boundingRadius = 80;

        smartEnemy = new SmartEnemy(box2dWorld);
        stage.addActor(smartEnemy);

//        targetEnemy = new SmartEnemy(box2dWorld, -2, -2 , 1);
//        stage.addActor(smartEnemy);

        smartEnemy.setMaxLinearSpeed(4);
        smartEnemy.setMaxLinearAcceleration(200);
        smartEnemy.setMaxAngularSpeed(20);
        smartEnemy.setMaxAngularAcceleration(10);
        smartEnemy.boundingRadius = 2;

//        ProximityEntity proximity = new ProximityEntity(smartEnemy, box2dWorld,
//                smartEnemy.getBoundingRadius() * 4);
//
//        ProximityEntity proximity2 = new ProximityEntity(player, box2dWorld,
//                player.getBoundingRadius() * 4);


//        CollisionAvoidance<Vector2> collisionAvoidanceSB = new CollisionAvoidance<Vector2>(smartEnemy, proximity);
        Face<Vector2> face  = new Face<Vector2>(smartEnemy, player)
                .setTimeToTarget(0.1f) //
                .setAlignTolerance(0.001f) //
                .setDecelerationRadius(MathUtils.degreesToRadians * 180);

//        Wander<Vector2> wanderSB = new Wander<Vector2>(smartEnemy) //
//                // Don't use Face internally because independent facing is off
//                .setFaceEnabled(false) //
//                        // We don't need a limiter supporting angular components because Face is not used
//                        // No need to call setAlignTolerance, setDecelerationRadius and setTimeToTarget for the same reason
//                .setLimiter(new LinearAccelerationLimiter(30)) //
//                .setWanderOffset(60) //
//                .setWanderOrientation(10) //
//                .setWanderRadius(40) //
//                .setWanderRate(MathUtils.PI / 5);


//        PrioritySteering<Vector2> prioritySteeringSB = new PrioritySteering<Vector2>(smartEnemy, 0.0001f);
//        prioritySteeringSB.add(seek);
//        prioritySteeringSB.add(collisionAvoidanceSB);
//
//
//        smartEnemy.setSteeringBehavior(prioritySteeringSB);
        smartEnemy.setSteeringBehavior(face);
    }

    private void initPools(){
        bullets = new ArrayList<Bullet>();

        bulletPool = new Pool<Bullet>() {

            @Override
            protected Bullet newObject() {
                Bullet bullet = new Bullet(box2dWorld, player.getShootingPoint().x, player.getShootingPoint().y, player.getBody().getAngle(), player.getBody().getLinearVelocity());
                stage.addActor(bullet);
                return bullet;
            }
        };

        enemies = new ArrayList<Enemy>();

        enemyPool = new Pool<Enemy>() {

            @Override
            protected Enemy newObject() {
                Enemy enemy = new Enemy(box2dWorld);
                stage.addActor(enemy);
                return enemy;
            }
        };
    }

    public void update(float delta) {
        shoot();
        destroyObjects();
        // perform game logic here
        box2dWorld.step(delta, 3, 3); // update box2d world
        stage.act(delta); // update game stage

    }

    private void shoot() {
        if (player.isShooting()) {
            if (TimeUtils.nanoTime() - player.getLastBulletTime() > player.getShootingSpeed()) {
                Gdx.app.log(LOG_CLASS_NAME, "Shooting");
                Bullet bullet = bulletPool.obtain();
                bullet.init(player.getShootingPoint().x, player.getShootingPoint().y, player.getBody().getAngle(), player.getBody().getLinearVelocity());
                bullets.add(bullet);
                player.setLastBulletTime(TimeUtils.nanoTime());
            }
        }
    }

    public void generateEnemy() {
        Gdx.app.log(LOG_CLASS_NAME, "Generating enemy");
        Enemy enemy = enemyPool.obtain();
        enemy.init(2, 2);
        enemies.add(enemy);
    }

    private void destroyObjects() {
        // if you want to free dead bullets, returning them to the pool:
        Bullet bullet;
        int len = bullets.size();
        for (int i = len; --i >= 0; ) {
            bullet = bullets.get(i);
            if (!bullet.characteristic.isAlive()) {
                bullets.remove(i);
                bulletPool.free(bullet);
            }
        }

        Enemy enemy;
        int lenE = enemies.size();
        for (int i = lenE; --i >= 0; ) {
            enemy = enemies.get(i);
            if (!enemy.characteristic.isAlive()) {
                enemies.remove(i);
                enemyPool.free(enemy);
            }
        }
    }

    public void zoomIn() {
        UNIT_WIDTH -= 5;
        UNIT_HEIGHT = getUnitHeight();
        stage.setViewport(new ExtendViewport(UNIT_WIDTH, UNIT_HEIGHT, 0, 0)); // set the game stage viewport to the meters size
        isResized = true;
//        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void zoomOut() {
        UNIT_WIDTH += 5;
        UNIT_HEIGHT = getUnitHeight();
        stage.setViewport(new ExtendViewport(UNIT_WIDTH, UNIT_HEIGHT, 0, 0)); // set the game stage viewport to the meters size
        isResized = true;
//        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private static float getUnitHeight() {
        return UNIT_WIDTH * GameScreen.SCREEN_HEIGHT / GameScreen.SCREEN_WIDTH;
    }

    public void resetWorld() {
        destroyWorld();
        createWorld();
        setResetGame(false);
    }

    private void destroyWorld(){
        bullets.clear();
        enemies.clear();
        stage.clear();
        box2dWorld.dispose();
    }

    public boolean isResetGame() {
        return resetGame;
    }

    public void setResetGame(boolean resetGame) {
        this.resetGame = resetGame;
    }


}

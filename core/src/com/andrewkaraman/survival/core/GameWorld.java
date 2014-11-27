package com.andrewkaraman.survival.core;

import com.andrewkaraman.survival.core.actors.AbsActor;
import com.andrewkaraman.survival.core.actors.Bullet;
import com.andrewkaraman.survival.core.actors.EnemyBullet;
import com.andrewkaraman.survival.core.actors.Loot;
import com.andrewkaraman.survival.core.actors.Player;
import com.andrewkaraman.survival.core.actors.SmartEnemy;
import com.andrewkaraman.survival.core.screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
    public boolean isResized = false;
    private boolean resetGame = false;

    // bullet pool.
    public ArrayList<Bullet> bullets;
    public Pool<Bullet> bulletPool;
    public ArrayList<EnemyBullet> enemyBullets;
    public Pool<EnemyBullet> enemyBulletPool;
    public ArrayList<SmartEnemy> enemies;
    public Pool<SmartEnemy> enemyPool;
    public ArrayList<Loot> loots;
    public Pool<Loot> lootPool;

    public GameWorld() {
        createWorld();
    }

    private void createWorld() {
        stage = new Stage(); // create the game stage
        box2dWorld = new World(GRAVITY, true);
        box2dWorld.setContactListener(new CustomizedContactListener());
        Gdx.app.debug(LOG_CLASS_NAME, "Unit size " + UNIT_WIDTH + " / " + UNIT_HEIGHT);
        stage.setViewport(new ExtendViewport(UNIT_WIDTH, UNIT_HEIGHT, 0, 0)); // set the game stage viewport to the meters size
        stage.setDebugAll(true);
        initPools(this);
        // create box2d bodies and the respective actors here.
        player = new Player(this);
        stage.addActor(player);
        stage.setDebugAll(true);
        player.boundingRadius = 80;
        generateEnemy();
    }

    private void initPools(final GameWorld gameWorld){
        bullets = new ArrayList<Bullet>();

        bulletPool = new Pool<Bullet>() {

            @Override
            protected Bullet newObject() {
                Bullet bullet = new Bullet(box2dWorld);
                stage.addActor(bullet);
                return bullet;
            }
        };

        enemies = new ArrayList<SmartEnemy>();

        enemyPool = new Pool<SmartEnemy>() {

            @Override
            protected SmartEnemy newObject() {
                SmartEnemy enemy = new SmartEnemy(gameWorld);
                stage.addActor(enemy);
                return enemy;
            }
        };

        loots = new ArrayList<Loot>();

        lootPool = new Pool<Loot>() {

            @Override
            protected Loot newObject() {
                Loot loot = new Loot(gameWorld);
                stage.addActor(loot);
                return loot;
            }
        };


        enemyBullets = new ArrayList<EnemyBullet>();

        enemyBulletPool = new Pool<EnemyBullet>() {

            @Override
            protected EnemyBullet newObject() {
                EnemyBullet enemyBullet = new EnemyBullet(box2dWorld);
                stage.addActor(enemyBullet);
                return enemyBullet;
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
                Bullet bullet = bulletPool.obtain();
                bullet.init(player.getShootingPoint().x, player.getShootingPoint().y, player.getBody().getAngle(), player.getBody().getLinearVelocity());
                bullets.add(bullet);
                player.setLastBulletTime(TimeUtils.nanoTime());
            }
        }

        for (SmartEnemy enemy: enemies){
            if (enemy.isShooting()) {
                if (TimeUtils.nanoTime() - enemy.getLastBulletTime() > enemy.getShootingSpeed()) {
                    EnemyBullet bullet = enemyBulletPool.obtain();
                    bullet.init(enemy.getShootingPoint().x, enemy.getShootingPoint().y, enemy.getBody().getAngle(), enemy.getBody().getLinearVelocity());
                    enemyBullets.add(bullet);
                    enemy.setLastBulletTime(TimeUtils.nanoTime());
                }
            }
        }
    }

    public void generateEnemy() {
        SmartEnemy enemy = enemyPool.obtain();
        enemy.init(2, 2);
        enemies.add(enemy);
    }

    public void generateLoot(AbsActor actor) {
        Loot loot = lootPool.obtain();
        loot.init(actor.getBody().getPosition().x, actor.getBody().getPosition().y);
        loots.add(loot);
    }

    private void destroyObjects() {

        Bullet bullet;
        int len = bullets.size();
        for (int i = len; --i >= 0; ) {
            bullet = bullets.get(i);
            if (!bullet.characteristic.isAlive()) {
                bullets.remove(i);
                bulletPool.free(bullet);
            }
        }

        EnemyBullet enemyBullet;
        len = enemyBullets.size();
        for (int i = len; --i >= 0; ) {
            enemyBullet = enemyBullets.get(i);
            if (!enemyBullet.characteristic.isAlive()) {
                enemyBullets.remove(i);
                enemyBulletPool.free(enemyBullet);
            }
        }

        SmartEnemy enemy;
        len = enemies.size();
        for (int i = len; --i >= 0; ) {
            enemy = enemies.get(i);
            if (!enemy.characteristic.isAlive()) {
                generateLoot(enemy);
                enemies.remove(i);
                enemyPool.free(enemy);
            }
        }

        Loot loot;
        len = loots.size();
        for (int i = len; --i >= 0; ) {
            loot = loots.get(i);
            if (!loot.characteristic.isAlive()) {
                loots.remove(i);
                lootPool.free(loot);
            }
        }
    }

//    private void removeDestroedObj(Object object, Array<Object> array, Pool<Object> pool){
//        //TODO add universal method
//    }

    public void zoomIn() {
        UNIT_WIDTH -= 5;
        UNIT_HEIGHT = getUnitHeight();
        stage.setViewport(new ExtendViewport(UNIT_WIDTH, UNIT_HEIGHT, 0, 0)); // set the game stage viewport to the meters size
        isResized = true;
    }

    public void zoomOut() {
        UNIT_WIDTH += 5;
        UNIT_HEIGHT = getUnitHeight();
        stage.setViewport(new ExtendViewport(UNIT_WIDTH, UNIT_HEIGHT, 0, 0)); // set the game stage viewport to the meters size
        isResized = true;
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

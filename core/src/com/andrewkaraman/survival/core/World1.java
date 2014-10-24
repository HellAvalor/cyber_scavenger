package com.andrewkaraman.survival.core;

import com.andrewkaraman.survival.core.actors.Bullet;
import com.andrewkaraman.survival.core.actors.Enemy;
import com.andrewkaraman.survival.core.actors.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by KaramanA on 21.10.2014.
 */
public class World1 implements Disposable {

    private final String LOG_CLASS_NAME = World1.class.getName();

    private long enemyGenerationSpeed = 200000000;
    private long lastEnemyGenerateTime;

    PlayerInputListener listener;


    public boolean generateEnemy = false;
    private Stage stage;
    private Player player;
    public ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    public World1(Stage stage) {
        this.stage = stage;
//        player = new Player( stage);
//        listener = new PlayerInputListener(this, player);
//        player.addListener(listener);
        stage.addActor(player);
        stage.setKeyboardFocus(player);
    }

    // bullet pool.
    private final Pool<Bullet> bulletPool = new Pool<Bullet>() {

        @Override
        protected Bullet newObject() {
            Bullet bullet = new Bullet(player.getCenterX(), player.getCenterY());
            stage.addActor(bullet);
            return bullet;
        }
    };

    public ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    // bullet pool.
//    private final Pool<Enemy> enemyPool = new Pool<Enemy>() {
//
//        @Override
//        protected Enemy newObject() {
//            Vector2 coord = generateRandomCoordinates();
////            Enemy enemy = new Enemy(coord.x, coord.y);
//            stage.addActor(enemy);
//            return enemy;
//        }
//    };



    public boolean isGenerateEnemy() {
        return generateEnemy;
    }

    public void setGenerateEnemy(boolean generateEnemy) {
        this.generateEnemy = generateEnemy;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void update(float delta){
        checkCollision();
        shoot();
        generateEnemy();
    }

    private void generateEnemy(){

        if (generateEnemy) {
            //      Gdx.app.log(LOG_CLASS_NAME, "Size bullets " + bullets.size() +" bulletPool "+ bulletPool.peak + " stage actors count " + stage.getRoot().getChildren().size);
            if (TimeUtils.nanoTime() - lastEnemyGenerateTime > enemyGenerationSpeed) {
                Gdx.app.log(LOG_CLASS_NAME, "Generating enemy");
//                Enemy enemy = enemyPool.obtain();
                Vector2 coord = generateRandomCoordinates();
//                enemy.init(coord.x, coord.y);
//                enemies.add(enemy);
                lastEnemyGenerateTime =TimeUtils.nanoTime();
            }
        }
    }

    private void shoot(){
        if (player.isShooting()) {
            //      Gdx.app.log(LOG_CLASS_NAME, "Size bullets " + bullets.size() +" bulletPool "+ bulletPool.peak + " stage actors count " + stage.getRoot().getChildren().size);
            if (TimeUtils.nanoTime() - player.getLastBulletTime() > player.getShootingSpeed()) {
                Gdx.app.log(LOG_CLASS_NAME, "Shooting");
                Bullet bullet = bulletPool.obtain();
                bullet.init(player.getCenterX(), player.getCenterY());
                bullets.add(bullet);
                player.setLastBulletTime(TimeUtils.nanoTime());
            }
        }
    }

    private void checkCollision(){
        checkShotEnemyCollision();
        updateShots();
    }

    private void updateShots () {

        // if you want to free dead bullets, returning them to the pool:
        Bullet bullet;
        int len = bullets.size();
        for (int i = len; --i >= 0;) {
            bullet = bullets.get(i);
            if (!bullet.alive) {
                bullets.remove(i);
                bulletPool.free(bullet);
                //todo remove actor if free
            }
        }
    }

    private void checkShotEnemyCollision(){

//        Iterator enemyIterator = enemies.iterator();
//        while(enemyIterator.hasNext()){
//            Enemy enemy = (Enemy) enemyIterator.next();
//
//            // enemy vs. ship
//            if(enemy.getBounds().overlaps(player.getBounds())){
//
//                // create explosion animation
////                createExplosion(ship.getX()+ship.getWidth()/2, ship.getY()+ship
////                        .getHeight()/2);
//
////                ship.remove();
////                quitGame();
//            }
//
//            // enemy vs. shot
//            Iterator shotIterator=bullets.iterator();
//            while(shotIterator.hasNext()){
//                Shot shot=shotIterator.next();
//                if(enemy.getBounds().overlaps(shot.getBounds())){
//                    // create explosion animation
////                    createExplosion(enemy.getX()+enemy.getWidth()/2, enemy.getY()+enemy.getHeight()/2);
//
//                    enemy.remove();
//                    enemyIterator.remove();
//                    shot.remove();
//                    shotIterator.remove();
//                }
//            }
//        }
    }

    private Vector2 generateRandomCoordinates(){
        Vector2 coordinates = new Vector2();
        coordinates.x = randInt(0, (int)stage.getWidth());
        coordinates.y = randInt(0, (int)stage.getHeight());
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


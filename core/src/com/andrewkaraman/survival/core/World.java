package com.andrewkaraman.survival.core;

import com.andrewkaraman.survival.core.actors.Bullet;
import com.andrewkaraman.survival.core.actors.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

/**
 * Created by KaramanA on 21.10.2014.
 */
public class World implements Disposable {

    private final String LOG_CLASS_NAME = this.getClass().getName();
    PlayerInputListener listener;

    private Stage stage;
    private Player player;

    public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private ArrayList<Bullet> removedBullets = new ArrayList<Bullet>();

    // bullet pool.
    private final Pool<Bullet> bulletPool = new Pool<Bullet>() {

        @Override
        protected Bullet newObject() {
            Bullet bullet = new Bullet(player.getCenterX(), player.getCenterY());
            stage.addActor(bullet);
            return bullet;
        }
    };

    public World(Stage stage) {
        this.stage = stage;
        player = new Player(stage);
        player.setBounds(0, 0, player.getWidth(), player.getHeight());

        listener = new PlayerInputListener(player);
        player.addListener(listener);
        stage.addActor(player);
        stage.setKeyboardFocus(player);
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
    }

    private void shoot(){
        if (player.isShooting()) {
            Gdx.app.log(LOG_CLASS_NAME, "Size bullets " + bullets.size() +" bulletPool "+ bulletPool.peak + " stage actors count " + stage.getRoot().getChildren().size);
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
}


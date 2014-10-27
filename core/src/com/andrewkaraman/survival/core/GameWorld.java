package com.andrewkaraman.survival.core;

import com.andrewkaraman.survival.core.actors.Bullet;
import com.andrewkaraman.survival.core.actors.NewEnemy;
import com.andrewkaraman.survival.core.actors.NewPlayer;
import com.andrewkaraman.survival.core.screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * Created by Andrew on 26.10.2014.
 */
public class GameWorld {

    private final String LOG_CLASS_NAME = GameWorld.class.getName();

    // here we set up the actual viewport size of the game in meters.
    public static float UNIT_WIDTH = 20; // 6.4 meters width
    public static float UNIT_HEIGHT =  UNIT_WIDTH*GameScreen.SCREEN_HEIGHT/GameScreen.SCREEN_WIDTH; // 3.75 meters height

    public static final Vector2 GRAVITY = new Vector2(0, 0);

    public final Stage stage; // stage containing game actors (not GUI, but actual game elements)
    public World box2dWorld; // box2d world
    public NewPlayer newPlayer; // our playing character
    public NewEnemy newEnemy;

    public GameWorld() {

        box2dWorld = new World(GRAVITY, true);
        stage = new Stage(); // create the game stage
        Gdx.app.log(LOG_CLASS_NAME, "Unit size " +UNIT_WIDTH + " / "+ UNIT_HEIGHT);
        stage.setViewport(new ExtendViewport(UNIT_WIDTH, UNIT_HEIGHT, 0,0)); // set the game stage viewport to the meters size

        stage.setDebugAll(true);
        createWorld();
    }

    private void createWorld() {

        // create box2d bodies and the respective actors here.
        newPlayer = new NewPlayer(this);
        stage.addActor(newPlayer);

        PlayerInputListener listener = new PlayerInputListener(newPlayer);
        stage.addListener(listener);

        stage.setDebugAll(true);
        stage.setKeyboardFocus(newPlayer);

        Gdx.input.setInputProcessor(stage);
        // add more game elements here

        newEnemy = new NewEnemy(this);
        stage.addActor(newEnemy);
    }

    public void update(float delta) {
        shoot();
        // perform game logic here
        box2dWorld.step(delta, 3, 3); // update box2d world
        stage.act(delta); // update game stage
    }


    private void shoot(){
        if (newPlayer.isShooting()) {
            //      Gdx.app.log(LOG_CLASS_NAME, "Size bullets " + bullets.size() +" bulletPool "+ bulletPool.peak + " stage actors count " + stage.getRoot().getChildren().size);
            if (TimeUtils.nanoTime() - newPlayer.getLastBulletTime() > newPlayer.getShootingSpeed()) {
                Gdx.app.log(LOG_CLASS_NAME, "Shooting");
                Bullet bullet = new Bullet(this, newPlayer.getCenterX(), newPlayer.getCenterY()+newPlayer.getHeight());
                stage.addActor(bullet);
//                Bullet bullet = bulletPool.obtain();
//                bullet.init(player.getCenterX(), player.getCenterY());
//                bullets.add(bullet);
                newPlayer.setLastBulletTime(TimeUtils.nanoTime());
            }
        }
    }
}

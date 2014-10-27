package com.andrewkaraman.survival.core;

import com.andrewkaraman.survival.core.actors.Bullet;
import com.andrewkaraman.survival.core.actors.NewEnemy;
import com.andrewkaraman.survival.core.actors.NewPlayer;
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
    public static float UNIT_WIDTH = 20;
    public static float UNIT_HEIGHT =  getUnitHeight(); // 3.75 meters height

    public static final Vector2 GRAVITY = new Vector2(0, 0);

    public final Stage stage; // stage containing game actors (not GUI, but actual game elements)
    public World box2dWorld; // box2d world
    public NewPlayer newPlayer; // our playing character
    public NewEnemy newEnemy;
    public boolean isResized = false;
    private boolean resetGame = false;

    // bullet pool.
    public ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    public final Pool<Bullet> bulletPool = new Pool<Bullet>() {

        @Override
        protected Bullet newObject() {
            Bullet bullet = new Bullet(box2dWorld, newPlayer.getCenterX(), newPlayer.getCenterY()+newPlayer.getHeight());
            stage.addActor(bullet);
            return bullet;
        }
    };

    public GameWorld() {
        stage = new Stage(); // create the game stage
        createWorld();
    }

    private void createWorld() {
        box2dWorld = new World(GRAVITY, true);
        Gdx.app.log(LOG_CLASS_NAME, "Unit size " +UNIT_WIDTH + " / "+ UNIT_HEIGHT);
        stage.setViewport(new ExtendViewport(UNIT_WIDTH, UNIT_HEIGHT, 0,0)); // set the game stage viewport to the meters size
        stage.setDebugAll(true);
        // create box2d bodies and the respective actors here.
        newPlayer = new NewPlayer(this);
        stage.addActor(newPlayer);

        PlayerInputListener listener = new PlayerInputListener(this, newPlayer);
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

//        if (isResetGame()){
//            resetWorld();
//        }
    }


    private void shoot(){
        if (newPlayer.isShooting()) {
            //      Gdx.app.log(LOG_CLASS_NAME, "Size bullets " + bullets.size() +" bulletPool "+ bulletPool.peak + " stage actors count " + stage.getRoot().getChildren().size);
            if (TimeUtils.nanoTime() - newPlayer.getLastBulletTime() > newPlayer.getShootingSpeed()) {
                Gdx.app.log(LOG_CLASS_NAME, "Shooting");
//                Bullet bullet = new Bullet(box2dWorld, newPlayer.getCenterX(), newPlayer.getCenterY()+newPlayer.getHeight());
//                stage.addActor(bullet);
                Bullet bullet = bulletPool.obtain();
                bullet.init(newPlayer.getCenterX(), newPlayer.getCenterY()+newPlayer.getHeight());
                bullets.add(bullet);
                newPlayer.setLastBulletTime(TimeUtils.nanoTime());
            }
        }
        updateShots ();
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

    public void zoomIn(){
        UNIT_WIDTH = UNIT_WIDTH-5;
        UNIT_HEIGHT = getUnitHeight();
        stage.setViewport(new ExtendViewport(UNIT_WIDTH, UNIT_HEIGHT, 0,0)); // set the game stage viewport to the meters size
        isResized = true;
stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        stage.getViewport().update(stage.getViewport().getScreenWidth(), stage.getViewport().getScreenHeight());
    }

    public void zoomOut() {
        UNIT_WIDTH = UNIT_WIDTH+5;
        UNIT_HEIGHT = getUnitHeight();
        stage.setViewport(new ExtendViewport(UNIT_WIDTH, UNIT_HEIGHT, 0,0)); // set the game stage viewport to the meters size
        isResized = true;

        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        ((ExtendViewport) stage.getViewport()).setMinWorldHeight(getMinWorldHeight() + 5);
//        ((ExtendViewport) stage.getViewport()).setMinWorldWidth(getMinWorldWidth() + 5);
//        stage.getViewport().update(stage.getViewport().getScreenWidth(), stage.getViewport().getScreenHeight());
    }

    private static float getUnitHeight(){
        return UNIT_WIDTH * GameScreen.SCREEN_HEIGHT/GameScreen.SCREEN_WIDTH; // 3.75 meters height
    }

    public void resetWorld(){
        setResetGame(false);
        stage.clear();
        box2dWorld.dispose();
        createWorld();
    }
    public boolean isResetGame() {
        return resetGame;
    }

    public void setResetGame(boolean resetGame) {
        this.resetGame = resetGame;
    }


}

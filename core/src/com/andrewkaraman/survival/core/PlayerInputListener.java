package com.andrewkaraman.survival.core;

import com.andrewkaraman.survival.core.actors.NewPlayer;
import com.andrewkaraman.survival.core.actors.Player;
import com.andrewkaraman.survival.core.screens.GameScreen;
import com.andrewkaraman.survival.core.screens.GameScreenBox;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by KaramanA on 21.10.2014.
 */
public class PlayerInputListener extends InputListener {

    private NewPlayer player;

    private GameWorld world;
    private MyGame myGame;
    private GameScreen screen;


    private final String LOG_CLASS_NAME = this.getClass().getName();

    public PlayerInputListener(/*World world,*/ Player player, MyGame myGame, GameScreenBox screen) {
//        this.world = world;
//        this.screen = screen;
//        this.player = player;
        this.myGame = myGame;
    }

    public PlayerInputListener(GameWorld world, NewPlayer player) {
        this.world = world;
//        this.screen = screen;
        this.player = player;

    }


    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        Gdx.app.log(LOG_CLASS_NAME, "down " + x + " / " + y);
        return true;
    }

    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        Gdx.app.log(LOG_CLASS_NAME, "up");
    }

    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        Gdx.app.log(LOG_CLASS_NAME, "touchDragged " + x + " / " + y + " actor " + player.getCenterX() + " / " + player.getCenterY());
        player.setCenterPosition(x, y);
    }


    @Override
    public boolean keyDown(InputEvent event, int keycode) {

        Gdx.app.log(LOG_CLASS_NAME, "keyDown " + Input.Keys.toString(keycode));

        switch (keycode) {
            case Input.Keys.LEFT:
                player.setMoveLeft(true);
                break;

            case Input.Keys.RIGHT:
                player.setMoveRight(true);
                break;

            case Input.Keys.UP:
                player.setMoveUp(true);
                break;

            case Input.Keys.DOWN:
                player.setMoveDown(true);
                break;

            case Input.Keys.SPACE:
                player.setShooting(true);
                break;

            case Input.Keys.BACKSPACE:
                player.stop();
                break;

            case Input.Keys.G:
//                world.setGenerateEnemy(true);
                break;

            case Input.Keys.R:
                world.setResetGame(true);
                break;

            case Input.Keys.COMMA:
                world.zoomIn();
                break;
            case Input.Keys.PERIOD:
                world.zoomOut();
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        Gdx.app.log(LOG_CLASS_NAME, "keyup " + Input.Keys.toString(keycode));
        switch (keycode) {
            case Input.Keys.LEFT:
                player.setMoveLeft(false);
                break;

            case Input.Keys.RIGHT:
                player.setMoveRight(false);
                break;

            case Input.Keys.UP:
                player.setMoveUp(false);
                break;

            case Input.Keys.DOWN:
                player.setMoveDown(false);
                break;

            case Input.Keys.SPACE:
                player.setShooting(false);
                break;

            case Input.Keys.G:
//                world.setGenerateEnemy(false);
                break;
        }
        return true;
    }
}

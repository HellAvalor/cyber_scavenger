package com.andrewkaraman.survival.core;

import com.andrewkaraman.survival.core.actors.NewPlayer;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

/**
 * Created by KaramanA on 21.10.2014.
 */
public class PlayerInputListener extends InputListener {

    private NewPlayer player;
    private GameWorld world;
    private Touchpad touchpad;

    private final String LOG_CLASS_NAME = this.getClass().getName();

    public PlayerInputListener(GameWorld world, NewPlayer player, Touchpad touchpad) {
        this.world = world;
        this.player = player;
        this.touchpad = touchpad;
    }

    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        Gdx.app.log(LOG_CLASS_NAME, "down " + x + " / " + y);
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            player.setJoystickMove(touchpad.getKnobPercentX(), touchpad.getKnobPercentY());
        }
        return true;
    }

    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        Gdx.app.log(LOG_CLASS_NAME, "up");
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            player.setJoystickMove(touchpad.getKnobPercentX(), touchpad.getKnobPercentY());
        }
    }

    public void touchDragged(InputEvent event, float x, float y, int pointer) {
//        Gdx.app.log(LOG_CLASS_NAME, "touchDragged " + x + " / " + y + " actor " + player.getCenterX() + " / " + player.getCenterY());
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            player.setJoystickMove(touchpad.getKnobPercentX(), touchpad.getKnobPercentY());
        }
    }


    @Override
    public boolean keyDown(InputEvent event, int keycode) {

        Gdx.app.log(LOG_CLASS_NAME, "keyDown " + Input.Keys.toString(keycode));

        switch (keycode) {

            case Input.Keys.SPACE:
                player.setShooting(true);
                break;

            case Input.Keys.BACKSPACE:
                player.stop();
                break;

            case Input.Keys.G:
                world.generateEnemy();
                break;

            case Input.Keys.R:
                world.setResetGame(true);
                break;

            case Input.Keys.W:
                player.setSpeedUp(NewPlayer.SPEED_UP);
                break;

            case Input.Keys.A:
                player.setTurning(NewPlayer.TURN_LEFT);
                break;

            case Input.Keys.D:
                player.setTurning(NewPlayer.TURN_RIGHT);
                break;

            case Input.Keys.S:
                player.setSpeedUp(NewPlayer.FORCE_STOP);
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

        Gdx.app.log(LOG_CLASS_NAME, "keyUp " + Input.Keys.toString(keycode));

        switch (keycode) {

            case Input.Keys.SPACE:
                player.setShooting(false);
                break;

            case Input.Keys.W:
            case Input.Keys.S:
                player.setSpeedUp(NewPlayer.STOP);
                break;
            case Input.Keys.A:
            case Input.Keys.D:
                player.setTurning(NewPlayer.STOP);
                break;

        }
        return true;
    }
}

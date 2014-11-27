package com.andrewkaraman.survival.core;

import com.andrewkaraman.survival.core.actors.Player;
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

    private Player player;
    private GameWorld world;
    private Touchpad touchpad;

    private final String LOG_CLASS_NAME = this.getClass().getName();

    public PlayerInputListener(GameWorld world, Player player, Touchpad touchpad) {
        this.world = world;
        this.player = player;
        this.touchpad = touchpad;
    }

    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            player.setJoystickMove(touchpad.getKnobPercentX(), touchpad.getKnobPercentY());
        }
        return true;
    }

    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            player.setJoystickMove(touchpad.getKnobPercentX(), touchpad.getKnobPercentY());
        }
    }

    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            player.setJoystickMove(touchpad.getKnobPercentX(), touchpad.getKnobPercentY());
        }
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {

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
                player.setSpeedUp(Player.SPEED_UP);
                break;

            case Input.Keys.A:
                player.setTurning(Player.TURN_LEFT);
                break;

            case Input.Keys.D:
                player.setTurning(Player.TURN_RIGHT);
                break;

            case Input.Keys.S:
                player.setSpeedUp(Player.FORCE_STOP);
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

        switch (keycode) {
            case Input.Keys.SPACE:
                player.setShooting(false);
                break;

            case Input.Keys.W:
            case Input.Keys.S:
                player.setSpeedUp(Player.STOP);
                break;
            case Input.Keys.A:
            case Input.Keys.D:
                player.setTurning(Player.STOP);
                break;
        }
        return true;
    }
}

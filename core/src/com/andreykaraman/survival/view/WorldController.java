package com.andreykaraman.survival.view;

import com.andreykaraman.survival.Game.Level;
import com.andreykaraman.survival.screens.DirectedGame;
import com.andreykaraman.survival.utils.CameraHelper;
import com.andreykaraman.survival.utils.Constants;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Andrew on 05.10.2014.
 */
public class WorldController extends InputAdapter{
    private static final String TAG = WorldController.class.getName();
    public CameraHelper cameraHelper;
    public Level level;
    private DirectedGame game;

//    private void backToMenu() {
//        ScreenTransition transition = ScreenTransitionSlide.init(0.75f,
//                ScreenTransitionSlide.DOWN, false, Interpolation.bounceOut);
//        // switch to main menu
//        game.setScreen(new MenuScreen(game), transition);
//    }

    private void initLevel() {
        level = new Level();
    }

    public WorldController(DirectedGame game)
    {
        this.game = game;
        init();
    }

    private void init()
    {
        cameraHelper = new CameraHelper(Constants.MAP_SIZE_IN_PX/2, Constants.MAP_SIZE_IN_PX/2);
        initLevel();
    }

    public void update(float deltaTime)
    {
        handleDebugInput(deltaTime);
        level.update(deltaTime);
        cameraHelper.update(deltaTime);
    }

    private void handleInputGame(float deltaTime) {
//        if (cameraHelper.hasTarget(level.bunnyHead)) {
//            // Player Movement
//            if (Gdx.input.isKeyPressed(Keys.LEFT)) {
//                level.bunnyHead.velocity.x = -level.bunnyHead.terminalVelocity.x;
//            } else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
//                level.bunnyHead.velocity.x = level.bunnyHead.terminalVelocity.x;
//            } else {
//                // Execute auto-forward movement on non-desktop platform
//                if (Gdx.app.getType() != ApplicationType.Desktop) {
//                    level.bunnyHead.velocity.x = level.bunnyHead.terminalVelocity.x;
//                }
//            }
//            // Bunny Jump
//            if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.SPACE))
//                level.bunnyHead.setJumping(true);
//            else
//                level.bunnyHead.setJumping(false);
//        }
    }


    private void handleDebugInput(float deltaTime) {
        if(Gdx.app.getType() != Application.ApplicationType.Desktop)
            return;

        // Camera controls (move)
        float camMoveSpeed = 50 * deltaTime;
        float camMoveSpeedAccelerationFactor = 5;
        if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
            camMoveSpeed *= camMoveSpeedAccelerationFactor;
        if(Gdx.input.isKeyPressed(Keys.LEFT))
            moveCamera(-camMoveSpeed, 0);
        if(Gdx.input.isKeyPressed(Keys.RIGHT))
            moveCamera(camMoveSpeed, 0);
        if(Gdx.input.isKeyPressed(Keys.UP))
            moveCamera(0, -camMoveSpeed);
        if(Gdx.input.isKeyPressed(Keys.DOWN))
            moveCamera(0, camMoveSpeed);
        if(Gdx.input.isKeyPressed(Keys.BACKSPACE))
            cameraHelper.setPosition(0, 0);

        // Camera controls (zoom)
        float camZoomSpeed = 1 * deltaTime;
        float camZoomSpeedAccelerationFactor = 5;
        if(Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT))
            camZoomSpeed *= camZoomSpeedAccelerationFactor;
        if(Gdx.input.isKeyPressed(Keys.COMMA))
            cameraHelper.addZoom(camZoomSpeed);
        if(Gdx.input.isKeyPressed(Keys.PERIOD))
            cameraHelper.addZoom(-camZoomSpeed);
        if(Gdx.input.isKeyPressed(Keys.SLASH))
            cameraHelper.setZoom(1);
    }

    private void moveCamera(float x, float y) {
        x += cameraHelper.getPosition().x;
        y += cameraHelper.getPosition().y;
        cameraHelper.setPosition(x, y);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean keyUp(int keyCode) {
        // Reset game world
        if(keyCode == Keys.R) {
            init();
            Gdx.app.debug(TAG, "Game world reset");
        }
//        // Toggle camera follow
//        else if (keyCode == Keys.ENTER) {
//            cameraHelper.setTarget(cameraHelper.hasTarget() ? null
//                    : level.bunnyHead);
//            Gdx.app.debug(TAG,
//                    "Camera follow enabled: " + cameraHelper.hasTarget());
//        }

        return false;
    }
}

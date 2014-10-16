package com.andrewkaraman.survival.core.screens;

import com.andrewkaraman.survival.core.MyGame;
import com.andrewkaraman.survival.core.actors.Enemy;
import com.andrewkaraman.survival.core.actors.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by KaramanA on 15.10.2014.
 */
public class GameScreen extends AbstractScreen {

    private final String LOG_CLASS_NAME = this.getClass().getName();

    private Stage stage;
    private Player actor;
    private Enemy enemy;

    public GameScreen(MyGame game) {
        super(game);
        stage = new Stage(new FitViewport(640, 480));
        Gdx.input.setInputProcessor(stage);
        actor = new Player();
        enemy = new Enemy();
        enemy.setPosition(stage.getWidth()/2, stage.getHeight()/2);
        stage.addActor(actor);
        stage.addActor(enemy);
        stage.setKeyboardFocus(actor);
        actor.setBounds(0, 0, actor.getWidth(), actor.getHeight());
        Gdx.app.log(LOG_CLASS_NAME, actor.getWidth() + " / " + actor.getHeight() );
    }

    public void resize (int width, int height) {
        // See below for what true means.
        stage.getViewport().update(width, height, true);
    }

    public void render (float delta) {
        super.render(delta);
        stage.act(delta);
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }

//    public Actor hit (float x, float y, boolean touchable) {
//        if (touchable && getTouchable() != Touchable.enabled) return null;
//        return x >= 0 && x < width && y >= 0 && y < height ? this : null;
//    }
}

package com.andrewkaraman.survival.core.screens;

import com.andrewkaraman.survival.core.MyGame;
import com.andrewkaraman.survival.core.World;
import com.andrewkaraman.survival.core.actors.Bullet;
import com.andrewkaraman.survival.core.actors.Enemy;
import com.andrewkaraman.survival.core.actors.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by KaramanA on 15.10.2014.
 */
public class GameScreen extends AbstractScreen {

    private final String LOG_CLASS_NAME = this.getClass().getName();

    private World world;
    private Stage stage;
    private Stage uiStage;

    Label welcomeLabel;

    public GameScreen(MyGame game) {
        super(game);
        Skin skin = super.getSkin();
        stage = new Stage(new FitViewport(640, 480));
        stage.setDebugAll(true);
        world = new World(stage);
        uiStage = new Stage(new FitViewport(640, 480));
//        InputMultiplexer multiplexer = new InputMultiplexer();
//        multiplexer.addProcessor(new MyUiInputProcessor());
//        multiplexer.addProcessor(new MyGameInputProcessor());
//        Gdx.input.setInputProcessor(multiplexer);
        Gdx.input.setInputProcessor(stage);
        welcomeLabel = new Label("", skin);
        uiStage.addActor(welcomeLabel);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        uiStage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.act(delta);
        uiStage.act(delta);
        welcomeLabel.setText(world.getPlayer().getHorisontalSpeed() + " / " + world.getPlayer().getVerticalSpeed());
    }

    @Override
    public void dispose() {
        stage.dispose();
        uiStage.dispose();
        super.dispose();
    }

    @Override
     public void update (float delta) {
        world.update(delta);
    }

    @Override
    public void draw(float delta) {
        stage.draw();
        uiStage.draw();
    }
}

package com.andrewkaraman.survival.core.screens;

import com.andrewkaraman.survival.core.GameRenderer;
import com.andrewkaraman.survival.core.GameWorld;
import com.andrewkaraman.survival.core.MyGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by KaramanA on 15.10.2014.
 */
public class GameScreen extends AbstractScreen{

    private final String LOG_CLASS_NAME = GameScreen.class.getName();

    // this is actually my tablet resolution in landscape mode. I'm using it for making the GUI pixel-exact.
    public static float SCREEN_WIDTH = Gdx.graphics.getWidth();
    public static float SCREEN_HEIGHT = Gdx.graphics.getHeight();
    Label labelStatus;
    private GameWorld world; // contains the game world's bodies and actors.
    private GameRenderer renderer; // our custom game renderer.
    private Stage stage; // stage that holds the GUI. Pixel-exact size.
    private OrthographicCamera guiCam; // camera for the GUI. It's the stage default camera.

    public GameScreen(MyGame game) {
        super(game);
        Gdx.app.log(LOG_CLASS_NAME, "Screen size " +SCREEN_WIDTH +" / "+SCREEN_HEIGHT);
    }

    @Override
    public void show() {
        super.show();
        Skin skin = super.getSkin();
        this.stage = new Stage(); // create the GUI stage
        this.stage.setViewport(new ScreenViewport()); // set the GUI stage viewport to the pixel size
        this.stage.setDebugAll(true);

        world = new GameWorld();
        renderer = new GameRenderer(world);

        // add GUI actors to stage, labels, meters, buttons etc.
        labelStatus = new Label("TOUCH TO START", skin);
        labelStatus.setPosition(10, 10);
        labelStatus.setWidth(SCREEN_WIDTH);
        labelStatus.setAlignment(Align.center);
        labelStatus.setFontScale(0.5f);
        stage.addActor(labelStatus);
        // add other GUI elements here
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        guiCam = (OrthographicCamera) stage.getCamera();
        guiCam.position.set(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, 0);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_TEXTURE_2D);
        labelStatus.setText(world.newPlayer.getX() + " / " + world.newPlayer.getY() + " Objects "+ world.box2dWorld.getBodyCount()+" / stage actors count " + world.stage.getRoot().getChildren().size);
        guiCam.update();

        world.update(delta); // update the box2d world
        stage.act(delta); // update GUI

        renderer.render(); // draw the box2d world
        stage.draw(); // draw the GUI
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void draw(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height);
        world.stage.getViewport().update(width, height);
    }
}

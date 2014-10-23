package com.andrewkaraman.survival.core.screens;

import com.andrewkaraman.survival.core.MyGame;
import com.andrewkaraman.survival.core.PlayerInputListener;
import com.andrewkaraman.survival.core.actors.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by KaramanA on 22.10.2014.
 */
public class GameScreenBox extends AbstractScreen {

    private final String LOG_CLASS_NAME = GameScreenBox.class.getName();

    private World world;
    private Stage stage;
    Stage staticStage;
    Camera camera;
    // World size Meters
    private Player player;
    private  Label welcomeLabel;
    private Player player2;
    private PlayerInputListener listener;

    protected float cameraW;
    protected float cameraH;


    public static final float FRUSTUM_HEIGHT = 25;
    public static final float WORLD_HEIGHT = FRUSTUM_HEIGHT * 10;
    public static final float FRUSTUM_WIDTH = 15;
    public static final float WORLD_WIDTH = FRUSTUM_WIDTH *10;

    Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    public GameScreenBox(MyGame game) {
        super(game);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        stage.getCamera().position.set(player.getX(),player.getY(), 0);
//        stage.getViewport().getCamera()
        Gdx.app.log(LOG_CLASS_NAME, player.getX() + "/" + player.getY() + " " + stage.getViewport().getWorldWidth() +"/"+ stage.getViewport().getWorldHeight());
        Gdx.app.log(LOG_CLASS_NAME, player2.getX() + "/" + player2.getY() + " " + stage.getViewport().getScreenX() +"/"+ stage.getViewport().getScreenY());
//        welcomeLabel.setText(player.getX() + " / " + player.getY() +" camera " + camera.position.x + " / " + camera.position.y);

//        staticStage.act(delta);
        stage.act(delta);

        world.step(1/60f, 6, 2);
//        debugRenderer.render(world, camera.combined);
        debugRenderer.render(world, stage.getCamera().combined);
    }

    @Override
    public void show() {


        Skin skin = super.getSkin();

        world = new World(new Vector2(0f, 0f), true);

        stage = new Stage();
//        staticStage = new Stage();
        stage.setDebugAll(true);

        player = new Player(world);
        player2 = new Player(world, 5, 5);
        PlayerInputListener listener = new PlayerInputListener(player, game);
        stage.addListener(listener);
        stage.addActor(player);
        stage.addActor(player2);

        stage.setKeyboardFocus(player);
        Gdx.input.setInputProcessor(stage);

//        welcomeLabel = new Label("Test", skin);
//        staticStage.addActor(welcomeLabel);

        camera = stage.getCamera();
//        camera.viewportHeight = 5;
//        camera.viewportWidth = 5;
//        camera.position.y = player.getY();
//        camera.position.x = player.getX();

        Viewport v = new FitViewport(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        //Uncomment for keep aspect ratio
        stage.setViewport(v);
//        camera = stage.getCamera();


    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void draw(float delta) {
        stage.draw();
//        staticStage.draw();
    }

    @Override
    public void resize(int width, int height) {

        stage.getViewport().update(width, height, false);

        //        stage.getViewport().setScreenPosition((int) player.getX(), (int)player.getY());
//        stage.setViewport(FRUSTUM_WIDTH, FRUSTUM_HEIGHT, false);
//        Viewport v = new StretchViewport(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
//        //Uncomment for keep aspect ratio
//        stage.setViewport(v);
//        stage.getCamera().viewportHeight = FRUSTUM_HEIGHT;
//        stage.getCamera().viewportWidth = FRUSTUM_WIDTH;

    }

    @Override
    public void dispose() {
        world.dispose();
        stage.dispose();
//        staticStage.dispose();
    }

    public void reset(){
        dispose();
        show();
    }
}

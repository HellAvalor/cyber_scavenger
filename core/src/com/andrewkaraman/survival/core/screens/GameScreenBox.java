package com.andrewkaraman.survival.core.screens;

import com.andrewkaraman.survival.core.MyGame;
import com.andrewkaraman.survival.core.PlayerInputListener;
import com.andrewkaraman.survival.core.actors.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by KaramanA on 22.10.2014.
 */
public class GameScreenBox extends AbstractScreen {

    private final String LOG_CLASS_NAME = GameScreenBox.class.getName();

    World world;
    Stage stage;
    Stage staticStage;
    Camera camera;
    // World size Meters
    Player player;
    Label welcomeLabel;

    public static final float WORLD_WIDTH = 15;
    public static final float FRUSTUM_HEIGHT = 25;
    public static final float WORLD_HEIGHT = FRUSTUM_HEIGHT * 20;
    public static final float FRUSTUM_WIDTH = WORLD_WIDTH;


    Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    public GameScreenBox(MyGame game) {
        super(game);
    }

    @Override
    public void render(float delta) {

//        if (Gdx.app.getType() == Application.ApplicationType.Android) {
//            inputHandler.accelerometerChange(Gdx.input.getAccelerometerX());
//        }

        // TODO Auto-generated method stub
        camera = stage.getCamera();
        camera.position.y = player.getY();
        camera.position.x = player.getX();
//        checkCollision();

        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        welcomeLabel.setText(player.getX() + " / " + player.getY() +" camera " + camera.position.x + " / " + camera.position.y);

        staticStage.act(delta);
        staticStage.draw();
        stage.act(delta);
        stage.draw();

        world.step(1/60f, 6, 2);
        debugRenderer.render(world, camera.combined);

    }

    @Override
    public void show() {

        Skin skin = super.getSkin();
        // TODO Auto-generated method stub
        world = new World(new Vector2(0f, -1), true);

        stage = new Stage();
        staticStage = new Stage();
        stage.setDebugAll(true);

        player = new Player(stage);
        PlayerInputListener listener = new PlayerInputListener(player);
        player.addListener(listener);
        stage.addActor(player);
        stage.setKeyboardFocus(player);
        Gdx.input.setInputProcessor(stage);

        welcomeLabel = new Label("Test", skin);
        staticStage.addActor(welcomeLabel);

//        fallingMan = new FallingMan(world);
//        inputHandler = new InputHandler(fallingMan);
//
//        platforms = new Platforms(world);
//        stage.addListener(inputHandler);
//        stage.addActor(fallingMan);
//        stage.addActor(platforms);

//        Image testPlatform = new Image(Assets.backgroundTexture);
//        testPlatform.setPosition(0,  0);
//        staticStage.addActor(testPlatform);

//        InputMultiplexer im = new InputMultiplexer(staticStage, stage);
//
//        Gdx.input.setInputProcessor(im);

//        testPlatform.addListener(new InputListener() {
//            public boolean touchDown(InputEvent event, float x, float y,
//                                     int pointer, int button) {
//                System.out.println("down platform");
//                return false;
//            }
//
//
//            public void touchUp(InputEvent event, float x, float y, int pointer,
//                                int button) {
//                System.out.println("up platform");
//            }
//        });
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void draw(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
//        stage.setViewport(FRUSTUM_WIDTH, FRUSTUM_HEIGHT, false);

        //Uncomment for keep aspect ratio
		stage.setViewport(new ExtendViewport(FRUSTUM_WIDTH, FRUSTUM_HEIGHT));
//		stage.getCamera().position.set(FRUSTUM_WIDTH/2 - stage.getWidth(), WORLD_HEIGHT
//				- FRUSTUM_HEIGHT / 2 - stage.getHeight(), 0);
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        stage.dispose();
//        staticStage.dispose();
    }
}

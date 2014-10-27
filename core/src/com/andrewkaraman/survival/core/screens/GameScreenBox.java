package com.andrewkaraman.survival.core.screens;

import com.andrewkaraman.survival.core.MyGame;
import com.andrewkaraman.survival.core.PlayerInputListener;
import com.andrewkaraman.survival.core.WorldProcessor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;



/**
 * Created by KaramanA on 22.10.2014.
 */
public class GameScreenBox extends AbstractScreen {

    private final String LOG_CLASS_NAME = GameScreenBox.class.getName();

    private World physWorld;
    private WorldProcessor gameWorld;
    private Stage stage;
    Stage staticStage;
    Camera camera;

//    private Player player;
    private Label debugLabel;
//    private Enemy enemy;
    private PlayerInputListener listener;

    Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    public GameScreenBox(MyGame game) {
        super(game);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        stage.getCamera().position.set(gameWorld.getPlayer().getX(),gameWorld.getPlayer().getY(), 0);


        physWorld.step(1 / 60f, 6, 2);

        staticStage.act(delta);
        stage.act(delta);


        String playerPosition = gameWorld.getPlayer().getX() + " / " + gameWorld.getPlayer().getY();
        String playerBodyPosition = gameWorld.getPlayer().getBody().getPosition().x + " / " + gameWorld.getPlayer().getBody().getPosition().y;
        debugLabel.setText(playerPosition + " " + playerBodyPosition );

//        debugRenderer.render(physWorld, camera.combined);
        debugRenderer.render(physWorld, stage.getCamera().combined);
    }

    @Override
    public void show() {

        Skin skin = super.getSkin();

        physWorld = new World(new Vector2(0f, 0f), true);

        stage = new Stage();
        staticStage = new Stage();


        gameWorld = new WorldProcessor(stage, physWorld);

        PlayerInputListener listener = new PlayerInputListener(gameWorld.getPlayer(), game, this);
        stage.addListener(listener);

        stage.setDebugAll(true);
        stage.setKeyboardFocus(gameWorld.getPlayer());

        Gdx.input.setInputProcessor(stage);

        debugLabel = new Label("Test", skin);

        debugLabel.setFontScale(0.3f);
        staticStage.addActor(debugLabel);

        camera = stage.getCamera();

        Viewport v = new ExtendViewport(20, 20, 0, 0);
        //Uncomment for keep aspect ratio
        stage.setViewport(v);
//        staticStage.setViewport(new StretchViewport(FRUSTUM_WIDTH, FRUSTUM_HEIGHT));
//        camera = stage.getCamera();

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void draw(float delta) {
        stage.draw();
        staticStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
     //   staticStage.getViewport().update(width, height, false);
    }

    @Override
    public void dispose() {
        physWorld.dispose();
        staticStage.dispose();
        stage.dispose();
    }

    public void reset(){
        dispose();
        show();
    }

    public void zoomIn(){
        ((ExtendViewport) stage.getViewport()).setMinWorldHeight(getMinWorldHeight() -5);
        ((ExtendViewport) stage.getViewport()).setMinWorldWidth(getMinWorldWidth() - 5);
        stage.getViewport().update(stage.getViewport().getScreenWidth(), stage.getViewport().getScreenHeight());
    }

    public void zoomOut() {
        ((ExtendViewport) stage.getViewport()).setMinWorldHeight(getMinWorldHeight() + 5);
        ((ExtendViewport) stage.getViewport()).setMinWorldWidth(getMinWorldWidth() + 5);
        stage.getViewport().update(stage.getViewport().getScreenWidth(), stage.getViewport().getScreenHeight());
    }

    private float getMinWorldHeight(){
        return ((ExtendViewport) stage.getViewport()).getMinWorldHeight();
    }

    private float getMinWorldWidth(){
        return ((ExtendViewport) stage.getViewport()).getMinWorldWidth();
    }
}

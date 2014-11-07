package com.andrewkaraman.survival.core.screens;

import com.andrewkaraman.survival.core.GameRenderer;
import com.andrewkaraman.survival.core.GameWorld;
import com.andrewkaraman.survival.core.MyGame;
import com.andrewkaraman.survival.core.PlayerInputListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
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

    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;

    public GameScreen(MyGame game) {
        super(game);
        Gdx.app.log(LOG_CLASS_NAME, "Screen size " +SCREEN_WIDTH +" / "+SCREEN_HEIGHT);
    }

    @Override
    public void show() {
        super.show();
        Skin skin = super.getSkin();

        stage = new Stage(); // create the GUI stage
        stage.setViewport(new ScreenViewport()); // set the GUI stage viewport to the pixel size
        stage.setDebugAll(true);

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

        //Create a touchpad skin
        touchpadSkin = new Skin();
        //Set background image
        touchpadSkin.add("touchBackground", new Texture("data/touchBackground.png"));
        //Set knob image
        touchpadSkin.add("touchKnob", new Texture("data/touchKnob.png"));
        //Create TouchPad Style
        touchpadStyle = new Touchpad.TouchpadStyle();
        //Create Drawable's from TouchPad skin
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        touchKnob.setMinHeight(20);
        touchKnob.setMinWidth(20);
        //Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        //Create new TouchPad with the created style
        touchpad = new Touchpad(10, touchpadStyle);
        //setBounds(x,y,width,height)
        touchpad.setBounds(15, 15, 200, 200);
        stage.addActor(touchpad);

        PlayerInputListener listener = new PlayerInputListener(world, world.newPlayer, touchpad);
        stage.addListener(listener);
        //TODO set keyboard listener (multilistener)
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        guiCam = (OrthographicCamera) stage.getCamera();
        guiCam.position.set(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, 0);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_TEXTURE_2D);

        String str = "";
        if (!world.enemies.isEmpty()){
            str =   "\n enemy life " +  world.enemies.get(0).characteristic.getHealth();
        }
        labelStatus.setText(
//                world.newPlayer.getX() + " / " + world.newPlayer.getY() + " / " +world.newPlayer.body.getLinearVelocity().x+ " / " +world.newPlayer.body.getLinearVelocity().y+ " angle " +world.newPlayer.body.getAngle() +
//                "\n Objects "+ world.box2dWorld.getBodyCount()+" / stage actors count " + world.stage.getRoot().getChildren().size +
//                "\n bullets " +world.bullets.size() +" / pool "+ world.bulletPool.peak+" / pool free "+ world.bulletPool.getFree()+" / pool max "+ world.bulletPool.max +
                "\n Player angle " + world.newPlayer.body.getAngle()+ " normalize "  +(world.newPlayer.normalizeAngle(world.newPlayer.body.getAngle()))+
                "\n touchPad " +touchpad.getKnobPercentX() +" / "+ touchpad.getKnobPercentY()+ " atan  "+ Math.atan2(-touchpad.getKnobPercentX(), touchpad.getKnobPercentY()) + str

//                "\n enemies " +world.enemies.size() +" / pool "+ world.enemyPool.peak+" / pool free "+ world.enemyPool.getFree()+" / pool max "+ world.enemyPool.max
        );
        guiCam.update();

        world.update(delta); // update the box2d world
        stage.act(delta); // update GUI

        renderer.render(); // draw the box2d world
        stage.draw(); // draw the GUI
    }

    @Override
    public void update(float delta) {
        if (world.isResetGame()) {
            world.resetWorld();
            PlayerInputListener listener = new PlayerInputListener(world, world.newPlayer, touchpad);
            stage.addListener(listener);
            resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        if (world.isResized) {
            resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            world.isResized = false;
        }
    }

    @Override
    public void draw(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height);
        world.stage.getViewport().update(width, height);
        renderer.resize();
    }
}

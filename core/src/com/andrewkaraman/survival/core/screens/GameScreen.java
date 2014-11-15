package com.andrewkaraman.survival.core.screens;

import com.andrewkaraman.survival.core.GameRenderer;
import com.andrewkaraman.survival.core.GameWorld;
import com.andrewkaraman.survival.core.MyGame;
import com.andrewkaraman.survival.core.PlayerInputListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


import static com.badlogic.gdx.Application.ApplicationType;

/**
 * Created by KaramanA on 15.10.2014.
 */
public class GameScreen extends AbstractScreen {

    private final String LOG_CLASS_NAME = GameScreen.class.getName();
    // this is actually my tablet resolution in landscape mode. I'm using it for making the GUI pixel-exact.
    public static float SCREEN_WIDTH = Gdx.graphics.getWidth();
    public static float SCREEN_HEIGHT = Gdx.graphics.getHeight();

    public static float SCALE_UNIT = Math.min(Gdx.graphics.getWidth() * 0.01f, 20);

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
        Gdx.app.log(LOG_CLASS_NAME, "Screen size " + SCREEN_WIDTH + " / " + SCREEN_HEIGHT);
    }

    @Override
    public void show() {
        super.show();

        stage = new Stage(); // create the GUI stage
        stage.setViewport(new ScreenViewport()); // set the GUI stage viewport to the pixel size
        stage.setDebugAll(true);

        guiCam = (OrthographicCamera) stage.getCamera();
        guiCam.position.set(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, 0);

        world = new GameWorld();
        renderer = new GameRenderer(world);

        initUI();

        PlayerInputListener listener = new PlayerInputListener(world, world.player, touchpad);
        stage.addListener(listener);
        Gdx.input.setInputProcessor(stage);


    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_TEXTURE_2D);

        String str = "";
        if (!world.enemies.isEmpty()) {
            str = "\n enemy life " + world.enemies.get(0).characteristic.getHealth();
        }

        labelStatus.setText(
//                world.newPlayer.getX() + " / " + world.newPlayer.getY() + " / " +world.newPlayer.body.getLinearVelocity().x+ " / " +world.newPlayer.body.getLinearVelocity().y+ " angle " +world.newPlayer.body.getAngle() +
//                "\n Objects "+ world.box2dWorld.getBodyCount()+" / stage actors count " + world.stage.getRoot().getChildren().size +
//                "\n bullets " +world.bullets.size() +" / pool "+ world.bulletPool.peak+" / pool free "+ world.bulletPool.getFree()+" / pool max "+ world.bulletPool.max +
//                "\n Player angle " + world.newPlayer.body.getAngle()+ " normalize "  +(world.newPlayer.normalizeAngle(world.newPlayer.body.getAngle()))+
//                "\n Screen coord " +
//                "\n touchPad " +touchpad.getKnobPercentX() +" / "+ touchpad.getKnobPercentY()+ " atan  "+ Math.atan2(-touchpad.getKnobPercentX(), touchpad.getKnobPercentY())+
                str

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
            PlayerInputListener listener = new PlayerInputListener(world, world.player, touchpad);
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
        guiCam.position.set(width / 2, height / 2, 0);
        world.stage.getViewport().update(width, height);
        renderer.resize();
    }

    private void setUpTouchPad() {
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
    }

    private void initUI() {
        Skin skin = super.getSkin();
        // add GUI actors to stage, labels, meters, buttons etc.
        labelStatus = new Label("TOUCH TO START", skin);
        labelStatus.setPosition(10, 10);
        labelStatus.setWidth(SCREEN_WIDTH);
        labelStatus.setAlignment(Align.center);
        labelStatus.setFontScale(0.5f);

        stage.addActor(labelStatus);
        // add other GUI elements here

        Button shootButton = new Button(skin);
        shootButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                world.player.setShooting(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                world.player.setShooting(false);
            }
        });

        Button missileButton = new Button(skin);
        missileButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                world.setResetGame(true);
            }
        });

        Table shootGroup = new Table();
        shootGroup.setSkin(skin);
        shootGroup.defaults().expand();
        shootGroup.row();
        shootGroup.add(missileButton).fillY().right().minWidth(SCALE_UNIT * 10);
        shootGroup.row();
        shootGroup.add(shootButton).fillY().left().minWidth(SCALE_UNIT * 10);


        Table controls = new Table();
        controls.setSkin(skin);

        if (Gdx.app.getType() == ApplicationType.Android) {
            setUpTouchPad();

            controls.defaults().fill();
            controls.add(touchpad).width(SCALE_UNIT * 20);
            controls.add().expand();
            controls.add(shootGroup).width(SCALE_UNIT * 20);
        }
        skin.getFont("default-font").setScale(0.5f);


        Table statusBar = new Table();
        statusBar.setSkin(skin);
        statusBar.defaults().fill();
        statusBar.add("left column").width(SCALE_UNIT * 10);
        statusBar.add("left info").expand();
        statusBar.add("level").width(SCALE_UNIT * 5).height(SCALE_UNIT * 5).top();
        statusBar.add("Bars").width(SCALE_UNIT * 20).height(SCALE_UNIT * 5).top();
        statusBar.add("level").width(SCALE_UNIT * 5).height(SCALE_UNIT * 5).top();
        statusBar.add("right info").expand();
        statusBar.add("right column").width(SCALE_UNIT * 10);


        Table table = new Table();
        table.setFillParent(true);
        table.setSkin(skin);
        table.defaults().fill().pad(5);
        table.row().height(SCALE_UNIT * 10);
        table.add(statusBar).expandX().colspan(3);
        table.row();
        table.add("left column").width(SCALE_UNIT * 10).left();
        table.add().expand();
        table.add("right column").width(SCALE_UNIT * 10).right();
        table.row().minHeight(SCALE_UNIT * 20).bottom();
        table.add(controls).colspan(table.getColumns());

        table.setDebug(true, true);
        stage.addActor(table);
    }
}

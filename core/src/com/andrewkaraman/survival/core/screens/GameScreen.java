package com.andrewkaraman.survival.core.screens;

import com.andrewkaraman.survival.core.GameRenderer;
import com.andrewkaraman.survival.core.GameWorld;
import com.andrewkaraman.survival.core.MyGame;
import com.andrewkaraman.survival.core.PlayerInputListener;
import com.andrewkaraman.survival.core.model.PlayerCharacteristic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.badlogic.gdx.Application.ApplicationType;

/**
 * Created by KaramanA on 15.10.2014.
 */
public class GameScreen extends AbstractScreen {

    private final String LOG_CLASS_NAME = GameScreen.class.getName();
    // this is actually my tablet resolution in landscape mode. I'm using it for making the GUI pixel-exact.

    public static float SCALE_UNIT = Math.min(Gdx.graphics.getWidth() * 0.01f, 20);

    Label labelStatus;
    Label labelFPS;
    private GameWorld world; // contains the game world's bodies and actors.
    private GameRenderer renderer; // our custom game renderer.
    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;
    private ProgressBar lifeBar;
    long startTime = TimeUtils.nanoTime();
    String str2;

    public GameScreen() {
        super();
        Gdx.app.debug(LOG_CLASS_NAME, "Screen size " + SCREEN_WIDTH + " / " + SCREEN_HEIGHT);
    }

    @Override
    protected void init() {
        world = MyGame.world;
        renderer = new GameRenderer(world);
        super.init();
    }


    @Override
    public void update(float delta) {

        super.update(delta);
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

        String str = "";
        if (!world.enemies.isEmpty()) {
            str = "\n enemy life " + world.enemies.get(0).characteristic.getHealth() + " state  " + world.enemies.get(0).getFSM().getCurrentState();
        }


        if (TimeUtils.nanoTime() - startTime > 1000000000) /* 1,000,000,000ns == one second */ {
            str2 = "" + Gdx.graphics.getFramesPerSecond();
            startTime = TimeUtils.nanoTime();
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

        labelFPS.setText("FPS: " + str2);
        lifeBar.setValue(world.player.characteristic.getHealth());
        world.update(delta); // update the box2d world
        renderer.render(); // draw the box2d world

    }


    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
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

    @Override
    protected void initUI() {
        // add GUI actors to stage, labels, meters, buttons etc.
        labelStatus = new Label("TOUCH TO START", skin);
        labelStatus.setPosition(10, 10);
        labelStatus.setWidth(SCREEN_WIDTH);
        labelStatus.setAlignment(Align.center);
        labelStatus.setFontScale(0.5f);

        labelFPS = new Label("FPS 999", skin);
        labelFPS.setFontScale(0.8f);

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
                world.generateEnemy();
            }
        });

        TextButton menu = new TextButton("Menu", skin);
        menu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.debug(LOG_CLASS_NAME, "Menu pressed");
                ScreenManager.getInstance().show(Screens.LOADING);
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

        lifeBar = new ProgressBar(0, 10, 1, false, skin);
        lifeBar.setValue(world.player.characteristic.getHealth());

        Table statusBar = new Table();
        statusBar.setSkin(skin);
        statusBar.defaults().fill();
        statusBar.add(menu).width(SCALE_UNIT * 10).align(Align.center);
        statusBar.add("left info").expand();
        statusBar.add("level").width(SCALE_UNIT * 5).height(SCALE_UNIT * 5).top();
        statusBar.add(lifeBar).width(SCALE_UNIT * 20).height(SCALE_UNIT * 5).top();
        statusBar.add("level").width(SCALE_UNIT * 5).height(SCALE_UNIT * 5).top();
        statusBar.add("right info").expand();
        statusBar.add(labelFPS).width(SCALE_UNIT * 10);


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

        PlayerInputListener listener = new PlayerInputListener(world, world.player, touchpad);
        stage.addListener(listener);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public boolean isNeedToSave() {
        return true;
    }
}

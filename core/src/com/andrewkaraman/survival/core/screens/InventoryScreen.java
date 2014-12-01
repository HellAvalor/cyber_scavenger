package com.andrewkaraman.survival.core.screens;

import com.andrewkaraman.survival.core.GameRenderer;
import com.andrewkaraman.survival.core.GameWorld;
import com.andrewkaraman.survival.core.MyGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by KaramanA on 26.11.2014.
 */
public class InventoryScreen extends AbstractScreen {

    private GameWorld world;

    @Override
    protected void init() {
        world = MyGame.world;
        super.init();
    }

    @Override
    protected void initUI() {

        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.BACK) {
                    ScreenManager.getInstance().show(Screens.GAME);
                }
                return super.keyDown(event, keycode);
            }
        });

        Table container = new Table();
        container.setFillParent(true);
        container.setSkin(skin);

        List<String> textList = new List<String>(skin, "default");
        textList.setItems("1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1");
        Table table = new Table();
        table.defaults().fill().pad(5);
        table.row();
        table.add(textList);
        table.add();

        container.setDebug(true, true);
        stage.addActor(container);

        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);


    }

    @Override
    public void update(float delta) {

    }

    @Override
    public boolean isNeedToSave() {
        return false;
    }
}

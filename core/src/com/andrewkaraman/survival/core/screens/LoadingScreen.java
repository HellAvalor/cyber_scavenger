package com.andrewkaraman.survival.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by KaramanA on 26.11.2014.
 */
public class LoadingScreen extends AbstractScreen {

    @Override
    protected void initUI() {
        TextButton startButton= new TextButton("Start game", skin);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().show(Screens.GAME);
            }
        });


        Table table = new Table();
        table.setFillParent(true);
        table.setSkin(skin);
        table.defaults().fill().pad(5);
        table.row();
        table.add(startButton).minHeight(60).minWidth(120);

        table.setDebug(true, true);
        stage.addActor(table);

        stage.addListener(new InputListener());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void update(float delta) {

    }
    @Override
    public boolean isNeedToSave() {
        return false;
    }
}

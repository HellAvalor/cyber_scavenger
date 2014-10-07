package com.andreykaraman.survival.screens;

import com.andreykaraman.survival.NewScavengerGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.rotateTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by KaramanA on 07.10.2014.
 */

public class LoadingScreen extends AbstractScreen {
    private AbstractScreen nextScreen;

    public LoadingScreen() {
        super();
        Gdx.app.debug("in loading", "init");
        //TextureRegion region = NewScavengerGame.getInstance().getTexture("loader");
        Texture region = new Texture(Gdx.files.internal("loading.png"));
        Image loadingImage = new Image(region);
        loadingImage.setSize(region.getWidth(), region.getHeight());
        loadingImage.setPosition((NewScavengerGame.getInstance().GAME_WIDTH - loadingImage.getWidth()) / 2, (NewScavengerGame.getInstance().GAME_HEIGHT - loadingImage.getHeight()) / 2);
        loadingImage.setOrigin(loadingImage.getWidth() / 2, loadingImage.getHeight() / 2);
        loadingImage.addAction(forever(sequence(rotateTo(-360f, 1f), rotateTo(0f))));

        this.getStage().addActor(loadingImage);
    }

    public LoadingScreen(AbstractScreen nextScreen) {
        this();
        Gdx.app.debug("in LoadingScreen", "constructor");
        this.setNextScreen(nextScreen);
    }

    public void setNextScreen(AbstractScreen nextScreen) {
        this.nextScreen = nextScreen;
    }

    public void render(float delta) {
        if(getManager().update()) {
            NewScavengerGame.getInstance().setScreen(this.nextScreen);
        }
        super.render(delta);
    }

    public void hide() {
        //Gdx.app.log(this.getClass().getName(), "hide");
    }
}
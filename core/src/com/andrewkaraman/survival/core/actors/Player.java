package com.andrewkaraman.survival.core.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

/**
 * Created by KaramanA on 15.10.2014.
 */
public class Player extends Actor {

    InputListener listener;

    Texture region;
    private final String LOG_CLASS_NAME = this.getClass().getName();

    public Player () {
        region = new Texture(Gdx.files.internal("particle-star.png"));
        listener = new CustomListener();
        addListener(listener);

    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(region, getX(), getY());
    }

    @Override
    public Touchable getTouchable() {
        return super.getTouchable();
    }

    @Override
    public float getWidth() {
        return region.getWidth();
    }

    @Override
    public float getHeight() {
        return region.getHeight();
    }

    public class CustomListener extends InputListener {

        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.log(LOG_CLASS_NAME, "down " + x +" / " +y);
//                actor.moveBy(10,10);
            return true;
        }

        public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            Gdx.app.log(LOG_CLASS_NAME, "up" );
        }

        public void touchDragged(InputEvent event, float x, float y, int pointer){
            Gdx.app.log(LOG_CLASS_NAME, "touchDragged " + x +" / " +y + " actor " + getCenterX() + " / " + getCenterY());
            setCenterPosition(x,y);
        }
    }
}

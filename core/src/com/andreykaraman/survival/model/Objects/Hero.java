package com.andreykaraman.survival.model.Objects;

import com.andreykaraman.survival.screens.GameScreen;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import javax.xml.soap.Text;

/**
 * Created by KaramanA on 13.10.2014.
 */
public class Hero  extends Actor {
//    private Animation walkAnimation;
//    private Animation stayAnimation;
    private Sprite sprite;
//    private boolean flip;
    private float stateTime;
    private GameScreen screen;
    private Texture textureAtlas;

    public Hero(Texture textureAtlas, float x, float y, int width, int height, GameScreen screen) {
        super();
        this.setPosition(x, y);
        this.setSize(width, height);
        this.setScreen(screen);
        this.textureAtlas = textureAtlas;
//        TextureRegion[] walkframes = new TextureRegion[4];
//        for(int i=0; i<walkframes.length; i++) {
//            walkframes[i] = textureAtlas.findRegion("step" + (i + 1));
//        }
//        TextureRegion[] stayframes = new TextureRegion[3];
//        for(int i=0; i<stayframes.length; i++) {
//            stayframes[i] = textureAtlas.findRegion("stay" + (i + 1));
//        }
//        this.setWalkAnimation(new Animation(0.05f, walkframes));
//        this.setStayAnimation(new Animation(0.2f, stayframes));
        this.stop();
//        this.setFliping(false);
        this.setStateTime(0);
    }

    public Sprite getSprite() {
        if (this.sprite == null) {
            this.sprite = new Sprite();
        }
        return this.sprite;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public float getStateTime() {
        return this.stateTime;
    }

    public void setScreen(GameScreen screen) {
        this.screen = screen;
    }

    public GameScreen getScreen() {
        return this.screen;
    }

//    public void setWalkAnimation(Animation animation) {
//        this.walkAnimation = animation;
//    }
//
//    public Animation getWalkAnimation() {
//        return this.walkAnimation;
//    }
//
//    public void setStayAnimation(Animation animation) {
//        this.stayAnimation = animation;
//    }
//
//    public Animation getStayAnimation() {
//        return this.stayAnimation;
//    }

    public void stop() {
        this.clearActions();
        this.setStateTime(0);
    }

    public boolean isWalking() {
        return (this.getActions().size > 0);
    }

//    public void setFliping(boolean flip) {
//        this.flip = flip;
//    }
//
//    public boolean isFliping() {
//        return this.flip;
//    }

    public void act(float delta){
        super.act(delta);

        this.setStateTime(this.getStateTime() + delta);

//        if (this.isWalking()) {
//            this.getSprite().setRegion(this.getWalkAnimation().getKeyFrame(this.getStateTime(), true));
//        } else {
//            this.getSprite().setRegion(this.getStayAnimation().getKeyFrame(this.getStateTime(), true));
//        }
        this.getSprite().setTexture(textureAtlas);
        this.getSprite().setPosition(this.getX(), this.getY());
        this.getSprite().setSize(this.getWidth(), this.getHeight());

//        if (this.isFliping()) {
//            this.getSprite().flip(true, false);
//        }
    }

    public void draw(SpriteBatch batch, float parentAlpha) {
        this.getSprite().draw(batch);
    }

    public void walkTo(float x) {
        this.stop();
        this.setStateTime(0);
//        if (x > this.getX()) {
//            this.setFliping(false);
//        } else {
//            this.setFliping(true);
//        }
        float halfHero = this.getWidth() / 2f;
        float heroDuration = this.getDuration(this.getX() - (x - halfHero));
        this.addAction(sequence(moveTo(x - halfHero, this.getY(), heroDuration), run(new Runnable(){
            @Override
            public void run() {
                setStateTime(0);
            }

        })));
    }

    public float getDuration(float distance) {
        return ((Math.abs(distance) / 100) * 0.5f);
    }
}

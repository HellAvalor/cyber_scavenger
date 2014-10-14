package com.andreykaraman.survival.screens;

import com.andreykaraman.survival.model.Objects.Hero;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

/**
 * Created by KaramanA on 07.10.2014.
 */

public class GameScreen extends AbstractScreen {

    private Stage worldStage;
    private Hero hero;
    private int heroWidth = 0;
    private int heroHeight = 0;

    public GameScreen() {
        super();
        Gdx.app.log(this.getClass().getName(), "GameScreen");
        this.worldStage = new Stage();

    }

    public void show(){
        Gdx.app.log(this.getClass().getName(), "show");
        this.addHero();
        this.showWorld();
        super.show();

    }

    public void showWorld() {
        Gdx.app.log(this.getClass().getName(), "showWorld");
        this.setStage(worldStage);
        this.hero.stop();
    }

    public void dispose() {
        Gdx.app.log(this.getClass().getName(), "dispose");
        super.dispose();
//        EvilHunterGame.getInstance().saveInfo(this.screenName + "_screen_x", this.getHero().getX());
//        if (this.screenConfig != null) this.screenConfig = null;
        if (this.hero != null) {
            this.hero.clear();
            this.hero = null;
        }
//        if (this.level != null) {
//            this.level.clear();
//            this.level = null;
//        }
//        if (this.textureAtlas != null) {
//            this.textureAtlas.dispose();
//        }
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Hero getHero() {
        return this.hero;
    }

    @Override
    public void loadResources() {
        Gdx.app.log(this.getClass().getName(), "loadResources");
//        getManager().load("loaded", Texture.class);
//        Gdx.app.debug("in gamescreen", "loadResources loaded.png");
//        Texture thumbnail = getManager().get("loaded", Texture.class);

          getManager().load("loaded.png", Texture.class);
//        if (Gdx.files.internal("loaded.png").exists()) {
//            getManager().load("loaded.png", Texture.class);
//        } else {
//            getManager().load(EvilHunterGame.getInstance().getChapterInfo(this.chapter).getString("directory") + "hero.pack", TextureAtlas.class);
//        }
//        if (Gdx.files.internal(getScreenConfig().getString("directory") + "theme.ogg").exists()) {
//            this.setMusic(Gdx.audio.newMusic(Gdx.files.internal(getScreenConfig().getString("directory") + "theme.ogg")));
//        }

    }

    public void addHero() {
        Gdx.app.log(this.getClass().getName(), "addHero");
        Texture hero;
        if (getManager().isLoaded("loaded.png")) {
            hero = getManager().get("loaded.png", Texture.class);
        } else {
//            getManager().load("loaded.png", Texture.class);
            hero = getManager().get("loaded.png", Texture.class);
        }

//        if (getManager().isLoaded(this.getScreenConfig().getString("directory") + "hero.pack")) {
//            hero = getManager().get(this.getScreenConfig().getString("directory") + "hero.pack", TextureAtlas.class);
//        } else {
//            hero = getManager().get(EvilHunterGame.getInstance().getChapterInfo(this.chapter).getString("directory") + "hero.pack", TextureAtlas.class);
//        }
//        float heroX =
//                EvilHunterGame.getInstance().getSave().contains(this.screenName + "_screen_x") ?
//                        EvilHunterGame.getInstance().getSave().getFloat(this.screenName + "_screen_x")
//                        :
//                        this.getScreenConfig().get("hero").getInt("x");
        float heroX = 15;
        float heroy = 15;
        this.setHero(new Hero(hero, heroX, heroy, this.getHeroWidth(),this.getHeroHeight(), this));
//        this.setHero(new Hero(hero, heroX, this.getScreenConfig().get("hero").getInt("y"), this.getHeroWidth(),this.getHeroHeight(), this));
        this.getHero().setTouchable(Touchable.disabled);
        this.worldStage.addActor(this.getHero());
        Gdx.app.log(this.getClass().getName(), "addActor(this.getHero()");
    }

    public int getHeroWidth() {
        if (this.heroWidth == 0) {
//            this.heroWidth = this.getScreenConfig().get("hero").getInt("width");
            this.heroWidth = 50;
        }

        return this.heroWidth;
    }

    public int getHeroHeight() {
        if (this.heroHeight == 0) {
//            this.heroHeight = this.getScreenConfig().get("hero").getInt("height");
            this.heroHeight = 50;
        }

        return this.heroHeight;
    }


    private class WalkListener extends InputListener {
        private GameScreen screen;

        public WalkListener(GameScreen screen) {
            super();
            this.setScreen(screen);
        }

        public GameScreen getScreen() {
            return this.screen;
        }

        public void setScreen(GameScreen screen) {
            this.screen = screen;
        }

        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            return true;
        }

        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            this.getScreen().getHero().walkTo(x);
        }
    }
}
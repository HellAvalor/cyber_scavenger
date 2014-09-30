package com.andreykaraman.survival.screens;

import com.andreykaraman.survival.CSurv;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by KaramanA on 26.09.2014.
 */
public class IntroScreen implements Screen, InputProcessor {
    CSurv game;
    private int width, height;
    private Texture bgTexture;
    private SpriteBatch spriteBatch;
    public OrthographicCamera cam;
    public float ppuX;	// pixels per unit on the X axis
    public float ppuY;	// pixels per unit on the Y axis
    float CAMERA_WIDTH = 16F;
    float CAMERA_HEIGHT = 8F;

    Texture texture;
    public Map<String, TextureRegion> textureRegions;// = new HashMap<String, TextureRegion>();
    public boolean soundOn;
    Sound sound;
    /** Called when the activity is first created. */
    @Override
    public void show() {
        startedPlaying = false;
        timeLeft = 0;
        soundOn = game.getPrefs().getInteger("sound-on") == 1 ? true : false;

        CAMERA_WIDTH =  CAMERA_HEIGHT* Gdx.graphics.getWidth()/Gdx.graphics.getHeight();

        ppuX = (float)width / CAMERA_WIDTH;
        ppuY = (float)height / CAMERA_HEIGHT;
        textureRegions = new HashMap<String, TextureRegion>();
        bgTexture = new Texture(Gdx.files.internal("images/bombermanmain.png"));
        spriteBatch = new SpriteBatch();
        this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
        this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
        loadSounds();
        loadTextures();
        Gdx.input.setInputProcessor(this);

    }

    private void loadTextures(){
        texture  = new Texture(Gdx.files.internal("images/atlas.png"));
        TextureRegion tmpLeftRight[][] = TextureRegion.split(texture, texture.getWidth()/ 2, texture.getHeight() );

        TextureRegion tmpMain[][] = tmpLeftRight[0][0].split(tmpLeftRight[0][0].getRegionWidth(), tmpLeftRight[0][0].getRegionHeight()/2);
        TextureRegion tmpTop[][] =  tmpMain[0][0].split(tmpMain[0][0].getRegionWidth()/2, tmpMain[0][0].getRegionHeight());

        TextureRegion tmp[][] = tmpTop[0][0].split(tmpTop[0][0].getRegionWidth()/ 8, tmpTop[0][0].getRegionHeight() / 16);

        textureRegions.put("Door", tmp[0][0]);
        textureRegions.put("HardBrick", tmp[0][1]);
        textureRegions.put("wallpass", tmp[0][2]);
        textureRegions.put("flames", tmp[0][3]);
        textureRegions.put("bombs", tmp[1][0]);
        textureRegions.put("speed", tmp[1][1]);
        textureRegions.put("detonator", tmp[1][2]);
        textureRegions.put("bombpass", tmp[1][3]);

        TextureRegion tmpBot[][] = tmpMain[1][0].split(tmpMain[1][0].getRegionWidth()/2, tmpMain[1][0].getRegionHeight());

        TextureRegion tmpFont[][] =  tmpBot[0][0].split(tmpBot[0][0].getRegionWidth()/ 4, tmpBot[0][0].getRegionHeight() / 8);
        textureRegions.put("1",  tmpFont[0][0]);
        textureRegions.put("2",  tmpFont[0][1]);
        textureRegions.put("3",  tmpFont[0][2]);
        textureRegions.put("4",  tmpFont[0][3]);
        textureRegions.put("5",  tmpFont[1][0]);
        textureRegions.put("6",  tmpFont[1][1]);
        textureRegions.put("7",  tmpFont[1][2]);
        textureRegions.put("8",  tmpFont[1][3]);
        textureRegions.put("9",  tmpFont[2][0]);
        textureRegions.put("0",  tmpFont[2][1]);

        textureRegions.put("t",  tmpFont[3][0]);
        textureRegions.put("i",  tmpFont[3][1]);
        textureRegions.put("m",  tmpFont[3][2]);
        textureRegions.put("e",  tmpFont[3][3]);

        textureRegions.put("l",  tmpFont[4][0]);
        textureRegions.put("f",  tmpFont[4][1]);
        textureRegions.put("lG",  tmpFont[4][2]);
        textureRegions.put("zG",  tmpFont[4][3]);

        textureRegions.put("cG",  tmpFont[7][2]);
        textureRegions.put("iG",  tmpFont[7][1]);
        textureRegions.put("sG",  tmpFont[7][0]);
        textureRegions.put("tG",  tmpFont[5][0]);
        textureRegions.put("aG",  tmpFont[5][1]);
        textureRegions.put("gG",  tmpFont[5][2]);
        textureRegions.put("eG",  tmpFont[5][3]);

        textureRegions.put("bG",  tmpFont[6][0]);
        textureRegions.put("oG",  tmpFont[6][1]);
        textureRegions.put("nG",  tmpFont[6][2]);
        textureRegions.put("uG",  tmpFont[6][3]);

        textureRegions.put("circle", tmpFont[6][3]);

    }
    public void showMenu(){

    }

    public void showBG(){
        float offset = (CAMERA_WIDTH-8F)/2;
        spriteBatch.draw(bgTexture,offset*ppuX, 4F*ppuY, 8F*ppuX, 4F*ppuY);

        spriteBatch.draw(textureRegions.get("cG"),offset*ppuX, 2F*ppuY, 1F*ppuX, 1F*ppuY);
        spriteBatch.draw(textureRegions.get("oG"),(offset+1F)*ppuX, 2F*ppuY, 1F*ppuX, 1F*ppuY);
        spriteBatch.draw(textureRegions.get("nG"),(offset+2F)*ppuX, 2F*ppuY, 1F*ppuX, 1F*ppuY);
        spriteBatch.draw(textureRegions.get("tG"),(offset+3F)*ppuX, 2F*ppuY, 1F*ppuX, 1F*ppuY);
        spriteBatch.draw(textureRegions.get("iG"),(offset+4F)*ppuX, 2F*ppuY, 1F*ppuX, 1F*ppuY);
        spriteBatch.draw(textureRegions.get("nG"),(offset+5F)*ppuX, 2F*ppuY, 1F*ppuX, 1F*ppuY);
        spriteBatch.draw(textureRegions.get("uG"),(offset+6F)*ppuX, 2F*ppuY, 1F*ppuX, 1F*ppuY);
        spriteBatch.draw(textureRegions.get("eG"),(offset+7F)*ppuX, 2F*ppuY, 1F*ppuX, 1F*ppuY);

        spriteBatch.draw(textureRegions.get("sG"),offset*ppuX, 0.5F*ppuY, 1F*ppuX, 1F*ppuY);
        spriteBatch.draw(textureRegions.get("eG"),(offset+1F)*ppuX, 0.5F*ppuY, 1F*ppuX, 1F*ppuY);
        spriteBatch.draw(textureRegions.get("tG"),(offset+2F)*ppuX, 0.5F*ppuY, 1F*ppuX, 1F*ppuY);
        spriteBatch.draw(textureRegions.get("tG"),(offset+3F)*ppuX, 0.5F*ppuY, 1F*ppuX, 1F*ppuY);
        spriteBatch.draw(textureRegions.get("iG"),(offset+4F)*ppuX, 0.5F*ppuY, 1F*ppuX, 1F*ppuY);
        spriteBatch.draw(textureRegions.get("nG"),(offset+5F)*ppuX, 0.5F*ppuY, 1F*ppuX, 1F*ppuY);
        spriteBatch.draw(textureRegions.get("gG"),(offset+6F)*ppuX, 0.5F*ppuY, 1F*ppuX, 1F*ppuY);
        spriteBatch.draw(textureRegions.get("sG"),(offset+7F)*ppuX, 0.5F*ppuY, 1F*ppuX, 1F*ppuY);

    }
    private void loadSounds(){
      //     sound = Gdx.audio.newSound(Gdx.files.internal("audio/intro.ogg"));

    }
    public IntroScreen(CSurv game){
        this.game = game;
    }
    @Override
    public boolean keyUp(int keycode) {
        return true;
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    public void setSize (int w, int h) {
        this.width = w;
        this.height = h;
        ppuX = (float)width / CAMERA_WIDTH;
        ppuY = (float)height / CAMERA_HEIGHT;
        //this.world.setSize (w,h);
    }

    @Override
    public void resize(int width, int height) {
        setSize(width, height);
        this.width = width;
        this.height = height;
    }

    boolean startedPlaying;

    private void playMusic(){
        if(!startedPlaying &&  soundOn){
            sound.loop(0.8F);
            startedPlaying = true;
        }
    }

    float timeLeft;

    @Override
    public void render(float delta) {

        timeLeft+=delta;
        Gdx.gl.glClearColor(0,0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        showBG();
        showMenu();
        spriteBatch.end();
        try{
            if(timeLeft>=0.6 && soundOn) playMusic();
        }
        catch(Exception e){
            Gdx.app.error("sound err", "err");}
    }

    @Override
    public void pause() {
        sound.stop();
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        startedPlaying = false;
        playMusic();
        // TODO Auto-generated method stub
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        try{
            sound.stop();
            sound.dispose();
            spriteBatch.dispose();
            texture.dispose();
            textureRegions.clear();
        }
        catch(Exception e){

        }
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {

        // TODO Auto-generated method stub
        return false;
    }

    //@Override
    public boolean touchMoved(int x, int y) {

        //ChangeNavigation(x,y);
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int x, int y) {
        //ChangeNavigation(x,y);
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {

        if((height-y)/ppuY >=2F && (height-y)/ppuY <=4F ){

            dispose();
            if(!game.getStage().equals("51"))
                game.stageScreen();
            else
                game.goToWinScreen();
        }
        if((height-y)/ppuY >=0.5F && (height-y)/ppuY <2F ){
            dispose();
          //  game.setScreen(game.settings);
        }

        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        return true;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        if (!Gdx.app.getType().equals(Application.ApplicationType.Android))
            return false;
        return true;
    }
}

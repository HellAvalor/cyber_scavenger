package com.andreykaraman.survival.controllers;

import com.andreykaraman.survival.CSurv;
import com.andreykaraman.survival.model.World;
import com.andreykaraman.survival.view.WorldRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

import java.util.Map;

/**
 * Created by KaramanA on 26.09.2014.
 */
public class WorldController implements Screen, InputProcessor {

    //public static enum GameStatus {RUNNING, PAUSED};
    //private GameStatus gameStatus;
    private World world;
    private WorldRenderer renderer;
    private WorldController	controller;
    public CSurv game;
    private int width, height;
    WalkingControl control;
    WalkingControlArrows controlArrows;

    Map<String, Integer> pointers;
    //private  Preferences prefs;
    //private int offsetX, offsetY;
    //private float xToY;
    public GameScreen(CSurv game){
        this.game = game;
    }

    public void preLoad(){
        renderer = new WorldRenderer();
    }

    @Override
    public void show() {
        game.checkLeft();
        //gameStatus = GameStatus.RUNNING;
        //Log.e("GameScreen","show");
        //offsetX = 0;
        dead = false;
        float CAMERA_WIDTH =WorldRenderer.CAMERA_WIDTH;
        float CAMERA_HEIGHT = WorldRenderer.CAMERA_HEIGHT;
        CAMERA_WIDTH =  CAMERA_HEIGHT* Gdx.graphics.getWidth()/Gdx.graphics.getHeight();

        WorldController.soundOn = game.getPrefs().getInteger("sound-on") == 1 ? true : false;
        WorldController.joy = game.getPrefs().getInteger("controller-type") == 1 ? true : false;
        world = new World(game.getPrefs()/*(int)CAMERA_WIDTH+2, (int)CAMERA_HEIGHT*/);
        //renderer = new WorldRenderer(world, CAMERA_WIDTH,CAMERA_HEIGHT,false);
        //preLoad();
        renderer.init(world, CAMERA_WIDTH,CAMERA_HEIGHT,false);
        controller = new WorldController(world, renderer);
        if(WorldController.joy){
            control =world.getWalkingControl();
            WalkingControl.Coefficient = 1+0.3F*game.getPrefs().getInteger("controller-size");
            WalkingControl.Opacity = game.getPrefs().getInteger("opacity");
        }
        else{
            WalkingControlArrows.Coefficient = 1+0.3F*game.getPrefs().getInteger("controller-size");
            WalkingControlArrows.Opacity = game.getPrefs().getInteger("opacity");
            controlArrows =world.getWalkingControlArrows();
        }



        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
        task = null;
        timer = null;
        won = false;
        pointers = new HashMap<String, Integer>();
        clearPointers();

        //renderer.sounds.get("stage-theme").play();
    }

    private void clearPointers(){
        pointers.put("joy", -1);
    }
    TimerTask task;
    Timer timer;
    boolean won;
    private void createTimer(){
        if( timer == null){
            task = new TimerTask(){
                public void run()
                {
                    won = true;
					/*renderer.disposeSounds();
					renderer.clearTimer();
					game.setPoints(world.points);
					timer.cancel();
					timer.purge();
					timer = null;
					game.nextStage();*/
                }
            };
            timer=new Timer();

            timer.schedule(task, 4000);
        }
    }

    public void dead(){
        dead = true;
        int left = game.getPrefs().getInteger("left")-1;
        game.losePowers();
        game.getPrefs().putInteger("left", left);
        game.getPrefs().flush();
        game.setPoints(world.points);
    }
    boolean dead;
    //float times = 0;
    private void renderSave(float delta){
        //Log.e("renderGS","1");
        //times+=0.1F;
        if(!dead && world.getBomberman().getState() == Bomberman.State.DYING)
            dead();
        if(world.getBomberman().getState() == Bomberman.State.DEAD)
        {

            int left = game.getPrefs().getInteger("left");//-1;
            if(left<0)
            {
                //renderer.dispose();
                //controller.dispose();

                this.dispose();
                game.gameOver();
            }
            else
            {
                if(!world.inreasedPowerUp.equals("")){
                    game.getPrefs().putInteger(world.inreasedPowerUp, 	game.getPrefs().getInteger(world.inreasedPowerUp)+1);
                    game.getPrefs().flush();
                }
                game.losePowers();
                //renderer.stopMusic();
                //renderer.dispose();
                //controller.dispose();

                //dead();

                this.dispose();
                //game.setScreen(game.stage);
                game.stageScreen();
            }
            return;
        }

        if(world.getBomberman().getState() == Bomberman.State.WON)
            if(!won) {createTimer(); /*return;*/}
            else
            {
                //this.pause();
                if(!world.inreasedPowerUp.equals("")){
                    game.getPrefs().putInteger(world.inreasedPowerUp, 	game.getPrefs().getInteger(world.inreasedPowerUp)+1);
                    game.getPrefs().flush();
                }

                timer.cancel();
                timer.purge();
                timer = null;

                this.dispose();
                game.setPoints(world.points);
                game.nextStage();

                return;
            }

        if(1F/delta>80) delta = 1F/60;
        if(!WorldController.paused && world.getBomberman().getState() != Bomberman.State.WON /*&& !world.getBomberman().isDead() && !(world.getBomberman().getState() == Bomberman.State.WON)*/){
            controller.update(delta);
        }
        Gdx.gl20.glClearColor(0, 118F/255, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
    }

    @Override
    public void render(float delta) {
        renderSave(delta);
    }

    @Override
    public void resize(int width, int height) {
        renderer.setSize(width, height);
        this.width = width;
        this.height = height;
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void pause() {
        //Log.e("pause", "pause");
        if(!WorldController.paused){

            //gameStatus = GameStatus.PAUSED;
            renderer.stopMainTheme();
            WorldController.paused = true;
            //renderer.drawPauseControlls();
        }
		/*else
			this.resume();*/
    }


    @Override
    public void resume() {
        //Log.e("resume", "resume");
        renderer.playMainTheme();
        //gameStatus = GameStatus.RUNNING;
        WorldController.paused = false;
    }

    @Override
    public void dispose() {
        //Log.e("dispose", "+");

        renderer.dispose();
        controller.dispose();
        Gdx.input.setInputProcessor(null);
        Gdx.input.setCatchBackKey(false);
    }

    // * InputProcessor methods ***************************//

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Keys.BACK || WorldController.paused) {
            return false;
        }
        controller.hasKeyBoard = true;

        if(keycode==Keys.SPACE || keycode == Keys.DPAD_CENTER)
            controller.bombPressed(Math.round(controller.bomberman.getPosition().x), Math.round(controller.bomberman.getPosition().y));
        if(keycode==Keys.SHIFT_LEFT || keycode == Keys.BUTTON_X)
            controller.boomBombPressed();
        if (keycode == Keys.LEFT)
            controller.leftPressed();
        if (keycode == Keys.RIGHT)
            controller.rightPressed();
        if (keycode == Keys.UP)
            controller.upPressed();
        if (keycode == Keys.DOWN)
            controller.downPressed();

        if (keycode == Keys.A)
            controller.leftPressed();
        if (keycode == Keys.D)
            controller.rightPressed();
        if (keycode == Keys.W)
            controller.upPressed();
        if (keycode == Keys.S)
            controller.downPressed();
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Keys.BACK || WorldController.paused) {

            this.pause();
            //Log.e("key", "back");
            return false;
        }
        controller.hasKeyBoard = true;
        if (keycode == Keys.LEFT)
            controller.leftReleased();
        if (keycode == Keys.RIGHT)
            controller.rightReleased();
        if (keycode == Keys.UP)
            controller.upReleased();
        if (keycode == Keys.DOWN)
            controller.downReleased();

        if (keycode == Keys.A)
            controller.leftReleased();
        if (keycode == Keys.D)
            controller.rightReleased();
        if (keycode == Keys.W)
            controller.upReleased();
        if (keycode == Keys.S)
            controller.downReleased();
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }
    private void withControl(int x, int y, int pointer){
        float calcX = x/renderer.ppuX-(world.getWalkingControl().getPosition().x+WalkingControl.SIZE*WalkingControl.Coefficient/2) ;
        float calcY = (height-y)/renderer.ppuY-(world.getWalkingControl().getPosition().y+WalkingControl.SIZE*WalkingControl.Coefficient/2);
        float xOff= x-(world.getWalkingControl().getPosition().x+WalkingControl.SIZE*WalkingControl.Coefficient/2)*renderer.ppuX ;
        float yOff = (height-y)-(world.getWalkingControl().getPosition().y+WalkingControl.SIZE*WalkingControl.Coefficient/2)*renderer.ppuY;

        if(((calcX*calcX + calcY* calcY)/(WalkingControl.Coefficient*WalkingControl.Coefficient)<=WalkingControl.CONTRLRADIUS*WalkingControl.CONTRLRADIUS)
                && xOff*xOff+ yOff* yOff >= 400){
            controller.resetWay();
            pointers.put("joy",	pointer);
            double angle = Math.atan(calcY/calcX)*180/Math.PI;

            if(angle>0 &&calcY<0)
                angle+=180;
            if(angle <0)
                if(calcX<0)
                    angle=180+angle;
                else

                    angle+=360;
            if(angle>30 && angle<150)
                controller.upPressed();
            if(angle>210 && angle<330)
                controller.downPressed();

            if(angle>120 && angle<240)
                controller.leftPressed();
            if(angle<60 || angle>300)
                controller.rightPressed();

            renderer.angle = (float)(angle*Math.PI/180);
            control.getOffsetPosition().x = (float)((calcX*calcX + calcY* calcY>1F)? Math.cos(renderer.angle)*WalkingControl.Coefficient*0.75F: calcX*WalkingControl.Coefficient);
            control.getOffsetPosition().y = (float)((calcX*calcX + calcY* calcY>1F)? Math.sin(renderer.angle)*WalkingControl.Coefficient*0.75F: calcY*WalkingControl.Coefficient);
        }
        else if(pointer == pointers.get("joy")){
            controller.resetWay();
            control.getOffsetPosition().x = 0;
            control.getOffsetPosition().y = 0;
        }
    }

    private void withArrowControl(int x, int y, int pointer){
        boolean arrow = false;
        if(x/renderer.ppuX >= controlArrows.positionLeft.x && x/renderer.ppuX <= controlArrows.positionLeft.x+WalkingControlArrows .BSIZE* WalkingControlArrows .Coefficient &&
                (height-y)/renderer.ppuY >= controlArrows.positionLeft.y && (height-y)/renderer.ppuY <= controlArrows.positionLeft.y+WalkingControlArrows .BSIZE* WalkingControlArrows .Coefficient	)
        //
        {
            //Log.e("left", "pressed");
            arrow= true;
            controller.resetWay();
            pointers.put("joy",	pointer);
            controller.leftPressed();
        }

        if(!arrow &&x/renderer.ppuX >= controlArrows.positionRight.x && x/renderer.ppuX <= controlArrows.positionRight.x+WalkingControlArrows .BSIZE* WalkingControlArrows .Coefficient &&
                (height-y)/renderer.ppuY >= controlArrows.positionRight.y && (height-y)/renderer.ppuY <= controlArrows.positionRight.y+WalkingControlArrows .BSIZE* WalkingControlArrows .Coefficient	)
        //
        {
            //Log.e("Right", "pressed");
            arrow= true;
            controller.resetWay();
            pointers.put("joy",	pointer);
            controller.rightPressed();
        }
        if(!arrow &&x/renderer.ppuX >= controlArrows.positionUp.x && x/renderer.ppuX <= controlArrows.positionUp.x+WalkingControlArrows .BSIZE* WalkingControlArrows .Coefficient &&
                (height-y)/renderer.ppuY >= controlArrows.positionUp.y && (height-y)/renderer.ppuY <= controlArrows.positionUp.y+WalkingControlArrows .BSIZE* WalkingControlArrows .Coefficient	)

        {
            //Log.e("Up", "pressed");
            arrow= true;
            controller.resetWay();
            pointers.put("joy",	pointer);
            controller.upPressed();
        }
        if(!arrow &&x/renderer.ppuX >= controlArrows.positionDown.x && x/renderer.ppuX <= controlArrows.positionDown.x+WalkingControlArrows .BSIZE* WalkingControlArrows .Coefficient &&
                (height-y)/renderer.ppuY >= controlArrows.positionDown.y && (height-y)/renderer.ppuY <= controlArrows.positionDown.y+WalkingControlArrows.BSIZE* WalkingControlArrows .Coefficient	){
            //Log.e("Down", "pressed");
            arrow= true;
            controller.resetWay();
            pointers.put("joy",	pointer);
            controller.downPressed();
        }

        if(!arrow && pointer == pointers.get("joy")){
            controller.resetWay();
            //Log.e("reset", "=\\");
            if(WorldController.joy){
                control.getOffsetPosition().x = 0;
                control.getOffsetPosition().y = 0;
            }
        }
    }
    private void ChangeNavigation(int x, int y, int pointer){
        if(WorldController.joy)
            withControl(x, y, pointer);
        else
            withArrowControl(x, y, pointer);
    }
    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {

        if (!Gdx.app.getType().equals(ApplicationType.Android))
            return false;
        if(!controller.bomberman.isDead()  && !WorldController.paused&& !(world.getBomberman().getState() == Bomberman.State.WON))
            ChangeNavigation(x,y, pointer);
        return true;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        if (!Gdx.app.getType().equals(ApplicationType.Android) )
            return false;

        if ( x/renderer.ppuX >= WorldRenderer.CAMERA_WIDTH/2+1F && x/renderer.ppuX<=WorldRenderer.CAMERA_WIDTH/2+3F &&
                y/renderer.ppuY >= WorldRenderer.CAMERA_HEIGHT/2-1F && y/renderer.ppuY<=WorldRenderer.CAMERA_HEIGHT/2+1F && WorldController.paused)
            this.resume();
        //Log.e("resume","go");

        if ( x/renderer.ppuX >= WorldRenderer.CAMERA_WIDTH/2-3F && x/renderer.ppuX<=WorldRenderer.CAMERA_WIDTH/2-1F &&
                y/renderer.ppuY >= WorldRenderer.CAMERA_HEIGHT/2-1F && y/renderer.ppuY<=WorldRenderer.CAMERA_HEIGHT/2+1F&& WorldController.paused){
            renderer.dispose();
            controller.dispose();
            dispose() ;
            game.setScreen(game.intro);
        }

        if(WorldController.joy){
            if(x/renderer.ppuX >= control.bombPosition.x && x/renderer.ppuX <= control.bombPosition.x+WalkingControl .BSIZE* WalkingControl .Coefficient &&
                    (height-y)/renderer.ppuY >= control.bombPosition.y && (height-y)/renderer.ppuY <= control.bombPosition.y+WalkingControl .BSIZE* WalkingControl .Coefficient	)
                controller.bombPressed(Math.round(controller.bomberman.getPosition().x), Math.round(controller.bomberman.getPosition().y));

            if(x/renderer.ppuX >= control.detonatorPosition.x && x/renderer.ppuX <= control.detonatorPosition.x+WalkingControl .BSIZE* WalkingControl .Coefficient &&
                    (height-y)/renderer.ppuY >= control.detonatorPosition.y && (height-y)/renderer.ppuY <=control.detonatorPosition.y+WalkingControl .BSIZE* WalkingControl .Coefficient	)
                controller.boomBombPressed();
        }
        else{
            if(x/renderer.ppuX >= controlArrows.bombPosition.x && x/renderer.ppuX <= controlArrows.bombPosition.x+WalkingControlArrows .BSIZE* WalkingControlArrows .Coefficient &&
                    (height-y)/renderer.ppuY >= controlArrows.bombPosition.y && (height-y)/renderer.ppuY <= controlArrows.bombPosition.y+WalkingControlArrows .BSIZE* WalkingControlArrows .Coefficient	)
                controller.bombPressed(Math.round(controller.bomberman.getPosition().x), Math.round(controller.bomberman.getPosition().y));

            if(x/renderer.ppuX >= controlArrows.detonatorPosition.x && x/renderer.ppuX <= controlArrows.detonatorPosition.x+WalkingControlArrows .BSIZE* WalkingControlArrows .Coefficient &&
                    (height-y)/renderer.ppuY >= controlArrows.detonatorPosition.y && (height-y)/renderer.ppuY <= controlArrows.detonatorPosition.y+WalkingControlArrows .BSIZE* WalkingControlArrows .Coefficient	)
                controller.boomBombPressed();
        }
        if(pointer == pointers.get("joy") /*|| pointers.get("joy") == -1*/){
            controller.resetWay();
            if(WorldController.joy){
                control.getOffsetPosition().x = 0;
                control.getOffsetPosition().y = 0;
            }
            pointers.put("joy",	-1);
        }
        return true;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        if(!world.getBomberman().isDead() && !WorldController.paused && !(world.getBomberman().getState() == Bomberman.State.WON))
            ChangeNavigation(x,y, pointer);
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
}
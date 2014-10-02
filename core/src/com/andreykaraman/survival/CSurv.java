package com.andreykaraman.survival;

import com.andreykaraman.survival.screens.GameScreen;
import com.andreykaraman.survival.screens.IntroScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Preferences;

/**
 * Created by KaramanA on 25.09.2014.
 */
public class CSurv extends Game {

    private Preferences prefs;// = Gdx.app.getPreferences("my-preferences");
    public IntroScreen intro;
//    public SettingsScreen settings;
//    public CustomArrowsScreen customArrowsScreen;
    public GameScreen game;
//    public StageScreen stage;
//    public WinScreen  win;
//    public GameOverScreen gameOver;
      AndroidLauncher app;

    public CSurv (AndroidLauncher app){
        this.app = app;
    }

    public void goHome(){
        app.goHome();
    }


    public void create() {
//        stageNumber = getStagePref();
//        prefs = Gdx.app.getPreferences("bomber-pref");
//        setts = Gdx.app.getPreferences("setts");
//        if(getStage() == "") {
//            //Log.e("gg","+");
//            setDefault();
//            setDefSettings();
//        }
//        checkSettings();
//        checkLeft();
//        gameOver = new  GameOverScreen(this);
//        stage = new StageScreen(this);
        intro = new IntroScreen(this);
        game = new GameScreen(this);
//        settings = new  SettingsScreen(this);
//        customArrowsScreen = new CustomArrowsScreen(this);

//        prefs.flush();
        setScreen(intro);

    }

    public void checkLeft(){

        //Log.e("left", Integer.toString(prefs.getInteger("left")));
//        if(prefs.getInteger("left")<0)
//            setDefault();
    }

    private void checkSettings(){
//        if(!prefs.contains("sound-on") || !prefs.contains("controller-size")
//                || !prefs.contains("controller-type") || !prefs.contains("x")  || !prefs.contains("y")
//                || !prefs.contains("xbomb")  || !prefs.contains("ybomb")
//                || !prefs.contains("opacity")
//                || !prefs.contains("xdetonator")  || !prefs.contains("ydetonator")
//                || !prefs.contains("xl")  || !prefs.contains("yl")
//                || !prefs.contains("xr")  || !prefs.contains("yr")
//                || !prefs.contains("xu")  || !prefs.contains("yu")
//                || !prefs.contains("xd")  || !prefs.contains("yd"))
            setDefSettings();

    }
    private void setDefSettings(){
        prefs.putInteger("controller-size", 1);
//        prefs.putInteger("sound-on", 1);

        prefs.putInteger("controller-type", 1);
        prefs.putFloat("x", 0);
        prefs.putFloat("y", 0);
        prefs.putFloat("xl", 3F);
        prefs.putFloat("yl", 3F);
        prefs.putFloat("xr", 7F);
        prefs.putFloat("yr", 3F);
        prefs.putFloat("xu", 5F);
        prefs.putFloat("yu", 5F);
        prefs.putFloat("xd", 5F);
        prefs.putFloat("yd", 1F);


        prefs.putInteger("opacity", 9);
        prefs.putFloat("xdetonator", 15F);
        prefs.putFloat("ydetonator", 5F);
        prefs.putFloat("xbomb",15F);
        prefs.putFloat("ybomb", 2F);
        prefs.flush();
    }

    private void setDefault(){

//        //Map<String,Integer>  pref = new  HashMap<String,Integer>();
//        prefs.putInteger("bombs", 0);
//        prefs.putInteger("flames", 0);
//        prefs.putInteger("speed", 0);
//        prefs.putInteger("wallpass", 0);
//        prefs.putInteger("detonator", 0);
//        prefs.putInteger("bombpass", 0);
//        prefs.putInteger("score", 0);
//        prefs.putInteger("left", 2);
//        prefs.putString("stage", "1");
//        prefs.putInteger("flamepass", 0);
//
//        //prefs.putInteger("stage", 1);
//        prefs.putInteger("points", 0);
//        //prefs.put(pref);
//        prefs.flush();

    }

    public void updatePrefs(){
//        prefs.flush();
    }

    public void nextStage(){
//        String s = getStage();
//        String sOld = s;
//        if(s.equals("A")) s = "6";
//        if(s.equals("B")) s = "11";
//        if(s.equals("C")) s = "16";
//        if(s.equals("D")) s = "21";
//        if(s.equals("E")) s = "26";
//        if(s.equals("F")) s = "31";
//        if(s.equals("G")) s = "36";
//        if(s.equals("H")) s = "40";
//        if(s.equals("I")) s = "45";
//        if(s.equals("J")) s = "50";
//
//        if(s.equals("5")) s = "A";
//        if(s.equals("10")) s = "B";
//        if(s.equals("15")) s = "C";
//        if(s.equals("20")) s = "D";
//        if(s.equals("25")) s = "E";
//        if(s.equals("30")) s = "F";
//        if(s.equals("35")) s = "G";
//        if(s.equals("39")) s = "H";
//        if(s.equals("44")) s = "I";
//        if(s.equals("49")) s = "J";
//        if(s.equals(sOld)) s = Integer.toString(Integer.parseInt(s)+1);
//        prefs.putString("stage", s);
//        prefs.putInteger("left", prefs.getInteger("left")+1);
//        prefs.flush();
//        //Log.e("next", "true");
//
//        if(!getStage().equals("51"))
//            //setScreen(stage);
//            stageScreen();
//        else
//        {
//            goToWinScreen();
//        }

    }

    public void startFromBegining(){
        setDefault();
        setScreen(intro);
    }
    public void goToWinScreen(){
//        win = new WinScreen(this);
//        //setDefault();
//        setScreen(win);
    }
    public void losePowers(){
//        prefs.putInteger("wallpass", 0);
//        prefs.putInteger("detonator", 0);
//        prefs.putInteger("bombpass", 0);
//        prefs.putInteger("flamepass", 0);
//        prefs.flush();
    }

    public void setPoints(int points){
//        prefs.putInteger("points",  points);
//        prefs.flush();
    }
    public void gameOver(){
		 /*setDefault();
//		 setScreen(stage);*/
//        setDefault();
//        setScreen(gameOver);
    }

    public Preferences  getPrefs(){
        return prefs;
    }


    public void stageScreen(){

        setScreen(game);
        game.preLoad();
    }
    public String getStage()
    {
        //return "A";
        return prefs.getString("stage");
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
    }
}

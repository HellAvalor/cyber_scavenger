package com.andreykaraman.survival.view;

import com.andreykaraman.survival.CSurv;
import com.andreykaraman.survival.controllers.WalkingControl;
import com.andreykaraman.survival.controllers.WalkingControlArrows;
import com.andreykaraman.survival.controllers.WorldController;
import com.andreykaraman.survival.model.Player;
import com.andreykaraman.survival.model.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Timer;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

/**
 * Created by KaramanA on 26.09.2014.
 */
public class WorldRenderer {

    public static float CAMERA_WIDTH = 10f;
    public static float CAMERA_HEIGHT = 14f;

    private World world;
    public OrthographicCamera cam;
//    public Sound sound;

    public Map<String, Sound> sounds = new HashMap<String, Sound>();

    /**
     * for debug rendering *
     */
    ShapeRenderer debugRenderer = new ShapeRenderer();
    ShapeRenderer bgRenderer = new ShapeRenderer();
    /**
     * Textures *
     */

    public double angle;
    private SpriteBatch spriteBatch;
    private boolean debug = false;
    public int width;
    public int height;
    public float ppuX;    // pixels per unit on the X axis
    public float ppuY;    // pixels per unit on the Y axis

//    TimerTask task;
//    Timer timer;

    Texture texture;
    public Map<String, TextureRegion> textureRegions;// = new HashMap<String, TextureRegion>();
    //public  Map<String, Texture> textures = new HashMap<String, Texture>();
    public Map<String, Animation> animations;// = new HashMap<String,  Animation>();
    Sprite arrowLeft;
    Sprite arrowRight;
    Sprite arrowUp;
    Sprite arrowDown;

    public void setSize(int w, int h) {
        this.width = w;
        this.height = h;
        ppuX = (float) width / CAMERA_WIDTH;
        ppuY = (float) height / CAMERA_HEIGHT;
        //this.world.setSize (w,h);
    }

    public void SetCamera(float x, float y) {
        this.cam.position.set(x, y, 0);
        this.cam.update();
    }

    public void init(World world, float w, float h, boolean debug) {

        CAMERA_WIDTH = w;
        CAMERA_HEIGHT = h;
        this.world = world;
        this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
        SetCamera(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f);
        this.cam.update();

        foundDoor = false;
        currentSound = CurrentSound.none;

        this.debug = debug;
        spriteBatch = new SpriteBatch();

        createTimer();
        playMainTheme();
    }

    public WorldRenderer(/*World world, float w, float h, boolean debug*/) {

        //createTimer();
        //playMainTheme();
        animations = new HashMap<String, Animation>();
        textureRegions = new HashMap<String, TextureRegion>();
        loadTextures();
//        loadSounds();
        //loadMusics();

    }

//    private void loadSounds() {
//        sounds.put("stage-main-theme", Gdx.audio.newSound(Gdx.files.internal("audio/stage-main-theme.ogg")));
//        sounds.put("stage-main-theme-find-the-door", Gdx.audio.newSound(Gdx.files.internal("audio/stage-main-theme-find-the-door.ogg")));
//
//		/*musics.put("dying", Gdx.audio.newMusic(Gdx.files.internal("audio/dying.ogg")));
//        musics.put("left-right", Gdx.audio.newMusic(Gdx.files.internal("audio/left-right.ogg")));
//		musics.put("just-died", Gdx.audio.newMusic(Gdx.files.internal("audio/just-died.mp3")));
//		musics.put("up-down", Gdx.audio.newMusic(Gdx.files.internal("audio/up-down.ogg")));
//		musics.put("boom", Gdx.audio.newMusic(Gdx.files.internal("audio/boom.ogg")));
//		musics.put("put-bomb", Gdx.audio.newMusic(Gdx.files.internal("audio/put-bomb.ogg")));*/
//        sounds.put("dying", Gdx.audio.newSound(Gdx.files.internal("audio/dying.ogg")));
//        sounds.put("left-right", Gdx.audio.newSound(Gdx.files.internal("audio/left-right.ogg")));
//        sounds.put("just-died", Gdx.audio.newSound(Gdx.files.internal("audio/just-died.mp3")));
//        sounds.put("level-complete", Gdx.audio.newSound(Gdx.files.internal("audio/level-complete.mp3")));
//        sounds.put("up-down", Gdx.audio.newSound(Gdx.files.internal("audio/up-down.ogg")));
//        sounds.put("boom", Gdx.audio.newSound(Gdx.files.internal("audio/boom.ogg")));
//        sounds.put("put-bomb", Gdx.audio.newSound(Gdx.files.internal("audio/put-bomb.ogg")));
//
//    }

    private void loadRegions() {
        texture = new Texture(Gdx.files.internal("images/atlas.png"));
        TextureRegion tmpLeftRight[][] = TextureRegion.split(texture, texture.getWidth() / 2, texture.getHeight());

        TextureRegion tmpMain[][] = tmpLeftRight[0][0].split(tmpLeftRight[0][0].getRegionWidth(), tmpLeftRight[0][0].getRegionHeight() / 2);
        TextureRegion tmpTop[][] = tmpMain[0][0].split(tmpMain[0][0].getRegionWidth() / 2, tmpMain[0][0].getRegionHeight());

        TextureRegion tmp[][] = tmpTop[0][0].split(tmpTop[0][0].getRegionWidth() / 8, tmpTop[0][0].getRegionHeight() / 16);

        textureRegions.put("Door", tmp[0][0]);
        textureRegions.put("HardBrick", tmp[0][1]);
        textureRegions.put("wallpass", tmp[0][2]);
        textureRegions.put("flames", tmp[0][3]);
        textureRegions.put("bombs", tmp[1][0]);
        textureRegions.put("speed", tmp[1][1]);
        textureRegions.put("detonator", tmp[1][2]);
        textureRegions.put("bombpass", tmp[1][3]);
        textureRegions.put("flamepass", tmp[8][7]);
        textureRegions.put("mystery", tmp[9][7]);
//        loadBrickAnimation(tmp);
//        loadBombAnimation(tmp);
//        loadBoomAnimation(tmp);
//        loadBaloomAnimation(tmp);
//        loadDollAnimation(tmp);
//        loadOnealAnimation(tmp);
//        loadMinvoAnimation(tmp);
//        loadPassAnimation(tmp);
//        loadPontanAnimation(tmp);
//        loadOvapiAnimation(tmp);
//        loadKondoriaAnimation(tmp);

//        TextureRegion tmpBomber[][] = tmpTop[0][1].split(tmpTop[0][1].getRegionWidth(), tmpTop[0][1].getRegionHeight() / 2);
//        loadBombermanAnimation(tmpBomber[0][0].split(tmpBomber[0][0].getRegionWidth() / 8, tmpBomber[0][0].getRegionHeight() / 8));

        TextureRegion controls[][] = tmpLeftRight[0][1].split(tmpLeftRight[0][1].getRegionWidth(), tmpLeftRight[0][1].getRegionHeight() / 2);
        textureRegions.put("navigation-arrows", controls[0][0]);

        TextureRegion controls2[][] = controls[1][0].split(controls[1][0].getRegionWidth() / 2, controls[1][0].getRegionHeight() / 2);
        textureRegions.put("khob", controls2[0][1]);
        arrowLeft = new com.badlogic.gdx.graphics.g2d.Sprite(controls2[0][0].split(controls2[0][0].getRegionWidth() / 2, controls2[0][0].getRegionHeight() / 2)[0][0]);


        arrowLeft.rotate90(true);
        arrowLeft.rotate90(true);
        arrowLeft.rotate90(true);
        arrowRight = new com.badlogic.gdx.graphics.g2d.Sprite(controls2[0][0].split(controls2[0][0].getRegionWidth() / 2, controls2[0][0].getRegionHeight() / 2)[0][0]);

        arrowRight.rotate90(true);

        arrowDown = new com.badlogic.gdx.graphics.g2d.Sprite(controls2[0][0].split(controls2[0][0].getRegionWidth() / 2, controls2[0][0].getRegionHeight() / 2)[0][0]);

        arrowDown.rotate90(true);
        arrowDown.rotate90(true);
        arrowUp = new com.badlogic.gdx.graphics.g2d.Sprite(controls2[0][0].split(controls2[0][0].getRegionWidth() / 2, controls2[0][0].getRegionHeight() / 2)[0][0]);

        textureRegions.put("home", (controls2[1][0].split(controls2[1][0].getRegionWidth() / 2, controls2[1][0].getRegionHeight() / 2))[0][0]);
        textureRegions.put("resume", (controls2[1][0].split(controls2[1][0].getRegionWidth() / 2, controls2[1][0].getRegionHeight() / 2))[0][1]);
        textureRegions.put("fon", (controls2[1][0].split(controls2[1][0].getRegionWidth(), controls2[1][0].getRegionHeight() / 2))[1][0]);

        TextureRegion tmpBot[][] = tmpMain[1][0].split(tmpMain[1][0].getRegionWidth() / 2, tmpMain[1][0].getRegionHeight());

        TextureRegion tmpFont[][] = tmpBot[0][0].split(tmpBot[0][0].getRegionWidth() / 4, tmpBot[0][0].getRegionHeight() / 8);

        textureRegions.put("1", tmpFont[0][0]);
        textureRegions.put("2", tmpFont[0][1]);
        textureRegions.put("3", tmpFont[0][2]);
        textureRegions.put("4", tmpFont[0][3]);
        textureRegions.put("5", tmpFont[1][0]);
        textureRegions.put("6", tmpFont[1][1]);
        textureRegions.put("7", tmpFont[1][2]);
        textureRegions.put("8", tmpFont[1][3]);
        textureRegions.put("9", tmpFont[2][0]);
        textureRegions.put("0", tmpFont[2][1]);

        textureRegions.put("t", tmpFont[3][0]);
        textureRegions.put("i", tmpFont[3][1]);
        textureRegions.put("m", tmpFont[3][2]);
        textureRegions.put("e", tmpFont[3][3]);

        textureRegions.put("l", tmpFont[4][0]);
        textureRegions.put("f", tmpFont[4][1]);

        textureRegions.put("cG", tmpFont[7][2]);
        textureRegions.put("iG", tmpFont[7][1]);
        textureRegions.put("sG", tmpFont[7][0]);
        textureRegions.put("tG", tmpFont[5][0]);
        textureRegions.put("aG", tmpFont[5][1]);
        textureRegions.put("gG", tmpFont[5][2]);
        textureRegions.put("eG", tmpFont[5][3]);

        textureRegions.put("bG", tmpFont[6][0]);
        textureRegions.put("oG", tmpFont[6][1]);
        textureRegions.put("nG", tmpFont[6][2]);
        textureRegions.put("uG", tmpFont[6][3]);
    }


//    private void loadOnealAnimation(TextureRegion tmp[][]) {
//        TextureRegion[] textureFrames = new TextureRegion[7];
//        int index = 0;
//        for (int j = 0; j <= 6; j++)
//            textureFrames[index++] = tmp[8][j];
//
//
//        animations.put(Oneal.Name, new Animation(Oneal.ANIMATIONSPEED, textureFrames));
//    }
//
//    private void loadBombermanAnimation(TextureRegion tmp[][]) {
//        TextureRegion[] textureFrames = new TextureRegion[24];
//        int index = 0;
//        for (int i = 0; i <= 2; i++)
//            for (int j = 0; j <= 7; j++)
//                textureFrames[index++] = tmp[i][j];
//        animations.put("player", new Animation(Bomberman.ANIMATIONSPEED, textureFrames));
//    }
//
//    private void loadBaloomAnimation(TextureRegion tmp[][]) {
//
//        TextureRegion[] textureFrames = new TextureRegion[Balloom.FRAME_COLS * Balloom.FRAME_ROWS];
//        int index = 0;
//        for (int i = 4; i <= 7; i++)
//            for (int j = 0; j <= 3; j++)
//                textureFrames[index++] = tmp[i][j];
//
//
//        animations.put(Balloom.Name, new Animation(2 * Balloom.SPEED, textureFrames));
//    }
//
//
//    private void loadDollAnimation(TextureRegion tmp[][]) {
//
//        TextureRegion[] textureFrames = new TextureRegion[7];
//        int index = 0;
//        for (int j = 0; j <= 6; j++)
//            textureFrames[index++] = tmp[12][j];
//
//
//        animations.put(Doll.Name, new Animation(Doll.ANIMATIONSPEED, textureFrames));
//    }
//
//
//    private void loadMinvoAnimation(TextureRegion tmp[][]) {
//
//        TextureRegion[] textureFrames = new TextureRegion[7];
//        int index = 0;
//        for (int j = 0; j <= 6; j++)
//            textureFrames[index++] = tmp[13][j];
//
//
//        animations.put(Minvo.Name, new Animation(Minvo.ANIMATIONSPEED, textureFrames));
//    }
//
//    private void loadPassAnimation(TextureRegion tmp[][]) {
//        TextureRegion[] textureFrames = new TextureRegion[7];
//        int index = 0;
//        for (int j = 0; j <= 6; j++)
//            textureFrames[index++] = tmp[9][j];
//
//
//        animations.put(Pass.Name, new Animation(Pass.ANIMATIONSPEED, textureFrames));
//    }
//
//    private void loadPontanAnimation(TextureRegion tmp[][]) {
//        TextureRegion[] textureFrames = new TextureRegion[7];
//        int index = 0;
//        for (int j = 0; j <= 6; j++)
//            textureFrames[index++] = tmp[11][j];
//
//
//        animations.put(Pontan.Name, new Animation(Pontan.ANIMATIONSPEED, textureFrames));
//    }
//
//    private void loadKondoriaAnimation(TextureRegion tmp[][]) {
//        TextureRegion[] textureFrames = new TextureRegion[7];
//        int index = 0;
//        for (int j = 0; j <= 6; j++)
//            textureFrames[index++] = tmp[14][j];
//
//
//        animations.put(Kondoria.Name, new Animation(Kondoria.ANIMATIONSPEED, textureFrames));
//    }
//
//    private void loadOvapiAnimation(TextureRegion tmp[][]) {
//        TextureRegion[] textureFrames = new TextureRegion[7];
//        int index = 0;
//        for (int j = 0; j <= 6; j++)
//            textureFrames[index++] = tmp[15][j];
//
//
//        animations.put(Ovapi.Name, new Animation(Ovapi.ANIMATIONSPEED, textureFrames));
//    }
//
//    private void loadBoomAnimation(TextureRegion tmp[][]) {
//        TextureRegion[] textureFrames = new TextureRegion[Boom.FRAME_COLS * Boom.FRAME_ROWS];
//        int index = 0;
//        for (int i = 0; i <= 6; i++)
//            for (int j = 4; j <= 7; j++)
//                textureFrames[index++] = tmp[i][j];
//
//
//        animations.put(Boom.Name, new Animation(Boom.SPEED, textureFrames));
//    }
//
//    private void loadBombAnimation(TextureRegion tmp[][]) {
//        TextureRegion[] textureFrames = new TextureRegion[Bomb.FRAME_COLS * Bomb.FRAME_ROWS];
//        int index = 0;
//        for (int j = 4; j <= 7; j++)
//            textureFrames[index++] = tmp[7][j];
//
//
//        animations.put(Bomb.Name, new Animation(8F, textureFrames));
//    }
//
//    private void loadBrickAnimation(TextureRegion tmp[][]) {
//        TextureRegion[] textureFrames = new TextureRegion[Brick.FRAME_COLS * Brick.FRAME_ROWS];
//
//        int index = 0;
//        for (int i = 2; i <= 3; i++)
//            for (int j = 0; j < 4; j++)
//                textureFrames[index++] = tmp[i][j];
//
//        animations.put(Brick.Name, new Animation(Brick.SPEED, textureFrames));
//    }

    private void loadTextures() {
        loadRegions();
    }

    public void dispose() {
        this.stopMusic();
        this.clearTimer();
        this.disposeSounds();

        try {

            spriteBatch.dispose();
            debugRenderer.dispose();
            bgRenderer.dispose();
            animations.clear();
            textureRegions.clear();
            texture.dispose();

            world.dispose();
        } catch (Exception e) {

        }

    }

    public int timeLeft;

    public void clearTimer() {
//        if (task != null) {
//            timer.cancel();
//            timer.purge();
//            timer = null;
//            task.cancel();
//            task = null;
//        }
    }

    private void createTimer() {
//        timeLeft = 0;
//        task = new TimerTask() {
//            public void run() {
//                if (!WorldController.paused)
//                    ++timeLeft;
//                if (!world.bonus) {
//
//                    if (timeLeft >= 200) {
//                        timeLeft = 200;
//                        world.generateNpc(6, 8);
//                        clearTimer();
//                    }
//                } else {
//                    if (timeLeft % 3 == 0 && world.getNpcCount() <= 15)
//                        world.generateBonusNpc(2);
//                    if (timeLeft >= 30) {
//                        timeLeft = 30;
//                        clearTimer();
//                        world.getBomberman().setState(Player.State.WON);
//                        playMusic();
//                    }
//                }
//
//            }
//        };
//        timer = new Timer();
//
//        timer.schedule(task, 1, 1000);
    }

    private void drawPoints() {
//        String points = Integer.toString(world.points);
//        int length = points.length();
//        for (int i = 0; i < length; ++i) {
//            spriteBatch.draw(textureRegions.get(points.substring(i, i + 1)), (CAMERA_WIDTH * 0.48F + 0.7F * i) * ppuX, (CAMERA_HEIGHT - 1F) * ppuY, 1F * ppuX, 1F * ppuY);
//        }
    }


    private void drawTimer() {

//        spriteBatch.draw(textureRegions.get("t"), CAMERA_WIDTH * 0.05F * ppuX, (CAMERA_HEIGHT - 1F) * ppuY, 1F * ppuX, 1F * ppuY);
//        spriteBatch.draw(textureRegions.get("i"), (CAMERA_WIDTH * 0.05F + 0.7F) * ppuX, (CAMERA_HEIGHT - 1F) * ppuY, 1F * ppuX, 1F * ppuY);
//        spriteBatch.draw(textureRegions.get("m"), (CAMERA_WIDTH * 0.05F + 1.4F) * ppuX, (CAMERA_HEIGHT - 1F) * ppuY, 1F * ppuX, 1F * ppuY);
//        spriteBatch.draw(textureRegions.get("e"), (CAMERA_WIDTH * 0.05F + 2.1F) * ppuX, (CAMERA_HEIGHT - 1F) * ppuY, 1F * ppuX, 1F * ppuY);
//
//        if (world.bonus && timeLeft > 30) timeLeft = 30;
//        if (!world.bonus && timeLeft >= 200) timeLeft = 200;
//
//        String tLeft = ((world.bonus) ? Integer.toString(30 - timeLeft) : Integer.toString(200 - timeLeft));
//        int length = tLeft.length();
//        for (int i = 0; i < length; ++i) {
//            spriteBatch.draw(textureRegions.get(tLeft.substring(i, i + 1)), (CAMERA_WIDTH * 0.05F + 3.5F + 0.7F * i) * ppuX, (CAMERA_HEIGHT - 1F) * ppuY, 1F * ppuX, 1F * ppuY);
//        }
//        spriteBatch.draw(textureRegions.get("l"), CAMERA_WIDTH * 0.8F * ppuX, (CAMERA_HEIGHT - 1F) * ppuY, 1F * ppuX, 1F * ppuY);
//        spriteBatch.draw(textureRegions.get("e"), (CAMERA_WIDTH * 0.8F + 0.7F) * ppuX, (CAMERA_HEIGHT - 1F) * ppuY, 1F * ppuX, 1F * ppuY);
//        spriteBatch.draw(textureRegions.get("f"), (CAMERA_WIDTH * 0.8F + 1.4F) * ppuX, (CAMERA_HEIGHT - 1F) * ppuY, 1F * ppuX, 1F * ppuY);
//        spriteBatch.draw(textureRegions.get("t"), (CAMERA_WIDTH * 0.8F + 2.1F) * ppuX, (CAMERA_HEIGHT - 1F) * ppuY, 1F * ppuX, 1F * ppuY);
//
//        String lLeft = Integer.toString(world.Left);
//
//        int length2 = lLeft.length();
//        for (int i = 0; i < length2; ++i)
//            spriteBatch.draw(textureRegions.get(lLeft.substring(i, i + 1)), (CAMERA_WIDTH * 0.8F + 3F + 0.7F * i) * ppuX, (CAMERA_HEIGHT - 1F) * ppuY, 1F * ppuX, 1F * ppuY);

    }


    private void drawTop() {

        drawPoints();
        drawTimer();
    }

    private void drawParts() {
        //
//        bgRenderer.setProjectionMatrix(cam.combined);
//        bgRenderer.begin(ShapeType.FilledRectangle);
//        bgRenderer.setColor(new Color(188F / 255, 188F / 255, 188F / 255, 1));
//        bgRenderer.filledRect(-CAMERA_WIDTH, 0, CAMERA_WIDTH + 4.1F, CAMERA_HEIGHT);
//        bgRenderer.filledRect(world.width - 3.5F, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
//
//        bgRenderer.filledRect(0, CAMERA_HEIGHT - 1F, world.width, 1F);
//        bgRenderer.end();
    }

    private boolean insideCameraBorder() {
//        return CAMERA_WIDTH / 2f <= world.getBomberman().getPosition().x && world.width - CAMERA_WIDTH / 2F >= world.getBomberman().getPosition().x;
        return true;
    }

    public void render() {
//        drawParts();
//
//        Bomberman player = world.getBomberman();
//
//        if (oldX == 0) oldX = player.getPosition().x;
//
//        if (oldX != player.getPosition().x) {
//            if (insideCameraBorder()) {
//                SetCamera(player.getPosition().x, CAMERA_HEIGHT / 2f);
//                oldX = player.getPosition().x;
//            }
//        }
//
//        spriteBatch.begin();
//        spriteBatch.disableBlending();
//
//        drawObjects();
//        drawBricks();
//        spriteBatch.enableBlending();
//
//        if (!WorldController.paused) drawBooms();
//        drawBombs();
//
//        drawBomberman();
//        drawNpcs();
//        drawTop();
//
//        if (WorldController.joy)
//            drawWalkingControlCircle();
//        else
//            drawWalkingControlArrows();
//        if (WorldController.paused) drawPauseControlls();
//        spriteBatch.end();
//
//        if (debug) drawDebug();
//        if (WorldController.soundOn && !WorldController.paused) playMusic();

    }

    public void drawPauseControlls() {
//        spriteBatch.setColor(0.9f, 0.9f, 0.9f, 0.7f);
//        spriteBatch.draw(textureRegions.get("fon"),
//                (CAMERA_WIDTH / 2 - 4F) * ppuX, (CAMERA_HEIGHT / 2 - 2F) * ppuY,
//                8F * ppuX, 4F * ppuY);
//        spriteBatch.setColor(1f, 1f, 1f, 1f);
//
//        spriteBatch.draw(textureRegions.get("home"),
//                (CAMERA_WIDTH / 2 + 1F) * ppuX, (CAMERA_HEIGHT / 2 - 1F) * ppuY,
//                2F * ppuX, 2F * ppuY);
//
//        spriteBatch.draw(textureRegions.get("resume"),
//                (CAMERA_WIDTH / 2 - 3F) * ppuX, (CAMERA_HEIGHT / 2 - 1F) * ppuY,
//                2F * ppuX, 2F * ppuY);
    }

    private void drawWalkingControlArrows() {

//        WalkingControlArrows control = world.getWalkingControlArrows();
//
//        arrowLeft.setColor(1F, 1F, 1F, 1F / (11 - WalkingControlArrows.Opacity));
//        arrowRight.setColor(1F, 1F, 1F, 1F / (11 - WalkingControlArrows.Opacity));
//        arrowDown.setColor(1F, 1F, 1F, 1F / (11 - WalkingControlArrows.Opacity));
//        arrowUp.setColor(1F, 1F, 1F, 1F / (11 - WalkingControlArrows.Opacity));
//
//
//        arrowLeft.setPosition(control.positionLeft.x * ppuX, control.positionLeft.y * ppuY);
//        arrowRight.setPosition(control.positionRight.x * ppuX, control.positionRight.y * ppuY);
//        arrowUp.setPosition(control.positionUp.x * ppuX, control.positionUp.y * ppuY);
//        arrowDown.setPosition(control.positionDown.x * ppuX, control.positionDown.y * ppuY);
//
//        arrowLeft.setSize(WalkingControlArrows.BSIZE * WalkingControlArrows.Coefficient * ppuX, WalkingControlArrows.BSIZE * WalkingControlArrows.Coefficient * ppuY);
//        arrowDown.setSize(WalkingControlArrows.BSIZE * WalkingControlArrows.Coefficient * ppuX, WalkingControlArrows.BSIZE * WalkingControlArrows.Coefficient * ppuY);
//        arrowRight.setSize(WalkingControlArrows.BSIZE * WalkingControlArrows.Coefficient * ppuX, WalkingControlArrows.BSIZE * WalkingControlArrows.Coefficient * ppuY);
//        arrowUp.setSize(WalkingControlArrows.BSIZE * WalkingControlArrows.Coefficient * ppuX, WalkingControlArrows.BSIZE * WalkingControlArrows.Coefficient * ppuY);
//
//
//        arrowLeft.draw(spriteBatch);
//        arrowRight.draw(spriteBatch);
//        arrowUp.draw(spriteBatch);
//        arrowDown.draw(spriteBatch);
//
//
//        spriteBatch.setColor(1F, 1F, 1F, 1F / (11 - WalkingControlArrows.Opacity));
//        spriteBatch.draw(animations.get(Bomb.Name).getKeyFrame(0, true), control.bombPosition.x * ppuX, control.bombPosition.y * ppuY, WalkingControlArrows.BSIZE * WalkingControlArrows.Coefficient * ppuX, WalkingControlArrows.BSIZE * WalkingControlArrows.Coefficient * ppuY);
//
//        if (world.getPower("detonator") >= 1 || world.inreasedPowerUp == DetonatorPower.Name)
//            spriteBatch.draw(textureRegions.get(DetonatorPower.Name), control.detonatorPosition.x * ppuX, control.detonatorPosition.y * ppuY, WalkingControlArrows.BSIZE * WalkingControlArrows.Coefficient * ppuX, WalkingControlArrows.BSIZE * WalkingControlArrows.Coefficient * ppuY);
//
//        spriteBatch.setColor(1F, 1F, 1F, 1F);

    }

    private void drawWalkingControlCircle() {
//        WalkingControl control = world.getWalkingControl();
//
//        spriteBatch.setColor(1F, 1F, 1F, 1F / (11 - WalkingControl.Opacity));
//
//        spriteBatch.draw(textureRegions.get("navigation-arrows"),
//                control.getPosition().x * ppuX, control.getPosition().y * ppuY, WalkingControl.SIZE * WalkingControl.Coefficient * ppuX, WalkingControl.SIZE * WalkingControl.Coefficient * ppuY);
//
//        spriteBatch.draw(textureRegions.get("khob"),
//                (float) (control.getPosition().x + WalkingControl.SIZE * WalkingControl.Coefficient / 2 - WalkingControl.CSIZE * WalkingControl.Coefficient / 2 + control.getOffsetPosition().x) * ppuX,
//                (float) (control.getPosition().y + WalkingControl.SIZE * WalkingControl.Coefficient / 2 - WalkingControl.CSIZE * WalkingControl.Coefficient / 2 + control.getOffsetPosition().y) * ppuY,
//                WalkingControl.CSIZE * WalkingControl.Coefficient * ppuX, WalkingControl.CSIZE * WalkingControl.Coefficient * ppuY);
//
//        spriteBatch.draw(animations.get(Bomb.Name).getKeyFrame(0, true), control.bombPosition.x * ppuX, control.bombPosition.y * ppuY, WalkingControl.BSIZE * WalkingControl.Coefficient * ppuX, WalkingControl.BSIZE * WalkingControl.Coefficient * ppuY);
//
//        if (world.getPower("detonator") >= 1 || world.inreasedPowerUp == DetonatorPower.Name)
//            spriteBatch.draw(textureRegions.get(DetonatorPower.Name), control.detonatorPosition.x * ppuX, control.detonatorPosition.y * ppuY, WalkingControl.BSIZE * WalkingControl.Coefficient * ppuX, WalkingControl.BSIZE * WalkingControl.Coefficient * ppuY);
//        spriteBatch.setColor(1F, 1F, 1F, 1F);
    }

    private void drawObjects() {
//        for (HiddenObject object : world.getHiddenObjects()) {
//            spriteBatch.draw(textureRegions.get(object.getName()), (object.getPosition().x - cam.position.x + CAMERA_WIDTH / 2) * ppuX, object.getPosition().y * ppuY, HiddenObject.SIZE * ppuX, HiddenObject.SIZE * ppuY);
//        }
    }

    private void drawBooms() {
//        try {
//            for (Boom boom : world.getBooms())
//
//                for (BoomPart boomPart : boom.getParts())
//
//                    spriteBatch.draw(animations.get(Boom.Name).getKeyFrame(boomPart.getAnimationState(), true), (boomPart.getPosition().x - cam.position.x + CAMERA_WIDTH / 2) * ppuX, boomPart.getPosition().y * ppuY, BoomPart.SIZE * ppuX, BoomPart.SIZE * ppuY);
//        } catch (NullPointerException e) {
//            if (e.getMessage() != null) Log.e("drawBooms", e.getMessage());
//            if (e.getLocalizedMessage() != null) Log.e("drawBooms", e.getLocalizedMessage());
//        }

    }

//    public void playBoomSound() {
//        if (WorldController.soundOn) sounds.get("boom").play(0.7F);
//    }
//
//    public void playPutingBombSound() {
//        if (WorldController.soundOn) sounds.get("put-bomb").play(0.7F);
//    }
//
//    public void playFindDoorMusic() {
//        foundDoor = true;
//        if (!WorldController.soundOn) return;
//        stopMainTheme();
//
//        playMainTheme();
//    }

    public void disposeSounds() {
//        stopMusic();
//        sounds.get("up-down").dispose();
//        sounds.get("left-right").dispose();
//        sounds.get("just-died").dispose();
//        sounds.get("dying").dispose();
//        sounds.get("boom").dispose();
//        sounds.get("put-bomb").dispose();
//        sounds.get("stage-main-theme").dispose();
//        sounds.get("stage-main-theme-find-the-door").dispose();
    }

    public void stopMusic() {
//        stopMainTheme();
//
//
//        sounds.get("up-down").stop();
//        sounds.get("left-right").stop();
//        sounds.get("just-died").stop();
//        sounds.get("dying").stop();
//        sounds.get("boom").stop();
//        sounds.get("put-bomb").stop();

    }

    public void stopMainTheme() {
//        sounds.get("stage-main-theme").stop();
//        sounds.get("stage-main-theme-find-the-door").stop();

    }


    public boolean foundDoor;

    enum CurrentSound {walking, dying, dying2, none, won}
    CurrentSound currentSound;

    public void playMainTheme() {
//        if (WorldController.soundOn) {
//            if (foundDoor)
//                sounds.get("stage-main-theme-find-the-door").loop(0.6F);
//            else
//                sounds.get("stage-main-theme").loop(1F);
//        }
    }

    private void playMusic() {
//        if (!WorldController.soundOn) return;
//        Bomberman player = world.getBomberman();
//        if (player.getState() == Bomberman.State.WON) {
//            if (!(currentSound == CurrentSound.won)) {
//                stopMusic();
//                currentSound = CurrentSound.won;
//                sounds.get("level-complete").play(0.8F);
//            }
//        }
//        if (currentSound == CurrentSound.won) return;
//
//        if (player.getState() == Bomberman.State.DYING) {
//            if (!(currentSound == CurrentSound.dying)) {
//                stopMusic();
//                currentSound = CurrentSound.dying;
//                sounds.get("dying").play(0.8F);
//            }
//        }
//        if (player.getState() == Bomberman.State.DYING2) {
//            if (!(currentSound == CurrentSound.dying2)) {
//                currentSound = CurrentSound.dying2;
//                sounds.get("just-died").play(0.8F);
//            }
//
//            stopMainTheme();
//        }
//        if (player.getState() == Bomberman.State.DEAD || player.getState() == Bomberman.State.WON)
//            stopMainTheme();
//        if (player.getState() == Bomberman.State.WALKING) {
//            if (!(currentSound == CurrentSound.walking))
//            {
//                currentSound = CurrentSound.walking;
//
//
//                if ((player.getDirection(Bomberman.Direction.LEFT) || player.getDirection(Bomberman.Direction.RIGHT)) &&
//                        !(player.getDirection(Bomberman.Direction.UP) || player.getDirection(Bomberman.Direction.DOWN))) {
//                    sounds.get("left-right").loop(0.5F);
//                }
//                if ((player.getDirection(Bomberman.Direction.UP) || player.getDirection(Bomberman.Direction.DOWN))) {
//                    sounds.get("up-down").loop(0.5F);
//                }
//            }
//
//        } else {
//            if (currentSound == CurrentSound.walking) {
//                currentSound = CurrentSound.none;
//                sounds.get("left-right").stop();
//                sounds.get("up-down").stop();
//            }
//        }
    }

    private void drawBombs() {
//        for (Bomb bomb : world.getBombs()) {
//            spriteBatch.draw(animations.get(Bomb.Name).getKeyFrame(bomb.getAnimationState(), true), (bomb.getPosition().x - cam.position.x + CAMERA_WIDTH / 2) * ppuX, bomb.getPosition().y * ppuY, Bomb.SIZE * ppuX, Bomb.SIZE * ppuY);
//        }
    }

    private void drawNpcs() {
//        for (NpcBase npc : world.getNpcs()) {
//            spriteBatch.draw(animations.get(npc.getName()).getKeyFrame(npc.getAnimationState(), true), (npc.getPosition().x - cam.position.x + CAMERA_WIDTH / 2) * ppuX, npc.getPosition().y * ppuY, NpcBase.SIZE * ppuX, NpcBase.SIZE * ppuY);
//        }
    }

    private void drawBricks() {
//        for (BrickBase block : world.getBlocks()) {
//            if (block.getName() == HardBrick.Name)
//                spriteBatch.draw(textureRegions.get("HardBrick"), (block.getPosition().x - cam.position.x + CAMERA_WIDTH / 2) * ppuX, block.getPosition().y * ppuY, BrickBase.SIZE * ppuX, BrickBase.SIZE * ppuY);
//            if (block.getName() == Brick.Name)
//                spriteBatch.draw(animations.get(Brick.Name).getKeyFrame(block.getAnimationState(), true), (block.getPosition().x - cam.position.x + CAMERA_WIDTH / 2) * ppuX, block.getPosition().y * ppuY, BrickBase.SIZE * ppuX, BrickBase.SIZE * ppuY);
//        }
    }

    float oldX = 0;

    private void drawBomberman() {


//        Bomberman player = world.getBomberman();
//
//        if (world.invincibility)
//            spriteBatch.setColor(0.9f, 0.9f, 0.9f, 0.7f);
//
//        if (!insideCameraBorder()) {
//            float fromX = 0;
//            if (!(player.getPosition().x < world.width / 2))
//                fromX = world.width - CAMERA_WIDTH;
//            spriteBatch.draw(animations.get("player").getKeyFrame(player.getAnimationState(), true),
//                    (player.getPosition().x - fromX) * ppuX, player.getPosition().y * ppuY,
//                    Bomberman.SIZE * ppuX, Bomberman.SIZE * ppuY);
//        } else
//            spriteBatch.draw(animations.get("player").getKeyFrame(player.getAnimationState(), true),
//                    (player.getPosition().x - (player.getPosition().x - CAMERA_WIDTH / 2)) * ppuX, player.getPosition().y * ppuY,
//                    Bomberman.SIZE * ppuX, Bomberman.SIZE * ppuY);
//        spriteBatch.setColor(1f, 1f, 1f, 1f);
    }


    private void drawDebug() {
//        // render blocks
//        debugRenderer.setProjectionMatrix(cam.combined);
//        debugRenderer.begin(ShapeType.Rectangle);
//        for (BrickBase block : world.getBlocks()) {
//            Rectangle rect = block.getBounds();
//            float x1 = block.getPosition().x + rect.x;
//            float y1 = block.getPosition().y + rect.y;
//            debugRenderer.setColor(new Color(1, 0, 0, 1));
//            debugRenderer.rect(x1, y1, rect.width, rect.height);
//        }
//        // render player
//        float fromX = 0;
//        Bomberman player = world.getBomberman();
//        Rectangle rect = player.getBounds();
//        float x1 = player.getPosition().x;// + rect.x;
//        float y1 = player.getPosition().y;// + rect.y;
//        debugRenderer.setColor(new Color(0, 1, 0, 1));
//
//        if (!insideCameraBorder()) {
//            debugRenderer.rect(x1 + fromX, y1, rect.width, rect.height);
//        } else
//            debugRenderer.rect(x1, y1, rect.width, rect.height);
//
//        // render npcs
//
//        for (NpcBase npc : world.getNpcs()) {
//            Rectangle rectnpc = npc.getBounds();
//            float x1npc = npc.getPosition().x;// + rect.x;
//            float y1npc = npc.getPosition().y;// + rect.y;
//            debugRenderer.setColor(new Color(255F / 256, 255F / 256, 0, 1));
//            debugRenderer.rect(x1npc, y1npc, rectnpc.width, rectnpc.height);
//        }
//
//        WalkingControlArrows control = world.getWalkingControlArrows();
//
//        debugRenderer.setColor(new Color(36F / 256, 36F / 256, 200F / 256, 1));
//        debugRenderer.rect(control.positionLeft.x, control.positionLeft.y, WalkingControlArrows.BSIZE * WalkingControlArrows.Coefficient, WalkingControlArrows.BSIZE * WalkingControlArrows.Coefficient);
//        debugRenderer.rect(control.positionRight.x, control.positionRight.y, WalkingControlArrows.BSIZE * WalkingControlArrows.Coefficient, WalkingControlArrows.BSIZE * WalkingControlArrows.Coefficient);
//        debugRenderer.rect(control.positionUp.x, control.positionUp.y, WalkingControlArrows.BSIZE * WalkingControlArrows.Coefficient, WalkingControlArrows.BSIZE * WalkingControlArrows.Coefficient);
//        debugRenderer.rect(control.positionDown.x, control.positionDown.y, WalkingControlArrows.BSIZE * WalkingControlArrows.Coefficient, WalkingControlArrows.BSIZE * WalkingControlArrows.Coefficient);
//
//        debugRenderer.end();
    }
}

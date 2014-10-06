package com.andreykaraman.survival.controllers;

import com.andreykaraman.survival.model.Player;
import com.andreykaraman.survival.model.World_a;
import com.andreykaraman.survival.view.WorldRenderer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KaramanA on 26.09.2014.
 */
public class WorldController {
    private WorldRenderer 	renderer;
    enum Keys {
        LEFT, RIGHT, UP, DOWN
    }

    private World_a world;
    public Player player;
    public float toX;
    public float toY;
    public boolean hasKeyBoard = false;
    public static boolean paused = false;
    public static boolean soundOn;
    public static boolean joy;
    static Map<Keys, Boolean> keys = new HashMap<Keys, Boolean>();
    static {
        keys.put(Keys.LEFT, false);
        keys.put(Keys.RIGHT, false);
        keys.put(Keys.UP, false);
        keys.put(Keys.DOWN, false);
    };

    public WorldController(World_a world, WorldRenderer renderer) {

        this.renderer = renderer;
        this.world = world;
        this.player = world.getPlayer();
        paused = false;
//        task = null;
//        timer = null;

        //this.player = world.getBomberman();
    }

    // ** Key presses and touches **************** //

    public void leftPressed() {
        keys.get(keys.put(Keys.LEFT, true));
    }
    public void boomBombPressed() {
//        world.boomLastBomb();
    }
    public void bombPressed(int x, int y) {
//        if(!player.isDead())
//            if(world.putBomb(x, y, true))
//                renderer.playPutingBombSound();
//        //Log.e("bomb",	"booom");
    }
    public void rightPressed() {
        keys.get(keys.put(Keys.RIGHT, true));
    }

    public void upPressed() {
        keys.get(keys.put(Keys.UP, true));
    }

    public void downPressed() {
        keys.get(keys.put(Keys.DOWN, true));
    }

    public void leftReleased() {
        keys.get(keys.put(Keys.LEFT, false));
    }

    public void rightReleased() {
        keys.get(keys.put(Keys.RIGHT, false));
    }

    public void upReleased() {
        keys.get(keys.put(Keys.UP, false));
    }

    public void downReleased() {
        keys.get(keys.put(Keys.DOWN, false));
    }

//    TimerTask task;
//    Timer timer;
//    public void dispose(){
//        clearTimer();
//        //world.dispose();
//    }
//
//
//    int timeLeft;
//    private void createMysteryTimer(){
//        timeLeft = 0;
//        world.invincibility = true;
//        task = new TimerTask(){
//            public void run()
//            {
//                if(!WorldController.paused)
//                    ++timeLeft;
//
//                if(timeLeft >= 20){
//                    //Log.e("cleartimer","clear");
//                    world.invincibility = false;
//                    clearTimer();
//                }
//
//            }
//        };
//        timer=new Timer();
//
//        timer.schedule(task, 1, 1000);
//    }

//    public void clearTimer(){
//        if(timer != null){
//            timer.cancel();
//            timer.purge();
//            timer = null;
//        }
//    }

    /** The main update method **/
    public void update(float delta) {

        processInput();
        checkBombsTimer();
//        if(!world.bonus) destroyBricks();
        removeDeadNpc();
        killByBoom();
        removeHiddenObjects();

        player.update(delta);
//        for(NpcBase npc : world.getNpcs()){
//            npc.update(delta);
//        }
        removeBooms();
        //ShowPos();
    }

    private void removeHiddenObjects(){
//        for(HiddenObject object : world.getHiddenObjects()){
//            if(object.state == HiddenObject.State.TAKEN){
//
//                if(object.getName() == MysteryPower.Name)
//                    createMysteryTimer();
//                world.getHiddenObjects().removeValue(object, true);
//                renderer.playFindDoorMusic();
//            }
//            if(object.state == HiddenObject.State.DESTROYED){
//                //world.generateNpcAfterDestroyingObject(object.getPosition());
//                if(!(object.getName() == Door.Name))
//                    world.getHiddenObjects().removeValue(object, true);
//            }
//        }
    }
    private void removeBooms(){
//        for(Boom boom : world.getBooms()){
//            if(boom.getState() == Boom.State.BOOM){
//                world.getBooms().removeValue(boom, true);
//                for(BoomPart part : boom.getParts()){
//                    for(HiddenObject object : world.getHiddenObjects()){
//                        if( object.getPosition().x == part.getPosition().x &&
//                                object.getPosition().y==part.getPosition().y){
//                            object.state = HiddenObject.State.DESTROYED;
//                            world.generateNpcAfterDestroyingObject(object.getPosition());
//                        }
//
//                    }
//                }
//
//            }
//
//        }
    }

    private void destroyBricks(){
//        for (BrickBase block : world.getBlocks()) {
//            if(block.getState() == BrickBase.State.DESTROYED)
//                //world.getBlocks().removeValue(block,true);
//                world.removeBrick(block);
//        }
    }
    private void removeDeadNpc(){
//        for (NpcBase npc : world.getNpcs()) {
//            if(npc.getState() == NpcBase.State.DEAD)
//                world.getNpcs().removeValue(npc, true);
//        }
    }

    private void killByBoom(){
//        for(Boom boom: world.getBooms())
//            for(BoomPart part: boom.getParts()){
//                for(NpcBase npc : world.getNpcs()){
//                    float x1 = Math.round(npc.getPosition().x);
//                    float y1 = Math.round(npc.getPosition().y);
//                    if(x1 == part.getPosition().x && y1 == part.getPosition().y)
//                    {
//                        if(npc.getState() != NpcBase.State.DYING && npc.getState() != NpcBase.State.DEAD){
//                            world.points += npc.getPoints();
//                            npc.setState(NpcBase.State.DYING);
//                        }
//                    }
//                }
//
//                float x1 = (int)(world.getBomberman().getPosition().x+Bomberman.SIZE/2);
//                float y1 =  (int)(world.getBomberman().getPosition().y+Bomberman.SIZE/2);
//                if(x1 == part.getPosition().x && y1 == part.getPosition().y && !world.invincibility && !world.bonus && !world.getBomberman().flamePass)
//                    world.getBomberman().setState(Bomberman.State.DYING);
//            }
    }
//    private void killNearNpc(Bomb bombCheck){
//        Array<NpcBase> npcToKill = new Array<NpcBase>();
//        float x = bombCheck.getPosition().x;
//        float y = bombCheck.getPosition().y;
//
//        for(int i=1;i<=bombCheck.length;++i){
//            for(NpcBase npc : world.getNpcs()){
//                float x1 = Math.round(npc.getPosition().x);
//                float y1 = Math.round(npc.getPosition().y);
//                if((x1 == x+i && y1==y)|| (x1 == x-i && y1==y)|| (x1 == x && y1==y+i)|| (x1 == x && y1==y-i))
//                    npcToKill.add(npc);
//                //bomb.setState(Bomb.State.BOOM);
//            }
//        }
//
//        for (BrickBase block : world.getBlocks()) {
//            for(NpcBase npc :npcToKill){
//                float x1 = Math.round(npc.getPosition().x);
//                float y1 = Math.round(npc.getPosition().y);
//
//                if(block.getPosition().x == x1 &&
//                        ((block.getPosition().y> y1&& block.getPosition().y < bombCheck.getPosition().y)
//                                ||
//                                (block.getPosition().y< y1&& block.getPosition().y > bombCheck.getPosition().y)
//                        )
//                        )
//                    npcToKill.removeValue(npc, true);
//
//                if(block.getPosition().y == y1 &&
//                        ((block.getPosition().x > x1 && block.getPosition().x < bombCheck.getPosition().x)
//                                ||
//                                (block.getPosition().x <  x1 && block.getPosition().x > bombCheck.getPosition().x))
//                        )
//                    npcToKill.removeValue(npc, true);
//            }
//
//        }
//
//        for(NpcBase npc : npcToKill)
//            if(npc.getState() != NpcBase.State.DYING && npc.getState() != NpcBase.State.DEAD){
//                world.points += npc.getPoints();
//                npc.setState(NpcBase.State.DYING);
//            }
//    }


    private void killBombermanInsideBrick(int x,int y){
//
//        //Log.e("killBombermanInsideBrick", "+");
//        if(x == Math.round(world.getBomberman().getPosition().x) && y == Math.round(world.getBomberman().getPosition().y) && !world.invincibility && !world.bonus && !world.getBomberman().flamePass)
//            world.getBomberman().setState(Bomberman.State.DYING);
    }

//    private void destroyNearObjects(Bomb bomb){
//
//        Array<BoomPart> boomParts = new Array<BoomPart>();
//        boomParts.add(new BoomPart(new Vector2(bomb.getPosition().x,bomb.getPosition().y), BoomPart.Type.MIDDLE));
//        boolean topEnd = false;
//        boolean botEnd = false;
//
//        boolean leftEnd = false;
//        boolean rightEnd = false;
//
//        Array<BrickBase> bricks = new Array<BrickBase>();
//
//        for(int i=1;i<=bomb.length;++i){
//            for (BrickBase block : world.getBlocks())
//                if((block.position.x == bomb.getPosition().x+i &&
//                        block.position.y == bomb.getPosition().y) ||
//                        (block.position.x == bomb.getPosition().x-i &&
//                                block.position.y == bomb.getPosition().y)||
//                        (block.position.y == bomb.getPosition().y-i &&
//                                block.position.x == bomb.getPosition().x)
//                        ||
//                        (block.position.y == bomb.getPosition().y+i &&
//                                block.position.x == bomb.getPosition().x))
//                    bricks.add(block);
//
//        }
//
//        for(int i=1;i<=bomb.length;++i){
//            for (BrickBase block : bricks) {
//                if(!botEnd){
//                    if(block.getName()=="HardBrick" && block.getPosition().y-i*1F == bomb.getPosition().y &&
//                            block.getPosition().x == bomb.getPosition().x){
//
//                        //Log.e("HardBrick", "true");
//                        botEnd = true;
//                    }
//                    if(block.getName()=="Brick" && block.getPosition().y-i*1F == bomb.getPosition().y&&
//                            block.getPosition().x == bomb.getPosition().x){
//                        //Log.e("Brick", "true");
//                        killBombermanInsideBrick((int)block.getPosition().x, (int)block.getPosition().y);
//                        block.setState(BrickBase.State.DESTROYING);
//                        //world.getBlocks().removeValue(block,true);
//                        botEnd = true;
//                    }
//                }
//
//
//                if(!topEnd){
//                    if(block.getName()=="HardBrick" && block.getPosition().y+i*1F == bomb.getPosition().y&&
//                            block.getPosition().x == bomb.getPosition().x){
//                        topEnd = true;
//                    }
//                    if(block.getName()=="Brick" && block.getPosition().y+i*1F == bomb.getPosition().y&&
//                            block.getPosition().x == bomb.getPosition().x){
//                        killBombermanInsideBrick((int)block.getPosition().x, (int)block.getPosition().y);
//                        block.setState(BrickBase.State.DESTROYING);
//                        //world.getBlocks().removeValue(block,true);
//                        topEnd = true;
//                    }
//
//					/*
//					for(HiddenObject object : world.getHiddenObjects()){
//						if( object.getPosition().x == bomb.getPosition().x &&
//								object.getPosition().y> bomb.getPosition().y&&
//								object.getPosition().y<bomb.getPosition().y+i*1F)
//							object.state = HiddenObject.State.DESTROYED;
//
//					}*/
//                }
//
//                if(!leftEnd){
//                    if(block.getName()=="HardBrick" && block.getPosition().y == bomb.getPosition().y&&
//                            block.getPosition().x == bomb.getPosition().x-i*1F){
//                        leftEnd = true;
//                    }
//                    if(block.getName()=="Brick" && block.getPosition().y == bomb.getPosition().y&&
//                            block.getPosition().x == bomb.getPosition().x-i*1F){
//                        killBombermanInsideBrick((int)block.getPosition().x, (int)block.getPosition().y);
//                        block.setState(BrickBase.State.DESTROYING);
//                        //world.getBlocks().removeValue(block,true);
//                        leftEnd = true;
//                    }
//					/*
//					for(HiddenObject object : world.getHiddenObjects()){
//						if( object.getPosition().x > bomb.getPosition().x-i*1F &&
//								 object.getPosition().x < bomb.getPosition().x &&
//								object.getPosition().y==bomb.getPosition().y)
//							object.state = HiddenObject.State.DESTROYED;
//
//					}*/
//                }
//                if(!rightEnd){
//                    if(block.getName()=="HardBrick" && block.getPosition().y == bomb.getPosition().y&&
//                            block.getPosition().x == bomb.getPosition().x+i*1F){
//                        rightEnd= true;
//                    }
//                    if(block.getName()=="Brick" && block.getPosition().y == bomb.getPosition().y&&
//                            block.getPosition().x == bomb.getPosition().x+i*1F){
//                        killBombermanInsideBrick((int)block.getPosition().x, (int)block.getPosition().y);
//                        block.setState(BrickBase.State.DESTROYING);
//                        //world.getBlocks().removeValue(block,true);
//                        rightEnd = true;
//                    }
//					/*
//					for(HiddenObject object : world.getHiddenObjects()){
//						if( object.getPosition().x < bomb.getPosition().x+i*1F &&
//								 object.getPosition().x > bomb.getPosition().x &&
//								object.getPosition().y==bomb.getPosition().y)
//							object.state = HiddenObject.State.DESTROYED;
//
//					}*/
//                }
//            }
//            if(!rightEnd)
//                boomParts.add(new BoomPart(new Vector2(bomb.getPosition().x+i*1F,bomb.getPosition().y), BoomPart.Type.HORIZONTAL));
//			/*else
//				if(i>100)
//					for(BoomPart boomPart: boomParts)
//						if(boomPart.getPosition().x == bomb.getPosition().x+(i-1)*1F && boomPart.getPosition().y == bomb.getPosition().y)
//							boomPart.setType(BoomPart.Type.RIGHT);*/
//
//
//
//            if(!leftEnd)
//                boomParts.add(new BoomPart(new Vector2(bomb.getPosition().x-i*1F,bomb.getPosition().y), BoomPart.Type.HORIZONTAL));
//			/*else
//				if(i>100)
//					for(BoomPart boomPart: boomParts)
//						if(boomPart.getPosition().x == bomb.getPosition().x-(i-1)*1F && boomPart.getPosition().y == bomb.getPosition().y)
//							boomPart.setType(BoomPart.Type.LEFT);*/
//            if(!botEnd)
//                boomParts.add(new BoomPart(new Vector2(bomb.getPosition().x,bomb.getPosition().y+i*1F), BoomPart.Type.VERTICAL));
//			/*else
//				if(i>100)
//					for(BoomPart boomPart: boomParts)
//						if(boomPart.getPosition().x == bomb.getPosition().x && boomPart.getPosition().y-(i-1)*1F == bomb.getPosition().y)
//							boomPart.setType(BoomPart.Type.UP);*/
//            if(!topEnd)
//                boomParts.add(new BoomPart(new Vector2(bomb.getPosition().x,bomb.getPosition().y-i*1F), BoomPart.Type.VERTICAL));
//			/*else
//				if(i>100)
//					for(BoomPart boomPart: boomParts)
//						if(boomPart.getPosition().x == bomb.getPosition().x && boomPart.getPosition().y+(i-1)*1F == bomb.getPosition().y)
//							boomPart.setType(BoomPart.Type.DOWN);*/
//
//        }
//
//        if(!leftEnd)
//            for(BoomPart boomPart: boomParts)
//                if(boomPart.getPosition().x == bomb.getPosition().x-bomb.length*1F && boomPart.getPosition().y == bomb.getPosition().y)
//                    boomPart.setType(BoomPart.Type.LEFT);
//
//        if(!rightEnd)
//            for(BoomPart boomPart: boomParts)
//                if(boomPart.getPosition().x == bomb.getPosition().x+bomb.length*1F && boomPart.getPosition().y == bomb.getPosition().y)
//                    boomPart.setType(BoomPart.Type.RIGHT);
//        if(!botEnd)
//            for(BoomPart boomPart: boomParts)
//                if(boomPart.getPosition().x == bomb.getPosition().x && boomPart.getPosition().y-bomb.length*1F == bomb.getPosition().y)
//                    boomPart.setType(BoomPart.Type.UP);
//
//        if(!topEnd)
//            for(BoomPart boomPart: boomParts)
//                if(boomPart.getPosition().x == bomb.getPosition().x && boomPart.getPosition().y+bomb.length*1F == bomb.getPosition().y)
//                    boomPart.setType(BoomPart.Type.DOWN);
//        world.putBoom(new Boom(boomParts));
//    }
//    private void killBomberman(Bomb bombCheck){
//        boolean kill = false;
//        float x = bombCheck.getPosition().x;
//        float y = bombCheck.getPosition().y;
//        float x1 = (int)world.getBomberman().getPosition().x+0.03F;
//        float y1 =  (int)world.getBomberman().getPosition().y+0.03F;
//
//        for(int i=0;i<=bombCheck.length;++i)
//            if((x1 == x+i && y1==y)|| (x1 == x-i && y1==y)|| (x1 == x && y1==y+i)|| (x1 == x && y1==y-i))
//                kill = true;
//
//
//        for (BrickBase block : world.getBlocks()) {
//            if(block.getPosition().x == x1 &&
//                    ((block.getPosition().y> y1&& block.getPosition().y < bombCheck.getPosition().y)||
//                            (block.getPosition().y< y1&& block.getPosition().y > bombCheck.getPosition().y)))
//                kill = false;
//
//            if(block.getPosition().y == y1 &&  ((block.getPosition().x > x1 && block.getPosition().x < bombCheck.getPosition().x)
//                    ||
//                    (block.getPosition().x <  x1 && block.getPosition().x > bombCheck.getPosition().x)))
//                kill = false;
//
//        }
//
//        if(kill  && !world.bonus && !world.invincibility && !world.getBomberman().flamePass){
//            //player.setDead(true);
//            player.setState(Bomberman.State.DYING);
//        }
//    }

    private void checkBombsTimer(){
//        for(Bomb bomb : world.getBombs()){
//            if(bomb.state == Bomb.State.BOOM)
//            {
//                killNearNpc(bomb);
//                if(!world.bonus) killBomberman(bomb);
//                checkAroundBombs(bomb);
//                destroyNearObjects(bomb);
//                renderer.playBoomSound();
//                world.getBombs().removeValue(bomb, true);
//            }
//			/*if(bomb.state == Bomb.State.BOOM)
//				world.getBombs().removeValue(bomb, true);*/
//        }
    }
//    private void checkAroundBombs(Bomb bombCheck){
//        float x = bombCheck.getPosition().x;
//        float y = bombCheck.getPosition().y;
//        Array<Bomb> bombsToRemove = new Array<Bomb>();
//        for(int i=1;i<=bombCheck.length;++i){
//            for(Bomb bomb : world.getBombs()){
//                float x1 = bomb.getPosition().x;
//                float y1 = bomb.getPosition().y;
//                if((x1 == x+i && y1==y)|| (x1 == x-i && y1==y)|| (x1 == x && y1==y+i)|| (x1 == x && y1==y-i))
//                    bombsToRemove.add(bomb);
//                //bomb.setState(Bomb.State.BOOM);
//            }
//        }
//        //Log.e("countbefore",Integer.toString(bombsToRemove.size));
//        for (BrickBase block : world.getBlocks()) {
//            for(Bomb bomb : bombsToRemove){
//                if(block.getPosition().x == bomb.getPosition().x &&
//                        ((block.getPosition().y> bomb.getPosition().y && block.getPosition().y < bombCheck.getPosition().y)
//                                ||
//                                (block.getPosition().y< bomb.getPosition().y && block.getPosition().y > bombCheck.getPosition().y)
//                        )
//                        )
//                    bombsToRemove.removeValue(bomb, true);
//
//                if(block.getPosition().y == bomb.getPosition().y &&
//                        ((block.getPosition().x > bomb.getPosition().x && block.getPosition().x < bombCheck.getPosition().x)
//                                ||
//                                (block.getPosition().x <  bomb.getPosition().x && block.getPosition().x > bombCheck.getPosition().x))
//                        )
//                    bombsToRemove.removeValue(bomb, true);
//            }
//
//        }
//        for(Bomb bomb : bombsToRemove){
//
//            //renderer.playBoomSound();
//            bomb.setState(Bomb.State.BOOM);
//        }
//
//    }

    public void resetWay(){
        rightReleased();
        leftReleased();
        downReleased();
        upReleased();
    }

    /** Change Bob's state and parameters based on input controls **/
    private void processInput() {
//        player.resetDirection();
//
//        if (!player.isDead()){
//            if (keys.get(Keys.LEFT)) {
//
//                // left is pressed
//                //vedroid.setFacingLeft(true);
//                player.setState(Bomberman.State.WALKING);
//                player.setDirection(Bomberman.Direction.LEFT, true);
//                //player.getDirection().put(Bomberman.Direction.LEFT, true);
//                player.getVelocity().x = -player.getSpeed();
//
//            }
//
//            if (keys.get(Keys.RIGHT)) {
//                player.setDirection(Bomberman.Direction.RIGHT, true);
//                // right is pressed
//                //vedroid.setFacingLeft(false);
//                player.setState(Bomberman.State.WALKING);
//                //player.getDirection().put(Bomberman.Direction.RIGHT, true);
//                //if(Math.abs(toX-vedroid.getPosition().x)< Math.abs(toY-vedroid.getPosition().y))
//                //	vedroid.getVelocity().x = (float)(vedroid.SPEED/45 * Math.atan(Math.abs((toX-vedroid.getPosition().x)/(toY-vedroid.getPosition().y))));
//                //else
//                player.getVelocity().x = player.getSpeed();
//            }
//
//            if (keys.get(Keys.UP)) {
//
//                //vedroid.setFacingLeft(true);
//                player.setDirection(Bomberman.Direction.UP, true);
//                player.setState(Bomberman.State.WALKING);
//                //player.getDirection().put(Bomberman.Direction.UP, true);
//                player.getVelocity().y = player.getSpeed();
//            }
//
//            if (keys.get(Keys.DOWN)) {
//
//                //vedroid.setFacingLeft(true);
//                player.setDirection(Bomberman.Direction.DOWN, true);
//                //player.getDirection().put(Bomberman.Direction.DOWN, true);
//                player.setState(Bomberman.State.WALKING);
//                player.getVelocity().y = -player.getSpeed();
//            }
//
//
//            // need to check if both or none direction are pressed, then Bob is idle
//            if ((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT)) ||
//                    (!keys.get(Keys.LEFT) && (!keys.get(Keys.RIGHT)))) {
//                //player.setState(State.IDLE);
//                // acceleration is 0 on the x
//                player.getAcceleration().x = 0;
//                // horizontal speed is 0
//                player.getVelocity().x = 0;
//            }
//            if ((keys.get(Keys.UP) && keys.get(Keys.DOWN)) ||
//                    (!keys.get(Keys.UP) && (!keys.get(Keys.DOWN)))) {
//                //player.setState(State.IDLE);
//                // acceleration is 0 on the x
//                player.getAcceleration().y = 0;
//                // horizontal speed is 0
//                player.getVelocity().y = 0;
//            }
//            if(!keys.get(Keys.LEFT) && !keys.get(Keys.RIGHT)&& !keys.get(Keys.UP) && !keys.get(Keys.DOWN)){
//                player.getVelocity().x = 0;
//                player.getVelocity().y = 0;
//                player.setState(Bomberman.State.IDLE);
//            }
//			/*if (keys.get(Keys.UP)){
//				vedroid.setState(State.JUMPING);
//			}*/
//        }
//        else {
//            player.getVelocity().x = 0;
//            player.getVelocity().y = 0;
//        }
    }

}

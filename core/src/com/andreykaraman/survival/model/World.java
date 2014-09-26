package com.andreykaraman.survival.model;

/**
 * Created by KaramanA on 25.09.2014.
 */
public class World {
    public Player player;
    public int[][]  map;
    //private Preferences prefs;

   // Array<NpcBase> npcs;
   // Array<Bomb> bombs;
   // Array<Boom> booms;
   // Array<HiddenObject> hiddenObjects;
   // Array<BrickBase> blocks;
   // WalkingControl   walkingControl;
   // WalkingControlArrows controlArrows;

//    public void generateNpcOnPosition(int count, int type, Vector2 position){
//        Random generator = new Random();
//        int x = 0,y =0;
//
//        for(int i=0;i<count;++i)
//        {
//            boolean exist = true;
//
//
//            while(exist)
//            {
//                exist = false;
//                x = 10+generator.nextInt(width-15);
//                y = 2+generator.nextInt(height-3);
//                for (BrickBase block : blocks) {
//                    if(block.position.x == x && block.position.y == y)
//                    {
//                        exist = true;
//                    }
//                }
//
//            }
//            switch(type){
//                case 1:
//                    npcs.add(new Balloom(this, new Vector2(position.x,position.y)));
//                    break;
//                case 2:
//                    npcs.add(new Oneal(this,new Vector2(position.x,position.y)));
//                    break;
//                case 3:
//                    npcs.add(new Doll(this, new Vector2(position.x,position.y)));
//                    break;
//                case 4:
//                    npcs.add(new Minvo(this,new Vector2(position.x,position.y)));
//                    break;
//                case 5:
//                    npcs.add(new Kondoria(this, new Vector2(position.x,position.y)));
//                    break;
//                case 6:
//                    npcs.add(new Ovapi(this,new Vector2(position.x,position.y)));
//                case 7:
//                    npcs.add(new Pass(this, new Vector2(position.x,position.y)));
//
//                    break;
//                case 8:
//                    npcs.add(new Pontan(this,new Vector2(position.x,position.y)));
//                    break;
//            }
//
//        }
//    }


    public void createLevel() {
//
//
//        bomberman = new Bomberman(this, new Vector2(5.01F, 1.01F));
//        bomberman.setSpeed(getPower("speed"));
//        bomberman.setWallPass(getPower("wallpass")>=1 ? true : false);
//        bomberman.setBombPass(getPower("bombpass")>=1 ? true : false);
//        bomberman.setFlamePass(getPower("flamepass")>=1 ? true : false);
//        //bomberman = new Bomberman(this, new Vector2(1.1F, 1.1F));
//
//
//        for (int i = offset; i <= width-offset; i++) {
//            blocks.add(new HardBrick(new Vector2(i, 0)));
//            blocks.add(new HardBrick(new Vector2(i, height-2)));
//        }
//        for (int i = 0; i < height-1; i++) {
//
//            blocks.add(new HardBrick(new Vector2(offset, i)));
//            blocks.add(new HardBrick(new Vector2(width-offset, i)));
//        }
//
//        for (int i = offset; i < width-offset; i++) {
//            for (int j = 1; j< height-1; j++) {
//                if(i%2==0 && j%2 == 0)
//                    blocks.add(new HardBrick(new Vector2(i, j)));
//            }
//        }
//        int bricksCount = (int)((width-2)*(height-2)/6);
//        String s = getStage();
//        //Log.e("g", s);
//        int sI = -1;
//        try{sI = Integer.parseInt(s); /*sI=1;*/}
//        catch(Exception e){bonus = true; }
//        if(bonus)
//            //Log.e("stage", s);
//            generateBonusNpc(6);
//
//        else
//        {
//            if(sI == 1 || sI == 7|| sI == 15 || sI == 27 || sI == 38)
//                powerName = FlamesPower.Name;
//            if(sI == 2 || sI == 5|| sI == 6 || sI == 11 || sI == 12 || sI == 17 || sI == 19 || sI == 23 || sI == 28 || sI == 32)
//                powerName = BombsPower.Name;
//            if(sI == 3 || sI == 8 || sI == 13 || sI == 20 || sI == 22 || sI == 24 || sI == 29 || sI == 33 || sI == 37 || sI == 41 || sI == 44 || sI == 48)
//                powerName = DetonatorPower.Name;
//            if(sI == 4)
//                powerName = SpeedPower.Name;
//            if(sI == 9 || sI == 14 || sI == 18 || sI == 21 || sI == 25 || sI == 35 || sI == 43 || sI == 47)
//                powerName = BombPassPower.Name;
//            if(sI == 10  || sI == 16 || sI == 31 || sI == 39 || sI == 42 || sI == 46)
//                powerName = WallPassPower.Name;
//            if(sI == 30  || sI == 36 || sI == 49)
//                powerName = FlamePassPower.Name;
//            if(sI == 26  || sI == 34 || sI == 40|| sI == 45|| sI == 50)
//                powerName = MysteryPower.Name;
//
//
//            //powerName = BombPassPower.Name;
//            generateBricks(bricksCount);
//            generateAllNpc(sI);
//
//
//			/*
//			if(sI == 1 || sI == 2 || sI == 3|| sI == 4|| sI == 18|| sI == 19 || sI == 26 || sI == 27)
//				generateNpc(1,1);
//			if(sI == 1 || sI == 2 || sI == 3|| sI == 18)
//				generateNpc(1,1);
//			if(sI == 1 || sI == 2 || sI == 18)
//				generateNpc(1,1);
//			if(sI == 1 )
//				generateNpc(3,1);
//
//			if((sI >= 2 && sI <=12) || sI == 19  || sI == 20 || (sI >=25 && sI<=28) || sI == 31 || sI == 32)
//				generateNpc(1,2);
//			if(sI==2 || sI==3 || sI==5 || sI==6|| sI==7 || sI==31)
//				generateNpc(1,2);
//			if(sI==2 || sI==5 )
//				generateNpc(1,2);
//			if(sI==5) generateNpc(1,2);
//
//			if((sI>=3 && sI<= 13) || sI==15  )
//				generateNpc(1,3);
//
//			if(sI==3 || sI==4 || sI==5 || sI==6|| sI==7 || sI==8 || sI==11 || sI==13)
//				generateNpc(1,3);
//
//			if(sI==5 || sI==6 || sI==7 || sI==13)
//				generateNpc(1,3);*/

        }

    private void generateAllNpc(int sI){
//        switch(sI){
//
//            case 1:
//                generateNpc(6,1);
//                break;
//            case 2:
//                generateNpc(3,1);
//                generateNpc(3,2);
//                break;
//            default:
//
//                generateNpc(6,8);
//                break;
//        }
    }

    private void generateBricks(int bricksCount){
//        Door door = null;
//        HiddenObject power = null;
//        Random generator = new Random();
//        int x,y;
//        for(int i=0; i<bricksCount;++i){
//            x = offset+generator.nextInt(width-2*offset);
//            y = 2+generator.nextInt(height-3);
//            if(x%2==0 && y%2==0)
//                x++;
//
//
//
//            boolean exist = false;
//            for (BrickBase block : blocks) {
//                if(block.position.x == x && block.position.y == y)
//                {
//                    exist = true;
//                }
//            }
//            if(!exist)
//            {
//                blocks.add(new Brick(new Vector2(x, y)));
//                if(door == null) door = new Door(new Vector2(x,y));
//                else
//
//                if(power == null)
//                {
//                    if(powerName.equals(FlamesPower.Name))
//                        power = new FlamesPower(new Vector2(x,y));
//                    if(powerName.equals(BombsPower.Name))
//                        power = new BombsPower(new Vector2(x,y));
//                    if(powerName.equals(SpeedPower.Name))
//                        power = new SpeedPower(new Vector2(x,y));
//                    if(powerName.equals(BombPassPower.Name))
//                        power = new BombPassPower(new Vector2(x,y));
//                    if(powerName.equals(WallPassPower.Name))
//                        power = new WallPassPower(new Vector2(x,y));
//                    if(powerName.equals(DetonatorPower.Name))
//                        power = new DetonatorPower(new Vector2(x,y));
//                    if(powerName.equals(FlamePassPower.Name))
//                        //Log.e("flamepss", "true");
//                        power = new FlamePassPower(new Vector2(x,y));
//                    //Log.e("flamepss", power.getName());
//                    if(powerName.equals(MysteryPower.Name))
//                        power = new MysteryPower(new Vector2(x,y));
//
//                }
//
//            }
//        }
//
//        hiddenObjects.add(door);
//        hiddenObjects.add(power);

    }

    public void generateNpcSave(int count, int type){
//        Random generator = new Random();
//        int x = 0,y =0;
//
//        for(int i=0;i<count;++i)
//        {
//            boolean exist = true;
//
//
//            while(exist)
//            {
//                exist = false;
//                x = 10+generator.nextInt(width-15);
//                y = 2+generator.nextInt(height-3);
//                for (BrickBase block : blocks) {
//                    if(block.position.x == x && block.position.y == y)
//                    {
//                        exist = true;
//                    }
//                }
//
//            }
//            switch(type){
//                case 1:
//                    npcs.add(new Balloom(this, new Vector2(x, y)));
//                    break;
//                case 2:
//                    npcs.add(new Oneal(this, new Vector2(x, y)));
//                    break;
//                case 3:
//                    npcs.add(new Doll(this, new Vector2(x, y)));
//                    break;
//                case 4:
//                    npcs.add(new Minvo(this, new Vector2(x, y)));
//                    break;
//                case 5:
//                    npcs.add(new Kondoria(this, new Vector2(x, y)));
//                    break;
//                case 6:
//                    npcs.add(new Ovapi(this, new Vector2(x, y)));
//                case 7:
//                    npcs.add(new Pass(this, new Vector2(x, y)));
//
//                    break;
//                case 8:
//                    npcs.add(new Pontan(this, new Vector2(x, y)));
//                    break;
//            }
//
//        }
    }

    public void generateNpc(int count, int type){
        try{generateNpcSave(count,  type);}
        catch(Exception ee){
            //Log.e("generateNpc","er");
        }

    }

    public World(/*int w, int h,int stage*/Preferences  prefs) {
//        inreasedPowerUp = "";
//        //width = w;
//        //height=h;
//        width = 32;
//        walkingControl = new WalkingControl (new Vector2(0F,0F), new Vector2(prefs.getFloat("xbomb"),prefs.getFloat("ybomb")),
//                new Vector2(prefs.getFloat("xdetonator"),prefs.getFloat("ydetonator")));
//        //controlArrows = new WalkingControlArrows(new Vector2(3F,3F), new Vector2(7F,3F), new Vector2(5F,5F), new Vector2(5F,1F));
//        controlArrows = new WalkingControlArrows(new Vector2(prefs.getFloat("xl"),prefs.getFloat("yl")),
//                new Vector2(prefs.getFloat("xr"),prefs.getFloat("yr")),
//                new Vector2(prefs.getFloat("xu"),prefs.getFloat("yu")),
//                new Vector2(prefs.getFloat("xd"),prefs.getFloat("yd")),
//                new Vector2(prefs.getFloat("xbomb"),prefs.getFloat("ybomb")),
//                new Vector2(prefs.getFloat("xdetonator"),prefs.getFloat("ydetonator"))
//        );
//        height=14;
//        //this.stage = stage;
//        this.prefs =  prefs;
//        points = this.prefs.getInteger("points");
//        Left = prefs.getInteger("left");
//
//        npcs = new Array<NpcBase>();
//        bombs = new Array<Bomb>();
//        booms = new Array<Boom>();
//        hiddenObjects = new Array<HiddenObject>();
//        blocks = new Array<BrickBase>();
//
//        if(!prefs.getString("stage").equals("51")) createLevel();
//
//        map = new int[height][width];
//        map = new int[height][width];
//
//        for(int i=0;i<height;++i)
//            for(int j=0;j<width;++j)
//                map[i][j]= 0;
//        setMap();
//        //else bomberman = new Bomberman(this, new Vector2(0, 2F));
//
//        //createDemoWorld();

    }
    private void setMap(){
//        for (BrickBase block : blocks)
//            if(block instanceof Brick)
//                map[(int)block.position.y][(int)block.position.x] = 2;
//            else
//                map[(int)block.position.y][(int)block.position.x] = 1;
    }
	/*public void setSize (int w, int h) {
		width = w;
		height=h;
	}*/

    public void dispose(){
//        booms.clear();
//        bombs.clear();
//        blocks.clear();
//        hiddenObjects.clear();
//        npcs.clear();

    }

}

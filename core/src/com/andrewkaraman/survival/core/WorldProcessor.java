package com.andrewkaraman.survival.core;

import com.andrewkaraman.survival.core.actors.Enemy;
import com.andrewkaraman.survival.core.actors.Player;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by KaramanA on 24.10.2014.
 */
public class WorldProcessor {

    public static final float WORLD_HEIGHT = 200;
    public static final float WORLD_WIDTH = 200;

    private Stage stage;



    private Player player;
    private World physWorld;
    private MyGame game;
    private Enemy enemy;

    public WorldProcessor(Stage stage, World physWorld) {
        this.stage = stage;
        this.physWorld = physWorld;
        initWorld();
    }

    private void initWorld(){
        player = new Player(physWorld);
        stage.addActor(player);
        enemy = new Enemy(physWorld);
        stage.addActor(enemy);
    }

    public Player getPlayer() { return player; }

    public void setPlayer(Player player) { this.player = player; }
}

package com.andreykaraman.survival.model;

/**
 * Created by KaramanA on 02.10.2014.
 */
public class NewWorld {
    public Player player;

    public TerrainTile[][] getMap() {
        return map;
    }

    public void setMap(TerrainTile[][] map) {
        this.map = map;
    }

    TerrainTile[][] map;


    public NewWorld() {

    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void dispose(){
    }
}

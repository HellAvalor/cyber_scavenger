package com.andreykaraman.survival.model;

/**
 * Created by Andrew on 18.09.2014.
 */
public class TerrainTile {

    private int id;
    private String name;
    private String loot;
    private boolean walkable;
    private int height;
    private String tile;

    public TerrainTile(int id, String name, String loot, boolean walkable, int height, String tile) {
        this.id = id;
        this.name = name;
        this.loot = loot;
        this.walkable = walkable;
        this.height = height;
        this.tile = tile;
    }

    public TerrainTile() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoot() {
        return loot;
    }

    public void setLoot(String loot) {
        this.loot = loot;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getTile() {
        return tile;
    }

    public void setTile(String tile) {
        this.tile = tile;
    }

}

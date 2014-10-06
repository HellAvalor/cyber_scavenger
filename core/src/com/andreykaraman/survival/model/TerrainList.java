package com.andreykaraman.survival.model;

import java.util.ArrayList;

/**
 * Created by Andrew on 19.09.2014.
 */
public class TerrainList {
    protected ArrayList<TerrainTile> terrainList;

    public ArrayList<TerrainTile> getTerrains() {
        return terrainList;
    }

    public void setTerrains(ArrayList<TerrainTile> terrainList) {
        this.terrainList = terrainList;
    }


}

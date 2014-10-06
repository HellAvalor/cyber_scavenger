package com.andreykaraman.survival.utils;

import com.andreykaraman.survival.model.TerrainList;
import com.andreykaraman.survival.model.TerrainTile;
import com.andreykaraman.survival.providers.ImageProvider;
import com.badlogic.gdx.Gdx;

/**
 * Created by Andrew on 18.09.2014.
 */
public class MapGenerator {


    protected final static String TAG = "MapGenerator";

    public static int MAP_SIZE = 24;

    public static TerrainTile[][] getMap(TerrainList terrainList, ImageProvider imageProvider) {
       TerrainTile[][] map = new TerrainTile[MAP_SIZE][MAP_SIZE];
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                int tileType = (int)(Math.random()*terrainList.getTerrains().size());
                map[i][j] = (TerrainTile) terrainList.getTerrains().get(tileType);
                map[i][j].setTextureRegion(imageProvider.getTexture(((TerrainTile) terrainList.getTerrains().get(tileType)).getTile()));
                Gdx.app.log(TAG, "getName = " + map[i][j].getName() + "getLoot = " + map[i][j].getLoot() + "getTile =" + map[i][j].getTile());
            }
        }
        return map;
    }

    private TerrainTile generateTile(){
        TerrainTile tile = new TerrainTile();
        return tile;
    }
}

package com.andreykaraman.survival.managers;

import com.andreykaraman.survival.model.TerrainList;
import com.andreykaraman.survival.model.TerrainTile;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

/**
 * Created by Andrew on 18.09.2014.
 */
public class TerrainManager {
    protected final static String TAG = "terrainManager";

    public static TerrainList getTerrainTiles() {

        FileHandle handle = Gdx.files.internal("data/terrain.json");
        String fileContent = handle.readString();
        Json json = new Json();
        json.setElementType(TerrainList.class, "terrainList", TerrainTile.class);
        TerrainList data;
        data = json.fromJson(TerrainList.class, fileContent);

        for (Object e : data.getTerrains()) {
            TerrainTile tile = (TerrainTile) e;
            Gdx.app.log(TAG, "type = " + tile.getName() + "x = " + tile.getLoot() + "y =" + tile.getTile());
        }

        return data;
    }
}

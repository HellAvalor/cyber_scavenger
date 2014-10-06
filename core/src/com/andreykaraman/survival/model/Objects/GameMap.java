package com.andreykaraman.survival.model.Objects;

import com.andreykaraman.survival.Game.Assets;
import com.andreykaraman.survival.managers.TerrainManager;
import com.andreykaraman.survival.model.TerrainTile;
import com.andreykaraman.survival.utils.Constants;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

/**
 * Created by KaramanA on 06.10.2014.
 */
public class GameMap extends AbstractGameObject{

    private TerrainTile[][] mapInfo = new TerrainTile[Constants.MAP_SIZE][Constants.MAP_SIZE];
    private TextureRegion[][] mapTextures = new TextureRegion[Constants.MAP_SIZE][Constants.MAP_SIZE];


    protected final static String TAG = "MapGenerator";

    public GameMap() {
       init();
    }


    private void init(){
        generateMap(TerrainManager.getTerrainTiles().getTerrains());
    }

    private void generateMap(ArrayList<TerrainTile> terrainList) {

        ArrayList<TextureAtlas.AtlasRegion> map = Assets.instance.assetMap.map;

        for (int i = 0; i < Constants.MAP_SIZE; i++) {
            for (int j = 0; j < Constants.MAP_SIZE; j++) {
                int tileType = (int)(Math.random()*terrainList.size());
                mapInfo[i][j] = terrainList.get(tileType);
                mapTextures[i][j] = map.get(tileType);
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        for (int i = 0; i < Constants.MAP_SIZE; i++) {
            for (int j = 0; j < Constants.MAP_SIZE; j++) {
                batch.draw(mapTextures[i][j], i * 64, j * 64);

                BitmapFont fpsFont = Assets.instance.fonts.defaultSmall;
                fpsFont.draw(batch, "x " + i + " y " + j , i * 64, j * 64);
                fpsFont.setColor(1, 1, 1, 1);

            }
        }
    }
}

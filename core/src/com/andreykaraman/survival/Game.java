package com.andreykaraman.survival;

import com.andreykaraman.survival.model.TerrainTile;
import com.andreykaraman.survival.utils.OrthoCamController;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Game extends ApplicationAdapter implements Screen {
    Texture img;

    int mapSize = 640;
    OrthographicCamera cam;
    OrthoCamController cameraController;
    TiledMapRenderer renderer;
    AssetManager assetManager;
    Texture tiles;
    Texture texture;
    BitmapFont font;
    SpriteBatch batch;
    MapLayer mapLayer;
//    TiledMap map;

    float scalex;
    float scaley;
    float camscalex;
    float camscaley;
    int CAM_SCALE;

    public float w;
    public float h;

    TerrainTile[][] map;
    Texture deepwater;

    @Override
    public void create() {
        //batch = new SpriteBatch();
        //img = new Texture("badlogic.jpg");


        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        cam = new OrthographicCamera();
        cam.setToOrtho(false, (w / h) * 320, 320);
        cam.update();

        cameraController = new OrthoCamController(cam);

        font = new BitmapFont();
        batch = new SpriteBatch();

        //     TerrainList t = TerrainManager.getTerrainTiles();
        //     map = MapGenerator.getMap(t);
        deepwater = new Texture(Gdx.files.internal("textures/land/rocks.png"));


//        {
//           // tiles = new Texture(Gdx.files.internal("textures/tiles.png"));
//            map = new TiledMap();
//          //  TextureRegion[][] splitTiles = TextureRegion.split(tiles, 64, 64);
//            MapLayers layers = map.getLayers();
//            for (int l = 0; l < 20; l++){
//                TiledMapTileLayer layer = new TiledMapTileLayer(150, 100, 64, 64);
//                for (int x = 0; x<150; x++){
//                    for (int y = 0; y < 100; y++) {
//                      //  int ty = (int)(Math.random() * splitTiles.length);
//                     //   int tx = (int)(Math.random() * splitTiles[ty].length);
//                        Cell cell = new Cell();
//                        cell.setTile(new Texture(Gdx.files.internal("textures/land/grass.png")));
//                        layer.setCell(x,y,cell);
//                    }
//                }
//                layers.add(layer);
//            }
//        }
//
//            renderer = new OrthogonalTiledMapRenderer(map);
//
        float tex_SCALE = .5f;
        scalex = tex_SCALE;
        scaley = tex_SCALE;
    }

    @Override
    public void render() {
        handleInput();
//        Gdx.gl.glClearColor(100f / 255f, 100f / 255f, 255f / 255f, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
//        renderer.setView(camera);
//        renderer.render();
        batch.begin();

        for (int x = 0; x < mapSize; x++) {
            w = (float) (x * 64);
            for (int y = 0; y < mapSize; y++) {
                h = (float) (y * 64);
                batch.draw(deepwater, w, h, 64, 64);
            }
        }


        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        batch.end();
    }


    @Override
    public void render(float delta) {
    }


    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            cam.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            cam.zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            cam.translate(-3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            cam.translate(3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            cam.translate(0, -3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            cam.translate(0, 3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            cam.rotate(-1, 0, 0, 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            cam.rotate(1, 0, 0, 1);
        }

        float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
        float effectiveViewportHeight = cam.viewportHeight * cam.zoom;

        cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, 100 / cam.viewportWidth);
        cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
        cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);
    }

    @Override
    public void resize(int width, int height) {
        cam.viewportWidth = 30f;
        cam.viewportHeight = 30f * height/width;
        cam.update();
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
      //  mapSprite.getTexture().dispose();
        batch.dispose();
    }
}

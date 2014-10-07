package com.andreykaraman.survival.desktop;

import com.andreykaraman.survival.NewScavengerGame;
import com.andreykaraman.survival.utils.Constants;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class DesktopLauncher {
	public static void main (String[] arg) {



        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Game";
        cfg.width = 800;
        cfg.height = 480;
        cfg.resizable = true;
        cfg.fullscreen = false;

        if (Constants.TEXTURE_REPACK) {
            TexturePacker.Settings settings = new TexturePacker.Settings();
            settings.maxWidth = 512;
            settings.maxHeight = 512;

            TexturePacker.process(settings, "textures/land", "../assets/packed", "land");
        }
        new LwjglApplication(new NewScavengerGame(), cfg);
	}
}

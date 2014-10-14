package com.andreykaraman.survival.desktop;

import com.andreykaraman.survival.NewScavengerGame;
import com.andreykaraman.survival.Tyrian;
import com.andreykaraman.survival.utils.Constants;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class DesktopLauncher {
	public static void main (String[] arg) {


// create the listener that will receive the application events
        ApplicationListener listener = new Tyrian();
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Tyrian";
        cfg.width = 800;
        cfg.height = 480;
        cfg.useGL30 = false;
//        cfg.resizable = true;
//        cfg.fullscreen = false;

//        if (Constants.TEXTURE_REPACK) {
//            TexturePacker.Settings settings = new TexturePacker.Settings();
//            settings.maxWidth = 512;
//            settings.maxHeight = 512;
//
//            TexturePacker.process(settings, "textures/land", "../assets/packed", "land");
//        }

//        // define the window's title
//        String title = "Tyrian";
//
//        // define the window's size
//        int width = 800, height = 480;
//
//        // whether to use OpenGL ES 2.0
//        boolean useOpenGLES2 = false;
        new LwjglApplication(listener, cfg);
	}
}

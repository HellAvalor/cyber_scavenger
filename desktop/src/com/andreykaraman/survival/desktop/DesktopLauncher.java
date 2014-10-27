package com.andreykaraman.survival.desktop;

import com.andrewkaraman.survival.core.MyGame;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "scavenger";
        cfg.useGL30 = false;
        cfg.width = 800;
        cfg.height = 600;

        new LwjglApplication(new MyGame(), cfg);
	}
}

package com.andreykaraman.survival.desktop;

import com.andrewkaraman.survival.core.MyGame;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "libgdx-tyrian";
        cfg.useGL30 = false;
        cfg.width = 480;
        cfg.height = 320;

        new LwjglApplication(new MyGame(), cfg);
	}
}

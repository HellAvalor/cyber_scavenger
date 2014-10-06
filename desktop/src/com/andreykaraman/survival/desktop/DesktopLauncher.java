package com.andreykaraman.survival.desktop;

import com.andreykaraman.survival.CSurv1;
import com.andreykaraman.survival.utils.Constants;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        if (Constants.TEXTURE_REPACK) {
            TexturePacker.Settings settings = new TexturePacker.Settings();
            settings.maxWidth = 512;
            settings.maxHeight = 512;

            TexturePacker.process(settings, "textures/land", "../assets/packed", "land");
        }
        new LwjglApplication(new CSurv1(), config);
	}
}

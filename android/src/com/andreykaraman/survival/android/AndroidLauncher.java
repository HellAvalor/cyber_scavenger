package com.andreykaraman.survival.android;

import android.os.Bundle;

import com.andreykaraman.survival.NewScavengerGame;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        config.useAccelerometer = false;
        config.useCompass = false;
        config.useWakelock = true;
        config.useGLSurfaceView20API18 = false;

		initialize(new NewScavengerGame(), config);
	}

//    public void goHome(){
//        Intent i = new Intent();
//        i.setAction(Intent.ACTION_MAIN);
//        i.addCategory(Intent.CATEGORY_HOME);
//        this.startActivity(i);
//    }
}
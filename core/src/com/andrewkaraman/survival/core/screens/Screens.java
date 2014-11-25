package com.andrewkaraman.survival.core.screens;

import com.andrewkaraman.survival.core.MyGame;

/**
 * Created by KaramanA on 25.11.2014.
 */
public enum Screens {

//    INTRO {
//        @Override
//        protected AbstractScreen getScreenInstance() {
//            return new IntroScreen();
//        }
//    },
//
//    MAIN_MENU {
//        @Override
//        protected AbstractScreen getScreenInstance() {
//            return new MainMenuScreen();
//        }
//    },

    GAME {
        @Override
        protected AbstractScreen getScreenInstance() {
            return new GameScreen();
        }
    };

//    INVENTORY {
//        @Override
//        protected AbstractScreen getScreenInstance() {
//            return new CreditsScreen();
//        }
//    };

    protected abstract AbstractScreen getScreenInstance();

}

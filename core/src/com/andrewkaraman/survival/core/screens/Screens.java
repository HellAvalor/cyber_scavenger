package com.andrewkaraman.survival.core.screens;

import com.andrewkaraman.survival.core.MyGame;

/**
 * Created by KaramanA on 25.11.2014.
 */
public enum Screens {

    PRODUCTION {
        @Override
        protected AbstractScreen getScreenInstance() {
            return new ProductionScreen();
        }
    },

    LOADING {
        @Override
        protected AbstractScreen getScreenInstance() {
            return new LoadingScreen();
        }
    },

    GAME {
        @Override
        protected AbstractScreen getScreenInstance() {
            return new GameScreen();
        }
    },

    INVENTORY {
        @Override
        protected AbstractScreen getScreenInstance() {
            return new InventoryScreen();
        }
    };

    protected abstract AbstractScreen getScreenInstance();

}

package com.andrewkaraman.survival.core.model;

import com.andrewkaraman.survival.core.actors.SmartEnemy;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by KaramanA on 20.11.2014.
 */
public enum SmartEnemyState implements State<SmartEnemy> {

    NONE() {
        @Override
        public void enter(SmartEnemy entity) {

        }

        @Override
        public void update(SmartEnemy entity) {

        }

        @Override
        public void exit(SmartEnemy entity) {

        }

        @Override
        public boolean onMessage(SmartEnemy entity, Telegram telegram) {
            return false;
        }
    },
    GLOBAL_STATE() {
        @Override
        public void enter(SmartEnemy smartEnemy) {
            // TODO Auto-generated method stub

        }

        @Override
        public void update(SmartEnemy smartEnemy) {
//            // 1 in 1000
            if (MathUtils.randomBoolean(0.001f)) {
                smartEnemy.say("OH! Gotta random event");
//                smartEnemy.getFSM().changeState(PEEING);
            }
        }

        @Override
        public void exit(SmartEnemy smartEnemy) {
            // TODO Auto-generated method stub

        }

        @Override
        public boolean onMessage(SmartEnemy smartEnemy, Telegram telegram) {
            // TODO Auto-generated method stub
            return false;
        }
    },

    IDLE() {
        @Override
        public void enter(SmartEnemy smartEnemy) {
            smartEnemy.say("Feeling great at home");
            smartEnemy.idle();
        }

        @Override
        public void update(SmartEnemy smartEnemy) {

            if (smartEnemy.isTargetInRadarRange() && smartEnemy.characteristic.getHealth() < 3) {
                smartEnemy.getFSM().changeState(FLEE);
                return;
            }

            if (smartEnemy.isTargetInRadarRange()) smartEnemy.getFSM().changeState(SEEK);
        }

        @Override
        public void exit(SmartEnemy smartEnemy) {
            smartEnemy.say("Let's move!");

        }

        @Override
        public boolean onMessage(SmartEnemy smartEnemy, Telegram telegram) {
            // TODO Auto-generated method stub
            return false;
        }
    },

    SEEK() {
        @Override
        public void enter(SmartEnemy smartEnemy) {
            smartEnemy.say("Begin seeking");
            smartEnemy.seek();
        }

        @Override
        public void update(SmartEnemy smartEnemy) {
            if (smartEnemy.isTargetInShootingRange()) smartEnemy.getFSM().changeState(FIGHT);
            if (!smartEnemy.isTargetInRadarRange()) smartEnemy.getFSM().changeState(IDLE);
        }

        @Override
        public void exit(SmartEnemy smartEnemy) {
            smartEnemy.say("Stop seeking");

        }

        @Override
        public boolean onMessage(SmartEnemy smartEnemy, Telegram telegram) {
            // TODO Auto-generated method stub
            return false;
        }
    },

    FIGHT() {
        @Override
        public void enter(SmartEnemy smartEnemy) {
            smartEnemy.say("Begin fighting");
            smartEnemy.fight();
        }

        @Override
        public void update(SmartEnemy smartEnemy) {
            if (smartEnemy.characteristic.getHealth() < 3) {
                smartEnemy.getFSM().changeState(FLEE);
            }
            if (!smartEnemy.isTargetInShootingRange() && smartEnemy.isTargetInRadarRange())
                smartEnemy.getFSM().changeState(SEEK);
        }

        @Override
        public void exit(SmartEnemy smartEnemy) {
            smartEnemy.say("Stop fighting");
            smartEnemy.setShooting(false);
        }

        @Override
        public boolean onMessage(SmartEnemy smartEnemy, Telegram telegram) {
            // TODO Auto-generated method stub
            return false;
        }
    },

    FLEE() {
        @Override
        public void enter(SmartEnemy smartEnemy) {
            smartEnemy.say("Begin flee");
            smartEnemy.flee();

        }

        @Override
        public void update(SmartEnemy smartEnemy) {
            if (!smartEnemy.isTargetInRadarRange()) smartEnemy.getFSM().changeState(IDLE);

        }

        @Override
        public void exit(SmartEnemy smartEnemy) {
            smartEnemy.say("Stop flee");
        }

        @Override
        public boolean onMessage(SmartEnemy smartEnemy, Telegram telegram) {
            // TODO Auto-generated method stub
            return false;
        }
    }

}
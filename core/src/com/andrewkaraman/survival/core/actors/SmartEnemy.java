package com.andrewkaraman.survival.core.actors;

import com.andrewkaraman.survival.core.model.EnemyCharacteristic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.Proximity;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

import aurelienribon.bodyeditor.BodyEditorLoader;

/**
 * Created by KaramanA on 15.11.2014.
 */
public class SmartEnemy extends AbsActorImpl {
    private final String LOG_CLASS_NAME = this.getClass().getName();

    public boolean alive;
//    protected EnemyCharacteristic characteristic;

    public SmartEnemy(World world) {
        this(world, 2, 4, 1);
    }

    public SmartEnemy(World world, float startPosX, float startPosY, float actorWidth) {

        Texture tex = new Texture(Gdx.files.internal("front-gun-proton-launcher.png"));
        this.setDrawable(new TextureRegionDrawable(new TextureRegion(tex)));

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.x = startPosX;
        bodyDef.position.y = startPosY;
        bodyDef.linearDamping = 0.1f;
        bodyDef.angularDamping = 0.5f;

        body = world.createBody(bodyDef);
        characteristic = new EnemyCharacteristic();
//        body.setUserData(characteristic);

        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("testPhysSettings.json"));

        FixtureDef fd = new FixtureDef();
        fd.friction = 0.1f;
        fd.restitution = 0.3f;
        fd.density = 5;

        fd.filter.categoryBits = (short) ActorsCategories.ENEMY_SHIP.getTypeMask();
        fd.filter.maskBits = (short) (ActorsCategories.BULLET.getTypeMask()|ActorsCategories.USER.getTypeMask());

        loader.attachFixture(body, "Enemy", fd, 1);

        setSize(actorWidth, actorWidth * (tex.getHeight() / tex.getWidth())); // scale actor to body's size

        setOrigin(Align.center);
        setScaling(Scaling.stretch);
        init(startPosX, startPosY);
        alive = true;
        setVisible(alive);
        body.setLinearVelocity(0.2f,0.2f);
        setIndependentFacing(true);
        body.setUserData(this);
        textureSetup();
    }

    public void init(float posX, float posY) {
        body.setTransform(posX, posY, 0);
        setRotation(MathUtils.radiansToDegrees * body.getAngle());
        setPosition(body.getPosition().x, body.getPosition().y); // set the actor position at the box2d body position

        characteristic.setAlive(true);
        body.setActive(true);
        alive = true;
        setVisible(alive);
    }

    @Override
    protected void updateMotion(float delta) {
        if (steeringBehavior != null) {
            // Calculate steering acceleration
            steeringBehavior.calculateSteering(steeringOutput);
            /*
             * Here you might want to add a motor control layer filtering steering accelerations.
             *
             * For instance, a car in a driving game has physical constraints on its movement:
             * - it cannot turn while stationary
             * - the faster it moves, the slower it can turn (without going into a skid)
             * - it can brake much more quickly than it can accelerate
             * - it only moves in the direction it is facing (ignoring power slides)
             */

            // Apply steering acceleration to move this agent
            applySteering(steeringOutput, delta);
        }
    }

    private void applySteering (SteeringAcceleration<Vector2> steering, float deltaTime) {
        boolean anyAccelerations = false;

        // Update position and linear velocity.
        if (!steeringOutput.linear.isZero()) {
            Vector2 force = steeringOutput.linear.scl(deltaTime);
            body.applyForceToCenter(force, true);
            anyAccelerations = true;
        }


        // Update orientation and angular velocity
        if (isIndependentFacing()) {
            if (steeringOutput.angular != 0) {
                body.applyTorque(steeringOutput.angular * deltaTime, true);
                anyAccelerations = true;
            }
        }
        else {
            // If we haven't got any velocity, then we can do nothing.
            Vector2 linVel = getLinearVelocity();
            if (!linVel.isZero(MathUtils.FLOAT_ROUNDING_ERROR)) {
                float newOrientation = vectorToAngle(linVel);
                body.setAngularVelocity((newOrientation - getAngularVelocity()) * deltaTime); // this is superfluous if independentFacing is always true
                body.setTransform(body.getPosition(), newOrientation);
            }
        }


        if (anyAccelerations) {
            // body.activate();


            // TODO:
            // Looks like truncating speeds here after applying forces doesn't work as expected.
            // We should likely cap speeds form inside an InternalTickCallback, see
            // http://www.bulletphysics.org/mediawiki-1.5.8/index.php/Simulation_Tick_Callbacks


            // Cap the linear speed
            Vector2 velocity = body.getLinearVelocity();
            float currentSpeedSquare = velocity.len2();
            float maxLinearSpeed = getMaxLinearSpeed();
            if (currentSpeedSquare > maxLinearSpeed * maxLinearSpeed) {
                body.setLinearVelocity(velocity.scl(maxLinearSpeed / (float)Math.sqrt(currentSpeedSquare)));
            }


            // Cap the angular speed
            float maxAngVelocity = getMaxAngularSpeed();
            if (body.getAngularVelocity() > maxAngVelocity) {
                body.setAngularVelocity(maxAngVelocity);
            }
        }
    }

//    @Override
//    public void reset() {
//        alive = false;
//        setVisible(alive);
//        body.setActive(false);
//        body.setLinearVelocity(0,0);
//    }

}

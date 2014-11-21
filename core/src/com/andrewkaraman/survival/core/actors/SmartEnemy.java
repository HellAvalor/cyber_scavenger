package com.andrewkaraman.survival.core.actors;

import com.andrewkaraman.survival.core.GameWorld;
import com.andrewkaraman.survival.core.model.EnemyCharacteristic;
import com.andrewkaraman.survival.core.model.SmartEnemyState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.behaviors.Evade;
import com.badlogic.gdx.ai.steer.behaviors.Face;
import com.badlogic.gdx.ai.steer.behaviors.LookWhereYouAreGoing;
import com.badlogic.gdx.ai.steer.behaviors.PrioritySteering;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Scaling;

import aurelienribon.bodyeditor.BodyEditorLoader;

/**
 * Created by KaramanA on 15.11.2014.
 */
public class SmartEnemy extends AbsActorImpl implements Pool.Poolable {
    private final String LOG_CLASS_NAME = this.getClass().getName();

    private StateMachine<SmartEnemy> fsm;
    public boolean alive;
    private float detectionRadius = 3;
    private float shootingRadius = 1;
    private boolean seeTarget = false;
    private GameWorld gameWorld;

//    protected EnemyCharacteristic characteristic;

    public SmartEnemy(GameWorld gameWorld) {
        this(gameWorld, 2, 4, 1);
    }

    public SmartEnemy(GameWorld gameWorld, float startPosX, float startPosY, float actorWidth) {
        this.gameWorld = gameWorld;

        Texture tex = new Texture(Gdx.files.internal("front-gun-proton-launcher.png"));
        this.setDrawable(new TextureRegionDrawable(new TextureRegion(tex)));

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.x = startPosX;
        bodyDef.position.y = startPosY;
        bodyDef.linearDamping = 0.1f;
        bodyDef.angularDamping = 0.5f;

        body = gameWorld.box2dWorld.createBody(bodyDef);
        characteristic = new EnemyCharacteristic();
//        body.setUserData(characteristic);

        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("testPhysSettings.json"));

        FixtureDef fd = new FixtureDef();
        fd.friction = 0.1f;
        fd.restitution = 0.3f;
        fd.density = 5;

        fd.filter.categoryBits = (short) ActorsCategories.ENEMY_SHIP.getTypeMask();
        fd.filter.maskBits = (short) (ActorsCategories.BULLET.getTypeMask()|ActorsCategories.USER.getTypeMask());

        FixtureDef fd2 = new FixtureDef();
        //add radar sensor to ship
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(detectionRadius);
        fd2.shape = circleShape;
        fd2.isSensor = true;
        fd2.filter.categoryBits = (short) ActorsCategories.RADAR_SENSOR.getTypeMask();
        fd2.filter.maskBits = (short) (ActorsCategories.USER.getTypeMask());

        loader.attachFixture(body, "Enemy", fd, 1);
        body.createFixture(fd2);

        circleShape.dispose();

        setSize(actorWidth, actorWidth * (tex.getHeight() / tex.getWidth())); // scale actor to body's size

        setOrigin(Align.center);
        setScaling(Scaling.stretch);
        alive = true;
        setVisible(alive);
        body.setLinearVelocity(0.2f,0.2f);
        setIndependentFacing(true);
        body.setUserData(this);

        fsm = new DefaultStateMachine<SmartEnemy>(this, SmartEnemyState.IDLE);
        fsm.setGlobalState(SmartEnemyState.GLOBAL_STATE);
        init(startPosX, startPosY);
        textureSetup();
    }

    public void init(float posX, float posY) {
        body.setTransform(posX, posY, 0);
        setRotation(MathUtils.radiansToDegrees * body.getAngle());
        setPosition(body.getPosition().x, body.getPosition().y); // set the actor position at the box2d body position

        characteristic.setAlive(true);
        characteristic.setHealth(10);
//        fsm.setGlobalState(SmartEnemyState.GLOBAL_STATE);
        fsm.changeState(SmartEnemyState.IDLE);
        body.setActive(true);
        alive = true;
        setVisible(alive);
    }

    @Override
    protected void updateMotion(float delta) {
        fsm.update();
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

    public StateMachine<SmartEnemy> getFSM() {
        return fsm;
    }

    public void say(String string) {
        Gdx.app.log(LOG_CLASS_NAME, string);
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        super.drawDebug(shapes);
        shapes.setColor(0, 0, 1, 1);
        shapes.circle(body.getPosition().x, body.getPosition().y, detectionRadius+1);
        shapes.circle(body.getPosition().x, body.getPosition().y, shootingRadius);
    }

    public boolean isSeeTarget() {
        return seeTarget;
    }

    public void setSeeTarget(boolean seeTarget) {
        this.seeTarget = seeTarget;
    }


    @Override
    public void reset() {
        fsm.changeState(SmartEnemyState.NONE);
        //TODO stop global state
//        fsm.setGlobalState(SmartEnemyState.NONE);
        alive = false;
        setVisible(alive);
        body.setActive(false);
        body.setLinearVelocity(0,0);
    }

    public void flee(){
        setMaxLinearSpeed(5);
        setMaxLinearAcceleration(500);
        setMaxAngularAcceleration(40);
        setMaxAngularSpeed(15);
        boundingRadius = 1;

        LookWhereYouAreGoing<Vector2> lookWhereYouAreGoingSB = new LookWhereYouAreGoing<Vector2>(this) //
                .setTimeToTarget(0.1f) //
                .setAlignTolerance(0.001f) //
                .setDecelerationRadius(MathUtils.PI/2);

        Evade<Vector2> evade  = new Evade<Vector2>(this, gameWorld.player);

        PrioritySteering<Vector2> prioritySteeringSB = new PrioritySteering<Vector2>(this, 0.0001f);
        prioritySteeringSB.add(evade);
        prioritySteeringSB.add(lookWhereYouAreGoingSB);


        setSteeringBehavior(prioritySteeringSB);
    }

    public void fight(){
//        setMaxLinearSpeed(5);
//        setMaxLinearAcceleration(500);
//        setMaxAngularAcceleration(40);
//        setMaxAngularSpeed(15);
//        boundingRadius = 1;
//
//        Seek<Vector2> seek = new Seek<Vector2>(this);
//
//        LookWhereYouAreGoing<Vector2> lookWhereYouAreGoingSB = new LookWhereYouAreGoing<Vector2>(this) //
//                .setTimeToTarget(0.1f) //
//                .setAlignTolerance(0.001f) //
//                .setDecelerationRadius(MathUtils.PI);
//
//        PrioritySteering<Vector2> prioritySteeringSB = new PrioritySteering<Vector2>(this, 0.0001f);
//        prioritySteeringSB.add(seek);
//        prioritySteeringSB.add(lookWhereYouAreGoingSB);
//
//        setSteeringBehavior(prioritySteeringSB);
    }

    public void idle(){

        setMaxLinearSpeed(5);
        setMaxLinearAcceleration(500);
        setMaxAngularAcceleration(40);
        setMaxAngularSpeed(15);
        boundingRadius = 1;

        Face<Vector2> face  = new Face<Vector2>(this, gameWorld.player)
                .setTimeToTarget(0.1f) //
                .setAlignTolerance(0.001f) //
                .setDecelerationRadius(MathUtils.degreesToRadians * 180);


        PrioritySteering<Vector2> prioritySteeringSB = new PrioritySteering<Vector2>(this, 0.0001f);
        prioritySteeringSB.add(face);

        setSteeringBehavior(prioritySteeringSB);
    }
}

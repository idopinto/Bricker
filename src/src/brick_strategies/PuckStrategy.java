package src.brick_strategies;

import danogl.*;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Puck;

import java.util.Random;


public class PuckStrategy extends RemoveBrickStrategyDecorator {
    private static final int NUM_OF_PUCKS = 3;
    private static final float PUCK_SPEED = 360; /*TODO 20 % more or less then ball speed*/
    private ImageReader imageReader;
    private SoundReader soundReader;

    /**
     * @param toBeDecorated - Collision strategy object to be decorated.
     */
    public PuckStrategy(CollisionStrategy toBeDecorated, ImageReader imageReader, SoundReader soundReader) {
        super(toBeDecorated);
        this.imageReader = imageReader;
        this.soundReader = soundReader;
    }

    /**
     * Add pucks to game on collision and delegate to held CollisionStrategy.
     *
     * @param thisObj
     * @param otherObj
     * @param brickCounter global brick counter
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter brickCounter) {

        super.onCollision(thisObj, otherObj, brickCounter);
        createPucks(thisObj);
    }

    private void createPucks(GameObject brick) {
        Renderable puckImage = this.imageReader.readImage("assets/mockBall.png", true);
        Sound collisionSound = this.soundReader.readSound("assets/blop.wav");
        Vector2 brickCenter = brick.getCenter();
        Vector2 puckDimensions = new Vector2(brick.getDimensions().x()/3,brick.getDimensions().x()/3);
        Vector2 puckVelocity = reflectVelocityVectorRandomlyPuck();
        for (int i = 0; i < NUM_OF_PUCKS; i++) {
            Vector2 puckPosition = new Vector2(brick.getTopLeftCorner().x() + i * puckDimensions.x(), brick.getCenter().y());
            Puck puck = new Puck(puckPosition, puckDimensions, puckImage, collisionSound);
            puck.setVelocity(puckVelocity);

            this.getGameObjectCollection().addGameObject(puck);
        }
    }

    /*  This method randomly returns Vector2 which represents the ball direction and speed */
    private Vector2 reflectVelocityVectorRandomlyPuck() {
        float puckVelX = PUCK_SPEED;
        float puckVelY = PUCK_SPEED;
        Random random = new Random();
        if (random.nextBoolean()) {
            puckVelX *= -1;
        } else {
            puckVelY *= -1;
        }
        return new Vector2(puckVelX, puckVelY);
    }
}

package src.brick_strategies;

import danogl.*;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Ball;
import src.gameobjects.Puck;

import java.util.Random;


public class PuckStrategy extends RemoveBrickStrategyDecorator {
    private static final int PUCK_SIZE = 25;
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
        Vector2[] velocities = {new Vector2(0,300),new Vector2(300,300),new Vector2(-300,-300)};
        for (int i = 0; i < 3; i++) {

            Puck puck = new Puck(Vector2.ZERO, new Vector2(20, 20), puckImage, collisionSound);
            puck.setCenter(brickCenter);
            puck.setVelocity(velocities[i]);
            this.getGameObjectCollection().addGameObject(puck);
        }
    }
}

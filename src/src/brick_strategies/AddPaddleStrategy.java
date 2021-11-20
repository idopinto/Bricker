package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.*;
import danogl.gui.*;
import src.gameobjects.MockPaddle;
import src.gameobjects.Paddle;

import java.util.Random;

/**
 * Concrete class extending abstract RemoveBrickStrategyDecorator. Introduces extra paddle to game window which remains
 * until colliding NUM_COLLISIONS_FOR_MOCK_PADDLE_DISAPPEARANCE with other game objects.
 */
public class AddPaddleStrategy extends RemoveBrickStrategyDecorator{
    private ImageReader imageReader;
    private UserInputListener inputListener;
    private Vector2 windowDimensions;
    private static int  NUM_COLLISIONS_FOR_MOCK_PADDLE_DISAPPEARANCE = 3;

    /**
     * @param toBeDecorated - Collision strategy object to be decorated.
     */
    public AddPaddleStrategy(CollisionStrategy toBeDecorated,
                              ImageReader imageReader,
                              UserInputListener inputListener,
                              Vector2 windowDimensions)
    {
        super(toBeDecorated);
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter brickCounter) {
        super.onCollision(thisObj, otherObj, brickCounter);
        createMockPaddle();
    }

    private void createMockPaddle() {
        Renderable paddleImage = imageReader.readImage("assets/paddle.png", true);
        Random random = new Random();
        int r = random.nextInt(100);
        // create mock paddle
        MockPaddle mockPaddle = new MockPaddle(Vector2.ZERO, new Vector2(100, 15), paddleImage, inputListener,
                windowDimensions,getGameObjectCollection(),20,NUM_COLLISIONS_FOR_MOCK_PADDLE_DISAPPEARANCE);

        mockPaddle.setCenter(new Vector2(windowDimensions.x() / 2, (int) windowDimensions.y() - 40 - r));
        this.getGameObjectCollection().addGameObject(mockPaddle);
    }

}

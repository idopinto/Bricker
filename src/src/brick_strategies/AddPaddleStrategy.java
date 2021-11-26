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
    private static int MIN_DISTANCE_FROM_EDGE = 20;
    private static int MOCK_PADDLE_HEIGHT = 15;
    private static int MOCK_PADDLE_WIDTH= 100;


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
        if (!MockPaddle.isInstantiated){
            createMockPaddle(thisObj);
        }

    }

    private void createMockPaddle(GameObject thisObj) {
        Renderable paddleImage = imageReader.readImage("assets/paddle.png", true);
        // create mock paddle
        MockPaddle mockPaddle = new MockPaddle(Vector2.ZERO, new Vector2(MOCK_PADDLE_WIDTH, MOCK_PADDLE_HEIGHT),
                                                paddleImage, inputListener,
                windowDimensions,getGameObjectCollection(),MIN_DISTANCE_FROM_EDGE,NUM_COLLISIONS_FOR_MOCK_PADDLE_DISAPPEARANCE);

        mockPaddle.setCenter(new Vector2(thisObj.getCenter().x(), (int) (windowDimensions.y()/2)));
        this.getGameObjectCollection().addGameObject(mockPaddle);
    }

}

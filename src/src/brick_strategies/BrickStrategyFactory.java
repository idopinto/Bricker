package src.brick_strategies;
import danogl.collisions.GameObjectCollection;
import danogl.gui.*;
import danogl.util.*;
import src.*;
import src.gameobjects.Paddle;

import java.util.Random;

public class BrickStrategyFactory {
    private static  final int NUM_OF_STRATEGIES = 5;
    private static  final int MAX_STRATEGIES = 3;


    private  GameObjectCollection gameObjects;
    private BrickerGameManager gameManager;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private UserInputListener inputListener;
    private WindowController windowController;
    private Vector2 windowDimensions;

    public BrickStrategyFactory(GameObjectCollection gameObjectCollection,
                                 BrickerGameManager gameManager,
                                 ImageReader imageReader,
                                 SoundReader soundReader,
                                 UserInputListener inputListener,
                                 WindowController windowController,
                                 Vector2 windowDimensions)
    {
        this.gameObjects = gameObjectCollection;
        this.gameManager = gameManager;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.windowDimensions = windowDimensions;

    }

    public CollisionStrategy getStrategy(){
        // Choose randomly between the possible brick strategies

        Random random = new Random();
        int numberOfStrategies = random.nextInt(MAX_STRATEGIES);
        CollisionStrategy removeBrickStrategy = new RemoveBrickStrategy(this.gameObjects);

        for (int i = 0; i < numberOfStrategies; i++) {

            removeBrickStrategy = this.selectStrategy(removeBrickStrategy,random);
        }

        return removeBrickStrategy;
    }

    private CollisionStrategy selectStrategy(CollisionStrategy collisionStrategy,Random random)
    {
        int randomStrategy = random.nextInt(NUM_OF_STRATEGIES);
//        int randomStrategy = 4;
        if (randomStrategy != StrategyType.DEFAULT.getValue()) {

            if (randomStrategy == StrategyType.ADD_PADDLE.getValue()) {
                collisionStrategy = new AddPaddleStrategy(collisionStrategy, this.imageReader, this.inputListener, this.windowDimensions);
            }
            if (randomStrategy == StrategyType.CHANGE_CAMERA.getValue()) {
                collisionStrategy = new ChangeCameraStrategy(collisionStrategy, this.windowController, this.gameManager);
            }
            if (randomStrategy == StrategyType.PUCK.getValue()) {
                collisionStrategy = new PuckStrategy(collisionStrategy, this.imageReader, this.soundReader);

            }
            if (randomStrategy == StrategyType.WIDE_PADDLE.getValue()) {
                collisionStrategy = new WidePaddleStrategy(collisionStrategy, this.imageReader, this.windowDimensions);
            }
        }
        return collisionStrategy;
    }

}

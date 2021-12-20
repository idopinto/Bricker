package src.brick_strategies;
import danogl.collisions.GameObjectCollection;
import danogl.gui.*;
import danogl.util.*;
import src.*;

import java.util.Random;

public class BrickStrategyFactory {
    private static  final int MAX_STRATEGIES = 3;


    private final GameObjectCollection gameObjects;
    private final BrickerGameManager gameManager;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final UserInputListener inputListener;
    private final WindowController windowController;
    private final Vector2 windowDimensions;

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
        int isDoubleStrategy = random.nextInt(6);
        int maxStrategies =  MAX_STRATEGIES;
        CollisionStrategy removeBrickStrategy = new RemoveBrickStrategy(this.gameObjects);
        if (isDoubleStrategy == StrategyType.DOUBLE_STRATEGY.getValue())
        {
            isDoubleStrategy = random.nextInt(5);
            if (isDoubleStrategy != StrategyType.DOUBLE_STRATEGY.getValue())
            {
                maxStrategies = MAX_STRATEGIES -1;
            }

            for (int i = 0; i < maxStrategies; i++) {
                removeBrickStrategy = this.selectStrategy(removeBrickStrategy,random.nextInt(4));
            }
        }
        else
        {
            removeBrickStrategy = this.selectStrategy(removeBrickStrategy,random.nextInt(5));
        }

        return removeBrickStrategy;
    }

    private CollisionStrategy selectStrategy(CollisionStrategy collisionStrategy,int randomStrategy)
    {
        if (randomStrategy == StrategyType.ADD_PADDLE.getValue()) {
            collisionStrategy = new AddPaddleStrategy(collisionStrategy, this.imageReader, this.inputListener,
                                                                        this.windowDimensions);
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
        return collisionStrategy;
    }

}

package src.brick_strategies;
import danogl.collisions.GameObjectCollection;
import danogl.gui.*;
import danogl.util.*;
import src.*;
import src.gameobjects.Paddle;

import java.util.Random;

public class BrickStrategyFactory {

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
        RemoveBrickStrategy removeBrickStrategy = new RemoveBrickStrategy(this.gameObjects);
        Random random = new Random();
        CollisionStrategy strategy = removeBrickStrategy;
        int x = random.nextInt(5);
        if (x == 0)
        {
            strategy = new PuckStrategy(removeBrickStrategy,this.imageReader,this.soundReader);
        }
        if (x == 1)
        {
            strategy = new ChangeCameraStrategy(removeBrickStrategy,this.windowController,this.gameManager);
        }
        if (x == 2)
        {
            strategy = new AddPaddleStrategy(removeBrickStrategy,this.imageReader,this.inputListener,this.windowDimensions);
        }
        if (x == 3)
            strategy = new WidePaddleStrategy(removeBrickStrategy,this.imageReader,this.windowDimensions);
        return strategy;
//        return new WidePaddleStrategy(removeBrickStrategy,this.imageReader);
    }
}

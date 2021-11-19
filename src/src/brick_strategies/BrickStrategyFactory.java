package src.brick_strategies;

import danogl.collisions.GameObjectCollection;

public class BrickStrategyFactory {
    private final GameObjectCollection gameObjects;

    public BrickStrategyFactory(GameObjectCollection gameObjects){
        this.gameObjects = gameObjects;

    }

    public CollisionStrategy getStrategy(){
        // Choose randomly between the possible brick strategies
        return new RemoveBrickStrategy(this.gameObjects);
    }
}

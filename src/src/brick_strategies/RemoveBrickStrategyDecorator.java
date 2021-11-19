package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

public abstract class RemoveBrickStrategyDecorator implements CollisionStrategy{

    private CollisionStrategy toBeDecorated;

    /**
     *
     * @param toBeDecorated - Collision strategy object to be decorated.
     */
    public RemoveBrickStrategyDecorator(CollisionStrategy toBeDecorated){

        this.toBeDecorated = toBeDecorated;
    }

    /**
     * Should delegate to held Collision strategy object.
     * @param thisObj
     * @param otherObj
     * @param brickCounter  global brick counter
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter brickCounter) {

    }

    @Override
    public GameObjectCollection getGameObjectCollection() {
        return this.getGameObjectCollection();
    }
}

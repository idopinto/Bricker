package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

/**
 * Abstract decorator to add functionality to the remove brick strategy,
 * following the decorator pattern. All strategy decorators should inherit from this class.
 */

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
     * @param thisObj brick object
     * @param otherObj ball object
     * @param brickCounter  global brick counter
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter brickCounter) {
        toBeDecorated.onCollision(thisObj,otherObj,brickCounter);
    }

    @Override
    public GameObjectCollection getGameObjectCollection() {
        return toBeDecorated.getGameObjectCollection();
    }
}
